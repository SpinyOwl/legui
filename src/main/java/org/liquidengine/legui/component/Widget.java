package org.liquidengine.legui.component;

import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.event.widget.WidgetCloseEvent;
import org.liquidengine.legui.component.misc.listener.widget.WidgetCloseButMouseClickEventListener;
import org.liquidengine.legui.component.misc.listener.widget.WidgetDragListener;
import org.liquidengine.legui.component.misc.listener.widget.WidgetMinimizeButMouseClickEventListener;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.layout.borderlayout.BorderLayout;
import org.liquidengine.legui.layout.borderlayout.BorderLayoutConstraint;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.MouseDragEventListener;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.theme.Themes;

/**
 * Widget component is container which have predefined components such as container, title label, close and minimize buttons and predefined event listeners.
 * This component can be moved, minimized and restored, closed. Also now you can enable or disable title.
 * <p>
 * TODO: REIMPLEMENT THIS COMPONENT ACCORDING TO NEW LAYOUT SYSTEM
 */
public class Widget extends Component {

    /**
     * Initial height of title. Used to initialize title components.
     */
    private static final int INITIAL_TITLE_HEIGHT = 20;
    private static final int CLOSE_ICON_CHAR = 0xF5AD;
    private static final int MINIMIZE_ICON_CHAR = 0xF5B0;
    private static final int MAXIMIZE_ICON_CHAR = 0xF5AF;
    private MouseDragEventListener mouseDragEventLeguiEventListener;

    private Icon closeIcon;
    private Icon minimizeIcon;
    private Icon maximizeIcon;

    private boolean draggable = true;
    private boolean minimized = false;
    /**
     * Used to store widget size in maximized state when minimizing widget.
     */
    private Vector2f maximizedSize = new Vector2f();
    private Component container;
    private Component titleContainer;
    private Label title;
    private Button closeButton;
    private Button minimizeButton;

    /**
     * Creates a widget with default title text.
     */
    public Widget() {
        initialize("Widget");
    }

    /**
     * Creates a widget with default title text and specified position and size.
     *
     * @param x x position in parent.
     * @param y y position in parent.
     * @param width width of component.
     * @param height height of component.
     */
    public Widget(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("Widget");
    }

    /**
     * Creates a widget with default title text and specified position and size.
     *
     * @param position position in parent.
     * @param size size of component.
     */
    public Widget(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("Widget");
    }

    /**
     * Creates a widget with specified title text.
     *
     * @param title widget text.
     */
    public Widget(String title) {
        initialize(title);
    }

    /**
     * Creates a widget with specified title text and specified position and size.
     *
     * @param title widget text.
     * @param x x position in parent.
     * @param y y position in parent.
     * @param width width of component.
     * @param height height of component.
     */
    public Widget(String title, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(title);
    }

    /**
     * Creates a widget with specified title text and specified position and size.
     *
     * @param title widget text.
     * @param position position in parent.
     * @param size size of component.
     */
    public Widget(String title, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(title);
    }

    /**
     * This method used to initialize widget.
     *
     * @param title title to set.
     */
    private void initialize(String title) {
        this.setLayout(new BorderLayout());

        titleContainer = new Panel();
        titleContainer.setLayout(new BorderLayout());
        titleContainer.getStyle().setMaximumSize(new Vector2f(Float.MAX_VALUE, INITIAL_TITLE_HEIGHT));
        titleContainer.getStyle().setMinimumSize(new Vector2f(0, INITIAL_TITLE_HEIGHT));
        titleContainer.getStyle().getBackground().setColor(ColorConstants.white());
        titleContainer.getSize().y = INITIAL_TITLE_HEIGHT;
        titleContainer.setTabFocusable(false);

        this.title = new Label(title);
        this.title.setPosition(0, 0);
        this.title.getSize().y = INITIAL_TITLE_HEIGHT;
        this.title.getTextState().getPadding().set(10, 5, 10, 5);
        this.title.getStyle().setMaximumSize(Float.MAX_VALUE, INITIAL_TITLE_HEIGHT);
        this.title.getStyle().setMinimumSize(0, INITIAL_TITLE_HEIGHT);
        this.title.getStyle().getBackground().setColor(ColorConstants.transparent());
        this.title.getStyle().setBorder(null);
        this.title.setTabFocusable(false);

        mouseDragEventLeguiEventListener = new WidgetDragListener(this);
        this.title.getListenerMap().addListener(MouseDragEvent.class, mouseDragEventLeguiEventListener);

        closeButton = new Button("");
        closeButton.setSize(new Vector2f(INITIAL_TITLE_HEIGHT));
        closeButton.getStyle().getBackground().setColor(ColorConstants.transparent());
        closeIcon = new CharIcon(new Vector2f(INITIAL_TITLE_HEIGHT * 2 / 3), FontRegistry.MATERIAL_DESIGN_ICONS, (char) CLOSE_ICON_CHAR,
            ColorConstants.black());
        closeIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        closeIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        closeButton.setBackgroundIcon(closeIcon);

        closeButton.getStyle().setMaximumSize(INITIAL_TITLE_HEIGHT, INITIAL_TITLE_HEIGHT);
        closeButton.getStyle().setMinimumSize(INITIAL_TITLE_HEIGHT, INITIAL_TITLE_HEIGHT);
        closeButton.getListenerMap().addListener(MouseClickEvent.class, new WidgetCloseButMouseClickEventListener(this));
        closeButton.getStyle().setBorder(null);
        closeButton.getTextState().setVerticalAlign(VerticalAlign.MIDDLE);
        closeButton.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);
        closeButton.setTabFocusable(false);

        minimizeButton = new Button("");
        minimizeButton.setSize(new Vector2f(INITIAL_TITLE_HEIGHT));
        minimizeButton.getStyle().getBackground().setColor(ColorConstants.transparent());
        minimizeButton.setTabFocusable(false);
        minimizeButton.getStyle().setMaximumSize(INITIAL_TITLE_HEIGHT, INITIAL_TITLE_HEIGHT);
        minimizeButton.getStyle().setMinimumSize(INITIAL_TITLE_HEIGHT, INITIAL_TITLE_HEIGHT);

        minimizeIcon = new CharIcon(new Vector2f(INITIAL_TITLE_HEIGHT * 2 / 3), FontRegistry.MATERIAL_DESIGN_ICONS, (char) MINIMIZE_ICON_CHAR,
            ColorConstants.black());
        minimizeIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        minimizeIcon.setVerticalAlign(VerticalAlign.MIDDLE);

        maximizeIcon = new CharIcon(new Vector2f(INITIAL_TITLE_HEIGHT * 2 / 3), FontRegistry.MATERIAL_DESIGN_ICONS, (char) MAXIMIZE_ICON_CHAR,
            ColorConstants.black());
        maximizeIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        maximizeIcon.setVerticalAlign(VerticalAlign.MIDDLE);

        minimizeButton.setBackgroundIcon(minimizeIcon);

        minimizeButton.getListenerMap().addListener(MouseClickEvent.class, new WidgetMinimizeButMouseClickEventListener(this));
        minimizeButton.getStyle().setBorder(null);
        minimizeButton.getTextState().setVerticalAlign(VerticalAlign.MIDDLE);
        minimizeButton.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);

        container = new Panel();
        container.setTabFocusable(false);

        titleContainer.add(this.title, BorderLayoutConstraint.LEFT);
        titleContainer.add(closeButton, BorderLayoutConstraint.RIGHT);
        titleContainer.add(minimizeButton, BorderLayoutConstraint.CENTER);

        add(titleContainer, BorderLayoutConstraint.TOP);
        add(container, BorderLayoutConstraint.CENTER);

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Widget.class).applyAll(this);
        getLayout().layout(this);
        resize();
    }

    /**
     * Used to resize widget container and title elements.
     */
    public void resize() {
        float titHei = titleContainer.getSize().y;
        if (titleContainer.isVisible()) {
            titleContainer.getPosition().set(0);

            float widgetWidth = getSize().x;
            float titleWidth = widgetWidth;

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

    /**
     * Used to set size of widget.
     *
     * @param size size vector.
     */
    @Override
    public void setSize(Vector2f size) {
        if (!minimized) {
            super.setSize(size);
            resize();
        }
    }

    /**
     * Used to set size of widget.
     *
     * @param width width to set.
     * @param height height to set.
     */
    @Override
    public void setSize(float width, float height) {
        if (!minimized) {
            super.setSize(width, height);
            resize();
        }
    }

    /**
     * Returns height of title.
     *
     * @return height of title.
     */
    public float getTitleHeight() {
        return titleContainer.getSize().y;
    }

    /**
     * Used to set title height.
     *
     * @param titleHeight title height to set.
     */
    public void setTitleHeight(float titleHeight) {
        this.titleContainer.getSize().y = titleHeight;
        this.title.getSize().y = titleHeight;
        resize();
    }

    /**
     * Returns true if title enabled.
     *
     * @return true if title enabled.
     */
    public boolean isTitleEnabled() {
        return titleContainer.isVisible();
    }

    /**
     * Used to set title enabled or not.
     *
     * @param titleEnabled title state (enable or not) to set.
     */
    public void setTitleEnabled(boolean titleEnabled) {
        if (minimized) {
            return;
        }
        this.titleContainer.setVisible(titleEnabled);
        if (titleEnabled) {
            this.getLayout().addComponent(titleContainer, BorderLayoutConstraint.TOP);
        } else {
            this.getLayout().removeComponent(titleContainer);
        }
        resize();
    }

    /**
     * Returns true if widget should be closeable.
     *
     * @return true if widget should be closeable.
     */
    public boolean isCloseable() {
        return closeButton.isVisible();
    }

    /**
     * Used to set would widget closeable or not.
     *
     * @param closeable widget state (closeable or not) to set.
     */
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

    /**
     * Returns title container. Can be used to customize widget.
     *
     * @return title container.
     */
    public Component getTitleContainer() {
        return titleContainer;
    }

    /**
     * Returns title text state.
     *
     * @return title text state.
     */
    public TextState getTitleTextState() {
        return title.getTextState();
    }

    /**
     * Returns close button text color.
     *
     * @return close button text color.
     */
    public Vector4f getCloseButtonColor() {
        return closeButton.getTextState().getTextColor();
    }

    /**
     * Used to set close button text color.
     *
     * @param closeButtonColor close button text color to set.
     */
    public void setCloseButtonColor(Vector4f closeButtonColor) {
        this.closeButton.getTextState().setTextColor(closeButtonColor);
    }

    /**
     * Returns widget container that hold all other elements. Should be used to add components.
     *
     * @return widget container.
     */
    public Component getContainer() {
        return container;
    }

    /**
     * Used to set widget container.
     *
     * @param container widget container to set.
     */
    public void setContainer(Component container) {
        this.remove(this.container);
        this.add(this.container = container);
    }

    /**
     * Returns true if widget could be dragged.
     *
     * @return true if widget could be dragged.
     */
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * Used to set widget draggable or not.
     *
     * @param draggable new draggable state of widget.
     */
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

    /**
     * Returns true if widget could be minimized (minimize button is visible).
     *
     * @return true if widget could be minimized (minimize button is visible).
     */
    public boolean isMinimizable() {
        return minimizeButton.isVisible();
    }

    /**
     * Used to make minimize button visible or not.
     *
     * @param minimizable new minimizable state of widget.
     */
    public void setMinimizable(boolean minimizable) {
        this.minimizeButton.setVisible(minimizable);
        resize();
    }

    /**
     * Returns true if widget minimized.
     *
     * @return true if widget minimized.
     */
    public boolean isMinimized() {
        return minimized;
    }

    /**
     * Used to minimize/maximize widget.
     *
     * @param minimized true to minimize, false to maximize.
     */
    public void setMinimized(boolean minimized) {
        if (this.minimized != minimized) {
            this.minimized = minimized;
            if (minimized) {
                minimize();
            } else {
                maximize();
            }
            updateIcons();
        }
    }

    /**
     * Used to minimize widget.
     */
    private void minimize() {
        if (isTitleEnabled()) {
            Vector2f size = getSize();
            maximizedSize.set(size);
            size.set(size.x, getTitleHeight());
            resize();
        }
    }

    /**
     * Used to maximize widget.
     */
    private void maximize() {
        if (isTitleEnabled()) {
            getSize().set(maximizedSize);
            resize();
        }
    }

    /**
     * Returns close icon that used by close button.
     *
     * @return close icon that used by close button.
     */
    public Icon getCloseIcon() {
        return closeIcon;
    }

    /**
     * Used to set close icon to close button.
     *
     * @param closeIcon close icon to set.
     */
    public void setCloseIcon(Icon closeIcon) {
        this.closeIcon = closeIcon;
        updateIcons();
    }

    /**
     * Returns maximize icon that used by minimize button.
     *
     * @return maximize icon that used by minimize button.
     */
    public Icon getMaximizeIcon() {
        return maximizeIcon;
    }

    /**
     * Used to set maximize icon to minimize button.
     *
     * @param maximizeIcon maximize icon to set.
     */
    public void setMaximizeIcon(Icon maximizeIcon) {
        this.maximizeIcon = maximizeIcon;
        updateIcons();
    }

    /**
     * Returns minimize icon that used by minimize button.
     *
     * @return minimize icon that used by minimize button.
     */
    public Icon getMinimizeIcon() {
        return minimizeIcon;
    }

    /**
     * Used to set minimize icon to minimize button.
     *
     * @param minimizeIcon minimize icon to set.
     */
    public void setMinimizeIcon(Icon minimizeIcon) {
        this.minimizeIcon = minimizeIcon;
        updateIcons();
    }

    /**
     * Used to update icons of close and minimize buttons.
     */
    private void updateIcons() {
        closeButton.setBackgroundIcon(closeIcon);
        minimizeButton.setBackgroundIcon(minimized ? maximizeIcon : minimizeIcon);
    }

    /**
     * Used to add event listener for widget close event.
     *
     * @param eventListener event listener to add.
     */
    public void addWidgetCloseEventListener(EventListener<WidgetCloseEvent> eventListener) {
        this.getListenerMap().addListener(WidgetCloseEvent.class, eventListener);
    }

    /**
     * Returns all event listeners for widget close event.
     *
     * @return all event listeners for widget close event.
     */
    public List<EventListener<WidgetCloseEvent>> getWidgetCloseEvents() {
        return this.getListenerMap().getListeners(WidgetCloseEvent.class);
    }

    /**
     * Used to remove event listener for widget close event.
     *
     * @param eventListener event listener to remove.
     */
    public void removeWidgetCloseEventListener(EventListener<WidgetCloseEvent> eventListener) {
        this.getListenerMap().removeListener(WidgetCloseEvent.class, eventListener);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

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

}
