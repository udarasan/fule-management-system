package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.repository.PaymentRepository;
import lk.esoft.fulemanagementsystem.service.PaymentService;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public int changePaymentStatus(int pid,String status){
        if (paymentRepository.existsById(pid)) {
            paymentRepository.changePaymentStatus(pid,status);
            return VarList.Accepted;
        } else {
            return VarList.Not_Found;
        }


    }

}
