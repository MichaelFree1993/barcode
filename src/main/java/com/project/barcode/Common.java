package com.project.barcode;

import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hasee on 2017/8/16.
 */
public class Common {

    /**
     * 根据数组位置计算位于哪一通道
     *
     * @param index
     * @return
     */
    public static int whichRgb(int index, int rPlus, int gPlus, int bPlus) {
        if (index % 3 == 0) {
            return rPlus;
        } else if (index % 3 == 1) {
            return gPlus;
        } else {
            return bPlus;
        }
    }

    /**
     * 获取像素点的RGB
     *
     * @param image 图片
     * @param i     像素列坐标
     * @param j     像素行坐标
     * @return 三通道灰度值
     */
    public static int[] getRGB(BufferedImage image, int i, int j) {
        int pixel = image.getRGB(i, j);
        int[] rgb = new int[3];
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);
        return rgb;
    }

    /**
     * 根据位置获取灰度值
     *
     * @param image 图片
     * @param index 横坐标
     * @param j     纵坐标
     * @return
     */
    public static int getValueByIndex(BufferedImage image, int index, int j) {
        int[] rgb = getRGB(image, index / 3, j);
        return rgb[index % 3];
    }



}
