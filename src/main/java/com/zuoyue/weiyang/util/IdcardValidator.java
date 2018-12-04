package com.zuoyue.weiyang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdcardValidator {

    protected static final String codeAndCity[][] = {
            {"11", "北京"}, {"12", "天津"}, {"13", "河北"}, {"14", "山西"}, {"15", "内蒙古"},
            {"21", "辽宁"}, {"22", "吉林"}, {"23", "黑龙江"},
            {"31", "上海"}, {"32", "江苏"}, {"33", "浙江"}, {"34", "安徽"}, {"35", "福建"}, {"36", "江西"}, {"37", "山东"},
            {"41", "河南"}, {"42", "湖北"}, {"43", "湖南"}, {"44", "广东"}, {"45", "广西"}, {"46", "海南"},
            {"50", "重庆"}, {"51", "四川"}, {"52", "贵州"}, {"53", "云南"}, {"54", "西藏"},
            {"61", "陕西"}, {"62", "甘肃"}, {"63", "青海"}, {"64", "宁夏"}, {"65", "新疆"},
            {"71", "台湾"},
            {"81", "香港"}, {"82", "澳门"},
            {"91", "国外"}
    };

    private static final String cityCode[] = {
            "11", "12", "13", "14", "15",
            "21", "22", "23", "31", "32", "33", "34", "35", "36", "37",
            "41", "42", "43", "44", "45", "46",
            "50", "51", "52", "53", "54",
            "61", "62", "63", "64", "65",
            "71",
            "81", "82",
            "91"
    };

    // 每位加权因子
    private static final int power[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 第18位校验码
    private static final String verifyCode[] = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    /**
     * 验证所有的身份证的合法性
     * @param idcard
     * @return
     */
    public static boolean isValidatedAllIdcard(String idcard) {
        if (idcard != null) {
            if (idcard.length() == 15) return isValidate15Idcard(idcard);
            if (idcard.length() == 18) return isValidate18Idcard(idcard);
        }
        return false;
    }

    /**
     * 判断18位身份证的合法性
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
     * 1.前1、2位数字表示：所在省份的代码；
     * 2.第3、4位数字表示：所在城市的代码；
     * 3.第5、6位数字表示：所在区县的代码；
     * 4.第7~14位数字表示：出生年、月、日；
     * 5.第15、16位数字表示：所在地的派出所的代码；
     * 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
     * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
     * 第十八位数字(校验码)的计算方法为：
     * 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 2.将这17位数字和系数相乘的结果相加。
     * 3.用加出来和除以11，看余数是多少？
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     *
     * @param idcard
     * @return
     */
    public static boolean isValidate18Idcard(String idcard) {
        // 非18位为假
        if (idcard == null || idcard.length() != 18) return false;

        // 获取前17位
        String idcard17 = idcard.substring(0, 17);
        if (!isDigital(idcard17)) return false;

        String birthday = idcard.substring(6, 14);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            if (new Date().before(sdf.parse(birthday))) return false;
        } catch (ParseException e) {
            return false;
        }

        // 获取第18位
        String idcard18Code = idcard.substring(17, 18);

        // 将和值与11取模得到余数进行校验判断
        String checkCode = verifyCode[getPowerSum(idcard17) % 11];
        // 将身份证的第18位与算出来的校码进行匹配，不想等就为假
        if (!idcard18Code.equalsIgnoreCase(checkCode)) return false;

        return true;
    }

    /**
     * 验证15身份证的合法性，该方法验证不准确，最好是将15转为18为后再判断，该类中已提供。
     * @param idcard
     * @return
     */
    public static boolean isValidate15Idcard(String idcard) {
        // 非15位为假
        if (idcard != null && idcard.length() != 15) return false;
        // 是否全都为数字
        if (!isDigital(idcard)) return false;

        String provinceid = idcard.substring(0, 2);
        String birthday = "19" + idcard.substring(6, 12);

//        // 判断是否为合法的省份
//        boolean flag = false;
//        for (String id : cityCode) {
//            if (id.equals(provinceid)) {
//                flag = true;
//                break;
//            }
//        }
//        if (!flag) return false;

        // 该身份证生出日期在当前日期之后时为假
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            if (new Date().before(sdf.parse(birthday))) return false;
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public boolean isValidateCityCode(String str) {
        for (String id : cityCode) {
            if (id.equals(str)) return true;
        }
        return false;
    }

    /**
     * 将15位的身份证转成18位身份证
     * @param idcard
     * @return
     */
    public String convertIdcardBy15bit(String idcard) {
        // 非15位身份证
        if (idcard.length() != 15) return null;
        if (!isDigital(idcard)) return null;

        // 获取出生年月日
        String birthday = "19" + idcard.substring(6, 12);
        Date birthdate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            birthdate = sdf.parse(birthday);
        } catch (ParseException e) {
            return null;
        }

        String idcard17 = idcard.substring(0, 6) + new SimpleDateFormat("yyyy").format(birthdate) + idcard.substring(8);
        int sum17 = getPowerSum(idcard17);
        return idcard17 + verifyCode[sum17 % 11];
    }

    /**
     * 数字验证
     * @param str
     * @return
     */
    private static boolean isDigital(String str) {
        return str.matches("^[0-9]*$");
    }

    /**
     * 将身份证的每位和对应的位加权因子相乘之后，再得到和值
     * @param char17
     * @return
     */
    private static int getPowerSum(String char17) {
        char[] temp = char17.toCharArray();
        if (power.length != temp.length) return 0;

        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += Integer.parseInt(String.valueOf(temp[i])) * power[i];
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        String idcard15 = "142431190901145"; // 15位  
        String idcard18 = "33072619901128272X"; // 18位
        IdcardValidator iv = new IdcardValidator();
        System.out.println(iv.isValidatedAllIdcard(idcard15));
        System.out.println(iv.isValidatedAllIdcard(idcard18));
    }
}
