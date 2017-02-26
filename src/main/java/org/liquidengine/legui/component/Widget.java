package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.MouseDragEventListener;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.util.TextUtil.cpToStr;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public class Widget<T extends Component> extends Container {
    private static final String CLOSE_ICON    = cpToStr(0xE5CD);
    private static final String MINIMIZE_ICON = cpToStr(0xE5D6);
    private static final String MAXIMIZE_ICON = cpToStr(0xE5D7);
    protected MouseDragEventListener mouseDragEventLeguiEventListener;
    private boolean  draggable     = true;
    private boolean  minimized     = false;
    /**
     * Used to store widget size in maximized state when minimizing widget
     */
    private Vector2f maximizedSize = new Vector2f();
    private Container container;
    private Label     title;
    private Button    closeButton;
    private Button    minimizeButton;

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
        this.title = new Label(title);
        this.title.getTextState().getPadding().set(10, 5, 10, 5);

        mouseDragEventLeguiEventListener = new WidgetDragListener();
        this.title.getListenerMap().addListener(MouseDragEvent.class, mouseDragEventLeguiEventListener);

        this.closeButton = new Button(CLOSE_ICON);
        this.closeButton.setBackgroundColor(ColorConstants.red());
        this.closeButton.getTextState().setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        this.closeButton.getListenerMap().addListener(MouseClickEvent.class, new WidgetCloseButMouseClickEventListener());

        this.minimizeButton = new Button(MINIMIZE_ICON);
        this.minimizeButton.getTextState().setFont(FontRegister.MATERIAL_ICONS_REGULAR);
        this.minimizeButton.getListenerMap().addListener(MouseClickEvent.class, new WidgetMinimizeButMouseClickEventListener());

        this.container = new Panel();

        this.add(this.title);
        this.add(this.closeButton);
        this.add(this.minimizeButton);
        this.add(this.container);

        resize();
    }

    public void resize() {
        float titHei = title.getSize().y;
        if (title.isVisible()) {
            title.getPosition().set(0);

            float widgetWidth = getSize().x;
            float titleWidth  = widgetWidth;
            if (closeButton.isVisible()) {
                titleWidth -= titHei;
            }
            if (minimizeButton.isVisible()) {
                titleWidth -= titHei;
            }

            title.getSize().x = titleWidth;
            container.getPosition().set(0, titHei);
            container.getSize().set(widgetWidth, getSize().y - titHei);

            closeButton.getSize().set(titHei);
            closeButton.getPosition().set(widgetWidth - titHei, 0);
            closeButton.getTextState().setFontSize(titHei * 2f / 3f);
            closeButton.getTextState().getPadding().set(titHei / 6f);

            minimizeButton.getSize().set(titHei);
            minimizeButton.getPosition().set(titleWidth, 0);
            minimizeButton.getTextState().setFontSize(titHei * 2f / 3f);
            minimizeButton.getTextState().getPadding().set(titHei / 6f);

        } else {
            container.getPosition().set(0, 0);
            container.getSize().set(getSize());
            closeButton.getSize().set(0);
            minimizeButton.getSize().set(0);
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
        return title.getSize().y;
    }

    public void setTitleHeight(float titleHeight) {
        this.title.getSize().y = titleHeight;
        resize();
    }

    public boolean isTitleEnabled() {
        return title.isVisible();
    }

    public void setTitleEnabled(boolean titleEnabled) {
        if (minimized) return;
        this.title.setVisible(titleEnabled);
        resize();
    }

    public boolean isCloseable() {
        return closeButton.isVisible();
    }

    public void setCloseable(boolean closeable) {
        this.closeButton.setVisible(closeable);
        resize();
    }

    public Vector4f getTitleBackgroundColor() {
        return title.getBackgroundColor();
    }

    public void setTitleBackgroundColor(Vector4f titleBackgroundColor) {
        this.title.setBackgroundColor(titleBackgroundColor);
    }

    public TextState getTitleTextState() {
        return title.getTextState();
    }

    public Vector4f getCloseButtonColor() {
        return closeButton.getBackgroundColor();
    }

    public void setCloseButtonColor(Vector4f closeButtonColor) {
        this.closeButton.getTextState().setTextColor(closeButtonColor);
    }

    public void setCloseButtonBackgroundColor(Vector4f closeButtonBackgroundColor) {
        this.closeButton.setBackgroundColor(closeButtonBackgroundColor);
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.remove(this.container);
        this.add(this.container = container);
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        if (this.draggable != draggable) {
            if (draggable) {
                this.title.getListenerMap().addListener(MouseDragEvent.class, mouseDragEventLeguiEventListener);
            } else {
                this.title.getListenerMap().removeListener(MouseDragEvent.class, mouseDragEventLeguiEventListener);
            }
            this.draggable = draggable;
        }
    }

    public boolean isMinimizeable() {
        return minimizeButton.isVisible();
    }

    public void setMinimizeable(boolean minimizeable) {
        this.minimizeButton.setVisible(minimizeable);
        resize();
    }

    /**
     * Returns true if widget minimized
     *
     * @return true if widget minimized
     */
    public boolean isMinimized() {
        return minimized;
    }

    /**
     * Used to minimize/maximize widget
     *
     * @param minimized true to minimize, false to maximize
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
            Vector2f size = getSize();
            maximizedSize.set(size);
            size.set(size.x, getTitleHeight());
            resize();
        }
    }

    private void maximize() {
        if (isTitleEnabled()) {
            getSize().set(maximizedSize);
            resize();
        }
    }

    /**
     * (non-Javadoc)
     *
     * @param o
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Widget<?> widget = (Widget<?>) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(draggable, widget.draggable)
                .append(minimized, widget.minimized)
                .append(maximizedSize, widget.maximizedSize)
                .append(container, widget.container)
                .append(title, widget.title)
                .append(closeButton, widget.closeButton)
                .append(minimizeButton, widget.minimizeButton)
                .isEquals();
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
                .append(draggable)
                .append(minimized)
                .append(maximizedSize)
                .append(container)
                .append(title)
                .append(closeButton)
                .append(minimizeButton)
                .toHashCode();
    }


    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("draggable", draggable)
                .append("minimized", minimized)
                .append("maximizedSize", maximizedSize)
                .append("container", container)
                .append("title", title)
                .append("closeButton", closeButton)
                .append("minimizeButton", minimizeButton)
                .toString();
    }

    public class WidgetDragListener implements MouseDragEventListener {
        @Override
        public void process(MouseDragEvent event) {
            Widget.this.getPosition().add(event.getDelta());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .toString();
        }
    }

    public class WidgetCloseButMouseClickEventListener implements MouseClickEventListener {

        @Override
        public void process(MouseClickEvent event) {
            if (CLICK == event.getAction()) {
                Widget.this.setVisible(false);
            }
        }
    }

    public class WidgetMinimizeButMouseClickEventListener implements MouseClickEventListener {
        public void process(MouseClickEvent event) {
            if (CLICK == event.getAction()) {
                boolean newValue = !Widget.this.isMinimized();
                Widget.this.minimizeButton.getTextState().setText(newValue ? MAXIMIZE_ICON : MINIMIZE_ICON);
                Widget.this.setMinimized(newValue);
            }
        }
    }
}
