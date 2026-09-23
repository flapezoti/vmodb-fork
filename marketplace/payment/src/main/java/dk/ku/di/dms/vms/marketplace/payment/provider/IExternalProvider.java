package dk.ku.di.dms.vms.marketplace.payment.provider;

import dk.ku.di.dms.vms.marketplace.payment.integration.PaymentIntent;
import dk.ku.di.dms.vms.marketplace.payment.integration.PaymentIntentCreateOptions;

public interface IExternalProvider {
    PaymentIntent Create(PaymentIntentCreateOptions options);
}
