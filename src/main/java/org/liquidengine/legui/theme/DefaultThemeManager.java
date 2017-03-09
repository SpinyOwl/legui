package org.liquidengine.legui.theme;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.Widget;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultThemeManager extends ThemeManager {
    private Map<Class<? extends Component>, IComponentTheme<? extends Component>> themeMap = new ConcurrentHashMap<>();

    private IComponentTheme defaultComponentTheme = c -> apply(c);

    private void apply(Component c) {
    }

    @Override
    public void applyAll(Component component) {
        IComponentTheme componentTheme = getComponentTheme(component.getClass());
        componentTheme.apply(component);
        if (component instanceof Container) {
            if (component instanceof Widget) {

            } else if (component instanceof ScrollablePanel) {
            } else {
                Container       container = (Container) component;
                List<Component> childs    = container.getChilds();
                for (Component child : childs) {
                    applyAll(child);
                }
            }
        }
    }

    @Override
    public <T extends Component> IComponentTheme<T> getComponentTheme(Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null!");
        }
        IComponentTheme<T> theme = this.<T, IComponentTheme<T>>cycledSearch(clazz, themeMap, defaultComponentTheme);
        return theme;
    }

    @Override
    public <T extends Component> void setComponentTheme(Class<T> clazz, IComponentTheme<T> theme) {
        if (theme == null) {
            throw new NullPointerException("Theme cannot be null!");
        }
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null!");
        }
        themeMap.put(clazz, theme);
    }

    protected <C, R> R cycledSearch(Class<C> componentClass, Map map, R defaultComponentTheme) {
        R     componentTheme = null;
        Class cClass         = componentClass;
        while (componentTheme == null) {
            componentTheme = ((Map<Class<C>, R>) map).get(cClass);
            if (cClass.isAssignableFrom(Component.class)) break;
            cClass = cClass.getSuperclass();
        }
        if (componentTheme == null) componentTheme = defaultComponentTheme;
        return componentTheme;
    }

}
