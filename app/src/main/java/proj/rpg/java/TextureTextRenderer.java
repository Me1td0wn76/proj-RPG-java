package proj.rpg.java;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * BufferedImageを使用したテクスチャベースのテキスト描画クラス。
 * AWTのフォント機能を利用して高品質な日本語テキスト描画を提供。
 */
public class TextureTextRenderer {
    private Map<String, Integer> textureCache = new HashMap<>();
    private Map<String, FontMetrics> metricsCache = new HashMap<>();

    // デフォルトフォント設定
    private Font defaultFont = new Font("MS Gothic", Font.PLAIN, 24);
    private Font largeFont = new Font("MS Gothic", Font.PLAIN, 36);

    /**
     * 指定したテキストからテクスチャを生成
     */
    public ByteBuffer createTextTexture(String text, Font font, Color color, int maxWidth, int maxHeight) {
        // テンポラリBufferedImageでテキストサイズを測定
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tempG = tempImage.createGraphics();
        tempG.setFont(font);
        FontMetrics metrics = tempG.getFontMetrics();

        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        // 実際のテクスチャサイズを決定（2の冪乗に調整）
        int textureWidth = Math.min(nearestPowerOf2(textWidth + 20), maxWidth);
        int textureHeight = Math.min(nearestPowerOf2(textHeight + 10), maxHeight);

        tempG.dispose();

        // 実際の描画用BufferedImage作成
        BufferedImage image = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // アンチエイリアス有効化
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 背景を透明に設定
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, textureWidth, textureHeight);
        g.setComposite(AlphaComposite.SrcOver);

        // フォントと色を設定
        g.setFont(font);
        g.setColor(color);

        // テキストを描画（中央揃え）
        FontMetrics fm = g.getFontMetrics();
        int x = (textureWidth - fm.stringWidth(text)) / 2;
        int y = (textureHeight - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, x, y);

        g.dispose();

        // BufferedImageをByteBufferに変換
        ByteBuffer buffer = BufferUtils.createByteBuffer(textureWidth * textureHeight * 4);
        for (int py = 0; py < textureHeight; py++) {
            for (int px = 0; px < textureWidth; px++) {
                int pixel = image.getRGB(px, py);
                buffer.put((byte) ((pixel >> 16) & 0xFF)); // 赤
                buffer.put((byte) ((pixel >> 8) & 0xFF)); // 緑
                buffer.put((byte) (pixel & 0xFF)); // 青
                buffer.put((byte) ((pixel >> 24) & 0xFF)); // アルファ
            }
        }
        buffer.flip();
        return buffer;
    }

    /**
     * テクスチャを作成してOpenGLにアップロード
     */
    public int createTexture(ByteBuffer buffer, int width, int height) {
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

        return textureID;
    }

    /**
     * テクスチャ付きクワッドを描画
     */
    public void drawTexturedQuad(int textureID, float x, float y, float width, float height, float r, float g, float b,
            float a) {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glBindTexture(GL_TEXTURE_2D, textureID);
        glColor4f(r, g, b, a);

        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glEnd();

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    /**
     * 高レベルテキスト描画メソッド（簡易版 - クラッシュ回避）
     */
    public void drawText(String text, float x, float y, float scale, float r, float g, float b, float a) {
        // 一時的に従来の描画方式に戻す（安全のため）
        // BufferedImageテクスチャは後で段階的に実装

        float charWidth = 12f * scale;
        float charHeight = 16f * scale;

        // シンプルな矩形ベーステキスト描画（フォールバック）
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ')
                continue;

            float cx = x + i * charWidth;

            // 文字の背景
            glColor4f(r * 0.3f, g * 0.3f, b * 0.3f, a * 0.7f);
            glBegin(GL_QUADS);
            glVertex2f(cx, y);
            glVertex2f(cx + charWidth - 2, y);
            glVertex2f(cx + charWidth - 2, y + charHeight);
            glVertex2f(cx, y + charHeight);
            glEnd();

            // 文字の簡単な表現
            glColor4f(r, g, b, a);
            glBegin(GL_QUADS);
            glVertex2f(cx + 2, y + 2);
            glVertex2f(cx + charWidth - 4, y + 2);
            glVertex2f(cx + charWidth - 4, y + charHeight - 2);
            glVertex2f(cx + 2, y + charHeight - 2);
            glEnd();
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
    public void drawCenteredText(String text, float centerX, float y, float scale, float r, float g, float b, float a) {
        float charWidth = 12f * scale;
        float textWidth = text.length() * charWidth;
        float startX = centerX - textWidth / 2;
        drawText(text, startX, y, scale, r, g, b, a);
    }

    /**
     * 中央揃えテキストの描画（RGB指定）
     */
    public void drawCenteredText(String text, float centerX, float y, float r, float g, float b) {
        drawCenteredText(text, centerX, y, 1.0f, r, g, b, 1.0f);
    }

    /**
     * キャッシュされたテクスチャをクリア
     */
    public void clearCache() {
        for (Integer textureID : textureCache.values()) {
            glDeleteTextures(textureID);
        }
        textureCache.clear();
        metricsCache.clear();
    }

    /**
     * フォントメトリクスをキャッシュから取得または作成
     */
    private FontMetrics getOrCreateFontMetrics(Font font) {
        String key = font.toString();
        FontMetrics metrics = metricsCache.get(key);

        if (metrics == null) {
            BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D tempG = tempImage.createGraphics();
            tempG.setFont(font);
            metrics = tempG.getFontMetrics();
            tempG.dispose();

            metricsCache.put(key, metrics);
        }

        return metrics;
    }

    /**
     * 最も近い2の冪乗を計算
     */
    private int nearestPowerOf2(int value) {
        int power = 1;
        while (power < value) {
            power <<= 1;
        }
        return power;
    }

    /**
     * リソースのクリーンアップ
     */
    public void dispose() {
        clearCache();
    }
}
