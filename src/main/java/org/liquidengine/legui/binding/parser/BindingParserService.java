package org.liquidengine.legui.binding.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.BindingCreationException;
import org.liquidengine.legui.binding.model.ClassBinding;
import org.xml.sax.SAXException;

/**
 * Parser for bindings.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class BindingParserService {

    private BindingParserService() {
    }

    public static BindingParserService getInstance() {
        return BindingParserHolder.INSTANCE;
    }

    public Map<Class, ClassBinding> parseList(String listPath) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            BindingListParser listParser = new BindingListParser();
            saxParserFactory.newSAXParser().parse(getInputStream(listPath), listParser);
            return listParser.getBindings();
        } catch (SAXException | IOException | ParserConfigurationException | BindingCreationException e) {
            e.printStackTrace();
            return null;
        }
    }

    ClassBinding parseBinding(String bindingPath) {
        return this.parseBinding(bindingPath, null);
    }

    ClassBinding parseBinding(String bindingPath, String parentPath) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            BindingParser bindingParser = new BindingParser(parentPath);
            saxParserFactory.newSAXParser().parse(getInputStream(bindingPath), bindingParser);
            return bindingParser.getBinding();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private InputStream getInputStream(String listPath) throws FileNotFoundException {
        InputStream stream;
        File file = new File(listPath);
        if (file.exists() && file.isFile()) {
            stream = new FileInputStream(file);
        } else {
            stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(listPath);
        }
        if (stream == null) {
            throw new FileNotFoundException(listPath);
        }
        return stream;
    }

    private static class BindingParserHolder {

        private static final BindingParserService INSTANCE = new BindingParserService();
    }
}
