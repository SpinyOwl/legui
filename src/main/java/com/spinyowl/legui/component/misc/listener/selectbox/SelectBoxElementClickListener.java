package com.spinyowl.legui.component.misc.listener.selectbox;

import static com.spinyowl.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import com.spinyowl.legui.component.SelectBox;
import com.spinyowl.legui.component.event.selectbox.SelectBoxChangeSelectionEvent;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;


public class SelectBoxElementClickListener<T> implements MouseClickEventListener {

  private SelectBox<T> selectBox;

  public SelectBoxElementClickListener(SelectBox<T> selectBox) {
    this.selectBox = selectBox;
  }

  @Override
  public void process(MouseClickEvent event) {
    SelectBox<T>.SelectBoxElement<T> component = (SelectBox<T>.SelectBoxElement<T>) event.getTargetComponent();
    if (event.getAction() == CLICK && event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_1)
        && selectBox.isEnabled()) {
      T selection = selectBox.getSelection();
      T newValue = component.getObject();
      selectBox.setSelected(newValue, true);
      EventProcessorProvider.getInstance().pushEvent(
          new SelectBoxChangeSelectionEvent<>(selectBox, event.getContext(), event.getFrame(),
              selection, newValue));
      selectBox.setCollapsed(true);
      event.getFrame().removeLayer(selectBox.getSelectBoxLayer());
    }
  }
}
