package com.spinyowl.legui.component;

import static com.spinyowl.legui.component.TabbedPanel.TabStripPosition.BOTTOM;
import static com.spinyowl.legui.component.TabbedPanel.TabStripPosition.RIGHT;
import static com.spinyowl.legui.component.TabbedPanel.TabStripPosition.TOP;
import static com.spinyowl.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static com.spinyowl.legui.style.Style.DisplayType.FLEX;
import static com.spinyowl.legui.style.Style.PositionType.RELATIVE;
import static com.spinyowl.legui.style.flex.FlexStyle.FlexDirection.COLUMN;
import static com.spinyowl.legui.style.flex.FlexStyle.FlexDirection.COLUMN_REVERSE;
import static com.spinyowl.legui.style.flex.FlexStyle.FlexDirection.ROW;
import static com.spinyowl.legui.style.flex.FlexStyle.FlexDirection.ROW_REVERSE;

import com.spinyowl.legui.component.event.button.ButtonWidthChangeEvent;
import com.spinyowl.legui.component.misc.listener.button.UpdateButtonStyleWidthListener;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.flex.FlexStyle.AlignItems;
import com.spinyowl.legui.style.flex.FlexStyle.JustifyContent;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Implementation of tabbed panel.
 *
 * <pre>
 *   |-------------------------|
 *   | active tab 1 | tab 2... │ -- tab strip
 *   |-------------------------|
 *   |                         |
 *   |    Content of tab 1     | -- container
 *   |                         |
 *   |-------------------------|
 * </pre>
 */
public class TabbedPanel extends Component {

  /** Default tab height. */
  private static final float DEFAULT_TAB_HEIGHT = 30F;
  /** Default tab width. */
  private static final float DEFAULT_TAB_WIDTH = 90f;

  /** Container that used to hold tab content. */
  private Component container;
  /** Tab strip that holds all tabs. */
  private TabStrip tabStrip;

  /** List of all added tabs to tabbed panel. */
  private final List<Tab> tabs = new CopyOnWriteArrayList<>();
  /** Map of tab buttons. Used to hold tab to tab button relation. */
  private final Map<Tab, ToggleButton> tabButtons = new ConcurrentHashMap<>();

  /** Current tab width. */
  private float tabWidth;
  /** Current tab height. */
  private float tabHeight;
  /** Tab strip position in tabbed panel. */
  private TabStripPosition tabStripPosition;

  private boolean toggleNewTabActive = true;

  /**
   * Creates default tabbed panel with scrollable tab strip. Strip position will be top, tab height
   * - 28px, tab width - 60px.
   *
   * <p>Scrollable tab strip subtracts space required for scroll bar depending on strip position
   * from tab width or tab height.
   */
  public TabbedPanel() {
    this(TOP, DEFAULT_TAB_WIDTH, DEFAULT_TAB_HEIGHT);
  }

  /**
   * Creates tabbed panel with default
   *
   * @param tabStripPosition tab strip position - position where tab strip should be placed.
   * @param tabWidth minimum tab width.
   * @param tabHeight minimum tab height.
   */
  public TabbedPanel(TabStripPosition tabStripPosition, float tabWidth, float tabHeight) {
    this.tabStripPosition = tabStripPosition;
    this.tabWidth = tabWidth;
    this.tabHeight = tabHeight;
    initialize();
  }

  private void initialize() {
    tabStrip = new ScrollTabStrip(this, tabStripPosition, tabWidth, tabHeight);
    this.add(tabStrip);

    createContainer();
    this.add(container);

    updateStyles();
  }

  private void updateStyles() {
    Style style = this.getStyle();
    style.setDisplay(FLEX);
    style.setMinWidth(50f);
    style.setMinHeight(50f);
    style.setPadding(0f);
    style.getFlexStyle().setAlignItems(AlignItems.STRETCH);

    if (TOP == tabStripPosition) {
      style.getFlexStyle().setFlexDirection(COLUMN);
    } else if (BOTTOM == tabStripPosition) {
      style.getFlexStyle().setFlexDirection(COLUMN_REVERSE);
    } else if (RIGHT == tabStripPosition) {
      style.getFlexStyle().setFlexDirection(ROW_REVERSE);
    } else {
      style.getFlexStyle().setFlexDirection(ROW);
    }
    setStripStyles();
  }

  private void setStripStyles() {
    tabStrip.setFocusable(false);
    tabStrip.getStyle().setPosition(RELATIVE);
    tabStrip.getStyle().getFlexStyle().setFlexGrow(1);
    tabStrip.getStyle().getFlexStyle().setFlexShrink(1);
    tabStrip.getStyle().getBackground().setColor(ColorConstants.lightGray());
    if (TOP.equals(tabStripPosition) || BOTTOM.equals(tabStripPosition)) {
      tabStrip.getStyle().setWidth(null);
      tabStrip.getStyle().setMinWidth(null);
      tabStrip.getStyle().setMaxWidth(null);

      tabStrip.getStyle().setHeight(tabHeight);
      tabStrip.getStyle().setMinHeight(tabHeight);
      tabStrip.getStyle().setMaxHeight(tabHeight);
    } else {
      tabStrip.getStyle().setWidth(tabWidth);
      tabStrip.getStyle().setMinWidth(tabWidth);
      tabStrip.getStyle().setMaxWidth(tabWidth);

      tabStrip.getStyle().setHeight(null);
      tabStrip.getStyle().setMinHeight(null);
      tabStrip.getStyle().setMaxHeight(null);
    }
    tabStrip.updateStyles();
    for (ToggleButton button : tabButtons.values()) {
      tabStrip.updateTabButtonStyles(button);
    }
  }

  private void createContainer() {
    container = new Panel();
    container.setTabFocusable(false);
    container.getStyle().getFlexStyle().setFlexShrink(1);
    container.getStyle().getFlexStyle().setFlexGrow(1);
    container.getStyle().setPosition(RELATIVE);
    container.getStyle().setDisplay(FLEX);
  }

  /**
   * Used to add tab to tabbed panel. After tab added it becomes active if {@link
   * #toggleNewTabActive} is set to true (default = true).
   *
   * @param tab tab to add.
   */
  public void addTab(Tab tab) {
    Objects.requireNonNull(tab);

    tabs.add(tab);
    // add tab toggle button
    ToggleButton tabButton = createTabButton(tab);
    tabButtons.put(tab, tabButton);
    tabStrip.addTabButton(tabButton);
    tabStrip.updateStyles();
    for (ToggleButton button : tabButtons.values()) {
      tabStrip.updateTabButtonStyles(button);
    }
    // add to tab content to container
    tab.tabComponent.getStyle().getFlexStyle().setFlexGrow(1);
    tab.tabComponent.getStyle().setPosition(RELATIVE);

    if (toggleNewTabActive) {
      setActiveTab(tab);
    }
  }

  /**
   * Used to set specified tab active.
   *
   * @param tab tab to activate.
   */
  public void setActiveTab(Tab tab) {
    Objects.requireNonNull(tab);
    if (tabs.contains(tab)) {
      tabButtons.forEach(
          (t, button) -> {
            container.remove(t.tabComponent);
            button.setToggled(false);
          });
      tabButtons.get(tab).setToggled(true);
      container.add(tab.tabComponent);
    }
  }

  /**
   * Used to update tab width.
   *
   * @param tabWidth new tab width.
   */
  public void setTabWidth(int tabWidth) {
    this.tabWidth = tabWidth;
    this.tabStrip.setTabWidth(tabWidth);
    updateStyles();
  }

  /**
   * Used to update tab height.
   *
   * @param tabHeight new tab height.
   */
  public void setTabHeight(float tabHeight) {
    this.tabHeight = tabHeight;
    this.tabStrip.setTabHeight(tabHeight);
    updateStyles();
  }

  /**
   * Used to update tab strip position.
   *
   * @param tabStripPosition new tab strip position.
   */
  public void setTabStripPosition(TabStripPosition tabStripPosition) {
    this.tabStripPosition = tabStripPosition;
    tabStrip.setTabStripPosition(tabStripPosition);
    updateStyles();
  }

  /**
   * Used to get tab strip position.
   *
   * @return tab strip position.
   */
  public TabStripPosition getTabStripPosition() {
    return tabStripPosition;
  }

  /**
   * Used to create tab button based on provided tab.
   *
   * @param tab tab to use for tab button creation.
   * @return returns newly created tab button with needed logic.
   */
  private ToggleButton createTabButton(Tab tab) {
    ToggleButton tabButton = new ToggleButton(tab.tabName);
    tabButton
        .getListenerMap()
        .addListener(ButtonWidthChangeEvent.class, new UpdateButtonStyleWidthListener());
    tabStrip.updateTabButtonStyles(tabButton);
    tabButton
        .getListenerMap()
        .addListener(
            MouseClickEvent.class,
            event -> {
              if (CLICK.equals(event.getAction())) {
                // we have updated state here (so actually before click it was not toggled).
                if (tabButton.isToggled()) {
                  tabButtons.values().stream()
                      .filter(b -> b != tabButton)
                      .forEach(b -> b.setToggled(false));
                } else {
                  tabButton.setToggled(true);
                }
                setActiveTab(tab);
              }
            });
    return tabButton;
  }

  /**
   * Returns count of tabs.
   *
   * @return count of tabs.
   */
  public int tabCount() {
    return tabs.size();
  }

  /**
   * Returns tab by index.
   *
   * @param index tab index to retrieve.
   * @return tab by index.
   */
  public Tab getTab(int index) {
    return tabs.get(index);
  }

  /**
   * Returns currently active tab.
   *
   * @return currently active tab.
   */
  public Tab getCurrentTab() {
    return tabButtons.entrySet().stream()
        .filter(e -> e.getValue().isToggled())
        .findFirst()
        .map(Entry::getKey)
        .orElse(null);
  }

  /**
   * Removes tab by index.
   *
   * @param index tab index to remove.
   */
  public void removeTab(int index) {
    Tab tabToRemove = tabs.get(index);
    this.removeTab(tabToRemove);
  }

  /**
   * Removes specified tab.
   *
   * @param tabToRemove tab to remove.
   */
  public void removeTab(Tab tabToRemove) {
    Objects.requireNonNull(tabToRemove);
    int index = tabs.indexOf(tabToRemove);
    tabs.remove(tabToRemove);
    ToggleButton button = tabButtons.remove(tabToRemove);

    // remove button from tab strip
    tabStrip.removeTabButton(button);
    tabStrip.updateStyles();

    // remove content from container
    container.remove(tabToRemove.tabComponent);

    if (!tabs.isEmpty()) {
      if (index < tabs.size()) {
        setActiveTab(tabs.get(index));
      } else {
        setActiveTab(tabs.get(index - 1));
      }
    }
  }

  /**
   * Returns tab strip.
   *
   * @return tab strip.
   */
  public TabStrip getTabStrip() {
    return tabStrip;
  }

  /**
   * Returns container.
   *
   * @return container.
   */
  public Component getContainer() {
    return container;
  }

  /**
   * Returns if `toggle new tab active` feature enabled.
   *
   * @return true if feature enabled.
   */
  public boolean isToggleNewTabActive() {
    return toggleNewTabActive;
  }

  /**
   * Used to set feature `toggle new tab active` enabled or disabled.
   *
   * @param toggleNewTabActive new flag.
   */
  public void setToggleNewTabActive(boolean toggleNewTabActive) {
    this.toggleNewTabActive = toggleNewTabActive;
  }

  /** Tab strip abstraction. Defines basic methods that tab strip should provide. */
  public abstract static class TabStrip extends Component {

    /** Used to update tab strip position. As well should update styles. */
    public abstract void setTabStripPosition(TabStripPosition tabStripPosition);

    /** Used to add new tab button to tab strip. */
    public abstract void addTabButton(Button tabButton);

    /** Used to remove tab button from tab strip. */
    public abstract void removeTabButton(Button tabButton);

    /** Used to update tab strip styles. */
    public abstract void updateStyles();

    /** Used to update tab button styles. */
    public abstract void updateTabButtonStyles(Button tabButton);

    /** Used to update tab width. */
    public abstract void setTabWidth(float tabSize);

    /** Used to update tab height. */
    public abstract void setTabHeight(float tabSize);
  }

  public static class ScrollTabStrip extends TabStrip {
    private final TabbedPanel tabbedPanel;
    private ScrollablePanel scrollablePanel;
    private TabStripPosition tabStripPosition;
    private float tabWidth;
    private float tabHeight;

    public ScrollTabStrip(
        TabbedPanel tabbedPanel,
        TabStripPosition tabStripPosition,
        float tabWidth,
        float tabHeight) {
      this.tabbedPanel = tabbedPanel;
      this.tabStripPosition = tabStripPosition;
      this.tabWidth = tabWidth;
      this.tabHeight = tabHeight;
      initialize();
    }

    private void initialize() {
      this.getStyle().setDisplay(FLEX);

      scrollablePanel = new TabStripScrollablePanel();
      scrollablePanel.getStyle().setPosition(RELATIVE);
      scrollablePanel.getStyle().getFlexStyle().setFlexShrink(1);
      scrollablePanel.getStyle().getFlexStyle().setFlexGrow(1);
      scrollablePanel.getStyle().getBackground().setColor(ColorConstants.transparent());
      scrollablePanel.getStyle().setBorder(null);
      scrollablePanel.getContainer().getStyle().setBorder(null);
      scrollablePanel
          .getViewport()
          .getStyle()
          .getBackground()
          .setColor(ColorConstants.white().mul(1, 1, 1, 0.5f));

      this.add(scrollablePanel);
      updateStyles();
    }

    public TabStripPosition getTabStripPosition() {
      return tabStripPosition;
    }

    public void setTabStripPosition(TabStripPosition tabStripPosition) {
      this.tabStripPosition = tabStripPosition;
      this.updateStyles();
    }

    public void updateStyles() {
      Component container = scrollablePanel.getContainer();
      Style containerStyle = container.getStyle();
      containerStyle.setDisplay(FLEX);
      containerStyle.getBackground().setColor(ColorConstants.transparent());
      containerStyle.getFlexStyle().setJustifyContent(JustifyContent.FLEX_START);
      float width;
      float height;
      if (tabStripPosition == BOTTOM || tabStripPosition == TOP) {
        scrollablePanel.setHorizontalScrollBarVisible(true);
        scrollablePanel.setVerticalScrollBarVisible(false);
        width = Math.max(container.count() * tabWidth, tabbedPanel.getSize().x);
        height = tabHeight - scrollablePanel.getHorizontalScrollBar().getSize().y;
        containerStyle.getFlexStyle().setFlexDirection(ROW);
      } else {
        scrollablePanel.setHorizontalScrollBarVisible(false);
        scrollablePanel.setVerticalScrollBarVisible(true);
        width = tabWidth - scrollablePanel.getVerticalScrollBar().getSize().x;
        height = Math.max(container.count() * tabHeight, tabbedPanel.getSize().y);
        containerStyle.getFlexStyle().setFlexDirection(COLUMN);
      }
      container.setSize(width, height);
      container.getStyle().setWidth(width);
      container.getStyle().setHeight(height);
      for (Button tab : tabbedPanel.tabButtons.values()) {
        updateTabButtonStyles(tab);
      }
    }

    public void updateTabButtonStyles(Button tabButton) {
      tabButton.getStyle().getFlexStyle().setFlexGrow(1);
      tabButton.getStyle().getFlexStyle().setFlexShrink(0);
      tabButton.getStyle().setPosition(RELATIVE);
      tabButton.getStyle().setPadding(2F, 4F);
      if (tabStripPosition == TOP || tabStripPosition == BOTTOM) {
        tabButton.getStyle().setMinWidth(tabWidth);
        tabButton.getStyle().setWidth(tabWidth);
        tabButton.getStyle().setMaxWidth(tabWidth);
        tabButton.getStyle().setMinHeight(null);
        tabButton.getStyle().setHeight(null);
        tabButton.getStyle().setMaxHeight(null);
      } else if (tabStripPosition == RIGHT || tabStripPosition == TabStripPosition.LEFT) {
        tabButton.getStyle().setMinWidth(null);
        tabButton.getStyle().setWidth(null);
        tabButton.getStyle().setMaxWidth(null);
        tabButton.getStyle().setMinHeight(tabHeight);
        tabButton.getStyle().setHeight(tabHeight);
        tabButton.getStyle().setMaxHeight(tabHeight);
      }
    }

    public void setTabWidth(float tabWidth) {
      this.tabWidth = tabWidth;
    }

    public void addTabButton(Button tabButton) {
      scrollablePanel.getContainer().add(tabButton);
    }

    public void removeTabButton(Button tabButton) {
      scrollablePanel.getContainer().remove(tabButton);
    }

    public void setTabHeight(float tabHeight) {
      this.tabHeight = tabHeight;
    }

    private static final class TabStripScrollablePanel extends ScrollablePanel {}
  }

  /** Tab that used to add tabs to tabbed panel. */
  public static class Tab {
    /** Unique identifier of tab. */
    private final String uid = UUID.randomUUID().toString();
    /** Tab name. */
    private final String tabName;
    /** Tab component. */
    private final Component tabComponent;

    public Tab(String tabName, Component tabComponent) {
      this.tabName = tabName;
      this.tabComponent = tabComponent;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Tab tab = (Tab) o;

      return new EqualsBuilder().append(uid, tab.uid).isEquals();
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder(17, 37).append(uid).toHashCode();
    }
  }

  public enum TabStripPosition {
    TOP,
    RIGHT,
    BOTTOM,
    LEFT
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TabbedPanel that = (TabbedPanel) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(tabWidth, that.tabWidth)
        .append(tabHeight, that.tabHeight)
        .append(container, that.container)
        .append(tabStrip, that.tabStrip)
        .append(tabs, that.tabs)
        .append(tabButtons, that.tabButtons)
        .append(tabStripPosition, that.tabStripPosition)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(container)
        .append(tabStrip)
        .append(tabs)
        .append(tabButtons)
        .append(tabWidth)
        .append(tabHeight)
        .append(tabStripPosition)
        .toHashCode();
  }
}
