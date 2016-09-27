package org.liquidengine.legui.processor.system.component;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.system.LeguiSystemEvent;

/**
 * Created by Shcherbin Alexander on 8/25/2016.
 */
public abstract class AbstractGuiProcessorProvider {
    public static AbstractGuiProcessorProvider getProvider() {
        return PPH.PROVIDER;
    }

    public static void setProvider(AbstractGuiProcessorProvider provider) {
        PPH.PROVIDER = provider;
    }

    public abstract LeguiComponentEventProcessor getGuiProcessor(Class<? extends Component> guiClass, Class<? extends LeguiSystemEvent> eventClass);

    private static class PPH {
        private static AbstractGuiProcessorProvider PROVIDER = new LeguiComponentEventProcessorProvider();
    }

}
