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
import lk.esoft.fulemanagementsystem.util.MailSender;
import lk.esoft.fulemanagementsystem.util.QRCodeGenerator;
import lk.esoft.fulemanagementsystem.util.VarList.BusinessLogicVarList;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private AuditServiceImpl auditService;
    @Autowired
    private JavaMailSender emailSender;

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
            auditService.saveAudit("generateToken","FAIL:Generate Fuel Token"+fuelTokenDTO.getVehicleRegNo());
            return VarList.Not_Found;
        } else {

            //update vehicle weekly limit
            int availableBalance = vehicleRepository.getAvailableBalance(fuelTokenDTO.getVehicleRegNo());
            int newBalance = availableBalance - fuelTokenDTO.getRequestQuota();
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicle_no(fuelTokenDTO.getVehicleRegNo());
            vehicle.setAvailable_quota(newBalance);
            vehicle.setUsername_Fk(fuelTokenDTO.getUsernameFk().getUsername());
            if (vehicleRepository.getUserName(fuelTokenDTO.getVehicleRegNo()).equals(fuelTokenDTO.getUsernameFk().getUsername())) {
                vehicleRepository.save(vehicle);
            } else {
                auditService.saveAudit("generateToken","FAIL:Generate Fuel Token"+fuelTokenDTO.getVehicleRegNo()+"Wrong Username");
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
            auditService.saveAudit("generateToken","PASS:Fuel Token Generation Pass"+fuelTokenDTO.getVehicleRegNo());
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
        auditService.saveAudit("generateQRString","PASS:Generate QR String"+fuelTokenDTO.getVehicleRegNo());
        return s;
    }

    @Override
    public int generateTokenInFirstTime(FuelTokenDTO fuelTokenDTO) {
        FuelTokenResponseDTO fuelTokenResponseDTO = new FuelTokenResponseDTO();
        if (fuelTokenRepository.existsById(fuelTokenDTO.getTid())) {
            //fuelTokenResponseDTO.setQrString(null);
            auditService.saveAudit("generateTokenInFirstTime","FAIL:Fist Time Generate Fuel Token"+fuelTokenDTO.getVehicleRegNo());
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

            auditService.saveAudit("generateTokenInFirstTime","PASS:Fist Time Generate Fuel Token"+fuelTokenDTO.getVehicleRegNo());
            return VarList.Created;
        }
    }

    @Override
    public int getAvailableBalance(Integer vehicleRegNo) {
        auditService.saveAudit("getAvailableBalance","PASS:Get Available Balance"+vehicleRegNo);

        return vehicleRepository.getAvailableBalance(vehicleRegNo);
    }

    @Override
    public boolean vehicleRegNoExists(Integer vehicleRegNo) {
        auditService.saveAudit("vehicleRegNoExists","PASS:Vehicle Reg No Exits"+vehicleRegNo);
        return fuelTokenRepository.existsByVehicleRegNo(vehicleRegNo);

    }

    @Override
    public int getAvailableBalanceInStation(Integer fid) {
        auditService.saveAudit("getAvailableBalanceInStation","PASS:GET Available Balance In Station"+fid);
        return fuelStationRepository.getAvailableBalance(fid);
    }

    @Override
    public boolean checkFuelRequestAvailability(int fid) {
        HashMap<String, Object> map = fuelStationRepository.checkFuelRequestAvailability(fid);

        if (map.get("status").equals("ACCEPTED")) {
            auditService.saveAudit("checkFuelRequestAvailability","PASS:Check Fuel Request Availability"+fid);
            return true;
        } else {
            auditService.saveAudit("checkFuelRequestAvailability","FAIL:Check Fuel Request Availability"+fid);
            return false;
        }
    }

    @Override
    public List<FuelTokenDTO> getAllTokenByUsername(String username) {

        List<FuelToken> fuelTokenDTOList = fuelTokenRepository.getAllTokenByUsername(username);
        auditService.saveAudit("getAllTokenByUsername","PASS:Get All Token By Username"+username);
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

        auditService.saveAudit("getAllQRandDetails","PASS:Get All QR And Details"+username);
        return fuelTokenResponseDTO;
    }

    @Override
    public List<FuelTokenDTO> getAllTokenByFuelStationId(String username) {
        //get fuel station pK
        int fid = fuelStationRepository.getFidByUserName(username);


        //get token by id
        List<FuelToken> fuelTokenDTOList = fuelTokenRepository.getAllTokenByFid(fid);

        auditService.saveAudit("getAllTokenByFuelStationId","PASS:Get All Token By FuelStationId"+username);
        return modelMapper.map(fuelTokenDTOList, new TypeToken<ArrayList<FuelTokenDTO>>() {
        }.getType());
    }

    @Override
    public int changeTokenStatus(int tid, String status) {
        //MailSender mailSender=new MailSender();
        try {
            if (fuelTokenRepository.existsById(tid)) {
                fuelTokenRepository.changeTokenStatus(tid, status);

                FuelToken fuelToken = fuelTokenRepository.findById(tid).orElse(null);
                System.out.println(fuelToken.getUsernameFk().getEmail());
                sendMail(fuelToken.getUsernameFk().getEmail(), status, fuelToken.getFillingTimeAndDate(), fuelToken.getFuelStationFk().getStationName());

                auditService.saveAudit("changeTokenStatus","PASS:Change Token Status :"+status);
                return VarList.Accepted;
            } else {
                auditService.saveAudit("changeTokenStatus","FAIL:Change Token Status :"+status);
                return VarList.Not_Found;
            }
        } catch (Exception e) {
            auditService.saveAudit("changeTokenStatus","FAIL:Change Token Status :"+status +"Exception");
            return 0;
        }


    }

    @Override
    public List<FuelTokenDTO> getAllTokens() {
        List<FuelToken> fuelTokenDTOList = fuelTokenRepository.findAll(Sort.by(Sort.Direction.DESC, "tid"));
        auditService.saveAudit("getAllTokens","PASS:Get All Token :");
        return modelMapper.map(fuelTokenDTOList, new TypeToken<ArrayList<FuelTokenDTO>>() {
        }.getType());
    }

    private double fuelPriceCalculation(int requestQuota) {
        return requestQuota * BusinessLogicVarList.OneLiter;
    }

    public int sendMail(
            String to, String status, Date dateAndTime, String fuelStation) throws MessagingException, IOException {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("esoftassigmenets@gmail.com");
            message.setTo(to);
            switch (status) {
                case "ACCEPTED":
                    message.setSubject("Your Fuel Token ACCEPTED!");
                    String atext = "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "<!-- HTML Codes by Quackit.com -->" +
                            "<title>" +
                            "</title>" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                            "<style>" +
                            "body {background-color:#ffffff;background-repeat:no-repeat;background-position:top left;background-attachment:fixed;}\n" +
                            "h1{font-family:Arial, sans-serif;color:#000000;background-color:#ffffff;}\n" +
                            "p {font-family:Georgia, serif;font-size:14px;font-style:normal;font-weight:normal;color:#000000;background-color:#ffffff;}\n" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<h1></h1>" +
                            "<p>Hey " + to + ",</p>" +
                            "<p></p>" +
                            "<p>We are ACCEPTED Your Fuel Token Now!</p>" +
                            "<!-- CSS Code: Place this code in the document's head (between the 'head' tags) -->\n" +
                            "<style>" +
                            "table.GeneratedTable {" +
                            "  width: 100%;" +
                            "  background-color: #ffffff;" +
                            "  border-collapse: collapse;" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  color: #000000;" +
                            "}" +
                            "" +
                            "table.GeneratedTable td, table.GeneratedTable th {" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  padding: 3px;" +
                            "}" +
                            "" +
                            "table.GeneratedTable thead {" +
                            "  background-color: #ffcc00;" +
                            "}" +
                            "</style>" +
                            "" +
                            "<!-- HTML Code: Place this code in the document's body (between the 'body' tags) where the table should appear -->\n" +
                            "<table class=\"GeneratedTable\">" +
                            "  <thead>" +
                            "    <tr>" +
                            "      <th>Date And Time</th>" +
                            "      <th>Fuel Station</th>" +
                            "    </tr>" +
                            "  </thead>" +
                            "  <tbody>" +
                            "    <tr>" +
                            "      <td>"+dateAndTime+"</td>" +
                            "      <td>"+fuelStation+"</td>" +
                            "    </tr>" +
                            "  </tbody>" +
                            "</table>" +
                            "<p></p>" +
                            "<p>Thank You!</p>" +
                            "<p>Power Fuel(pvt) Ltd</p>" +
                            "</body>" +
                            "</html>";
                    message.setText(atext);
                    System.out.println("------------------------------>");
                    emailSender.send(message);
                    System.out.println("------------------------------>");
                    auditService.saveAudit("sendMail","PASS:ACCEPTED Mail Send");
                    return VarList.Created;

                case "DELIVERED":
                    message.setSubject("Your Fuel Quota DELIVERED!");
                    String dtext = "<!DOCTYPE html>" +
                            "<html>" +
                            "<head" +
                            "<!-- HTML Codes by Quackit.com -->" +
                            "<title>" +
                            "</title>" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                            "<style>" +
                            "body {background-color:#ffffff;background-repeat:no-repeat;background-position:top left;background-attachment:fixed;}\n" +
                            "h1{font-family:Arial, sans-serif;color:#000000;background-color:#ffffff;}\n" +
                            "p {font-family:Georgia, serif;font-size:14px;font-style:normal;font-weight:normal;color:#000000;background-color:#ffffff;}\n" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<h1></h1>" +
                            "<p>Hey " + to + ",</p>" +
                            "<p></p>" +
                            "<p>We are DELIVERED Your Fuel Quota To Your Fuel Station!</p>" +
                            "<!-- CSS Code: Place this code in the document's head (between the 'head' tags) -->\n" +
                            "<style>" +
                            "table.GeneratedTable {" +
                            "  width: 100%;" +
                            "  background-color: #ffffff;" +
                            "  border-collapse: collapse;" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  color: #000000;" +
                            "}" +
                            "" +
                            "table.GeneratedTable td, table.GeneratedTable th {" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  padding: 3px;" +
                            "}" +
                            "" +
                            "table.GeneratedTable thead {" +
                            "  background-color: #ffcc00;" +
                            "}" +
                            "</style>" +
                            "" +
                            "<!-- HTML Code: Place this code in the document's body (between the 'body' tags) where the table should appear -->\n" +
                            "<table class=\"GeneratedTable\">" +
                            "  <thead>" +
                            "    <tr>" +
                            "      <th>Date And Time</th>" +
                            "      <th>Fuel Station</th>" +
                            "    </tr>" +
                            "  </thead>" +
                            "  <tbody>" +
                            "    <tr>" +
                            "      <td>"+dateAndTime+"</td>" +
                            "      <td>"+fuelStation+"</td>" +
                            "    </tr>" +
                            "  </tbody>" +
                            "</table>" +
                            "<p></p>" +
                            "<p>Thank You!</p>" +
                            "<p>Power Fuel(pvt) Ltd</p>" +
                            "</body>" +
                            "</html>";
                    message.setText(dtext);
                    System.out.println("------------------------------>");
                    emailSender.send(message);
                    System.out.println("------------------------------>");
                    auditService.saveAudit("sendMail","PASS:DELIVERED Mail Send");
                    return VarList.Created;
                case "CANCELED":
                    message.setSubject("Your Fuel Token CANCELED!");
                    String ctext = "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "<!-- HTML Codes by Quackit.com -->" +
                            "<title>" +
                            "</title>" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                            "<style>" +
                            "body {background-color:#ffffff;background-repeat:no-repeat;background-position:top left;background-attachment:fixed;}" +
                            "h1{font-family:Arial, sans-serif;color:#000000;background-color:#ffffff;}" +
                            "p {font-family:Georgia, serif;font-size:14px;font-style:normal;font-weight:normal;color:#000000;background-color:#ffffff;}" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<h1></h1>" +
                            "<p>Hey " + to + ",</p>" +
                            "<p></p>" +
                            "<p>We are CANCELED Your Fuel Token Now!</p>" +
                            "<!-- CSS Code: Place this code in the document's head (between the 'head' tags) -->\n" +
                            "<style>" +
                            "table.GeneratedTable {" +
                            "  width: 100%;" +
                            "  background-color: #ffffff;" +
                            "  border-collapse: collapse;" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  color: #000000;" +
                            "}" +
                            "" +
                            "table.GeneratedTable td, table.GeneratedTable th {" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  padding: 3px;" +
                            "}" +
                            "" +
                            "table.GeneratedTable thead {" +
                            "  background-color: #ffcc00;" +
                            "}" +
                            "</style>" +
                            "" +
                            "<!-- HTML Code: Place this code in the document's body (between the 'body' tags) where the table should appear -->\n" +
                            "<table class=\"GeneratedTable\">" +
                            "  <thead>" +
                            "    <tr>" +
                            "      <th>Date And Time</th>" +
                            "      <th>Fuel Station</th>" +
                            "    </tr>" +
                            "  </thead>" +
                            "  <tbody>" +
                            "    <tr>" +
                            "      <td>"+dateAndTime+"</td>" +
                            "      <td>"+fuelStation+"</td>" +
                            "    </tr>" +
                            "  </tbody>" +
                            "</table>" +
                            "<p></p>" +
                            "<p>Thank You!</p>" +
                            "<p>Power Fuel(pvt) Ltd</p>" +
                            "</body>" +
                            "</html>";
                    message.setText(ctext);
                    System.out.println("------------------------------>");
                    emailSender.send(message);
                    System.out.println("------------------------------>");
                    auditService.saveAudit("sendMail","PASS:CANCELED Mail Send");
                    return VarList.Created;
            }
            return VarList.Conflict;
        } catch (Exception e) {
            System.out.println(e);
            auditService.saveAudit("sendMail","FAIL:Exception");
            return VarList.Conflict;
        }
    }

}
