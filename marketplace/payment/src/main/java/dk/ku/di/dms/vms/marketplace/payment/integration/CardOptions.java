package dk.ku.di.dms.vms.marketplace.payment.integration;

public class CardOptions
{
    public String Number;
    public String ExpMonth;
    public String ExpYear;
    public String Cvc;

    public CardOptions(String number, String expMonth, String expYear, String cvc) {
        Number = number;
        ExpMonth = expMonth;
        ExpYear = expYear;
        Cvc = cvc;
    }
}