package dk.ku.di.dms.vms.coordinator.election;

import java.util.concurrent.ThreadFactory;

/**
 * Simple thread factory to speed up process of creating new tasks
 */
public class VmsDaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        // thread.setPriority();
        return thread;
    }

}