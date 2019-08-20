package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.misc.animation.scrollablepanel.ScrollablePanelAnimation;
import org.liquidengine.legui.component.misc.listener.scrollablepanel.ScrollablePanelViewportScrollListener;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.length.Length;
import org.liquidengine.legui.theme.Themes;

import static org.liquidengine.legui.style.length.LengthType.pixel;

/**
 * Panel with scroll bars. Default container layout is null.
 */
public class ScrollablePanel extends Component implements Viewport {

    /**
     * Initial scrollbar width/height.
     */
    private static final float INITIAL_SCROLL_SIZE = 8f;

    /**
     * Used to scroll panel vertically.
     */
    private ScrollBar verticalScrollBar;

    /**
     * Used to scroll panel horizontally.
     */
    private ScrollBar horizontalScrollBar;

    /**
     * Base container which holds component container. Viewport size is limited by scroll bars and parent ScrollablePanel size.
     */
    private Component viewport;

    /**
     * Used to hold components added by user.
     */
    private Component container;
    /**
     * Scrollable panel animation. Updates container position in viewport.
     */
    private Animation animation;

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public ScrollablePanel() {
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
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
     * @param size     size of component.
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
        this.getStyle().setDisplay(DisplayType.FLEX);

        float viewportWidth = getSize().x - INITIAL_SCROLL_SIZE;
        float viewportHeight = getSize().y - INITIAL_SCROLL_SIZE;

        verticalScrollBar = new ScrollBar();
        verticalScrollBar.getStyle().setWidth(INITIAL_SCROLL_SIZE);
        verticalScrollBar.getStyle().setTop(0f);
        verticalScrollBar.getStyle().setRight(0f);
        verticalScrollBar.getStyle().setBottom(INITIAL_SCROLL_SIZE);
        verticalScrollBar.setOrientation(Orientation.VERTICAL);
        verticalScrollBar.setViewport(this);
        verticalScrollBar.setTabFocusable(false);

        horizontalScrollBar = new ScrollBar();
        horizontalScrollBar.getStyle().setHeight(INITIAL_SCROLL_SIZE);
        horizontalScrollBar.getStyle().setLeft(0f);
        horizontalScrollBar.getStyle().setRight(INITIAL_SCROLL_SIZE);
        horizontalScrollBar.getStyle().setBottom(0f);
        horizontalScrollBar.setOrientation(Orientation.HORIZONTAL);
        horizontalScrollBar.setViewport(this);
        horizontalScrollBar.setTabFocusable(false);

        viewport = new ScrollablePanelViewport(0, 0, viewportWidth, viewportHeight);

        viewport.getStyle().getBackground().setColor(1, 1, 1, 0);
        viewport.getStyle().setBorder(null);
        viewport.getStyle().setTop(0f);
        viewport.getStyle().setLeft(0f);
        viewport.getStyle().setBottom(INITIAL_SCROLL_SIZE);
        viewport.getStyle().setRight(INITIAL_SCROLL_SIZE);
        viewport.getListenerMap().addListener(ScrollEvent.class, new ScrollablePanelViewportScrollListener());
        viewport.setTabFocusable(false);

        container = new ScrollablePanelContainer(0, 0, viewportWidth, viewportHeight);

        container.getStyle().setBorder(null);
        container.setTabFocusable(false);
        viewport.add(container);

        this.add(viewport);
        this.add(verticalScrollBar);
        this.add(horizontalScrollBar);
        this.getStyle().getBackground().setColor(ColorConstants.transparent());

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(ScrollablePanel.class).applyAll(this);

        animation = new ScrollablePanelAnimation(this);
        animation.startAnimation();
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
    }

    public void setHorizontalScrollBarVisible(boolean enabled) {
        if (enabled) {
            Length height = this.horizontalScrollBar.getStyle().getHeight();
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
    }

    public void setVerticalScrollBarVisible(boolean enabled) {
        if (enabled) {
            Length width = this.verticalScrollBar.getStyle().getWidth();
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
    }

    public void setHorizontalScrollBarHeight(float height) {
        this.horizontalScrollBar.getStyle().setHeight(height);
        this.viewport.getStyle().setBottom(height);
        this.verticalScrollBar.getStyle().setBottom(height);
    }

    public void setVerticalScrollBarWidth(float width) {
        this.verticalScrollBar.getStyle().setWidth(width);
        this.viewport.getStyle().setRight(width);
        this.horizontalScrollBar.getStyle().setRight(width);
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
        return new Vector2f(container.getSize());
    }

    public static class ScrollablePanelViewport extends Panel {
        public ScrollablePanelViewport() {
        }

        public ScrollablePanelViewport(float x, float y, float width, float height) {
            super(x, y, width, height);
        }

        public ScrollablePanelViewport(Vector2f position, Vector2f size) {
            super(position, size);
        }
    }

    public static class ScrollablePanelContainer extends Panel {
        public ScrollablePanelContainer() {
        }

        public ScrollablePanelContainer(float x, float y, float width, float height) {
            super(x, y, width, height);
        }

        public ScrollablePanelContainer(Vector2f position, Vector2f size) {
            super(position, size);
        }
    }
}
