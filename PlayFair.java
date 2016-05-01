package com.company;

import java.util.ArrayList;

/**
 * Created by bips on 3/2/16.
 */
public class PlayFair {
    public static void main(String[] args) {
        String key = "DEERWALKHIKING";
        String plainText="ILOVEHIKINGBUTIAMNOTGIVENPRIORITY";
        Character alphabetMatrix[][]=constructMatrix(key);
        System.out.println("The plain text is: ");
        for (int a=0;a<5;a++){
            for(int b=0;b<5;b++){
                System.out.print(alphabetMatrix[a][b] + "\t");
            }
            System.out.println();
        }
        encrypt(plainText,alphabetMatrix);
    }


    public static String finalText(String plainText){
        //function to insert filler in appropriate positions
        StringBuilder plain= new StringBuilder();
        plain.append(plainText);
        for(int i=0;i< plain.length()-1;i+=2 ){
            if(plain.charAt(i)==plain.charAt(i+1)){
                plain.insert(i+1,'X');
            }
        }
        if(plain.length()%2==1) plain.append('X');
        return plain.toString();
    }



    public static void encrypt(String plainText,Character[][] alphabetMatrix){
        plainText=finalText(plainText);
        int plainCounter=0;
        String encryptedString="";
        System.out.println(plainText);
        StringBuilder bb= new StringBuilder();

        while(encryptedString.length()<plainText.length()){
            Character firstCharacter;
            Character secondCharacter;
            firstCharacter=plainText.charAt(plainCounter);
            secondCharacter=plainText.charAt(plainCounter+1);
            plainCounter=plainCounter+2;
            ArrayList<Integer> indexes=checkPosition(firstCharacter,secondCharacter,alphabetMatrix);
            int IIndexForA=indexes.get(0),JIndexForA=indexes.get(1),IIndexForB=indexes.get(2),JIndexForB=indexes.get(3);
            if(IIndexForA==IIndexForB) {
                JIndexForA++;
                JIndexForB++;
                if(JIndexForA==5) JIndexForA=0;
                if(JIndexForB==5) JIndexForB=0;
                encryptedString+=alphabetMatrix[IIndexForA][JIndexForA]+""+alphabetMatrix[IIndexForB][JIndexForB];
            }
            else if(JIndexForA==JIndexForB){
                IIndexForA++;
                IIndexForB++;
                if(IIndexForA==5) IIndexForA=0;
                if(IIndexForB==5) IIndexForB=0;
                encryptedString+=alphabetMatrix[IIndexForA][JIndexForA]+""+alphabetMatrix[IIndexForB][JIndexForB];
            }
            else{
                encryptedString+= alphabetMatrix[IIndexForA][JIndexForB] +""+ alphabetMatrix[IIndexForB][JIndexForA];
            }
        }
        System.out.println("Encrypted String is" +encryptedString);
        decrypt(encryptedString,alphabetMatrix);
    }

    public static void decrypt(String plainText,Character[][] alphabetMatrix){

        int encryptedTextCounter=0;
        String decryptedString="";


        while(decryptedString.length()<plainText.length()){
            Character firstCharacter;
            Character secondCharacter;
            firstCharacter=plainText.charAt(encryptedTextCounter);
            secondCharacter=plainText.charAt(encryptedTextCounter+1);
            encryptedTextCounter=encryptedTextCounter+2;
            ArrayList<Integer> indexes=checkPosition(firstCharacter,secondCharacter,alphabetMatrix);
            int IIndexForA=indexes.get(0),JIndexForA=indexes.get(1),IIndexForB=indexes.get(2),JIndexForB=indexes.get(3);
            if(IIndexForA==IIndexForB) {
                JIndexForA--;
                JIndexForB--;
                if(JIndexForA==-1) JIndexForA=4;
                if(JIndexForB==-1) JIndexForB=4;
                decryptedString+=alphabetMatrix[IIndexForA][JIndexForA]+""+alphabetMatrix[IIndexForB][JIndexForB];
            }
            else if(JIndexForA==JIndexForB){
                IIndexForA--;
                IIndexForB--;
                if(IIndexForA==-1) IIndexForA=4;
                if(IIndexForB==-1) IIndexForB=4;
                decryptedString+=alphabetMatrix[IIndexForA][JIndexForA]+""+alphabetMatrix[IIndexForB][JIndexForB];
            }
            else{
                decryptedString+= alphabetMatrix[IIndexForA][JIndexForB] +""+ alphabetMatrix[IIndexForB][JIndexForA];
            }
        }
        System.out.println("Decrypted String is "+decryptedString);
    }

    public static ArrayList<Integer> checkPosition(Character a, Character b,Character[][] alphabetMatrix){
        ArrayList<Integer> arrayList=new ArrayList<Integer>();
        int iIndexOfA=0;
        int jIndexOfA=0;
        int iIndexOfB=0;
        int jIndexOfB=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(alphabetMatrix[i][j]==a){
                    iIndexOfA=i;
                    jIndexOfA=j;
                    continue;
                }
                if(alphabetMatrix[i][j]==b){
                    iIndexOfB=i;
                    jIndexOfB=j;
                    continue;
                }
            }
        }
        arrayList.add(iIndexOfA);
        arrayList.add(jIndexOfA);
        arrayList.add(iIndexOfB);
        arrayList.add(jIndexOfB);
        return arrayList;
    }

    public static Character[][] constructMatrix(String key) {
        Character[][] alphabetArray = new Character[5][5];
        int i=0,j=0,keyCounter=0;
        while(keyCounter< key.length() ){
            Character keyAlphabet= key.charAt(keyCounter);
            int checkResult= checkInMatrix(alphabetArray,keyAlphabet);
            if(checkResult==0){
                alphabetArray[i][j]= keyAlphabet;
                j = j+1;
                if(j==5){
                    j=0;
                    i=i+1;
                }
            }
            keyCounter++;
        }

        int ascii=65;
        while(ascii< 91){
            if(ascii==74){
                ascii++;
                continue;
            }
            Character remainingAlphabet = (char) ascii;
            int checkResult= checkInMatrix(alphabetArray,remainingAlphabet);
            if(checkResult==0){
                alphabetArray[i][j]= remainingAlphabet;
                j = j+1;
                if(j==5){
                    j=0;
                    i=i+1;
                }
            }
            ascii++;
        }
        return alphabetArray;
    }

    public static int checkInMatrix(Character[][] alphabetArray,Character singleAlphabet){
        int result=0;
        for(int  i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if (singleAlphabet==alphabetArray[i][j]){
                    result=1;
                    break;
                }
            }
        }
        return result;
    }
}