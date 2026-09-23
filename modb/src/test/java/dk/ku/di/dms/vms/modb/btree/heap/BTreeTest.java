package dk.ku.di.dms.vms.modb.btree.heap;

import org.junit.Test;

public class BTreeTest {

    @Test
    public void test() {

        ParentNode parentNode = new ParentNode();

        parentNode.insert(1,1);

        parentNode.insert(2,2);
        parentNode.insert(3,3);
        parentNode.insert(4,4);
        parentNode.insert(5,5);
        parentNode.insert(6,6);

        parentNode.insert(7,7);
        parentNode.insert(8,8);
        parentNode.insert(9,9);
        parentNode.insert(10,10);

    }

}
