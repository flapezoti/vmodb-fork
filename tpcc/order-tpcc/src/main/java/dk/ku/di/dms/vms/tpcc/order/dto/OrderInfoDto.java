package dk.ku.di.dms.vms.tpcc.order.dto;

import java.util.Date;

public class OrderInfoDto {

    public int o_id;

    public Date o_entry_d;

    public int o_carrier_id;

    public OrderInfoDto() {}

    public OrderInfoDto(int o_id, Date o_entry_d, int o_carrier_id) {
        this.o_id = o_id;
        this.o_entry_d = o_entry_d;
        this.o_carrier_id = o_carrier_id;
    }

    @Override
    public String toString() {
        return "{"
                + "\"o_id\":\"" + o_id + "\""
                + ",\"o_entry_d\":" + o_entry_d
                + ",\"o_carrier_id\":\"" + o_carrier_id + "\""
                + "}";
    }

}
