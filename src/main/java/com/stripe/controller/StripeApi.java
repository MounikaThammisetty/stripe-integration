package com.stripe.controller;

import com.stripe.dto.PaymentRequest;
import com.stripe.dto.StripeTokenDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.service.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class StripeApi {

    private StripeService stripeService;

    //http://localhost:9095/payments/card/token
    @PostMapping("/card/token")
    public String createCardToken(@RequestBody StripeTokenDto model){
      return stripeService.createToken(model.getCardNumber(),model.getExpMonth(),model.getExpYear(),model.getCvc());
    }
    @PostMapping("/charge")
    public String processPayment(  PaymentRequest paymentRequest)  {
        PaymentIntentCreateParams createParams =
                PaymentIntentCreateParams.builder()
                        .setAmount(paymentRequest.getAmount()*100)
                        .setCurrency(paymentRequest.getCurrency())
                        .setPaymentMethod(paymentRequest.getTokenId())
                        .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                        .build();
        try {
            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            PaymentIntentConfirmParams confirmParams =
                    PaymentIntentConfirmParams.builder()
                            .setPaymentMethod(paymentRequest.getTokenId())
                            .build();

            paymentIntent.confirm(confirmParams);
            System.out.println("paymentIntent confirmed:" + paymentIntent);
            return "done";
        }catch (StripeException e){
            e.printStackTrace();
            return "error";
        }
    }
}
