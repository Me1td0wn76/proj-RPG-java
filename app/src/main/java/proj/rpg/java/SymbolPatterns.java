package proj.rpg.java;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * 数字、記号、日本語文字のパターン描画メソッド群
 */
public class SymbolPatterns {

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

    // === 数字描画メソッド ===

    public static void drawDigit0(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 1 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawDigit1(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + w / 2 - lineWidth / 2, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 中央縦線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawDigit2(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 右上縦線
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 中央線
        drawRect(x + 1 * scale, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 左下縦線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawDigit3(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 2 * scale, y + h / 2, w - 5 * scale, lineWidth, r, g, b, a); // 中央線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
    }

    // === 記号描画メソッド ===

    public static void drawSymbolGreater(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // 右向き矢印（三角形風）
        drawRect(x + 2 * scale, y + h / 2 - 1 * scale, lineWidth, 2 * scale, r, g, b, a); // 左点
        drawRect(x + 3 * scale, y + h / 2 - 2 * scale, lineWidth, 4 * scale, r, g, b, a); // 中央線
        drawRect(x + 4 * scale, y + h / 2 - 3 * scale, lineWidth, 6 * scale, r, g, b, a); // 右線
    }

    public static void drawSymbolLess(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // 左向き矢印
        drawRect(x + w - 3 * scale, y + h / 2 - 1 * scale, lineWidth, 2 * scale, r, g, b, a); // 右点
        drawRect(x + w - 4 * scale, y + h / 2 - 2 * scale, lineWidth, 4 * scale, r, g, b, a); // 中央線
        drawRect(x + w - 5 * scale, y + h / 2 - 3 * scale, lineWidth, 6 * scale, r, g, b, a); // 左線
    }

    public static void drawSymbolDash(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 横線
    }

    public static void drawSymbolSlash(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // 斜線（階段状）
        drawRect(x + 6 * scale, y + 2 * scale, lineWidth, 3 * scale, r, g, b, a);
        drawRect(x + 4 * scale, y + 5 * scale, lineWidth, 3 * scale, r, g, b, a);
        drawRect(x + 2 * scale, y + 8 * scale, lineWidth, 3 * scale, r, g, b, a);
    }

    public static void drawSymbolColon(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // コロン
        drawRect(x + w / 2 - 1 * scale, y + h / 3, 2 * scale, 2 * scale, r, g, b, a); // 上点
        drawRect(x + w / 2 - 1 * scale, y + 2 * h / 3, 2 * scale, 2 * scale, r, g, b, a); // 下点
    }

    // === 日本語文字描画メソッド ===

    public static void drawHiraganaA(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        // あ の簡易パターン
        drawRect(x + 2 * scale, y + 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + w / 2, y + 4 * scale, lineWidth, h / 2 - 2 * scale, r, g, b, a); // 中央縦線
        drawRect(x + 2 * scale, y + h - 4 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
        drawRect(x + 1 * scale, y + 5 * scale, lineWidth, h / 3, r, g, b, a); // 左縦線
    }

    public static void drawHiraganaI(float x, float y, float scale, float w, float h, float lineWidth, float r, float g,
            float b, float a) {
        // い の簡易パターン
        drawRect(x + 3 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 3 * scale, y + 3 * scale, lineWidth, h - 5 * scale, r, g, b, a); // 右縦線
        drawRect(x + 4 * scale, y + h / 2, w / 2 - 2 * scale, lineWidth, r, g, b, a); // 中央線
    }

    public static void drawHiraganaNo(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // の の簡易パターン（円形風）
        drawRect(x + 2 * scale, y + 4 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 1 * scale, y + 5 * scale, lineWidth, h / 2 - 2 * scale, r, g, b, a); // 左線
        drawRect(x + w - 2 * scale, y + 5 * scale, lineWidth, h / 2 - 2 * scale, r, g, b, a); // 右線
        drawRect(x + 2 * scale, y + h - 4 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下線
    }

    public static void drawKatakanaGe(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ゲ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上線
        drawRect(x + 1 * scale, y + 3 * scale, lineWidth, h - 5 * scale, r, g, b, a); // 左縦線
        drawRect(x + w / 2, y + h / 2, w / 2 - 2 * scale, lineWidth, r, g, b, a); // 中央線
        drawRect(x + w - 2 * scale, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 右下縦線
    }

    public static void drawKatakanaLongVowel(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // 長音記号
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth * 1.5f, r, g, b, a); // 太い横線
    }

    public static void drawKatakanaMu(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ム の簡易パターン
        drawRect(x + w / 2 - lineWidth / 2, y + 2 * scale, lineWidth, h / 3, r, g, b, a); // 上縦線
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 横線
        drawRect(x + 3 * scale, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 左下線
        drawRect(x + w - 3 * scale, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 右下線
    }

    public static void drawKatakanaDa(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ダ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 中横線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下横線
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
        // 濁点
        drawRect(x + w + 1 * scale, y + 2 * scale, lineWidth, lineWidth, r, g, b, a);
        drawRect(x + w + 1 * scale, y + 4 * scale, lineWidth, lineWidth, r, g, b, a);
    }

    public static void drawKatakanaN(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ン の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + h / 2, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
    }

    public static void drawKatakanaJi(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ジ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + w / 2 - lineWidth / 2, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 中央縦線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下横線
        // 濁点
        drawRect(x + w + 1 * scale, y + 2 * scale, lineWidth, lineWidth, r, g, b, a);
        drawRect(x + w + 1 * scale, y + 4 * scale, lineWidth, lineWidth, r, g, b, a);
    }

    public static void drawKatakanaYo(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ョ の簡易パターン（小さいヨ）
        float smallScale = scale * 0.7f;
        drawRect(x + 2 * smallScale, y + h / 3, w - 4 * smallScale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * smallScale, y + h / 2, w - 4 * smallScale, lineWidth, r, g, b, a); // 中横線
        drawRect(x + 2 * smallScale, y + 2 * h / 3, w - 4 * smallScale, lineWidth, r, g, b, a); // 下横線
    }

    public static void drawKatakanaE(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // エ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 中横線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下横線
        drawRect(x + 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 左縦線
    }

    public static void drawKatakanaKu(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ク の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w / 2, lineWidth, r, g, b, a); // 上横線（短い）
        drawRect(x + 2 * scale, y + h / 2, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
    }

    public static void drawKatakanaS(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ス の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + h / 2, w / 2, lineWidth, r, g, b, a); // 中横線（短い）
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
    }

    public static void drawKatakanaP(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // プ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + h / 2, w / 2, lineWidth, r, g, b, a); // 中横線（短い）
        drawRect(x + w / 2, y + 3 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 中央縦線
        // 半濁点
        drawRect(x + w + 1 * scale, y + 2 * scale, lineWidth * 2, lineWidth, r, g, b, a);
    }

    public static void drawKatakanaRo(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ロ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下横線
        drawRect(x + 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
    }

    public static void drawKatakanaRa(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ラ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + 3 * scale, lineWidth, h / 2, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
    }

    public static void drawKatakanaU(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ウ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + h / 2, w / 2, lineWidth, r, g, b, a); // 左中横線
        drawRect(x + w / 2, y + h / 2, w / 2 - 2 * scale, lineWidth, r, g, b, a); // 右中横線
        drawRect(x + w / 2 - lineWidth / 2, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 中央縦線
    }

    public static void drawKatakanaSmallI(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ィ の簡易パターン（小さいイ）
        float smallScale = scale * 0.7f;
        drawRect(x + 2 * smallScale, y + h / 3, w - 4 * smallScale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + w / 2 - lineWidth / 2, y + h / 3 + smallScale, lineWidth, h / 3, r, g, b, a); // 縦線
        drawRect(x + 3 * smallScale, y + 2 * h / 3, w / 2, lineWidth, r, g, b, a); // 下横線
    }

    public static void drawKatakanaZa(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ザ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 中横線
        drawRect(x + w - 2 * scale, y + h / 2, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 右縦線
        // 濁点
        drawRect(x + w + 1 * scale, y + 2 * scale, lineWidth, lineWidth, r, g, b, a);
        drawRect(x + w + 1 * scale, y + 4 * scale, lineWidth, lineWidth, r, g, b, a);
    }

    public static void drawKatakanaDo(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ド の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + h / 2, w / 2, lineWidth, r, g, b, a); // 中横線（短い）
        drawRect(x + 2 * scale, y + h - 3 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 下横線
        // 濁点
        drawRect(x + w + 1 * scale, y + 2 * scale, lineWidth, lineWidth, r, g, b, a);
        drawRect(x + w + 1 * scale, y + 4 * scale, lineWidth, lineWidth, r, g, b, a);
    }

    public static void drawKatakanaRi(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // リ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 左縦線
        drawRect(x + w - 2 * scale, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 右縦線
    }

    public static void drawKatakanaGa(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ガ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + h / 2, w / 2, lineWidth, r, g, b, a); // 中横線（短い）
        drawRect(x + w / 2, y + h / 2 + 2 * scale, w / 2 - 2 * scale, lineWidth, r, g, b, a); // 下横線（短い）
        drawRect(x + w - 2 * scale, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 右縦線
        // 濁点
        drawRect(x + w + 1 * scale, y + 2 * scale, lineWidth, lineWidth, r, g, b, a);
        drawRect(x + w + 1 * scale, y + 4 * scale, lineWidth, lineWidth, r, g, b, a);
    }

    // ひらがな文字の追加
    public static void drawHiraganaFu(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // ふ の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + 3 * scale, lineWidth, h / 3, r, g, b, a); // 左縦線
        drawRect(x + 2 * scale, y + h / 2, w / 2, lineWidth, r, g, b, a); // 中横線（短い）
        drawRect(x + w / 2, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 中央縦線
    }

    public static void drawHiraganaU(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // う の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w / 2, lineWidth, r, g, b, a); // 上横線（短い）
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 中横線
        drawRect(x + w / 2 - lineWidth / 2, y + h / 2 + 1 * scale, lineWidth, h / 2 - 3 * scale, r, g, b, a); // 縦線
    }

    public static void drawHiraganaRu(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // る の簡易パターン
        drawRect(x + 2 * scale, y + 2 * scale, w / 2, lineWidth, r, g, b, a); // 上横線（短い）
        drawRect(x + 2 * scale, y + h / 2, w - 4 * scale, lineWidth, r, g, b, a); // 中横線
        drawRect(x + w / 2 - lineWidth / 2, y + 3 * scale, lineWidth, h - 6 * scale, r, g, b, a); // 縦線
    }

    public static void drawHiraganaKi(float x, float y, float scale, float w, float h, float lineWidth, float r,
            float g, float b, float a) {
        // キ の簡易パターン
        drawRect(x + w / 2 - lineWidth / 2, y + 2 * scale, lineWidth, h - 4 * scale, r, g, b, a); // 中央縦線
        drawRect(x + 2 * scale, y + h / 3, w - 4 * scale, lineWidth, r, g, b, a); // 上横線
        drawRect(x + 2 * scale, y + 2 * h / 3, w - 4 * scale, lineWidth, r, g, b, a); // 下横線
    }

    public static void drawUnknownCharacter(float x, float y, float scale, float w, float h, float r, float g, float b,
            float a) {
        // 不明な文字は矩形で表示
        drawRect(x + 2 * scale, y + 3 * scale, w - 4 * scale, h - 6 * scale, r, g, b, a);
    }
}
