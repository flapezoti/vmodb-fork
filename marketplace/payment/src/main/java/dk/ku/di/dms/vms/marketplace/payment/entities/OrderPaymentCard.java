package dk.ku.di.dms.vms.marketplace.payment.entities;

import dk.ku.di.dms.vms.modb.api.annotations.VmsForeignKey;
import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@VmsTable(name="order_payment_cards")
public final class OrderPaymentCard implements IEntity<OrderPaymentCard.OrderPaymentCardId> {

    public static class OrderPaymentCardId implements Serializable {
        public int customer_id;
        public int order_id;
        public int sequential;
        public OrderPaymentCardId(){}
        public OrderPaymentCardId(int customer_id, int order_id, int sequential) {
            this.customer_id = customer_id;
            this.order_id = order_id;
            this.sequential = sequential;
        }
    }

    @Id
    @VmsForeignKey(table = OrderPayment.class, column = "customer_id")
    public int customer_id;

    @Id
    @VmsForeignKey(table = OrderPayment.class, column = "order_id")
    public int order_id;

    @Id
    @VmsForeignKey(table = OrderPayment.class, column = "sequential")
    public int sequential;

    @Column
    public String card_number;

    @Column
    public String card_holder_name;

    @Column
    public String card_expiration;

    // public String card_security_number;

    @Column
    public String card_brand;

    @Column
    public Date created_at;

    public OrderPaymentCard() { }

    public OrderPaymentCard(int customer_id, int order_id, int sequential, String card_number, String card_holder_name, String card_expiration, String card_brand) {
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.sequential = sequential;
        this.card_number = card_number;
        this.card_holder_name = card_holder_name;
        this.card_expiration = card_expiration;
        this.card_brand = card_brand;
        this.created_at = new Date();
    }
}
