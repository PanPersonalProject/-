package com.example.newchinesechess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends Activity implements OnClickListener {
    private Button AiModeButton;
    private Button gameStartButton;
    private Button ruleButton;
    private Button ChessBoardButton;
    private Button quitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init(); //初始化按钮

    }

    private void init() {
        // TODO 自动生成的方法存根
        gameStartButton = (Button) findViewById(R.id.gameStartButton);
        ruleButton = (Button) findViewById(R.id.ruleButton);
        ChessBoardButton = (Button) findViewById(R.id.ChessBoardButton);
        quitButton = (Button) findViewById(R.id.quitButton);
        AiModeButton=(Button) findViewById(R.id.AiModeButton);

        gameStartButton.setOnClickListener(this);
        ruleButton.setOnClickListener(this);
        ChessBoardButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);
        AiModeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.gameStartButton:
                Intent intent=new Intent();
                intent.setClass(this, GameActivity.class);
                intent.putExtra("AiMode",false);
                startActivity(intent);
                break;
            case R.id.ruleButton:
                startActivity(new Intent(this, CustomRuleActivity.class));
                break;
            case R.id.ChessBoardButton:
                startActivity(new Intent(this, CustomChessBoardActivity.class));
                break;

            case R.id.quitButton:

                this.finish();
                break;
            case R.id.AiModeButton:
                Intent i=new Intent();
                i.setClass(this, GameActivity.class);

                    i.putExtra("AiMode",true);

                startActivity(i);
                break;
        }
    }

}
