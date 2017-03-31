package org.liquidengine.legui.theme;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;

import java.util.List;

import static org.liquidengine.legui.font.FontRegistry.MATERIAL_ICONS_REGULAR;

/**
 * Dark Theme. Used to change theme of components to dark.
 */
public class DarkTheme extends Theme {

    /**
     * Used to create theme instance.
     */
    public DarkTheme() {
        super(createThemeManager());
    }

    /**
     * Used to initialize theme manager.
     *
     * @return initialized theme manager.
     */
    private static ThemeManager createThemeManager() {
        ThemeManager manager = new DefaultThemeManager();
        manager.setComponentTheme(Button.class, new DarkButtonTheme<>());
        manager.setComponentTheme(CheckBox.class, new DarkCheckBoxTheme<>());
        manager.setComponentTheme(Component.class, new DarkComponentTheme<>());
        manager.setComponentTheme(Container.class, new DarkContainerTheme<>());
        manager.setComponentTheme(Controller.class, new DarkControllerTheme<>());
        manager.setComponentTheme(Label.class, new DarkLabelTheme<>());
        manager.setComponentTheme(LayerContainer.class, new DarkLayerContainerTheme<>());
        manager.setComponentTheme(ProgressBar.class, new DarkProgressBarTheme<>());
        manager.setComponentTheme(ScrollablePanel.class, new DarkScrollablePanelTheme<>());
        manager.setComponentTheme(RadioButton.class, new DarkRadioButtonTheme<>());
        manager.setComponentTheme(ScrollBar.class, new DarkScrollBarTheme<>());
        manager.setComponentTheme(SelectBox.class, new DarkSelectBoxTheme<>());
        manager.setComponentTheme(SelectBox.SelectBoxScrollablePanel.class, new DarkSelectBoxScrollablePanelTheme<>());
        manager.setComponentTheme(SelectBox.SelectBoxElement.class, new DarkSelectBoxElementTheme<>());
        manager.setComponentTheme(Slider.class, new DarkSliderTheme<>());
        manager.setComponentTheme(TextArea.class, new DarkTextAreaTheme<>());
        manager.setComponentTheme(TextInput.class, new DarkTextInputTheme<>());
        manager.setComponentTheme(ToggleButton.class, new DarkToggleButtonTheme<>());
        manager.setComponentTheme(Tooltip.class, new DarkTooltipTheme<>());
        manager.setComponentTheme(Widget.class, new DarkWidgetTheme<>());
        return manager;
    }

    private static class DarkComponentTheme<T extends Component> extends AbstractTheme<T> {
        @Override
        public void apply(T component) {
            component.setBorder(new SimpleLineBorder(ColorConstants.lightGray(), 1.2f));
            component.setCornerRadius(2);
            component.setBackgroundColor(ColorConstants.darkGray());
        }
    }

    private static class DarkContainerTheme<T extends Container> extends DarkControllerTheme<T> {
        @Override
        public void applyAll(T component) {
            super.applyAll(component);
            List<? extends Component> childs = component.getChilds();
            for (Component child : childs) {
                Themes.getDefaultTheme().applyAll(child);
            }
        }
    }

    private static class DarkRadioButtonTheme<T extends RadioButton> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.setBackgroundColor(ColorConstants.transparent());
            component.getTextState().setTextColor(ColorConstants.white());
            component.setIconUnchecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, (char) 0xE836, ColorConstants.white()));
            component.setIconChecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, (char) 0xE837, ColorConstants.white()));
            component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
            component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
        }
    }

    private static class DarkButtonTheme<T extends Button> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(new SimpleLineBorder(ColorConstants.lightGray(), 1.2f));
            component.getTextState().setTextColor(ColorConstants.white());
        }
    }

    private static class DarkCheckBoxTheme<T extends CheckBox> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.setBackgroundColor(ColorConstants.transparent());
            component.getTextState().setTextColor(ColorConstants.white());
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
            component.setIconUnchecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, (char) 0xE835, ColorConstants.white()));
            component.setIconChecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, (char) 0xE834, ColorConstants.white()));
            component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
            component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
        }
    }

    private static class DarkLabelTheme<T extends Label> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.setBackgroundColor(ColorConstants.transparent());
            component.getTextState().setTextColor(ColorConstants.white());
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
        }
    }

    private static class DarkSliderTheme<T extends Slider> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBackgroundColor(ColorConstants.transparent());
            component.setSliderColor(ColorConstants.gray());
            component.setSliderActiveColor(ColorConstants.white());
            component.setBorder(null);
        }
    }

    private static class DarkTextInputTheme<T extends TextInput> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.getTextState().setTextColor(ColorConstants.white());
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
            component.getTextState().setHighlightColor(ColorConstants.black());
        }
    }

    private static class DarkTextAreaTheme<T extends TextArea> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.getTextState().setTextColor(ColorConstants.white());
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
            component.getTextState().setHighlightColor(ColorConstants.black());
        }
    }

    private static class DarkScrollBarTheme<T extends ScrollBar> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setArrowColor(ColorConstants.white());
            component.setScrollColor(ColorConstants.white());
            component.setArrowsEnabled(true);
        }
    }

    private static class DarkWidgetTheme<T extends Widget> extends DarkControllerTheme<T> {
        @Override
        public void applyAll(T component) {
            super.applyAll(component);
            component.getMinimizeButton().setBackgroundColor(ColorConstants.lightBlack());
            component.getMinimizeButton().getTextState().setTextColor(ColorConstants.white());
            component.getMinimizeButton().setBorder(null);
            component.getCloseButton().setBackgroundColor(ColorConstants.lightBlack());
            component.getCloseButton().getTextState().setTextColor(ColorConstants.white());
            component.getCloseButton().setBorder(null);
            component.getTitleContainer().setBackgroundColor(ColorConstants.lightBlack());
            component.setTitleBackgroundColor(ColorConstants.lightBlack());
            component.getTitleTextState().setTextColor(ColorConstants.white());
            Icon closeIcon = component.getCloseIcon();
            if (closeIcon != null && closeIcon instanceof CharIcon) {
                CharIcon bgIcon = (CharIcon) closeIcon;
                bgIcon.setColor(ColorConstants.white());
                bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
                bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
            }

            Icon minimizeIcon = component.getMinimizeIcon();
            if (minimizeIcon != null && minimizeIcon instanceof CharIcon) {
                CharIcon bgIcon = (CharIcon) minimizeIcon;
                bgIcon.setColor(ColorConstants.white());
                bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
                bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
            }

            Icon maximizeIcon = component.getMaximizeIcon();
            if (maximizeIcon != null && maximizeIcon instanceof CharIcon) {
                CharIcon bgIcon = (CharIcon) maximizeIcon;
                bgIcon.setColor(ColorConstants.white());
                bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
                bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
            }

            Themes.getDefaultTheme().applyAll(component.getContainer());
        }
    }

    private static class DarkControllerTheme<T extends Controller> extends DarkComponentTheme<T> {
        @Override
        public void applyAll(T component) {
            super.applyAll(component);
            Tooltip tooltip = component.getTooltip();
            if (tooltip != null) {
                Themes.getDefaultTheme().applyAll(tooltip);
            }
        }
    }

    private static class DarkTooltipTheme<T extends Tooltip> extends DarkComponentTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(new SimpleLineBorder(new Vector4f(0, 0, 0, 0.9f), 1.2f));
            component.setBackgroundColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.9f));
            component.getTextState().setTextColor(ColorConstants.white());
        }
    }


    private static class DarkLayerContainerTheme<T extends LayerContainer> extends DarkContainerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
        }
    }

    private static class DarkProgressBarTheme<T extends ProgressBar> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setProgressColor(ColorConstants.gray());
        }
    }

    private static class DarkScrollablePanelTheme<T extends ScrollablePanel> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            Container viewport = component.getViewport();
            Themes.getDefaultTheme().apply(viewport);
            Themes.getDefaultTheme().applyAll(component.getVerticalScrollBar());
            Themes.getDefaultTheme().applyAll(component.getHorizontalScrollBar());
            viewport.setBorder(null);
            viewport.setBackgroundColor(ColorConstants.transparent());
        }

        @Override
        public void applyAll(T component) {
            super.applyAll(component);
            Container viewport = component.getViewport();
            Themes.getDefaultTheme().applyAll(viewport);
            viewport.setBorder(null);
            viewport.setBackgroundColor(ColorConstants.transparent());
        }
    }

    private static class DarkSelectBoxTheme<T extends SelectBox> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.getExpandButton().setBorder(null);
            component.getExpandButton().setBackgroundColor(ColorConstants.transparent());
            component.getExpandButton().getTextState().setTextColor(ColorConstants.white());
            component.getSelectionButton().setBorder(null);
            component.getSelectionButton().setBackgroundColor(ColorConstants.transparent());
            component.getSelectionButton().getTextState().setTextColor(ColorConstants.white());

            Themes.getDefaultTheme().applyAll(component.getSelectionListPanel());
        }

        @Override
        public void applyAll(T component) {
            super.applyAll(component);

            Themes.getDefaultTheme().applyAll(component.getSelectionListPanel());
        }
    }

    private static class DarkSelectBoxScrollablePanelTheme<T extends SelectBox.SelectBoxScrollablePanel> extends DarkScrollablePanelTheme<T> {

    }

    private static class DarkSelectBoxElementTheme<T extends SelectBox.SelectBoxElement> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.setBackgroundColor(ColorConstants.transparent());
            component.getTextState().setTextColor(ColorConstants.white());
        }
    }

    private static class DarkToggleButtonTheme<T extends ToggleButton> extends DarkControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBackgroundColor(ColorConstants.darkRed());
            component.setToggledBackgroundColor(ColorConstants.darkGreen());
        }
    }
}
