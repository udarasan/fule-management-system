package lk.esoft.fulemanagementsystem.service.impl;

import com.google.zxing.WriterException;
import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.dto.FuelTokenResponseDTO;
import lk.esoft.fulemanagementsystem.dto.UserDTO;
import lk.esoft.fulemanagementsystem.entity.FuelToken;
import lk.esoft.fulemanagementsystem.entity.User;
import lk.esoft.fulemanagementsystem.entity.Vehicle;
import lk.esoft.fulemanagementsystem.repository.FuelStationRepository;
import lk.esoft.fulemanagementsystem.repository.FuelTokenRepository;
import lk.esoft.fulemanagementsystem.repository.UserRepository;
import lk.esoft.fulemanagementsystem.repository.VehicleRepository;
import lk.esoft.fulemanagementsystem.service.FuelTokenService;
import lk.esoft.fulemanagementsystem.util.QRCodeGenerator;
import lk.esoft.fulemanagementsystem.util.VarList.BusinessLogicVarList;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Udara San
 * @TimeStamp 11:12 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@Service
@Transactional
public class FuelTokenServiceImpl implements FuelTokenService {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private FuelTokenRepository fuelTokenRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private FuelStationRepository fuelStationRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public int generateToken(FuelTokenDTO fuelTokenDTO) {

        //FuelTokenResponseDTO fuelTokenResponseDTO=new FuelTokenResponseDTO();

        if (fuelTokenRepository.existsById(fuelTokenDTO.getTid())) {
            //fuelTokenResponseDTO.setQrString(null);
            return VarList.Not_Found;
        } else {

            //update vehicle weekly limit
            int availableBalance = vehicleRepository.getAvailableBalance(fuelTokenDTO.getVehicleRegNo());
            int newBalance = availableBalance - fuelTokenDTO.getRequestQuota();
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicle_no(fuelTokenDTO.getVehicleRegNo());
            vehicle.setAvailable_quota(newBalance);
            vehicle.setUsername_Fk(fuelTokenDTO.getUsernameFk().getUsername());
            if(vehicleRepository.getUserName(fuelTokenDTO.getVehicleRegNo()).equals(fuelTokenDTO.getUsernameFk().getUsername())){
                vehicleRepository.save(vehicle);
            }else {
                return VarList.Not_Acceptable;
            }

            //update fuel station available stock
            int stationAvailability = fuelStationRepository.getAvailableBalance(fuelTokenDTO.getFuelStationFk().getFid());
            int requestQuota = fuelTokenDTO.getRequestQuota();
            int newAvailability = stationAvailability - requestQuota;
            int customerRequestedLimit = fuelStationRepository.getCustomerRequestQuota(fuelTokenDTO.getFuelStationFk().getFid());
            int newCustomerRequestedLimit = customerRequestedLimit + requestQuota;
            fuelStationRepository.updateFuelStationBalances(newAvailability, newCustomerRequestedLimit, fuelTokenDTO.getFuelStationFk().getFid());

            //set Price
            fuelTokenDTO.getPidFk().setPrice(fuelPriceCalculation(fuelTokenDTO.getRequestQuota()));
            //create fuel token
            fuelTokenRepository.save(modelMapper.map(fuelTokenDTO, FuelToken.class));

            /*String qr=generateQRString(fuelTokenDTO);
            try {
                byte[] qrimage=QRCodeGenerator.getQRCodeImage(qr,200,200);
                fuelTokenResponseDTO.setQrString(qrimage);
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }*/
            return VarList.Created;
        }
    }

    private String generateQRString(FuelTokenDTO fuelTokenDTO) {
        String s = "" + fuelTokenDTO.getTid().toString() + "" + fuelTokenDTO.getFillingTimeAndDate().toString() + "" +
                fuelTokenDTO.getRequestQuota().toString() + "" +
                fuelTokenDTO.getStatus() + "" +
                fuelTokenDTO.getTokenExpDate().toString() + "" +
                fuelTokenDTO.getVehicleRegNo().toString() + "" +
                fuelTokenDTO.getFuelStationFk().getFid().toString() + "" +
                fuelTokenDTO.getPidFk().getPid().toString() + "" + fuelTokenDTO.getUsernameFk().getUsername() + "";
        return s;
    }

    @Override
    public int generateTokenInFirstTime(FuelTokenDTO fuelTokenDTO) {
        FuelTokenResponseDTO fuelTokenResponseDTO = new FuelTokenResponseDTO();
        if (fuelTokenRepository.existsById(fuelTokenDTO.getTid())) {
            //fuelTokenResponseDTO.setQrString(null);
            return VarList.Not_Found;
        } else {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicle_no(fuelTokenDTO.getVehicleRegNo());
            vehicle.setAvailable_quota(20);
            vehicle.setUsername_Fk(fuelTokenDTO.getUsernameFk().getUsername());
            fuelTokenDTO.getPidFk().setPrice(fuelPriceCalculation(fuelTokenDTO.getRequestQuota()));
            vehicleRepository.save(vehicle);
            fuelTokenRepository.save(modelMapper.map(fuelTokenDTO, FuelToken.class));
           /* String qr=generateQRString(fuelTokenDTO);
            try {
                byte[] qrimage=QRCodeGenerator.getQRCodeImage(qr,200,200);
                fuelTokenResponseDTO.setQrString(qrimage);
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }*/
            return VarList.Created;
        }
    }

    @Override
    public int getAvailableBalance(Integer vehicleRegNo) {

        return vehicleRepository.getAvailableBalance(vehicleRegNo);
    }

    @Override
    public boolean vehicleRegNoExists(Integer vehicleRegNo) {
        return fuelTokenRepository.existsByVehicleRegNo(vehicleRegNo);

    }

    @Override
    public int getAvailableBalanceInStation(Integer fid) {
        return fuelStationRepository.getAvailableBalance(fid);
    }

    @Override
    public boolean checkFuelRequestAvailability(int fid) {
        HashMap<String, Object> map = fuelStationRepository.checkFuelRequestAvailability(fid);

        if (map.get("status").equals("ACCEPTED")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<FuelTokenDTO> getAllTokenByUsername(String username) {

        List<FuelToken> fuelTokenDTOList = fuelTokenRepository.getAllTokenByUsername(username);

        return modelMapper.map(fuelTokenDTOList, new TypeToken<ArrayList<FuelTokenDTO>>() {
        }.getType());

    }

    @Override
    public FuelTokenResponseDTO getAllQRandDetails(String username) {
        FuelTokenResponseDTO fuelTokenResponseDTO = new FuelTokenResponseDTO();
        FuelTokenDTO fuelTokenDTO = modelMapper.map(fuelTokenRepository.getQRGeneratingToken(username), FuelTokenDTO.class);
        String qr = generateQRString(fuelTokenDTO);
        try {
            byte[] qrimage = QRCodeGenerator.getQRCodeImage(qr, 200, 200);
            fuelTokenResponseDTO.setQrString(qrimage);

            // TODO: 12/5/2022 SET AVAILABLE QUOTA
            //fuelTokenResponseDTO.getAvailableQuota();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return fuelTokenResponseDTO;
    }

    @Override
    public List<FuelTokenDTO> getAllTokenByFuelStationId(String username) {
        //get fuel station pK
        int fid = fuelStationRepository.getFidByUserName(username);


        //get token by id
        List<FuelToken> fuelTokenDTOList = fuelTokenRepository.getAllTokenByFid(fid);

        return modelMapper.map(fuelTokenDTOList, new TypeToken<ArrayList<FuelTokenDTO>>() {
        }.getType());
    }

    @Override
    public int changeTokenStatus(int tid, String status) {
        if (fuelTokenRepository.existsById(tid)) {
            fuelTokenRepository.changeTokenStatus(tid,status);
            return VarList.Accepted;
        } else {
            return VarList.Not_Found;
        }

    }

    @Override
    public List<FuelTokenDTO> getAllTokens() {
        List<FuelToken> fuelTokenDTOList = fuelTokenRepository.findAll(Sort.by(Sort.Direction.DESC, "tid"));

        return modelMapper.map(fuelTokenDTOList, new TypeToken<ArrayList<FuelTokenDTO>>() {
        }.getType());
    }

    private double fuelPriceCalculation(int requestQuota) {
        return requestQuota * BusinessLogicVarList.OneLiter;
    }

}
