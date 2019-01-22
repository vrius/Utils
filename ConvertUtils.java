package com.discovery.animationdemo.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;


/**
 * 十六进制字符串处理相关工具类
 */
public class ConvertUtils {

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * byteArr转hexString
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes 字节数组
     * @return 16进制大写字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * byte转十六进制字符
     */
    public static String byte2HexString(byte bt) {
        int len = 1;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bt >>> 4 & 0x0f];
            ret[j++] = hexDigits[bt & 0x0f];
        }
        return new String(ret);
    }
    
    /**
     * hexString转byteArr
     * <p>例如：</p>
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }
    
    /**
     * hexString转byteArr
     * <p>例如：</p>
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexStrByte 十六进制字符串
     * @return 字节数组
     */
    public static byte hexString2Byte(String hexStrByte) {
    	int len = 2;
    	if (len % 2 != 0) {
    		hexStrByte = "0" + hexStrByte;
    		len = len + 1;
    	}
    	char[] hexBytes = hexStrByte.toUpperCase().toCharArray();
    	byte ret;
    	ret = (byte) (hex2Dec(hexBytes[0]) << 4 | hex2Dec(hexBytes[1]));
    	return ret;
    }


    /**
     * 亦或值生成
     * @param datas
     * @return : hexString to bytes
     */
    public static byte getXor(byte[] datas) {
        byte temp = 0;
        for (int i = 0; i < datas.length; i++) {
            temp ^= datas[i];
        }
        return temp;
    }

    /**
     * 亦或校验
     * @param datas
     * @param offset
     * @param byteCount
     * @return
     */
    public static byte getXor(byte[] datas,int offset,int byteCount) {
        byte temp = 0;
        for (int i = offset; i <= byteCount; i++) {
            temp ^=datas[i];
        }

        return temp;
    }

    /**
     * 亦或值生成（hexString）
     * @param datas:hexString to bytes
     * @return :hexString
     */
    public static String getXor2(byte[] datas) {
        byte temp = 0;
        for (int i = 0; i < datas.length; i++) {
            temp ^= datas[i];
        }
        return byte2HexString(temp);
    }
    
    
    
    /**把byts4数组变成int数据
     * @param bytes4 四个字节的bytes
     * @return
     */
    public static int hexByte2Int(byte[] bytes4){
    	int ret =0;
    	if(isEmpty(bytes4)||(bytes4.length!=4))
    		return -1;
    	ret = ((bytes4[0]&0x00000000ff)<<24)
    		 +((bytes4[1]&0x00000000ff)<<16)
    		 +((bytes4[2]&0x00000000ff)<<8)
    		 +((bytes4[3]&0x00000000ff));
    	return ret;
    }

    public static int hexByte22Int(byte[] bytes2){
        int ret =0;
        if(isEmpty(bytes2)||(bytes2.length!=2))
            return -1;
        ret = ((bytes2[0]&0x00000000ff)<<8)
                +((bytes2[1]&0x00000000ff));
        return ret;
    }

    public static int hexByte21Int(byte[] bytes1){
        int ret =0;
        if(isEmpty(bytes1)||(bytes1.length!=1))
            return -1;
        ret = ((bytes1[0]&0x00000000ff));
        return ret;
    }

    /**
     * 把数组中的byts中的pos~pos+3变成int数据
     * @param bytes  
     * @param pos    
     * @return
     */
    public static int hexByte2Int(byte[] bytes ,int pos){
    	int ret =0;
    	if(isEmpty(bytes)||(bytes.length - pos >= 4))
    		return -1;
    	ret = ((bytes[pos]&0x00000000ff)<<24)
    		 +((bytes[pos+1]&0x00000000ff)<<16)
    		 +((bytes[pos+2]&0x00000000ff)<<8)
    		 +((bytes[pos+3]&0x00000000ff));
    	return ret;
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    @SuppressLint("NewApi")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * 把int数据转为四个字节的byte[]
     * @param srcData
     * @return
     */
   public static byte[] int2hexByte(int srcData){
	   byte[] retByte = new byte[4];
	   retByte[0] = (byte)((srcData&0xff000000)>>24);
	   retByte[1] = (byte)((srcData&0x00ff0000)>>16);
	   retByte[2] = (byte)((srcData&0x0000ff00)>>8);
	   retByte[3] = (byte)((srcData&0x000000ff));
	   return retByte;
   }
    
  /**
   * 
   * @param srcData int
   * @param byteLen 0-3 之间的数
   * @return
   */
   public static byte[] int2hexByte(int srcData ,int byteLen){
	   byte[] iByte = new byte[4];
	   iByte[0] = (byte)((srcData&0xff000000)>>24);
	   iByte[1] = (byte)((srcData&0x00ff0000)>>16);
	   iByte[2] = (byte)((srcData&0x0000ff00)>>8);
	   iByte[3] = (byte)((srcData&0x000000ff));
	   
	   byte[] retByte = new byte[byteLen];
	   System.arraycopy(iByte, 4-byteLen, retByte, 0, byteLen);
	   
	   return retByte;
   }
    
    
    /**
     * hexChar转int
     *
     * @param hexChar hex单个字节
     * @return 0..15
     */
    private static int hex2Dec(char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else if (hexChar >= 'a' && hexChar <= 'f') {
        	return hexChar - 'a' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * charArr转byteArr
     *
     * @param chars 字符数组
     * @return 字节数组
     */
    public static byte[] chars2Bytes(char[] chars) {
        if (chars == null || chars.length <= 0) return null;
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * 
     * @param bArrarySrc  源数组
     * @param bArraryLen  源数组长度
     * @param pos		    从源数据中截取的位置 
     * @param len		   从源数据中截取的长度
     * @return
     */
    public static byte[] byteArraryIntercept(byte[] bArrarySrc,int bArraryLen,int pos,int len){
    	byte[] bRet =new byte[len];
    	if(bArraryLen-pos<len)
    		return null;
    	System.arraycopy(bArrarySrc, pos, bRet, 0, len);
    	return bRet;
    }
    
    /**
     * 
     * @param bArrarySrc   源数组
     * @param bArraryLen   源数组长度
     * @param pos          从源数据中截取的位置 
     * @return             
     */
    public static byte byteArraryIntercept(byte[] bArrarySrc,int bArraryLen,int pos){
    	byte bRet;
    	if(bArraryLen<=pos)
    		return -1;
    	return bArrarySrc[pos];
    }  
    
    /**
     * byteArr转charArr
     *
     * @param bytes 字节数组
     * @return 字符数组
     */
    public static char[] bytes2Chars(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }


    /**
     * bytes转bits
     *
     * @param bytes 字节数组
     * @return bits
     */
    public static String bytes2Bits(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    /**
     * bits转bytes
     *
     * @param bits 二进制
     * @return bytes
     */
    public static byte[] bits2Bytes(String bits) {
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // 不是8的倍数前面补0
        if (lenMod != 0) {
            for (int i = lenMod; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }
}
