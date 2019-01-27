package com.project.util;

/**
 * Created by MaGao on 2017/8/2.
 */
public class CheckCode {

    /**
     * 计算校验位
     *
     * @param s 字符串
     * @return 校验位
     */

    //校验码编码方式1
    public static int getCheckNumber(String s) {
        int sum = 0;
        char a[] = s.toCharArray();
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum % 10;
    }

    //校验码编码方式2
    public static int getCRC8(String s){
        byte[] buf = new byte[s.length()];
        //复制需要校验的数据
        for(int i=0;i<buf.length; i++){
            buf[i]=(byte)s.charAt(i);
        }
        int crci =0;
        for(int j=0;j<buf.length;j++){
            crci ^= buf[j] & 0xFF;
            for(int i=0;i<8;i++){
                if((crci&1)!=0){
                    crci >>=1;
                    crci ^= 0xB8;
                } else{
                    crci >>=1;
                }
            }
        }
        return crci < 0 ? crci + 256 : crci;
    }

    //校验码编码方式3
    public static String MakeCRC(String s){
        byte[] buf = new byte[s.length()];
        //复制需要校验的数据
        for(int i=0;i<buf.length; i++){
            buf[i]=(byte)s.charAt(i);
        }
        int len=buf.length;
        int crc=0;
        for(int pos = 0; pos<len;pos++){
            if(buf[pos]<0){
                crc ^=(int)buf[pos] + 256;
            }else{
                crc ^=(int)buf[pos];
            }
            for(int i=0; i<8; i++){
                if((crc&0x01)!=0){
                    crc >>= 1;
                    crc ^=0xE0;
                }else {
                    crc >>= 1;
                }
            }
        }
        String c = Integer.toString(crc);
        return c;
    }
}
