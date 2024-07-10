

# jp-text-converter

[![License](https://img.shields.io/github/license/lassjs/lass.svg)](LICENSE)

jp-text-converter是一款十分方便使用的日文转换注音工具，主要针对日文文本，进行到平假名、片假名的转换，并支持注音假名、送假名（旁注音）等注音模式。

## 特性
- 日文文本 => 平假名、片假名
- 支持注音假名和送假名
- 使用kuromoji词法分析器
- 实用日语工具

## 如何使用
**添加Maven依赖**

```
<dependency>
    <groupId>io.github.kvapril</groupId>
    <artifactId>jp-text-converter</artifactId>
    <version>1.0.0</version>
</dependency>
```

**使用示例**

**转换成平假名，NORMAL模式**

```
public static void main(String[] args) {
    String text = "自らを愛するという事は、一生続くロマンスの始まりだ";
    //转换成平假名，NORMAL模式
    ConversionOption option = new ConversionOption(ConvertTarget.HIRAGANA, ConvertMode.NORMAL);
    //新建Convert
    JapaneseTextConverter textConverter = new JapaneseTextConverter(option);
    //调用convert进行转换
    String convertedText = textConverter.convert(text);
    System.out.println(convertedText);
    //输出：みずからをあいするということは、いっしょうつづくロマンスのはじまりだ
}
```

**转换列表**

| 目标                      | 模式      | 输出                                                         |
| ------------------------- | --------- | ------------------------------------------------------------ |
| HIRAGANA[平假名-hiragana] | NORMAL    | みずからをあいするということは、いっしょうつづくロマンスのはじまりだ |
|                           | SPACED    | みずから を あいする という こと は 、 いっしょう つづく ロマンス の はじまり だ |
|                           | OKURIGANA | 自(みずか)らを愛(あい)するという事(こと)は、一生(いっしょう)続(つづ)くロマンスの始(はじ)まりだ |
|                           | FURIGANA  | <ruby>自<rp>(</rp><rt>みずか</rt><rp>)</rp></ruby>らを<ruby>愛<rp>(</rp><rt>あい</rt><rp>)</rp></ruby>するという<ruby>事<rp>(</rp><rt>こと</rt><rp>)</rp></ruby>は、<ruby>一生<rp>(</rp><rt>いっしょう</rt><rp>)</rp></ruby><ruby>続<rp>(</rp><rt>つづ</rt><rp>)</rp></ruby>くロマンスの<ruby>始<rp>(</rp><rt>はじ</rt><rp>)</rp></ruby>まりだ |
| KATAKANA[片假名-katakana] | NORMAL    | ミズカラヲアイスルトイウコトハ、イッショウツヅクロマンスノハジマリダ |
|                           | SPACED    | ミズカラ ヲ アイスル トイウ コト ハ 、 イッショウ ツヅク ロマンス ノ ハジマリ ダ |
|                           | OKURIGANA | 自(ミズカ)らを愛(アイ)するという事(コト)は、一生(イッショウ)続(ツヅ)くロマンスの始(ハジ)まりだ |
|                           | FURIGANA  | <ruby>自<rp>(</rp><rt>ミズカ</rt><rp>)</rp></ruby>らを<ruby>愛<rp>(</rp><rt>アイ</rt><rp>)</rp></ruby>するという<ruby>事<rp>(</rp><rt>コト</rt><rp>)</rp></ruby>は、<ruby>一生<rp>(</rp><rt>イッショウ</rt><rp>)</rp></ruby><ruby>続<rp>(</rp><rt>ツヅ</rt><rp>)</rp></ruby>くロマンスの<ruby>始<rp>(</rp><rt>ハジ</rt><rp>)</rp></ruby>まりだ**** |

<ruby>自<rp>(</rp><rt>ミズカ</rt><rp>)</rp></ruby>らを<ruby>愛<rp>(</rp><rt>アイ</rt><rp>)</rp></ruby>するという<ruby>事<rp>(</rp><rt>コト</rt><rp>)</rp></ruby>は、<ruby>一生<rp>(</rp><rt>イッショウ</rt><rp>)</rp></ruby><ruby>続<rp>(</rp><rt>ツヅ</rt><rp>)</rp></ruby>くロマンスの<ruby>始<rp>(</rp><rt>ハジ</rt><rp>)</rp></ruby>まりだ**** 

```
<ruby>自<rp>(</rp><rt>みずか</rt><rp>)</rp></ruby>らを<ruby>愛<rp>(</rp><rt>あい</rt><rp>)</rp></ruby>するという<ruby>事<rp>(</rp><rt>こと</rt><rp>)</rp></ruby>は、<ruby>一生<rp>(</rp><rt>いっしょう</rt><rp>)</rp></ruby><ruby>続<rp>(</rp><rt>つづ</rt><rp>)</rp></ruby>くロマンスの<ruby>始<rp>(</rp><rt>はじ</rt><rp>)</rp></ruby>まりだ
```



### 实用工具

__示例__
```
//检查给定字符是否为平假名
String result = ConvertUitl.isHiragana("あ"));
```

**其他方法**

```
检查给定字符是否为平假名：public static boolean isHiragana(char ch)
```

```
检查给定字符是否为片假名：public static boolean isKatakana (char ch)
```

```
检查给定字符是否为假名（包括平假名和片假名）：public static boolean isKana(char ch)
```

```
检查给定字符是否为汉字：public static boolean isKanji(char ch)
```

```
检查给定字符是否为日语字符（假名或汉字）：public static boolean isJapanese(char ch)
```

```
检查给定字符串中是否包含平假名：public static boolean hasHiragana(String str)
```

```
检查给定字符串中是否包含片假名：public static boolean hasKatakana(String str) 
```

```
检查给定字符串中是否包含假名：public static boolean hasKana(String str) 
```

```
检查给定字符串中是否包含汉字：public static boolean hasKanji (String str)
```

```
检查给定字符串中是否包含日语字符：public static boolean hasJapanese(String str) 
```

```
将给定字符串中的片假名转换为平假名：public static String toRawHiragana(String str) 
```

```
将给定字符串中的平假名转换为片假名：public static String toRawKatakana(String str) 
```

```
将给定字符串中的假名转换为平假名：public static String kanaToHiragna(String str)
```

```
将给定字符串中的假名转换为片假名：public static String kanaToKatakana(String str)
```



## 参考实现

- [kuromoji](https://github.com/atilika/kuromoji)
- [kuroshiro](https://github.com/hexenq/kuroshiro/tree/master)

## 版权说明
[![License](https://img.shields.io/github/license/lassjs/lass.svg)](LICENSE)

