package org.liquidengine.legui.component;

import org.apache.commons.collections4.list.SetUniqueList;
import org.joml.Vector2f;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Basic abstract ComponentContainer object is a component
 * that can contain other components.
 * <p>
 * The base of container is <b><span style="color:red">Set</span> created on base of <span style="color:red">IdentityHashMap</span></b>,
 * that's little restriction which determines that child
 * can exist in parent only one time. When adding component it compares only <span style="color:red">references</span>.
 * <p>
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class ComponentContainer extends Component {
    protected final List<Component> components = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());

    /**
     * Default constructor. Used to create instance without any parameters.
     */
    public ComponentContainer() {
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component
     * @param y      y position position in parent component
     * @param width  width of component
     * @param height height of component
     */
    public ComponentContainer(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component
     * @param size     size of component
     */
    public ComponentContainer(Vector2f position, Vector2f size) {
        super(position, size);
    }

    /**
     * Returns count of child components.
     *
     * @return count of child components.
     * @see Set#size()
     */
    public int componentsCount() {
        return components.size();
    }

    /**
     * Returns true if container contains no elements.
     *
     * @return true if container contains no elements.
     * @see Set#isEmpty()
     */
    public boolean isContainerEmpty() {
        return components.isEmpty();
    }

    /**
     * Returns true if container contains specified component.
     *
     * @param component component to check.
     * @return true if container contains specified component.
     * @see Set#contains(Object)
     */
    public boolean containsComponent(Component component) {
        return components.contains(component);
    }

    /**
     * Returns an iterator over the elements in this container.
     * The elements are returned in no particular order.
     *
     * @return an iterator over the elements in this container.
     * @see Set#iterator()
     */
    public Iterator<Component> containerIterator() {
        return components.iterator();
    }

    /**
     * Used to add component to container.
     *
     * @param component component to add.
     * @return true if component is added.
     * @see Set#add(Object)
     */
    public boolean addComponent(Component component) {
        if (component == null || component == this) return false;
        changeParent(component);
        return components.add(component);
    }

    /**
     * Used to add components.
     *
     * @param components components nodes to add.
     * @return true if added.
     * @see Set#addAll(Collection)
     */
    public boolean addAllComponents(Collection<? extends Component> components) {
        if (components != null) {
            components.forEach(abstractGui -> {
                if (abstractGui != this) changeParent(abstractGui);
            });
            return this.components.addAll(components);
        } else {
            return false;
        }
    }

    /**
     * Used to change parent of added component.
     *
     * @param component component to change.
     */
    private void changeParent(Component component) {
        if (component != null) {
            Component parent = component.parent;
            if (parent != null) {
                ComponentContainer container = ((ComponentContainer) parent);
                container.removeComponent(component);
            }
            component.parent = this;
        }
    }

    /**
     * Used to remove component.
     *
     * @param component component to remove.
     * @return true if removed.
     * @see Set#remove(Object)
     */
    public boolean removeComponent(Component component) {
        if (component.parent != null && component.parent == this && components.contains(component)) {
            component.parent = null;
            return components.remove(component);
        }
        return false;
    }

    /**
     * Used to remove components.
     *
     * @param components components to remove.
     * @see Set#removeAll(Collection)
     */
    public void removeAllComponents(Collection<? extends Component> components) {
        components.forEach(compo -> compo.parent = null);
        this.components.removeAll(components);
    }

    /**
     * Removes all of the elements of this container
     * that satisfy the given predicate.
     * Errors or runtime exceptions
     * thrown during iteration or by
     * the predicate are relayed to the caller.
     *
     * @param filter a predicate which returns true for elements to be removed.
     * @return true if any components were removed.
     * @see Set#removeIf(Predicate)
     */
    public boolean removeComponentIf(Predicate<? super Component> filter) {
        components.stream().filter(filter).forEach(compo -> compo.parent = null);
        return components.removeIf(filter);
    }

    /**
     * Used to remove all child components from container.
     *
     * @see Set#clear()
     */
    public void clearComponents() {
        components.forEach(compo -> compo.parent = null);
        components.clear();
    }

    /**
     * Returns true if this ComponentContainer contains all of the elements of the specified collection.
     *
     * @param components components collection to check.
     * @return true if this ComponentContainer contains all of the elements of the specified collection.
     * @see Set#containsAll(Collection)
     */
    public boolean containerContainsAll(Collection<?> components) {
        return this.components.containsAll(components);
    }

    /**
     * Returns a sequential Stream with this collection as its source.
     *
     * @return a sequential Stream with this collection as its source.
     * @see Set#stream()
     */
    public Stream<Component> componentStream() {
        return components.stream();
    }

    /**
     * Returns a possibly parallel Stream with this collection as its source.
     * It is allowable for this method to return a sequential stream.
     *
     * @return possibly parallel Stream with this collection as its source.
     * @see Set#parallelStream()
     */
    public Stream<Component> componentParallelStream() {
        return components.parallelStream();
    }

    /**
     * Performs the given action for each element of the Iterable
     * until all elements have been processed or the action throws an exception.
     *
     * @param action The action to be performed for each element.
     */
    public void forEachComponent(Consumer<? super Component> action) {
        components.forEach(action);
    }

    /**
     * Used to retrieve child components as {@link List}
     * <p>
     * <p>
     * <span style="color:red">NOTE: this method returns {@link List} of components when components stored as {@link Set}</span>
     *
     * @return list of child components.
     */
    public List<Component> getComponents() {
        return new ArrayList<>(components);
    }

    /**
     * If this component is composite of several other components
     * it should return one of child components which intersects with cursor
     * else it should return {@code this}.
     * <p>
     * If cursor is outside of component method should return null.
     *
     * @param cursorPosition cursor position
     * @return component at cursor or null.
     */
    @Override
    public Component getComponentAt(Vector2f cursorPosition) {
        Component componentAt = super.getComponentAt(cursorPosition);
        if (componentAt != null) {
            for (Component child : components) {
                if (child.isVisible()) {
                    Component childComponentAt = child.getComponentAt(cursorPosition);
                    componentAt = childComponentAt == null ? componentAt : childComponentAt;
                }
            }
        }
        return componentAt;
    }
}
