package lk.esoft.fulemanagementsystem.controller;

import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.dto.FuelTokenResponseDTO;
import lk.esoft.fulemanagementsystem.dto.ResponseDTO;
import lk.esoft.fulemanagementsystem.dto.UserDTO;
import lk.esoft.fulemanagementsystem.service.FuelTokenService;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Udara San
 * @TimeStamp 11:11 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@RestController
@RequestMapping("api/v1/fueltoken")
public class FuelTokenController {

    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private FuelTokenService fuelTokenService;

    @PostMapping(value = "/generate")
    public ResponseEntity<ResponseDTO> requestToken(@RequestBody FuelTokenDTO fuelTokenDTO) {
        try {
            //check if this Vehicle no has record
            if (fuelTokenService.vehicleRegNoExists(fuelTokenDTO.getVehicleRegNo())) {
                //check if user exceed the weekly limit(assume one vehicle can request 20L per week)
                int availableLitters = fuelTokenService.getAvailableBalance(fuelTokenDTO.getVehicleRegNo());
                if (availableLitters >= fuelTokenDTO.getRequestQuota()) {
                    //check fuel station fuel availability
                    int availableLittersInStation = fuelTokenService.getAvailableBalanceInStation(fuelTokenDTO.getFuelStationFk().getFid());
                    if (availableLittersInStation >= fuelTokenDTO.getRequestQuota()) {
                        //generata token
                        int res = fuelTokenService.generateToken(fuelTokenDTO);
                        if (res==201) {
                            responseDTO.setCode(VarList.Created);
                            responseDTO.setMessage("success");
                            responseDTO.setData(fuelTokenDTO);
                            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
                        } else if (res==404) {
                            responseDTO.setCode(VarList.Not_Found);
                            responseDTO.setMessage("Username Already Use");
                            responseDTO.setData(null);
                            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
                        }  else {
                            responseDTO.setCode(VarList.Internal_Server_Error);
                            responseDTO.setMessage("QR Generation Fail");
                            responseDTO.setData(null);
                            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        //check schedule delivery available or not

                        if (fuelTokenService.checkFuelRequestAvailability(fuelTokenDTO.getFuelStationFk().getFid())){

                            int res = fuelTokenService.generateToken(fuelTokenDTO);
                            if (res==201) {
                                responseDTO.setCode(VarList.Created);
                                responseDTO.setMessage("success");
                                responseDTO.setData(fuelTokenDTO);
                                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
                            } else if (res==404) {
                                responseDTO.setCode(VarList.Not_Found);
                                responseDTO.setMessage("Username Already Use");
                                responseDTO.setData(null);
                                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
                            }  else {
                                responseDTO.setCode(VarList.Internal_Server_Error);
                                responseDTO.setMessage("QR Generation Fail");
                                responseDTO.setData(null);
                                return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
                            }

                        }else {
                            responseDTO.setCode(VarList.Not_Acceptable);
                            responseDTO.setMessage("This Station fuel Almost Finished Now! No Any Schedule Delivery");
                            responseDTO.setData(null);
                            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_ACCEPTABLE);
                        }

                    }
                } else {
                    responseDTO.setCode(VarList.Not_Acceptable);
                    responseDTO.setMessage("Your Weekly Limit Exceed Or Requesting Quota Limit Greater than Available Fuel Quota Limit");
                    responseDTO.setData(null);
                    return new ResponseEntity<>(responseDTO, HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                int res  = fuelTokenService.generateTokenInFirstTime(fuelTokenDTO);

                if (res==201) {
                    responseDTO.setCode(VarList.Created);
                    responseDTO.setMessage("success");
                    responseDTO.setData(fuelTokenDTO);
                    return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
                } else if (res==404) {
                    responseDTO.setCode(VarList.Not_Found);
                    responseDTO.setMessage("Username Already Use");
                    responseDTO.setData(null);
                    return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
                } else {
                    responseDTO.setCode(VarList.Bad_Gateway);
                    responseDTO.setMessage("Error");
                    responseDTO.setData(null);
                    return new ResponseEntity<>(responseDTO, HttpStatus.BAD_GATEWAY);
                }
            }
        } catch (Exception e) {
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(null);
            System.out.println(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getAllTokenByUsername")
    public ResponseEntity<ResponseDTO> getAllTokenByUsername(@RequestAttribute String username) {

        try{

            //System.out.println(role);
            List<FuelTokenDTO> fuelTokenDTOS = fuelTokenService.getAllTokenByUsername(username);
            responseDTO.setCode(VarList.Created);
            responseDTO.setMessage("Success");
            responseDTO.setData(fuelTokenDTOS);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getAllQRandDetails")
    public ResponseEntity<ResponseDTO> getAllQRandDetails(@RequestAttribute String username) {

        try{

            //System.out.println(role);
            FuelTokenResponseDTO fuelTokenResponseDTO = fuelTokenService.getAllQRandDetails(username);
            responseDTO.setCode(VarList.Created);
            responseDTO.setMessage("Success");
            responseDTO.setData(fuelTokenResponseDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
