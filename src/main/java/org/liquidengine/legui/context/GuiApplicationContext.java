package org.liquidengine.legui.context;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public final class GuiApplicationContext {

    // singleton block
    // @formatter:off
    private GuiApplicationContext() {}
    public static GuiApplicationContext getInstance() {return GAC_H.I;}
    private static class GAC_H {private static final GuiApplicationContext I = new GuiApplicationContext();}
    // @formatter:on



}
