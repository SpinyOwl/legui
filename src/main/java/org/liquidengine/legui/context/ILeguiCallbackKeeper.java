package org.liquidengine.legui.context;

import org.liquidengine.cbchain.*;

/**
 * Created by Alexander on 02.12.2016.
 */
public interface ILeguiCallbackKeeper {

    IChainCharCallback getChainCharCallback();
    IChainDropCallback getChainDropCallback();
    IChainKeyCallback getChainKeyCallback();
    IChainScrollCallback getChainScrollCallback();
    IChainCharModsCallback getChainCharModsCallback();
    IChainCursorEnterCallback getChainCursorEnterCallback();
    IChainFramebufferSizeCallback getChainFramebufferSizeCallback();
    IChainMouseButtonCallback getChainMouseButtonCallback();
    IChainCursorPosCallback getChainCursorPosCallback();
    IChainWindowCloseCallback getChainWindowCloseCallback();
    IChainWindowFocusCallback getChainWindowFocusCallback();
    IChainWindowIconifyCallback getChainWindowIconifyCallback();
    IChainWindowPosCallback getChainWindowPosCallback();
    IChainWindowRefreshCallback getChainWindowRefreshCallback();
    IChainWindowSizeCallback getChainWindowSizeCallback();
}
