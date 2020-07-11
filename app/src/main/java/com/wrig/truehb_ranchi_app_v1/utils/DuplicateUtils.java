package com.wrig.truehb_ranchi_app_v1.utils;

import java.util.ArrayList;
import java.util.List;

public class DuplicateUtils {
    public static List<String> removeDuplicatesFromList(List<String> descriptions) {
        List<String> tempList = new ArrayList<String>();
        for (String desc : descriptions) {
            if (!tempList.contains(desc)) {
                tempList.add(desc);
            }
        }
        descriptions = tempList;
        tempList = null;
        return descriptions;
    }
}
