package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.TextState;

/**
 * Created by Aliaksandr_Shcherbin on 1/27/2017.
 */
public class Tooltip extends Component {
    protected TextState textState;
    protected Component tooltipComponent;

    public Tooltip(Component tooltipComponent) {
        this.tooltipComponent = tooltipComponent;
        initialize(tooltipComponent);
    }

    private void initialize(Component component) {
        textState = new TextState();
        size.set(100, 20);
        textState.setPadding(7, 7, 7, 7);
        cornerRadius = 3f;
    }

    @Override
    public String getTooltipText() {
        return null;
    }

    @Override
    public void setTooltipText(String tooltip) {
    }

    @Override
    public Tooltip getTooltip() {
        return this;
    }

    public TextState getTextState() {
        return textState;
    }

    public Component getTooltipComponent() {
        return tooltipComponent;
    }

    /**
     * Position of tooltip is position relative to tooltip owner component
     *
     * @param position new tooltip position
     */
    @Override
    public void setPosition(Vector2f position) {
        super.setPosition(position);
    }

    /**
     * Position of tooltip is offset relative to tooltip owner component
     *
     * @return tooltip position relative to tooltip owner component
     */
    @Override
    public Vector2f getPosition() {
        return super.getPosition();
    }
}
