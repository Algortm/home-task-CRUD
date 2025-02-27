package org.example.client;

public class CheckValideNameClient {
    public static void checkName(String name) throws IllegalArgumentException {
        char[] arrayName = name.toCharArray();
        if(arrayName.length>1000 || arrayName.length<=2){
            throw new IllegalArgumentException("Incorrect input name: " + name);
        }
    }
}
