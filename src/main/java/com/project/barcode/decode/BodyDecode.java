package com.project.barcode.decode;

import com.project.util.Const;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hasee on 2017/8/16.
 */
@Repository
public class BodyDecode extends BodyDecodeBase {


    /**
     * 用第一种编码方式解码
     *
     * @param imgContents    线码正文序列
     * @param leftMargin     线码正文左边距
     * @param contentsLength 线码正文字符串长度
     * @return
     */
    public static String bodyDecode(List<Integer> imgContents, int leftMargin, int contentsLength) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < contentsLength; i++) {
            stringBuffer.append((char) (imgContents.get(leftMargin + i * 2) * 10 + imgContents.get(leftMargin + i * 2 + 1) + Const.MINUS));
        }
        return stringBuffer.toString();
    }
}
