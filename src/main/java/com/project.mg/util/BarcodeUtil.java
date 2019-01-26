package com.cmb.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by hasee on 2017/7/11.
 */
public class BarcodeUtil {
    /**
     * 生成条形码
     *
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    // int width = 105, height = 50; 长度很容易报错:NotFoundException
    public void encodeBarCode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.CODE_128, codeWidth, height, null);

            MatrixToImageWriter.writeToStream(bitMatrix, "png",
                    new FileOutputStream(imgPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析条形码
     *
     * @param imgPath
                    * @return
     */
            public String decodeBarCode(String imgPath) {
                BufferedImage image = null;
                Result result = null;
                try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
/*            Map<DecodeHintType, Object> hints = new Hashtable<>();
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            result = new MultiFormatReader().decode(bitmap, hints);*/
            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String imgPath = "C:\\Users\\hasee\\Desktop\\barcode.png";
        // 益达无糖口香糖的条形码
        String contents = "aaaaa";
        int width = 105, height = 50;
        BarcodeUtil handler = new BarcodeUtil();
        handler.encodeBarCode(contents, width, height, imgPath);
        String barcode = handler.decodeBarCode(imgPath);
        System.out.println(barcode);
    }
}

