package dk.ku.di.dms.vms.marketplace.common.inputs;

import dk.ku.di.dms.vms.modb.api.annotations.Event;

@Event
public final class UpdateDelivery {

    public String instanceId;

    public UpdateDelivery(){}

    public UpdateDelivery(String instanceId) {
        this.instanceId = instanceId;
    }

}
