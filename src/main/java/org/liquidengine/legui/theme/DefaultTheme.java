package org.liquidengine.legui.theme;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.icon.CharIcon;

import java.util.List;

import static org.liquidengine.legui.font.FontRegister.MATERIAL_ICONS_REGULAR;

/**
 * Created by Aliaksandr_Shcherbin on 3/9/2017.
 */
public class DefaultTheme extends Theme {

    public DefaultTheme() {
        super(createThemeManager());
    }

    private static ThemeManager createThemeManager() {
        ThemeManager manager = new DefaultThemeManager();
        manager.setComponentTheme(Button.class, new DefaultButtonTheme<>());
        manager.setComponentTheme(CheckBox.class, new DefaultCheckBoxTheme<>());
        manager.setComponentTheme(Component.class, new DefaultComponentTheme<>());
        manager.setComponentTheme(Container.class, new DefaultContainerTheme<>());
        manager.setComponentTheme(Controller.class, new DefaultControllerTheme<>());
//        manager.setComponentTheme(Dialog.class, new DefaultDialogTheme<>());
//        manager.setComponentTheme(ImageView.class, new DefaultImageViewTheme<>());
        manager.setComponentTheme(Label.class, new DefaultLabelTheme<>());
        manager.setComponentTheme(LayerContainer.class, new DefaultLayerContainerTheme<>());
//        manager.setComponentTheme(Panel.class, new DefaultPanelTheme<>());
        manager.setComponentTheme(ProgressBar.class, new DefaultProgressBarTheme<>());
        manager.setComponentTheme(ScrollablePanel.class, new DefaultScrollablePanelTheme<>());
        manager.setComponentTheme(RadioButton.class, new DefaultRadioButtonTheme<>());
        manager.setComponentTheme(ScrollBar.class, new DefaultScrollBarTheme<>());
        manager.setComponentTheme(SelectBox.class, new DefaultSelectBoxTheme<>());
        manager.setComponentTheme(SelectBox.SelectBoxScrollablePanel.class, new DefaultSelectBoxScrollablePanelTheme<>());
        manager.setComponentTheme(SelectBox.SelectBoxElement.class, new DefaultSelectBoxElementTheme<>());
        manager.setComponentTheme(Slider.class, new DefaultSliderTheme<>());
        manager.setComponentTheme(TextArea.class, new DefaultTextAreaTheme<>());
        manager.setComponentTheme(TextInput.class, new DefaultTextInputTheme<>());
        manager.setComponentTheme(ToggleButton.class, new DefaultToggleButtonTheme<>());
        manager.setComponentTheme(Tooltip.class, new DefaultTooltipTheme<>());
        manager.setComponentTheme(Widget.class, new DefaultWidgetTheme<>());
        return manager;
    }

    private static class DefaultComponentTheme<T extends Component> extends AbstractTheme<T> {
        @Override
        public void apply(T component) {
            component.setBorder(new SimpleLineBorder(ColorConstants.darkGray(), .7f));
            component.setCornerRadius(2);
            component.setBackgroundColor(ColorConstants.white());
        }
    }

    private static class DefaultRadioButtonTheme<T extends RadioButton> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.setBackgroundColor(ColorConstants.transparent());
            component.getTextState().setTextColor(ColorConstants.black());
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
            component.setIconUnchecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, 0xE836, ColorConstants.black()));
            component.setIconChecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, 0xE837, ColorConstants.black()));
            component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
            component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
        }
    }

    private static class DefaultButtonTheme<T extends Button> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.getTextState().setTextColor(ColorConstants.black());
        }
    }

    private static class DefaultCheckBoxTheme<T extends CheckBox> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.setBackgroundColor(ColorConstants.transparent());
            component.getTextState().setTextColor(ColorConstants.black());
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
            component.setIconUnchecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, 0xE835, ColorConstants.black()));
            component.setIconChecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, 0xE834, ColorConstants.black()));
            component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
            component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
        }
    }

    private static class DefaultLabelTheme<T extends Label> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.setBackgroundColor(ColorConstants.transparent());
            component.getTextState().setTextColor(ColorConstants.black());
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
        }
    }

    private static class DefaultSliderTheme<T extends Slider> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.setBackgroundColor(ColorConstants.transparent());
            component.setSliderColor(ColorConstants.gray());
            component.setSliderActiveColor(ColorConstants.blue());
        }
    }

    private static class DefaultContainerTheme<T extends Container> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
        }

        @Override
        public void applyAll(T component) {
            super.applyAll(component);
            List<? extends Component> childs = component.getChilds();
            for (Component child : childs) {
                Theme.getDefaultTheme().applyAll(child);
            }
        }
    }

    private static class DefaultTextInputTheme<T extends TextInput> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.getTextState().setTextColor(ColorConstants.black());
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
            component.getTextState().setHighlightColor(ColorConstants.lightBlue());
        }
    }

    private static class DefaultTextAreaTheme<T extends TextArea> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.getTextState().setTextColor(ColorConstants.black());
            component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
            component.getTextState().setHighlightColor(ColorConstants.lightBlue());
        }
    }


    private static class DefaultScrollBarTheme<T extends ScrollBar> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setArrowColor(ColorConstants.gray());
            component.setScrollColor(ColorConstants.gray());
            component.setArrowsEnabled(true);
        }
    }

    private static class DefaultWidgetTheme<T extends Widget> extends DefaultControllerTheme<T> {
        @Override
        public void applyAll(T component) {
            super.applyAll(component);
            component.getTitleContainer().setBackgroundColor(ColorConstants.lightGray());
            component.getMinimizeButton().setBackgroundColor(ColorConstants.lightGray());
            component.getMinimizeButton().getTextState().setTextColor(ColorConstants.black());
            component.getMinimizeButton().setBorder(null);
            component.getCloseButton().setBackgroundColor(ColorConstants.lightGray());
            component.getCloseButton().getTextState().setTextColor(ColorConstants.black());
            component.getCloseButton().setBorder(null);
            component.setTitleBackgroundColor(ColorConstants.lightGray());
            component.getTitleTextState().setTextColor(ColorConstants.black());

            Theme.getDefaultTheme().applyAll(component.getContainer());
        }
    }

    private static class DefaultControllerTheme<T extends Controller> extends DefaultComponentTheme<T> {
        @Override
        public void applyAll(T component) {
            super.applyAll(component);
            Tooltip tooltip = component.getTooltip();
            if (tooltip != null) {
                Theme.getDefaultTheme().applyAll(tooltip);
            }
        }
    }

    private static class DefaultTooltipTheme<T extends Tooltip> extends DefaultComponentTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(new SimpleLineBorder(new Vector4f(0, 0, 0, 0.9f), 1.2f));
            component.setBackgroundColor(new Vector4f(1f, 1f, 1f, 0.9f));
            component.getTextState().setTextColor(ColorConstants.black());
        }
    }

    private static class DefaultLayerContainerTheme<T extends LayerContainer> extends DefaultContainerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
        }
    }

    private static class DefaultProgressBarTheme<T extends ProgressBar> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setProgressColor(new Vector4f(0.6f, 0.8f, 0.7f, 1f));
        }
    }

    private static class DefaultScrollablePanelTheme<T extends ScrollablePanel> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            Container viewport = component.getViewport();
            Theme.getDefaultTheme().apply(viewport);
            Theme.getDefaultTheme().apply(component.getVerticalScrollBar());
            Theme.getDefaultTheme().apply(component.getHorizontalScrollBar());
            viewport.setBorder(null);
            viewport.setBackgroundColor(ColorConstants.transparent());
//            Theme.getDefaultTheme().applyAll(component.getContainer());
        }

        @Override
        public void applyAll(T component) {
            super.applyAll(component);
            Container viewport = component.getViewport();
            Theme.getDefaultTheme().applyAll(viewport);
            viewport.setBorder(null);
            viewport.setBackgroundColor(ColorConstants.transparent());
//            Theme.getDefaultTheme().applyAll(component.getContainer());
        }
    }

    private static class DefaultSelectBoxTheme<T extends SelectBox> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.getExpandButton().setBorder(null);
            component.getExpandButton().setBackgroundColor(ColorConstants.transparent());
            component.getExpandButton().getTextState().setTextColor(ColorConstants.black());
            component.getSelectionButton().setBorder(null);
            component.getSelectionButton().setBackgroundColor(ColorConstants.transparent());
            component.getSelectionButton().getTextState().setTextColor(ColorConstants.black());

            Theme.getDefaultTheme().applyAll(component.getSelectionListPanel());
        }

        @Override
        public void applyAll(T component) {
            super.applyAll(component);

            Theme.getDefaultTheme().applyAll(component.getSelectionListPanel());
        }
    }

    private static class DefaultSelectBoxScrollablePanelTheme<T extends SelectBox.SelectBoxScrollablePanel> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            Theme.getDefaultTheme().apply(component.getContainer());
        }

        @Override
        public void applyAll(T component) {
            super.applyAll(component);

            Theme.getDefaultTheme().applyAll(component.getContainer());
        }
    }

    private static class DefaultSelectBoxElementTheme<T extends SelectBox.SelectBoxElement> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBorder(null);
            component.setBackgroundColor(ColorConstants.transparent());
            component.getTextState().setTextColor(ColorConstants.black());
        }
    }

    private static class DefaultToggleButtonTheme<T extends ToggleButton> extends DefaultControllerTheme<T> {
        @Override
        public void apply(T component) {
            super.apply(component);
            component.setBackgroundColor(ColorConstants.red());
            component.setToggledBackgroundColor(ColorConstants.green());
        }
    }
}
