package dk.ku.di.dms.vms.marketplace.payment.integration;

import dk.ku.di.dms.vms.marketplace.payment.enums.PaymentStatus;

public class PaymentIntent {

    public String id;

    public float amount;

    public String status = PaymentStatus.succeeded.toString();

    public String client_secret;

    public String currency;

    public String customer;

    public String confirmation_method;

    public int created;

    public PaymentIntent() {
    }

    public PaymentIntent(String id, float amount, String status, String client_secret, String currency, String customer, String confirmation_method, int created) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.client_secret = client_secret;
        this.currency = currency;
        this.customer = customer;
        this.confirmation_method = confirmation_method;
        this.created = created;
    }

}
