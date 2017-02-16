package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;

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
        CursorEnterEventListener listener = new CursorEnterListenerForTooltip(this);
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
     * Default event listener for {@link CursorEnterEvent} to add tooltip to tooltip layer and make it visible or not visible.
     */
    public static class CursorEnterListenerForTooltip implements CursorEnterEventListener {
        private final Controller controller;

        public CursorEnterListenerForTooltip(Controller controller) {
            this.controller = controller;
        }

        @Override
        public void process(CursorEnterEvent event) {
            Tooltip tooltip = controller.getTooltip();
            if (event.getComponent() == controller && tooltip != null) {
                if (event.isEntered()) {
                    event.getContext().getFrame().getTooltipLayer().getContainer().add(tooltip);
                    tooltip.setVisible(true);
                } else {
                    tooltip.setVisible(false);
                }
            }
        }
    }
}
