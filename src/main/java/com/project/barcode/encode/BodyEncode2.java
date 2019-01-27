package com.project.barcode.encode;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/8/16.
 */
@Repository
public class BodyEncode2 extends BodyEncodeBase {

    /**
     * 编码线码正文2
     *
     * @param contents 字符串
     * @throws IOException 捕获写文件异常
     */
    public static List bodyEncode(String contents, int ratio, int rightMargin) throws IOException {

        int[] matrix = parseContents(contents);
        List<Integer> imgContents = new ArrayList<Integer>();
        int n = matrix.length % 3 == 0 ? matrix.length / 3 : matrix.length / 3 + 1;
        for (int i = 0; i < 3 * (n - 1); i = i + 3) {
            for (int j = 0; j < ratio; j++) {
                imgContents.add(matrix[i]);
                imgContents.add(matrix[i + 1]);
                imgContents.add(matrix[i + 2]);
            }
        }
        for (int j = 0; j < ratio; j++) {
            if (matrix.length % 3 == 0) {
                imgContents.add(matrix[matrix.length - 3]);
                imgContents.add(matrix[matrix.length - 2]);
                imgContents.add(matrix[matrix.length - 1]);
            }
            if (matrix.length % 3 == 1) {
                imgContents.add(matrix[matrix.length - 1]);
                imgContents.add(5);
                imgContents.add(5);
                rightMargin = rightMargin - 2;
            }
            if (matrix.length % 3 == 2) {
                imgContents.add(matrix[matrix.length - 2]);
                imgContents.add(matrix[matrix.length - 1]);
                imgContents.add(5);
                rightMargin = rightMargin - 1;
            }
        }

        for (int i = 0; i < rightMargin; i++) {
            imgContents.add(5);
        }
        return imgContents;
    }
}
