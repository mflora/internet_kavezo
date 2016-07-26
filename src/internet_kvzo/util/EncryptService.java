package internet_kvzo.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class EncryptService {
    public static String passwordToMD5(String pw){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pw.getBytes());
            byte[] digest = md.digest();

            return new String(digest, StandardCharsets.UTF_8);
        }catch (Exception ex){
            return null;
        }
    }
}
