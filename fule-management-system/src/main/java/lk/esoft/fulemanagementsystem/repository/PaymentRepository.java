package lk.esoft.fulemanagementsystem.repository;

import lk.esoft.fulemanagementsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Modifying
    @Query(value = "UPDATE Payment SET payment_status = ?2 WHERE pid = ?1",nativeQuery = true)
    void changePaymentStatus(int pid,String status);


}
