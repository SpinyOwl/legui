package org.liquidengine.legui.component;

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
        Vector2f position = new Vector2f(getPosition());
        if (controller != null) {
            position.add(controller.getScreenPosition());
        }
        return position;
    }
}
