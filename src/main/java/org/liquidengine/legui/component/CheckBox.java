package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.intersector.RectangleIntersector;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;

/**
 * Created by Shcherbin Alexander on 9/23/2016.
 */
public class CheckBox extends Component {
    protected TextState textState;
    protected boolean checked;

    public CheckBox() {
        this("CheckBox");
    }

    public CheckBox(float x, float y, float width, float height) {
        this(x, y, width, height, "CheckBox");
    }

    public CheckBox(Vector2f position, Vector2f size) {
        this(position, size, "CheckBox");
    }

    public CheckBox(String text) {
        initialize(text);
    }

    public CheckBox(float x, float y, float width, float height, String text) {
        super(x, y, width, height);
        initialize(text);
    }

    public CheckBox(Vector2f position, Vector2f size, String text) {
        super(position, size);
        initialize(text);
    }

    private void initialize(String text) {
        this.textState = new TextState(text);
        intersector = new RectangleIntersector();
        backgroundColor.set(0f);
        textState.setHorizontalAlign(HorizontalAlign.CENTER);
    }

    public TextState getTextState() {
        return textState;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
