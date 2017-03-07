package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;

import java.util.HashMap;

/**
 * Default component which can hold tooltip component.
 * By default has event listener which adds tooltip to tooltip layer when controller is hovered.
 */
public abstract class Controller extends Component {
    private Tooltip tooltip;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     */
    public Controller() {
        super();
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
    public Controller(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public Controller(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    /**
     * Initializes controller with default {@link CursorEnterEventListener} to show tooltip.
     * <p>
     * See {@link CursorEnterListenerForTooltip}
     */
    private void initialize() {
        CursorEnterEventListener listener = new CursorEnterListenerForTooltip();
        getListenerMap().addListener(CursorEnterEvent.class, listener);
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
        tooltip.setController(this);
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

        Controller that = (Controller) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(tooltip, that.tooltip)
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
                .append(tooltip)
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
                .append("tooltip", tooltip)
                .toString();
    }

    /**
     * Default event listener for {@link CursorEnterEvent} to add tooltip to tooltip layer and make it visible or not visible.
     */
    public static class CursorEnterListenerForTooltip implements CursorEnterEventListener {

        @Override
        public void process(CursorEnterEvent event) {
            Controller controller = (Controller) event.getComponent();
            Tooltip    tooltip    = controller.getTooltip();
            if (tooltip != null) {
                if (event.isEntered()) {
                    event.getContext().getFrame().getTooltipLayer().getContainer().add(tooltip);
                } else {
                    event.getContext().getFrame().getTooltipLayer().getContainer().remove(tooltip);
                }
            }
        }

        /**
         * Indicates whether some other object is "equal to" this one.
         * <p>
         * The {@code equals} method implements an equivalence relation
         * on non-null object references:
         * <ul>
         * <li>It is <i>reflexive</i>: for any non-null reference value
         * {@code x}, {@code x.equals(x)} should return
         * {@code true}.
         * <li>It is <i>symmetric</i>: for any non-null reference values
         * {@code x} and {@code y}, {@code x.equals(y)}
         * should return {@code true} if and only if
         * {@code y.equals(x)} returns {@code true}.
         * <li>It is <i>transitive</i>: for any non-null reference values
         * {@code x}, {@code y}, and {@code z}, if
         * {@code x.equals(y)} returns {@code true} and
         * {@code y.equals(z)} returns {@code true}, then
         * {@code x.equals(z)} should return {@code true}.
         * <li>It is <i>consistent</i>: for any non-null reference values
         * {@code x} and {@code y}, multiple invocations of
         * {@code x.equals(y)} consistently return {@code true}
         * or consistently return {@code false}, provided no
         * information used in {@code equals} comparisons on the
         * objects is modified.
         * <li>For any non-null reference value {@code x},
         * {@code x.equals(null)} should return {@code false}.
         * </ul>
         * <p>
         * The {@code equals} method for class {@code Object} implements
         * the most discriminating possible equivalence relation on objects;
         * that is, for any non-null reference values {@code x} and
         * {@code y}, this method returns {@code true} if and only
         * if {@code x} and {@code y} refer to the same object
         * ({@code x == y} has the value {@code true}).
         * <p>
         * Note that it is generally necessary to override the {@code hashCode}
         * method whenever this method is overridden, so as to maintain the
         * general contract for the {@code hashCode} method, which states
         * that equal objects must have equal hash codes.
         *
         * @param obj the reference object with which to compare.
         * @return {@code true} if this object is the same as the obj
         * argument; {@code false} otherwise.
         * @see #hashCode()
         * @see HashMap
         */
        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
