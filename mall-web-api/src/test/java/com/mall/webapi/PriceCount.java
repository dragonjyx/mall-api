package com.mall.webapi;

import com.java.utils.code.AES_CBC_PKCS7;
import com.java.utils.code.Base64Util;

public class PriceCount {


    public static void main(String[] args) {


        /*
        BigDecimal totalPrice = new BigDecimal(0);//计算订单总价
        for (int i = 0; i< 5 ; i++){
            System.out.println("i:"+i);
            BigDecimal goodsPrice = new BigDecimal(i);
            totalPrice = totalPrice.add(goodsPrice);
        }

        System.out.println(totalPrice);
        */


        /**

         String encryptedData = "t3lK/Rur7kfagly3XPTLNYeVXGwWA2/QV/sMCreVUFSW94xJACmk9DT0T5sM+P3CI5en2x2YeZJAfjUzRVmKY8CAPsn4xWDqUNqOMaFOuN3rRD+SAk1K86o83tL5J3xyZfdrqoHoPUOpCErPuowzngXKOU9vgQFtV4IuAyBS5FfbtyHigvEoYsTWujgEtVLhx5HSs1Wl/sD+/ajrRJhSXMA0oz2LeQ1LBH5XgTov/Y5N1M5/vAwuOIs1Q8wDFD9yzRpdVZszDR79y0Hlt46uB+9tZupzNcEitM5r+xbvlrQcKr7RdGutw8K8WR1yOUXk8hTllY39h3/8+C7ZmzWrO9rzOml9a+u56WnxNfA4/RoncpEnQshjbPX5GeWqrXfrKQ5/X0XqyLcyZbvyAndKStajwS1+mzB4NUUO26NJ70M1biXx/m7Vg/LG8eE7loESJGDgJHhrTphhEP9X9RnIpAQKP1EiEObt5i6/QAc0JZECVWVP0xd0PYf1MLsLD1M3xdmQy5bDysq1Zgkxs2uz9A==";
         String iv = "c5V2wtqXaXI7M1BpVm3jkw==";
         String sessionKey = "7D6IzFxdthWHa5Il6wcg6Q==";

         */


        String encryptedData = "sW2Wm1naQNXCvgH4fKdu32QCKhZ7suDqjmI7zD2piToOMe6Sq3bC9k2N9rdkrKQZuxncO1f3slLK4AFsgoiBtYyZz/LhO4SfO5xnNyjLRwmk682mC3nOQhsS0GXGp3I/K6vElZgRXvW96yoKe27IsBGyEV2sFjK+dSKj5OHYXIiefk3w2nXYubR5tijpr41DQntl616y8DAt+5BhkIbDu+U7wJ+WKtA310JwJN1twmS1TB1I4G4eMlHKvh5qu5JDIOzxM5ViQXEEDu/afqABzXvRsSNePUtYGVoU+4RB5yf32PwfLjnkiUUsb9U2CgGtuNClzMHMA+1zA2HMK8El/ovjLs5eL5bgtsV2NhulmD3dWs5uvIo2FO3JyuEAyYFw/+pRXdw2ufk86EJJS3DKTojltKOlcu0D9Cqm3GXvGvTPbJtjSRJ4jIYcdhJWraWPMl/ni6+nU9BWla+XU+F0jcxxf4a7VRHpUBpIqAjyGj4=";
        String iv = "HYOHet0OsnOTS7LNrttnaA==";
        String sessionKey = "8qELTx9hKL6KxUJRm78seA==";

        byte[] content = Base64Util.decode(encryptedData);
        byte[] aesKey  = Base64Util.decode(sessionKey);
        byte[] ivByte  = Base64Util.decode(iv);


        byte[] descryptBytes = AES_CBC_PKCS7.decrypt(content,aesKey,ivByte);


        System.out.println("content: "+new String(descryptBytes));





    }

}
