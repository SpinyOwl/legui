package org.liquidengine.legui.component;

import org.joml.Vector2f;
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
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

import static org.liquidengine.legui.component.optional.Orientation.HORIZONTAL;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.PRESS;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.RELEASE;

public class SplitPanel extends Panel {

    private Orientation orientation;

    private Panel topLeftContainer;
    private Component topLeft;

    private Panel bottomRightContainer;
    private Component bottomRight;

    private Button separator;

    private float position = 50f;

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
        separator = new Button();
        topLeftContainer = new Panel();
        bottomRightContainer = new Panel();

        separator.getTextState().setText("");

        this.add(topLeftContainer);
        this.add(separator);
        this.add(bottomRightContainer);

        boolean[] dragging = {false};
        separator.getListenerMap().addListener(MouseClickEvent.class, e -> {
            if (e.getAction() == PRESS) {
                dragging[0] = true;
            }
            if (e.getAction() == RELEASE) {
                dragging[0] = false;
            }
        });
        separator.getListenerMap().addListener(MouseDragEvent.class, event -> {
            Vector2f delta = event.getDelta();
            float d;
            if (orientation == HORIZONTAL) {
                d = delta.x;
            } else {
                d = delta.y;
            }
            position += 100 * d / getActual();
            FlexStyle ls = topLeftContainer.getStyle().getFlexStyle();
            FlexStyle rs = bottomRightContainer.getStyle().getFlexStyle();
            ls.setFlexGrow(getLeft());
            rs.setFlexGrow(getRight());
        });

        separator.getListenerMap().addListener(CursorEnterEvent.class, e -> {
            long window = e.getContext().getGlfwWindow();
            if (e.isEntered() && orientation == HORIZONTAL) {
                GLFW.glfwSetCursor(window, GLFW.glfwCreateStandardCursor(GLFW.GLFW_HRESIZE_CURSOR));
            } else if (e.isEntered() && orientation != HORIZONTAL) {
                GLFW.glfwSetCursor(window, GLFW.glfwCreateStandardCursor(GLFW.GLFW_VRESIZE_CURSOR));
            } else if(!dragging[0]) {
                GLFW.glfwSetCursor(window, GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
            }
        });

        resetStyle();

        Themes.getDefaultTheme().applyAll(this);

        topLeftContainer.getStyle().getBackground().setColor(ColorConstants.lightGreen());
        bottomRightContainer.getStyle().getBackground().setColor(ColorConstants.lightBlue());
    }

    public void setOrientation(Orientation orientation) {
        if (orientation == null) return;
        this.orientation = orientation;
        resetStyle();
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

    private int getLeft() {
        return (int) (position * getActual() / 100f);
    }

    private int getRight() {
        return (int) ((100 - position) * getActual() / 100f);
    }

    private float getActual() {
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
        separator.setTextDirection(orientation != HORIZONTAL ? TextDirection.HORIZONTAL : TextDirection.VERTICAL_TOP_DOWN);

        separator.getStyle().setPosition(Style.PositionType.RELATIVE);
        separator.getStyle().getFlexStyle().setFlexGrow(1);
        separator.getStyle().getFlexStyle().setFlexShrink(1);
        separator.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        separator.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        separator.setTabFocusable(false);

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
        separator.getStyle().setBorderRadius(0);

        topLeftContainer.getStyle().setPosition(Style.PositionType.RELATIVE);
        topLeftContainer.getStyle().getFlexStyle().setFlexGrow(getLeft());
        topLeftContainer.getStyle().getFlexStyle().setFlexShrink(1);
        topLeftContainer.getStyle().setBorderRadius(0);
        topLeftContainer.setTabFocusable(false);

        bottomRightContainer.getStyle().setPosition(Style.PositionType.RELATIVE);
        bottomRightContainer.getStyle().getFlexStyle().setFlexGrow(getRight());
        bottomRightContainer.getStyle().getFlexStyle().setFlexShrink(1);
        bottomRightContainer.getStyle().setBorderRadius(0);
        bottomRightContainer.setTabFocusable(false);

    }

    public Component getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Component topLeft) {
        Component left = this.topLeft;
        if (left != null) {
            topLeftContainer.remove(left);
        }
        this.topLeft = topLeft;
        if (topLeft != null) {
            topLeftContainer.add(topLeft);
        }
    }

    public Component getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Component bottomRight) {
        Component right = this.bottomRight;
        if (right != null) {
            bottomRightContainer.remove(right);
        }
        this.bottomRight = bottomRight;
        if (bottomRight != null) {
            bottomRightContainer.add(right);
        }
    }

    public float getSeparatorThickness() {
        return separatorThickness;
    }

    public void setSeparatorThickness(float separatorThickness) {
        if (separatorThickness <= 0) return;
        this.separatorThickness = separatorThickness;
        if (HORIZONTAL == orientation) {
            separator.getStyle().setMaxWidth(separatorThickness);
            separator.getStyle().setMinWidth(separatorThickness);
        } else {
            separator.getStyle().setMaxHeight(separatorThickness);
            separator.getStyle().setMinHeight(separatorThickness);
        }
    }
}
