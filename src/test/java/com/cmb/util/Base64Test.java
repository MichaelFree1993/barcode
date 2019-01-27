package com.cmb.util;

import com.project.util.Base64;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by hasee on 2017/8/16.
 */
public class Base64Test {
    @Test
    public void getImageBase64() throws Exception {
        String imgPath = "C:\\Users\\hasee\\Desktop\\1502895121589.png";
        File sourceImage = new File(imgPath);
        BufferedImage image = ImageIO.read(sourceImage);
        System.out.print(Base64.GetImageBase64(image));
    }

}