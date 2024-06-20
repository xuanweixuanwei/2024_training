package com.dejavu.utopia.bean;

public class AmountDetail {
    private int yuan; // 元
    private int jiao; // 角
    private int fen; // 分

    public AmountDetail(int yuan, int jiao, int fen) {
        this.yuan = yuan;
        this.jiao = jiao;
        this.fen = fen;
    }

    // Getters and Setters
    public int getYuan() {
        return yuan;
    }

    public void setYuan(int yuan) {
        this.yuan = yuan;
    }

    public int getJiao() {
        return jiao;
    }

    public void setJiao(int jiao) {
        this.jiao = jiao;
    }

    public int getFen() {
        return fen;
    }

    public void setFen(int fen) {
        this.fen = fen;
    }

    /**
     *  计算总金额
     * */
    public double getTotalAmount() {
        return yuan + jiao / 10.0 + fen / 100.0;
    }

}