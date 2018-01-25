package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.layout.borderlayout.BorderLayout;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.component.misc.listener.scrollablepanel.ScrollablePanelViewportScrollListener;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.layout.borderlayout.BorderLayoutConstraint;
import org.liquidengine.legui.theme.Themes;

/**
 * Panel with scroll bars.
 * Default container layout is null.
 * <p>
 * TODO: REIMPLEMENT THIS COMPONENT ACCORDING TO NEW LAYOUT SYSTEM
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
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
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

    private void initialize() {
        this.setLayout(new BorderLayout());

        float viewportWidth = getSize().x - INITIAL_SCROLL_SIZE;
        float viewportHeight = getSize().y - INITIAL_SCROLL_SIZE;

        verticalScrollBar = new ScrollBar();
        verticalScrollBar.getStyle().setMaximumSize(INITIAL_SCROLL_SIZE, Float.MAX_VALUE);
        verticalScrollBar.getStyle().setMinimumSize(INITIAL_SCROLL_SIZE, 0);
        verticalScrollBar.setOrientation(Orientation.VERTICAL);
        verticalScrollBar.setViewport(this);
        verticalScrollBar.setTabFocusable(false);

        horizontalScrollBar = new ScrollBar();
        horizontalScrollBar.getStyle().setMaximumSize(Float.MAX_VALUE, INITIAL_SCROLL_SIZE);
        horizontalScrollBar.getStyle().setMinimumSize(0, INITIAL_SCROLL_SIZE);
        horizontalScrollBar.setOrientation(Orientation.HORIZONTAL);
        horizontalScrollBar.setViewport(this);
        horizontalScrollBar.setTabFocusable(false);

        viewport = new Panel(0, 0, viewportWidth, viewportHeight);

        viewport.getStyle().getBackground().setColor(1, 1, 1, 0);
        viewport.getStyle().setBorder(null);
        viewport.getListenerMap().addListener(ScrollEvent.class, new ScrollablePanelViewportScrollListener());
        viewport.setTabFocusable(false);

        container = new Panel(0, 0, viewportWidth, viewportHeight);

        container.getStyle().setBorder(null);
        container.setTabFocusable(false);
        viewport.add(container);

        this.add(viewport, BorderLayoutConstraint.CENTER);
        this.add(verticalScrollBar, BorderLayoutConstraint.RIGHT);
        this.add(horizontalScrollBar, BorderLayoutConstraint.BOTTOM);
        this.getStyle().getBackground().setColor(ColorConstants.transparent());

        container.getStyle().setBorder(null);
        container.setTabFocusable(false);
        viewport.add(container);

        this.add(viewport);
        this.add(verticalScrollBar);
        this.add(horizontalScrollBar);
        this.getStyle().getBackground().setColor(ColorConstants.transparent());

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(ScrollablePanel.class).applyAll(this);

        resize();
    }

    /**
     * Used to resize scrollable panel.
     */
    public void resize() {
        boolean horizontalScrollBarVisible = horizontalScrollBar.isVisible();
        boolean verticalScrollBarVisible = verticalScrollBar.isVisible();

        Vector2f scrollablePanelSize = new Vector2f(getSize());
        Vector2f containerSize = new Vector2f(container.getSize());
        Vector2f viewportSize = new Vector2f(getSize());

        if (horizontalScrollBarVisible) {
            horizontalScrollBar.getPosition().y = viewportSize.y = scrollablePanelSize.y - horizontalScrollBar.getSize().y;
        }
        if (verticalScrollBarVisible) {
            verticalScrollBar.getPosition().x = viewportSize.x = scrollablePanelSize.x - verticalScrollBar.getSize().x;
        }
        viewport.setSize(viewportSize);
        horizontalScrollBar.getSize().x = viewportSize.x;
        verticalScrollBar.getSize().y = viewportSize.y;
        float horizontalRange = horizontalScrollBar.getMaxValue() - horizontalScrollBar.getMinValue();
        horizontalScrollBar.setVisibleAmount(containerSize.x >= viewportSize.x ? (horizontalRange * viewportSize.x / containerSize.x) : horizontalRange);

        float verticalRange = verticalScrollBar.getMaxValue() - verticalScrollBar.getMinValue();
        verticalScrollBar.setVisibleAmount(containerSize.y >= viewportSize.y ? (verticalRange * viewportSize.y / containerSize.y) : verticalRange);

        updateViewport();
    }

    /**
     * Used to update container position in viewport.
     */
    public void updateViewport() {
        float vh = viewport.getSize().y;
        float ch = container.getSize().y;
        float newPosY;
        if (vh > ch) {
            newPosY = 0;
        } else {
            newPosY = (vh - ch) * verticalScrollBar.getCurValue() / (verticalScrollBar.getMaxValue() - verticalScrollBar.getMinValue());
        }

        float vw = viewport.getSize().x;
        float cw = container.getSize().x;
        float newPosX;
        if (vw > cw) {
            newPosX = 0;
        } else {
            newPosX = (vw - cw) * horizontalScrollBar.getCurValue() / (horizontalScrollBar.getMaxValue() - horizontalScrollBar.getMinValue());
        }
        container.setPosition(new Vector2f(newPosX, newPosY));
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
}
