package org.liquidengine.legui.cursor;

public class CursorServiceProvider {

    //@formatter:off

    /**
     * Hidden constructor.
     */
    private CursorServiceProvider() {
    }

    /**
     * Returns instance of event processor.
     *
     * @return instance of event processor.
     */
    public static CursorService getInstance() {
        return CursorServiceProvider.CursorServiceProviderHolder.INSTANCE;
    }

    /**
     * Used to set event processor.
     *
     * @param eventProcessor event processor to set.
     */
    public static void setInstance(CursorService eventProcessor) {
        if (eventProcessor != null) {
            CursorServiceProvider.CursorServiceProviderHolder.INSTANCE = eventProcessor;
        }
    }

    /**
     * Instance holder.
     */
    private static class CursorServiceProviderHolder {
        private static CursorService INSTANCE = new GLFWCursorServiceImpl();
    }
    //@formatter:on
}
