package com.example.newchinesechess;

/**
 * Created by MR.Pan on 2016/5/15.
 */
public class ChessBoardLayout {
    private int Guard = 2;
    private int Soldiers = 5;
    private int[][] ChessBoard = new int[][]{// 棋盘[9][10]
            {2, 3, 6, 5, 1, 5, 6, 3, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 4, 0, 0, 0, 0, 0, 4, 0},
            {7, 0, 7, 0, 7, 0, 7, 0, 7},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},

            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {14, 0, 14, 0, 14, 0, 14, 0, 14},
            {0, 11, 0, 0, 0, 0, 0, 11, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {9, 10, 13, 12, 8, 12, 13, 10, 9}};

    private static Boolean fourGuard = false;
    private static Boolean sevenSoldiers = false;

    public static void setSevenSoldiers(Boolean sevenSoldiers) {
        ChessBoardLayout.sevenSoldiers = sevenSoldiers;
    }

    public static void setFourGuard(Boolean fourGuard) {
        ChessBoardLayout.fourGuard = fourGuard;

    }

    public ChessBoardLayout() {
        if (fourGuard) {

            ChessBoard[0][4] = 0;
            ChessBoard[1][4] = 1;
            ChessBoard[2][3] = 5;
            ChessBoard[2][5] = 5;

            ChessBoard[9][4] = 0;
            ChessBoard[8][4] = 8;
            ChessBoard[7][3] = 12;
            ChessBoard[7][5] = 12;
            Guard=4;

        }
        if (sevenSoldiers) {
            ChessBoard[3][3] = 7;
            ChessBoard[3][5] = 7;

            ChessBoard[6][3] = 14;
            ChessBoard[6][5] = 14;
            Soldiers=7;
        }


    }

    public int[][] getChessBoard() {
        return ChessBoard;
    }
    public int[] getQZcount(){
        int Qzcount[] = new int[]{
                0,1,2,2,2,Guard,2,Soldiers,1,2,2,2,Guard,2,Soldiers
                //从左到右 	帅车马炮士相兵
        };
        return Qzcount;
    }
}
