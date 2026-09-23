package dk.ku.di.dms.vms.tpcc.order.dto;

import java.util.Date;

public final class OrderLineInfoDto {

    public int ol_i_id;

    public int ol_supply_w_id;

    public Date ol_delivery_d;

    public int ol_quantity;

    public float ol_amount;

    public OrderLineInfoDto() {}

    public OrderLineInfoDto(int ol_i_id, int ol_supply_w_id, Date ol_delivery_d, int ol_quantity, float ol_amount) {
        this.ol_i_id = ol_i_id;
        this.ol_supply_w_id = ol_supply_w_id;
        this.ol_delivery_d = ol_delivery_d;
        this.ol_quantity = ol_quantity;
        this.ol_amount = ol_amount;
    }

    @Override
    public String toString() {
        return "{"
                + "\"ol_i_id\":\"" + ol_i_id + "\""
                + ",\"ol_supply_w_id\":\"" + ol_supply_w_id + "\""
                + ",\"ol_delivery_d\":" + ol_delivery_d
                + ",\"ol_quantity\":\"" + ol_quantity + "\""
                + ",\"ol_amount\":\"" + ol_amount + "\""
                + "}";
    }

}
