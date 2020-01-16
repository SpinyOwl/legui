package org.liquidengine.legui.theme;

import org.liquidengine.legui.component.Component;


/**
 * This interface defines method which can be used to change theme only for provided component. Children and parent components should not be changed by this
 * method.
 */
public abstract class AbstractTheme<T extends Component> {

    /**
     * Used to apply theme only for component and not apply for child components. Applies changes only for current class part and not for parent part.
     *
     * @param component component to apply theme.
     */
    public void apply(T component) {
        // this is default image marshaller. no need to implement this method.
    }

    /**
     * Used to apply theme for component and for all children of this component. Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    public void applyAll(T component) {
        apply(component);

        for (Component child : component.getChildComponents()) {
            Themes.getDefaultTheme().applyAll(child);
        }
    }

}
