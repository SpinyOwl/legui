package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.flex.FlexStyle;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.theme.Themes;

import java.util.HashMap;

// TODO: Style the tabs better
// TODO: Hide the nav bars when there aren't enough tabs to scroll
public class TabbedPanel extends Panel {
    private static final int INITIAL_TITLE_HEIGHT = 20;
    private static final int NAV_BUTTON_WIDTH = 10;
    private static final int ICON_SIZE = INITIAL_TITLE_HEIGHT * 2 / 3;
    private static final int TAB_BUTTON_WIDTH = 40;
    private static final int FORWARDS_ICON_CHAR = 0xF142;
    private static final int BACKWARDS_ICON_CHAR = 0xF141;

    private HashMap<RadioButton, Panel> panelHashMap = new HashMap<>();
    // A wrapper panel that contains a scroll panel for tabs and buttons
    private Panel titleWrapper = new Panel();
    // A panel that contains the tabs
    private ScrollablePanel tabWrapper = new ScrollablePanel();
    // A wrapper for the contents
    private Panel contentsWrapper = new Panel();
    // Only one tab can be active at a time, so they may as well be radio buttons
    private RadioButtonGroup tabButtonGroup = new RadioButtonGroup();
    // Navigation buttons
    private Button forwardsButton = new Button("");
    private Button backwardsButton = new Button("");

    private Icon forwardsIcon = new CharIcon(
            new Vector2f(ICON_SIZE),
            FontRegistry.MATERIAL_DESIGN_ICONS,
            (char) FORWARDS_ICON_CHAR,
            ColorConstants.black()
    );
    private Icon backwardsIcon = new CharIcon(
            new Vector2f(ICON_SIZE),
            FontRegistry.MATERIAL_DESIGN_ICONS,
            (char) BACKWARDS_ICON_CHAR,
            ColorConstants.black()
    );

    private float scrollStep = 6.0f;

    private boolean navButtons = false;

    public TabbedPanel() {
        this.initialize();
    }

    private void initialize() {
        this.getStyle().setDisplay(Style.DisplayType.FLEX);
        this.getStyle().getFlexStyle().setFlexDirection(FlexStyle.FlexDirection.COLUMN);
        this.getStyle().setMinWidth(50f);
        this.getStyle().setMinHeight(50f);
        this.getStyle().setPadding(0f);

        this.titleWrapper.setTabFocusable(false);
        this.titleWrapper.getStyle().setHeight((float) INITIAL_TITLE_HEIGHT);
        this.titleWrapper.getStyle().setMinHeight((float) INITIAL_TITLE_HEIGHT);
        this.titleWrapper.getStyle().setMaxHeight((float) INITIAL_TITLE_HEIGHT);
        this.add(titleWrapper);
        this.add(contentsWrapper);

        this.tabWrapper.setAutoResize(true);
        this.tabWrapper.setHorizontalScrollBarVisible(false);
        this.tabWrapper.setVerticalScrollBarVisible(false);
        this.tabWrapper.getContainer().getStyle().setDisplay(Style.DisplayType.FLEX);
        this.tabWrapper.getContainer().getStyle().getFlexStyle().setFlexDirection(FlexStyle.FlexDirection.ROW);

        this.titleWrapper.add(tabWrapper);

        backwardsIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        backwardsIcon.setVerticalAlign(VerticalAlign.MIDDLE);

        forwardsIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        forwardsIcon.setVerticalAlign(VerticalAlign.MIDDLE);

        this.setPanelFlex(titleWrapper.getStyle(), FlexStyle.FlexDirection.ROW);
        this.setPanelFlex(tabWrapper.getStyle(), FlexStyle.FlexDirection.ROW);
        this.setPanelFlex(contentsWrapper.getStyle(), FlexStyle.FlexDirection.COLUMN);

        forwardsButton.getStyle().getBackground().setIcon(forwardsIcon);
        backwardsButton.getStyle().getBackground().setIcon(backwardsIcon);

        forwardsButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event-> {
            this.tabWrapper.getHorizontalScrollBar().setCurValue(
                    this.tabWrapper.getHorizontalScrollBar().getCurValue() + scrollStep
            );
        });

        backwardsButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event-> {
            this.tabWrapper.getHorizontalScrollBar().setCurValue(
                    this.tabWrapper.getHorizontalScrollBar().getCurValue() - scrollStep
            );
        });

        this.setChildFlex(forwardsButton.getStyle(), NAV_BUTTON_WIDTH);
        this.setChildFlex(backwardsButton.getStyle(), NAV_BUTTON_WIDTH);

        this.addNavButtons();

        Themes.getDefaultTheme().applyAll(this);
    }

    private void setPanelFlex(Style style, FlexStyle.FlexDirection direction) {
        style.setDisplay(Style.DisplayType.FLEX);
        style.setPosition(Style.PositionType.RELATIVE);
        style.getFlexStyle().setFlexGrow(1);
        style.getFlexStyle().setFlexShrink(1);
        style.getFlexStyle().setFlexDirection(direction);
    }

    private void setChildFlex(Style style, int width) {
        style.setPosition(Style.PositionType.RELATIVE);
        style.getBackground().setColor(ColorConstants.transparent());
        style.setMaxWidth((float) width);
        style.setMaxHeight((float) INITIAL_TITLE_HEIGHT);
        style.setMinWidth((float) width);
        style.setMinHeight((float) INITIAL_TITLE_HEIGHT);
        style.setWidth((float) width);
        style.setHeight((float) INITIAL_TITLE_HEIGHT);
        style.getFlexStyle().setFlexGrow(1);
        style.getFlexStyle().setFlexShrink(1);
    }

    public Panel addTab() {
        return this.addTab(new Panel());
    }

    public Panel addTab(Panel panel) {
        return this.addTab(String.valueOf(panelHashMap.keySet().size()), panel);
    }

    public Panel addTab(String name, Panel panel) {
        RadioButton button = new RadioButton(name);
        button.setRadioButtonGroup(tabButtonGroup);
        tabButtonGroup.setSelection(button, true);

        button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event-> {
            this.contentsWrapper.clearChildComponents();
            this.contentsWrapper.add(panel);
        });

        this.setChildFlex(button.getStyle(), TAB_BUTTON_WIDTH);
        this.setPanelFlex(panel.getStyle(), FlexStyle.FlexDirection.COLUMN);

        this.panelHashMap.put(button, panel);
        this.tabWrapper.getContainer().add(button);

        // They have to be hidden/shown because there isn't a selection event causer
        this.contentsWrapper.clearChildComponents();
        this.contentsWrapper.add(panel);

        return panel;
    }

    public void addNavButtons() {
        this.titleWrapper.add(backwardsButton);
        this.titleWrapper.add(forwardsButton);
    }

    public void removeNavButtons() {
        this.titleWrapper.remove(backwardsButton);
        this.titleWrapper.remove(forwardsButton);
    }
}
