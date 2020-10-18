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
import org.liquidengine.legui.component.misc.listener.widget.WidgetResizeButtonDragListener;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.MouseDragEventListener;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.style.Style.PositionType;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.flex.FlexStyle.FlexDirection;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.style.length.Length;
import org.liquidengine.legui.theme.Themes;

/**
 * Widget component is container which have predefined components such as container, title label, close and minimize buttons and predefined event listeners.
 * This component can be moved, minimized and restored, closed. Also you can enable or disable title and make widget non-ascendible using {@link
 * #setAscendible(boolean)}.
 */
public class Widget extends Component {

    /**
     * Default widget title.
     */
    public static final String DEFAULT_WIDGET_TITLE = "Widget";
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
    private boolean resizable = true;
    /**
     * Used to store widget size in maximized state when minimizing widget.
     */
    private Vector2f maximizedSize = new Vector2f();

    private Component container;
    private Component titleContainer;

    private Label title;

    private Button closeButton;
    private Button minimizeButton;
    private Button resizeButton;

    private Length maximizedMinWidth;
    private Length maximizedMinHeight;
    private Length maximizedMaxWidth;
    private Length maximizedMaxHeight;
    private Length maximizedWidth;
    private Length maximizedHeight;

    private boolean ascendible = true;

    /**
     * Creates a widget with default title text. Ascendible by default (See {@link #isAscendible()}).
     */
    public Widget() {
        initialize(DEFAULT_WIDGET_TITLE);
    }

    /**
     * Creates a widget with default title text. Ascendible by default (See {@link #isAscendible()}).
     *
     * @param ascendible used to make it ascendible or not.
     */
    public Widget(boolean ascendible) {
        this.ascendible = ascendible;
        initialize(DEFAULT_WIDGET_TITLE);
    }

    /**
     * Creates a widget with default title text and specified position and size. Ascendible by default (See {@link #isAscendible()}).
     *
     * @param x x position in parent.
     * @param y y position in parent.
     * @param width width of component.
     * @param height height of component.
     */
    public Widget(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(DEFAULT_WIDGET_TITLE);
    }

    /**
     * Creates a widget with default title text and specified position and size. Ascendible by default (See {@link #isAscendible()}).
     *
     * @param x x position in parent.
     * @param y y position in parent.
     * @param width width of component.
     * @param height height of component.
     * @param ascendible used to make it ascendible or not.
     */
    public Widget(float x, float y, float width, float height, boolean ascendible) {
        super(x, y, width, height);
        this.ascendible = ascendible;
        initialize(DEFAULT_WIDGET_TITLE);
    }

    /**
     * Creates a widget with default title text and specified position and size. Ascendible by default (See {@link #isAscendible()}).
     *
     * @param position position in parent.
     * @param size size of component.
     */
    public Widget(Vector2f position, Vector2f size) {
        super(position, size);
        initialize(DEFAULT_WIDGET_TITLE);
    }


    /**
     * Creates a widget with default title text and specified position and size. Ascendible by default (See {@link #isAscendible()}).
     *
     * @param position position in parent.
     * @param size size of component.
     * @param ascendible used to make it ascendible or not.
     */
    public Widget(Vector2f position, Vector2f size, boolean ascendible) {
        super(position, size);
        this.ascendible = ascendible;
        initialize(DEFAULT_WIDGET_TITLE);
    }

    /**
     * Creates a widget with specified title text. Ascendible by default (See {@link #isAscendible()}).
     *
     * @param title widget text.
     */
    public Widget(String title) {
        initialize(title);
    }

    /**
     * Creates a widget with specified title text. Ascendible by default (See {@link #isAscendible()}).
     *
     * @param title widget text.
     * @param ascendible used to make it ascendible or not.
     */
    public Widget(String title, boolean ascendible) {
        this.ascendible = ascendible;
        initialize(title);
    }

    /**
     * Creates a widget with specified title text and specified position and size. Ascendible by default (See {@link #isAscendible()}).
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
     * Creates a widget with specified title text and specified position and size. Ascendible by default (See {@link #isAscendible()}).
     *
     * @param title widget text.
     * @param x x position in parent.
     * @param y y position in parent.
     * @param width width of component.
     * @param height height of component.
     * @param ascendible used to make it ascendible or not.
     */
    public Widget(String title, float x, float y, float width, float height, boolean ascendible) {
        super(x, y, width, height);
        this.ascendible = ascendible;
        initialize(title);
    }

    /**
     * Creates a widget with specified title text and specified position and size. Ascendible by default (See {@link #isAscendible()}).
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
     * Creates a widget with specified title text and specified position and size. Ascendible by default (See {@link #isAscendible()}).
     *
     * @param title widget text.
     * @param position position in parent.
     * @param size size of component.
     * @param ascendible used to make it ascendible or not.
     */
    public Widget(String title, Vector2f position, Vector2f size, boolean ascendible) {
        super(position, size);
        this.ascendible = ascendible;
        initialize(title);
    }

    /**
     * This method used to initialize widget.
     *
     * @param title title to set.
     */
    private void initialize(String title) {
        this.getStyle().setDisplay(DisplayType.FLEX);
        this.getStyle().getFlexStyle().setFlexDirection(FlexDirection.COLUMN);
        this.getStyle().setMinWidth(50f);
        this.getStyle().setMinHeight(50f);
        this.getStyle().setPadding(0f);

        titleContainer = new Panel();
        titleContainer.setTabFocusable(false);

        titleContainer.getStyle().getBackground().setColor(ColorConstants.white());
        titleContainer.getStyle().setDisplay(DisplayType.FLEX);
        titleContainer.getStyle().setPosition(PositionType.RELATIVE);
        titleContainer.getStyle().setHeight((float) INITIAL_TITLE_HEIGHT);
        titleContainer.getStyle().setMinHeight((float) INITIAL_TITLE_HEIGHT);
        titleContainer.getStyle().setMaxHeight((float) INITIAL_TITLE_HEIGHT);
        titleContainer.getStyle().getFlexStyle().setFlexGrow(1);
        titleContainer.getStyle().getFlexStyle().setFlexShrink(1);
        titleContainer.getStyle().getFlexStyle().setFlexDirection(FlexDirection.ROW);

        this.title = new Label(title);
        this.title.getStyle().setPosition(PositionType.RELATIVE);
        this.title.getStyle().setMaxWidth(Float.MAX_VALUE);
        this.title.getStyle().setMaxHeight((float) INITIAL_TITLE_HEIGHT);
        this.title.getStyle().setHeight((float) INITIAL_TITLE_HEIGHT);
        this.title.getStyle().setMinWidth(0f);
        this.title.getStyle().setMinHeight((float) INITIAL_TITLE_HEIGHT);
        this.title.getStyle().getBackground().setColor(ColorConstants.transparent());
        this.title.getStyle().setBorder(null);
        this.title.getStyle().getFlexStyle().setFlexGrow(1);
        this.title.getStyle().getFlexStyle().setFlexShrink(1);
        this.title.setTabFocusable(false);

        mouseDragEventLeguiEventListener = new WidgetDragListener(this);
        this.title.getListenerMap().addListener(MouseDragEvent.class, mouseDragEventLeguiEventListener);

        closeButton = new Button("");

        int iconSize = INITIAL_TITLE_HEIGHT * 2 / 3;
        closeIcon = new CharIcon(new Vector2f(iconSize), FontRegistry.MATERIAL_DESIGN_ICONS, (char) CLOSE_ICON_CHAR,
                                 ColorConstants.black());
        closeIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        closeIcon.setVerticalAlign(VerticalAlign.MIDDLE);

        closeButton.getStyle().setPosition(PositionType.RELATIVE);
        closeButton.getStyle().getBackground().setIcon(closeIcon);
        closeButton.getStyle().getBackground().setColor(ColorConstants.transparent());
        closeButton.getStyle().setMaxWidth((float) INITIAL_TITLE_HEIGHT);
        closeButton.getStyle().setMaxHeight((float) INITIAL_TITLE_HEIGHT);
        closeButton.getStyle().setMinWidth((float) INITIAL_TITLE_HEIGHT);
        closeButton.getStyle().setMinHeight((float) INITIAL_TITLE_HEIGHT);
        closeButton.getStyle().setWidth((float) INITIAL_TITLE_HEIGHT);
        closeButton.getStyle().setHeight((float) INITIAL_TITLE_HEIGHT);
        closeButton.getStyle().setBorder(null);
        closeButton.getStyle().getFlexStyle().setFlexGrow(1);
        closeButton.getStyle().getFlexStyle().setFlexShrink(1);

        closeButton.getListenerMap().addListener(MouseClickEvent.class, new WidgetCloseButMouseClickEventListener(this));
        closeButton.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        closeButton.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        closeButton.setTabFocusable(false);

        minimizeButton = new Button("");
        minimizeButton.setTabFocusable(false);

        minimizeIcon = new CharIcon(new Vector2f(iconSize),
                                    FontRegistry.MATERIAL_DESIGN_ICONS,
                                    (char) MINIMIZE_ICON_CHAR,
                                    ColorConstants.black());
        minimizeIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        minimizeIcon.setVerticalAlign(VerticalAlign.MIDDLE);

        maximizeIcon = new CharIcon(new Vector2f(iconSize),
                                    FontRegistry.MATERIAL_DESIGN_ICONS,
                                    (char) MAXIMIZE_ICON_CHAR,
                                    ColorConstants.black());
        maximizeIcon.setHorizontalAlign(HorizontalAlign.CENTER);
        maximizeIcon.setVerticalAlign(VerticalAlign.MIDDLE);

        minimizeButton.getStyle().getBackground().setColor(ColorConstants.transparent());
        minimizeButton.getStyle().getBackground().setIcon(minimizeIcon);
        minimizeButton.getStyle().setPosition(PositionType.RELATIVE);
        minimizeButton.getStyle().setMaxWidth((float) INITIAL_TITLE_HEIGHT);
        minimizeButton.getStyle().setMaxHeight((float) INITIAL_TITLE_HEIGHT);
        minimizeButton.getStyle().setMinWidth((float) INITIAL_TITLE_HEIGHT);
        minimizeButton.getStyle().setMinHeight((float) INITIAL_TITLE_HEIGHT);
        minimizeButton.getStyle().setWidth((float) INITIAL_TITLE_HEIGHT);
        minimizeButton.getStyle().setHeight((float) INITIAL_TITLE_HEIGHT);
        minimizeButton.getStyle().getFlexStyle().setFlexGrow(1);
        minimizeButton.getStyle().getFlexStyle().setFlexShrink(1);
        minimizeButton.getStyle().setBorder(null);

        minimizeButton.getListenerMap().addListener(MouseClickEvent.class, new WidgetMinimizeButMouseClickEventListener(this));
        minimizeButton.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        minimizeButton.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);

        container = new Panel();
        applyStylesToContainer(container);

        titleContainer.add(this.title);
        titleContainer.add(minimizeButton);
        titleContainer.add(closeButton);

        resizeButton = new Button("");
        CharIcon icon = new CharIcon(FontRegistry.MATERIAL_DESIGN_ICONS, '\uF45D');
        icon.setSize(new Vector2f(20, 20));
        icon.setPosition(new Vector2f(-10, -10));
        resizeButton.getStyle().getBackground().setIcon(icon);
        resizeButton.getStyle().setWidth(10f);
        resizeButton.getStyle().setHeight(10f);
        resizeButton.getStyle().setBottom(0f);
        resizeButton.getStyle().setRight(0f);
        resizeButton.getStyle().setBorder(null);
        resizeButton.getStyle().getBackground().setColor(ColorConstants.transparent());
        resizeButton.setTabFocusable(false);
        resizeButton.getListenerMap().addListener(MouseDragEvent.class, new WidgetResizeButtonDragListener(resizeButton, this));

        this.add(titleContainer);
        this.add(container);
        this.add(resizeButton);

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Widget.class).applyAll(this);
    }

    private void applyStylesToContainer(Component container) {
        container.setTabFocusable(false);
        container.getStyle().getFlexStyle().setFlexShrink(1);
        container.getStyle().getFlexStyle().setFlexGrow(1);
        container.getStyle().setPosition(PositionType.RELATIVE);
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
        this.titleContainer.getStyle().setMinHeight(titleHeight);
        this.titleContainer.getStyle().setHeight(titleHeight);
        this.titleContainer.getStyle().setMaxHeight(titleHeight);
        this.title.getStyle().setMinHeight(titleHeight);
        this.title.getStyle().setHeight(titleHeight);
        this.title.getStyle().setMaxHeight(titleHeight);
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
        this.titleContainer.getStyle().setDisplay(titleEnabled ? DisplayType.FLEX : DisplayType.NONE);
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
        this.closeButton.getStyle().setDisplay(closeable ? DisplayType.MANUAL : DisplayType.NONE);
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

    public Label getTitle() {
        return title;
    }

    /**
     * Returns close button text color.
     *
     * @return close button text color.
     */
    public Vector4f getCloseButtonColor() {
        return closeButton.getStyle().getTextColor();
    }

    /**
     * Used to set close button text color.
     *
     * @param closeButtonColor close button text color to set.
     */
    public void setCloseButtonColor(Vector4f closeButtonColor) {
        this.closeButton.getStyle().setTextColor(closeButtonColor);
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
        this.container = container;
        this.add(1, this.container);
        applyStylesToContainer(this.container);
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
        this.minimizeButton.getStyle().setDisplay(minimizable ? DisplayType.MANUAL : DisplayType.NONE);
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

    public void hide() {
        getStyle().setDisplay(DisplayType.NONE);
    }

    public void show() {
        getStyle().setDisplay(DisplayType.FLEX);
    }

    /**
     * Used to minimize widget.
     */
    private void minimize() {
        if (isTitleEnabled()) {
            Vector2f size = getSize();
            maximizedSize.set(size);

            maximizedMinWidth = getStyle().getMinWidth();
            maximizedMinHeight = getStyle().getMinHeight();
            maximizedMaxWidth = getStyle().getMaxWidth();
            maximizedMaxHeight = getStyle().getMaxHeight();
            maximizedWidth = getStyle().getWidth();
            maximizedHeight = getStyle().getHeight();

            float titleHeight = getTitleHeight();
            size.set(size.x, titleHeight);

            this.getStyle().setMaxHeight(titleHeight);
            this.getStyle().setHeight(titleHeight);
            this.getStyle().setMinHeight(titleHeight);

            if (resizable) {
                resizeButton.getStyle().setDisplay(DisplayType.NONE);
            }
        }
    }

    /**
     * Used to maximize widget.
     */
    private void maximize() {
        if (isTitleEnabled()) {

            this.getSize().set(maximizedSize);

            this.getStyle().setMaxWidth(maximizedMaxWidth);
            this.getStyle().setMaxHeight(maximizedMaxHeight);
            this.getStyle().setMinWidth(maximizedMinWidth);
            this.getStyle().setMinHeight(maximizedMinHeight);
            this.getStyle().setWidth(maximizedWidth);
            this.getStyle().setHeight(maximizedHeight);

            if (resizable) {
                resizeButton.getStyle().setDisplay(DisplayType.MANUAL);
            }
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
        closeButton.getStyle().getBackground().setIcon(closeIcon);
        minimizeButton.getStyle().getBackground().setIcon(minimized ? maximizeIcon : minimizeIcon);
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

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
        this.resizeButton.getStyle().setDisplay(resizable ? DisplayType.MANUAL : DisplayType.NONE);
    }

    public Button getResizeButton() {
        return resizeButton;
    }

    /**
     * Returns true if widget could ascend to the top in parent container.
     *
     * @return true if widget could ascend to the top in parent container.
     */
    public boolean isAscendible() {
        return ascendible;
    }

    /**
     * Set true to make widget ascend to the top in parent container on click event on widget or any component inside.
     *
     * @param ascendible new state.
     */
    public void setAscendible(boolean ascendible) {
        this.ascendible = ascendible;
    }
}
