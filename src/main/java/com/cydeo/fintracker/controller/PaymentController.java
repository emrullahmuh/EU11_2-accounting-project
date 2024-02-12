package com.cydeo.fintracker.controller;

import com.cydeo.fintracker.dto.PaymentResultDto;
import com.cydeo.fintracker.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/list")
    public String listAllPayments(@RequestParam(name = "year", required = false) Integer year, Model model) {

        if (year == null) {
            year = Year.now().getValue();
        }
        model.addAttribute("year", year);
        model.addAttribute("payments", paymentService.listAllPaymentsByYear(year));

        return "payment/payment-list";

    }

    @GetMapping("/newpayment/{paymentid}")
    public String newPayment(@PathVariable("paymentid") long paymentId,Model model) {

        model.addAttribute("stripePublicKey", paymentService.findPaymentById(paymentId).getCompanyStripeId());
        model.addAttribute("amount", paymentService.findPaymentById(paymentId).getAmount());
        model.addAttribute("modelId", paymentId);
        model.addAttribute("currency", "USD");

        return "payment/payment-method";

    }


    @GetMapping("/toInvoice/{paymentid}")
    public String paymentInvoice(@PathVariable("paymentid") long paymentId,Model model) {

        model.addAttribute("company", paymentService.findPaymentById(paymentId).getCompany());
        model.addAttribute("payment", paymentService.findPaymentById(paymentId));
        model.addAttribute("modelId", paymentId);
        model.addAttribute("currency", "USD");
        return "payment/payment-invoice";

    }

    @PostMapping("/charge/{paymentid}")
    public String charge(@PathVariable("paymentid") long modelId,
                         @RequestParam("stripeToken") String stripeToken,
                         Model model) {

            PaymentResultDto paymentResult = paymentService.processPayment(modelId, stripeToken);

            model.addAttribute("id", paymentResult.getId());
            model.addAttribute("status", paymentResult.getStatus());
            model.addAttribute("chargeId", paymentResult.getChargeId());
            model.addAttribute("balance_transaction", paymentResult.getBalanceTransaction());
            model.addAttribute("month", paymentService.findPaymentById(modelId).getMonth());
            model.addAttribute("year", paymentService.findPaymentById(modelId).getYear());


            return "payment/payment-result";

    }


}
