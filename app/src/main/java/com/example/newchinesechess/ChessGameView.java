package com.example.newchinesechess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ChessGameView extends ImageView {


    private QZMove qzMove;
    private PCNextMove_1 pcNextMove_1;
    private ChessBoardLayout chessBoardLayout;//棋盘布置
    private Boolean noDifferentAttack;
    private int redTime;
    private int blackTime;
    private GameActivity gameActivity;
    private Rule rule;
    private Bitmap qiPan;// 棋盘图片
    private Bitmap background;// 背景图片
    private Bitmap[] hongZi = new Bitmap[7];// 红子的图片数组
    private Bitmap[] heiZi = new Bitmap[7];// 黑子的图片数组
    private Bitmap select;
    private int[][] qizi;
    private int Qzcount[];
    private Paint paint;
    private int geziLength; //棋盘格子边长，正方形
    private int marginX;//第一颗子距X距离
    private int marginY;//第一颗子距Y距离
    /*
    点击开始位置XY和目标XY值
    */
    private int selectX;
    private int selectY;
    private float getX;
    private float getY;
    private int startX;
    private int startY;
    private int targetX;
    private int targetY;


    private Boolean isChoosed = false;//选择棋子
    protected Boolean isBlackTurn = true;
    protected Boolean isRedTurn = true;//ai模式
    private MediaPlayer xuanziMediaPlayer;
    private MediaPlayer luoziMediaPlayer;

    public ChessGameView(Context context) {// 代码当中引用
        super(context);
        // TODO 自动生成的构造函数存根
        init();
        xuanziMediaPlayer = MediaPlayer.create(context, R.raw.xuanzi);
        luoziMediaPlayer = MediaPlayer.create(context, R.raw.luozi);
        gameActivity = (GameActivity) context;
    }

    public ChessGameView(Context context, AttributeSet attrs) { // 布局当中引用
        super(context, attrs);
        // TODO 自动生成的构造函数存根
        init();

        gameActivity = (GameActivity) context;
    }

    private void init() {
        // TODO 自动生成的方法存根
        pcNextMove_1 = new PCNextMove_1();
        redTime = 0;
        blackTime = 0;
        noDifferentAttack = CustomRule.getNoDifferentAttack();
        rule = new Rule();
        selectX = -100;
        selectY = -100;
        Matrix qipan1080p = new Matrix();
        Matrix qizi1080p = new Matrix();
        Matrix xuanze1080p = new Matrix();
        qipan1080p.postScale(1.15f, 1.15f); //缩放1.2倍
        qizi1080p.postScale(0.7f, 0.7f);
        xuanze1080p.postScale(0.72f, 0.72f);
        geziLength = 117;//棋盘格子边长，正方形
        marginX = 15;//第一颗子距X距离
        marginY = 380;//

        background = BitmapFactory.decodeResource(getResources(),
                R.drawable.background); // 背景图片
        select = BitmapFactory.decodeResource(getResources(),
                R.drawable.xuanzi_png); // 选中框
        select = Bitmap.createBitmap(select, 0, 0, select.getWidth(),
                select.getHeight(), xuanze1080p, true);
        qiPan = BitmapFactory.decodeResource(getResources(), R.drawable.qipan);// 棋盘图片

        qiPan = Bitmap.createBitmap(qiPan, 0, 0, qiPan.getWidth(),
                qiPan.getHeight(), qipan1080p, true);
        hongZi[0] = BitmapFactory.decodeResource(getResources(),
                R.drawable.hongjiang);// 红将
        hongZi[1] = BitmapFactory.decodeResource(getResources(),
                R.drawable.hongju);// 红车
        hongZi[2] = BitmapFactory.decodeResource(getResources(),
                R.drawable.hongma);// 红马
        hongZi[3] = BitmapFactory.decodeResource(getResources(),
                R.drawable.hongpao);// 红砲
        hongZi[4] = BitmapFactory.decodeResource(getResources(),
                R.drawable.hongshi);// 红仕
        hongZi[5] = BitmapFactory.decodeResource(getResources(),
                R.drawable.hongxiang);// 红相
        hongZi[6] = BitmapFactory.decodeResource(getResources(),
                R.drawable.hongzu);// 红卒

        heiZi[0] = BitmapFactory.decodeResource(getResources(),
                R.drawable.heishuai);// 黑帅
        heiZi[1] = BitmapFactory.decodeResource(getResources(),
                R.drawable.heiju);// 黑车
        heiZi[2] = BitmapFactory.decodeResource(getResources(),
                R.drawable.heima);// 黑马
        heiZi[3] = BitmapFactory.decodeResource(getResources(),
                R.drawable.heipao);// 黑炮
        heiZi[4] = BitmapFactory.decodeResource(getResources(),
                R.drawable.heishi);// 黑士
        heiZi[5] = BitmapFactory.decodeResource(getResources(),
                R.drawable.heixiang);// 黑象
        heiZi[6] = BitmapFactory.decodeResource(getResources(),
                R.drawable.heibing);// 黑兵

        for (int i = 0; i <= 6; i++) {
            hongZi[i] = Bitmap.createBitmap(hongZi[i], 0, 0, hongZi[i].getWidth(),
                    hongZi[i].getHeight(), qizi1080p, true);
        }
        for (int i = 0; i <= 6; i++) {
            heiZi[i] = Bitmap.createBitmap(heiZi[i], 0, 0, heiZi[i].getWidth(),
                    heiZi[i].getHeight(), qizi1080p, true);
        }
        chessBoardLayout = new ChessBoardLayout();
        qizi = chessBoardLayout.getChessBoard();
        Qzcount=chessBoardLayout.getQZcount();
        paint = new Paint();

    }

    // @Override
    // protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    // // TODO 自动生成的方法存根
    // super.onSizeChanged(w, h, oldw, oldh);
    // }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO 自动生成的方法存根
        super.onDraw(canvas);


        canvas.drawBitmap(background, 0, 0, paint); //绘制背景
        canvas.drawBitmap(qiPan, 0, getHeight() / 2 - qiPan.getHeight() / 2, paint); // 绘制棋盘
        canvas.drawBitmap(select, marginX - 27 + selectY * geziLength, marginY - 20 + selectX * geziLength, paint);
        for (int i = 0; i < qizi.length; i++) {// 绘制棋子  行
            for (int j = 0; j < qizi[i].length; j++) { //  列
                if (qizi[i][j] == 1) {// 为黑帅时
                    canvas.drawBitmap(heiZi[0], marginX + j * geziLength, marginY + i * geziLength, paint);
                } else if (qizi[i][j] == 2) {// 为黑车时
                    canvas.drawBitmap(heiZi[1], marginX + j * geziLength, marginY + i * geziLength, paint);
                } else if (qizi[i][j] == 3) {// 为黑马时
                    canvas.drawBitmap(heiZi[2], marginX + j * geziLength, marginY + i * geziLength, paint);
                } else if (qizi[i][j] == 4) {// 为黑炮时
                    canvas.drawBitmap(heiZi[3], marginX + j * geziLength, marginY + i * geziLength, paint);
                } else if (qizi[i][j] == 5) {// 为黑士时
                    canvas.drawBitmap(heiZi[4], marginX + j * geziLength, marginY + i * geziLength, paint);
                } else if (qizi[i][j] == 6) {// 为黑象时
                    canvas.drawBitmap(heiZi[5], marginX + j * geziLength, marginY + i * geziLength, paint);
                } else if (qizi[i][j] == 7) {// 为黑兵时
                    canvas.drawBitmap(heiZi[6], marginX + j * geziLength, marginY + i * geziLength, paint);
                } else if (qizi[i][j] == 8) {// 为红将时
                    canvas.drawBitmap(hongZi[0], marginX + j * geziLength, marginY + i * geziLength,
                            paint);
                } else if (qizi[i][j] == 9) {// 为红车时
                    canvas.drawBitmap(hongZi[1], marginX + j * geziLength, marginY + i * geziLength,
                            paint);
                } else if (qizi[i][j] == 10) {// 为红马时
                    canvas.drawBitmap(hongZi[2], marginX + j * geziLength, marginY + i * geziLength,
                            paint);
                } else if (qizi[i][j] == 11) {// 为红砲时
                    canvas.drawBitmap(hongZi[3], marginX + j * geziLength, marginY + i * geziLength,
                            paint);
                } else if (qizi[i][j] == 12) {// 为红仕时
                    canvas.drawBitmap(hongZi[4], marginX + j * geziLength, marginY + i * geziLength,
                            paint);
                } else if (qizi[i][j] == 13) {// 为红相时
                    canvas.drawBitmap(hongZi[5], marginX + j * geziLength, marginY + i * geziLength,
                            paint);
                } else if (qizi[i][j] == 14) {// 为红卒时
                    canvas.drawBitmap(hongZi[6], marginX + j * geziLength, marginY + i * geziLength,
                            paint);
                }

            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO 自动生成的方法存根
        try {

            if (gameActivity.AiMode) {
                playWithAI(event);
            } else {
                chessPlay(event);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
    }

    private void playWithAI(MotionEvent event) {

        getX = event.getX();
        getY = event.getY();

        if (getX >= 0 && getX <= getWidth() && getY >= getHeight() / 2 - qiPan.getHeight() / 2 && getY <= getHeight() / 2 + qiPan.getHeight() / 2)//在棋盘内
        {

            int pos[] = location(getX, getY);
            int x = pos[0];
            int y = pos[1];

            if (isChoosed == false) {//之前没有选中棋子

                if (qizi[x][y] != 0 && qizi[x][y] > 7 && isRedTurn) {//自己的子
                    selectX = x;
                    selectY = y;
                    postInvalidate();
                    xuanziMediaPlayer.start();

                    isChoosed = true;
                    startX = x;
                    startY = y;
                }

            } else {//之前选中过棋子
                if (qizi[x][y] != 0 && qizi[x][y] > 7 && isRedTurn) //自己的红子
                {

                    if (noDifferentAttack) {
                        isChoosed = false;
                        targetX = x;
                        targetY = y;
                        Boolean canMove = rule.canMove(qizi, startX, startY, targetX, targetY);

                        if (canMove) {
                            if (isRedTurn == true) {
                                isRedTurn = false;
                            } else {
                                isRedTurn = true;
                            }

                            luoziMediaPlayer.start();
                            qizi[targetX][targetY] = qizi[startX][startY];//移动棋子
                            qizi[startX][startY] = 0;//将原来处设空
                            selectX = -100;
                            selectY = -100;
                            targetX = -1;
                            targetY = -1;
                            startX = -1;
                            startY = -1;//还原保存点
                            postInvalidate();//刷onDraw()
                            qzMove = pcNextMove_1.Go(qizi, 0,Qzcount);
                            int x_to = qzMove.x_to;
                            int y_to = qzMove.y_to;
                            isRedTurn = true;
                            judgement(x_to, y_to);
                            qizi[x_to][y_to] = qizi[qzMove.x_now][qzMove.y_now];//移动棋子
                            qizi[qzMove.x_now][qzMove.y_now] = 0;//将原来处设空
                            postInvalidate();

                        }

                        return;//
                    } else {
                        selectX = x;
                        selectY = y;
                        postInvalidate();
                        xuanziMediaPlayer.start();
                    }
                    startX = x;
                    startY = y;
                } else {//敌方的子或空地

                    isChoosed = false;//标记当前没有选中棋子
                    targetX = x;
                    targetY = y;
                    Boolean canMove = rule.canMove(qizi, startX, startY, targetX, targetY);


                    Log.i("celiang", "canMove=" + canMove);
                    if (canMove) {
                        judgement(targetX, targetY);//判断胜利

                        luoziMediaPlayer.start();
                        qizi[targetX][targetY] = qizi[startX][startY];//移动棋子
                        qizi[startX][startY] = 0;//将原来处设空
                        selectX = -100;
                        selectY = -100;
                        targetX = -1;
                        targetY = -1;
                        startX = -1;
                        startY = -1;//还原保存点
                        if (isRedTurn == true) {
                            isRedTurn = false;
                        } else {
                            isRedTurn = true;
                        }
                        postInvalidate();//刷onDraw()

                        qzMove = pcNextMove_1.Go(qizi, 0,Qzcount);
                        int x_to = qzMove.x_to;
                        int y_to = qzMove.y_to;
                        isRedTurn = true;
                        judgement(x_to, y_to);
                        qizi[x_to][y_to] = qizi[qzMove.x_now][qzMove.y_now];//移动棋子
                        qizi[qzMove.x_now][qzMove.y_now] = 0;//将原来处设空
                        postInvalidate();


                    }
                }

            }


        }


    }

    private void judgement(int x, int y) {


        if (qizi[x][y] == 1) {//黑将
            gameActivity.alertDialog.setTitle("红方胜利");
            gameActivity.alertDialog.show();
        }
        if (qizi[x][y] == 8) {//红将
            gameActivity.alertDialog.setTitle("黑方胜利");
            gameActivity.alertDialog.show();
        }

    }

    private void chessPlay(MotionEvent event) {


        getX = event.getX();
        getY = event.getY();

        if (getX >= 0 && getX <= getWidth() && getY >= getHeight() / 2 - qiPan.getHeight() / 2 && getY <= getHeight() / 2 + qiPan.getHeight() / 2)//在棋盘内
        {

            int pos[] = location(getX, getY);
            int x = pos[0];
            int y = pos[1];
            // Log.i("celiang", "chessPlay:x "+x);
            //Log.i("celiang", "chessPlay:y "+y);
            if (isChoosed == false) {//之前没有选中棋子

                if ((qizi[x][y] != 0 && qizi[x][y] <= 7 && isBlackTurn) || (qizi[x][y] != 0 && qizi[x][y] > 7 && !isBlackTurn)) {//自己的黑子或红子
                    selectX = x;
                    selectY = y;
                    postInvalidate();
                    xuanziMediaPlayer.start();
                    isChoosed = true;
                    startX = x;
                    startY = y;
                }

            } else {//之前选中过棋子
                if ((qizi[x][y] != 0 && qizi[x][y] <= 7 && isBlackTurn) || (qizi[x][y] != 0 && qizi[x][y] > 7 && !isBlackTurn)) //自己的黑子或红子
                {

                    if (noDifferentAttack) {
                        isChoosed = false;
                        targetX = x;
                        targetY = y;
                        postInvalidate();
                        Boolean canMove = rule.canMove(qizi, startX, startY, targetX, targetY);
//                        Log.i("celiang", "chessPlay:startX " + startX);
//                        Log.i("celiang", "chessPlay:startY " + startY);
//                        Log.i("celiang", "chessPlay:targetX " + targetX);
//                        Log.i("celiang", "chessPlay:targetY " + targetY);
//                        Log.i("celiang", "canMove=" + canMove);
                        if (canMove) {
                            judgement(targetX, targetY);
                            if (isBlackTurn == true) {
                                isBlackTurn = false;
                            } else {
                                isBlackTurn = true;
                            }

                            if ((qizi[x][y] != 0 && qizi[x][y] <= 7 && isBlackTurn) || (qizi[x][y] != 0 && qizi[x][y] > 7 && !isBlackTurn)) {
                                gameActivity.attckSelf.show();
                            }

                            luoziMediaPlayer.start();
                            qizi[targetX][targetY] = qizi[startX][startY];//移动棋子
                            qizi[startX][startY] = 0;//将原来处设空
                            selectX = -100;
                            selectY = -100;
                            targetX = -1;
                            targetY = -1;
                            startX = -1;
                            startY = -1;//还原保存点

                        }
                        postInvalidate();//刷onDraw()
                        return;//
                    } else {
                        selectX = x;
                        selectY = y;
                        postInvalidate();
                        xuanziMediaPlayer.start();
                    }
                    startX = x;
                    startY = y;
                } else {//敌方的子或空地

                    isChoosed = false;//标记当前没有选中棋子
                    targetX = x;
                    targetY = y;
                    Boolean canMove = rule.canMove(qizi, startX, startY, targetX, targetY);
                    Log.i("celiang", "chessPlay:startX " + startX);
                    Log.i("celiang", "chessPlay:startY " + startY);
                    Log.i("celiang", "chessPlay:targetX " + targetX);
                    Log.i("celiang", "chessPlay:targetY " + targetY);


                    Log.i("celiang", "canMove=" + canMove);
                    if (canMove) {
                        judgement(targetX, targetY);

                        luoziMediaPlayer.start();
                        // move();//移动子
                        qizi[targetX][targetY] = qizi[startX][startY];//移动棋子
                        qizi[startX][startY] = 0;//将原来处设空

                        selectX = -100;
                        selectY = -100;
                        targetX = -1;
                        targetY = -1;
                        startX = -1;
                        startY = -1;//还原保存点
                        if (isBlackTurn == true) {
                            isBlackTurn = false;
                        } else {
                            isBlackTurn = true;
                        }
                        postInvalidate();//刷onDraw()
                    }
                }

            }


        }

    }

//    private void move() {
//        while (startX!= targetX || startY!= targetY) {
//            if (startX < targetX) {
//                startX++;
//            }
//            if (startX > targetX) {
//                startX--;
//            }
//            if (startY < targetY) {
//                startY++;
//            }
//            if (startY > targetY) {
//                startY--;
//            }
//            qizi[targetX][targetY] = qizi[startX][startY];//移动棋子
//            qizi[startX][startY] = 0;//
//
//            postInvalidate();
//        }
//    }

    private int[] location(float getX, float getY) {
        int[] pos = new int[2];
        pos[0] = (int) ((getY - marginY) / geziLength); //行数
        pos[1] = (int) ((getX - marginX) / geziLength); //列数

        return pos;
    }

}
