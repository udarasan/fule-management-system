package lk.esoft.fulemanagementsystem.controller;

import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.dto.ResponseDTO;
import lk.esoft.fulemanagementsystem.service.FuelTokenService;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            int res = fuelTokenService.generateToken(fuelTokenDTO);
            if (res==201) {
                responseDTO.setCode(VarList.Created);
                responseDTO.setMessage("success");
                responseDTO.setData(fuelTokenDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            } else if (res==404) {
                responseDTO.setCode(VarList.Not_Found);
                responseDTO.setMessage("TokenID Already Use");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.Bad_Gateway);
                responseDTO.setMessage("Error");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(null);
            System.out.println(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
