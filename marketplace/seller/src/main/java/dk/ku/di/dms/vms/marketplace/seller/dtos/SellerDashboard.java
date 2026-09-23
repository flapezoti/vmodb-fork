package dk.ku.di.dms.vms.marketplace.seller.dtos;

import dk.ku.di.dms.vms.marketplace.seller.entities.OrderEntry;

import java.util.List;

public final class SellerDashboard {

    public final OrderSellerView view;
    public final List<OrderEntry> entries;

    public SellerDashboard(OrderSellerView view, List<OrderEntry> entries) {
        this.view = view;
        this.entries = entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if(entries != null && !entries.isEmpty()) {
            for (OrderEntry entry : entries) {
                sb.append(entry.toString()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]");
        return "{"
                + "\"view\":" + view
                + ",\"entries\":" + sb
                + "}";
    }
}
