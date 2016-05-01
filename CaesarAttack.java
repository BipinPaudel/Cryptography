package com.company;

import java.util.*;

public class CaesarAttack {
    public static void main(String[] args) {
        // write your code here
        LinkedHashMap<Character, Double> eng_character_freq = new LinkedHashMap<Character, Double>();

        double frequencies[] = {0.080, 0.015, 0.030, 0.040, 0.130, 0.020, 0.015,
                0.060, 0.065, 0.005, 0.005, 0.035, 0.030, 0.070, 0.080,
                0.020, 0.002, 0.065, 0.060, 0.090, 0.030, 0.010, 0.015, 0.005, 0.020, 0.002};

        int counter = 65;
        String alphabet = "";

        for (int j = 0; j < 26; j++) { // inserting universal frequency in eng_character_Frequency hashmap
            eng_character_freq.put(((char) counter), frequencies[j]);
            alphabet += (char) counter;
            counter++;
        }

        String encrypted_string = "KHOOR ZRUOG";
        LinkedHashMap<Character, Double> frequency = new LinkedHashMap();
        frequency = calculateFrequencyOfEncryptedString(encrypted_string);

        HashMap<Integer,Double> sie_dictionary = new HashMap<Integer,Double>();
        sie_dictionary = calculateSieArray(eng_character_freq, frequency, alphabet);

        ArrayList<Double> initial_values_of_sie=new ArrayList<Double>();
        ArrayList<Double> sorted_values_of_sie=new ArrayList<Double>();
        Set set=sie_dictionary.entrySet();
        Iterator iterator=set.iterator();
        while(iterator.hasNext()){
            Map.Entry mentry= (Map.Entry) iterator.next();
            initial_values_of_sie.add((Double) mentry.getValue());
            sorted_values_of_sie.add((Double) mentry.getValue());
        }
        Collections.sort(sorted_values_of_sie, Collections.reverseOrder());
        checkInDictionary(encrypted_string,initial_values_of_sie,sorted_values_of_sie);
    }

    public static void checkInDictionary(String encrypted_string,ArrayList<Double> initial_sie_dictionary,
                                         ArrayList<Double> sorted_values_of_sie){
        int key;
        int condition=1;
        int counter=0;
        Scanner input=new Scanner(System.in);
        while(condition==1 && counter < 26){
            double temp=sorted_values_of_sie.get(counter);
            key=initial_sie_dictionary.indexOf(temp);
            String decrypted=decrypt(encrypted_string,key);
            System.out.println(decrypted);
            System.out.println("1 for go to next 0 for exit");

            if(input.nextInt()==1){
                counter++;
            }
            else{
                condition=0;
                System.out.println("Bye Bye you are a great hacker");
            }
        }
    }



    public static String decrypt(String encrypted_message,int key){
        StringBuilder decrypted_message=new StringBuilder();
        for(int i=0;i<encrypted_message.length();i++){

            if(encrypted_message.charAt(i)==' ') decrypted_message.append(" ");
            else {
                int final_ascii= encrypted_message.charAt(i)-key;

                if(final_ascii <65){
                    final_ascii= ((final_ascii+ 25)%90) +1;

                }
                decrypted_message.append((char)final_ascii);
            }
        }
        return decrypted_message.toString();


    }

    public static HashMap<Integer,Double> calculateSieArray(LinkedHashMap<Character, Double> universal_hashmap,
                                                      LinkedHashMap<Character, Double> frequency,
                                                      String alphabet) {
        HashMap<Integer,Double> sie=new HashMap<>();
        ArrayList<Character> key_array_of_frequency = new ArrayList<Character>();
        Set set = frequency.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            key_array_of_frequency.add((Character) mentry.getKey());//inserting calculated frequency's key in arraylist
        }
        for (int i = 0; i < 26; i++) {
            double individual_sum = 0;
            for (int j = 0; j < frequency.size(); j++) {//upto the total size of distinct elements
                Character c = key_array_of_frequency.get(j);
                int index_of_c_in_universal_map = alphabet.toString().indexOf(c);
                int temp = (index_of_c_in_universal_map - i+26)%26;
                double first_term = frequency.get(c);//f(c)
                double second_term = universal_hashmap.get(alphabet.toString().charAt(temp));//p(c)
                individual_sum += first_term * second_term;
            }
            sie.put(i, individual_sum);
        }
            return sie;
    }

    public static LinkedHashMap calculateFrequencyOfEncryptedString(String encrypted_string) {

        LinkedHashMap<Character, Double> each_character_map = new LinkedHashMap<Character, Double>();
        double length_of_string = encrypted_string.length();
        int number_of_spaces = 0;
        for (int i = 0; i < encrypted_string.length(); i++) {
            if (encrypted_string.charAt(i) == ' ') number_of_spaces++;
        }
        for (int i = 0; i < length_of_string; i++) {

            Character cc = encrypted_string.charAt(i);
            if (cc == ' ') {

            } else {
                int frequency_of_character = 0;
                if (each_character_map.containsKey(cc)) {

                } else {
                    for (int j = 0; j < length_of_string; j++) {
                        if (cc.equals(encrypted_string.charAt(j))) {
                            frequency_of_character++;
                        }
                    }
                    each_character_map.put(cc, (frequency_of_character / (length_of_string - number_of_spaces)));
                }
            }
        }
        return each_character_map;
    }
}
