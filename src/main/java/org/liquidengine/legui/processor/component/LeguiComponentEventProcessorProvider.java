package org.liquidengine.legui.processor.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.system.LeguiSystemEvent;
import org.liquidengine.legui.event.system.ScrollEvent;
import org.liquidengine.legui.processor.component.def.DefaultGuiScrollProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 8/25/2016.
 */
public class LeguiComponentEventProcessorProvider extends AbstractGuiProcessorProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PATTERN = "No specified gui Processor for \"%s\" class and \"%s\" event.";
    private static final LeguiComponentEventProcessor DEFAULT = (gui, event, state) -> {
    };
    private static final LeguiComponentEventProcessor<Component, ScrollEvent> DEFAULT_SCROLL_PROCESSOR = new DefaultGuiScrollProcessor();

    private Map<ProviderKey, LeguiComponentEventProcessor> guiProcessorMap = new ConcurrentHashMap<>();

    public LeguiComponentEventProcessorProvider() {
        registerProcessors();
    }

    private void registerProcessors() {
        // registerGuiProcessor(CheckBox.class, MouseClickEvent.class, new CheckBoxMouseClickProcessor());

        // registerGuiProcessor(Slider.class, MouseClickEvent.class, new SliderMouseClickProcessor());
        // registerGuiProcessor(Slider.class, CursorPosEvent.class, new SliderCursorPosProcessor());
        // registerGuiProcessor(Slider.class, ScrollEvent.class, new SliderScrollProcessor());

        // registerGuiProcessor(RadioButton.class, MouseClickEvent.class, new RadioButtonMouseClickProcessor());

        // registerGuiProcessor(ScrollBar.class, MouseClickEvent.class, new ScrollBarMouseClickProcessor());
        // registerGuiProcessor(ScrollBar.class, CursorPosEvent.class, new ScrollBarCursorPosProcessor());
        // registerGuiProcessor(ScrollBar.class, ScrollEvent.class, new ScrollBarScrollProcessor());

        // registerGuiProcessor(TextInput.class, MouseClickEvent.class, new TextInputMouseClickProcessor());
        // registerGuiProcessor(TextInput.class, CharEvent.class, new TextInputCharEventProcessor());
        // registerGuiProcessor(TextInput.class, KeyEvent.class, new TextInputKeyProcessor());

        // registerGuiProcessor(Widget.class, CursorPosEvent.class, new WidgetCursorPosProcessor());
    }

    @Override
    public LeguiComponentEventProcessor getGuiProcessor(Class<? extends Component> guiClass, Class<? extends LeguiSystemEvent> eventClass) {
        ProviderKey key = new ProviderKey(guiClass, eventClass);
        LeguiComponentEventProcessor defaultValue = eventClass.equals(ScrollEvent.class) ? DEFAULT_SCROLL_PROCESSOR : DEFAULT;
        return guiProcessorMap.getOrDefault(key, defaultValue);
    }

    @Override
    public void registerGuiProcessor(Class<? extends Component> guiClass, Class<? extends LeguiSystemEvent> eventClass, LeguiComponentEventProcessor guiProcessor) {
        guiProcessorMap.put(new ProviderKey(guiClass, eventClass), guiProcessor);
    }


    private static class ProviderKey {
        private Class<? extends Component> first;
        private Class<? extends LeguiSystemEvent> second;

        public ProviderKey(Class<? extends Component> first, Class<? extends LeguiSystemEvent> second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof ProviderKey)) return false;

            ProviderKey that = (ProviderKey) o;

            return new EqualsBuilder()
                    .append(first, that.first)
                    .append(second, that.second)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(first)
                    .append(second)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("first", first)
                    .append("second", second)
                    .toString();
        }
    }

}
