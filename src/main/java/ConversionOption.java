import enums.ConvertMode;
import enums.ConvertTarget;

public class ConversionOption {
    private static final String default_delimiter_start = "(";
    private static final String default_delimiter_end = ")";
    /**
     * to：转换的目标文字类型（平假名或片假名）。
     * mode：转换模式（普通、带空格、带送假名或带假名标注）。
     * delimiter_start 和 delimiter_end：用于分隔带送假名或假名标注的分隔符。默认:()
     */
    String to; // hiragana, katakana
    String mode; // normal, spaced, okurigana, furigana
    String delimiter_start;
    String delimiter_end;

    /**
     * 构造函数
     * @param to
     * @param mode
     * @param delimiter_start
     * @param delimiter_end
     */
    public ConversionOption(String to, String mode, String delimiter_start, String delimiter_end) {
        this.to = to;
        this.mode = mode;
        this.delimiter_start = delimiter_start;
        this.delimiter_end = delimiter_end;
    }

    /**
     * 构造函数
     * @param to
     * @param mode
     */
    public ConversionOption(String to, String mode) {
        this(to,mode,default_delimiter_start,default_delimiter_end);
    }

    /**
     * 构造函数
     * @param convertTarget
     * @param convertMode
     */
    public ConversionOption(ConvertTarget convertTarget, ConvertMode convertMode){
        this(convertTarget.getValue(),convertMode.getValue(),default_delimiter_start,default_delimiter_end);
    }

    /**
     * 获取转换的目标文字类型（平假名或片假名）
     * @return String
     */
    public String getTo() {
        return to;
    }

    /**
     * 获取转换模式（普通、带空格、带送假名或带假名标注）
     * @return String
     */
    public String getMode() {
        return mode;
    }
}
