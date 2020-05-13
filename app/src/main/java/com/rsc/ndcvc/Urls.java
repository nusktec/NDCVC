package com.rsc.ndcvc;

public interface Urls {
    String BASE_TOKEN = "67d968c16d763d28ff223b2777b7b702b0df94af";
    String DOMAIN = "https://ndcvc.live";
    String URL_WEB_BASE = DOMAIN;
    String URL_BASE = DOMAIN + "/api/mobile";
    String URL_LOGIN = URL_BASE + "/login";
    String URL_ADM_DATA = URL_BASE + "/attendance-get-permit-all";
    String URL_ADM_CLASS_STATUS = URL_BASE + "/class-status";
    String URL_STD_CLASS_GET = URL_BASE + "/class-get-all";
    String URL_STD_MRK = URL_BASE + "/attendance-add";
}