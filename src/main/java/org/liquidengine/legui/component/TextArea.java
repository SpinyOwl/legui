package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Alexander on 09.10.2016.
 */
public class TextArea extends Component {
    protected TextState textState;

    protected int caretPosition;
    protected int mouseCaretPosition;

    protected int startSelectionIndex;
    protected int endSelectionIndex;

    protected boolean  editable       = true;
    protected Vector4f selectionColor = ColorConstants.lightBlue();

    public TextArea() {
        initialize("");
    }

    public TextArea(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("");
    }

    public TextArea(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("");
    }

    private void initialize(String s) {
        backgroundColor.set(1);
        cornerRadius = 3;
        border = new SimpleRectangleLineBorder(ColorConstants.darkGray(), 1);
        textState = new TextState(s);
        textState.getPadding().set(5, 10, 5, 10);
    }

    public TextState getTextState() {
        return textState;
    }

    public int getCaretPosition() {
        return caretPosition;
    }

    public void setCaretPosition(int caretPosition) {
        int length = getTextState().getText().length();
        this.caretPosition = caretPosition < 0 ? 0 : caretPosition > length ? length : caretPosition;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getMouseCaretPosition() {
        return mouseCaretPosition;
    }

    public void setMouseCaretPosition(int mouseCaretPosition) {
        this.mouseCaretPosition = mouseCaretPosition;
    }

    public int getStartSelectionIndex() {
        return startSelectionIndex;
    }

    public void setStartSelectionIndex(int startSelectionIndex) {
        this.startSelectionIndex = startSelectionIndex;
    }

    public int getEndSelectionIndex() {
        return endSelectionIndex;
    }

    public void setEndSelectionIndex(int endSelectionIndex) {
        this.endSelectionIndex = endSelectionIndex;
    }

    public String getSelection() {
        if (startSelectionIndex < 0 || endSelectionIndex < 0) return null;
        String selection;
        if (startSelectionIndex > endSelectionIndex) {
            selection = textState.substring(endSelectionIndex, startSelectionIndex);
        } else {
            selection = textState.substring(startSelectionIndex, endSelectionIndex);
        }
        return selection;
    }

    public Vector4f getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(Vector4f selectionColor) {
        this.selectionColor = selectionColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TextArea textArea = (TextArea) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(caretPosition, textArea.caretPosition)
                .append(mouseCaretPosition, textArea.mouseCaretPosition)
                .append(editable, textArea.editable)
                .append(startSelectionIndex, textArea.startSelectionIndex)
                .append(endSelectionIndex, textArea.endSelectionIndex)
                .append(textState, textArea.textState)
                .append(selectionColor, textArea.selectionColor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(caretPosition)
                .append(mouseCaretPosition)
                .append(editable)
                .append(startSelectionIndex)
                .append(endSelectionIndex)
                .append(selectionColor)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("textState", textState)
                .append("caretPosition", caretPosition)
                .append("mouseCaretPosition", mouseCaretPosition)
                .append("editable", editable)
                .append("startSelectionIndex", startSelectionIndex)
                .append("endSelectionIndex", endSelectionIndex)
                .append("selectionColor", selectionColor)
                .toString();
    }
}
