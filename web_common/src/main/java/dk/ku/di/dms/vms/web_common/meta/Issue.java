package dk.ku.di.dms.vms.web_common.meta;

/**
 * Writer threads must report issues to the main loop thread
 * So the next loop the main loop can take care of the problem
 */
public class Issue {

    public enum Category {
        UNREACHABLE_NODE,
        CHANNEL_CLOSED,
        CHANNEL_NOT_REGISTERED,
        COMMIT_FAILED,
        TRANSACTION_MANAGER_STOPPED,
        CANNOT_READ_FROM_NODE,
        CANNOT_CONNECT_TO_NODE
    }

    // category of the issue
    public Category category;

    // the network object on which the error was identified
    public int node;

    public Issue(Category category, int node) {
        this.category = category;
        this.node = node;
    }

}
