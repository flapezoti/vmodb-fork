package dk.ku.di.dms.vms.modb.query;

import dk.ku.di.dms.vms.modb.api.query.builder.QueryBuilderFactory;
import dk.ku.di.dms.vms.modb.api.query.builder.SelectStatementBuilder;
import dk.ku.di.dms.vms.modb.api.query.builder.UpdateStatementBuilder;
import dk.ku.di.dms.vms.modb.api.query.enums.GroupByOperationEnum;
import dk.ku.di.dms.vms.modb.api.query.statement.IStatement;
import dk.ku.di.dms.vms.modb.api.query.statement.SelectStatement;
import dk.ku.di.dms.vms.modb.common.type.DataType;
import dk.ku.di.dms.vms.modb.definition.Schema;
import dk.ku.di.dms.vms.modb.definition.Table;
import dk.ku.di.dms.vms.modb.definition.key.SimpleKey;
import dk.ku.di.dms.vms.modb.query.analyzer.Analyzer;
import dk.ku.di.dms.vms.modb.query.analyzer.QueryTree;
import dk.ku.di.dms.vms.modb.query.analyzer.exception.AnalyzerException;
import dk.ku.di.dms.vms.modb.query.execution.operators.AbstractSimpleOperator;
import dk.ku.di.dms.vms.modb.query.planner.SimplePlanner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static dk.ku.di.dms.vms.modb.api.query.enums.ExpressionTypeEnum.EQUALS;
import static dk.ku.di.dms.vms.modb.api.query.enums.ExpressionTypeEnum.GREATER_THAN;

public class QueryTest {

    private static Map<String, Table> catalog;

    @BeforeClass
    public static void buildCatalog(){

        // catalog = TestCommon.getDefaultCatalog();

        Table table = catalog.get("item");

        SimpleKey key = SimpleKey.of(3);
        // Row row = new Row(3,2L,"HAHA","HEHE");

        SimpleKey key1 = SimpleKey.of(4);
        // Row row1 = new Row(4,3L,"HAHAdedede","HEHEdeded");

//        table.getPrimaryKeyIndex().upsert(key, row);
//        table.getPrimaryKeyIndex().upsert(key1, row1);
    }

    @Test
    public void isCatalogNotEmpty() {
        assert catalog.get("item") != null;
    }

    @Test
    public void testQueryParse(){

        final Map<String, Table> catalog = new HashMap<>();
        String[] columnNames = { "col1", "col2" };
        DataType[] columnDataTypes = { DataType.INT, DataType.INT };
        final Schema schema = new Schema( columnNames, columnDataTypes, new int[]{0}, null, false );
        catalog.put("tb1", new Table( "tb1", schema, null));
        catalog.put( "tb2", new Table( "tb2", schema, null ));

        try {
            // TODO move this test to query test
            SelectStatementBuilder builder = QueryBuilderFactory.select();
            IStatement sql = builder.project("col1, col2") // this should raise an error
                    .from("tb1")
                    .join("tb2","col1").on(EQUALS, "tb1", "col1")//.and
                    // .where("col2", EQUALS, 2) // this should raise an error
                    .build();

            Analyzer analyzer = new Analyzer(catalog);
            QueryTree queryTree = analyzer.analyze( sql );

        } catch(AnalyzerException e){
            System.out.println(e.getMessage());
            assert true;
        }
    }

    @Test
    public void testGroupByQuery() throws Exception {

        SelectStatementBuilder builder = QueryBuilderFactory.select();
        SelectStatement sql = builder //.select("AVG(item.i_price)")
                .avg("item.i_price")
                .from("item")
                .groupBy("i_price")
                .having(GroupByOperationEnum.COUNT, "i_price", GREATER_THAN, 10)
                //.where("col2", EQUALS, 2) // this should raise an error
                .build();

        Table tb = new Table( "district", null, null);
        Analyzer analyzer = new Analyzer(Map.of("district", tb));
        QueryTree queryTree = analyzer.analyze( sql );

        var planner = new SimplePlanner();
//        PlanNode planTree = planner.plan(queryTree);
//
//        SequentialQueryExecutor queryExecutor = new SequentialQueryExecutor(planTree);
//
//        IOperatorResult result = queryExecutor.get();

//        assert(result != null);

    }

    @Test
    public void testQueryParsing() throws Exception {
        UpdateStatementBuilder builder = QueryBuilderFactory.update();
        IStatement sql = builder.update("district")
                                .set("d_next_o_id",1)
                                .where("d_w_id", EQUALS, 2)
                                .and("d_id", EQUALS, 3)
                                .build();

        Table tb = new Table( "district", null, null);

        // insert data, build metadata, query processing
        Analyzer analyzer = new Analyzer(Map.of("district", tb));
        QueryTree queryTree = analyzer.analyze(sql);
        var planner = new SimplePlanner();
        AbstractSimpleOperator planTree = planner.plan(queryTree);
        //  assert the set, where, and...
        Assert.assertNotNull(planTree);
    }

}
