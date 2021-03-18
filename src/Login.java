import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Login {

  public Boolean loginUser() {

    Scanner sc = new Scanner(System.in); // System.in is a standard input stream
    System.out.print("Enter user name: ");
    String str = sc.nextLine(); // reads string

    Console console = System.console();
    char[] passwordArray = console.readPassword("Enter password (it will be invisible): ");
    String st1 = new String(passwordArray);
  
    return testKey(str, st1);
  }

  public byte[] getSHA(String input) throws NoSuchAlgorithmException {
    // Static getInstance method is called with hashing SHA
    MessageDigest md = MessageDigest.getInstance("SHA-256");

    // digest() method called
    // to calculate message digest of an input
    // and return array of byte
    return md.digest(input.getBytes(StandardCharsets.UTF_8));
  }

  public String toHexString(byte[] hash) {
    // Convert byte array into signum representation
    BigInteger number = new BigInteger(1, hash);

    // Convert message digest into hex value
    StringBuilder hexString = new StringBuilder(number.toString(16));

    // Pad with leading zeros
    while (hexString.length() < 32) {
      hexString.insert(0, '0');
    }

    return hexString.toString();
  }

  public String generate(String s1) {
    try {
      s1 = toHexString(getSHA(s1));
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Exception thrown for incorrect hash algorithm: " + e);
    }
    return s1;
  }

  public Boolean testKey(String uname, String upass) {

    String userNameHash = generate(uname);
    String passwordHash = generate(upass);

    try (BufferedReader in = new BufferedReader(new FileReader("login.txt"))) {
      String hash1, hash2;
      String str;
      while ((str = in.readLine()) != null) {
        // splitting lines on the basis of token
        String[] tokens = str.split(",");
        hash1 = tokens[0];
        hash2 = tokens[1];
        if (hash1.equals(userNameHash) && hash2.equals(passwordHash))
          return true;

      }

    } catch (Exception e) {
      System.out.println("File Read Error");
    }

    return false;
  }

}
