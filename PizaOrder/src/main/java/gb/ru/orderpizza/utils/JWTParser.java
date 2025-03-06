package gb.ru.orderpizza.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Утилитарный класс для разбора JWT-токенов.
 */
public class JWTParser {

    /**
     * Метод для извлечения номера телефона из JWT-токена.
     *
     * @param token JWT-токен
     * @return номер телефона пользователя
     */
    public static String extractNumber(String token) {
        String userNumber = JWTParser.jwtExtraction(token, "\"sub\"");
        if (userNumber != null) 
            return userNumber;
        return "";
    }

    /**
     * Метод для извлечения роли пользователя из JWT-токена.
     *
     * @param token JWT-токен
     * @return роль пользователя
     */
    public static String extractRole(String token) {
        String userType = jwtExtraction(token, "\"role\"");
        if (userType != null)
            return userType;
        return "";    
    }

    /**
     * Вспомогательный метод для извлечения данных из JWT-токена.
     *
     * @param token JWT-токен
     * @param tag   тег данных, которые нужно извлечь
     * @return извлеченные данные
     */
    public static String jwtExtraction(String token, String tag) {

        token.replace("Bearer ", "");

        String[] args = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String data = new String(decoder.decode(args[1]));

        String[] values = data.split(",");
        Map<String, String> dict = new HashMap<String, String>();

        for (String value : values) {
            String[] key = value.split(":");
            if (key[0].equals(tag)) {
                
                int remove = 1;
                if (key[1].endsWith("}")) {
                    remove = 2;
                }
                key[1] = key[1].substring(0, key[1].length() - remove);
                key[1] = key[1].substring(1);

                dict.put(key[0], key[1]);
            }
        }
        if (dict.containsKey(tag)) {
            return dict.get(tag);
        }
        return null;
    }
}