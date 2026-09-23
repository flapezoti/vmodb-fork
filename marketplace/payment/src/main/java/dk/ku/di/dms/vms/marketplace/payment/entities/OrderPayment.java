package dk.ku.di.dms.vms.marketplace.payment.entities;

import dk.ku.di.dms.vms.marketplace.payment.enums.PaymentStatus;
import dk.ku.di.dms.vms.marketplace.payment.enums.PaymentType;
import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@VmsTable(name="order_payments")
public final class OrderPayment implements IEntity<OrderPayment.OrderPaymentId> {

    public static class OrderPaymentId implements Serializable {
        public int customer_id;
        public int order_id;
        public int sequential;
        public OrderPaymentId(){}
        public OrderPaymentId(int customer_id, int order_id, int sequential) {
            this.customer_id = customer_id;
            this.order_id = order_id;
            this.sequential = sequential;
        }
    }

    @Id
    public int customer_id;

    @Id
    public int order_id;

    @Id
    public int sequential;

    @Column
    public PaymentType type;

    @Column
    public int installments;

    @Column
    public float value;

    @Column
    public PaymentStatus status;

    @Column
    public Date created_at;

    public OrderPayment() { }

    public OrderPayment(int customer_id, int order_id, int sequential, PaymentType type, int installments, float value, PaymentStatus status) {
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.sequential = sequential;
        this.type = type;
        this.installments = installments;
        this.value = value;
        this.status = status;
        this.created_at = new Date();
    }

}
