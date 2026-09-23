package dk.ku.di.dms.vms.coordinator.transaction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Immutable representation of a transaction DAG
 * Basically the workflow as defined by the user
 */
public final class TransactionDAG {

    public final String name; // transaction name, e.g., new-order

    // the topology. input events have children and so on, until a terminal node is reached
    public final Map<String, EventIdentifier> inputEvents;
    public final List<String> terminalNodes;
    public final Set<String> internalNodes;

    public TransactionDAG(String name, List<EventIdentifier> inputEvents, Set<String> internalNodes, List<String> terminalNodes) {
        this.name = name;
        this.inputEvents = inputEvents.stream().collect( Collectors.toMap( EventIdentifier::getName, Function.identity() ) );
        this.internalNodes = Set.copyOf(internalNodes);
        this.terminalNodes = List.copyOf(terminalNodes);
    }

}