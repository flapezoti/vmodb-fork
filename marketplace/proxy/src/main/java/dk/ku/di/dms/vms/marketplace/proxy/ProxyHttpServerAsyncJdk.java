package dk.ku.di.dms.vms.marketplace.proxy;

import dk.ku.di.dms.vms.coordinator.Coordinator;
import dk.ku.di.dms.vms.coordinator.infra.AbstractHttpHandler;
import dk.ku.di.dms.vms.coordinator.transaction.TransactionInput;
import dk.ku.di.dms.vms.web_common.IHttpHandler;

import static dk.ku.di.dms.vms.marketplace.common.Constants.*;

public final class ProxyHttpServerAsyncJdk extends AbstractHttpHandler implements IHttpHandler {

    public ProxyHttpServerAsyncJdk(Coordinator coordinator) {
        super(coordinator);
    }

    public void post(String uri, String body) {
        this.submitCustomerCheckout(body);
    }

    public void patch(String uri, String body) {
        this.submitUpdatePrice(body);
    }

    public void put(String uri, String body) {
        this.submitUpdateProduct(body);
    }

    private void submitCustomerCheckout(String payload) {
        TransactionInput.Event eventPayload = new TransactionInput.Event(CUSTOMER_CHECKOUT, payload);
        TransactionInput txInput = new TransactionInput(CUSTOMER_CHECKOUT, eventPayload);
        this.coordinator.queueTransactionInput(txInput);
    }

    private void submitUpdatePrice(String payload) {
        TransactionInput.Event eventPayload = new TransactionInput.Event(UPDATE_PRICE, payload);
        TransactionInput txInput = new TransactionInput(UPDATE_PRICE, eventPayload);
        this.coordinator.queueTransactionInput(txInput);
    }

    private void submitUpdateProduct(String payload) {
        TransactionInput.Event eventPayload = new TransactionInput.Event(UPDATE_PRODUCT, payload);
        TransactionInput txInput = new TransactionInput(UPDATE_PRODUCT, eventPayload);
        this.coordinator.queueTransactionInput(txInput);
    }

    private void submitUpdateDelivery(String payload) {
        TransactionInput.Event eventPayload = new TransactionInput.Event(UPDATE_DELIVERY, payload);
        TransactionInput txInput = new TransactionInput(UPDATE_DELIVERY, eventPayload);
        this.coordinator.queueTransactionInput(txInput);
    }

}
