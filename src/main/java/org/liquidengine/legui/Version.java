/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.liquidengine.legui;

import java.util.ResourceBundle;

/**
 * This class can be used to query the LEGUI version.
 */
public final class Version {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("org/liquidengine/legui/version");

    private Version() {
    }

    /**
     * Returns the version of the library.
     *
     * @return version of the library.
     */
    public static String getVersion() {
        return BUNDLE.getString("version.full");
    }

    /**
     * Returns the major version of the library.
     *
     * @return major version of the library.
     */
    public static int getMajorVersion() {
        return Integer.parseInt(BUNDLE.getString("version.major"));
    }

    /**
     * Returns the minor version of the library.
     *
     * @return minor version of the library.
     */
    public static int getMinorVersion() {
        return Integer.parseInt(BUNDLE.getString("version.minor"));
    }

    /**
     * Returns the patch version of the library.
     *
     * @return patch version of the library.
     */
    public static int getPatchVersion() {
        return Integer.parseInt(BUNDLE.getString("version.patch"));
    }

    /**
     * Returns the revision of the build.
     *
     * @return revision of the build.
     */
    public static String getRevision() {
        return BUNDLE.getString("version.revision");
    }

    /**
     * Prints the version of the library.
     */
    public static void main(String[] args) {
        System.out.println("Version: " + getVersion() + "\nRevision: " + getRevision());
    }

}