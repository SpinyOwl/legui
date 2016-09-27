package org.liquidengine.legui.component;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public final class ComponentContainer {
    protected final Set<Component> components = new java.util.concurrent.CopyOnWriteArraySet<>();
    private Component containerHolder;

    public ComponentContainer(Component containerHolder) {
        this.containerHolder = containerHolder;
    }

    public int containerSize() {
        return components.size();
    }

    public boolean isContainerEmpty() {
        return components.isEmpty();
    }

    public boolean containerContains(Object o) {
        return components.contains(o);
    }

    public Iterator<Component> containerIterator() {
        return components.iterator();
    }

    public boolean addComponent(Component component) {
        if (component == containerHolder) return false;
        changeParent(component);
        return components.add(component);
    }

    public boolean addAllComponents(Collection<? extends Component> c) {
        c.forEach(abstractGui -> {
            if (abstractGui != containerHolder) changeParent(abstractGui);
        });
        return components.addAll(c);
    }

    private void changeParent(Component element) {
        Component parent = element.parent;
        if (parent != null && (parent instanceof ContainerHolder)) {
            ComponentContainer container = ((ContainerHolder) parent).getContainer();
            container.removeComponent(element);
        }
        element.parent = containerHolder;
    }

    public boolean removeComponent(Component component) {
        if (component.parent != null && component.parent == containerHolder && components.contains(component)) {
            component.parent = null;
            return components.remove(component);
        }
        return false;
    }

    public void removeAllComponents(Collection<? extends Component> c) {
        c.forEach(compo -> compo.parent = null);
        components.removeAll(c);
    }

    public boolean removeComponentIf(Predicate<? super Component> filter) {
        components.stream().filter(filter).forEach(compo -> compo.parent = null);
        return components.removeIf(filter);
    }

    public void clearComponents() {
        components.forEach(compo -> compo.parent = null);
        components.clear();
    }

    public boolean containerContainsAll(Collection<?> c) {
        return components.containsAll(c);
    }

    public Stream<Component> componentStream() {
        return components.stream();
    }

    public Stream<Component> componentParallelStream() {
        return components.parallelStream();
    }

    public void forEachComponent(Consumer<? super Component> action) {
        components.forEach(action);
    }

    public List<Component> getComponents() {
        return new ArrayList<>(components);
    }

}
