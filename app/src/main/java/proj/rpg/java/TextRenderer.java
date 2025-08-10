package proj.rpg.java;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * テキスト描画専用クラス。
 * 文字パターンベースでのテキスト描画機能を提供。
 */
public class TextRenderer {

    /**
     * 基本的な矩形描画関数
     */
    private void drawRect(float x, float y, float width, float height, float r, float g, float b, float a) {
        glColor4f(r, g, b, a);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }

    /**
     * テキスト描画（パターンベース）
     * 各文字に対して異なるパターンの矩形を描画して文字らしく見せる
     */
    public void drawText(String text, float x, float y, float scale, float r, float g, float b, float a) {
        float charWidth = 12f * scale;
        float charHeight = 16f * scale;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                // スペースはスキップ
                continue;
            }

            float cx = x + i * charWidth;

            // 文字の背景（わずかに暗い背景）
            drawRect(cx, y, charWidth - 1 * scale, charHeight, r * 0.2f, g * 0.2f, b * 0.2f, a * 0.3f);

            // 文字ごとに異なるパターンを描画
            drawCharacterPattern(c, cx, y, scale, r, g, b, a);
        }
    }

    /**
     * 個別の文字パターンを描画
     */
    private void drawCharacterPattern(char c, float x, float y, float scale, float r, float g, float b, float a) {
        float w = 10f * scale; // 文字幅
        float h = 14f * scale; // 文字高さ
        float lineWidth = 1.5f * scale; // 線の太さ

        // 文字ごとのパターンを描画
        switch (Character.toLowerCase(c)) {
            case 'a' -> CharacterPatterns.drawLetterA(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'b' -> CharacterPatterns.drawLetterB(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'c' -> CharacterPatterns.drawLetterC(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'd' -> CharacterPatterns.drawLetterD(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'e' -> CharacterPatterns.drawLetterE(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'f' -> CharacterPatterns.drawLetterF(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'g' -> CharacterPatterns.drawLetterG(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'h' -> CharacterPatterns.drawLetterH(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'i' -> CharacterPatterns.drawLetterI(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'l' -> CharacterPatterns.drawLetterL(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'm' -> CharacterPatterns.drawLetterM(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'n' -> CharacterPatterns.drawLetterN(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'o' -> CharacterPatterns.drawLetterO(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'p' -> CharacterPatterns.drawLetterP(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'r' -> CharacterPatterns.drawLetterR(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 's' -> CharacterPatterns.drawLetterS(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 't' -> CharacterPatterns.drawLetterT(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'u' -> CharacterPatterns.drawLetterU(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'v' -> CharacterPatterns.drawLetterV(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'w' -> CharacterPatterns.drawLetterW(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'x' -> CharacterPatterns.drawLetterX(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'y' -> CharacterPatterns.drawLetterY(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'z' -> CharacterPatterns.drawLetterZ(x, y, scale, w, h, lineWidth, r, g, b, a);

            // 数字
            case '0' -> SymbolPatterns.drawDigit0(x, y, scale, w, h, lineWidth, r, g, b, a);
            case '1' -> SymbolPatterns.drawDigit1(x, y, scale, w, h, lineWidth, r, g, b, a);
            case '2' -> SymbolPatterns.drawDigit2(x, y, scale, w, h, lineWidth, r, g, b, a);
            case '3' -> SymbolPatterns.drawDigit3(x, y, scale, w, h, lineWidth, r, g, b, a);

            // 記号類
            case '>' -> SymbolPatterns.drawSymbolGreater(x, y, scale, w, h, lineWidth, r, g, b, a);
            case '<' -> SymbolPatterns.drawSymbolLess(x, y, scale, w, h, lineWidth, r, g, b, a);
            case '-' -> SymbolPatterns.drawSymbolDash(x, y, scale, w, h, lineWidth, r, g, b, a);
            case '/' -> SymbolPatterns.drawSymbolSlash(x, y, scale, w, h, lineWidth, r, g, b, a);
            case ':' -> SymbolPatterns.drawSymbolColon(x, y, scale, w, h, lineWidth, r, g, b, a);

            // 日本語文字パターン
            case 'あ' -> SymbolPatterns.drawHiraganaA(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'い' -> SymbolPatterns.drawHiraganaI(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'う' -> SymbolPatterns.drawHiraganaU(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'の' -> SymbolPatterns.drawHiraganaNo(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ふ' -> SymbolPatterns.drawHiraganaFu(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'る' -> SymbolPatterns.drawHiraganaRu(x, y, scale, w, h, lineWidth, r, g, b, a);

            // カタカナ文字パターン
            case 'ウ' -> SymbolPatterns.drawKatakanaU(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ィ' -> SymbolPatterns.drawKatakanaSmallI(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ザ' -> SymbolPatterns.drawKatakanaZa(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ド' -> SymbolPatterns.drawKatakanaDo(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'リ' -> SymbolPatterns.drawKatakanaRi(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ガ' -> SymbolPatterns.drawKatakanaGa(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'キ' -> SymbolPatterns.drawHiraganaKi(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ダ' -> SymbolPatterns.drawKatakanaDa(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ン' -> SymbolPatterns.drawKatakanaN(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ジ' -> SymbolPatterns.drawKatakanaJi(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ョ' -> SymbolPatterns.drawKatakanaYo(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'エ' -> SymbolPatterns.drawKatakanaE(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ク' -> SymbolPatterns.drawKatakanaKu(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ス' -> SymbolPatterns.drawKatakanaS(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'プ' -> SymbolPatterns.drawKatakanaP(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ロ' -> SymbolPatterns.drawKatakanaRo(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ラ' -> SymbolPatterns.drawKatakanaRa(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ゲ' -> SymbolPatterns.drawKatakanaGe(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ー' -> SymbolPatterns.drawKatakanaLongVowel(x, y, scale, w, h, lineWidth, r, g, b, a);
            case 'ム' -> SymbolPatterns.drawKatakanaMu(x, y, scale, w, h, lineWidth, r, g, b, a);

            default -> SymbolPatterns.drawUnknownCharacter(x, y, scale, w, h, r, g, b, a);
        }
    }

    /**
     * シンプルなテキスト描画（RGB指定）
     */
    public void drawText(String text, float x, float y, float r, float g, float b) {
        drawText(text, x, y, 1.0f, r, g, b, 1.0f);
    }

    /**
     * 中央揃えテキストの描画
     */
    public void drawCenteredText(String text, float centerX, float y, float r, float g, float b) {
        float textWidth = text.length() * 12f; // charWidth = 12
        float startX = centerX - textWidth / 2;
        drawText(text, startX, y, r, g, b);
    }
}
