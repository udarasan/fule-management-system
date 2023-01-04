package lk.esoft.fulemanagementsystem.controller;

import lk.esoft.fulemanagementsystem.dto.FuelStationDTO;
import lk.esoft.fulemanagementsystem.dto.MainFuelStockDTO;
import lk.esoft.fulemanagementsystem.dto.ResponseDTO;
import lk.esoft.fulemanagementsystem.service.impl.MainFuelStockServiceImpl;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/mainstock")
public class MainStockController {

    @Autowired
    private MainFuelStockServiceImpl mainFuelStockService;

    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping(value = "/addStock")
    public ResponseEntity<ResponseDTO> addStock(@RequestBody MainFuelStockDTO mainFuelStockDTO) {
        try {
            int res = mainFuelStockService.saveMainFuelStock(mainFuelStockDTO);
            if (res == 201) {
                responseDTO.setCode(VarList.Created);
                responseDTO.setMessage("success");
                responseDTO.setData(mainFuelStockDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            } else if (res == 404) {
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
        } catch (Exception e) {
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(null);
            System.out.println(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getMainStockDetails")
    public ResponseEntity<ResponseDTO> getMainStockDetails() {

        try{
            List<MainFuelStockDTO> allFuelStationDetails = mainFuelStockService.getMainStockDetails();
            responseDTO.setCode(VarList.Created);
            responseDTO.setMessage("Success");
            responseDTO.setData(allFuelStationDetails);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
