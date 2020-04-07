package org.liquidengine.legui.component.misc.listener.selectbox;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.SelectBoxLayer;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * Default mouse click listener for selectbox.
 * <p>
 * Used to expand/collapse selectbox if clicked on it.
 */
public class SelectBoxClickListener<T> implements MouseClickEventListener {

    private SelectBox<T> selectBox;

    public SelectBoxClickListener(SelectBox<T> selectBox) {
        this.selectBox = selectBox;
    }

    @Override
    public void process(MouseClickEvent event) {
        SelectBox<T> box = selectBox;
        if (event.getAction() == CLICK) {
            Frame frame = event.getFrame();
            SelectBoxLayer selectBoxLayer = box.getSelectBoxLayer();
            boolean collapsed = box.isCollapsed();
            box.setCollapsed(!collapsed);
            if (collapsed) {
                Vector2f layerSize = new Vector2f(frame.getContainer().getSize());
                selectBoxLayer.getContainer().setSize(layerSize);

                frame.addLayer(selectBoxLayer);
            } else {
                frame.removeLayer(selectBoxLayer);
            }
        }
    }
}
