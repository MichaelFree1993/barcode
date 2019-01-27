package com.project.controller;

import com.alibaba.fastjson.JSON;
import com.project.barcode.Output;
import com.project.service.RGBBarcode;
import com.project.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2017/6/26.
 */
@Controller
@RequestMapping("/")
public class BarcodeController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String encode() throws IOException {
        return "encode";
    }

    @RequestMapping(value = {"/encode"}, produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String encode(@RequestParam(value = "contents", required = true) String contents,
                         @RequestParam(value = "length", required = true) Integer length,
                         @RequestParam(value = "markStr", required = true) String markStr,
                         @RequestParam(value = "r", required = true) Integer r,
                         @RequestParam(value = "g", required = true) Integer g,
                         @RequestParam(value = "b", required = true) Integer b,
                         @RequestParam(value = "codeType", required = true) Integer codeType,
                         @RequestParam(value = "checkType", required = true) Integer checkType,
                         @RequestParam(value = "imgType", required = true) Integer imgType,
                         HttpServletRequest request) throws IOException {
        String parentPath = request.getSession().getServletContext().getRealPath("/");
        String childPath = "/upload/" + System.currentTimeMillis() + ".png";
        String imgPath = parentPath + childPath;
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<Integer> list = RGBBarcode.encode(codeType, checkType, contents, length, markStr, r, g, b);
            if (imgType == 1) {
                Output.writeToFile(imgPath, list);
                map.put("imgPath", childPath);

            } else {
                String base64 = Output.imageToBase64(list);
                map.put("imgPath", base64);
            }
            map.put("success", "0");
            map.put("msg", Const.ENCODE_SUCCESS);
        } catch (IllegalArgumentException e) {
            map.put("success", "1");
            map.put("msg", e.getMessage());
        }

        return JSON.toJSONString(map);
    }

    @RequestMapping(value = {"/decode"}, method = RequestMethod.GET)
    public String decode() throws IOException {
        return "decode";

    }

    @RequestMapping(value = {"/decode"}, produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String decode(
            @RequestParam(value = "file", required = true) MultipartFile file,
            @RequestParam(value = "markStr", required = true) String markStr) throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        try {
            String string = RGBBarcode.decode(ImageIO.read(file.getInputStream()), markStr);
            map.put("success", "0");
            map.put("msg", Const.DECODE_SUCCESS);
            map.put("str", string);
        } catch (IllegalArgumentException e) {
            map.put("success", "1");
            map.put("msg", e.getMessage());
        }
        return JSON.toJSONString(map);
    }
}
