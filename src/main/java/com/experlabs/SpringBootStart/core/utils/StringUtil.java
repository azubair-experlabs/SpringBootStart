package com.experlabs.SpringBootStart.core.utils;

import java.util.List;

public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }
}

