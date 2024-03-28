package dev.mritunjay.paymentservice.service;

import com.razorpay.RazorpayException;
import dev.mritunjay.paymentservice.dto.PaymentResponse;

public interface PaymentService {

    String doPayment(String email , String phoneNumber , Long amount , String orderId) throws RazorpayException;

}
