import enums.ConvertMode;
import enums.ConvertTarget;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 文件描述
 *
 * @Description: note
 * @Author: kiwi
 **/
class JapaneseTextConverterTest {

    private static String text;

    @BeforeAll
    static void setUp(){
        text = "自らを愛するという事は、一生続くロマンスの始まりだ";
    }

    @Test
    void TestConvertToHiraganaByUsingNormalMode(){
        ConversionOption option = new ConversionOption(ConvertTarget.HIRAGANA, ConvertMode.NORMAL);
        JapaneseTextConverter textConverter = new JapaneseTextConverter(option);
        String convertedText = textConverter.convert(text);
        assertEquals("みずからをあいするということは、いっしょうつづくロマンスのはじまりだ",convertedText);
    }
    @Test
    void TestConvertToHiraganaByUsingSpacedMode(){
        ConversionOption option = new ConversionOption(ConvertTarget.HIRAGANA, ConvertMode.SPACED);
        JapaneseTextConverter textConverter = new JapaneseTextConverter(option);
        String convertedText = textConverter.convert(text);
        assertEquals("みずから を あいする という こと は 、 いっしょう つづく ロマンス の はじまり だ",convertedText);
    }
    @Test
    void TestConvertToHiraganaByUsingOkuriganaMode(){
        ConversionOption option = new ConversionOption(ConvertTarget.HIRAGANA, ConvertMode.OKURIGANA);
        JapaneseTextConverter textConverter = new JapaneseTextConverter(option);
        String convertedText = textConverter.convert(text);
        assertEquals("自(みずか)らを愛(あい)するという事(こと)は、一生(いっしょう)続(つづ)くロマンスの始(はじ)まりだ",convertedText);
    }

    @Test
    void TestConvertToHiraganaByUsingOFuriganaMode(){
        ConversionOption option = new ConversionOption(ConvertTarget.HIRAGANA, ConvertMode.FURIGANA);
        JapaneseTextConverter textConverter = new JapaneseTextConverter(option);
        String convertedText = textConverter.convert(text);
        assertEquals("<ruby>自<rp>(</rp><rt>みずか</rt><rp>)</rp></ruby>らを<ruby>愛<rp>(</rp><rt>あい</rt><rp>)</rp></ruby>するという<ruby>事<rp>(</rp><rt>こと</rt><rp>)</rp></ruby>は、<ruby>一生<rp>(</rp><rt>いっしょう</rt><rp>)</rp></ruby><ruby>続<rp>(</rp><rt>つづ</rt><rp>)</rp></ruby>くロマンスの<ruby>始<rp>(</rp><rt>はじ</rt><rp>)</rp></ruby>まりだ",convertedText);
    }

    @Test
    void TestConvertToKatanaByUsingNormalMode(){
        ConversionOption option = new ConversionOption(ConvertTarget.KATAKANA, ConvertMode.NORMAL);
        JapaneseTextConverter textConverter = new JapaneseTextConverter(option);
        String convertedText = textConverter.convert(text);
        assertEquals("ミズカラヲアイスルトイウコトハ、イッショウツヅクロマンスノハジマリダ",convertedText);
    }
    @Test
    void TestConvertToKatanaByUsingSpacedMode(){
        ConversionOption option = new ConversionOption(ConvertTarget.KATAKANA, ConvertMode.SPACED);
        JapaneseTextConverter textConverter = new JapaneseTextConverter(option);
        String convertedText = textConverter.convert(text);
        assertEquals("ミズカラ ヲ アイスル トイウ コト ハ 、 イッショウ ツヅク ロマンス ノ ハジマリ ダ",convertedText);
    }
    @Test
    void TestConvertToKatanaByUsingOkuriganaMode(){
        ConversionOption option = new ConversionOption(ConvertTarget.KATAKANA, ConvertMode.OKURIGANA);
        JapaneseTextConverter textConverter = new JapaneseTextConverter(option);
        String convertedText = textConverter.convert(text);
        assertEquals("自(ミズカ)らを愛(アイ)するという事(コト)は、一生(イッショウ)続(ツヅ)くロマンスの始(ハジ)まりだ",convertedText);
    }

    @Test
    void TestConvertToKatanaByUsingOFuriganaMode(){
        ConversionOption option = new ConversionOption(ConvertTarget.KATAKANA, ConvertMode.FURIGANA);
        JapaneseTextConverter textConverter = new JapaneseTextConverter(option);
        String convertedText = textConverter.convert(text);
        assertEquals("<ruby>自<rp>(</rp><rt>ミズカ</rt><rp>)</rp></ruby>らを<ruby>愛<rp>(</rp><rt>アイ</rt><rp>)</rp></ruby>するという<ruby>事<rp>(</rp><rt>コト</rt><rp>)</rp></ruby>は、<ruby>一生<rp>(</rp><rt>イッショウ</rt><rp>)</rp></ruby><ruby>続<rp>(</rp><rt>ツヅ</rt><rp>)</rp></ruby>くロマンスの<ruby>始<rp>(</rp><rt>ハジ</rt><rp>)</rp></ruby>まりだ",convertedText);
    }
}