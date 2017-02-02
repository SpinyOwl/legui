package org.liquidengine.legui.component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public interface Container<T extends Component> {
    /**
     * Returns count of child components.
     *
     * @return count of child components.
     * @see List#size()
     */
    int count();

    /**
     * Returns true if layerFrame contains no elements.
     *
     * @return true if layerFrame contains no elements.
     * @see List#isEmpty()
     */
    boolean isEmpty();

    /**
     * Returns true if layerFrame contains specified component.
     *
     * @param component component to check.
     * @return true if layerFrame contains specified component.
     * @see List#contains(Object)
     */
    boolean contains(T component);

    /**
     * Returns an iterator over the elements in this layerFrame.
     * The elements are returned in no particular order.
     *
     * @return an iterator over the elements in this layerFrame.
     * @see List#iterator()
     */
    Iterator<T> containerIterator();

    /**
     * Used to add component to layerFrame.
     *
     * @param component component to add.
     * @return true if component is added.
     * @see List#add(Object)
     */
    boolean add(T component);

    /**
     * Used to add components.
     *
     * @param components components nodes to add.
     * @return true if added.
     * @see List#addAll(Collection)
     */
    boolean addAll(Collection<? extends T> components);

    /**
     * Used to remove component.
     *
     * @param component component to remove.
     * @return true if removed.
     * @see List#remove(Object)
     */
    boolean remove(T component);

    /**
     * Used to remove components.
     *
     * @param components components to remove.
     * @see List#removeAll(Collection)
     */
    void removeAll(Collection<? extends T> components);

    /**
     * Removes all of the elements of this layerFrame
     * that satisfy the given predicate.
     * Errors or runtime exceptions
     * thrown during iteration or by
     * the predicate are relayed to the caller.
     *
     * @param filter a predicate which returns true for elements to be removed.
     * @return true if any components were removed.
     * @see List#removeIf(Predicate)
     */
    boolean removeIf(Predicate<? super T> filter);

    /**
     * Used to remove all child components from layerFrame.
     *
     * @see List#clear()
     */
    void clear();

    /**
     * Returns true if this ComponentContainer contains all of the elements of the specified collection.
     *
     * @param components components collection to check.
     * @return true if this ComponentContainer contains all of the elements of the specified collection.
     * @see List#containsAll(Collection)
     */
    boolean containsAll(Collection<T> components);

    /**
     * Returns a sequential Stream with this collection as its source.
     *
     * @return a sequential Stream with this collection as its source.
     * @see List#stream()
     */
    Stream<T> stream();

    /**
     * Returns a possibly parallel Stream with this collection as its source.
     * It is allowable for this method to return a sequential stream.
     *
     * @return possibly parallel Stream with this collection as its source.
     * @see List#parallelStream()
     */
    Stream<T> parallelStream();

    /**
     * Performs the given action for each element of the Iterable
     * until all elements have been processed or the action throws an exception.
     *
     * @param action The action to be performed for each element.
     */
    void forEach(Consumer<? super T> action);

    /**
     * Used to retrieve child components as {@link List}
     * <p>
     * <p>
     * <span style="color:red">NOTE: this method returns NEW {@link List} of components</span>
     *
     * @return list of child components.
     */
    List<T> getAll();

}
