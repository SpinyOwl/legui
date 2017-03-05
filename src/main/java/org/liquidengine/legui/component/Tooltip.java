package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.TextState;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public class Tooltip extends Component {
    private TextState  textState;
    private Controller controller;

    public Tooltip() {
        initialize("");
    }

    public Tooltip(String tooltip) {
        initialize(tooltip);
    }

    private void initialize(String text) {
        this.textState = new TextState(text);
    }

    public TextState getTextState() {
        return textState;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        if (this.controller != null) {
            this.controller.setTooltipComponent(null);
        }
        this.controller = controller;
    }

    @Override
    public Vector2f getScreenPosition() {
        Vector2f position       = new Vector2f(getPosition());
        if (controller != null) {
            position.add(controller.getScreenPosition());
        }
        return position;
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

        Tooltip tooltip = (Tooltip) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(textState, tooltip.textState)
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
                .append(textState)
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
                .append("textState", textState)
                .toString();
    }
}
