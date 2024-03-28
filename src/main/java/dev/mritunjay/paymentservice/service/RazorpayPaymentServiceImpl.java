package dev.mritunjay.paymentservice.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import dev.mritunjay.paymentservice.dto.PaymentResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("razorpay")
public class RazorpayPaymentServiceImpl implements PaymentService{

    private RazorpayClient razorpayClient;
//    in-built class in RazorPay dependency

    public RazorpayPaymentServiceImpl(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String doPayment(String email, String phoneNumber, Long amount, String orderId) throws RazorpayException {
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("receipt", orderId);

            JSONObject customer = new JSONObject();
            customer.put("email", email);
            customer.put("phone number ", phoneNumber);
            paymentLinkRequest.put("customer ", customer);

//        notify is already a keyword in JsonObject class which is used to notify customers via sms/email as following
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);

            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "https:domain.com/razorpayWebHook");
            paymentLinkRequest.put("callback_method", "get");  // if its post then post call is made here

//        Now that i have a razorpayClient now so i will generate a payment request to RazorPay
//        PaymentLink is a class under RazorPay class , takes a JSON object as input
            PaymentLink response = razorpayClient.paymentLink.create(paymentLinkRequest);
            return response.toString();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
