package org.liquidengine.legui.component;

import org.joml.Vector2f;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class Layer {
    protected LayerContainer container = new LayerContainer(this);
    protected Layer   botLayer;
    protected Layer   topLayer;
    protected Frame   frame;
    protected boolean permeable;

    public Layer() {
    }

    public Layer(boolean permeable) {
        this.permeable = permeable;
    }

    public LayerContainer getContainer() {
        return container;
    }

    public Frame getFrame() {
        return frame;
    }

    public Layer getBotLayer() {
        return botLayer;
    }

    public Layer getTopLayer() {
        return topLayer;
    }

    public boolean isPermeable() {
        return permeable;
    }

    public void setPermeable(boolean permeable) {
        this.permeable = permeable;
    }

    public int componentsCount() {
        return container.componentsCount();
    }

    public boolean isContainerEmpty() {
        return container.isContainerEmpty();
    }

    public boolean containsComponent(Component component) {
        return container.containsComponent(component);
    }

    public Iterator<Component> containerIterator() {
        return container.containerIterator();
    }

    public boolean addComponent(Component component) {
        return container.addComponent(component);
    }

    public boolean addAllComponents(Collection<? extends Component> components) {
        return container.addAllComponents(components);
    }

    public boolean removeComponent(Component component) {
        return container.removeComponent(component);
    }

    public void removeAllComponents(Collection<? extends Component> components) {
        container.removeAllComponents(components);
    }

    public boolean removeComponentIf(Predicate<? super Component> filter) {
        return container.removeComponentIf(filter);
    }

    public void clearComponents() {
        container.clearComponents();
    }

    public boolean containerContainsAll(Collection<?> components) {
        return container.containerContainsAll(components);
    }

    public Stream<Component> componentStream() {
        return container.componentStream();
    }

    public Stream<Component> componentParallelStream() {
        return container.componentParallelStream();
    }

    public void forEachComponent(Consumer<? super Component> action) {
        container.forEachComponent(action);
    }

    public List<Component> getComponents() {
        return container.getComponents();
    }

    public Component getComponentAt(Vector2f cursorPosition) {
        return container.getComponentAt(cursorPosition);
    }

    public static class LayerContainer extends ComponentContainer {
        protected Layer layer;

        public LayerContainer(Layer layer) {
            this.layer = layer;
        }

        public Layer getLayer() {
            return layer;
        }
    }
}
