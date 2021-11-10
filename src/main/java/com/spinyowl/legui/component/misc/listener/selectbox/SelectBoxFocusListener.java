package com.spinyowl.legui.component.misc.listener.selectbox;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.SelectBox;
import com.spinyowl.legui.event.FocusEvent;
import com.spinyowl.legui.listener.FocusEventListener;

/**
 * Default focus listener for selectbox. Used to collapse selectbox if it loses focus.
 */
public class SelectBoxFocusListener<T> implements FocusEventListener {

  private SelectBox<T> selectBox;

  public SelectBoxFocusListener(SelectBox<T> selectBox) {
    this.selectBox = selectBox;
  }

  @Override
  public void process(FocusEvent event) {
    if (!event.isFocused() && !selectBox.isCollapsed() && selectBox.isEnabled()) {
      boolean collapse = true;
      Component nextFocus = event.getNextFocus();
      for (SelectBox<T>.SelectBoxElement<T> selectBoxElement : selectBox.getSelectBoxElements()) {
        if (nextFocus == selectBoxElement) {
          collapse = false;
        }
      }
      if (nextFocus == selectBox.getExpandButton() ||
          nextFocus == selectBox.getSelectionButton() ||
          nextFocus == selectBox.getSelectionListPanel() ||
          nextFocus == selectBox.getSelectionListPanel().getVerticalScrollBar()) {
        collapse = false;
      }
      if (selectBox.isCollapsed() != collapse) {
        selectBox.setCollapsed(collapse);
        event.getFrame().removeLayer(selectBox.getSelectBoxLayer());
      }
    }
  }
}
