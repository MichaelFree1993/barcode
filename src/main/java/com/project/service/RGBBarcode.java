package com.project.service;

import com.project.barcode.decode.BodyDecode;
import com.project.barcode.decode.BodyDecode2;
import com.project.barcode.decode.BodyDecodeBase;
import com.project.barcode.decode.HeaderDecode;
import com.project.barcode.encode.BodyEncode;
import com.project.barcode.encode.BodyEncode2;
import com.project.barcode.encode.HeaderEncode;
import com.project.util.Const;
import com.project.util.StringUtil;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/8/15.
 */
@Service
public class RGBBarcode {

    public static List<Integer> encode(int codeType, int checkType, String contents, int imgWidth, String markStr, int r, int g, int b) throws IOException {
        contents= StringUtil.replaceBlank(contents);
        long startTime = System.nanoTime();   //获取开始时间
        List headerList = HeaderEncode.headerEncode(codeType, checkType, r, g, b, markStr, contents, imgWidth);
        long endTime = System.nanoTime(); //获取结束时间
        System.out.println("头编码时间：" + (endTime - startTime));
        List bodyList = null;
         startTime = System.nanoTime();   //获取开始时间
        if (codeType == 1) {
            int leftMargin = (imgWidth * 3 - headerList.size() - contents.length() * 2) / 2;
            int rightMargin = imgWidth * 3 - headerList.size() - contents.length() * 2 - leftMargin;
            bodyList = BodyEncode.bodyEncode(contents, leftMargin, rightMargin);
        }
        if (codeType == 2) {
            int ratio = (imgWidth * 3 - headerList.size()) / (contents.length() * 2);
            int n = (contents.length() * 2) % 3 == 0 ? (contents.length() * 2) / 3 : (contents.length() * 2) / 3 + 1;
            int rightMargin = (imgWidth * 3 - headerList.size() - ratio * n * 3);
            bodyList = BodyEncode2.bodyEncode(contents, ratio, rightMargin);
        }
         endTime = System.nanoTime(); //获取结束时间
        System.out.println("体编码时间：" + (endTime - startTime));
        List<Integer> list = new ArrayList<Integer>();
        list.addAll(headerList);

        if (bodyList != null) {
            list.addAll(bodyList);
        }
        return BodyEncode.addRgb(list, r, g, b);

    }

    public static String decode(BufferedImage image, String markStr) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException(Const.IMAGE_IS_NOT_EXIST);
        }
        long startTime = System.nanoTime(); //获取结束时间
        int indexJ = HeaderDecode.matchMarkStr(image, markStr);
        long endTime = System.nanoTime(); //获取结束时间
        System.out.println("扫描时间：" + (endTime - startTime));
         startTime = System.nanoTime(); //获取结束时间


        int rgb[] = HeaderDecode.getBackgroundColor(image, indexJ);
        int r = rgb[0];
        int g = rgb[1];
        int b = rgb[2];

        List list = HeaderDecode.headerDecode(image, indexJ, markStr.length(), r - 5, g - 5, b - 5);
        int codeType = HeaderDecode.getCodeType(list);
        int checkType = HeaderDecode.getCheckType(list);
        int imgWidth = HeaderDecode.getImgWidth(list);
        int contentLength = HeaderDecode.getContentsLength(list);
        int checkNumber = HeaderDecode.getCheckNumber(list);

        int start = 3 + (markStr.length() + 1) * 2 + Const.HEADER_LENGTH;

        List bodyList = BodyDecodeBase.bodyDecodeToArray(image, indexJ, start, imgWidth, r, g, b);

        String string = "";
        if (codeType == 1) {
            int leftMargin = (imgWidth * 3 - start - contentLength * 2) / 2;
            string = BodyDecode.bodyDecode(bodyList, leftMargin, contentLength);
        }
        if (codeType == 2) {
            int ratio = (imgWidth * 3 - start) / (contentLength * 2);
            string = BodyDecode2.bodyDecode(bodyList, contentLength, ratio);
        }
         endTime = System.nanoTime(); //获取结束时间
        System.out.println("解码时间：" + (endTime - startTime));
        boolean isCheckNumber = BodyDecodeBase.checkCheckNumber(string, checkType, checkNumber);
        if (isCheckNumber) {
            return string;
        } else {
            throw new IllegalArgumentException(Const.CHECK_FAIL);
        }
    }


}
