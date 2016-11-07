package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Alexander on 09.10.2016.
 */
public class ScrollablePanel extends Panel implements Viewport {
    public static final float INITIAL_SCROLL_SIZE = 12f;
    protected ScrollBar verticalScrollBar;
    protected ScrollBar horizontalScrollBar;
    protected ComponentContainer viewport;
    protected ComponentContainer container;

    public ScrollablePanel() {
        this(10, 10, 100, 100);
    }

    public ScrollablePanel(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(width, height);
    }

    public ScrollablePanel(Vector2f position, Vector2f size) {
        super(position, size);
        initialize(size.x, size.y);
    }

    private void initialize(float width, float height) {
        float viewportWidth = width - INITIAL_SCROLL_SIZE;
        float viewportHeight = height - INITIAL_SCROLL_SIZE;

        verticalScrollBar = new ScrollBar();
        verticalScrollBar.setBackgroundColor(ColorConstants.darkGray());
        verticalScrollBar.setPosition(viewportWidth, 0);
        verticalScrollBar.setSize(INITIAL_SCROLL_SIZE, viewportHeight);
        verticalScrollBar.setArrowColor(ColorConstants.white());
        verticalScrollBar.setScrollColor(ColorConstants.white());
        verticalScrollBar.setArrowsEnabled(true);
        verticalScrollBar.setOrientation(Orientation.VERTICAL);
        verticalScrollBar.setBorder(new SimpleRectangleLineBorder(ColorConstants.darkGray(), 0.5f));
        verticalScrollBar.setArrowSize(INITIAL_SCROLL_SIZE);
        verticalScrollBar.setViewport(this);

        horizontalScrollBar = new ScrollBar();
        horizontalScrollBar.setBackgroundColor(ColorConstants.darkGray());
        horizontalScrollBar.setPosition(0, viewportHeight);
        horizontalScrollBar.setSize(viewportWidth, INITIAL_SCROLL_SIZE);
        horizontalScrollBar.setArrowColor(ColorConstants.white());
        horizontalScrollBar.setScrollColor(ColorConstants.white());
        horizontalScrollBar.setArrowsEnabled(true);
        horizontalScrollBar.setOrientation(Orientation.HORIZONTAL);
        horizontalScrollBar.setBorder(new SimpleRectangleLineBorder(ColorConstants.darkGray(), 0.5f));
        horizontalScrollBar.setArrowSize(INITIAL_SCROLL_SIZE);
        horizontalScrollBar.setViewport(this);

        viewport = new Panel(0, 0, viewportWidth, viewportHeight);
        viewport.setBackgroundColor(1, 1, 1, 0);
        viewport.setBorder(null);


        container = new Panel(0, 0, viewportWidth, viewportHeight);
        container.setBorder(null);
        viewport.addComponent(container);

        this.addComponent(verticalScrollBar);
        this.addComponent(horizontalScrollBar);
        this.addComponent(viewport);
        this.setBackgroundColor(ColorConstants.transparent());
        this.setBorder(new SimpleRectangleLineBorder(ColorConstants.darkGray(), 0.5f));

        resize();
    }

    public void resize() {
        boolean horizontalScrollBarVisible = horizontalScrollBar.isVisible();
        boolean verticalScrollBarVisible = verticalScrollBar.isVisible();

        Vector2f scrollablePanelSize = new Vector2f(size);
        Vector2f containerSize = new Vector2f(container.size);
        Vector2f viewportSize = new Vector2f(size);

        if (horizontalScrollBarVisible) {
            horizontalScrollBar.position.y = viewportSize.y = scrollablePanelSize.y - horizontalScrollBar.size.y;
        }
        if (verticalScrollBarVisible) {
            verticalScrollBar.position.x = viewportSize.x = scrollablePanelSize.x - verticalScrollBar.size.x;
        }
        viewport.size = viewportSize;
        horizontalScrollBar.size.x = viewportSize.x;
        verticalScrollBar.size.y = viewportSize.y;
        float horizontalRange = horizontalScrollBar.getMaxValue() - horizontalScrollBar.getMinValue();
        horizontalScrollBar.setVisibleAmount(containerSize.x >= viewportSize.x ? (horizontalRange * viewportSize.x / containerSize.x) : horizontalRange);

        float verticalRange = verticalScrollBar.getMaxValue() - verticalScrollBar.getMinValue();
        verticalScrollBar.setVisibleAmount(containerSize.x >= viewportSize.x ? (verticalRange * viewportSize.y / containerSize.y) : verticalRange);

        updateViewport();
    }

    public void updateViewport() {
        float vh = viewport.size.y;
        float ch = container.size.y;
        float newPosY;
        if (vh > ch) {
            newPosY = 0;
        } else {
            newPosY = (vh - ch) * verticalScrollBar.getCurValue() / (verticalScrollBar.getMaxValue() - verticalScrollBar.getMinValue());
        }

        float vw = viewport.size.x;
        float cw = container.size.x;
        float newPosX;
        if (vw > cw) {
            newPosX = 0;
        } else {
            newPosX = (vw - cw) * horizontalScrollBar.getCurValue() / (horizontalScrollBar.getMaxValue() - horizontalScrollBar.getMinValue());
        }
        container.position = new Vector2f(newPosX, newPosY);
    }

    public ScrollBar getVerticalScrollBar() {
        return verticalScrollBar;
    }

    public void setVerticalScrollBar(ScrollBar verticalScrollBar) {
        this.verticalScrollBar.setViewport(null);
        this.verticalScrollBar = verticalScrollBar;
        this.verticalScrollBar.setViewport(this);
    }

    public ScrollBar getHorizontalScrollBar() {
        return horizontalScrollBar;
    }

    public void setHorizontalScrollBar(ScrollBar horizontalScrollBar) {
        this.horizontalScrollBar.setViewport(null);
        this.horizontalScrollBar = horizontalScrollBar;
        this.horizontalScrollBar.setViewport(this);
    }

    public ComponentContainer getContainer() {
        return container;
    }
}
