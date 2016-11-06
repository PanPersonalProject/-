package com.example.newchinesechess;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;

/**
 * Created by MR.Pan on 2016/5/15.
 */
public class CustomChessBoardActivity extends Activity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox fourGuard;
    private CheckBox sevenSoldiers ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_chessboard);
        sharedPreferences=getSharedPreferences("Setting", MODE_PRIVATE);
        editor= sharedPreferences.edit();
        fourGuard = (CheckBox) findViewById(R.id.fourGuard);
        sevenSoldiers= (CheckBox) findViewById(R.id.sevenSoldiers);
        init();
    }

    private void init() {
        fourGuard.setChecked(sharedPreferences.getBoolean("fourGuard",false));
        sevenSoldiers.setChecked(sharedPreferences.getBoolean("sevenSoldiers",false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putBoolean("fourGuard",fourGuard.isChecked());
        editor.putBoolean("sevenSoldiers",sevenSoldiers.isChecked());
        editor.commit();
        ChessBoardLayout.setFourGuard(fourGuard.isChecked());
        ChessBoardLayout.setSevenSoldiers(sevenSoldiers.isChecked());

    }
}
