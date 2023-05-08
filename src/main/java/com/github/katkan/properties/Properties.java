package com.github.katkan.properties;

import java.util.ResourceBundle;

public class Properties {
    private static final String EXISTING_USER_USERNAME = "username";
    private static final String EXISTING_USER_PASSWORD = "password";
    private static final String MAIN_PAGE_URL = "https://fakestore.testelka.pl/";

    public static String getUsername() {
        return getProperty(EXISTING_USER_USERNAME);
    }

    public static String getPassword() {
        return getProperty(EXISTING_USER_PASSWORD);
    }

    public static String getBaseUrl() {
        return MAIN_PAGE_URL;
    }

    private static String getProperty(String key) {
        return ResourceBundle.getBundle("fakestore").getString(key);
    }
}
