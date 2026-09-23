package dk.ku.di.dms.vms.modb.btree.heap;

import java.util.ArrayList;
import java.util.List;

public class ParentNode implements INode {

    public int branchingFactor;

    public int size; // of keys

    public List<Integer> keys;

    public List<INode> children;

    public ParentNode() {
        this.branchingFactor = DEFAULT_BRANCHING_FACTOR;
        this.keys = new ArrayList<>(branchingFactor-1);
        this.children = new ArrayList<>(branchingFactor);
        this.children.add( new LeafNode(this) );
    }

    /**
     * Return new parent
     * @param key
     * @param data
     * @return
     */
    @Override
    public INode insert(int key, Object data){

        // find the node
        int i = 0;
        for(int key_ : keys){
            if(key_ > key)
                break;
            i++;
        }

        INode newNode = this.children.get(i).insert(key,data);

        if(newNode != null){

            this.children.add(i + 1,  newNode );
            this.keys.add(i, this.children.get(i).lastKey()); // maybe should get the last key of the splitted node...

            if(this.keys.size() == branchingFactor){

                // overflow
                return overflow();

            }

        }

        return null;

    }

    @Override
    public int lastKey() {
        return this.keys.get(0);
    }

    private ParentNode overflow() {

        int half = (int) Math.ceil((double)branchingFactor - 1) / 2;

        List<Integer> keyLeft = cloneKeys(this.keys, 0, half);
        List<Integer> keyRight = cloneKeys(this.keys, half+1, branchingFactor - 1);

        List<INode> childrenLeft = new ArrayList<>( this.children.subList( 0, half+1 ) );
        List<INode> childrenRight = new ArrayList<>( this.children.subList( half+1, this.children.size() ) );

        InternalNode newLeft = new InternalNode(this, keyLeft, childrenLeft);
        InternalNode newRight = new InternalNode(this, keyRight, childrenRight);

        this.keys.clear();
        this.keys.add( newLeft.lastKey() );
        this.children.clear();
        this.children.add( newLeft );
        this.children.add( newRight );

        this.size = keys.size();

        return this;

    }


}
