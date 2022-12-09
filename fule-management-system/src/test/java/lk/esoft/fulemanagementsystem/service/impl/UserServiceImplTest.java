package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.UserDTO;
import lk.esoft.fulemanagementsystem.dto.UserRoleDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    private UserServiceImpl userService;

    @Test
    void loadUserByUsername() {
        assertNotNull(userService.loadUserByUsername("udarasan"));
    }

    @Test
    void loadUserDetailsByUsername() {
        assertNotNull(userService.loadUserDetailsByUsername("udarasan"));
    }

    @Test
    void saveUser() {
        UserDTO userDTO=new UserDTO();

        userDTO.setUsername("udaras");
        userDTO.setEmail("udarassanjeewa@gmail.com");
        userDTO.setIdPhoto("test.jpg");
        UserRoleDTO userRoleDTO=new UserRoleDTO();
        userRoleDTO.setUserRoleCode(1);
        userRoleDTO.setUserRoleDesc("Admin");
        userDTO.setRoleCode(userRoleDTO);
        userDTO.setName("Udara San");
        userDTO.setPassword("12345");
        userDTO.setPhoneNo("123456789");
        userDTO.setStatus("TEST");

        userService.saveUser(userDTO);

        assertEquals(userDTO,userService.loadUserDetailsByUsername("udaras"));
    }


    @Test
    void getAllUsers() {
        assertNotNull(userService.getAllUsers());

    }

    @Test
    void searchUser() {
        UserDTO userDTO=userService.searchUser("udaras");

        UserDTO userDTONew=new UserDTO();

        userDTONew.setUsername("udaras");
        userDTONew.setEmail("udarassanjeewa@gmail.com");
        userDTONew.setIdPhoto("test.jpg");
        UserRoleDTO userRoleDTO=new UserRoleDTO();
        userRoleDTO.setUserRoleCode(1);
        userRoleDTO.setUserRoleDesc("Admin");
        userDTONew.setRoleCode(userRoleDTO);
        userDTONew.setName("Udara San");
        userDTONew.setPassword("12345");
        userDTONew.setPhoneNo("123456789");
        userDTONew.setStatus("TEST");

        assertEquals(userDTO,userDTONew);

    }
}