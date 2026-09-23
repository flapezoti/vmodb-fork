package dk.ku.di.dms.vms.modb.query.execution.filter;

import dk.ku.di.dms.vms.modb.query.execution.filter.types.TypedBiPredicate;

import java.util.List;
import java.util.function.Predicate;

public final class FilterContext {

    public List<FilterType> filterTypes;

    public List<Integer> filterColumns; // allow querying the schema to get the corresponding data type

    @SuppressWarnings("rawtypes")
    public List<TypedBiPredicate> biPredicates;

    // case of literals passed to the query
    // transient, may change on every application code call
    public List<Object> biPredicateParams;

    // only applied to null
    public List<Predicate<Object>> predicates;

}
