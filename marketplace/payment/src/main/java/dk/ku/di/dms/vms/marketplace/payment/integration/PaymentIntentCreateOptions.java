package dk.ku.di.dms.vms.marketplace.payment.integration;

public class PaymentIntentCreateOptions {

    public String Customer;
    public float Amount;
    public String PaymentMethod;
    public String IdempotencyKey;
    public CardOptions cardOptions;
    public String SetupFutureUsage;
    public String Currency;

    public PaymentIntentCreateOptions(String customer, float amount, String paymentMethod,
                                      String idempotencyKey, CardOptions cardOptions,
                                      String setupFutureUsage, String currency) {
        Customer = customer;
        Amount = amount;
        PaymentMethod = paymentMethod;
        IdempotencyKey = idempotencyKey;
        this.cardOptions = cardOptions;
        SetupFutureUsage = setupFutureUsage;
        Currency = currency;
    }

}
