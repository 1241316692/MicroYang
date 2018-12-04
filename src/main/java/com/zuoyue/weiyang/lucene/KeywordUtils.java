package com.zuoyue.weiyang.lucene;

/**
 * Created by cherry on 2018/2/21.
 */
public class KeywordUtils {

    public static String getStr(String str) {
        String s = str.replaceAll("[\\s\\'\\,\\*]+", "").replaceAll("[-]", "_");
        return s;
    }

    public static String getChinese(String str) {
        String chinese = str.replaceAll("[^\\u4e00-\\u9fa5]", "").replaceAll("(.)", "$1 ").trim();
        return chinese;
    }

    public static String getSingleChinese(String str) {
        String chinese = str.replaceAll("([\\u4e00-\\u9fa5])", " $1 ").replaceAll("\\s+", " ").trim();
        return chinese;
    }

    public static String getPinyin(String str) {
        String pinyin = ChangeToPinYinJP.changeToTonePinYin(str, "");
        return pinyin;
    }

    public static String getRePinyin(String str) {
        String pinyin = ChangeToPinYinJP.changeToTonePinYin(str, "");
        String rePinyin = new StringBuilder(pinyin).reverse().toString();
        return rePinyin;
    }

    public static String getShortPinyin(String str) {
        String shortPinyin = ChangeToPinYinJP.changeToGetShortPinYin(str);
        return shortPinyin;
    }

    public static String getKeyWords(String str) {
        String pinyin = ChangeToPinYinJP.changeToTonePinYin(str, "");
        while (pinyin.length() < 3) {
            pinyin += "_";
        }
        String rePinyin = new StringBuilder(pinyin).reverse().toString();
        String shortPinyin = ChangeToPinYinJP.changeToGetShortPinYin(str);
        while (shortPinyin.length() < 3) {
            shortPinyin += "_";
        }
        String keywords = str.replaceAll("[\\s\\'-a-zA-Z\\d]+", "").replaceAll("(.)", "$1__,");
        if (keywords.length() > 0) keywords = keywords.substring(0, keywords.length() - 1);
        return pinyin + "," + rePinyin + "," + shortPinyin + "," + keywords;
    }

}
