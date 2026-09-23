package dk.ku.di.dms.vms.tpcc.warehouse.entities;

import dk.ku.di.dms.vms.modb.api.annotations.VmsForeignKey;
import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@VmsTable(name="district")
@IdClass(District.DistrictId.class)
public class District implements IEntity<District.DistrictId> {

    public record DistrictId(int d_id, int d_w_id) implements Serializable { }

    @Id
    public int d_id;

    @Id
    @VmsForeignKey(table=Warehouse.class, column = "w_id")
    public int d_w_id;

    @Column
    public String d_name;

    @Column
    public String d_street_1;

    @Column
    public String d_street_2;

    @Column
    public String d_city;

    @Column
    public String d_state;

    @Column
    public String d_zip;

    @Column
    public float d_tax;

    @Column
    public float d_ytd;

    @Column
    public int d_next_o_id;

    public District(){}

    public District(int d_id, int d_w_id, String d_name, String d_street_1, String d_street_2, String d_city, String d_state, String d_zip, float d_tax, float d_ytd, int d_next_o_id) {
        this.d_id = d_id;
        this.d_w_id = d_w_id;
        this.d_name = d_name;
        this.d_street_1 = d_street_1;
        this.d_street_2 = d_street_2;
        this.d_city = d_city;
        this.d_state = d_state;
        this.d_zip = d_zip;
        this.d_tax = d_tax;
        this.d_ytd = d_ytd;
        this.d_next_o_id = d_next_o_id;
    }

    @Override
    public String toString() {
        return "{"
                + "\"d_id\":\"" + d_id + "\""
                + ",\"d_w_id\":\"" + d_w_id + "\""
                + ",\"d_name\":\"" + d_name + "\""
                + ",\"d_street_1\":\"" + d_street_1 + "\""
                + ",\"d_street_2\":\"" + d_street_2 + "\""
                + ",\"d_city\":\"" + d_city + "\""
                + ",\"d_state\":\"" + d_state + "\""
                + ",\"d_zip\":\"" + d_zip + "\""
                + ",\"d_tax\":\"" + d_tax + "\""
                + ",\"d_ytd\":\"" + d_ytd + "\""
                + ",\"d_next_o_id\":\"" + d_next_o_id + "\""
                + "}";
    }
}