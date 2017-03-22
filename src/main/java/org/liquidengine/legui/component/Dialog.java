package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.theme.Theme;

/**
 * Dialog component is component which extended from {@link Widget} and have some additional functionality.
 * On {@link #show(Frame)} method it added to new created {@link DialogLayer}
 * which forbid underlying layers to receive mouse click and keyboard events.
 */
public class Dialog extends Widget {
    /**
     * Used to hold dialog layer with dialog.
     */
    private Frame       frame;
    /**
     * Used to hold dialog.
     */
    private DialogLayer dialogLayer;

    /**
     * Creates a dialog with default title text.
     */
    public Dialog() {
        initialize(null, "Dialog");
    }

    /**
     * Creates a dialog with default title text and specified position and size.
     *
     * @param width  width of component.
     * @param height height of component.
     */
    public Dialog(float width, float height) {
        initialize(new Vector2f(width, height), "Dialog");
    }

    /**
     * Creates a dialog with default title text and specified position and size.
     *
     * @param size size of component.
     */
    public Dialog(Vector2f size) {
        initialize(size, "Dialog");
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
        this.getListenerMap().addListener(WidgetCloseEvent.class, new DialogCloseEventListener());
        Theme.getDefaultTheme().getThemeManager().getComponentTheme(Dialog.class).applyAll(this);
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

            if (dialogLayer == null) {
                dialogLayer = new DialogLayer();
            }
            this.frame.addLayer(dialogLayer);
            dialogLayer.getContainer().setSize(dialogLayerSize);
            dialogLayer.getContainer().add(this);
        }
    }

    /**
     * Used to close dialog.
     */
    public void close() {
        closeDialog();
    }

    /**
     * Used to close dialog.
     */
    private void closeDialog() {
        if (frame != null) {
            frame.removeLayer(dialogLayer);
        }
    }

    /**
     * Dialog layer used to hold dialog window.
     */
    public static class DialogLayer extends Layer<Dialog> {

        /**
         * Default constructor of dialog layer.
         */
        public DialogLayer() {
            setEventPassable(false);
            setEventReceivable(true);
            getContainer().setBackgroundColor(0, 0, 0, 0.2f);
        }
    }

    /**
     * Close event listener for dialog.
     * When dialog closed dialog layer with dialog
     * should be removed from frame.
     */
    public class DialogCloseEventListener implements WidgetCloseEventListener<WidgetCloseEvent> {

        /**
         * Used to process {@link WidgetCloseEvent} event.
         *
         * @param event event to process.
         */
        @Override
        public void process(WidgetCloseEvent event) {
            closeDialog();
        }
    }
}
