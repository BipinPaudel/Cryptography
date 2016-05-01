package com.company;

public class Caesar {
    public static void main(String[] args) {
        String message="ASPEROPHISABADASSS";
//        System.out.println("Initial String: " +message);
//        encrypt(message);
        System.out.println(message.substring(12,18));
    }

    public static void encrypt(String message){
        StringBuilder encrypted_string=new StringBuilder();
        for(int i=0;i<message.length();i++){
            if(message.charAt(i)== ' '){
                encrypted_string.append(" ");//space is inserted if space is seen
            }
            else{
                int final_ascii= (int)message.charAt(i)+3; //calculation of ascii value
                if(final_ascii>=90){
                    final_ascii= final_ascii%90 + 64;
                }
                encrypted_string.append((char)final_ascii);
            }
        }
        System.out.println("this is encrypted string: "+ encrypted_string);
        decrypt(encrypted_string.toString());
    }

    public static void decrypt(String encrypted_message){
        StringBuilder decrypted_message=new StringBuilder();
       for(int i=0;i<encrypted_message.length();i++){
           if(encrypted_message.charAt(i)==' '){
               decrypted_message.append(" ");
           }
           else{
               int final_ascii= encrypted_message.charAt(i)-3;
               if(final_ascii<65){
                   final_ascii= (( final_ascii + 25)%90) +1;
               }
               decrypted_message.append((char)final_ascii);
           }
       }
        System.out.println("this is decrypted message "+ decrypted_message.toString());
    }
}
