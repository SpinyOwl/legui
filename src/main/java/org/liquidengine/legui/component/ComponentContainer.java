package org.liquidengine.legui.component;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public final class ComponentContainer<T extends Component & ContainerHolder> {
    protected final List<Component> components = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());
    private T containerHolder;

    public ComponentContainer(T containerHolder) {
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

    public boolean addAllComponents(int index, Collection<? extends Component> c) {
        c.forEach(abstractGui -> {
            if (abstractGui != containerHolder) changeParent(abstractGui);
        });
        return components.addAll(index, c);
    }

    public void addComponent(int index, Component element) {
        components.add(index, element);
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

    public Component removeComponent(int index) {
        Component remove = components.remove(index);
        remove.parent = null;
        return components.remove(index);
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

    public void sortComponents(Comparator<? super Component> c) {
        components.sort(c);
    }

    public Component getComponent(int index) {
        return components.get(index);
    }

    public Component setComponent(int index, Component element) {
        return components.set(index, element);
    }

    public int indexOfComponent(Object o) {
        return components.indexOf(o);
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


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
