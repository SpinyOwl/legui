package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.event.component.MouseClickEvent;
import org.liquidengine.legui.event.component.MouseDragEvent;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.listener.component.MouseClickEventListener;
import org.liquidengine.legui.listener.component.MouseDragEventListener;
import org.liquidengine.legui.util.ColorConstants;

import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.util.Util.cpToStr;

/**
 * Created by Shcherbin Alexander on 9/27/2016.
 */
public class Widget extends ComponentContainer {
    private static final String CLOSE_ICON    = cpToStr(0xE5CD);
    private static final String MINIMIZE_ICON = cpToStr(0xE5D6);
    private static final String MAXIMIZE_ICON = cpToStr(0xE5D7);

    protected boolean draggable = true;
    protected boolean minimized = false;

    /**
     * Used to store widget size in maximized state when minimizing widget
     */
    protected Vector2f maximizedSize = new Vector2f();

    protected ComponentContainer     container;
    protected Label                  title;
    protected Button                 closeButton;
    protected Button                 minimizeButton;
    protected MouseDragEventListener mouseDragEventLeguiEventListener;

    public Widget() {
        initialize("Widget");
    }

    public Widget(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("Widget");
    }

    public Widget(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("Widget");
    }

    public Widget(String title) {
        initialize(title);
    }

    public Widget(String title, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(title);
    }

    public Widget(String title, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(title);
    }

    private void initialize(String title) {
        SimpleRectangleLineBorder simpleRectangleLineBorder = new SimpleRectangleLineBorder(ColorConstants.black(), 1);
        this.title = new Label(title);
        this.title.textState.setHorizontalAlign(HorizontalAlign.LEFT);
        this.title.textState.getPadding().set(10, 5, 10, 5);
        this.title.setBorder(simpleRectangleLineBorder);

        mouseDragEventLeguiEventListener = new WidgetDragListener();
        this.title.leguiEventListeners.addListener(MouseDragEvent.class, mouseDragEventLeguiEventListener);

        this.closeButton = new Button(CLOSE_ICON);
        this.closeButton.backgroundColor.set(1, 0, 0, 1);
        this.closeButton.setBorder(simpleRectangleLineBorder);
        this.closeButton.textState.setHorizontalAlign(HorizontalAlign.LEFT);
        this.closeButton.textState.setVerticalAlign(VerticalAlign.MIDDLE);
        this.closeButton.textState.setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        this.closeButton.getLeguiEventListeners().addListener(MouseClickEvent.class, new WidgetCloseButMouseClickEventListener());

        this.minimizeButton = new Button(MINIMIZE_ICON);
        this.minimizeButton.backgroundColor.set(1);
        this.minimizeButton.setBorder(simpleRectangleLineBorder);
        this.minimizeButton.textState.setHorizontalAlign(HorizontalAlign.LEFT);
        this.minimizeButton.textState.setVerticalAlign(VerticalAlign.MIDDLE);
        this.minimizeButton.textState.setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        this.minimizeButton.getLeguiEventListeners().addListener(MouseClickEvent.class, new WidgetMinimizeButMouseClickEventListener());

        this.container = new Panel();
        this.container.setBorder(simpleRectangleLineBorder);

        this.backgroundColor.set(1);
        this.border = simpleRectangleLineBorder;

        this.addComponent(this.title);
        this.addComponent(this.closeButton);
        this.addComponent(this.minimizeButton);
        this.addComponent(this.container);

        resize();
    }

    public void resize() {
        float titHei = title.size.y;
        if (title.visible) {
            title.position.set(0);

            float widgetWidth = size.x;
            float titleWidth  = widgetWidth;
            if (closeButton.visible) {
                titleWidth -= titHei;
            }
            if (minimizeButton.visible) {
                titleWidth -= titHei;
            }

            title.size.x = titleWidth;
            container.position.set(0, titHei);
            container.size.set(widgetWidth, size.y - titHei);

            closeButton.size.set(titHei);
            closeButton.position.set(widgetWidth - titHei, 0);
            closeButton.textState.setFontSize(titHei * 2f / 3f);
            closeButton.textState.getPadding().set(titHei / 6f);

            minimizeButton.size.set(titHei);
            minimizeButton.position.set(titleWidth, 0);
            minimizeButton.textState.setFontSize(titHei * 2f / 3f);
            minimizeButton.textState.getPadding().set(titHei / 6f);

        } else {
            container.position.set(0, 0);
            container.size.set(size);
            closeButton.size.set(0);
            minimizeButton.size.set(0);
        }
    }

    @Override
    public void setSize(Vector2f size) {
        if (!minimized) {
            super.setSize(size);
            resize();
        }
    }

    @Override
    public void setSize(float width, float height) {
        if (!minimized) {
            super.setSize(width, height);
            resize();
        }
    }

    public float getTitleHeight() {
        return title.size.y;
    }

    public void setTitleHeight(float titleHeight) {
        this.title.size.y = titleHeight;
        resize();
    }

    public boolean isTitleEnabled() {
        return title.isVisible();
    }

    public void setTitleEnabled(boolean titleEnabled) {
        if (minimized) return;
        this.title.visible = titleEnabled;
        resize();
    }

    public boolean isCloseable() {
        return closeButton.isVisible();
    }

    public void setCloseable(boolean closeable) {
        this.closeButton.visible = closeable;
        resize();
    }

    public Vector4f getTitleBackgroundColor() {
        return title.backgroundColor;
    }

    public void setTitleBackgroundColor(Vector4f titleBackgroundColor) {
        this.title.backgroundColor = titleBackgroundColor;
    }

    public TextState getTitleTextState() {
        return title.textState;
    }

    public Vector4f getCloseButtonColor() {
        return closeButton.backgroundColor;
    }

    public void setCloseButtonColor(Vector4f closeButtonColor) {
        this.closeButton.textState.setTextColor(closeButtonColor);
    }

    public void setCloseButtonBackgroundColor(Vector4f closeButtonBackgroundColor) {
        this.closeButton.backgroundColor = closeButtonBackgroundColor;
    }

    public ComponentContainer getContainer() {
        return container;
    }

    public void setContainer(ComponentContainer container) {
        this.removeComponent(this.container);
        this.addComponent(this.container = container);
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        if (this.draggable != draggable) {
            if (draggable) {
                this.title.leguiEventListeners.addListener(MouseDragEvent.class, mouseDragEventLeguiEventListener);
            } else {
                this.title.leguiEventListeners.removeListener(MouseDragEvent.class, mouseDragEventLeguiEventListener);
            }
            this.draggable = draggable;
        }
    }

    public boolean isMinimizeable() {
        return minimizeButton.isVisible();
    }

    public void setMinimizeable(boolean minimizeable) {
        this.minimizeButton.visible = minimizeable;
        resize();
    }

    /**
     * Returns true if widget minimized
     *
     * @return
     */
    public boolean isMinimized() {
        return minimized;
    }

    /**
     * @param minimized
     */
    public void setMinimized(boolean minimized) {
        if (this.minimized != minimized) {
            this.minimized = minimized;
            if (minimized) {
                minimize();
            } else {
                maximize();
            }
        }
    }

    private void minimize() {
        if (isTitleEnabled()) {
            maximizedSize.set(size);
            size.set(size.x, getTitleHeight());
            resize();
        }
    }

    private void maximize() {
        if (isTitleEnabled()) {
            size.set(maximizedSize);
            resize();
        }
    }

    public class WidgetDragListener implements MouseDragEventListener {
        @Override
        public void update(MouseDragEvent event) {
            Vector2f delta = event.getCursorPosition().sub(event.getCursorPositionPrev());
            Widget.this.position.add(delta);
        }
    }

    public class WidgetCloseButMouseClickEventListener implements MouseClickEventListener {
        @Override
        public void update(MouseClickEvent event) {
            if (CLICK.equals(event.getAction())) {
                Widget.this.visible = false;
            }
        }
    }

    public class WidgetMinimizeButMouseClickEventListener implements MouseClickEventListener {
        @Override
        public void update(MouseClickEvent event) {
            if (CLICK.equals(event.getAction())) {
                boolean newValue = !Widget.this.isMinimized();
                Widget.this.minimizeButton.textState.setText(newValue ? MAXIMIZE_ICON : MINIMIZE_ICON);
                Widget.this.setMinimized(newValue);
            }
        }
    }
}
