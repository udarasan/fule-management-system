package lk.esoft.fulemanagementsystem.controller;
/**
 * @author Udara San
 * @TimeStamp 11:43 AM | 11/9/2022 | 2022
 * @ProjectDetails ecom-api
 */
import lk.esoft.fulemanagementsystem.dto.ResponseDTO;
import lk.esoft.fulemanagementsystem.dto.UserDTO;
import lk.esoft.fulemanagementsystem.service.impl.UserServiceImpl;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private UserServiceImpl userService;


    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
        try {
            int res = userService.saveUser(userDTO);
            if (res==201) {
                responseDTO.setCode(VarList.Created);
                responseDTO.setMessage("success");
                responseDTO.setData(userDTO);
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
        } catch (Exception e) {
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(null);
            System.out.println(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }/*
    @PostMapping(value = "/sendOTP")
    public ResponseEntity<ResponseDTO> sendOTP(@RequestParam String email) {
        System.out.println(email);
        try {
            String res = userService.sendOTPCode(email);
            if (res.equals("00")) {
                responseDTO.setCode(VarListUtil.RSP_SUCCESS);
                responseDTO.setMessage("success");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals("01")) {
                responseDTO.setCode(VarListUtil.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Username Already Use");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarListUtil.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarListUtil.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/getOTP")
    public ResponseEntity<ResponseDTO> getOTP(@RequestParam String email) {
        System.out.println(email);
        try {
            String otp = userService.getOTPCode(email);
            if (otp!=null) {
                responseDTO.setCode(VarListUtil.RSP_SUCCESS);
                responseDTO.setMessage("success");
                responseDTO.setContent(otp);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarListUtil.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarListUtil.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //change the user status
    @PostMapping("/delete/{username}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String username) {
        try {
            String res = userService.deleteUser(username);
            if (res.equals("00")) {
                responseDTO.setCode(VarListUtil.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals("01")) {
                responseDTO.setCode(VarListUtil.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Not available user");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            } else {
                responseDTO.setCode(VarListUtil.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarListUtil.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateUser")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserDTO userDTO) {
        try {
            String res = userService.updateUser(userDTO);
            if (res.equals("00")) {
                responseDTO.setCode(VarListUtil.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals("01")) {
                responseDTO.setCode(VarListUtil.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("You don't have account yet. please create new account");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarListUtil.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarListUtil.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/searchUser/{username}")
    public ResponseEntity<ResponseDTO> searchUser(@PathVariable String username) {
        try {
            UserDTO userDTO = userService.searchUser(username);
            if (userDTO !=null) {
                responseDTO.setCode(VarListUtil.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarListUtil.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No User Available For this name");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarListUtil.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

*/
    @GetMapping("/getAllUsers")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        System.out.println("username");
        try{

            //System.out.println(role);
            List<UserDTO> userDTOList = userService.getAllUsers();
            responseDTO.setCode(VarList.Created);
            responseDTO.setMessage("Success");
            responseDTO.setData(userDTOList);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}