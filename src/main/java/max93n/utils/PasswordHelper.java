package max93n.utils;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Component
public class PasswordHelper implements PasswordEncoder {

    public String encodePassword(String rawPass, Object salt) {
        MessageDigest messageDigest= null;
        byte[] digest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(rawPass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5hex = bigInt.toString(16);

        while (md5hex.length() < 32) {
            md5hex = "0" + md5hex;
        }

        return md5hex;
    }


    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        return encodePassword(rawPass, salt).equals(encPass);
    }
}
