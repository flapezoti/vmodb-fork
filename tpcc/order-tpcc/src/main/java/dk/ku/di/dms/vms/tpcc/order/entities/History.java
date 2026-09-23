package dk.ku.di.dms.vms.tpcc.order.entities;

import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@VmsTable(name="history")
public class History implements IEntity<Integer> {

    @Id
    @GeneratedValue
    public int id;

    @Column
    public int h_c_id;

    @Column
    public int h_c_d_id;

    @Column
    public int h_c_w_id;

    @Column
    public int h_d_id;

    @Column
    public int h_w_id;

    @Column
    public Date h_date;

    @Column
    public float h_amount;

    @Column
    public String h_data;

    public History(){}

    public History(int h_c_id, int h_c_d_id, int h_c_w_id, int h_d_id, int h_w_id, Date h_date, float h_amount, String h_data) {
        this.h_c_id = h_c_id;
        this.h_c_d_id = h_c_d_id;
        this.h_c_w_id = h_c_w_id;
        this.h_d_id = h_d_id;
        this.h_w_id = h_w_id;
        this.h_date = h_date;
        this.h_amount = h_amount;
        this.h_data = h_data;
    }

}
