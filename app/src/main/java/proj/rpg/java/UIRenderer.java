package proj.rpg.java;

import static org.lwjgl.opengl.GL11.*;

/**
 * UI要素の描画を行うクラス。
 * 固定機能パイプラインでシンプルなUI（矩形、テキスト）を描画。
 */
public class UIRenderer {
    private final int screenWidth;
    private final int screenHeight;
    private final TextRenderer textRenderer; // 従来のパターンベース描画（後方互換性）
    private final TextureTextRenderer textureTextRenderer; // 新しいテクスチャベース描画

    public UIRenderer(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.textRenderer = new TextRenderer();
        this.textureTextRenderer = new TextureTextRenderer();
    }

    /**
     * 画面をクリア（背景色設定）
     */
    public void clear(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * UI描画開始（2D座標系に切り替え）
     */
    public void beginUI() {
        glDisable(GL_DEPTH_TEST);
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0, screenWidth, screenHeight, 0, -1, 1); // 左上原点

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glDisable(GL_TEXTURE_2D);
    }

    /**
     * UI描画終了（3D座標系に復帰）
     */
    public void endUI() {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
    }

    /**
     * 矩形を描画（背景用）
     */
    public void drawRect(float x, float y, float width, float height,
            float r, float g, float b, float a) {
        glColor4f(r, g, b, a);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }

    /**
     * 枠線付き矩形を描画
     */
    public void drawRectWithBorder(float x, float y, float width, float height,
            float bgR, float bgG, float bgB, float bgA,
            float borderR, float borderG, float borderB, float borderA,
            float borderWidth) {
        // 背景
        drawRect(x, y, width, height, bgR, bgG, bgB, bgA);

        // 枠線
        glColor4f(borderR, borderG, borderB, borderA);
        glLineWidth(borderWidth);
        glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }

    /**
     * テキスト描画（元のパターンベースレンダラー使用 - 一時的な修正）
     */
    public void drawText(String text, float x, float y, float scale, float r, float g, float b, float a) {
        textRenderer.drawText(text, x, y, scale, r, g, b, a);
    }

    /**
     * シンプルなテキスト描画（RGB指定）
     */
    public void drawText(String text, float x, float y, float r, float g, float b) {
        textRenderer.drawText(text, x, y, r, g, b);
    }

    /**
     * 中央揃えテキストの描画
     */
    public void drawCenteredText(String text, float centerX, float y, float r, float g, float b) {
        textRenderer.drawCenteredText(text, centerX, y, r, g, b);
    }

    /**
     * 中央揃えテキストの描画（スケール指定）
     */
    public void drawCenteredText(String text, float centerX, float y, float scale, float r, float g, float b, float a) {
        float textWidth = text.length() * 12f * scale; // charWidth = 12
        float startX = centerX - textWidth / 2;
        textRenderer.drawText(text, startX, y, scale, r, g, b, a);
    }

    /**
     * 高品質テクスチャベーステキスト描画（日本語対応）
     */
    public void drawHighQualityText(String text, float x, float y, float scale, float r, float g, float b, float a) {
        java.awt.Font font = new java.awt.Font("MS Gothic", java.awt.Font.PLAIN, (int) (24 * scale));
        java.awt.Color textColor = java.awt.Color.WHITE;

        int textureID = TextureUtils.createTextTexture(text, font, textColor, 512, 64);

        // テキストサイズの推定
        float estimatedWidth = text.length() * 12f * scale;
        float estimatedHeight = 20f * scale;

        TextureUtils.drawTexturedQuad(textureID, x, y, estimatedWidth, estimatedHeight, r, g, b, a);
    }

    /**
     * 高品質中央揃えテキストの描画
     */
    public void drawHighQualityCenteredText(String text, float centerX, float y, float scale, float r, float g, float b,
            float a) {
        float estimatedWidth = text.length() * 12f * scale;
        float startX = centerX - estimatedWidth / 2;
        drawHighQualityText(text, startX, y, scale, r, g, b, a);
    }

    /**
     * レガシーテキスト描画（パターンベース）- 必要時のフォールバック用
     */
    public void drawLegacyText(String text, float x, float y, float scale, float r, float g, float b, float a) {
        textRenderer.drawText(text, x, y, scale, r, g, b, a);
    }

    /**
     * メニュー項目の描画（選択状態に応じてハイライト）
     */
    public void drawMenuItem(String text, float x, float y, boolean selected) {
        if (selected) {
            // 選択中は背景を明るく
            drawRect(x - 5, y - 2, text.length() * 12f + 10, 20, 0.3f, 0.3f, 0.5f, 0.8f);
            drawText(text, x, y, 1.0f, 1.0f, 0.8f); // 明るい黄色
        } else {
            drawText(text, x, y, 0.8f, 0.8f, 0.8f); // グレー
        }
    }

    /**
     * タイトルロゴの描画（大きな装飾文字）
     */
    public void drawTitle(String title, float centerX, float y) {
        float charWidth = 24f;
        float charHeight = 32f;

        float titleWidth = title.length() * charWidth;
        float startX = centerX - titleWidth / 2;

        for (int i = 0; i < title.length(); i++) {
            char c = title.charAt(i);
            if (c == ' ')
                continue;

            float cx = startX + i * charWidth;

            // 影効果
            drawRect(cx + 2, y + 2, charWidth - 2, charHeight, 0.1f, 0.1f, 0.1f, 0.8f);

            // メイン文字
            drawRect(cx, y, charWidth - 2, charHeight, 0.9f, 0.7f, 0.3f, 1.0f);

            // 装飾
            drawRect(cx + 4, y + 4, charWidth - 10, charHeight - 8, 0.6f, 0.4f, 0.1f, 1.0f);
        }
    }

    /**
     * 画面全体の暗いオーバーレイ（メニュー背景用）
     */
    public void drawOverlay(float alpha) {
        drawRect(0, 0, screenWidth, screenHeight, 0.0f, 0.0f, 0.0f, alpha);
    }

    /**
     * リソースのクリーンアップ
     */
    public void dispose() {
        textureTextRenderer.dispose();
    }
}
