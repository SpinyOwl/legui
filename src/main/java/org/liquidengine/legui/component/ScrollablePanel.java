package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.theme.Themes;

/**
 * Panel with scroll bars.
 */
public class ScrollablePanel<T extends Component> extends Container implements Viewport {
    public static final float INITIAL_SCROLL_SIZE = 8f;
    /**
     * Used to scroll panel vertically.
     */
    private ScrollBar            verticalScrollBar;
    /**
     * Used to scroll panel horizontally.
     */
    private ScrollBar            horizontalScrollBar;
    /**
     * Base container which holds scroll bars and content panel.
     */
    private Container<Component> viewport;
    /**
     * Used to hold components added by user.
     */
    private Container<T>         container;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
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

    private void initialize() {

        float viewportWidth  = getSize().x - INITIAL_SCROLL_SIZE;
        float viewportHeight = getSize().y - INITIAL_SCROLL_SIZE;

        verticalScrollBar = new ScrollBar();
        verticalScrollBar.setPosition(viewportWidth, 0);
        verticalScrollBar.setSize(INITIAL_SCROLL_SIZE, viewportHeight);
        verticalScrollBar.setOrientation(Orientation.VERTICAL);
        verticalScrollBar.setViewport(this);

        horizontalScrollBar = new ScrollBar();
        horizontalScrollBar.setPosition(0, viewportHeight);
        horizontalScrollBar.setSize(viewportWidth, INITIAL_SCROLL_SIZE);
        horizontalScrollBar.setOrientation(Orientation.HORIZONTAL);
        horizontalScrollBar.setViewport(this);

        viewport = new Panel(0, 0, viewportWidth, viewportHeight);
        viewport.setBackgroundColor(1, 1, 1, 0);
        viewport.setBorder(null);


        container = new Panel(0, 0, viewportWidth, viewportHeight);
        container.setBorder(null);
        viewport.add(container);

        this.add(viewport);
        this.add(verticalScrollBar);
        this.add(horizontalScrollBar);
        this.setBackgroundColor(ColorConstants.transparent());
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(ScrollablePanel.class).applyAll(this);

        resize();
    }

    /**
     * Used to resize scrollable panel.
     */
    public void resize() {
        boolean horizontalScrollBarVisible = horizontalScrollBar.isVisible();
        boolean verticalScrollBarVisible   = verticalScrollBar.isVisible();

        Vector2f scrollablePanelSize = new Vector2f(getSize());
        Vector2f containerSize       = new Vector2f(container.getSize());
        Vector2f viewportSize        = new Vector2f(getSize());

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
        verticalScrollBar.setVisibleAmount(containerSize.x >= viewportSize.x ? (verticalRange * viewportSize.y / containerSize.y) : verticalRange);

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
    public Container getContainer() {
        return container;
    }

    /**
     * Used to set container which should used to add components to scrollable panel.
     *
     * @param container container which should used to add components to scrollable panel.
     */
    public void setContainer(Container container) {
        viewport.remove(this.container);
        this.container = container;
        viewport.add(this.container);
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ScrollablePanel)) return false;

        ScrollablePanel panel = (ScrollablePanel) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(verticalScrollBar, panel.verticalScrollBar)
                .append(horizontalScrollBar, panel.horizontalScrollBar)
                .append(viewport, panel.viewport)
                .append(container, panel.container)
                .isEquals();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("verticalScrollBar", verticalScrollBar)
                .append("horizontalScrollBar", horizontalScrollBar)
                .append("viewport", viewport)
                .append("container", container)
                .toString();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
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

    public Container<Component> getViewport() {
        return viewport;
    }
}
