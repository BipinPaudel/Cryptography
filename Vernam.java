package com.company;

public class Vernam{
    public static void main(String[] args) {

        String plainText="CAT";
        String key="DOG";
        System.out.println("Plaintext: "+plainText);
        System.out.println("Key: "+key);
        String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String encrypted_text=encrypt(plainText,key,alphabet);
        System.out.println("Encrypted string: " + encrypted_text);
        String decrypted_text=decrypt(encrypted_text,key,alphabet);
        System.out.println("Decrypted string: "+decrypted_text);
    }

    public static String encrypt(String plainText,String key,String alphabet){
        StringBuilder encryptedText= new StringBuilder();
        StringBuilder plainTextBinary=new StringBuilder();
        StringBuilder keyBinary = new StringBuilder();
        StringBuilder cipherBinary=new StringBuilder();

        for (int i=plainText.length();i<key.length();i++){
            plainText+="X";
        }
        int counter=0;
        for(int i=key.length();i<plainText.length();i++){
            if(counter>key.length()) counter=0;
            key+= key.charAt(counter);
            counter++;
        }

        for(int i=0;i<plainText.length();i++){
            Integer index=alphabet.indexOf(plainText.charAt(i));
            Integer keyIndex=alphabet.indexOf(key.charAt(i));
            StringBuilder plainBinaryString= new StringBuilder();
            StringBuilder keyBinaryString=new StringBuilder();
            plainBinaryString.append(index.toBinaryString(index));
            keyBinaryString.append(keyIndex.toBinaryString(keyIndex));

            for(int j=plainBinaryString.length(); j< 5;j++){
                plainBinaryString.reverse().append('0').reverse();

            }

            for(int j=keyBinaryString.length();j<5;j++){
                keyBinaryString.reverse().append('0').reverse();
            }
            plainTextBinary.append(plainBinaryString);
            keyBinary.append(keyBinaryString);
        }

        System.out.println(plainTextBinary.toString());
        System.out.println(keyBinary);

        for(int j=0;j<keyBinary.length();j++){
            int keyBit, plainBit;
            keyBit=keyBinary.charAt(j);
            plainBit=plainTextBinary.charAt(j);
            if(keyBit==plainBit) cipherBinary.append("0");
            else cipherBinary.append("1");
        }

        System.out.println(cipherBinary);

        for(int i=0;i<cipherBinary.length();i=i+5){
            StringBuilder tempBit=new StringBuilder();
            for(int j=0;j<5;j++){
                tempBit.append(cipherBinary.charAt(i+j));
            }
            int foo=Integer.parseInt(tempBit.toString(),2);
            encryptedText.append(alphabet.charAt(foo));


        }

        return encryptedText.toString();

    }



    public static String decrypt(String plainText,String key,String alphabet){
        StringBuilder decryptedText= new StringBuilder();
        StringBuilder plainTextBinary=new StringBuilder();
        StringBuilder keyBinary = new StringBuilder();
        StringBuilder cipherBinary=new StringBuilder();

        for (int i=plainText.length();i<key.length();i++){
            plainText+="X";
        }
        int counter=0;
        for(int i=key.length();i<plainText.length();i++){
            if(counter>key.length()) counter=0;
            key+= key.charAt(counter);
            counter++;
        }

        for(int i=0;i<plainText.length();i++){
            Integer index=alphabet.indexOf(plainText.charAt(i));
            Integer keyIndex=alphabet.indexOf(key.charAt(i));
            StringBuilder plainBinaryString= new StringBuilder();
            StringBuilder keyBinaryString=new StringBuilder();
            plainBinaryString.append(index.toBinaryString(index));
            keyBinaryString.append(keyIndex.toBinaryString(keyIndex));

            for(int j=plainBinaryString.length(); j< 5;j++){
                plainBinaryString.reverse().append('0').reverse();

            }

            for(int j=keyBinaryString.length();j<5;j++){
                keyBinaryString.reverse().append('0').reverse();
            }
            plainTextBinary.append(plainBinaryString);
            keyBinary.append(keyBinaryString);
        }

        System.out.println(plainTextBinary.toString());
        System.out.println(keyBinary);

        for(int j=0;j<keyBinary.length();j++){
            int keyBit, plainBit;
            keyBit=keyBinary.charAt(j);
            plainBit=plainTextBinary.charAt(j);
            if(keyBit==plainBit) cipherBinary.append("0");
            else cipherBinary.append("1");
        }
        System.out.println(cipherBinary);

        for(int i=0;i<cipherBinary.length();i=i+5){
            StringBuilder tempBit=new StringBuilder();
            for(int j=0;j<5;j++){
                tempBit.append(cipherBinary.charAt(i+j));
            }
            int foo=Integer.parseInt(tempBit.toString(),2);
            decryptedText.append(alphabet.charAt(foo));

        }
        return decryptedText.toString();

    }
}