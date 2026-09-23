package dk.ku.di.dms.vms.tpcc.warehouse.dto;

public final class CustomerInfoDTO {

    public float c_balance;

    public String c_first;

    public String c_middle;

    public String c_last;

    public CustomerInfoDTO(){}

    public CustomerInfoDTO(float c_balance, String c_first, String c_middle, String c_last) {
        this.c_balance = c_balance;
        this.c_first = c_first;
        this.c_middle = c_middle;
        this.c_last = c_last;
    }
}
