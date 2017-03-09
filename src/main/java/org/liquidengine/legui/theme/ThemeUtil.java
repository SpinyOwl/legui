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
                updateComponent(child, theme);
                if (child instanceof Widget) {
                    updateWidget(theme, (Widget) child);
                } else {
                    updateContainer((Container) child, theme);
                }
            } else {
                updateComponent(child, theme);
            }
        }

    }

    private static void updateWidget(Theme theme, Widget widget) {
        Container container = widget.getContainer();
        updateComponent(container, theme);
        updateContainer(container, theme);

        Container titleContainer = widget.getTitleContainer();
        updateComponent(titleContainer, theme);
        updateContainer(titleContainer, theme);

//        widget.getCloseButton().setCornerRadius(theme.cornerRadius());
        widget.getCloseButton().setBorder(null);
        updateTextComponent(theme, widget.getCloseButton());

//        widget.getMinimizeButton().setCornerRadius(theme.cornerRadius());
        widget.getMinimizeButton().setBorder(null);
        updateTextComponent(theme, widget.getMinimizeButton());
    }

    private static void updateComponent(Component component, Theme theme) {
//        component.setBackgroundColor(theme.backgroundColor());
//        component.setCornerRadius(theme.cornerRadius());

        if (component instanceof TextComponent) {
            if (!(
                    (component instanceof CheckBox) ||
                            (component instanceof Label) ||
                            (component instanceof RadioButton))) {
//                component.setBorder(theme.border());
            }
            updateTextComponent(theme, (TextComponent) component);
        } else if (component instanceof ScrollBar) {
//            component.setBorder(theme.border());
            updateScrollBar(theme, (ScrollBar) component);
        } else if (component instanceof Slider) {
        } else {
//            component.setBorder(theme.border());
        }

        if (component instanceof Controller) {
            Tooltip tooltip = ((Controller) component).getTooltip();
            if (tooltip != null) {
                updateComponent(tooltip, theme);
            }
        }
    }

    private static void updateScrollBar(Theme theme, ScrollBar scrollBar) {
//        scrollBar.setArrowColor(theme.scrollBarArrowColor());
//        scrollBar.setScrollColor(theme.scrollBarColor());
//        scrollBar.setArrowsEnabled(theme.scrollBarArrowsEnabled());
//        scrollBar.setArrowSize(theme.scrollBarArrowSize());
    }

    private static void updateTextComponent(Theme theme, TextComponent textComponent) {
        TextState textState = textComponent.getTextState();
//        textState.setTextColor(theme.fontColor());
//        textState.setFontSize(theme.fontSize());
//        textState.setHighlightColor(theme.highlightColor());
//        textState.setPadding(theme.textPadding());
    }
}
