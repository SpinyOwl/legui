package org.liquidengine.legui.theme.white.def;

import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.theme.Themes;

/**
 * White Component Theme for all components. Used to make component white.
 *
 * @param <T> {@link Container} subclasses.
 */
public class WhiteContainerTheme<T extends Container> extends WhiteControllerTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
    }

    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        List<? extends Component> childs = component.getChilds();
        for (Component child : childs) {
            Themes.getDefaultTheme().applyAll(child);
        }
    }
}
