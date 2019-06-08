package com.mall.params.status;

public class MallTypeStatus {

    /* 启用 */
    public final static String START_USING ="1";

    /* 不启用 */
    public final static String NO_START_USING ="0";

    public static String getStart(String status) {
        String result = null;
        switch (status) {
            case START_USING:
                result = START_USING;
                break;
            case NO_START_USING:
                result = NO_START_USING;
                break;
        }
        return result;
    }

}
