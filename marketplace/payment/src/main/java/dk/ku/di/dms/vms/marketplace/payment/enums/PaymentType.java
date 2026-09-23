package dk.ku.di.dms.vms.marketplace.payment.enums;

public enum PaymentType {

    CREDIT_CARD("CREDIT_CARD"),
    BOLETO("BOLETO"),
    VOUCHER("VOUCHER"),
    DEBIT_CARD("DEBIT_CARD");

    private final String type;

    PaymentType(String type) {
        this.type = type;
    }

    public boolean equals(String type){
        return this.type.contentEquals(type);
    }

}
