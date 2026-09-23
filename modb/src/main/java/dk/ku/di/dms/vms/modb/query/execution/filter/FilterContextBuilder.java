package dk.ku.di.dms.vms.modb.query.execution.filter;

import dk.ku.di.dms.vms.modb.api.query.enums.ExpressionTypeEnum;
import dk.ku.di.dms.vms.modb.common.type.DataType;
import dk.ku.di.dms.vms.modb.query.analyzer.predicate.WherePredicate;
import dk.ku.di.dms.vms.modb.query.execution.filter.types.TypedBiPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@SuppressWarnings("DuplicatedCode")
public class FilterContextBuilder {

    public static Predicate<Object> predicateNull = Objects::isNull;
    public static Predicate<Object> predicateNotNull = Objects::nonNull;

    public static final TypedBiPredicate<Integer> intGtPredicate = ((t1, t2) -> t1 > t2);
    public static final TypedBiPredicate<Integer> intGtOrEqPredicate = ((t1, t2) -> t1 >= t2);
    public static final TypedBiPredicate<Integer> intLtPredicate = ((t1, t2) -> t1 < t2);
    public static final TypedBiPredicate<Integer> intLtOrEqPredicate = ((t1, t2) -> t1 <= t2);
    public static final TypedBiPredicate<Integer> intEqPredicate = (Integer::equals);
    public static final TypedBiPredicate<Integer> intNotEqPredicate = ((t1, t2) -> !t1.equals(t2));

    private static TypedBiPredicate<Integer> getIntValuePredicate(ExpressionTypeEnum expressionType){
        return switch (expressionType) {
            case EQUALS -> intEqPredicate;
            case GREATER_THAN -> intGtPredicate;
            case GREATER_THAN_OR_EQUAL -> intGtOrEqPredicate;
            case LESS_THAN -> intLtPredicate;
            case LESS_THAN_OR_EQUAL -> intLtOrEqPredicate;
            case NOT_EQUALS -> intNotEqPredicate;
            default -> throw new IllegalStateException("FAIL");
        };
    }

    public static final TypedBiPredicate<Float> floatGtPredicate = ((t1, t2) -> t1 > t2);
    public static final TypedBiPredicate<Float> floatGtOrEqPredicate = ((t1, t2) -> t1 >= t2);
    public static final TypedBiPredicate<Float> floatLtPredicate = ((t1, t2) -> t1 < t2);
    public static final TypedBiPredicate<Float> floatLtOrEqPredicate = ((t1, t2) -> t1 <= t2);
    public static final TypedBiPredicate<Float> floatEqPredicate = (Float::equals);
    public static final TypedBiPredicate<Float> floatNotEqPredicate = ((t1, t2) -> !t1.equals(t2));

    private static TypedBiPredicate<Float> getFloatValuePredicate(ExpressionTypeEnum expressionType){
        return switch (expressionType) {
            case EQUALS -> floatEqPredicate;
            case GREATER_THAN -> floatGtPredicate;
            case GREATER_THAN_OR_EQUAL -> floatGtOrEqPredicate;
            case LESS_THAN -> floatLtPredicate;
            case LESS_THAN_OR_EQUAL -> floatLtOrEqPredicate;
            case NOT_EQUALS -> floatNotEqPredicate;
            default -> throw new IllegalStateException("FAIL");
        };
    }

    public static final TypedBiPredicate<Double> doubleGtPredicate = ((t1, t2) -> t1 > t2);
    public static final TypedBiPredicate<Double> doubleGtOrEqPredicate = ((t1, t2) -> t1 >= t2);
    public static final TypedBiPredicate<Double> doubleLtPredicate = ((t1, t2) -> t1 < t2);
    public static final TypedBiPredicate<Double> doubleLtOrEqPredicate = ((t1, t2) -> t1 <= t2);
    public static final TypedBiPredicate<Double> doubleEqPredicate = (Double::equals);
    public static final TypedBiPredicate<Double> doubleNotEqPredicate = ((t1, t2) -> !t1.equals(t2));

    private static TypedBiPredicate<Double> getDoubleValuePredicate(ExpressionTypeEnum expressionType){
        return switch (expressionType) {
            case EQUALS -> doubleEqPredicate;
            case GREATER_THAN -> doubleGtPredicate;
            case GREATER_THAN_OR_EQUAL -> doubleGtOrEqPredicate;
            case LESS_THAN -> doubleLtPredicate;
            case LESS_THAN_OR_EQUAL -> doubleLtOrEqPredicate;
            case NOT_EQUALS -> doubleNotEqPredicate;
            default -> throw new IllegalStateException("FAIL");
        };
    }

    public static final TypedBiPredicate<Long> longGtPredicate = ((t1, t2) -> t1 > t2);
    public static final TypedBiPredicate<Long> longGtOrEqPredicate = ((t1, t2) -> t1 >= t2);
    public static final TypedBiPredicate<Long> longLtPredicate = ((t1, t2) -> t1 < t2);
    public static final TypedBiPredicate<Long> longLtOrEqPredicate = ((t1, t2) -> t1 <= t2);
    public static final TypedBiPredicate<Long> longEqPredicate = (Long::equals);
    public static final TypedBiPredicate<Long> longNotEqPredicate = ((t1, t2) -> !t1.equals(t2));

    private static TypedBiPredicate<Long> getLongValuePredicate(ExpressionTypeEnum expressionType){
        return switch (expressionType) {
            case EQUALS -> longEqPredicate;
            case GREATER_THAN -> longGtPredicate;
            case GREATER_THAN_OR_EQUAL -> longGtOrEqPredicate;
            case LESS_THAN -> longLtPredicate;
            case LESS_THAN_OR_EQUAL -> longLtOrEqPredicate;
            case NOT_EQUALS -> longNotEqPredicate;
            default -> throw new IllegalStateException("FAIL");
        };
    }

    public static final TypedBiPredicate<Boolean> boolEqPredicate = ((t1, t2) -> t1 == t2);
    public static final TypedBiPredicate<Boolean> boolNotEqPredicate = ((t1, t2) -> t1 != t2);

    private static TypedBiPredicate<Boolean> getBooleanValuePredicate(ExpressionTypeEnum expressionType){
        return switch (expressionType) {
            case EQUALS -> boolEqPredicate;
            case NOT_EQUALS -> boolNotEqPredicate;
            default -> throw new IllegalStateException("FAIL");
        };
    }

    public static final TypedBiPredicate<String> stringEqPredicate = (String::contentEquals);
    public static final TypedBiPredicate<String> stringNotEqPredicate = ((t1, t2) -> !t1.contentEquals(t2));

    private static TypedBiPredicate<String> getStringValuePredicate(ExpressionTypeEnum expressionType){
        return switch (expressionType) {
            case EQUALS -> stringEqPredicate;
            case NOT_EQUALS -> stringNotEqPredicate;
            default -> throw new IllegalStateException("FAIL");
        };
    }

    public static final TypedBiPredicate<Character> charEqPredicate = ((t1, t2) -> t1.compareTo(t2) == 0);
    public static final TypedBiPredicate<Character> charNotEqPredicate = ((t1, t2) -> t1.compareTo(t2) != 0);

    private static TypedBiPredicate<Character> getCharValuePredicate(ExpressionTypeEnum expressionType){
        return switch (expressionType) {
            case EQUALS -> charEqPredicate;
            case NOT_EQUALS -> charNotEqPredicate;
            default -> throw new IllegalStateException("FAIL");
        };
    }

    private static boolean isNullPredicate(ExpressionTypeEnum expressionType){
        return expressionType == ExpressionTypeEnum.IS_NOT_NULL || expressionType == ExpressionTypeEnum.IS_NULL;
    }

    private static Predicate<Object> getNullPredicate(ExpressionTypeEnum expressionType){
        if(expressionType == ExpressionTypeEnum.IS_NULL) return predicateNull;
        return predicateNotNull;
    }

    public static FilterContext build(List<WherePredicate> wherePredicates){
        int size = wherePredicates.size();
        FilterContext filterContext = new FilterContext();
        filterContext.filterColumns = new ArrayList<>(size);
        filterContext.filterTypes = new ArrayList<>(size);
        // bi-predicates usually dominate the workload
        filterContext.biPredicates = new ArrayList<>(size);
        filterContext.biPredicateParams = new ArrayList<>(size);
        filterContext.predicates = new ArrayList<>();

        for(WherePredicate wherePredicate : wherePredicates){
            filterContext.filterColumns.add( wherePredicate.columnReference.columnPosition );
            DataType dataType = wherePredicate.columnReference.dataType;
            ExpressionTypeEnum expressionType = wherePredicate.expression;

            if(isNullPredicate(expressionType)){
                filterContext.predicates.add( getNullPredicate(expressionType) );
                filterContext.filterTypes.add(FilterType.P);
                continue;
            }

            filterContext.filterTypes.add(FilterType.BP);
            filterContext.biPredicateParams.add( wherePredicate.value );
            switch(dataType){
                case INT -> filterContext.biPredicates.add(getIntValuePredicate(expressionType));
                case STRING -> filterContext.biPredicates.add(getStringValuePredicate(expressionType));
                case LONG, DATE -> filterContext.biPredicates.add( getLongValuePredicate(expressionType) );
                case DOUBLE -> filterContext.biPredicates.add( getDoubleValuePredicate(expressionType) );
                case FLOAT -> filterContext.biPredicates.add( getFloatValuePredicate(expressionType) );
                case BOOL -> filterContext.biPredicates.add( getBooleanValuePredicate(expressionType) );
                case CHAR -> filterContext.biPredicates.add( getCharValuePredicate(expressionType) );
                default -> throw new IllegalStateException("Unexpected value: " + dataType);
            }
        }
        return filterContext;
    }

}
