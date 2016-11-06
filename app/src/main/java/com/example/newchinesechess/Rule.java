package com.example.newchinesechess;

public class Rule {
    private Boolean noDifferentAttack;

    public Rule() {
        noDifferentAttack = CustomRule.getNoDifferentAttack();
    }

    public boolean canMove(int[][] qizi, int startX, int startY, int targetX, int targetY) {
        int i = 0;
        int j = 0;
        int moveChessID;//起始位置是什么棋子
        int targetID;//目的地是什么棋子或空地
        if (targetY < 0) {//当左边出界时
            return false;
        }
        if (targetY > 8) {//当右边出界时
            return false;
        }
        if (targetX < 0) {//当上边出界时
            return false;
        }
        if (targetX > 9) {//当下边出界时
            return false;
        }
        if (startY == targetY && startX == targetX) {//目的地与出发点相同，
            return false;
        }
        moveChessID = qizi[startX][startY];//得到起始棋子
        targetID = qizi[targetX][targetY];//得带终点棋子
        if (!noDifferentAttack) {
            if (isSameSide(moveChessID, targetID)) {//如果是同一阵营的
                return false;
            }
        }
        switch (moveChessID) {
            case 1://黑帅
                if (targetX > 2 || targetY < 3 || targetY > 5) {//出了九宫格
                    return false;
                }
                if ((Math.abs(startX - targetX) + Math.abs(targetY - startY)) > 1) {//只能走一步
                    return false;
                }
                break;
            case 5://黑士
                if (targetX > 2 || targetY < 3 || targetY > 5) {//出了九宫格
                    return false;
                }
                if (Math.abs(startX - targetX) != 1 || Math.abs(targetY - startY) != 1) {//走斜线
                    return false;
                }
                break;
            case 6://黑象
                if (targetX > 4) {//不能过河
                    return false;
                }
                if (Math.abs(startY - targetY) != 2 || Math.abs(startX - targetX) != 2) {//相走“田”字
                    return false;
                }
                if (qizi[(startX + targetX) / 2][(startY + targetY) / 2] != 0) {
                    return false;//相眼处有棋子
                }
                break;
            case 7://黑兵
                if (targetX < startX) {//不能回头
                    return false;
                }
                if (startX < 5 && startX == targetX) {//过河前只能直走
                    return false;
                }
                if (targetX - startX + Math.abs(targetY - startY) > 1) {//只能走一步，并且是直线
                    return false;
                }
                break;
            case 8://红将
                if (targetX < 7 || targetY > 5 || targetY < 3) {//出了九宫格
                    return false;
                }
                if ((Math.abs(startX - targetX) + Math.abs(targetY - startY)) > 1) {//只能走一步
                    return false;
                }
                break;
            case 2://黑车
            case 9://红车
                if (startX != targetX && startY != targetY) {//不走直线
                    return false;
                }
                if (startX == targetX) {//走横线
                    if (startY < targetY) {//向右走
                        for (i = startY + 1; i < targetY; i++) {//循环
                            if (qizi[startX][i] != 0) {
                                return false;//返回false
                            }
                        }
                    } else {//向左走
                        for (i = targetY + 1; i < startY; i++) {//循环
                            if (qizi[startX][i] != 0) {
                                return false;//返回false
                            }
                        }
                    }
                } else {//走的是竖线
                    if (startX < targetX) {//向右走
                        for (j = startX + 1; j < targetX; j++) {
                            if (qizi[j][startY] != 0)
                                return false;//返回false
                        }
                    } else {//想左走
                        for (j = targetX + 1; j < startX; j++) {
                            if (qizi[j][startY] != 0)
                                return false;//返回false
                        }
                    }
                }
                break;
            case 10://红马
            case 3://黑马
                if (!((Math.abs(targetY - startY) == 1 && Math.abs(targetX - startX) == 2)
                        || (Math.abs(targetY - startY) == 2 && Math.abs(targetX - startX) == 1))) {
                    return false;//马走的不是日字时
                }
                if (targetY - startY == 2) {//向右走
                    i = startY + 1;//移动
                    j = startX;
                } else if (startY - targetY == 2) {//向左走
                    i = startY - 1;//移动
                    j = startX;
                } else if (targetX - startX == 2) {//向下走
                    i = startY;//移动
                    j = startX + 1;
                } else if (startX - targetX == 2) {//向上走
                    i = startY;//移动
                    j = startX - 1;
                }
                if (qizi[j][i] != 0)
                    return false;//绊马腿
                break;
            case 11://红砲
            case 4://黑炮
                if (startX != targetX && startY != targetY) {//炮走直线
                    return false;//返回false
                }
                if (qizi[targetX][targetY] == 0) {//不吃子时
                    if (startX == targetX) {//横线
                        if (startY < targetY) {//想右走
                            for (i = startY + 1; i < targetY; i++) {
                                if (qizi[startX][i] != 0) {
                                    return false;//返回false
                                }
                            }
                        } else {//向走走
                            for (i = targetY + 1; i < startY; i++) {
                                if (qizi[startX][i] != 0) {
                                    return false;//返回false
                                }
                            }
                        }
                    } else {//竖线
                        if (startX < targetX) {//向下走
                            for (j = startX + 1; j < targetX; j++) {
                                if (qizi[j][startY] != 0) {
                                    return false;//返回false
                                }
                            }
                        } else {//向上走
                            for (j = targetX + 1; j < startX; j++) {
                                if (qizi[j][startY] != 0) {
                                    return false;//返回false
                                }
                            }
                        }
                    }
                } else {//吃子时
                    int count = 0;
                    if (startX == targetX) {//走的是横线
                        if (startY < targetY) {//向右走
                            for (i = startY + 1; i < targetY; i++) {
                                if (qizi[startX][i] != 0) {
                                    count++;
                                }
                            }
                            if (count != 1) {
                                return false;//返回false
                            }
                        } else {//向左走
                            for (i = targetY + 1; i < startY; i++) {
                                if (qizi[startX][i] != 0) {
                                    count++;
                                }
                            }
                            if (count != 1) {
                                return false;//返回false
                            }
                        }
                    } else {//走的是竖线
                        if (startX < targetX) {//向下走
                            for (j = startX + 1; j < targetX; j++) {
                                if (qizi[j][startY] != 0) {
                                    count++;//返回false
                                }
                            }
                            if (count != 1) {
                                return false;//返回false
                            }
                        } else {//向上走
                            for (j = targetX + 1; j < startX; j++) {
                                if (qizi[j][startY] != 0) {
                                    count++;//返回false
                                }
                            }
                            if (count != 1) {
                                return false;//返回false
                            }
                        }
                    }
                }
                break;
            case 12://红仕
                if (targetX < 7 || targetY > 5 || targetY < 3) {//出了九宫格
                    return false;
                }
                if (Math.abs(startX - targetX) != 1 || Math.abs(targetY - startY) != 1) {//走斜线
                    return false;
                }
                break;
            case 13://红相
                if (targetX < 5) {//不能过河
                    return false;//返回false
                }
                if (Math.abs(startY - targetY) != 2 || Math.abs(startX - targetX) != 2) {//相走“田”字
                    return false;//返回false
                }
                if (qizi[(startX + targetX) / 2][(startY + targetY) / 2] != 0) {
                    return false;//相眼处有棋子
                }
                break;
            case 14://红卒
                if (targetX > startX) {//不能回头
                    return false;
                }
                if (startX > 4 && startX == targetX) {
                    return false;//不让走
                }
                if (startX - targetX + Math.abs(targetY - startY) > 1) {//只能走一步，并且是直线
                    return false;//返回false不让走
                }
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean isSameSide(int moveChessID, int targetID) {//判断两个子是否为同一阵营
        if (targetID == 0) {// 当目标地位空地时
            return false;
        }
        if (moveChessID > 7 && targetID > 7) {//当都为红色棋子时
            return true;
        } else if (moveChessID <= 7 && targetID <= 7) {//都为黑色棋子时
            return true;
        } else {//其他情况
            return false;
        }
    }
}
