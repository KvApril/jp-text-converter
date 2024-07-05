package enums;

/**
 * 文件描述
 * 转换的目标文字类型（平假名或片假名）
 **/
public enum ConvertTarget {
    //平假名
    HIRAGANA("hiragana"),
    //片假名
    KATAKANA("katakana");

    public final String value;

    ConvertTarget(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ConvertTarget fromString(String value) {
        for (ConvertTarget elem : ConvertTarget.values()) {
            if (elem.value.equalsIgnoreCase(value)) {
                return elem;
            }
        }
        throw new IllegalArgumentException("Unknown convert target: " + value);
    }
}
