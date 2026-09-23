package dk.ku.di.dms.vms.modb.query.planner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Combinatorics {

    private Combinatorics(){}

    /**
     * The methods assume the original filter columns has been tested already
     * So it is excluded from a possible ocmbination
     * @param filterColumns
     * @return
     */

    private static List<int[]> getCombinationsFor2SizeColumnList(int[] filterColumns ){
        int[] arr0 = { filterColumns[0] };
        int[] arr1 = { filterColumns[1] };
        return Arrays.asList( arr0, arr1 );
    }

    private static List<int[]> getCombinationsFor3SizeColumnList( int[] filterColumns ){
        int[] arr0 = { filterColumns[0] };
        int[] arr1 = { filterColumns[1] };
        int[] arr2 = { filterColumns[2] };
        int[] arr4 = { filterColumns[0], filterColumns[1] };
        int[] arr5 = { filterColumns[0], filterColumns[2] };
        int[] arr6 = { filterColumns[1], filterColumns[2] };
        return Arrays.asList( arr0, arr1, arr2, arr4, arr5, arr6 );
    }

    private static List<int[]> getCombinationsFor4SizeColumnList( int[] filterColumns ){
        int[] arr0 = { filterColumns[0] };
        int[] arr1 = { filterColumns[1] };
        int[] arr2 = { filterColumns[2] };
        int[] arr3 = { filterColumns[3] };

        int[] arr4 = { filterColumns[0], filterColumns[1] };
        int[] arr5 = { filterColumns[0], filterColumns[2] };
        int[] arr6 = { filterColumns[0], filterColumns[3] };

        int[] arr7 = { filterColumns[1], filterColumns[2] };
        int[] arr8 = { filterColumns[1], filterColumns[3] };

        int[] arr9 = { filterColumns[2], filterColumns[3] };

        return Arrays.asList( arr0, arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 );
    }

    // TODO later get metadata to know whether such a column has an index, so it can be pruned from this search
    // TODO a column can appear more than once. make it sure it appears only once
    public static List<int[]> getAllPossibleColumnCombinations( int[] filterColumns ){

        // in case only one condition for join and single filter
        // if(filterColumns.length == 1) return Collections.singletonList(filterColumns);

        if(filterColumns.length == 2) return getCombinationsFor2SizeColumnList(filterColumns);

        if(filterColumns.length == 3) return getCombinationsFor3SizeColumnList(filterColumns);

        final int length = filterColumns.length;

        // not exact number, an approximation!
        int totalComb = (int) Math.pow( length, 2 );

        List<int[]> listRef = new ArrayList<>( totalComb );

        for(int i = 0; i < length - 1; i++){
            int[] base = Arrays.copyOfRange( filterColumns, i, i+1 );
            listRef.add ( base );
            for(int j = i+1; j < length; j++ ) {
                listRef.add(Arrays.copyOfRange(filterColumns, i, j + 1));

                // now get all possibilities without this j
                if (j < length - 1) {
                    int k = j + 1;
                    int[] aux1 = {filterColumns[i], filterColumns[k]};
                    listRef.add(aux1);

                    // if there are more elements, then I form a new array including my i and k
                    // i.e., is k the last element? if not, then I have to perform the next operation
                    if (k < length - 1) {
                        int[] aux2 = Arrays.copyOfRange(filterColumns, k, length);
                        int[] aux3 = Arrays.copyOf(base, base.length + aux2.length);
                        System.arraycopy(aux2, 0, aux3, base.length, aux2.length);
                        listRef.add(aux3);
                    }

                }

            }

        }

        listRef.add( Arrays.copyOfRange( filterColumns, length - 1, length ) );
        return listRef;

    }

}
