package dk.ku.di.dms.vms.marketplace.common.events;

import dk.ku.di.dms.vms.modb.api.annotations.Event;

@Event
public final class TransactionMark {

    private String tid;

    private String type;

    private int actorId;

    private String status;

    private String source;

    public TransactionMark(){}

    public TransactionMark(String tid,
                            TransactionType type,
                            int actorId,
                            MarkStatus status,
                            String source) {
        this.tid = tid;
        this.type = type.name();
        this.actorId = actorId;
        this.status = status.name();
        this.source = source;
    }

    public String getTid() {
        return tid;
    }

    public String getType() {
        return type;
    }

    public int getActorId() {
        return actorId;
    }

    public String getStatus() {
        return status;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "{" +
                " \"tid\" : \"" + tid + "\"" +
                ", \"type\" : \"" + type + "\"" +
                ", \"actorId\" : " + actorId +
                ", \"status\" : \"" + status + "\"" +
                ", \"source\" : \"" + source + "\"" +
                '}';
    }

    public enum TransactionType
    {
        CUSTOMER_SESSION,
        QUERY_DASHBOARD,
        PRICE_UPDATE,
        UPDATE_PRODUCT,
        UPDATE_DELIVERY
    }

    public enum MarkStatus
    {
        SUCCESS,
        ERROR,
        ABORT,
        NOT_ACCEPTED
    }

}