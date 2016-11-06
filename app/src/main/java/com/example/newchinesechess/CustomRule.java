package com.example.newchinesechess;

import android.content.SharedPreferences;

/**
 * Created by MR.Pan on 2016/5/12.
 */
public class CustomRule {

    private static Boolean noDifferentAttack = false;

    public static void setNoDifferentAttack(Boolean noDifferentAttack) {
        CustomRule.noDifferentAttack = noDifferentAttack;
    }

    public static Boolean getNoDifferentAttack() {
        return noDifferentAttack;
    }


}
