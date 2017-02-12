package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;

/**
 * Created by ShchAlexander on 01.02.2017.
 */
public abstract class Controller extends Component {
    private Tooltip tooltip;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json serializer/deserializer component should contain empty constructor.
     */
    public Controller() {
        super();
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component
     * @param y      y position position in parent component
     * @param width  width of component
     * @param height height of component
     */
    public Controller(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component
     * @param size     size of component
     */
    public Controller(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    private void initialize() {
        CursorEnterEventListener listener = new CursorEnterListenerForTooltip(this);
        getListenerMap().addListener(CursorEnterEvent.class, listener);
    }


    public Tooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = new Tooltip(tooltip);
        this.tooltip.setController(this);
    }

    public void setTooltipComponent(Tooltip tooltip) {
        this.tooltip = tooltip;
        tooltip.setController(this);
    }

    public static class CursorEnterListenerForTooltip implements CursorEnterEventListener {
        private final Controller controller;

        public CursorEnterListenerForTooltip(Controller controller) {
            this.controller = controller;
        }

        @Override
        public void process(CursorEnterEvent event) {
            Tooltip tooltip = controller.tooltip;
            if (event.getComponent() == controller && tooltip != null) {
                if (event.isEntered()) {
                    event.getFrame().getTooltipLayer().getContainer().add(tooltip);
                    tooltip.setVisible(true);
                    System.out.println("IN: " + tooltip);
                } else {
                    System.out.println("OUT: " + tooltip);
                    tooltip.setVisible(false);
                }
            }
        }
    }
}
