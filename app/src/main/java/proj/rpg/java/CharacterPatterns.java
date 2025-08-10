package proj.rpg.java;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * 文字パターン描画メソッド群
 * TextRendererで使用する個別の文字描画実装
 */
public class CharacterPatterns {

    /**
     * 基本的な矩形描画関数
     */
    public static void drawRect(float x, float y, float width, float height, float r, float g, float b, float a) {
        glColor4f(r, g, b, a);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }

    // === アルファベット描画メソッド ===

    public static void drawLetterA(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 右縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 中央線
    }

    public static void drawLetterB(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h / 2, w - 5 * scale, lineWidth, r, g, b, a); // 中央線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawLetterC(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 4 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawLetterD(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 5 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 5 * scale, lineWidth, r, g, b, a); // 下線
        drawRect(x + w - 3 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
    }

    public static void drawLetterE(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h / 2, w - 5 * scale, lineWidth, r, g, b, a); // 中央線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawLetterF(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h / 2, w - 5 * scale, lineWidth, r, g, b, a); // 中央線
    }

    public static void drawLetterG(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
        drawRect(x + w - 2 * scale, y + h / 2, lineWidth, h / 2 - 2 * scale, r, g, b, a); // 右下縦線
        drawRect(x + w / 2, y + h / 2, w / 2 - 2 * scale, lineWidth, r, g, b, a); // 中央右線
    }

    public static void drawLetterH(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 右縦線
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 中央線
    }

    public static void drawLetterI(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + w / 2 - lineWidth / 2, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 中央縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawLetterL(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawLetterM(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 右縦線
        drawRect(x + w / 2 - lineWidth / 2, y + 2 * scale, lineWidth, h / 2, r, g, b, a); // 中央縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
    }

    public static void drawLetterN(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 右縦線
        drawRect(x + 3 * scale, y + 4 * scale, w - 6 * scale, lineWidth, r, g, b, a); // 斜線の代わりに階段状の線
    }

    public static void drawLetterO(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawLetterP(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h / 2, w - 5 * scale, lineWidth, r, g, b, a); // 中央線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h / 2 - 2 * scale, r, g, b, a); // 右上縦線
    }

    public static void drawLetterR(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h / 2, w - 5 * scale, lineWidth, r, g, b, a); // 中央線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h / 2 - 2 * scale, r, g, b, a); // 右上縦線
        drawRect(x + w - 2 * scale, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 右下縦線
    }

    public static void drawLetterS(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 1 * scale, y + 3 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 左上縦線
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 中央線
        drawRect(x + w - 2 * scale, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 右下縦線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawLetterT(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + w / 2 - lineWidth / 2, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 中央縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
    }

    public static void drawLetterU(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 5 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h - 5 * scale, r, g, b, a); // 右縦線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawLetterV(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h / 2, r, g, b, a); // 左上縦線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h / 2, r, g, b, a); // 右上縦線
        drawRect(x + w / 2 - lineWidth / 2, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 中央下縦線
    }

    public static void drawLetterW(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 右縦線
        drawRect(x + w / 2 - lineWidth / 2, y + h / 2, lineWidth, h / 2 - 2 * scale, r, g, b, a); // 中央下縦線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawLetterX(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + w / 2 - lineWidth / 2, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 縦線
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 横線
    }

    public static void drawLetterY(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 2 * scale, lineWidth, h / 2, r, g, b, a); // 左上縦線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h / 2, r, g, b, a); // 右上縦線
        drawRect(x + w / 2 - lineWidth / 2, y + h / 2, lineWidth, h / 2 - 2 * scale, r, g, b, a); // 中央下縦線
    }

    public static void drawLetterZ(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
        drawRect(x + 3 * scale, y + h / 2, w - 6 * scale, lineWidth, r, g, b, a); // 中央斜線
    }
}
