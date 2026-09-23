package dk.ku.di.dms.vms.coordinator.transaction;

import java.util.ArrayList;
import java.util.List;

public final class EventIdentifier {

    public final String alias; // alias for fast identification in topology definition

    public final String targetVms; // virtual microservice

    public final boolean terminal; // terminal node

    /** Attributes used only if it is not terminal */
    public final String name; // event name only if it is not terminal

    public List<EventIdentifier> children;

    public EventIdentifier(String alias, String targetVms, String name) {
        this.alias = alias;
        this.targetVms = targetVms;
        this.name = name;
        this.terminal = false;
        this.children = new ArrayList<>();
    }

    public EventIdentifier(String alias, String targetVms) {
        this.alias = alias;
        this.targetVms = targetVms;
        this.name = null;
        this.terminal = true;
        this.children = List.of();
    }

    public void addChildren(EventIdentifier event){
        if (this.terminal) {
            throw new RuntimeException("Cannot add children to terminal event");
        }
        this.children.add(event);
    }

    // for the stream
    public String getName(){
        return this.name;
    }

}
