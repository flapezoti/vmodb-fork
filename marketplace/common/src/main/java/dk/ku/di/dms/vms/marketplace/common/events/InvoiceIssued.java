package dk.ku.di.dms.vms.marketplace.common.events;

import dk.ku.di.dms.vms.marketplace.common.entities.OrderItem;
import dk.ku.di.dms.vms.marketplace.common.inputs.CustomerCheckout;
import dk.ku.di.dms.vms.modb.api.annotations.Event;

import java.util.Date;
import java.util.List;

@Event
public final class InvoiceIssued {

    public CustomerCheckout customer;

    public int orderId;

    public String invoiceNumber;

    public Date issueDate;

    public float totalInvoice;

    public List<OrderItem> items;

    public String instanceId;

    public InvoiceIssued() { }

    public InvoiceIssued(CustomerCheckout customer, int orderId, String invoiceNumber, Date issueDate,
                         float totalInvoice, List<OrderItem> items, String instanceId) {
        this.customer = customer;
        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issueDate;
        this.totalInvoice = totalInvoice;
        this.items = items;
        this.instanceId = instanceId;
    }

}
