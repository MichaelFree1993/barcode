package com.cmb.barcode.encode;

import com.cmb.barcode.encode.BodyEncodeBase;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2017/8/16.
 */
@Repository
public class BodyEncode extends BodyEncodeBase {

    /**
     * 编码线码正文
     *
     * @param contents    字符串
     * @param leftMargin  左边距
     * @param rightMargin 右边距
     * @return
     * @throws IOException
     */
    public static List bodyEncode(String contents, int leftMargin, int rightMargin) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        int[] matrix = parseContents(contents);
        List<Integer> imgContents = new ArrayList<Integer>();
        //左背景填充
        for (int i = 0; i < leftMargin; i++) {
            imgContents.add(5);
        }
        //正文
        if (matrix != null) {
            for (int i = 0; i < matrix.length; i++) {
                imgContents.add(matrix[i]);
            }
        }
        //右背景填充
        for (int i = 0; i < rightMargin; i++) {
            imgContents.add(5);
        }
        return imgContents;
    }

}
