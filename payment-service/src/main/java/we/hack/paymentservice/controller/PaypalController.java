package we.hack.paymentservice.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import we.hack.paymentservice.service.PaypalService;

/**
 * Controller for handling PayPal payments.
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class PaypalController {

    private final PaypalService paypalService;

    @Value("${url.cansel}")
    private String cancelURL;
    @Value("${url.success}")
    private String successURL;

    @PostMapping("/payment/create")
    public RedirectView createPayment(
            @RequestParam("amount") String amount
    ) {
        try {
            Payment payment = paypalService.createPayment(
                    Double.valueOf(amount),
                    "USD",
                    "PayPal",
                    "sale",
                    "parking pro",
                    cancelURL,
                    successURL
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error ocured: ", e);
        }

        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "payment-success";
            }
        } catch (PayPalRESTException e) {
            log.error("Error ocured: ", e);
            return "payment-error";
        }

        return "payment-success";
    }
}