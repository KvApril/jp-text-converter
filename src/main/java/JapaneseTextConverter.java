import enums.ConvertMode;
import enums.ConvertTarget;
import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import patcher.PatchedToken;
import patcher.TokenPatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.ConvertUtil.*;

public class JapaneseTextConverter {

    private ConversionOption options;

    public JapaneseTextConverter(){}

    public JapaneseTextConverter(ConversionOption options) {
        this.options = options;
    }

    /**
     * 使用 Tokenizer 对输入文本进行分词，生成 Token 列表。
     * 使用 TokenPatcher.patchTokens 方法对 Token 列表进行修正，返回 PatchedToken 列表。
     * @param text
     * @return List
     */
    public List<PatchedToken> tokenize(String text) {
        Tokenizer tokenizer = Tokenizer.builder().build();
        List<Token> tokens = tokenizer.tokenize(text);
        return TokenPatcher.patchTokens(tokens);
    }

    /**
     * 根据 options.mode 选择不同的转换逻辑：
     * 普通模式或带空格模式：
     *      将文本转换为片假名或平假名。
     * 带送假名模式或带假名标注模式：
     *      生成包含原文本、类型、阅读和发音的符号表。
     *      使用符号表生成转换结果。
     * @param text
     * @return String
     */
    public String convert(String text) {
        List<PatchedToken> tokens = tokenize(text);
        ConvertTarget convertTarget = ConvertTarget.fromString(options.to);
        ConvertMode convertMode = ConvertMode.fromString(options.mode);

        if (convertMode.equals(ConvertMode.NORMAL) || convertMode.equals(ConvertMode.SPACED)) {
            switch (convertTarget) {
                case KATAKANA:
                    return convertMode.equals(ConvertMode.NORMAL) ?
                            tokens.stream().map(PatchedToken::getReading).reduce("", String::concat) :
                            tokens.stream().map(PatchedToken::getReading).reduce((a, b) -> a + " " + b).orElse("");
                case HIRAGANA:
                    List<String> readings = new ArrayList<>();
                    for (PatchedToken token : tokens) {
                        if (hasKanji(token.getSurface())) {
                            if (!hasKatakana(token.getSurface())) {
                                readings.add(toRawHiragana(token.getReading()));
                            } else {
                                readings.add(handleKatakanaKanjiMixedToken(token));
                            }
                        } else {
                            readings.add(token.getSurface());
                        }
                    }
                    return convertMode.equals(ConvertMode.NORMAL) ?
                            String.join("", readings) :
                            String.join(" ", readings);
                default:
                    throw new IllegalArgumentException("Unknown conversion target");
            }
        } else if (convertMode.equals(ConvertMode.OKURIGANA) || convertMode.equals(ConvertMode.FURIGANA)) {
            List<String[]> notations = new ArrayList<>();
            for (PatchedToken token : tokens) {
                int strType = getStrType(token.getSurface());
                switch (strType) {
                    case 0:
                        notations.add(new String[]{token.getSurface(), "1", toRawHiragana(token.getReading()), token.getPronunciation() != null ? token.getPronunciation() : token.getReading()});
                        break;
                    case 1:
                        processKanjiToken(notations, token);
                        break;
                    case 2:
                        processKanaToken(notations, token);
                        break;
                    case 3:
                        processOtherToken(notations, token);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown strType");
                }
            }
            return generateResult(notations);
        } else {
            throw new IllegalArgumentException("Unknown conversion mode");
        }
    }

    /**
     * 将片假名转换为平假名，生成匹配模式。
     * 使用正则表达式匹配阅读方式，处理汉字和片假名混合的情况。
     * @param token
     * @return
     */
    private String handleKatakanaKanjiMixedToken(PatchedToken token) {
        String reading = toRawHiragana(token.getReading());
        StringBuilder result = new StringBuilder();
        StringBuilder pattern = new StringBuilder();
        for (char c : token.getSurface().toCharArray()) {
            if (isKanji(c)) {
                pattern.append("(.*)");
            } else {
                pattern.append(isKatakana(c) ? toRawHiragana(Character.toString(c)) : c);
            }
        }
        Pattern hPattern = Pattern.compile(pattern.toString());
        Matcher matcher = hPattern.matcher(reading);
        if (matcher.find()) {
            int kanjiIndex = 0;
            for (char c : token.getSurface().toCharArray()) {
                if (isKanji(c)) {
                    result.append(matcher.group(kanjiIndex + 1));
                    kanjiIndex++;
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    /**
     * 生成正则表达式模式匹配阅读方式。
     * 将匹配结果添加到符号表中。
     * @param notations
     * @param token
     */
    private void processKanjiToken(List<String[]> notations, PatchedToken token) {
        StringBuilder pattern = new StringBuilder();
        boolean isLastTokenKanji = false;
        List<String> subs = new ArrayList<>();
        for (char c : token.getSurface().toCharArray()) {
            if (isKanji(c)) {
                if (!isLastTokenKanji) {
                    isLastTokenKanji = true;
                    pattern.append("(.+)");
                    subs.add(Character.toString(c));
                } else {
                    subs.set(subs.size() - 1, subs.get(subs.size() - 1) + c);
                }
            } else {
                isLastTokenKanji = false;
                subs.add(Character.toString(c));
                pattern.append(isKatakana(c) ? toRawHiragana(Character.toString(c)) : c);
            }
        }
        Pattern reg = Pattern.compile("^" + pattern.toString() + "$");
        Matcher matches = reg.matcher(toRawHiragana(token.getReading()));
        if (matches.find()) {
            int pickKanji = 1;
            for (String sub : subs) {
                if (isKanji(sub.charAt(0))) {
                    notations.add(new String[]{sub, "1", matches.group(pickKanji), toRawKatakana(matches.group(pickKanji))});
                    pickKanji++;
                } else {
                    notations.add(new String[]{sub, "2", toRawHiragana(sub), toRawKatakana(sub)});
                }
            }
        } else {
            notations.add(new String[]{token.getSurface(), "1", toRawHiragana(token.getReading()), token.getPronunciation() != null ? token.getPronunciation() : token.getReading()});
        }
    }

    /**
     * 将每个字符及其对应的平假名和发音添加到符号表中。
     * @param notations
     * @param token
     */
    private void processKanaToken(List<String[]> notations, PatchedToken token) {
        for (int i = 0; i < token.getSurface().length(); i++) {
            notations.add(new String[]{
                    Character.toString(token.getSurface().charAt(i)),
                    "2",
                    toRawHiragana(Character.toString(token.getReading().charAt(i))),
                    (token.getPronunciation() != null ? token.getPronunciation() : token.getReading()).charAt(i) + ""
            });
        }
    }

    /**
     * 将每个字符添加到符号表中。
     * @param notations
     * @param token
     */
    private void processOtherToken(List<String[]> notations, PatchedToken token) {
        for (int i = 0; i < token.getSurface().length(); i++) {
            notations.add(new String[]{
                    Character.toString(token.getSurface().charAt(i)),
                    "3",
                    Character.toString(token.getSurface().charAt(i)),
                    Character.toString(token.getSurface().charAt(i))
            });
        }
    }

    /**
     * 根据转换选项生成最终的转换结果字符串。
     * 支持片假名和平假名的送假名和假名标注两种模式。
     * @param notations
     * @return
     */
    private String generateResult(List<String[]> notations) {
        StringBuilder result = new StringBuilder();
        ConvertTarget convertTarget = ConvertTarget.fromString(options.to);
        ConvertMode convertMode = ConvertMode.fromString(options.mode);
        switch (convertTarget) {
            case KATAKANA:
                if (convertMode.equals(ConvertMode.OKURIGANA)) {
                    for (String[] notation : notations) {
                        if (!notation[1].equals("1")) {
                            result.append(notation[0]);
                        } else {
                            result.append(notation[0]).append(options.delimiter_start).append(toRawKatakana(notation[2])).append(options.delimiter_end);
                        }
                    }
                } else { // furigana
                    for (String[] notation : notations) {
                        if (!notation[1].equals("1")) {
                            result.append(notation[0]);
                        } else {
                            result.append("<ruby>").append(notation[0]).append("<rp>").append(options.delimiter_start).append("</rp><rt>").append(toRawKatakana(notation[2])).append("</rt><rp>").append(options.delimiter_end).append("</rp></ruby>");
                        }
                    }
                }
                return result.toString();
            case HIRAGANA:
                if (convertMode.equals(ConvertMode.OKURIGANA)) {
                    for (String[] notation : notations) {
                        if (!notation[1].equals("1")) {
                            result.append(notation[0]);
                        } else {
                            result.append(notation[0]).append(options.delimiter_start).append(notation[2]).append(options.delimiter_end);
                        }
                    }
                } else { // furigana
                    for (String[] notation : notations) {
                        if (!notation[1].equals("1")) {
                            result.append(notation[0]);
                        } else {
                            result.append("<ruby>").append(notation[0]).append("<rp>").append(options.delimiter_start).append("</rp><rt>").append(notation[2]).append("</rt><rp>").append(options.delimiter_end).append("</rp></ruby>");
                        }
                    }
                }
                return result.toString();
            default:
                throw new IllegalArgumentException("Unknown conversion target");
        }
    }
}
