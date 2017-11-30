package org.liquidengine.legui.binding.parser;

import org.liquidengine.legui.binding.BindingBuilder;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.binding.model.ClassBinding;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class BindingParser extends DefaultHandler {

    private ClassBinding binding;
    private String parentPath;
    private BindingBuilder builder;

    public BindingParser(String parentPath) {
        this.parentPath = parentPath;
    }

    public ClassBinding getBinding() {
        return binding;
    }

    /**
     * Receive notification of the start of an element. <p> <p>By default, do nothing.  Application writers may override this method in a subclass to take
     * specific actions at the start of each element (such as allocating a new tree node or writing output to a file).</p>
     *
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
     * @param attributes The attributes attached to the element.  If there are no attributes, it shall be an empty Attributes object.
     *
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see ContentHandler#startElement
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName == null) {
            return;
        }
        switch (localName) {
            case "class-binding": createClassBinding(attributes);
                break;
            case "bind": bind(attributes);
                break;
            case "unbind": unbind(attributes);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (localName) {
            case "class-binding": {
                binding = builder.build();
            }
                break;
        }
    }

    private void createClassBinding(Attributes attributes) throws SAXException {
        String forClass = null;
        String toField = null;
        String inheritBinding = null;
        boolean defaultBinding = false;

        for (int i = 0; i < attributes.getLength(); i++) {
            String name = attributes.getLocalName(i);
            String value = attributes.getValue(i);
            switch (name) {
                case "for": forClass = value;
                    break;
                case "to": toField = value;
                    break;
                case "inherit": inheritBinding = value;
                    break;
                case "default": defaultBinding = Boolean.valueOf(value);
                    break;
            }
        }
        ClassBinding binding = null;
        if (inheritBinding != null) {
            if (parentPath != null && parentPath.equals(inheritBinding)) {
                throw new SAXException("Cycled bindings detected. Bindings will not be added.");
            }
            binding = BindingStorage.getInstance().getBinding(inheritBinding);
            if (binding == null) {
                binding = BindingParserService.getInstance().parseBinding(inheritBinding);
                BindingStorage.getInstance().putBinding(inheritBinding, binding);
            }
        }
        Class clazz;
        try {
            clazz = Class.forName(forClass);
        } catch (ClassNotFoundException e) {
            throw new SAXException(e);
        }
        if (clazz != null) {
            builder = BindingBuilder.createForClass(clazz, toField, defaultBinding, binding);
        }
    }

    private void bind(Attributes attributes) {
        String field = null;
        String toField = null;
        String using = null;
        boolean attribute = true;

        for (int i = 0; i < attributes.getLength(); i++) {
            String name = attributes.getLocalName(i);
            String value = attributes.getValue(i);
            switch (name) {
                case "field": field = value;
                    break;
                case "to": toField = value;
                    break;
                case "using": using = value;
                    break;
                case "attribute": attribute = Boolean.valueOf(value);
                    break;
            }
        }
        ClassBinding linked = null;
        if (using != null) {
            linked = BindingStorage.getInstance().getBinding(using);
            if (linked == null) {
                linked = BindingParserService.getInstance().parseBinding(using);
                BindingStorage.getInstance().putBinding(using, linked);
            }
        }
        builder.bind(field, toField, attribute, linked);
    }

    private void unbind(Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String name = attributes.getLocalName(i);
            String value = attributes.getValue(i);
            switch (name) {
                case "field": builder.unbind(value);
                    break;
            }
        }
    }
}
