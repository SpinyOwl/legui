package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.theme.Themes;

/**
 * Created by ShchAlexander on 1/24/2017.
 */
public class Tooltip extends Component implements TextComponent {

    private TextState textState;
    private Component component;

    /**
     * Constructor.
     */
    public Tooltip() {
        initialize("");
    }

    /**
     * Constructor.
     *
     * @param tooltip text to set.
     */
    public Tooltip(String tooltip) {
        initialize(tooltip);
    }

    /**
     * Used to initialize tooltip.
     *
     * @param text text to set.
     */
    private void initialize(String text) {
        this.textState = new TextState(text);

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Tooltip.class).applyAll(this);
    }

    /**
     * Returns current text state.
     *
     * @return text state of component.
     */
    public TextState getTextState() {
        return textState;
    }

    /**
     * Returns component for which specified this tooltip.
     *
     * @return component for which specified this tooltip.
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Used to set component to toltip.
     *
     * @param component component to set.
     */
    public void setComponent(Component component) {
        // check self
        if (component == this) {
            return;
        }
        // check same component
        if (this.component == component) {
            return;
        }
        // remove tooltip from current component
        if (this.component != null) {
            Component prev = this.component;
            this.component = null;
            prev.setTooltip(null);
        }
        // set new component
        this.component = component;
        // bind this tooltip to component
        if (component != null) {
            component.setTooltip(this);
        }
    }

    /**
     * Returns absolute component position.
     *
     * @return position vector.
     */
    @Override
    public Vector2f getAbsolutePosition() {
        Vector2f position = new Vector2f(getPosition());
        if (component != null) {
            position.add(component.getAbsolutePosition());
        }
        return position;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tooltip tooltip = (Tooltip) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(textState, tooltip.textState)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(textState)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("textState", textState)
            .toString();
    }
}
