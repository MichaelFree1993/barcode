package com.project.barcode.decode;

import com.project.util.Const;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/8/16.
 */
@Repository
public class BodyDecode2 extends BodyDecodeBase {
    /**
     * 用第二种编码方式解码
     *
     * @param imgContents    线码正文
     * @param ratio
     * @param contentsLength 线码正文字符串长度
     * @return
     */
    public static String bodyDecode(List<Integer> imgContents, int contentsLength, int ratio) {

        StringBuffer stringBuffer = new StringBuffer();
        List<Integer> charSet = new ArrayList<Integer>();
        for (int i = 0; i < imgContents.size(); i = i + ratio * 3) {
            charSet.add(imgContents.get(i));
            charSet.add(imgContents.get(i + 1));
            charSet.add(imgContents.get(i + 2));
        }
        for (int i = 0; i < contentsLength; i++) {
            stringBuffer.append((char) (charSet.get(i * 2) * 10 + charSet.get(i * 2 + 1) + Const.MINUS));
        }
        return stringBuffer.toString();
    }
}
