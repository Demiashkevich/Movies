package com.epam.movies.manager;

import java.util.ResourceBundle;

public class ResourceManager {

    private static final String PATH_CONFIGURATION = "configuration";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(PATH_CONFIGURATION);

    public static String getConfiguration(String key) {
        return resourceBundle.getString(key);
    }

}
