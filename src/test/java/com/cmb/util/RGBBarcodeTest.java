package com.cmb.util;

import com.cmb.barcode.Output;
import com.cmb.service.RGBBarcode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Created by hasee on 2017/7/20.
 */
public class RGBBarcodeTest {


    @Test
    public void encode() throws Exception {
        String markStr = "cmb";
        int max = 126;
        int min = 32;
        Random random = new Random();
        for (int i = 0; i < 581; i++) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int j = 1; j <= i; j++) {
                int c = random.nextInt(max) % (max - min + 1) + min;
                stringBuffer.append((char) c);
            }
            String imgPath = "C:\\Users\\hasee\\Desktop\\encode\\" + i + ".png";
            System.out.println("编码字符串：" + stringBuffer.toString());
            long startTime = System.nanoTime();   //获取开始时间
            List<Integer> list = RGBBarcode.encode(2, 1, stringBuffer.toString(), 400, markStr, 220, 20, 20);
//            Output.imageToBase64(list);
//            Output.writeToFile(imgPath, list);

            long endTime = System.nanoTime(); //获取结束时间
            System.out.println("长度：" + i + " 编码时间：" + (endTime - startTime));
             startTime = System.nanoTime();   //获取开始时间
//            Output.imageToBase64(list);
            Output.writeToFile(imgPath, list);
             endTime = System.nanoTime(); //获取结束时间
            System.out.println(" 输出线码时间：" + (endTime - startTime));

            System.out.println();
        }
    }

    @Test
    public void decode() throws Exception {
        //        String imgPath = "C:\\Users\\hasee\\Desktop\\ccc.png";
        String markStr = "cmb";
        int max = 126;
        int min = 32;
        Random random = new Random();
        for (int i = 1; i < 581; i++) {
//            String imgPath = "C:\\Users\\hasee\\Desktop\\encode\\" + i + ".png";
            String imgPath = "C:\\Users\\hasee\\Desktop\\222.png";

//            long startTime = System.currentTimeMillis();   //获取开始时间
            long startTime = System.nanoTime(); //获取结束时间
            BufferedImage image = ImageIO.read(new File(imgPath));
            long endTime = System.nanoTime(); //获取结束时间
            System.out.println("图片读取时间：" + (endTime - startTime));
             startTime = System.nanoTime(); //获取结束时间
            String string = RGBBarcode.decode(image, markStr);
//            long endTime = System.currentTimeMillis(); //获取结束时间
             endTime = System.nanoTime(); //获取结束时间
            System.out.println("长度：" + i + " 解码时间：" + (endTime - startTime));
            System.out.println("解码字符串：" + string);

            System.out.println();
        }

    }
}