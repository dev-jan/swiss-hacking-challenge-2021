
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class Main {
  public static void main(String args[]) {
    byte[] exs = {-28, 73, 79, 78, 113, 73, 101, 98, 115, 6, 27, -35, 111, -55, -114, -11, -29, 0, -73, 91, 115, -24, -4, -94, -59, 43, -57, 112, 11, -54, -115, 2};
    String str2 = new String(AESUtil.decrypt(exs));
    System.out.println(str2);
  }

  public static class AESUtil {
    private static final String ENCRYPTION_IV = "SHCUOkfd89ut7777";
    private static final String ENCRYPTION_KEY = "Simpleji4todnkfL";

    public static byte[] encrypt(byte[] bArr) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(1, makeKey(), makeIv());
            return instance.doFinal(bArr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(byte[] bArr) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, makeKey(), makeIv());
            return instance.doFinal(bArr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static AlgorithmParameterSpec makeIv() {
        try {
            return new IvParameterSpec(ENCRYPTION_IV.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    static Key makeKey() {
        try {
            return new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(ENCRYPTION_KEY.getBytes("UTF-8")), "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }
  }
}
