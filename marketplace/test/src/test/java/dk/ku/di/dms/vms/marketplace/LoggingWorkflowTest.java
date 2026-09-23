package dk.ku.di.dms.vms.marketplace;

import java.io.File;

/**
 * In order to work, logging must be enabled
 * Overwriting app.config if necessary
 */
public final class LoggingWorkflowTest extends CartProductWorkflowTest {

    @Override
    protected void initCartAndProduct() throws Exception {
        System.setProperty("logging", "true");
        dk.ku.di.dms.vms.marketplace.product.Main.main(null);
        dk.ku.di.dms.vms.marketplace.cart.Main.main(null);
    }

    /**
     * Check whether transaction inputs have been logged
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void additionalAssertions() {
        String userDir = System.getProperty("user.dir");
        File folder = new File(userDir);
        File[] listOfFiles = folder.listFiles();
        int count = 0;
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                count++;
                file.delete();
            }
        }
        assert count == 3;
    }

}
