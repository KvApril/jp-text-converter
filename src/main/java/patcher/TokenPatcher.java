package patcher;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static util.ConvertUtil.*;

public class TokenPatcher {

    /**
     * 初始化：将输入的 Token 对象转换为 PatchedToken 对象。
     * 标准化阅读方式：对日语词汇的阅读方式进行标准化处理。
     * 特殊语法处理：对助动词 "う" 和结尾为 "っ" 的动词和形容词进行特殊处理，确保其语法正确。
     * Patch tokens for conversion
     * @param tokens tokens Given tokens
     * @return List Patched tokens
     */
    public static List<PatchedToken> patchTokens(List<Token> tokens) {
        List<PatchedToken> patchedTokens = new ArrayList<>();
        for (Token token : tokens) {
            PatchedToken patchedToken = new PatchedToken(token);

            if (hasJapanese(patchedToken.getSurface())) {
                if (patchedToken.reading == null) {
                    if (patchedToken.getSurface().chars().allMatch(c -> isKana((char) c))) {
                        patchedToken.reading = toRawKatakana(patchedToken.getSurface());
                    } else {
                        patchedToken.reading = patchedToken.getSurface();
                    }
                } else if (hasHiragana(patchedToken.reading)) {
                    patchedToken.reading = toRawKatakana(patchedToken.reading);
                }
            } else {
                patchedToken.reading = patchedToken.getSurface();
            }

            patchedTokens.add(patchedToken);
        }

        // patch for 助動詞"う" after 動詞
        for (int i = 0; i < patchedTokens.size(); i++) {
            PatchedToken token = patchedTokens.get(i);
            if (token.getPos() != null && token.getPos().startsWith("助動詞") &&
                    (token.getSurface().equals("う") || token.getSurface().equals("ウ"))) {
                if (i - 1 >= 0 && patchedTokens.get(i - 1).getPos() != null &&
                        patchedTokens.get(i - 1).getPos().startsWith("動詞")) {
                    PatchedToken prevToken = patchedTokens.get(i - 1);
                    prevToken.surface += "う";
                    prevToken.pronunciation = Objects.toString(prevToken.pronunciation, prevToken.reading) + "ー";
                    prevToken.reading += "ウ";
                    patchedTokens.remove(i);
                    i--;
                }
            }
        }

        // patch for "っ" at the tail of 動詞、形容詞
        for (int j = 0; j < patchedTokens.size(); j++) {
            PatchedToken token = patchedTokens.get(j);
            if (token.getPos() != null && (token.getPos().startsWith("動詞") || token.getPos().startsWith("形容詞")) &&
                    token.getSurface().length() > 1 &&
                    (token.getSurface().endsWith("っ") || token.getSurface().endsWith("ッ"))) {
                if (j + 1 < patchedTokens.size()) {
                    PatchedToken nextToken = patchedTokens.get(j + 1);
                    token.surface += nextToken.getSurface();
                    if (token.pronunciation != null) {
                        token.pronunciation += Objects.toString(nextToken.pronunciation, " ");
                    } else {
                        token.pronunciation = token.reading + nextToken.reading;
                    }
                    token.reading += nextToken.reading;
                    patchedTokens.remove(j + 1);
                    j--;
                }
            }
        }

        return patchedTokens;
    }
}
