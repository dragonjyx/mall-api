package com.mall.params.status;

public enum OrderStatus {

    //状态，0：待支付，1：已支付(待配送)，2：已支付(配送中)，3：已支付（配送完成），4：订单完成， 5：已取消 6：已删除

    //待支付
    NO_PAY(0),
    //已支付(待配送)
    PAYED_NO_DELIVER(1),
    //已支付(配送中)
    PAYED_DELIVING(2),
    //已支付（配送完成）
    PAYED_FINISH_DELIVER(3),
    //订单完成
    ORDER_FINISH(4),
    //已取消
    CANCEL(5),
    //已删除
    DELETE(6);
    public int value;
    OrderStatus(int value) {
        this.value = value;
    }

}
