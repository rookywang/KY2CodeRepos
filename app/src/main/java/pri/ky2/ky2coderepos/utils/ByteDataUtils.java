package pri.ky2.ky2coderepos.utils;

/**
 * 字节与整数、字符串的相互转换
 *
 * @author wangkaiyan
 * @date 2019/08/02
 */
public class ByteDataUtils {

    /**
     * int（0 ~ 255） 转成 byte
     *
     * @param intNum int 数字
     * @return byte 数字
     */
    public static byte int2Byte(int intNum) {
        return (byte) intNum;
    }

    /**
     * 无符号 byte 转成 int
     *
     * @param byteNum 无符号 byte
     * @return int 数字
     */
    public static int byte2Int(byte byteNum) {
        return ((int) byteNum) & 0xff;
    }

    /**
     * 将 IP 存入字节数组
     *
     * @param ip IP 地址
     * @return IP 的字节数组
     */
    public static byte[] getIpByteArray(String ip) {
        byte[] ipByteArray = new byte[4];
        try {
            String[] ips = ip.split("[.]");
            for (int i = 0; i < ips.length; i++) {
                ipByteArray[i] = int2Byte(Integer.parseInt(ips[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipByteArray;
    }

    /**
     * 将 int 转为 byte[]
     *
     * @param intNum       int 整数
     * @param byteArrayLen byte[] 的长度
     * @return byte[]
     */
    public static byte[] int2ByteArray(int intNum, int byteArrayLen) {
        byte[] byteArray = new byte[byteArrayLen];
        for (int i = 0; i < byteArrayLen; i++) {
            // 这里有一个存储顺序的问题
            byteArray[i] = (byte) ((intNum >> 8 * (byteArrayLen - 1 - i)) & 0xFF);
        }
        return byteArray;
    }


    /**
     * 2 字节 byte[] 转成无符号 short
     */
    public static int byteArray2UnsignedShort(byte[] bytes) {
        return byteArray2UnsignedShort(bytes, 0);
    }

    /**
     * 2 字节 byte[] 转成无符号 short
     *
     * @param bytes 字节数组
     * @param off   索引偏移
     * @return 整数
     */
    public static int byteArray2UnsignedShort(byte[] bytes, int off) {
        int high = bytes[off];
        int low = bytes[off + 1];
        return (high << 8 & 0xFF00) | (low & 0xFF);
    }


    /**
     * 4 字节 byte[] 转成 int
     */
    public static int byteArray2Int(byte[] bytes) {
        return byteArray2Int(bytes, 0);
    }

    /**
     * 4 字节 byte[] 转成 int
     *
     * @param bytes 字节数组
     * @param off   索引偏移
     * @return 整数
     */
    public static int byteArray2Int(byte[] bytes, int off) {
        int b0 = bytes[off] & 0xFF;
        int b1 = bytes[off + 1] & 0xFF;
        int b2 = bytes[off + 2] & 0xFF;
        int b3 = bytes[off + 3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }

    /**
     * 字符串转成字节数组
     *
     * @param str 字符串
     * @return 字节数组
     */
    public static byte[] string2ByteArray(String str) {
        return str.getBytes();
    }

    /**
     * 字节数组转成字符串
     *
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String byteArray2String(byte[] bytes) {
        return new String(bytes);
    }
}
