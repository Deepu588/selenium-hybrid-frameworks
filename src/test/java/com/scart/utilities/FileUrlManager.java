package com.scart.utilities;

import java.util.HashMap;
import java.util.Map;

public class FileUrlManager {
    private static final Map<String, String> fileUrlMap = new HashMap<>();
    
    // File type constants
    public static final String VIDEO_FILE = "video";
    public static final String IMAGE_FILE = "image";
    public static final String AUDIO_FILE = "audio";
    public static final String EXCEL_FILE = "excel";
    public static final String HTML_FILE = "html";
    
    public static void storeUrl(String fileType, String url) {
        fileUrlMap.put(fileType, url);
    }
    
    public static String getUrl(String fileType) {
        return fileUrlMap.get(fileType);
    }
    
    public static void clearUrls() {
        fileUrlMap.clear();
    }
}