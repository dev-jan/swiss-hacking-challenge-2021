import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.stream.IntStream;

public class HLLoginCheck {
    private static final String ENCRYPTION_IV = "skl5ls74/hdl2HU1";
    static long countWrongTries;

    public static void main(String[] args) throws Exception {
        String result = checkPin(100_000);
        System.out.println(result);

        IntStream.range(100_000, 1_000_000)
                .forEach(pin -> {
                    try {
                        String res = checkPin(pin);
                        if (res != null) {
                            System.out.println("FOUND!!!! " + pin);
                            System.out.println("Result: " + res);
                            System.exit(0);
                        }
                    } catch (InterruptedException interruptedException) {
                        System.out.println("interrupted...");
                    }
                });

    }

    private static final byte[] e = new byte[] {
            103, 44, -118, -71, 82, 112, -94, -2, 8, -52,
            -81, 114, -106, 23, 125, 46, -41, -15, 97, 35,
            32, Byte.MAX_VALUE, -1, 26, -61, 28, Byte.MIN_VALUE, -46, -63, 90,
            -79, -2 };

    private static String checkPin(int paramInt) throws InterruptedException {
        BigInteger bigInteger = new BigInteger(String.format("%d", new Object[] { Integer.valueOf(paramInt) }));
        if ((new BigInteger("25239776756291")).mod(bigInteger).intValue() != 0) {
            long l = countWrongTries;
            // Thread.sleep(l * l * 200L); // removed to improve speeeeeeed :)
            countWrongTries++;
            System.out.println("Login: Wrong PIN.");
            return null;
        }
        countWrongTries = 0L;
        return getFlag(bigInteger.multiply(bigInteger).toString());
    }

    public static String checkPin(String paramString) {
        if (paramString != null)
            try {
                if (paramString.length() >= 4) {
                    Integer integer = Integer.valueOf(Integer.parseInt(paramString));
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Pin is: ");
                    stringBuilder.append(integer);
                    System.out.println("Login: " + stringBuilder.toString());
                    return checkPin(integer.intValue());
                }
                System.out.println("Login: Pin must be an Integer of 6 digits.");
                return null;
            } catch (Exception exception) {
                System.out.println("Login: Password must be a PIN.");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Login error: ");
                stringBuilder.append(exception.toString());
                System.out.println("Login: " + stringBuilder.toString());
                return null;
            }
        System.out.println("Login: Pin must be an Integer of 6 digits.");
        return null;
    }

    public static byte[] decrypt(Key paramKey, byte[] paramArrayOfbyte) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, paramKey, makeIv());
            return cipher.doFinal(paramArrayOfbyte);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static byte[] encrypt(Key paramKey, byte[] paramArrayOfbyte) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, paramKey, makeIv());
            return cipher.doFinal(paramArrayOfbyte);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static String getFlag(String paramString) {
        return new String(decrypt(makeKey(paramString.getBytes()), e));
    }

    static AlgorithmParameterSpec makeIv() {
        try {
            return new IvParameterSpec("skl5ls74/hdl2HU1".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return null;
        }
    }

    static Key makeKey(byte[] paramArrayOfbyte) {
        try {
            return new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(paramArrayOfbyte), "AES");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
