package lk.esoft.fulemanagementsystem.repository;
/**
 * @author Udara San
 * @TimeStamp 11:43 AM | 11/9/2022 | 2022
 * @ProjectDetails ecom-api
 */
import lk.esoft.fulemanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String userName);

    boolean existsByUsername(String userName);

    int deleteByUsername(String userName);

    @Modifying
    @Query(value = "UPDATE systemuser SET address = ?1, email = ?2, id_photo = ?3, name = ?4, password = ?5, phone_no1 = ?6, phone_no2 = ?7, remarks = ?8, role_code = ?9, status = ?10 WHERE username = ?11",nativeQuery = true)
    void updateUser(String address,String email,String id_photo,String name,
                    String password,String phone_no1,String phone_no2,
                    String remarks,String role_code,String status,String username);
}
