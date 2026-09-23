package dk.ku.di.dms.vms.coordinator.batch;

import dk.ku.di.dms.vms.coordinator.transaction.EventIdentifier;
import dk.ku.di.dms.vms.coordinator.transaction.TransactionDAG;
import dk.ku.di.dms.vms.modb.common.schema.network.node.VmsNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Algorithms related to the formation of a batch of transactions
 */
public final class BatchAlgo {

    /**
     * A map of vms and corresponding precedent TID for a given tid
     * Build precedence for the downstream events of an (input) event
     */
    public static Map<String, Long> buildPrecedenceMap(EventIdentifier inputEvent, TransactionDAG transactionDAG, Map<String, VmsNode> vmsMetadata) {
        return buildPrecedenceRecursive(inputEvent, transactionDAG, vmsMetadata);
    }

    /**
     * API that considers complex scheduler (i.e., a VMS task can have two input events)
     */
    public static Map<String, Long> buildPrecedenceMap(TransactionDAG transactionDAG, Map<String, VmsNode> vmsMetadata) {
        List<Map<String, Long>> listOfMapPerInputEvent = new ArrayList<>(transactionDAG.inputEvents.size());
        for(EventIdentifier inputEvent : transactionDAG.inputEvents.values()) {
            listOfMapPerInputEvent.add( buildPrecedenceRecursive(inputEvent, transactionDAG, vmsMetadata) );
        }
        Map<String, Long> merged = new HashMap<>(listOfMapPerInputEvent.size());
        for(Map<String, Long> map : listOfMapPerInputEvent){
            merged.putAll( map );
        }
        return merged;
    }

    private static Map<String, Long> buildPrecedenceRecursive(EventIdentifier event,
                                                              TransactionDAG transactionDAG,
                                                              Map<String, VmsNode> vmsMetadata){
        Map<String, Long> listToBuildMap = new HashMap<>();

        // input and internal nodes first, since they have children
        if(transactionDAG.internalNodes.contains( event.targetVms ) || transactionDAG.inputEvents.get( event.getName() ) != null){
            listToBuildMap.put(event.targetVms, vmsMetadata.get(event.targetVms).getLastTid());
            for(EventIdentifier child : event.children){
                listToBuildMap.putAll(buildPrecedenceRecursive(child, transactionDAG, vmsMetadata));
            }
        } else if(transactionDAG.terminalNodes.contains( event.targetVms )){
                listToBuildMap.put(event.targetVms, vmsMetadata.get(event.targetVms).getLastTid());
        }

        return listToBuildMap;
    }

    @SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
    public static VmsNode[] buildTransactionDagVmsList(TransactionDAG transactionDAG, Map<String, VmsNode> vmsMetadata){
        List<VmsNode> transactionDagVmsList = new ArrayList<>();
        for(EventIdentifier event : transactionDAG.inputEvents.values()) {
            transactionDagVmsList.add( vmsMetadata.get(event.targetVms) );
            transactionDagVmsList.addAll( buildTransactionDagVmsListRecursive( event.children, vmsMetadata ) );
        }
        return transactionDagVmsList.toArray(new VmsNode[transactionDagVmsList.size()]);
    }

    private static List<VmsNode> buildTransactionDagVmsListRecursive(List<EventIdentifier> internalNodeList, Map<String, VmsNode> vmsMetadata) {
        List<VmsNode> transactionDagVmsList = new ArrayList<>();
        for(EventIdentifier event : internalNodeList) {
            transactionDagVmsList.add( vmsMetadata.get(event.targetVms) );
            if(!event.terminal){ //&& !event.children.isEmpty()){
                transactionDagVmsList.addAll( buildTransactionDagVmsListRecursive( event.children, vmsMetadata ) );
            }
        }
        return transactionDagVmsList;
    }
}
