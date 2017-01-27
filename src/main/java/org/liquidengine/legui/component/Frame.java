package org.liquidengine.legui.component;

import org.apache.commons.collections4.list.SetUniqueList;
import org.joml.Vector2f;
import org.liquidengine.legui.event.system.SystemWindowSizeEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.util.ColorConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Aliaksandr_Shcherbin on 12/13/2016.
 */
public class Frame {
    protected Layer componentLayer;
    protected List<Layer> layers = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());
    protected Layer tooltipLayer;

    public Frame() {
        initialize();
    }

    public Frame(float width, float height) {
        initialize();
        for (Layer layer : getAllLayers()) {
            layer.container.size.set(width, height);
        }
    }

    public Frame(Vector2f size) {
        this(size.x, size.y);
        initialize();
    }

    private void initialize() {
        componentLayer = new Layer();
        tooltipLayer = new Layer();
        componentLayer.topLayer = tooltipLayer;
        tooltipLayer.botLayer = componentLayer;
        componentLayer.frame = this;
        tooltipLayer.frame = this;

        SystemEventListener<Layer.LayerContainer, SystemWindowSizeEvent> frameSystemWindowSizeEventSystemEventListener = (event, component, context) -> {
            component.size.set(event.width, event.height);
            component.components.forEach(c -> c.getSystemEventListeners().getListener(event.getClass()).update(event, c, context));
        };

        componentLayer.container.systemEventListeners
                .setListener(SystemWindowSizeEvent.class, frameSystemWindowSizeEventSystemEventListener);
        componentLayer.container.backgroundColor = ColorConstants.transparent();
        componentLayer.container.position.set(0);
    }

    public void addLayer(Layer layer) {
        if (layer == null ||
                layer == tooltipLayer ||
                layer == componentLayer ||
                layer.frame == this) {
            return;
        }

        if (layer.frame != null) {
            layer.frame.removeLayer(layer);
        }
        Layer previous;
        if (layers.isEmpty()) {
            previous = componentLayer;
        } else {
            previous = layers.get(layers.size() - 1);
        }
        layer.frame = this;
        layer.botLayer = previous;
        layer.topLayer = tooltipLayer;
        previous.topLayer = layer;
        layers.add(layer);
    }

    public void removeLayer(Layer layer) {
        if (layer == null) {
            return;
        }
        layer.frame = null;
        if (layers.contains(layer)) {
            Layer botLayer = layer.botLayer;
            Layer topLayer = layer.topLayer;
            botLayer.topLayer = topLayer;
            topLayer.botLayer = botLayer;
            layers.remove(layer);
        }
    }

    public Layer getComponentLayer() {
        return componentLayer;
    }

    public Layer getTooltipLayer() {
        return tooltipLayer;
    }

    public List<Layer> getLayers() {
        return new ArrayList<>(layers);
    }

    public List<Layer> getAllLayers() {
        ArrayList<Layer> layers = new ArrayList<>();
        layers.add(componentLayer);
        layers.addAll(this.layers);
        layers.add(tooltipLayer);
        return layers;
    }

    public void setPosition(Vector2f position) {
    }

    public void setPosition(float x, float y) {
    }

    public int componentsCount() {
        return componentLayer.componentsCount();
    }

    public boolean isContainerEmpty() {
        return componentLayer.isContainerEmpty();
    }

    public boolean containsComponent(Component component) {
        return componentLayer.containsComponent(component);
    }

    public Iterator<Component> containerIterator() {
        return componentLayer.containerIterator();
    }

    public boolean addComponent(Component component) {
        return componentLayer.addComponent(component);
    }

    public boolean addAllComponents(Collection<? extends Component> components) {
        return componentLayer.addAllComponents(components);
    }

    public boolean removeComponent(Component component) {
        return componentLayer.removeComponent(component);
    }

    public void removeAllComponents(Collection<? extends Component> components) {
        componentLayer.removeAllComponents(components);
    }

    public boolean removeComponentIf(Predicate<? super Component> filter) {
        return componentLayer.removeComponentIf(filter);
    }

    public void clearComponents() {
        componentLayer.clearComponents();
    }

    public boolean containerContainsAll(Collection<?> components) {
        return componentLayer.containerContainsAll(components);
    }

    public Stream<Component> componentStream() {
        return componentLayer.componentStream();
    }

    public Stream<Component> componentParallelStream() {
        return componentLayer.componentParallelStream();
    }

    public void forEachComponent(Consumer<? super Component> action) {
        componentLayer.forEachComponent(action);
    }

    public List<Component> getComponents() {
        return componentLayer.getComponents();
    }

    public Component getComponentAt(Vector2f cursorPosition) {
        return componentLayer.getComponentAt(cursorPosition);
    }


}
