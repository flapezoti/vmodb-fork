package dk.ku.di.dms.vms.sdk.core.example;

import dk.ku.di.dms.vms.modb.api.annotations.Event;

@Event
public final class InputEventExample2 {

    public int id1;
    public int id2;

    public InputEventExample2(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    public Id getId() {
        return new Id(id1, id2);
    }

    public record Id(int id1, int id2){}


}