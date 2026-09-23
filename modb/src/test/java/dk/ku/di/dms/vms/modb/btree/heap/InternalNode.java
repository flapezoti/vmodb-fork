package dk.ku.di.dms.vms.modb.btree.heap;

import java.util.ArrayList;
import java.util.List;

public class InternalNode implements INode {

    public int branchingFactor;
    
    public int size; // of keys

    public INode parent;
    
    public List<Integer> keys;

    public List<INode> children;

    public InternalNode() {
        this.branchingFactor = DEFAULT_BRANCHING_FACTOR;
        this.size = 0;
        this.keys = new ArrayList<>(branchingFactor - 1);
        this.children = new ArrayList<>(branchingFactor);
    }

    public InternalNode(INode parent, List<Integer> keys, List<INode> children) {
        this.parent = parent;
        this.keys = keys;
        this.children = children;
    }

    @Override
    public INode insert(int key, Object data){

        // find the node
        int i = 0;
        for(int key_ : this.keys){
            if(key_ > key)
                break;
            i++;
        }

        INode newNode = this.children.get(i).insert(key,data);

        if(newNode != null){

            this.children.add(i + 1, newNode );
            this.keys.add(i, this.children.get(i).lastKey());

            if(this.keys.size() == this.branchingFactor){

                // overflow
                return overflow();

            }

        }

        return null;

    }

    private INode overflow() {

        int half = (int) Math.ceil((double)this.branchingFactor - 1) / 2;

        List<Integer> keyLeft = cloneKeys(this.keys, 0, half);
        List<Integer> keyRight = cloneKeys(this.keys, half+1, branchingFactor - 1);

        List<INode> childrenLeft = new ArrayList<>( this.children.subList( 0, half-1 ) );
        List<INode> childrenRight = new ArrayList<>( this.children.subList( half, this.children.size() - 1) );

        this.keys = keyLeft;
        this.size = this.keys.size();
        this.children = childrenLeft;

        return new InternalNode( this.parent, keyRight, childrenRight );

    }

    @Override
    public int lastKey() {
        return this.keys.get(this.keys.size() - 1);
    }

}
