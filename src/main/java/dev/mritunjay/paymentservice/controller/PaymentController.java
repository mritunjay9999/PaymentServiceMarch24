package dev.mritunjay.paymentservice.controller;

import com.razorpay.RazorpayException;
import dev.mritunjay.paymentservice.dto.InitiatePaymentRequestDto;
import dev.mritunjay.paymentservice.dto.PaymentResponse;
import dev.mritunjay.paymentservice.service.PaymentService;
import dev.mritunjay.paymentservice.service.strategy.PaymentGatewaySelectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

//    @Autowired -- field injection
    private PaymentService razorpayPaymentService;
    private PaymentService stripePaymentService;
    private PaymentGatewaySelectionStrategy paymentGatewaySelectionStrategy;

    public PaymentController(
            @Qualifier("razorpay") PaymentService razorpayPaymentService,
            @Qualifier("stripe") PaymentService stripePaymentService,
            PaymentGatewaySelectionStrategy paymentGatewaySelectionStrategy
            ) {
        this.razorpayPaymentService = razorpayPaymentService;
        this.stripePaymentService = stripePaymentService;
        this.paymentGatewaySelectionStrategy = paymentGatewaySelectionStrategy;
    }



    @PostMapping("/payment")
    public String initiatePayment(@RequestBody InitiatePaymentRequestDto requestDto) throws RazorpayException {

        int paymentGatewayOption = choosePaymentGateway();
        switch (paymentGatewayOption) {
            case 1:
                return razorpayPaymentService.doPayment(requestDto.getEmail(), requestDto.getPhoneNumber(), requestDto.getAmount(), requestDto.getOrderId());
            case 2:
                return stripePaymentService.doPayment(requestDto.getEmail(), requestDto.getPhoneNumber(), requestDto.getAmount(), requestDto.getOrderId());
        }
        return null;
    }

private int choosePaymentGateway(){
//        add your logic to choose payment gateway
    return paymentGatewaySelectionStrategy.paymentGatewaySelection(); // returns 0 ,hence switch statement not executed!
//    return 1;
    }
}

// @Autowired -- setter injection
//    public void setPaymentService(PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }
