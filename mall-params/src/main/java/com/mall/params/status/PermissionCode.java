package com.mall.params.status;

public enum PermissionCode {
    ALL(1),
    TEXU_MERCHANT(2),
    OEM_MERCHANT(3),
    O2O_MERCHANT(4);

    public int value;
    PermissionCode(int value) {
        this.value = value;
    }
}
