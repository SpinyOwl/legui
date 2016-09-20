package org.liquidengine.legui.component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class ComponentContainer extends Component {
    protected final List<Component> components = new CopyOnWriteArrayList<>();


    public List<Component> getComponents() {
        return new ArrayList<>(components);
    }
}
