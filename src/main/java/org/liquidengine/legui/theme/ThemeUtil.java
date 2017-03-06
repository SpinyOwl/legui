package org.liquidengine.legui.theme;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.TextState;

import java.util.List;

/**
 * Used to apply theme to frame.
 */
public final class ThemeUtil {
    private ThemeUtil() {
    }

    /**
     * Used to apply theme to frame.
     * Note that all custom styles would be overrided.
     *
     * @param theme
     * @param frame
     */
    public static void applyTheme(Theme theme, Frame frame) {
        List<Layer> layers = frame.getAllLayers();
        for (Layer layer : layers) {
            LayerContainer container = layer.getContainer();
            updateContainer(container, theme);
        }

    }

    private static void updateContainer(Container container, Theme theme) {
        List<Component> childs = container.getChilds();
        for (Component child : childs) {
            if (child instanceof Container) {
                updateComponent(container, theme);
                updateContainer((Container) child, theme);
            } else {
                updateComponent(child, theme);
            }
        }

    }

    private static void updateComponent(Component component, Theme theme) {
        component.setBackgroundColor(theme.backgroundColor());
        component.setBorder(theme.border());
        component.setCornerRadius(theme.cornerRadius());

        if (component instanceof TextComponent) {
            TextComponent textComponent = (TextComponent) component;
            TextState     textState     = textComponent.getTextState();
            textState.setTextColor(theme.fontColor());
//            textState.setFont(theme.font());
            textState.setFontSize(theme.fontSize());
        }
        if (component instanceof ScrollBar) {
            ScrollBar scrollBar = (ScrollBar) component;
            scrollBar.setArrowColor(theme.scrollBarArrowColor());
            scrollBar.setScrollColor(theme.scrollBarColor());
            scrollBar.setArrowsEnabled(theme.scrollBarArrowsEnabled());
            scrollBar.setArrowSize(theme.scrollBarArrowSize());
        }
    }
}
