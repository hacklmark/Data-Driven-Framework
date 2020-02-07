package com.w2a.rough;
import java.util.Hashtable;

    public class Hash {
        public static void main(String[] args) {
            //Created hashtable class object to use Its different properties.
            Hashtable<String, Integer> t1 = new Hashtable<String, Integer>();
            t1.put("Legs", 4); //Store value 4 In key = Legs
            t1.put("Eyes",2); //Store value 2 In key = Eyes
            t1.put("Mouth",1); //Store value 1 In key = Mouth

            //Accessing hash table values using keys.
            System.out.println("Animal Legs = " +t1.get("Legs"));
            System.out.println("Animal Eyes = " +t1.get("Eyes"));
            System.out.println("Animal Mouth = " +t1.get("Mouth"));
        }
    }

