package org.liquidengine.legui.renderer;

import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.exception.LeguiException;

/**
 * Abstract Renderer provider. Used to obtain renderer for component by component class.
 * Created by Alexander on 13.01.2017.
 */
public abstract class LeguiRendererProvider {
    private static LeguiRendererProvider provider = null;

    /**
     * Used to obtain provider instance.
     *
     * @return provider instance.
     */
    public static LeguiRendererProvider getProvider() {
        if (provider != null) {
            return provider;
        } else {
            throw new LeguiException();
        }
    }

    /**
     * Used to set current renderer provider
     *
     * @param provider renderer provider
     */
    public static void setProvider(LeguiRendererProvider provider) {
        LeguiRendererProvider.provider = provider;
    }

    /**
     * Used to obtain renderer for component
     *
     * @param componentClass component class
     * @return renderer for component
     */
    public abstract LeguiComponentRenderer getComponentRenderer(Class<? extends Component> componentClass);

    /**
     * Used to register renderer
     *
     * @param componentClass    component class
     * @param componentRenderer component renderer
     */
    public abstract void registerComponentRenderer(Class<? extends Component> componentClass, LeguiComponentRenderer componentRenderer);

    /**
     * Used to obtain renderer for border
     *
     * @param borderClass border class
     * @return renderer for border
     */
    public abstract LeguiBorderRenderer getBorderRenderer(Class<? extends Border> borderClass);

    /**
     * Used to register renderer
     *
     * @param borderClass    border class
     * @param borderRenderer border renderer
     */
    public abstract void registerBordserRenderer(Class<? extends Border> borderClass, LeguiBorderRenderer borderRenderer);
}
