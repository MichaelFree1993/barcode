package com.project.barcode.decode;

import com.project.barcode.Common;
import com.project.util.Const;
import org.springframework.stereotype.Repository;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/8/16.
 */
@Repository
public class HeaderDecode {

    /**
     * 获取线码背景颜色
     *
     * @param image  图片
     * @param indexJ 线码位置
     * @return
     */
    public static int[] getBackgroundColor(BufferedImage image, int indexJ) {
        return Common.getRGB(image, 0, indexJ);
    }

    /**
     * 获取编码方式
     *
     * @param list 线码头序列
     * @return
     */
    public static int getCodeType(List<Integer> list) {
        int codeType = list.get(0);
        return codeType;
    }

    /**
     * 获取解码方式
     *
     * @param list 线码头序列
     * @return
     */
    public static int getCheckType(List<Integer> list) {
        int checkType = list.get(1);
        return checkType;
    }

    /**
     * 获取线码长度
     *
     * @param list 线码头序列
     * @return
     */
    public static int getImgWidth(List<Integer> list) {
        int imgWidth4 = list.get(2);
        int imgWidth3 = list.get(2 + 1);
        int imgWidth2 = list.get(2 + 2);
        int imgWidth1 = list.get(2 + 3);
        int imgWidth = imgWidth4 * 1000 + imgWidth3 * 100 + imgWidth2 * 10 + imgWidth1;
        return imgWidth;
    }

    /**
     * 获取字符串长度
     *
     * @param list 线码头序列
     * @return
     */
    public static int getContentsLength(List<Integer> list) {
        int contentsLength4 = list.get(2 + 4);
        int contentsLength3 = list.get(2 + 4 + 1);
        int contentsLength2 = list.get(2 + 4 + 2);
        int contentsLength1 = list.get(2 + 4 + 3);
        int contentsLength = contentsLength4 * 1000 + contentsLength3 * 100 + contentsLength2 * 10 + contentsLength1;
        return contentsLength;
    }

    /**
     * 获取校验码
     *
     * @param list 线码头序列
     * @return
     */
    public static int getCheckNumber(List<Integer> list) {
        int checkNumber1 = list.get(2 + 4 + 4);
        int checkNumber2 = list.get(2 + 4 + 4 + 1);
        int checkNumber3 = list.get(2 + 4 + 4 + 2);
        int checkNumber = checkNumber1 * 100 + checkNumber2 * 10 + checkNumber3;
        return checkNumber;
    }

    /**
     * 找出线码位置
     *
     * @param image   图片
     * @param markStr 标志码
     * @return
     */
    public static int matchMarkStr(BufferedImage image, String markStr) {
        int height = image.getHeight();
        int markStrLength = markStr.length();
        int indexJ = -1;
        for (int j = 0; j < height; j++) {
            int[] rgb = getBackgroundColor(image, j);
            int rPlus = rgb[0] - 5;
            int gPlus = rgb[1] - 5;
            int bPlus = rgb[2] - 5;
            //匹配标志码
            boolean bool = true;
            for (int i = 0; i < markStrLength; i++) {
                int tens = ((int) markStr.charAt(i) - Const.MINUS) / 10;
                int units = ((int) markStr.charAt(i) - Const.MINUS) % 10;
                if (Common.getValueByIndex(image, 3 + i * 2, j) - Common.whichRgb(3 + i * 2, rPlus, gPlus, bPlus) != tens) {
                    bool = false;
                    break;
                }
                if (Common.getValueByIndex(image, 3 + i * 2 + 1, j) - Common.whichRgb(3 + i * 2 + 1, rPlus, gPlus, bPlus) != units) {
                    bool = false;
                    break;
                }
            }
            if (bool) {
                //是否到结束位
                if (Common.getValueByIndex(image, 3 + markStrLength * 2, j) - Common.whichRgb(3 + markStrLength * 2, rPlus, gPlus, bPlus) != 9) {
                    continue;
                }
                if (Common.getValueByIndex(image, 3 + markStrLength * 2 + 1, j) - Common.whichRgb(3 + markStrLength * 2 + 1, rPlus, gPlus, bPlus) != 9) {
                    continue;
                }
                indexJ = j;

                break;
            }
        }
        if (indexJ == -1) {
            throw new IllegalArgumentException(Const.MARK_STRING_IS_NOT_EXIST);
        }
        return indexJ;
    }

    /**
     * 解码线码头
     *
     * @param image         图片
     * @param indexJ        线码位置
     * @param markStrLength 标志码长度
     * @param rPlus         R偏移值
     * @param gPlus         G偏移值
     * @param bPlus         B偏移值
     * @return
     * @throws IOException
     */
    public static List headerDecode(BufferedImage image, int indexJ, int markStrLength, int rPlus, int gPlus, int bPlus) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException();
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < Const.HEADER_LENGTH; i++) {
            list.add(Common.getValueByIndex(image, 3 + (markStrLength + 1) * 2 + i, indexJ) - Common.whichRgb(3 + (markStrLength + 1) * 2 + i, rPlus, gPlus, bPlus));
        }
        return list;
    }

}
