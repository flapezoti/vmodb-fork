package dk.ku.di.dms.vms.marketplace.seller.entities;

import dk.ku.di.dms.vms.modb.api.annotations.VmsTable;
import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Id;

@VmsTable(name = "sellers")
public final class Seller implements IEntity<Integer> {

    @Id
    public int id;

    @Column
    public String name;

    @Column
    public String company_name;

    @Column
    public String email;

    @Column
    public String phone;

    @Column
    public String mobile_phone;

    @Column
    public String cpf;

    @Column
    public String cnpj;

    @Column
    public String address;

    @Column
    public String complement;

    @Column
    public String city;

    @Column
    public String state;

    @Column
    public String zip_code;

    public Seller(){}

    public Seller(int id, String name, String company_name, String email, String phone, String mobile_phone, String cpf, String cnpj, String address, String complement, String city, String state, String zip_code) {
        this.id = id;
        this.name = name;
        this.company_name = company_name;
        this.email = email;
        this.phone = phone;
        this.mobile_phone = mobile_phone;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.address = address;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"company_name\":\"" + company_name + "\""
                + ", \"email\":\"" + email + "\""
                + ", \"phone\":\"" + phone + "\""
                + ", \"mobile_phone\":\"" + mobile_phone + "\""
                + ", \"cpf\":\"" + cpf + "\""
                + ", \"cnpj\":\"" + cnpj + "\""
                + ", \"address\":\"" + address + "\""
                + ", \"complement\":\"" + complement + "\""
                + ", \"city\":\"" + city + "\""
                + ", \"state\":\"" + state + "\""
                + ", \"zip_code\":\"" + zip_code + "\""
                + "}";
    }

}
