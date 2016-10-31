package org.liquidengine.legui.component;

import org.joml.Vector2f;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class ComponentContainer extends Component {
    protected final Set<Component> components = new java.util.concurrent.CopyOnWriteArraySet<>();

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

    public boolean containerContains(Component o) {
        return components.contains(o);
    }

    public Iterator<Component> containerIterator() {
        return components.iterator();
    }

    public boolean addComponent(Component component) {
        if (component == this) return false;
        changeParent(component);
        return components.add(component);
    }

    public boolean addAllComponents(Collection<? extends Component> c) {
        c.forEach(abstractGui -> {
            if (abstractGui != this) changeParent(abstractGui);
        });
        return components.addAll(c);
    }

    private void changeParent(Component element) {
        Component parent = element.parent;
        if (parent != null && (parent instanceof ComponentContainer)) {
            ComponentContainer container = ((ComponentContainer) parent);
            container.removeComponent(element);
        }
        element.parent = this;
    }

    public boolean removeComponent(Component component) {
        if (component.parent != null && component.parent == this && components.contains(component)) {
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

    @Override
    public Component getComponentAt(Vector2f cursorPosition) {
        Component componentAt = super.getComponentAt(cursorPosition);
        if (componentAt != null) {
            for (Component child : components) {
                Component childComponentAt = child.getComponentAt(cursorPosition);
                componentAt = childComponentAt == null ? componentAt : childComponentAt;
            }
        }
        return componentAt;
    }
}
