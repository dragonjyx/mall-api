package com.mall.params.status;

public enum PaymentStatus {
    DISABLE(0),
    ENABLE(1);
    public int value;
    PaymentStatus(int value) {
        this.value = value;
    }

    public int getPaymentStatus() {
        return this.value;
    }

    public static PaymentStatus fromPaymentStatus(int status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getPaymentStatus() == status) {
                return paymentStatus;
            }
        }
        return null;
    }

}
