package org.liquidengine.legui.component;

import static org.liquidengine.legui.component.optional.Orientation.HORIZONTAL;

import java.util.Objects;
import org.joml.Vector2f;
import org.liquidengine.legui.component.misc.listener.splitpanel.SplitPanelDragListener;
import org.liquidengine.legui.component.misc.listener.splitpanel.SplitPanelSeparatorClickListener;
import org.liquidengine.legui.component.misc.listener.splitpanel.SplitPanelSeparatorCursorEnterListener;
import org.liquidengine.legui.component.misc.listener.splitpanel.SplitPanelSeparatorListenerDelegate;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.flex.FlexStyle;
import org.liquidengine.legui.style.font.TextDirection;
import org.liquidengine.legui.theme.Themes;

public class SplitPanel extends Panel {

    private Orientation orientation;

    private Component topLeft;
    private Component bottomRight;

    private SeparatorButton separator;

    private float ratio = 50f;

    private float separatorThickness = 4f;

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     *
     * @param orientation used to specify orientation of split panel. If orientation is VERTICAL then two components will be located vertically.
     */
    public SplitPanel(Orientation orientation) {
        this.orientation = Objects.requireNonNull(orientation);
        initialize();
    }

    private void initialize() {
        separator = new SeparatorButton();
        topLeft = new Panel();
        bottomRight = new Panel();

        separator.getTextState().setText("");

        this.add(topLeft);
        this.add(separator);
        this.add(bottomRight);
        SplitPanelSeparatorListenerDelegate delegate = new SplitPanelSeparatorListenerDelegate(this);
        separator.getListenerMap().addListener(MouseClickEvent.class, new SplitPanelSeparatorClickListener(delegate));
        separator.getListenerMap().addListener(CursorEnterEvent.class, new SplitPanelSeparatorCursorEnterListener(delegate));
        separator.getListenerMap().addListener(MouseDragEvent.class, new SplitPanelDragListener(this));
        separator.setTextDirection(
            orientation != HORIZONTAL ? TextDirection.HORIZONTAL : TextDirection.VERTICAL_TOP_DOWN);

        separator.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        separator.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        separator.setTabFocusable(false);
        separator.getStyle().setBorderRadius(0);

        topLeft.getStyle().setBorderRadius(0);
        topLeft.setTabFocusable(false);

        bottomRight.getStyle().setBorderRadius(0);
        bottomRight.setTabFocusable(false);

        resetStyle();

        Themes.getDefaultTheme().applyAll(this);

        topLeft.getStyle().getBackground().setColor(ColorConstants.lightGreen());
        bottomRight.getStyle().getBackground().setColor(ColorConstants.lightBlue());
    }

    private void updateGrow() {
        topLeft.getStyle().getFlexStyle().setFlexGrow(getLeftGrow());
        bottomRight.getStyle().getFlexStyle().setFlexGrow(getRightGrow());
    }

    @Override
    public void setSize(Vector2f size) {
        super.setSize(size);
        resetStyle();
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        resetStyle();
    }

    private int getLeftGrow() {
        return (int) (ratio * getActualSpace() / 100f);
    }

    private int getRightGrow() {
        return (int) ((100 - ratio) * getActualSpace() / 100f);
    }

    private float getActualSpace() {
        float actual;
        if (HORIZONTAL == orientation) {
            actual = this.getSize().x - separatorThickness;
        } else {
            actual = this.getSize().y - separatorThickness;
        }
        return actual;
    }

    public void resetStyle() {
        this.getStyle().setDisplay(Style.DisplayType.FLEX);

        if (orientation == Orientation.VERTICAL) {
            this.getStyle().getFlexStyle().setFlexDirection(FlexStyle.FlexDirection.COLUMN);

            this.separator.getStyle().setMaxHeight(separatorThickness);
            this.separator.getStyle().setMinHeight(separatorThickness);
            this.separator.getStyle().setMaxWidth(null);
            this.separator.getStyle().setMinWidth(null);
        } else {
            this.getStyle().getFlexStyle().setFlexDirection(FlexStyle.FlexDirection.ROW);

            this.separator.getStyle().setMaxHeight(null);
            this.separator.getStyle().setMinHeight(null);
            this.separator.getStyle().setMaxWidth(separatorThickness);
            this.separator.getStyle().setMinWidth(separatorThickness);
        }

        resetSeparator();
        resetTopLeft();
        resetBottomRight();
    }

    private void resetSeparator() {
        separator.getStyle().setPosition(Style.PositionType.RELATIVE);
        separator.getStyle().getFlexStyle().setFlexGrow(1);
        separator.getStyle().getFlexStyle().setFlexShrink(1);
    }

    private void resetBottomRight() {
        bottomRight.getStyle().setPosition(Style.PositionType.RELATIVE);
        bottomRight.getStyle().getFlexStyle().setFlexGrow(getRightGrow());
        bottomRight.getStyle().getFlexStyle().setFlexShrink(1);
    }

    private void resetTopLeft() {
        topLeft.getStyle().setPosition(Style.PositionType.RELATIVE);
        topLeft.getStyle().getFlexStyle().setFlexGrow(getLeftGrow());
        topLeft.getStyle().getFlexStyle().setFlexShrink(1);
    }

    public float getSeparatorThickness() {
        return separatorThickness;
    }

    public void setSeparatorThickness(float separatorThickness) {
        if (separatorThickness <= 0) {
            return;
        }
        this.separatorThickness = separatorThickness;
        if (HORIZONTAL == orientation) {
            separator.getStyle().setMaxWidth(separatorThickness);
            separator.getStyle().setMinWidth(separatorThickness);
        } else {
            separator.getStyle().setMaxHeight(separatorThickness);
            separator.getStyle().setMinHeight(separatorThickness);
        }
    }

    public Component getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Component topLeft) {
        if (topLeft != null) {
            this.remove(this.topLeft);
            this.topLeft = topLeft;
            this.add(0, topLeft);
            resetTopLeft();
        }
    }

    public Component getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Component bottomRight) {
        if (bottomRight != null) {
            this.remove(this.bottomRight);
            this.bottomRight = bottomRight;
            this.add(bottomRight);
            resetBottomRight();
        }
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        if (ratio < 0f) {
            ratio = 0f;
        } else if (ratio > 100f) {
            ratio = 100f;
        }
        this.ratio = ratio;

        updateGrow();
    }

    public Button getSeparator() {
        return separator;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        if (orientation == null) {
            return;
        }
        this.orientation = orientation;
        resetStyle();
    }

    private static class SeparatorButton extends Button {

    }
}
