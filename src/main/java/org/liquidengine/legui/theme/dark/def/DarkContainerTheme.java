package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.theme.Themes;

import java.util.List;

/**
 * Dark Component Theme for all components. Used to make component dark.
 *
 * @param <T> {@link Container} subclasses.
 */
public class DarkContainerTheme<T extends Container> extends DarkControllerTheme<T> {
    /**
     * Used to apply theme for component and for all children of this component.
     * Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        List<? extends Component> childs = component.getChilds();
        for (Component child : childs) {
            Themes.getDefaultTheme().applyAll(child);
        }
    }

    /**
     * Used to apply theme for component and for all children of this component.
     * Applies changes only for current class part and for parent part.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAllWithParent(T component) {
        super.applyAllWithParent(component);
        this.applyAll(component);
    }
}
