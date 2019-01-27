package com.project.barcode.decode;

import com.project.util.CheckCode;
import com.project.barcode.Common;
import org.springframework.stereotype.Repository;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/8/16.
 */
@Repository
public class BodyDecodeBase {

    /**
     * 判断计算出的校验码与线码头的校验码是否一致
     *
     * @param contents 待计算校验码的字符串
     * @param checkType 检验方式
     * @param checkNumber 线码头校验码
     * @return
     */
    public static boolean checkCheckNumber(String contents, int checkType, int checkNumber) {
        int checkNumberTemp = 0;
        if (checkType == 1) {
            checkNumberTemp = CheckCode.getCheckNumber(contents);
        } else if (checkType == 2) {
            checkNumberTemp = CheckCode.getCRC8(contents);
        }
        return checkNumberTemp == checkNumber ? true : false;
    }

    /**
     * 解码出正文整形数组
     *
     * @param image    图片
     * @param indexJ   条码位置
     * @param start    线码正文开始位置
     * @param imgWidth 线码宽度
     * @param r        线码背景颜色R值
     * @param g        线码背景颜色G值
     * @param b        线码背景颜色B值
     * @return
     * @throws IOException
     */
    public static List bodyDecodeToArray(BufferedImage image, int indexJ, int start, int imgWidth, int r, int g, int b) throws IOException {
        List<Integer> imgContents = new ArrayList<Integer>();
        for (int i = 0; i < imgWidth * 3 - start; i++) {
            imgContents.add(Common.getValueByIndex(image, start + i, indexJ) - Common.whichRgb(start + i, r - 5, g - 5, b - 5));
        }
        return imgContents;
    }
}
