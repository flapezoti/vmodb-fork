package dk.ku.di.dms.vms.modb.api;

import dk.ku.di.dms.vms.modb.api.query.parser.Parser;
import dk.ku.di.dms.vms.modb.api.query.statement.SelectStatement;
import org.junit.Assert;
import org.junit.Test;

public class AppTest 
{
    @Test
    public void testSelectWithOrderBy1()
    {
        SelectStatement parsed = Parser.parse("SELECT i_price FROM item WHERE i_id IN (:itemIds) ORDER BY i_id");
        Assert.assertFalse(parsed.orderByClause.isEmpty());
    }

    @Test
    public void testSelectWithOrder2()
    {
        SelectStatement parsed = Parser.parse("select * from customer where c_d_id = :d_id and c_w_id = :w_id and c_last = :c_last order by c_first");
        Assert.assertFalse(parsed.orderByClause.isEmpty());
    }

}
