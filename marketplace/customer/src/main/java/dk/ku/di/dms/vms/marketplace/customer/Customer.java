package dk.ku.di.dms.vms.marketplace.customer;

import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@VmsTable(name="customers")
public final class Customer implements IEntity<Integer> {

    @Id
    public int id;

    @Column
    public String first_name;

    @Column
    public String last_name;

    @Column
    public String address;

    @Column
    public String complement;

    @Column
    public String birth_date;

    @Column
    public String zip_code;

    @Column
    public String city;

    @Column
    public String state;

    @Column
    public String card_number;

    @Column
    public String card_security_number;

    @Column
    public String card_expiration;

    @Column
    public String card_holder_name;

    @Column
    public String card_type;

    @Column
    public int success_payment_count;

    @Column
    public int failed_payment_count;

    @Column
    public int delivery_count;

    @Column
    public String data;

    @Column
    public Date created_at;

    @Column
    public Date updated_at;

    public Customer() {
        this.created_at = new Date();
    }

    public Customer(int id, String first_name, String last_name, String address, String complement, String birth_date, String zip_code, String city, String state, String card_number,
                    String card_security_number, String card_expiration, String card_holder_name, String card_type, int success_payment_count, int failed_payment_count, int delivery_count, String data) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.complement = complement;
        this.birth_date = birth_date;
        this.zip_code = zip_code;
        this.city = city;
        this.state = state;
        this.card_number = card_number;
        this.card_security_number = card_security_number;
        this.card_expiration = card_expiration;
        this.card_holder_name = card_holder_name;
        this.card_type = card_type;
        this.success_payment_count = success_payment_count;
        this.failed_payment_count = failed_payment_count;
        this.delivery_count = delivery_count;
        this.data = data;
        this.created_at = new Date();
        this.updated_at = this.created_at;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\""
                + ", \"first_name\":\"" + first_name + "\""
                + ", \"last_name\":\"" + last_name + "\""
                + ", \"address\":\"" + address + "\""
                + ", \"complement\":\"" + complement + "\""
                + ", \"birth_date\":\"" + birth_date + "\""
                + ", \"zip_code\":\"" + zip_code + "\""
                + ", \"city\":\"" + city + "\""
                + ", \"state\":\"" + state + "\""
                + ", \"card_number\":\"" + card_number + "\""
                + ", \"card_security_number\":\"" + card_security_number + "\""
                + ", \"card_expiration\":\"" + card_expiration + "\""
                + ", \"card_holder_name\":\"" + card_holder_name + "\""
                + ", \"card_type\":\"" + card_type + "\""
                + ", \"success_payment_count\":\"" + success_payment_count + "\""
                + ", \"failed_payment_count\":\"" + failed_payment_count + "\""
                + ", \"delivery_count\":\"" + delivery_count + "\""
                + ", \"data\":\"" + data + "\""
                + "}";
    }
}
