package cn.sliew.milky.common.context;

import cn.sliew.milky.common.explain.Explanation;

import java.util.HashMap;
import java.util.Map;

/**
 * Thread local context.
 * Note: ExecuteContext is a temporary state holder.
 */
public class ExecuteContext {

    private static final ThreadLocal<ExecuteContext> LOCAL = ThreadLocal.withInitial(() -> new ExecuteContext());

    private final Map<String, String> attachments = new HashMap<String, String>();

    private boolean profile = false;

    private boolean explain = false;

    private Explanation explanation = new Explanation();

    protected ExecuteContext() {

    }

    public static ExecuteContext getContext() {
        return LOCAL.get();
    }

    public static void removeContext() {
        LOCAL.remove();
    }

    /**
     * get attachment.
     *
     * @param key
     * @return attachment
     */
    public String getAttachment(String key) {
        return attachments.get(key);
    }

    /**
     * set attachment.
     *
     * @param key
     * @param value
     * @return context
     */
    public ExecuteContext setAttachment(String key, String value) {
        if (value == null) {
            attachments.remove(key);
        } else {
            attachments.put(key, value);
        }
        return this;
    }

    /**
     * remove attachment.
     *
     * @param key
     * @return context
     */
    public ExecuteContext removeAttachment(String key) {
        attachments.remove(key);
        return this;
    }

    /**
     * get attachments.
     *
     * @return attachments
     */
    public Map<String, String> getAttachments() {
        return attachments;
    }

    /**
     * set attachments
     *
     * @param attachment
     * @return context
     */
    public ExecuteContext setAttachments(Map<String, String> attachment) {
        this.attachments.clear();
        if (attachment != null && attachment.size() > 0) {
            this.attachments.putAll(attachment);
        }
        return this;
    }

    public void clearAttachments() {
        this.attachments.clear();
    }

    public boolean profile() {
        return profile;
    }

    public void openProfile() {
        this.profile = true;
    }

    public boolean explain() {
        return explain;
    }

    public void openExplain() {
        this.explain = true;
    }

}
