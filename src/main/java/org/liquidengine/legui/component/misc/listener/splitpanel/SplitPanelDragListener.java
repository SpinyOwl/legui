package org.liquidengine.legui.component.misc.listener.splitpanel;

import static org.liquidengine.legui.component.optional.Orientation.HORIZONTAL;

import org.joml.Vector2f;
import org.liquidengine.legui.component.SplitPanel;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.listener.MouseDragEventListener;

public class SplitPanelDragListener implements MouseDragEventListener {

    private final SplitPanel splitPanel;

    public SplitPanelDragListener(SplitPanel splitPanel) {
        this.splitPanel = splitPanel;
    }

    /**
     * Used to handle {@link MouseDragEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseDragEvent event) {

        Orientation orientation = splitPanel.getOrientation();
        float ratio = splitPanel.getRatio();
        Vector2f delta = event.getDelta();
        float d;
        if (orientation == HORIZONTAL) {
            d = delta.x;
        } else {
            d = delta.y;
        }
        ratio += 100 * d / getActualSpace(orientation);
        updateGrow(ratio, orientation);
        splitPanel.setRatio(ratio);
    }


    private int getLeftGrow(float ratio, Orientation orientation) {
        return (int) (ratio * getActualSpace(orientation) / 100f);
    }

    private int getRightGrow(float ratio, Orientation orientation) {
        return (int) ((100 - ratio) * getActualSpace(orientation) / 100f);
    }

    private void updateGrow(float ratio, Orientation orientation) {
        splitPanel.getTopLeft().getStyle().getFlexStyle().setFlexGrow(getLeftGrow(ratio, orientation));
        splitPanel.getBottomRight().getStyle().getFlexStyle().setFlexGrow(getRightGrow(ratio, orientation));
    }


    private float getActualSpace(Orientation orientation) {
        float actual;
        if (HORIZONTAL == orientation) {
            actual = splitPanel.getSize().x - splitPanel.getSeparatorThickness();
        } else {
            actual = splitPanel.getSize().y - splitPanel.getSeparatorThickness();
        }
        return actual;
    }
}
