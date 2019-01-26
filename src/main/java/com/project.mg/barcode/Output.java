package com.cmb.barcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.cmb.util.Base64;

/**
 * Created by hasee on 2017/8/16.
 */
public class Output {
    /**
     * RGB值
     *
     * @param r R灰度值
     * @param g G灰度值
     * @param b B灰度值
     * @return RGB值
     */
    public static int toRgb(int r, int g, int b) {
        return (r << 16) + (g << 8) + b;
    }

    public static void writeToFile(String imgPath, List<Integer> list) throws IOException {
        //写入图片缓存
        OutputStream output = new FileOutputStream(imgPath);
        BufferedImage bufferedImage = listToBufferedImage(list);
        ImageIO.write(bufferedImage, "png", output);
    }

    public static String imageToBase64(List<Integer> list) throws IOException {
        //写入图片缓存
        BufferedImage bufferedImage = listToBufferedImage(list);
        String base64Header = "data:image/png;base64,";
        return base64Header + Base64.GetImageBase64(bufferedImage);
    }

    public static BufferedImage listToBufferedImage(List<Integer> list) throws IOException {
        int height = 1;
        int imgWidth = list.size() / 3;
        BufferedImage bufferedImage = new BufferedImage(list.size() / 3, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < imgWidth; i++) {
            bufferedImage.setRGB(i, 0, toRgb(list.get(i * 3), list.get(i * 3 + 1), list.get(i * 3 + 2)));
        }
        return bufferedImage;
    }
}
