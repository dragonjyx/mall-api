package com.mall.params.status;

public enum GoodsStatus {

    TENTATIVE("TENTATIVE"),//暂定
    PUTAWAY("PUTAWAY"),//上架
    SOLDOUT("SOLDOUT");//下架
    public String value;
    GoodsStatus(String value) {
        this.value = value;
    }

    public String getGoodsStatus() {
        return this.value;
    }

    public static GoodsStatus fromGoodsStatus(String status) {
        for (GoodsStatus goodsStatus : GoodsStatus.values()) {
            if (goodsStatus.getGoodsStatus().equals(status)) {
                return goodsStatus;
            }
        }
        return null;
    }
}
