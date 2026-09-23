package dk.ku.di.dms.vms.modb.btree.heap;

import dk.ku.di.dms.vms.modb.common.data_structure.Tuple;

import java.util.ArrayList;
import java.util.List;

public class LeafNode implements INode {

    public int branchingFactor;

    // parent or internal node
    public INode parent;

    public List<Tuple<Integer,Object>> data;

    public LeafNode previous;

    public LeafNode next;

    public LeafNode(INode parent){
        this.branchingFactor = DEFAULT_BRANCHING_FACTOR;
        this.parent = parent;
        this.data = new ArrayList<>(branchingFactor);
    }

    public LeafNode(INode parent, List<Tuple<Integer,Object>> data,
                    LeafNode previous, LeafNode next) {
        this.parent = parent;
        this.data = data;
        this.previous = previous;
        this.next = next;
        this.branchingFactor = DEFAULT_BRANCHING_FACTOR;
    }

    @Override
    public INode insert(int key, Object value){

        int i = 0;
        for(var tuple : data){
            if(tuple.t1() > key) break;
            i++;
        }

        data.add(i,Tuple.of(key,value));

        if(data.size() == branchingFactor){
            return overflow();
        }

        return null;

    }

    @Override
    public int lastKey() {
        return this.data.get(this.data.size() - 1).t1();
    }

    public INode overflow(){
        int half = (int) Math.ceil((double)branchingFactor - 1) / 2;
        var newNodeData = clone(this.data, half+1, branchingFactor-1);
        LeafNode newNode = new LeafNode( this.parent, newNodeData, this, this.next );
        this.data = clone(this.data, 0, half);
        this.next = newNode;
        return newNode;
    }

}
