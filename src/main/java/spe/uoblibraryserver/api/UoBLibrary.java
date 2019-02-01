package spe.uoblibraryserver.api;

import java.util.Random;

class UoBLibrary {
  
  static String getOCLC_SYMBOL() {
    return "BUB";
  }
  
  static String getREGISTRY_ID() {
    return "132607";
  }
  
  static String getNonce() {
    Random random = new Random();
    
    int i = random.nextInt(Integer.MAX_VALUE);
    int j = random.nextInt(Integer.MAX_VALUE);
    
    return Integer.toString(i) + j;
  }
  
  static String getPrivateKey() {
    return "";
  }
}
