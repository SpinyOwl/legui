package org.liquidengine.legui.theme;

import org.liquidengine.legui.component.Component;


/**
 * This interface defines method which can be used to change theme only for provided component.
 * Childs and parent components should not be changed by this method.
 */
public interface IComponentTheme<T extends Component> {

    void apply(T component);
}
