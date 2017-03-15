package org.liquidengine.legui.theme;

import org.liquidengine.legui.component.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultThemeManager extends ThemeManager {
    private Map<Class<? extends Component>, AbstractTheme<? extends Component>> themeMap = new ConcurrentHashMap<>();

    private AbstractTheme defaultComponentTheme = new AbstractTheme() {
        @Override
        public void apply(Component component) {
        }

        @Override
        public void applyAll(Component component) {
        }
    };

//    private void apply(Component c) {
//    }
//
//    @Override
//    public void applyAll(Component component) {
//        AbstractTheme componentTheme = getComponentTheme(component.getClass());
//        componentTheme.apply(component);
//        if (component instanceof Container) {
//            if (component instanceof Widget) {
//
//            } else if (component instanceof ScrollablePanel) {
//            } else {
//                Container       container = (Container) component;
//                List<Component> childs    = container.getChilds();
//                for (Component child : childs) {
//                    applyAll(child);
//                }
//            }
//        }
//    }

    @Override
    public <T extends Component> AbstractTheme<T> getComponentTheme(Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null!");
        }
        AbstractTheme<T> theme = this.<T, AbstractTheme<T>>cycledSearch(clazz, themeMap, defaultComponentTheme);
        return theme;
    }

    @Override
    public <T extends Component> void setComponentTheme(Class<T> clazz, AbstractTheme<T> theme) {
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
