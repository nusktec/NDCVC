package com.rsc.ndcvc;

public interface Urls {
    public static String BASE_TOKEN = "67d968c16d763d28ff223b2777b7b702b0df94af";
    public static String DOMAIN = "http://192.168.0.100/ndcx";
    public static String URL_WEB_BASE = DOMAIN;
    public static String URL_BASE = DOMAIN + "/api/mobile";
    public static String URL_LOGIN = URL_BASE + "/login";
    public static String URL_ADM_DATA = URL_BASE + "/attendance-get-permit-all";
    public static String URL_ADM_CLASS_STATUS = URL_BASE + "/class-status";
}