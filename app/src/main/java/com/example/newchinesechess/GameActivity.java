package com.example.newchinesechess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;

public class GameActivity extends Activity {
    private MediaPlayer bgm;
    public Boolean AiMode = false;
    public Dialog alertDialog ;
    public AlertDialog attckSelf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        ChessGameView chessGameView = new ChessGameView(this);
        setContentView(chessGameView);//new ChessGameView(this)
        bgm = MediaPlayer.create(this, R.raw.bgm);
        AiMode= getIntent().getBooleanExtra("AiMode",false);
        bgm.start();
        bgm.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                bgm.seekTo(0);
                bgm.start();
            }
        });
        alertDialog = new AlertDialog.Builder(this).
                setTitle("你胜利了").
                setMessage("再来一局？").
                setIcon(android.R.drawable.ic_dialog_info).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        setContentView(new ChessGameView(GameActivity.this));
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        bgm.stop();
                        GameActivity.this.finish();
                    }
                }).
                create();
//        attckSelf= new AlertDialog.Builder(GameActivity.this).setTitle("是否攻击自己的棋子？")
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 点击“确认”后的操作
//
//
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (ChessGameView.isBlackTurn == true) {
//                            ChessGameView. isBlackTurn = false;
//                        } else {
//                            ChessGameView.isBlackTurn = true;
//                        }
//                    }
//                }). create();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // 监听back按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {

        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        bgm.stop();
                        GameActivity.this.finish();

                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }


}
