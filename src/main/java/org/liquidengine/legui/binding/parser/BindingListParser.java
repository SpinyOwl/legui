package org.liquidengine.legui.binding.parser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class BindingListParser extends DefaultHandler {

    private String currentElement;
    private String currentPrefix;
    private String currentUri;



    /**
     * Receive notification of the beginning of the document. <p> <p>By default, do nothing.  Application writers may override this method in a subclass to take
     * specific actions at the beginning of a document (such as allocating the root node of a tree or creating an output file).</p>
     *
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see ContentHandler#startDocument
     */
    @Override
    public void startDocument() throws SAXException {
        System.out.println("start parse xml");
    }

    /**
     * Receive notification of the start of a Namespace mapping.
     * <p>
     * <p>By default, do nothing.  Application writers may override this method in a subclass to take specific actions at the start of each Namespace prefix
     * scope (such as storing the prefix mapping).</p>
     *
     * @param prefix The Namespace prefix being declared.
     * @param uri The Namespace URI mapped to the prefix.
     *
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see ContentHandler#startPrefixMapping
     */
    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        currentPrefix = prefix;
        currentUri = uri;

        System.out.println("-"  + prefix + " | " + uri);
    }

    /**
     * Receive notification of the end of a Namespace mapping.
     * <p>
     * <p>By default, do nothing.  Application writers may override this method in a subclass to take specific actions at the end of each prefix mapping.</p>
     *
     * @param prefix The Namespace prefix being declared.
     *
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see ContentHandler#endPrefixMapping
     */
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        currentPrefix = null;
        System.out.println(" + " + prefix);
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
        System.out.println(uri + " | " + localName + " | " + qName);
        switch (qName) {
            case "binding": {
                System.out.println("-------------");
                System.out.println(qName);
                int length = attributes.getLength();
                for(int i = 0; i < length; i++) {
                    System.out.println(attributes.getQName(i) + " : " + attributes.getValue(i) + "(" + attributes.getType(i) + ")");
                }
                System.out.println("-------------");
            }
            break;
            default:
                System.out.println("*------------");
                System.out.println(qName);
                System.out.println("*------------");
                break;
        }
    }
}
