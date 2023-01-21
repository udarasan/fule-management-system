package lk.esoft.fulemanagementsystem.service;

import lk.esoft.fulemanagementsystem.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {
     int changePaymentStatus(int pid,String status);

    List<PaymentDTO> getAllPayments();
}
