package com.spinyowl.legui.component;

import static com.spinyowl.legui.style.length.LengthType.pixel;

import com.spinyowl.legui.animation.Animation;
import com.spinyowl.legui.component.misc.animation.scrollablepanel.ScrollablePanelAnimation;
import com.spinyowl.legui.component.misc.listener.scrollablepanel.ScrollablePanelViewportScrollListener;
import com.spinyowl.legui.component.optional.Orientation;
import com.spinyowl.legui.event.AddChildEvent;
import com.spinyowl.legui.event.RemoveChildEvent;
import com.spinyowl.legui.event.ScrollEvent;
import com.spinyowl.legui.style.Style.DisplayType;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.flex.FlexStyle.AlignItems;
import com.spinyowl.legui.style.length.Unit;
import com.spinyowl.legui.theme.Themes;
import com.spinyowl.legui.util.Utilites;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;

/** Panel with scroll bars. Default container layout is null. */
public class ScrollablePanel extends Component implements Viewport {

  /** Initial scrollbar width/height. */
  private static final float INITIAL_SCROLL_SIZE = 8f;

  /** Used to scroll panel vertically. */
  private ScrollBar verticalScrollBar;

  /** Used to scroll panel horizontally. */
  private ScrollBar horizontalScrollBar;

  /**
   * Base container which holds component container. Viewport size is limited by scroll bars and
   * parent ScrollablePanel size.
   */
  private Component viewport;

  /** Used to hold components added by user. */
  private Component container;
  /** Scrollable panel animation. Updates container position in viewport. */
  private Animation animation;

  private boolean autoResize = false;

  /**
   * Default constructor. Used to create component instance without any parameters.
   *
   * <p>Also if you want to make it easy to use with Json marshaller/unmarshaller component should
   * contain empty constructor.
   */
  public ScrollablePanel() {
    initialize();
  }

  /**
   * Constructor with position and size parameters.
   *
   * @param x x position position in parent component.
   * @param y y position position in parent component.
   * @param width width of component.
   * @param height height of component.
   */
  public ScrollablePanel(float x, float y, float width, float height) {
    super(x, y, width, height);
    initialize();
  }

  /**
   * Constructor with position and size parameters.
   *
   * @param position position position in parent component.
   * @param size size of component.
   */
  public ScrollablePanel(Vector2f position, Vector2f size) {
    super(position, size);
    initialize();
  }

  /**
   * Returns animation of scrollable panel.
   *
   * @return animation.
   */
  public Animation getAnimation() {
    return animation;
  }

  /**
   * Used to set scrollable panel animation. Automatically starts animation.
   *
   * @param animation scroll bar animation to set.
   */
  public void setAnimation(Animation animation) {
    if (this.animation != null) {
      this.animation.stopAnimation();
    }
    this.animation = animation;
    if (animation != null) {
      this.animation.startAnimation();
    }
  }

  private void initialize() {
    container = new ScrollablePanelContainer();
    container.setTabFocusable(false);
    container.getListenerMap().addListener(AddChildEvent.class, e -> autoRecalculateSize());
    container.getListenerMap().addListener(RemoveChildEvent.class, e -> autoRecalculateSize());

    viewport = new ScrollablePanelViewport(this);
    viewport.add(container);
    viewport
        .getListenerMap()
        .addListener(ScrollEvent.class, new ScrollablePanelViewportScrollListener());
    viewport.setTabFocusable(false);

    this.add(viewport);

    autoResize = true;
    autoResize = false;

    verticalScrollBar = new ScrollBar();
    horizontalScrollBar = new ScrollBar();

    this.add(verticalScrollBar);
    this.add(horizontalScrollBar);

    verticalScrollBar.setViewport(this);
    verticalScrollBar.setOrientation(Orientation.VERTICAL);
    verticalScrollBar.setTabFocusable(false);

    horizontalScrollBar.setViewport(this);
    horizontalScrollBar.setOrientation(Orientation.HORIZONTAL);
    horizontalScrollBar.setTabFocusable(false);

    applyStyles();
    autoRecalculateSize();

    animation = new ScrollablePanelAnimation(this);
    animation.startAnimation();

    Themes.getDefaultTheme()
        .getThemeManager()
        .getComponentTheme(ScrollablePanel.class)
        .applyAll(this);
  }

  private void autoRecalculateSize() {
    if (autoResize) {
      recalculateSize();
    }
  }

  public void recalculateSize() {
    Vector2f viewportSize = calculateViewportSize();

    for (Component childComponent : container.getChildComponents()) {
      float right = childComponent.getPosition().x + childComponent.getSize().x;
      float bottom = childComponent.getPosition().y + childComponent.getSize().y;
      if (right > viewportSize.x) {
        viewportSize.x = right;
      }
      if (bottom > viewportSize.y) {
        viewportSize.y = bottom;
      }
    }
    container.setSize(viewportSize);
  }

  private void applyStyles() {
    this.getStyle().setDisplay(DisplayType.FLEX);
    this.getStyle().getFlexStyle().setAlignItems(AlignItems.STRETCH);

    setVStyles();
    setHStyles();
    setViewportStyles();

    container.getStyle().setBorder(null);

    this.getStyle().getBackground().setColor(ColorConstants.transparent());
  }

  private void setVStyles() {
    verticalScrollBar.getStyle().setWidth(INITIAL_SCROLL_SIZE);
    verticalScrollBar.getStyle().setTop(0f);
    verticalScrollBar.getStyle().setRight(0f);
    verticalScrollBar.getStyle().setBottom(INITIAL_SCROLL_SIZE);
    verticalScrollBar.getStyle().getFlexStyle().setFlexShrink(0);
    verticalScrollBar.getStyle().getFlexStyle().setFlexGrow(0);
  }

  private void setHStyles() {
    horizontalScrollBar.getStyle().setHeight(INITIAL_SCROLL_SIZE);
    horizontalScrollBar.getStyle().setLeft(0f);
    horizontalScrollBar.getStyle().setRight(INITIAL_SCROLL_SIZE);
    horizontalScrollBar.getStyle().setBottom(0f);
    horizontalScrollBar.getStyle().getFlexStyle().setFlexShrink(0);
    horizontalScrollBar.getStyle().getFlexStyle().setFlexGrow(0);
  }

  private void setViewportStyles() {
    viewport.getStyle().getBackground().setColor(1, 1, 1, 0);
    viewport.getStyle().setBorder(null);
    viewport.getStyle().setTop(0f);
    viewport.getStyle().setLeft(0f);
    viewport.getStyle().setBottom(INITIAL_SCROLL_SIZE);
    viewport.getStyle().setRight(INITIAL_SCROLL_SIZE);
  }

  private Vector2f calculateViewportSize() {
    Vector2f size = getSize();
    float viewportWidth =
        size.x - (isVerticalScrollBarVisible() ? verticalScrollBar.getSize().x : 0f);
    float viewportHeight =
        size.y - (isHorizontalScrollBarVisible() ? horizontalScrollBar.getSize().y : 0f);
    return new Vector2f(viewportWidth, viewportHeight);
  }

  @Override
  public void setSize(float width, float height) {
    super.setSize(width, height);
  }

  @Override
  public void setSize(Vector2f size) {
    super.setSize(size);
  }

  /**
   * Returns vertical scrollbar.
   *
   * @return vertical scrollbar.
   */
  public ScrollBar getVerticalScrollBar() {
    return verticalScrollBar;
  }

  /**
   * Used to set vertical scroll bar.
   *
   * @param verticalScrollBar vertical scroll bar to set.
   */
  public void setVerticalScrollBar(ScrollBar verticalScrollBar) {
    this.verticalScrollBar.setViewport(null);
    this.remove(this.verticalScrollBar);
    this.verticalScrollBar = verticalScrollBar;
    this.add(verticalScrollBar);
    this.verticalScrollBar.setViewport(this);
    autoRecalculateSize();
  }

  /**
   * Returns horizontal scrollbar.
   *
   * @return horizontal scrollbar.
   */
  public ScrollBar getHorizontalScrollBar() {
    return horizontalScrollBar;
  }

  /**
   * Used to set horizontal scroll bar.
   *
   * @param horizontalScrollBar horizontal scroll bar to set.
   */
  public void setHorizontalScrollBar(ScrollBar horizontalScrollBar) {
    this.horizontalScrollBar.setViewport(null);
    this.remove(this.horizontalScrollBar);
    this.horizontalScrollBar = horizontalScrollBar;
    this.add(horizontalScrollBar);
    this.horizontalScrollBar.setViewport(this);
    autoRecalculateSize();
  }

  public boolean isHorizontalScrollBarVisible() {
    return horizontalScrollBar.getStyle().getDisplay() != DisplayType.NONE;
  }

  public void setHorizontalScrollBarVisible(boolean enabled) {
    if (enabled) {
      Unit height = this.horizontalScrollBar.getStyle().getHeight();
      if (height == null) {
        height = pixel(this.horizontalScrollBar.getSize().y);
        this.horizontalScrollBar.getStyle().setHeight(height);
      }
      this.viewport.getStyle().setBottom(height);
      this.verticalScrollBar.getStyle().setBottom(height);
    } else {
      this.viewport.getStyle().setBottom(0f);
      this.verticalScrollBar.getStyle().setBottom(0f);
    }
    this.horizontalScrollBar.getStyle().setDisplay(enabled ? DisplayType.MANUAL : DisplayType.NONE);
    autoRecalculateSize();
  }

  public boolean isVerticalScrollBarVisible() {
    return verticalScrollBar.getStyle().getDisplay() != DisplayType.NONE;
  }

  public void setVerticalScrollBarVisible(boolean enabled) {
    if (enabled) {
      Unit width = this.verticalScrollBar.getStyle().getWidth();
      if (width == null) {
        width = pixel(this.verticalScrollBar.getSize().x);
        this.verticalScrollBar.getStyle().setWidth(width);
      }
      this.viewport.getStyle().setRight(width);
      this.horizontalScrollBar.getStyle().setRight(width);
    } else {
      this.viewport.getStyle().setRight(0f);
      this.horizontalScrollBar.getStyle().setRight(0f);
    }
    this.verticalScrollBar.getStyle().setDisplay(enabled ? DisplayType.MANUAL : DisplayType.NONE);
    autoRecalculateSize();
  }

  public void setHorizontalScrollBarHeight(float height) {
    this.horizontalScrollBar.getStyle().setHeight(height);
    this.viewport.getStyle().setBottom(height);
    this.verticalScrollBar.getStyle().setBottom(height);
    autoRecalculateSize();
  }

  public void setVerticalScrollBarWidth(float width) {
    this.verticalScrollBar.getStyle().setWidth(width);
    this.viewport.getStyle().setRight(width);
    this.horizontalScrollBar.getStyle().setRight(width);
    autoRecalculateSize();
  }

  /**
   * Returns container which should used to add components to scrollable panel.
   *
   * @return container which should used to add components to scrollable panel.
   */
  public Component getContainer() {
    return container;
  }

  /**
   * Used to set container which should used to add components to scrollable panel.
   *
   * @param container container which should used to add components to scrollable panel.
   */
  public void setContainer(Component container) {
    viewport.remove(this.container);
    this.container = container;
    viewport.add(this.container);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ScrollablePanel)) {
      return false;
    }

    ScrollablePanel panel = (ScrollablePanel) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(verticalScrollBar, panel.verticalScrollBar)
        .append(horizontalScrollBar, panel.horizontalScrollBar)
        .append(viewport, panel.viewport)
        .append(container, panel.container)
        .isEquals();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("verticalScrollBar", verticalScrollBar)
        .append("horizontalScrollBar", horizontalScrollBar)
        .append("viewport", viewport)
        .append("container", container)
        .toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(verticalScrollBar)
        .append(horizontalScrollBar)
        .append(viewport)
        .append(container)
        .toHashCode();
  }

  public Component getViewport() {
    return viewport;
  }

  @Override
  public Vector2f getViewportSize() {
    return new Vector2f(viewport.getSize());
  }

  @Override
  public Vector2f getViewportViewSize() {
    //    Vector2f viewportViewSize = viewport.getSize();
    //    container.getChildComponents().stream()
    //        .filter(Component::isVisible)
    //        .filter(Utilites::visibleInParents)
    //        .map(component -> new Vector2f(component.getPosition()).add(component.getSize()))
    //        .forEach(viewportViewSize::max);
    //
    //    return viewportViewSize;
    return new Vector2f(container.getSize());
  }

  public boolean isAutoResize() {
    return autoResize;
  }

  public void setAutoResize(boolean autoResize) {
    this.autoResize = autoResize;
  }

  public static class ScrollablePanelViewport extends Panel {

    private ScrollablePanel scrollablePanel;

    public ScrollablePanelViewport() {}

    public ScrollablePanelViewport(float x, float y, float width, float height) {
      super(x, y, width, height);
    }

    public ScrollablePanelViewport(Vector2f position, Vector2f size) {
      super(position, size);
    }

    public ScrollablePanelViewport(ScrollablePanel scrollablePanel) {
      this.scrollablePanel = scrollablePanel;
    }

    @Override
    public void setSize(Vector2f size) {
      super.setSize(size);
//      scrollablePanel.recalculateSize();
    }

    @Override
    public void setSize(float width, float height) {
      super.setSize(width, height);
//      scrollablePanel.recalculateSize();
    }
  }

  public static class ScrollablePanelContainer extends Panel {

    public ScrollablePanelContainer() {}

    public ScrollablePanelContainer(float x, float y, float width, float height) {
      super(x, y, width, height);
    }

    public ScrollablePanelContainer(Vector2f position, Vector2f size) {
      super(position, size);
    }
  }
}
