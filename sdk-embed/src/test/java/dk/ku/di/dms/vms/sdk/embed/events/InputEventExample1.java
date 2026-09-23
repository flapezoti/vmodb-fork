package dk.ku.di.dms.vms.sdk.embed.events;

import dk.ku.di.dms.vms.modb.api.annotations.Event;

@Event
public class InputEventExample1 {

    public int id;

    public InputEventExample1(int id) {
        this.id = id;
    }

}