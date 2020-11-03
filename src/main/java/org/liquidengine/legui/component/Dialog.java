package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.event.widget.WidgetCloseEvent;
import org.liquidengine.legui.component.misc.listener.dialog.DialogCloseEventListener;
import org.liquidengine.legui.theme.Themes;

/**
 * Dialog component is component which extended from {@link Widget} and have some additional functionality. On {@link #show(Frame)} method it added to new
 * created {@link DialogLayer} which forbid underlying layers to receive mouse click and keyboard events.
 */
public class Dialog extends Widget {

    /**
     * Default dialog title.
     */
    public static final String DEFAULT_DIALOG_TITLE = "Dialog";

    /**
     * Used to hold dialog layer with dialog.
     */
    private Frame frame;
    /**
     * Used to hold dialog.
     */
    private DialogLayer dialogLayer = new DialogLayer();

    /**
     * Creates a dialog with default title text.
     */
    public Dialog() {
        initialize(null, DEFAULT_DIALOG_TITLE);
    }

    /**
     * Creates a dialog with default title text and specified position and size.
     *
     * @param width  width of component.
     * @param height height of component.
     */
    public Dialog(float width, float height) {
        initialize(new Vector2f(width, height), DEFAULT_DIALOG_TITLE);
    }

    /**
     * Creates a dialog with default title text and specified position and size.
     *
     * @param size size of component.
     */
    public Dialog(Vector2f size) {
        initialize(size, DEFAULT_DIALOG_TITLE);
    }

    /**
     * Creates a dialog with specified title text.
     *
     * @param title dialog text.
     */
    public Dialog(String title) {
        super(title);
        initialize(null, title);
    }

    /**
     * Creates a dialog with specified title text and specified position and size.
     *
     * @param title  dialog text.
     * @param width  width of component.
     * @param height height of component.
     */
    public Dialog(String title, float width, float height) {
        initialize(new Vector2f(width, height), title);
    }

    /**
     * Creates a dialog with specified title text and specified position and size.
     *
     * @param title widget text.
     * @param size  size of component.
     */
    public Dialog(String title, Vector2f size) {
        initialize(size, title);
    }

    /**
     * Used to initialize dialog with title and size
     *
     * @param size  size of component.
     * @param title dialog text.
     */
    private void initialize(Vector2f size, String title) {
        getTitleTextState().setText(title);
        if (size != null) {
            setSize(size);
        }

        this.setMinimizable(false);
        this.getListenerMap().addListener(WidgetCloseEvent.class, new DialogCloseEventListener(this));
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Dialog.class).applyAll(this);
    }

    /**
     * Used to show title on specified frame.
     *
     * @param frame frame to show dialog.
     */
    public void show(Frame frame) {
        this.frame = frame;
        if (this.frame != null) {
            Vector2f dialogLayerSize = new Vector2f(frame.getContainer().getSize());
            this.setPosition((dialogLayerSize.x - this.getSize().x) / 2f, (dialogLayerSize.y - this.getSize().y) / 2f);

            this.frame.addLayer(dialogLayer);
            dialogLayer.setSize(dialogLayerSize);
            dialogLayer.add(this);
        }
    }

    public DialogLayer getDialogLayer() {
        return dialogLayer;
    }

    /**
     * Used to close dialog.
     */
    public void close() {
        if (frame != null) {
            frame.removeLayer(dialogLayer);
        }
    }

    /**
     * Dialog layer used to hold dialog window.
     */
    public static class DialogLayer extends Layer {

        /**
         * Default constructor of dialog layer.
         */
        public DialogLayer() {
            setEventPassable(false);
            setEventReceivable(true);

            getStyle().getBackground().setColor(0, 0, 0, 0.2f);
        }
    }
}
