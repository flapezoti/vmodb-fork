package dk.ku.di.dms.vms.marketplace.seller.dtos;

public final class OrderSellerView {

    public int seller_id;

    public double total_amount;

    // total_freight
    public double freight_value;

    public double total_incentive;

    public double total_invoice;
    public double total_items;

    public int count_orders;
    public int count_items;

    public OrderSellerView() { }

    @Override
    public String toString() {
        return "{"
                + "\"seller_id\":\"" + seller_id + "\""
                + ",\"total_amount\":\"" + total_amount + "\""
                + ",\"total_freight\":\"" + freight_value + "\""
                + ",\"total_incentive\":\"" + total_incentive + "\""
                + ",\"total_invoice\":\"" + total_invoice + "\""
                + ",\"total_items\":\"" + total_items + "\""
                + ",\"count_orders\":\"" + count_orders + "\""
                + ",\"count_items\":\"" + count_items + "\""
                + "}";
    }

}