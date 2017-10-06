package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.misc.listener.controller.TooltipCursorEnterListener;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;
import org.liquidengine.legui.theme.Themes;

/**
 * Default component which can hold tooltip component. By default has event listener which adds tooltip to tooltip layer when controller is hovered.
 */
public abstract class Controller extends Component {

    private Tooltip tooltip;

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public Controller() {
        super();
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
    public Controller(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size size of component.
     */
    public Controller(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    /**
     * Initializes controller with default {@link CursorEnterEventListener} to show tooltip. <p> See {@link TooltipCursorEnterListener}.
     */
    private void initialize() {
        CursorEnterEventListener listener = new TooltipCursorEnterListener();
        getListenerMap().addListener(CursorEnterEvent.class, listener);
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Controller.class).applyAll(this);
    }


    /**
     * Returns current tooltip component or null if tooltip doesn't exist.
     *
     * @return current tooltip component or null if tooltip doesn't exist.
     */
    public Tooltip getTooltip() {
        return tooltip;
    }

    /**
     * Used to set text to tooltip and to create tooltip component if doesn't exist.
     *
     * @param tooltip tooltip message.
     */
    public void setTooltip(String tooltip) {
        this.tooltip = new Tooltip(tooltip);
        this.tooltip.setController(this);
    }

    /**
     * Uzed to set custom tooltip component.
     *
     * @param tooltip tooltip component to set.
     */
    public void setTooltipComponent(Tooltip tooltip) {
        this.tooltip = tooltip;
        if (tooltip != null) {
            tooltip.setController(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Controller that = (Controller) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(tooltip, that.tooltip)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(tooltip)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("tooltip", tooltip)
            .toString();
    }

}
