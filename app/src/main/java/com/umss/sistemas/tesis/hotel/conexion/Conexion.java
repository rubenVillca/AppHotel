package com.umss.sistemas.tesis.hotel.conexion;

import java.io.UnsupportedEncodingException;

public class Conexion {
    private static String ip = "http://192.168.1.36/hotel/";
    //private static String ip = "http://hoteltesis.esy.es/";
    private static boolean isServer = false;
    public static String urlServer = ip;

    public static final String LOGIN = ip + "login";
    public static final String CREATE_ACCOUNT = ip +"createAccount";
    public static final String PROFILE = ip +"profile";
    public static final String INFO = ip +"info";
    public static final String COMPLAINTS = ip +"complaints";
    public static final String SUGGESTION = ip +"suggestion";
    public static final String CONTACT = ip +"contact";
    public static final String RESERVE = ip +"reserve";
    public static final String HISTORY = ip +"history";
    public static final String MENU_BAR = ip +"menu_bar";
    public static final String OFFER = ip + "offer";
    public static final String SERVICE = ip + "service";
    public static final String IMAGE = ip + "img";
    public static final String SITES = ip + "sites";
    public static final String FREQUENTLY = ip + "frequently";
    public static final String FOOD_MENU = ip + "foodMenu";
    public static final String MESSAGES = ip + "messages";
    public static final String CALENDAR = ip + "calendar";
    public static final String CHECK = ip + "check";
    public static final String RESERVE_SAVE = ip + "reserve/save";
    public static final String MEMBER_SAVE = ip + "member/save";
    public static final String FOOD_MENU_INSERT = ip + "foodMenu/update";
    public static final String CONSUME_SERVICE = ip + "consumeService/insert";
    public static final String PROFILE_UPLOAD = ip + "profile/upload";

    public static String decode(String string) {
        String res = string;
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
