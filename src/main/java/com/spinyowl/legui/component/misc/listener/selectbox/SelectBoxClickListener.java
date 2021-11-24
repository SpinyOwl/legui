package com.spinyowl.legui.component.misc.listener.selectbox;

import static com.spinyowl.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.SelectBox;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;
import org.joml.Vector2f;

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
    if (event.getAction() == CLICK && selectBox.isEnabled()) {
      Frame frame = event.getFrame();
      SelectBox.SelectBoxLayer selectBoxLayer = box.getSelectBoxLayer();
      boolean collapsed = box.isCollapsed();
      box.setCollapsed(!collapsed);
      if (collapsed) {
        Vector2f layerSize = new Vector2f(frame.getContainer().getSize());
        selectBoxLayer.setSize(layerSize);

        frame.addLayer(selectBoxLayer);
      } else {
        frame.removeLayer(selectBoxLayer);
      }
    }
  }
}
