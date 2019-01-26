package com.cmb.barcode.encode;

import com.cmb.util.Const;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by hasee on 2017/8/16.
 */
@Repository
public class BodyEncodeBase {

    /**
     * RGB值
     *
     * @param r R灰度值
     * @param g G灰度值
     * @param b B灰度值
     * @return RGB值
     */
    public static List<Integer> addRgb(List<Integer> list, int r, int g, int b) {
        for (int i = 0; i < list.size() / 3; i++) {
            list.set(i * 3, list.get(i * 3) + r - 5);
            list.set(i * 3 + 1, list.get(i * 3 + 1) + g - 5);
            list.set(i * 3 + 2, list.get(i * 3 + 2) + b - 5);
        }
        return list;
    }

    /**
     * 将字符转化为十位数字和个位数字
     *
     * @param contents 字符串
     * @return
     */
    public static int[] parseContents(String contents) {
        int[] matrix = new int[contents.length() * 2];
        for (int i = 0; i < contents.length() * 2; i++) {
            matrix[i] = 0;
        }
        for (int i = 0; i < contents.length(); i++) {
            char c = contents.charAt(i);
            if (c < 32 || c > 126) {
                throw new IllegalArgumentException(String.format(Const.NO_IN_ENCODE_RAANGE, c));

            } else {
                matrix[i * 2] = ((int) c - Const.MINUS) / 10;
                matrix[i * 2 + 1] = ((int) c - Const.MINUS) % 10;
            }
        }
        return matrix;
    }
}
