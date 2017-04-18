package com.umss.sistemas.tesis.hotel.conexion;

public class Conexion {

    private static String ip="http://192.168.1.35/";
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
            "image/url",//no usado
            "sites",
            "frequently"
    };
    private static String urlServer=ip+dirUser;

    public static String getUrlServer(int i) {
        String res="";
        if (pagina.length>=i)
            res=urlServer+pagina[i];
        return res;
    }
}
