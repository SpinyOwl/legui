package org.liquidengine.legui.binding.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
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

    public void parseList(String listPath) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            saxParserFactory.newSAXParser().parse(getInputStream(listPath), new BindingListParser());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
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
