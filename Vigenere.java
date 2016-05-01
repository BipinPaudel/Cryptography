package com.company;


/**
 * Created by bips on 2/29/16.
 */
public class Vigenere {
    public static void main(String[] args) {
        String message="DOG CHASE THE CAT";
        String key="LION";
        System.out.println("Plain text: " + message);
        vigenereEncrypt(message, key);
    }


    public static void vigenereEncrypt(String message,String key){

        StringBuilder encrypted_string= new StringBuilder();
        int key_counter=0;
        for(int i=0;i<message.length();i++){
            if(message.charAt(i)==' '){
                encrypted_string.append(' ');
            }
            else {
                if(key_counter==key.length()){
                    key_counter = 0 ;
                }
                int key_ascii = (int) key.charAt(key_counter);
                int message_ascii = (int) message.charAt(i);
                int cipher = (((key_ascii - 65) + message_ascii) ) ;
                if(cipher>90){
                cipher=(((key_ascii - 65) + message_ascii)%90 ) +64;
            }

            encrypted_string.append((char) cipher);
                key_counter++;

            }
        }
        System.out.println("Encrypted String: "+encrypted_string.toString());
        vigenereDecrypt(encrypted_string.toString(), key);
    }

    public static void vigenereDecrypt(String message,String key){
        StringBuilder decrypted_message=new StringBuilder();
        String res="";
        int key_counter=0;
        for(int i=0;i<message.length();i++) {
            if (message.charAt(i) ==' ') {
                decrypted_message.append(' ');
             } else {
                if(key_counter==key.length()){
                    key_counter = 0 ;
                }
                int key_ascii = (int) key.charAt(key_counter);
                int message_ascii = (int) message.charAt(i);
                int cipher = (message_ascii - key_ascii) + 65 ;
                if(cipher<65){
                    cipher= (message_ascii - key_ascii) + 65 +26;
                }
                key_counter++;
                decrypted_message.append((char) cipher);
            }
        }
        System.out.println("Decrypted Message is: " + decrypted_message.toString());
        System.out.println(res);
    }
}
