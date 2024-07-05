package util;

/**
 * 文件描述
 * 实用工具
 **/
public class ConvertUtil {

    // 平假名和片假名之间的偏移量
    private static final int KATAKANA_HIRAGANA_SHIFT = '\u3041' - '\u30a1';
    private static final int HIRAGANA_KATAKANA_SHIFT = '\u30a1' - '\u3041';

    /**
     * 检查给定字符是否为平假名
     * Check if given char is a hiragana
     * @param ch Given char
     * @return boolean if given char is a hiragana
     */
    public static boolean isHiragana(char ch) {
        return ch >= '\u3040' && ch <= '\u309f';
    }

    /**
     * 检查给定字符是否为片假名
     * Check if given char is a katakana
     * @param ch Given char
     * @return boolean if given char is a katakana
     */
    public static boolean isKatakana (char ch) {
        return ch >= '\u30a0' && ch <= '\u30ff';
    }

    /**
     * 检查给定字符是否为假名（包括平假名和片假名）
     * Check if given char is a kana
     * @param ch Given char
     * @return boolean if given char is a kana
     */
    public static boolean isKana(char ch) {
        return isHiragana(ch) || isKatakana(ch);
    }

    /**
     * 检查给定字符是否为汉字
     * Check if given char is a kanji
     * @param ch Given char
     * @return boolean if given char is a kanji
     */
    public static boolean isKanji(char ch) {
        return (ch >= '\u4e00' && ch <= '\u9fcf')
                || (ch >= '\uf900' && ch <= '\ufaff')
                || (ch >= '\u3400' && ch <= '\u4dbf');
    }

    /**
     * 检查给定字符是否为日语字符（假名或汉字）
     * Check if given char is a Japanese
     * @param ch Given char
     * @return boolean if given char is a Japanese
     */
    public static boolean isJapanese(char ch) {
       return isKana(ch) || isKanji(ch);
    }

    /**
     * 检查给定字符串中是否包含平假名
     * Check if given string has hiragana
     * @param str Given string
     * @return boolean if given string has hiragana
     */
    public static boolean hasHiragana(String str) {
        for (char c : str.toCharArray()) {
            if (isHiragana(c)) return true;
        }
        return false;
    }

    /**
     * 检查给定字符串中是否包含片假名
     * Check if given string has katakana
     * @param str Given string
     * @return boolean if given string has katakana
     */
     public static boolean hasKatakana(String str) {
         for (char c : str.toCharArray()) {
             if (isKatakana(c)) return true;
         }
            return false;
     }

    /**
     * 检查给定字符串中是否包含假名
     * Check if given string has kana
     * @param str Given string
     * @return boolean if given string has kana
     */
    public static boolean hasKana(String str) {
        for (char c : str.toCharArray()) {
            if (isKana(c)) return true;
        }
        return false;
    }

    /**
     * 检查给定字符串中是否包含汉字
     * Check if given string has kanji
     * @param str Given string
     * @return boolean if given string has kanji
     */
    public static boolean hasKanji (String str) {
        for (char c : str.toCharArray()) {
            if (isKanji(c)) return true;
        }
            return false;
        }

    /**
     * 检查给定字符串中是否包含日语字符
     * Check if given string has Japanese
     * @param str Given string
     * @return boolean if given string has Japanese
     */
    public static boolean hasJapanese(String str) {
        for (char c : str.toCharArray()) {
            if (isJapanese(c)) return true;
        }
        return false;
    }

    /**
     * 将给定字符串中的片假名转换为平假名
     * Convert kana to hiragana
     * @param str Given string
     * @return Hiragana string
     */
    public static String toRawHiragana(String str) {
        StringBuilder result = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch > '\u30a0' && ch < '\u30f7') {
                result.append((char) (ch + KATAKANA_HIRAGANA_SHIFT));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
    /**
     * 将给定字符串中的平假名转换为片假名
     * Convert kana to katakana
     * @param str Given string
     * @return Katakana string
     */
    public static String toRawKatakana(String str) {
        StringBuilder result = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch > '\u3040' && ch < '\u3097') {
                result.append((char) (ch + HIRAGANA_KATAKANA_SHIFT));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }


    /**
     * 获取给定字符串的类型
     * Get the type of given string
     * @param str str Given string
     * @return int. 0 for pure kanji, 1 for kanji-kana-mixed, 2 for pure kana, 3 for others
     */
    public static int getStrType(String str) {
        boolean hasKJ = false;
        boolean hasHK = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isKanji(c)) {
                hasKJ = true;
            } else if (isHiragana(c) || isKatakana(c)) {
                hasHK = true;
            }
        }

        if (hasKJ && hasHK) return 1;
        if (hasKJ) return 0;
        if (hasHK) return 2;
        return 3;
    }


    /**
     * 将给定字符串中的假名转换为平假名
     * Convert kana to hiragana
     * @param str str Given string
     * @return Hiragana string
     */
    public static String kanaToHiragna(String str){
        return toRawHiragana(str);
    }

    /**
     * 将给定字符串中的假名转换为片假名
     * Convert kana to katakana
     * @param str str Given string
     * @return Katakana string
     */
    public static String kanaToKatakana(String str){
        return toRawKatakana(str);
    }

}
