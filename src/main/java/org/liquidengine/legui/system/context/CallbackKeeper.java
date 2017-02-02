package org.liquidengine.legui.system.context;

import org.liquidengine.cbchain.*;

/**
 * Interface determines that class which implement it should provide chain callbacks.
 */
public interface CallbackKeeper {
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
