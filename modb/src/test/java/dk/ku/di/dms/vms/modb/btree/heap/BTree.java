package dk.ku.di.dms.vms.modb.btree.heap;

public class BTree {

    private ParentNode parent;

    public INode insert(int key, Object data) {
        INode newParent = parent.insert(key, data);
        if(newParent == null) return null;
        parent = (ParentNode) newParent;
        return parent;
    }
}
