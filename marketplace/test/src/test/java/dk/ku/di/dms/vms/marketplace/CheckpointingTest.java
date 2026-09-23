package dk.ku.di.dms.vms.marketplace;

import dk.ku.di.dms.vms.modb.common.memory.MemoryManager;
import dk.ku.di.dms.vms.modb.common.utils.ConfigUtils;
import dk.ku.di.dms.vms.modb.definition.Schema;
import dk.ku.di.dms.vms.modb.definition.key.IntKey;
import dk.ku.di.dms.vms.modb.index.unique.UniqueHashBufferIndex;
import dk.ku.di.dms.vms.modb.storage.record.RecordBufferContext;
import dk.ku.di.dms.vms.sdk.embed.client.VmsApplication;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class CheckpointingTest extends CartProductWorkflowTest {

    private static VmsApplication PRODUCT_VMS;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // System.setProperty("logging", "true");
        System.setProperty("checkpointing", "true");
        Properties properties = ConfigUtils.loadProperties();
        PRODUCT_VMS = dk.ku.di.dms.vms.marketplace.product.Main.buildVms(properties);
        PRODUCT_VMS.start();
    }

    @Override
    protected void initCartAndProduct() throws Exception {
        dk.ku.di.dms.vms.marketplace.cart.Main.main(null);
    }

    /**
     * Check whether state has been checkpointed
     */
    @Override
    protected void additionalAssertions() {
        String userHome = ConfigUtils.getUserHome();
        String filePath = userHome + "/vms/products.data";
        Path path = Paths.get(filePath);
        Schema schema = PRODUCT_VMS.getSchema("products");
        try (FileChannel fc = FileChannel.open(path, StandardOpenOption.READ)){
            var memorySegment = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    10L * schema.getRecordSize(), Arena.ofShared());
            var bufCtx = RecordBufferContext.build(memorySegment);
            var index = new UniqueHashBufferIndex(bufCtx, schema, schema.getPrimaryKeyColumns(), 10);
            var bb = MemoryManager.getTemporaryDirectBuffer( (int)fc.size() );
            int res = fc.read(bb);
            Assert.assertEquals(4260, res);
            Set<Integer> setOfIds = new HashSet<>();
            // read records from the byte buffer
            for(int i = 1; i <= MAX_ITEMS; i++){
                Object[] record = index.lookupByKey( IntKey.of(i) );
                Assert.assertNotNull(record);
                setOfIds.add((Integer) record[0]);
            }
            Assert.assertEquals(MAX_ITEMS, setOfIds.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
