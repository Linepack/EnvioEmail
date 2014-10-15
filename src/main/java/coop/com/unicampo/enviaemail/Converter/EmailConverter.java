/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coop.com.unicampo.enviaemail.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DBS
 */
public class EmailConverter {

    public static Map<String, String> stringToMap(String string, String caracterToSplit) {

        Map<String, String> map = new HashMap<>();
        String[] stringArray = string.split(caracterToSplit);

        if (stringArray.length == 0) {
            map.put(string, string);
        } else {

            for (Integer iterator = 0; iterator < stringArray.length; iterator++) {
                map.put(stringArray[iterator], stringArray[iterator]);
            }
        }
        return map;
    }

    public static List<String> stringToList(String string, String caracterToSplit) {

        List<String> list = new ArrayList<>();

        String[] stringArray = string.split(caracterToSplit);
        if (stringArray.length == 0) {
            list.add(string);
        } else {

            for (Integer iterator = 0; iterator < stringArray.length; iterator++) {
                list.add(stringArray[iterator]);
            }
        }
        return list;
    }

}
