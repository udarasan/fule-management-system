package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.PaymentDTO;
import lk.esoft.fulemanagementsystem.dto.UserDTO;
import lk.esoft.fulemanagementsystem.entity.Payment;
import lk.esoft.fulemanagementsystem.entity.User;
import lk.esoft.fulemanagementsystem.repository.PaymentRepository;
import lk.esoft.fulemanagementsystem.service.PaymentService;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private AuditServiceImpl auditService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public int changePaymentStatus(int pid,String status){
        if (paymentRepository.existsById(pid)) {
            paymentRepository.changePaymentStatus(pid,status);
            auditService.saveAudit("changePaymentStatus","PASS:Change Payment Status"+status);
            return VarList.Accepted;
        } else {
            auditService.saveAudit("changePaymentStatus","FAIL:Change Payment Status"+status);
            return VarList.Not_Found;

        }


    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        List<Payment> paymentList=paymentRepository.findAll();
        return modelMapper.map(paymentList, new TypeToken<ArrayList<PaymentDTO>>() {
        }.getType());
    }

}
