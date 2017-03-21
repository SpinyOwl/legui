package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.MouseDragEventListener;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.theme.Theme;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.util.TextUtil.cpToStr;

/**
 * Widget component is container which have predefined components such as container, title label, close and minimize buttons and predefined event listeners.
 * This component can be moved, minimized and restored, closed. Also now you can enable or disable title.
 */
public class Widget extends Container<Component> {

    /**
     * Initial height of title. Used to initialize title components.
     */
    public static final  int    INITIAL_TITLE_HEIGHT = 20;
    private static final int    CLOSE_ICON_CHAR      = 0xF5AD;
    private static final int    MINIMIZE_ICON_CHAR   = 0xF5B0;
    private static final int    MAXIMIZE_ICON_CHAR   = 0xF5AF;
    protected MouseDragEventListener mouseDragEventLeguiEventListener;

    private Icon closeIcon;
    private Icon minimizeIcon;
    private Icon maximizeIcon;

    private boolean  draggable     = true;
    private boolean  minimized     = false;
    /**
     * Used to store widget size in maximized state when minimizing widget
     */
    private Vector2f maximizedSize = new Vector2f();
    private Container<Component> container;
    private Container<Component> titleContainer;
    private Label                title;
    private Button               closeButton;
    private Button               minimizeButton;

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
        this.titleContainer = new Panel();
        this.titleContainer.getSize().y = INITIAL_TITLE_HEIGHT;
        this.titleContainer.setBackgroundColor(ColorConstants.white());

        this.title = new Label(title);
        this.title.setPosition(0, 0);
        this.title.getSize().y = INITIAL_TITLE_HEIGHT;
        this.title.getTextState().getPadding().set(10, 5, 10, 5);
        this.title.setBackgroundColor(ColorConstants.transparent());
        this.title.setBorder(null);

        mouseDragEventLeguiEventListener = new WidgetDragListener();
        this.title.getListenerMap().addListener(MouseDragEvent.class, mouseDragEventLeguiEventListener);

        this.closeButton = new Button("");
        this.closeButton.setBackgroundColor(ColorConstants.transparent());
        closeIcon = new CharIcon(new Vector2f(INITIAL_TITLE_HEIGHT*2/3), FontRegister.MATERIAL_DESIGN_ICONS, CLOSE_ICON_CHAR, ColorConstants.black());
        closeIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        closeIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        this.closeButton.setBackgroundIcon(closeIcon);

        this.closeButton.getListenerMap().addListener(MouseClickEvent.class, new WidgetCloseButMouseClickEventListener());
        this.closeButton.setBorder(null);
        this.closeButton.getTextState().setVerticalAlign(VerticalAlign.MIDDLE);
        this.closeButton.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);

        this.minimizeButton = new Button("");
        this.minimizeButton.setBackgroundColor(ColorConstants.transparent());

        minimizeIcon = new CharIcon(new Vector2f(INITIAL_TITLE_HEIGHT*2/3), FontRegister.MATERIAL_DESIGN_ICONS, MINIMIZE_ICON_CHAR, ColorConstants.black());
        minimizeIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        minimizeIcon.setVerticalAlign(VerticalAlign.MIDDLE);

        maximizeIcon = new CharIcon(new Vector2f(INITIAL_TITLE_HEIGHT*2/3), FontRegister.MATERIAL_DESIGN_ICONS, MAXIMIZE_ICON_CHAR, ColorConstants.black());
        maximizeIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        maximizeIcon.setVerticalAlign(VerticalAlign.MIDDLE);

        this.minimizeButton.setBackgroundIcon(minimizeIcon);

        this.minimizeButton.getListenerMap().addListener(MouseClickEvent.class, new WidgetMinimizeButMouseClickEventListener());
        this.minimizeButton.setBorder(null);
        this.minimizeButton.getTextState().setVerticalAlign(VerticalAlign.MIDDLE);
        this.minimizeButton.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);

        this.container = new Panel();

        titleContainer.add(this.title);
        titleContainer.add(this.closeButton);
        titleContainer.add(this.minimizeButton);

        this.add(this.titleContainer);
        this.add(this.container);

        Theme.getDefaultTheme().getThemeManager().getComponentTheme(Widget.class).applyAll(this);

        resize();
    }

    public void resize() {
        float titHei = titleContainer.getSize().y;
        if (titleContainer.isVisible()) {
            titleContainer.getPosition().set(0);

            float widgetWidth = getSize().x;
            float titleWidth  = widgetWidth;

            titleContainer.getSize().set(titleWidth, titHei);
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

            minimizeButton.getSize().set(titHei);
            minimizeButton.getPosition().set(titleWidth, 0);

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
        return titleContainer.isVisible();
    }

    public void setTitleEnabled(boolean titleEnabled) {
        if (minimized) return;
        this.titleContainer.setVisible(titleEnabled);
        resize();
    }

    public boolean isCloseable() {
        return closeButton.isVisible();
    }

    public void setCloseable(boolean closeable) {
        this.closeButton.setVisible(closeable);
        resize();
    }

    /**
     * Returns close button of widget.
     *
     * @return close button of widget.
     */
    public Button getCloseButton() {
        return closeButton;
    }

    /**
     * Returns minimize button of widget.
     *
     * @return minimize button of widget.
     */
    public Button getMinimizeButton() {
        return minimizeButton;
    }

    public Container getTitleContainer() {
        return titleContainer;
    }

    public Vector4f getTitleBackgroundColor() {
        return titleContainer.getBackgroundColor();
    }

    public void setTitleBackgroundColor(Vector4f titleBackgroundColor) {
        this.titleContainer.setBackgroundColor(titleBackgroundColor);
    }

    public TextState getTitleTextState() {
        return title.getTextState();
    }

    public Vector4f getCloseButtonColor() {
        return closeButton.getTextState().getTextColor();
    }

    public void setCloseButtonColor(Vector4f closeButtonColor) {
        this.closeButton.getTextState().setTextColor(closeButtonColor);
    }

    public Vector4f getCloseButtonBackgroundColor() {
        return this.closeButton.getBackgroundColor();
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

    public boolean isMinimizable() {
        return minimizeButton.isVisible();
    }

    public void setMinimizable(boolean minimizable) {
        this.minimizeButton.setVisible(minimizable);
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

    public Icon getCloseIcon() {
        return closeIcon;
    }

    public void setCloseIcon(Icon closeIcon) {
        this.closeIcon = closeIcon;
        updateIcons();
    }

    public Icon getMaximizeIcon() {
        return maximizeIcon;
    }

    public void setMaximizeIcon(Icon maximizeIcon) {
        this.maximizeIcon = maximizeIcon;
        updateIcons();
    }

    public Icon getMinimizeIcon() {
        return minimizeIcon;
    }

    public void setMinimizeIcon(Icon minimizeIcon) {
        this.minimizeIcon = minimizeIcon;
        updateIcons();
    }

    private void updateIcons() {
        closeButton.setBackgroundIcon(closeIcon);
        minimizeButton.setBackgroundIcon(minimized ? maximizeIcon : minimizeIcon);
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

        Widget widget = (Widget) o;

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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("draggable", draggable)
                .append("minimized", minimized)
                .append("maximizedSize", maximizedSize)
                .append("container", container)
                .append("title", title)
                .append("closeButton", closeButton)
                .append("minimizeButton", minimizeButton)
                .toString();
    }

    public interface WidgetCloseEventListener extends EventListener<WidgetCloseEvent> {
        void process(WidgetCloseEvent event);
    }

    public static class WidgetCloseEvent<T extends Widget> extends Event<T> {
        public WidgetCloseEvent(T component, Context context) {
            super(component, context);
        }
    }

    public class WidgetDragListener implements MouseDragEventListener {
        @Override
        public void process(MouseDragEvent event) {
            Widget.this.getPosition().add(event.getDelta());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .toString();
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }

    public class WidgetCloseButMouseClickEventListener implements MouseClickEventListener {

        @Override
        public void process(MouseClickEvent event) {
            if (CLICK == event.getAction()) {
                Widget.this.setVisible(false);
                event.getContext().getEventProcessor().pushEvent(new WidgetCloseEvent(Widget.this, event.getContext()));
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }

    public class WidgetMinimizeButMouseClickEventListener implements MouseClickEventListener {
        public void process(MouseClickEvent event) {
            if (CLICK == event.getAction()) {
                boolean newValue = !Widget.this.isMinimized();
//                Widget.this.minimizeButton.getTextState().setText(newValue ? MAXIMIZE_ICON_STRING : MINIMIZE_ICON_STRING);
                Widget.this.minimizeButton.setBackgroundIcon(newValue ? maximizeIcon : minimizeIcon);
                Widget.this.setMinimized(newValue);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
