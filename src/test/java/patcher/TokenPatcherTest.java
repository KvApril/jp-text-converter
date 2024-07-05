package patcher;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static patcher.TokenPatcher.patchTokens;

/**
 * 文件描述
 *
 * @Description: note
 * @Author: kwi
 **/
class TokenPatcherTest {
    private static Tokenizer tokenizer;

    @BeforeAll
    static void setup() {
        tokenizer = Tokenizer.builder().build();
    }

    @Test
    void TestPatchTokens(){
        List<Token> tokens = tokenizer.tokenize("動くう速いっ 今日");
        List<PatchedToken> patchedTokens = patchTokens(tokens);
        for (PatchedToken token : patchedTokens) {
            System.out.println("Surface Form: " + token.getSurface() +
                    ", Reading: " + token.reading +
                    ", Pronunciation: " + token.pronunciation +
                    ", POS: " + token.getPos());
        }
        assertEquals(4,patchedTokens.size());
    }
}