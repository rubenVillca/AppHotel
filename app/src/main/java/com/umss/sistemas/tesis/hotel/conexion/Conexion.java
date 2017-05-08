package com.umss.sistemas.tesis.hotel.conexion;

public class Conexion {
    public static final int LOGIN=0;
    public static final int REGISTER=1;
    public static final int PROFILE=2;
    public static final int INFO=3;
    public static final int COMPLAINTS=4;
    public static final int SUGGESTION=5;
    public static final int CONTACT=6;
    public static final int RESERVE=7;
    public static final int HISTORY=8;
    public static final int MENU_BAR=9;
    public static final int OFFER=10;
    public static final int SERVICE=11;
    public static final int IMAGE=12;
    public static final int SITES=13;
    public static final int FREQUENTLY=14;
    public static final int FOOD_MENU=15;

    private static String ip="http://192.168.1.41/";
    private static String dirUser="hotel/";
    private static String[] pagina={"login",
            "register",
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
            "foodMenu"
    };
    public static String urlServer=ip+dirUser;

    public static String getUrlServer(int i) {
        String res="";
        if (pagina.length>=i)
            res=urlServer+pagina[i];
        return res;
    }
}
