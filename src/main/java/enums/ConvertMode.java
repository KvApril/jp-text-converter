package enums;

/**
 * 文件描述
 * 转换模式（普通、带空格、带送假名或带假名标注）
 **/
public enum ConvertMode {
    //标准模式
    NORMAL("normal"),
    //空格分组
    SPACED("spaced"),
    //送假名
    OKURIGANA("okurigana"),
    //注音假名
    FURIGANA("furigana");

    public final String value;

    ConvertMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ConvertMode fromString(String value) {
        for (ConvertMode elem : ConvertMode.values()) {
            if (elem.value.equalsIgnoreCase(value)) {
                return elem;
            }
        }
        throw new IllegalArgumentException("Unknown convert mode: " + value);
    }
}
