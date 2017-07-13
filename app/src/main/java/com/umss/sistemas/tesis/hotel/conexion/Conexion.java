package com.umss.sistemas.tesis.hotel.conexion;

import java.io.UnsupportedEncodingException;

public class Conexion {
    public static final int LOGIN = 0;
    public static final int CREATE_ACCOUNT = 1;
    public static final int PROFILE = 2;
    public static final int INFO = 3;
    public static final int COMPLAINTS = 4;
    public static final int SUGGESTION = 5;
    public static final int CONTACT = 6;
    public static final int RESERVE = 7;
    public static final int HISTORY = 8;
    public static final int MENU_BAR = 9;
    public static final int OFFER = 10;
    public static final int SERVICE = 11;
    public static final int IMAGE = 12;
    public static final int SITES = 13;
    public static final int FREQUENTLY = 14;
    public static final int FOOD_MENU = 15;
    public static final int MESSAGES = 16;
    public static final int CALENDAR = 17;
    public static final int CHECK = 18;
    public static final int RESERVE_SAVE = 19;
    public static final int MEMBER_SAVE = 20;
    public static final int FOOD_MENU_INSERT = 21;
    public static final int CONSUME_SERVICE = 22;

    private static String[] pagina = {
            "login",
            "createAccount",
            "profile",
            "info",
            "complaints",
            "suggestion",
            "contact",
            "reserve",
            "history",
            "menu_bar",
            "offer",
            "service",
            "img",
            "sites",
            "frequently",
            "foodMenu",
            "messages",
            "calendar",
            "check",
            "reserve/save",
            "member/save",
            "foodMenu/update",
            "consumeService/insert"
    };
    private static String ip = "http://192.168.1.34/hotel/";
    //private static String ip = "http://hoteltesis.esy.es/";
    private static boolean isServer=false;
    public static String urlServer = ip ;

    public static String getUrlServer(int i) {
        String res = "";
        if (pagina.length >= i)
            res = urlServer + pagina[i];
        return res;
    }
    public static String decode(String string){
        String res=string;
        if (isServer) {
            try {
                res = new String(string.getBytes("ISO-8859-15"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
