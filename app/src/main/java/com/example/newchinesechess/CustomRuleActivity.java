package com.example.newchinesechess;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;

/**
 * Created by MR.Pan on 2016/5/12.
 */
public class CustomRuleActivity extends Activity {
    CheckBox noDifferentAttackCheckBox;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_rule);
        sharedPreferences=getSharedPreferences("Setting", MODE_PRIVATE);
        editor= sharedPreferences.edit();
        noDifferentAttackCheckBox = (CheckBox) findViewById(R.id.noDifferentAttack);

        init();
    }

    private void init() {
        noDifferentAttackCheckBox.setChecked( sharedPreferences.getBoolean("noDifferentAttackCheckBox",false));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putBoolean("noDifferentAttackCheckBox",noDifferentAttackCheckBox.isChecked());
        editor.commit();
        CustomRule.setNoDifferentAttack(noDifferentAttackCheckBox.isChecked());

    }
}
