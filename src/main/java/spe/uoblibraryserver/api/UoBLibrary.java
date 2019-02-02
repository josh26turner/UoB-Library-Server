package spe.uoblibraryserver.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

class UoBLibrary {
  
  static String getOCLCSymbol() {
    return "BUB";
  }
  
  static String getRegistryId() {
    return "132607";
  }

  static String getNonce() {
    Random random = new Random();

    int i = random.nextInt(Integer.MAX_VALUE);
    int j = random.nextInt(Integer.MAX_VALUE);

    return Integer.toString(i) + j;
  }

  static String getPrivateKey() throws IOException {
    FileReader fileReader = new FileReader("src/main/resources/WMSSecretKey.key");
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    return bufferedReader.readLine();
  }

  static String getPublicKey() throws IOException {
    FileReader fileReader = new FileReader("src/main/resources/WMSPublicKey.key");
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    return bufferedReader.readLine();
  }
}
