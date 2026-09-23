package dk.ku.di.dms.vms.modb.btree.heap;

import dk.ku.di.dms.vms.modb.common.data_structure.Tuple;

import java.util.ArrayList;
import java.util.List;

public interface INode {

    int DEFAULT_BRANCHING_FACTOR = 3;

    INode insert(int key, Object data);

    default List<Tuple<Integer,Object>> clone(List<Tuple<Integer,Object>> list){
        List<Tuple<Integer,Object>> tmp = new ArrayList<>();
        int i = 0;
        for (var val : list) {
            tmp.add(i,  val);
            i++;
        }
        return tmp;
    }

    default List<Integer> cloneKeys(List<Integer> list, int from, int to){
        List<Integer> tmp = new ArrayList<>(list.size());
        int idx = 0;
        for (int i = from; i <= to; i++) {
            tmp.add(idx,  list.get(i));
            idx++;
        }
        return tmp;
    }

    default List<Tuple<Integer,Object>> clone(List<Tuple<Integer,Object>> list, int from, int to){
        List<Tuple<Integer,Object>> tmp = new ArrayList<>(list.size());
        int idx = 0;
        for (int i = from; i <= to; i++) {
            tmp.add(idx,  list.get(i));
            idx++;
        }
        return tmp;
    }

    int lastKey();

//    default void overflow() {
//        throw new IllegalStateException("No support.");
//    }
//
//    default void overflow(INode splitted, ) {
//        throw new IllegalStateException("No support.");
//    }

}
