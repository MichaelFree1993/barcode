package com.project.barcode.encode;

import com.project.util.CheckCode;
import com.project.util.Const;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/8/16.
 */
@Repository
public class HeaderEncode {


    /**
     * 编码线码头
     *
     * @throws IOException 捕获写文件异常
     */
    public static List headerEncode(int codeType, int checkType, int r, int g, int b, String markStr, String contents, int imgWidth) throws IOException {
        if (markStr.length() > Const.MAX_MARK_STRING_LEN) {
            throw new IllegalArgumentException(Const.ILLEGAL_MARK_STRING);
        }
        if (!(5 <= r && r < 251 && 5 <= g && g <= 251 && 5 <= b && b <= 251)) {
            throw new IllegalArgumentException(Const.ILLEGAL_BG_COLOR);
        }
        if (imgWidth * 3 < 3 + (markStr.length() + 1) * 2 + Const.HEADER_LENGTH + contents.length() * 2) {
            throw new IllegalArgumentException(Const.ILLEGAL_STRING);
        }
        int contentsLength = contents.length();
        int markStrLength = markStr.length();
        //线码头
        List<Integer> imgContents = new ArrayList<Integer>();
        //背景颜色
        for (int i = 0; i < 3; i++) {
            imgContents.add(5);
        }
        //标志码
        for (int i = 0; i < markStrLength; i++) {
            int tens = ((int) markStr.charAt(i) - Const.MINUS) / 10;
            int units = ((int) markStr.charAt(i) - Const.MINUS) % 10;
            imgContents.add(tens);
            imgContents.add(units);
        }
        //标识码结束标志
        imgContents.add(9);
        imgContents.add(9);
        //编码方式和校验方式
        imgContents.add(codeType);
        imgContents.add(checkType);
        //图片宽度
        int imgWidth4 = imgWidth / 1000;
        int imgWidth3 = (imgWidth % 1000) / 100;
        int imgWidth2 = (imgWidth % 100) / 10;
        int imgWidth1 = imgWidth % 10;
        imgContents.add(imgWidth4);
        imgContents.add(imgWidth3);
        imgContents.add(imgWidth2);
        imgContents.add(imgWidth1);
        //字符串长度
        int contentsLength4 = contentsLength / 1000;
        int contentsLength3 = (contentsLength % 1000) / 100;
        int contentsLength2 = (contentsLength % 100) / 10;
        int contentsLength1 = contentsLength % 10;
        imgContents.add(contentsLength4);
        imgContents.add(contentsLength3);
        imgContents.add(contentsLength2);
        imgContents.add(contentsLength1);
        //检验码
        int checkNumber = 0;
        if (checkType == 1) {
            checkNumber = CheckCode.getCheckNumber(contents);
        } else if (checkType == 2) {
            checkNumber = CheckCode.getCRC8(contents);
        }
        int checkNumber1 = checkNumber / 100;
        int checkNumber2 = (checkNumber / 10) % 10;
        int checkNumber3 = checkNumber % 10;
        imgContents.add(checkNumber1);
        imgContents.add(checkNumber2);
        imgContents.add(checkNumber3);
        return imgContents;
    }
}
