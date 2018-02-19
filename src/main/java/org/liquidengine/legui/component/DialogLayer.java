package org.liquidengine.legui.component;

/**
 * Dialog layer used to hold dialog window.
 */
public class DialogLayer extends Layer<Dialog> {

    /**
     * Default constructor of dialog layer.
     */
    public DialogLayer() {
        setEventPassable(false);
        setEventReceivable(true);

        getContainer().getStyle().getBackground().setColor(0, 0, 0, 0.2f);
    }
}
