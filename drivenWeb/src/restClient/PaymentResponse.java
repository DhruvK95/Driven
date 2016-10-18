package restClient;

/**
 * Created by Dhruv on 12/10/2016. DrivenRest
 */
public class PaymentResponse {
    Payment payment;
    String Link;

    public PaymentResponse(Payment payment, String link) {
        this.payment = payment;
        Link = link;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
