package org.liquidengine.legui.component;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class ComponentContainer extends Component {
    protected final List<Component> components = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());

    public ComponentContainer() {
    }

    public ComponentContainer(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public ComponentContainer(Vector2f position, Vector2f size) {
        super(position, size);
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
        return components.add(component);
    }

    public boolean removeComponent(Object o) {
        return components.remove(o);
    }

    public boolean containerContainsAll(Collection<?> c) {
        return components.containsAll(c);
    }

    public boolean addAllComponents(Collection<? extends Component> c) {
        return components.addAll(c);
    }

    public boolean addAllComponents(int index, Collection<? extends Component> c) {
        return components.addAll(index, c);
    }

    public boolean removeAllComponents(Collection<?> c) {
        return components.removeAll(c);
    }

    public void sortComponents(Comparator<? super Component> c) {
        components.sort(c);
    }

    public void clearComponents() {
        components.clear();
    }

    public Component getComponent(int index) {
        return components.get(index);
    }

    public Component setComponent(int index, Component element) {
        return components.set(index, element);
    }

    public void addComponent(int index, Component element) {
        components.add(index, element);
    }

    public Component removeComponent(int index) {
        return components.remove(index);
    }

    public int indexOfComponent(Object o) {
        return components.indexOf(o);
    }

    public boolean removeComponentIf(Predicate<? super Component> filter) {
        return components.removeIf(filter);
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
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
