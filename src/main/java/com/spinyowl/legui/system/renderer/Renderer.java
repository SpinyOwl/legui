package com.spinyowl.legui.system.renderer;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;


public interface Renderer {

  void initialize();

  void render(Frame frame, Context context);

  void destroy();
}
