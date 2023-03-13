package com.github.katkan.properties;

import java.util.ResourceBundle;

public class Properties {
    private static final String EXISTING_USER_USERNAME = "username";
    private static final String EXISTING_USER_PASSWORD = "password";

    public static String getUsername() {
        return getProperty(EXISTING_USER_USERNAME);
    }

    public static String getPassword() {
        return getProperty(EXISTING_USER_PASSWORD);
    }

    private static String getProperty(String key) {
        return ResourceBundle.getBundle("fakestore").getString(key);
    }
}
