//package org.liquidengine.legui.processor.system.component;
//
//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.liquidengine.legui.component.*;
//import org.liquidengine.legui.event.SystemEvent;
//import org.liquidengine.legui.event.system.*;
//import org.liquidengine.legui.processor.system.component.button.ButtonCursorPosEventListener;
//import org.liquidengine.legui.processor.system.component.button.ButtonMouseClickEventListener;
//import org.liquidengine.legui.processor.system.component.checkbox.CheckBoxMouseClickListener;
//import org.liquidengine.legui.processor.system.component.def.DefaultGuiScrollListener;
//import org.liquidengine.legui.processor.system.component.radiobutton.RadioButtonMouseClickListener;
//import org.liquidengine.legui.processor.system.component.scrollbar.ScrollBarCursorPosListener;
//import org.liquidengine.legui.processor.system.component.scrollbar.ScrollBarMouseClickListener;
//import org.liquidengine.legui.processor.system.component.scrollbar.ScrollBarScrollListener;
//import org.liquidengine.legui.processor.system.component.slider.SliderSystemCursorPosEventListener;
//import org.liquidengine.legui.processor.system.component.slider.SliderSystemMouseClickEventListener;
//import org.liquidengine.legui.processor.system.component.slider.SliderSystemScrollEventListener;
//import org.liquidengine.legui.processor.system.component.textarea.TextAreaCharEventProcessor;
//import org.liquidengine.legui.processor.system.component.textarea.TextAreaKeyProcessor;
//import org.liquidengine.legui.processor.system.component.textarea.TextAreaMouseClickProcessor;
//import org.liquidengine.legui.processor.system.component.textinput.TextInputCharEventProcessor;
//import org.liquidengine.legui.processor.system.component.textinput.TextInputCursorPosProcessor;
//import org.liquidengine.legui.processor.system.component.textinput.TextInputKeyProcessor;
//import org.liquidengine.legui.processor.system.component.textinput.TextInputMouseClickProcessor;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by Shcherbin Alexander on 8/25/2016.
// */
//public class LeguiComponentEventProcessorProvider extends AbstractGuiProcessorProvider {
//    private static final Logger LOGGER = LogManager.getLogger();
//    private static final String PATTERN = "No specified gui Processor for \"%s\" class and \"%s\" event.";
//    private static final LeguiComponentEventProcessor DEFAULT = (gui, event, state) -> {
////        LOGGER.warn(String.format(PATTERN, gui.getClass(), event.getClass()));
//    };
//    private static final LeguiComponentEventProcessor<Component, SystemScrollEvent> DEFAULT_SCROLL_PROCESSOR = new DefaultGuiScrollListener();
//
//    private Map<ProviderKey, LeguiComponentEventProcessor> guiProcessorMap = new ConcurrentHashMap<>();
//
//    public LeguiComponentEventProcessorProvider() {
//        registerProcessors();
//    }
//
//    private void registerProcessors() {
//        registerGuiProcessor(Button.class, SystemMouseClickEvent.class, new ButtonMouseClickEventListener());
//        registerGuiProcessor(Button.class, SystemCursorPosEvent.class, new ButtonCursorPosEventListener());
//
//        registerGuiProcessor(CheckBox.class, SystemMouseClickEvent.class, new CheckBoxMouseClickListener());
//
//        registerGuiProcessor(Slider.class, SystemMouseClickEvent.class, new SliderSystemMouseClickEventListener());
//        registerGuiProcessor(Slider.class, SystemCursorPosEvent.class, new SliderSystemCursorPosEventListener());
//        registerGuiProcessor(Slider.class, SystemScrollEvent.class, new SliderSystemScrollEventListener());
//
//        registerGuiProcessor(RadioButton.class, SystemMouseClickEvent.class, new RadioButtonMouseClickListener());
//
//        registerGuiProcessor(ScrollBar.class, SystemMouseClickEvent.class, new ScrollBarMouseClickListener());
//        registerGuiProcessor(ScrollBar.class, SystemCursorPosEvent.class, new ScrollBarCursorPosListener());
//        registerGuiProcessor(ScrollBar.class, SystemScrollEvent.class, new ScrollBarScrollListener());
//
//        registerGuiProcessor(TextInput.class, SystemMouseClickEvent.class, new TextInputMouseClickProcessor());
//        registerGuiProcessor(TextInput.class, SystemCharEvent.class, new TextInputCharEventProcessor());
//        registerGuiProcessor(TextInput.class, SystemKeyEvent.class, new TextInputKeyProcessor());
//        registerGuiProcessor(TextInput.class, SystemCursorPosEvent.class, new TextInputCursorPosProcessor());
//
//        registerGuiProcessor(TextArea.class, SystemMouseClickEvent.class, new TextAreaMouseClickProcessor());
//        registerGuiProcessor(TextArea.class, SystemCharEvent.class, new TextAreaCharEventProcessor());
//        registerGuiProcessor(TextArea.class, SystemKeyEvent.class, new TextAreaKeyProcessor());
//
//    }
//
//    @Override
//    public LeguiComponentEventProcessor getGuiProcessor(Class<? extends Component> guiClass, Class<? extends SystemEvent> eventClass) {
//        ProviderKey key = new ProviderKey(guiClass, eventClass);
//        LeguiComponentEventProcessor defaultValue = eventClass.equals(SystemScrollEvent.class) ? DEFAULT_SCROLL_PROCESSOR : DEFAULT;
//        return guiProcessorMap.getOrDefault(key, defaultValue);
//    }
//
//    public void registerGuiProcessor(Class<? extends Component> guiClass, Class<? extends SystemEvent> eventClass, LeguiComponentEventProcessor guiProcessor) {
//        guiProcessorMap.put(new ProviderKey(guiClass, eventClass), guiProcessor);
//    }
//
//
//    private static class ProviderKey {
//        private Class<? extends Component> first;
//        private Class<? extends SystemEvent> second;
//
//        public ProviderKey(Class<? extends Component> first, Class<? extends SystemEvent> second) {
//            this.first = first;
//            this.second = second;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//
//            if (o == null || getClass() != o.getClass()) return false;
//
//            ProviderKey that = (ProviderKey) o;
//
//            return new EqualsBuilder()
//                    .append(first, that.first)
//                    .append(second, that.second)
//                    .isEquals();
//        }
//
//        @Override
//        public int hashCode() {
//            return new HashCodeBuilder(17, 37)
//                    .append(first)
//                    .append(second)
//                    .toHashCode();
//        }
//    }
//
//}
