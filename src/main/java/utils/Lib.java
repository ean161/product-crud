package utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author CE190990 - Nguyen Hoai An
 */
public class Lib {
    public static boolean isLogged(HttpServletRequest request) {
        return !(Lib.getCookie(request, "userID") == null || Lib.getCookie(request, "userID").isEmpty());
    }

    public static String getCookie(HttpServletRequest request, String key) {
        String value = null;

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                value = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                break;
            }
        }

        return value;
    }
    
    public static void createCookieMessage(HttpServletResponse response, String status, String message) {
        Lib.setCookie(response, "status", status, 10);
        Lib.setCookie(response, "message", message, 10);
    }
    
    public static String getDOMMessage(HttpServletRequest request, HttpServletResponse response) {
        String status = Lib.getCookie(request, "status");
        String message = Lib.getCookie(request, "message");
        
        if (status == null)
            return null;
        
        Lib.delCookie(response, "status");
        Lib.delCookie(response, "message");
        
        return String.format(
            "<div class='text-center'><span class='badge bg-%s'>%s</span></div>",
            status.equals("1") ? "success" : "danger",
            message.substring(0, 1).toUpperCase() + message.substring(1)
        );
    }

    public static void setCookie(HttpServletResponse response, String key, String value, int expired) {
        Cookie cookie = new Cookie(key, URLEncoder.encode(value, StandardCharsets.UTF_8));
        cookie.setMaxAge(expired);
        response.addCookie(cookie);
    }

    public static void delCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static String hashMD5(String pass) {
        try {
            MessageDigest mes = MessageDigest.getInstance("MD5");
            byte[] mesMD5 = mes.digest(pass.getBytes());
            StringBuilder str = new StringBuilder();
            for (byte b : mesMD5) {
                String ch = String.format("%02x", b);
                str.append(ch);
            }
            return str.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
