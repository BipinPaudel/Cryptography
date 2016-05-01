package com.company;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bips on 4/6/16.
 */
public class Des {
    static ArrayList<String> sixteenSubKeys = new ArrayList();

    public static void main(String[] args) {
        String plaintext = "0123456789ABCDEF";
        String key = "133457799BBCDFF1";

        StringBuilder binaryKey = hexToBinary(key);
        //this function calculates upto finding 16 subkeys
        initialPermutationOfKey(binaryKey);

        StringBuilder binaryMessage = hexToBinary(plaintext);
        String encryptedHex = initialPermutationOfMessage(binaryMessage);
        System.out.println("Plain Message is: " + plaintext);
        System.out.println("Initial key is: " + key);
        System.out.println("Encryted Hex number is: " + encryptedHex);
    }

    //calculates first k+ from binary key
    public static void initialPermutationOfKey(StringBuilder k) {
        ArrayList<Integer> pcTable = new ArrayList(Arrays.asList(
                57, 49, 41, 33, 25, 17, 9,
                1, 58, 50, 42, 34, 26, 18,
                10, 2, 59, 51, 43, 35, 27,
                19, 11, 3, 60, 52, 44, 36,
                63, 55, 47, 39, 31, 23, 15,
                7, 62, 54, 46, 38, 30, 22,
                14, 6, 61, 53, 45, 37, 29,
                21, 13, 5, 28, 20, 12, 4));
        StringBuilder kPlus = new StringBuilder();
        for (int i = 1; i <= pcTable.size(); i++) {
            int indexFrompcTable = pcTable.get(i - 1);
            kPlus.append(k.charAt(indexFrompcTable - 1));
        }

        calculateCD(kPlus);
    }

    //calculates series of c's and d's from kplus
    public static void calculateCD(StringBuilder kplus) {
        ArrayList<String> c = new ArrayList<>();
        ArrayList<String> d = new ArrayList<>();
        c.add(kplus.substring(0, 28));
        d.add(kplus.substring(28, 56));

        for (int i = 1; i <= 16; i++) {
            StringBuilder tempc = new StringBuilder();
            StringBuilder tempd = new StringBuilder();
            if (i == 1 || i == 2 || i == 9 || i == 16) {
                String inc = c.get(i - 1);
                String ind = d.get(i - 1);

                tempc.append(inc.substring(1) + inc.substring(0, 1));
                c.add(tempc.toString());

                tempd.append(ind.substring(1) + ind.substring(0, 1));
                d.add(tempd.toString());
            } else {
                String inc = c.get(i - 1);
                String ind = d.get(i - 1);
                tempc.append(inc.substring(2) + inc.substring(0, 2));
                c.add(tempc.toString());

                tempd.append(ind.substring(2) + ind.substring(0, 2));
                d.add(tempd.toString());
            }
        }
        calculateSubK(c, d);
    }

    //calculates series of 16 sub k's from series of c and d
    public static void calculateSubK(ArrayList<String> c, ArrayList<String> d) {
        ArrayList<Integer> pcTable = new ArrayList<Integer>(Arrays.asList(
                14, 17, 11, 24, 1, 5,
                3, 28, 15, 6, 21, 10,
                23, 19, 12, 4, 26, 8,
                16, 7, 27, 20, 13, 2,
                41, 52, 31, 37, 47, 55,
                30, 40, 51, 45, 33, 48,
                44, 49, 39, 56, 34, 53,
                46, 42, 50, 36, 29, 32));
        ArrayList cd = new ArrayList();
        for (int i = 1; i < c.size(); i++) {
            StringBuilder temp = new StringBuilder();
            temp.append(c.get(i));
            temp.append(d.get(i));
            cd.add(temp);
        }
        for (int i = 1; i <= 16; i++) {
            StringBuilder tempStr = (StringBuilder) cd.get(i - 1);
            StringBuilder temp = new StringBuilder();
            for (int j = 1; j <= pcTable.size(); j++) {

                int indexFromPctable = pcTable.get(j - 1);
                temp.append(tempStr.charAt(indexFromPctable - 1));
            }
            sixteenSubKeys.add(temp.toString());
        }
    }

    //operation of message starts from this function.
    public static String initialPermutationOfMessage(StringBuilder binaryMessage) {
        ArrayList<Integer> pcTable = new ArrayList<Integer>(Arrays.asList(
                58, 50, 42, 34, 26, 18, 10, 2,
                60, 52, 44, 36, 28, 20, 12, 4,
                62, 54, 46, 38, 30, 22, 14, 6,
                64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17, 9, 1,
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5,
                63, 55, 47, 39, 31, 23, 15, 7));
        StringBuilder IP = new StringBuilder();
        for (int i = 1; i <= pcTable.size(); i++) {
            int indexFrompcTable = pcTable.get(i - 1);
            IP.append(binaryMessage.charAt(indexFrompcTable - 1));
        }
        String encryptedHex = calculateLRForMessage(IP);
        return encryptedHex;

    }

    // series of l and r has to be calculates
    public static String calculateLRForMessage(StringBuilder ip) {
        ArrayList<String> l = new ArrayList<>();
        ArrayList<String> r = new ArrayList<>();
        l.add(ip.substring(0, 32));
        r.add(ip.substring(32, 64));
        for (int i = 1; i <= 16; i++) {
            l.add(r.get(i - 1));
            //calculateFunctionForKAndR takes argument l, r and key
            //that performs all the sbox's operations
            r.add(calculateFunctionForKAndR(l.get(i - 1).toString(),
                    r.get(i - 1).toString(),
                    sixteenSubKeys.get(i - 1).toString()));
        }

        StringBuilder r_16_l_16 = new StringBuilder();
        r_16_l_16.append(r.get(16) + l.get(16));
        ArrayList<Integer> final_ip_inverse_table = new ArrayList<Integer>(Arrays.asList(
                40, 8, 48, 16, 56, 24, 64, 32,
                39, 7, 47, 15, 55, 23, 63, 31,
                38, 6, 46, 14, 54, 22, 62, 30,
                37, 5, 45, 13, 53, 21, 61, 29,
                36, 4, 44, 12, 52, 20, 60, 28,
                35, 3, 43, 11, 51, 19, 59, 27,
                34, 2, 42, 10, 50, 18, 58, 26,
                33, 1, 41, 9, 49, 17, 57, 25));
        StringBuilder ip_inverse = new StringBuilder();
        for (int i = 1; i <= final_ip_inverse_table.size(); i++) {
            int indexFrompcTable = final_ip_inverse_table.get(i - 1);
            ip_inverse.append(r_16_l_16.charAt(indexFrompcTable - 1));
        }
        StringBuilder encryptedHex = new StringBuilder();
        for (int i = 0; i < ip_inverse.length(); i = i + 4) {
            encryptedHex.append(Integer.toHexString(Integer.parseInt(ip_inverse.substring(i,i+4), 2)));
        }
        return encryptedHex.toString();
    }
    // function E is calculated.. conversion of bits from 32 to 48 is performed.

    public static String calculateFunctionForKAndR(String l, String r, String key) {
        ArrayList<Integer> eBitTable = new ArrayList<Integer>(Arrays.asList(
                32, 1, 2, 3, 4, 5,
                4, 5, 6, 7, 8, 9,
                8, 9, 10, 11, 12, 13,
                12, 13, 14, 15, 16, 17,
                16, 17, 18, 19, 20, 21,
                20, 21, 22, 23, 24, 25,
                24, 25, 26, 27, 28, 29,
                28, 29, 30, 31, 32, 1));

        StringBuilder eR = new StringBuilder();
        for (int i = 1; i <= eBitTable.size(); i++) {
            int indexFrompcTable = eBitTable.get(i - 1);
            eR.append(r.charAt(indexFrompcTable - 1));
        }
        StringBuilder k_ORed_eR = new StringBuilder();
        for (int i = 0; i < eR.length(); i++) {
            if (key.charAt(i) == eR.charAt(i)) {
                k_ORed_eR.append("0");
            } else {
                k_ORed_eR.append("1");
            }
        }
        StringBuilder finalR = sBox(k_ORed_eR, l);
        return finalR.toString();
    }

    public static StringBuilder sBox(StringBuilder k_ORed_eR, String l) {
        StringBuilder main_s = new StringBuilder();
        SBox object_of_sbox = new SBox();
        main_s.append(object_of_sbox.forS1(k_ORed_eR.substring(0, 6)));
        main_s.append(object_of_sbox.forS2(k_ORed_eR.substring(6, 12)));
        main_s.append(object_of_sbox.forS3(k_ORed_eR.substring(12, 18)));
        main_s.append(object_of_sbox.forS4(k_ORed_eR.substring(18, 24)));
        main_s.append(object_of_sbox.forS5(k_ORed_eR.substring(24, 30)));
        main_s.append(object_of_sbox.forS6(k_ORed_eR.substring(30, 36)));
        main_s.append(object_of_sbox.forS7(k_ORed_eR.substring(36, 42)));
        main_s.append(object_of_sbox.forS8(k_ORed_eR.substring(42, 48)));

        StringBuilder afterPermutation = object_of_sbox.permutateAfterSBox(main_s);
        StringBuilder finalR = new StringBuilder();
        for (int i = 0; i < afterPermutation.length(); i++) {
            if (afterPermutation.charAt(i) == l.charAt(i)) {
                finalR.append("0");
            } else {
                finalR.append("1");
            }
        }
        return finalR;
    }

    //this function converts hexadecimal to binary
    public static StringBuilder hexToBinary(String hex) {
        StringBuilder binaryBuilder = new StringBuilder();
        for (int i = 0; i < hex.length(); i++) {
            int hexInt = Integer.parseInt(String.valueOf(hex.charAt(i)), 16);
            StringBuilder temp = new StringBuilder(Integer.toBinaryString(hexInt));

            int length = temp.length();
            for (int j = length; j < 4; j++) {
                temp.insert(0, '0');
            }
            binaryBuilder.append(temp);
        }
        return binaryBuilder;
    }

    //class that performs all the functionalities of sbox operation
    static class SBox {

        public StringBuilder forS1(String str) {
            Integer[][] sTable = new Integer[][]{
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            };
            StringBuilder fourLengthBinary = calculateS(sTable, str);
            return fourLengthBinary;
        }

        public StringBuilder forS2(String str) {
            Integer[][] sTable = new Integer[][]{
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            };
            StringBuilder fourLengthBinary = calculateS(sTable, str);
            return fourLengthBinary;
        }

        public StringBuilder forS3(String str) {
            Integer[][] sTable = new Integer[][]{
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            };
            StringBuilder fourLengthBinary = calculateS(sTable, str);
            return fourLengthBinary;
        }

        public StringBuilder forS4(String str) {
            Integer[][] sTable = new Integer[][]{
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            };
            StringBuilder fourLengthBinary = calculateS(sTable, str);
            return fourLengthBinary;
        }

        public StringBuilder forS5(String str) {
            Integer[][] sTable = new Integer[][]{
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            };
            StringBuilder fourLengthBinary = calculateS(sTable, str);
            return fourLengthBinary;
        }

        public StringBuilder forS6(String str) {
            Integer[][] sTable = new Integer[][]{
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            };
            StringBuilder fourLengthBinary = calculateS(sTable, str);
            return fourLengthBinary;
        }

        public StringBuilder forS7(String str) {
            Integer[][] sTable = new Integer[][]{
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            };
            StringBuilder fourLengthBinary = calculateS(sTable, str);
            return fourLengthBinary;
        }

        public StringBuilder forS8(String str) {
            Integer[][] sTable = new Integer[][]{
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            };
            StringBuilder fourLengthBinary = calculateS(sTable, str);
            return fourLengthBinary;
        }

        //cslculateS calculates
        public StringBuilder calculateS(Integer[][] sTable, String str) {
            StringBuilder fourLengthBinary = new StringBuilder();
            String rowString = String.valueOf(str.charAt(0)) + String.valueOf(str.charAt(5));
            int row = Integer.parseInt(rowString, 2);

            String colString = String.valueOf(str.substring(1, 5));
            int col = Integer.parseInt(colString, 2);
            String temp = Integer.toBinaryString(sTable[row][col]);

            fourLengthBinary.append(temp);
            for (int i = fourLengthBinary.length(); i < 4; i++) {
                fourLengthBinary.reverse().append("0").reverse();
            }
            return fourLengthBinary;
        }

        public StringBuilder permutateAfterSBox(StringBuilder str) {
            ArrayList<Integer> pTable = new ArrayList<Integer>(Arrays.asList(
                    16, 7, 20, 21,
                    29, 12, 28, 17,
                    1, 15, 23, 26,
                    5, 18, 31, 10,
                    2, 8, 24, 14,
                    32, 27, 3, 9,
                    19, 13, 30, 6,
                    22, 11, 4, 25)
            );
            StringBuilder finalStr = new StringBuilder();
            for (int i = 1; i <= pTable.size(); i++) {
                int indexFrompcTable = pTable.get(i - 1);
                finalStr.append(str.charAt(indexFrompcTable - 1));
            }
            return finalStr;
        }
    }
}
