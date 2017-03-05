package org.liquidengine.legui.component;

import org.joml.Vector2f;

/**
 * Created by ShchAlexander on 05.03.2017.
 */
public class Dialog extends Widget {
    private Frame       frame;
    private Component   creator;
    private DialogLayer dialogLayer;

    public Dialog(Frame frame, Component creator) {
        initialize(frame, creator, null, null);
    }

    public Dialog(float width, float height, Frame frame, Component creator) {
        initialize(frame, creator, new Vector2f(width, height), null);
    }

    public Dialog(Vector2f size, Frame frame, Component creator) {
        initialize(frame, creator, size, null);
    }

    public Dialog(String title, Frame frame, Component creator) {
        super(title);
        initialize(frame, creator, null, title);
    }

    public Dialog(String title, float width, float height, Frame frame, Component creator) {
        initialize(frame, creator, new Vector2f(width, height), title);
    }

    public Dialog(String title, Vector2f size, Frame frame, Component creator) {
        initialize(frame, creator, size, title);
    }

    private void initialize(Frame frame, Component creator, Vector2f size, String title) {
        if (title != null) {
            getTitleTextState().setText(title);
        } else {
            getTitleTextState().setText("Dialog");
        }
        if (size != null) {
            setSize(size);
        }

        this.frame = frame;
        this.creator = creator;

        this.setMinimizable(false);
        this.getListenerMap().addListener(WidgetCloseEvent.class, new DialogCloseEventListener());
    }

    public void show() {
        Vector2f dialogLayerSize = new Vector2f(frame.getContainer().getSize());
        this.setPosition((dialogLayerSize.x - this.getSize().x) / 2f, (dialogLayerSize.y - this.getSize().y) / 2f);
        this.frame.addLayer(dialogLayer = new DialogLayer(this));
        dialogLayer.getContainer().setSize(dialogLayerSize);
        dialogLayer.getContainer().add(this);
    }

    public void close() {
        closeDialog();
    }

    private void closeDialog() {
        Frame frame = dialogLayer.getFrame();
        if (frame != null) {
            frame.removeLayer(dialogLayer);
        }
    }

    public static class DialogLayer extends Layer<Dialog> {
        private Dialog dialog;

        public DialogLayer(Dialog dialog) {
            this.dialog = dialog;
            setEventPassable(false);
            setEventReceivable(true);
            getContainer().setBackgroundColor(0,0,0,0.2f);
        }
    }

    public class DialogCloseEventListener implements WidgetCloseEventListener {
        @Override
        public void process(WidgetCloseEvent event) {
            System.out.println("CLOSE");
            closeDialog();
        }
    }
}
