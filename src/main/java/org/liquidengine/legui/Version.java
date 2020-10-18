/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.liquidengine.legui;

import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class can be used to query the LEGUI version.
 */
public final class Version {
    private static final Logger LOGGER = LogManager.getLogger();

    private Version() {
    }

    public static String getVersion() {
        URL url = Version.class.getClassLoader().getResource("org/liquidengine/legui/Version.class");
        if (url != null) {
            String classURL = url.toString();
            if (classURL.startsWith("jar:")) {
                try (InputStream stream = new URL(classURL.substring(0, classURL.lastIndexOf('!') + 1) + '/' + JarFile.MANIFEST_NAME).openStream()) {
                    return new Manifest(stream).getMainAttributes().getValue("Full-Version");
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println("LEGUI: " + getVersion());
    }

}