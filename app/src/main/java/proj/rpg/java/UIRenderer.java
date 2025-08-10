package proj.rpg.java;

import static org.lwjgl.opengl.GL11.*;

/**
 * UI要素の描画を行うクラス。
 * 固定機能パイプラインでシンプルなUI（矩形、テキスト）を描画。
 */
public class UIRenderer {
    private final int screenWidth;
    private final int screenHeight;

    public UIRenderer(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
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
     * 簡易テキスト描画（パターンベース）
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
        
        // 基本的な線を描画する関数
        switch (Character.toLowerCase(c)) {
            case 'a' -> {
                // A の形状
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 右縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h/2, w - 4*scale, lineWidth, r, g, b, a); // 中央線
            }
            case 'b' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h/2, w - 5*scale, lineWidth, r, g, b, a); // 中央線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case 'c' -> {
                drawRect(x + 1*scale, y + 4*scale, lineWidth, h - 6*scale, r, g, b, a); // 左縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case 'd' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + 2*scale, y + 2*scale, w - 5*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h - 3*scale, w - 5*scale, lineWidth, r, g, b, a); // 下線
                drawRect(x + w - 3*scale, y + 3*scale, lineWidth, h - 6*scale, r, g, b, a); // 右縦線
            }
            case 'e' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h/2, w - 5*scale, lineWidth, r, g, b, a); // 中央線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case 'f' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h/2, w - 5*scale, lineWidth, r, g, b, a); // 中央線
            }
            case 'g' -> {
                drawRect(x + 1*scale, y + 3*scale, lineWidth, h - 6*scale, r, g, b, a); // 左縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
                drawRect(x + w - 2*scale, y + h/2, lineWidth, h/2 - 2*scale, r, g, b, a); // 右下縦線
                drawRect(x + w/2, y + h/2, w/2 - 2*scale, lineWidth, r, g, b, a); // 中央右線
            }
            case 'h' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 右縦線
                drawRect(x + 2*scale, y + h/2, w - 4*scale, lineWidth, r, g, b, a); // 中央線
            }
            case 'i' -> {
                drawRect(x + w/2 - lineWidth/2, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 中央縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case 'l' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case 'm' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 右縦線
                drawRect(x + w/2 - lineWidth/2, y + 2*scale, lineWidth, h/2, r, g, b, a); // 中央縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
            }
            case 'n' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 右縦線
                // 斜線の代わりに階段状の線
                drawRect(x + 3*scale, y + 4*scale, w - 6*scale, lineWidth, r, g, b, a);
            }
            case 'o' -> {
                drawRect(x + 1*scale, y + 3*scale, lineWidth, h - 6*scale, r, g, b, a); // 左縦線
                drawRect(x + w - 2*scale, y + 3*scale, lineWidth, h - 6*scale, r, g, b, a); // 右縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case 'p' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h/2, w - 5*scale, lineWidth, r, g, b, a); // 中央線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h/2 - 2*scale, r, g, b, a); // 右上縦線
            }
            case 'r' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h/2, w - 5*scale, lineWidth, r, g, b, a); // 中央線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h/2 - 2*scale, r, g, b, a); // 右上縦線
                drawRect(x + w - 2*scale, y + h/2 + 1*scale, lineWidth, h/2 - 3*scale, r, g, b, a); // 右下縦線
            }
            case 's' -> {
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 1*scale, y + 3*scale, lineWidth, h/2 - 3*scale, r, g, b, a); // 左上縦線
                drawRect(x + 2*scale, y + h/2, w - 4*scale, lineWidth, r, g, b, a); // 中央線
                drawRect(x + w - 2*scale, y + h/2 + 1*scale, lineWidth, h/2 - 3*scale, r, g, b, a); // 右下縦線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case 't' -> {
                drawRect(x + w/2 - lineWidth/2, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 中央縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
            }
            case 'u' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 5*scale, r, g, b, a); // 左縦線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h - 5*scale, r, g, b, a); // 右縦線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case 'v' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h/2, r, g, b, a); // 左上縦線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h/2, r, g, b, a); // 右上縦線
                drawRect(x + w/2 - lineWidth/2, y + h/2 + 1*scale, lineWidth, h/2 - 3*scale, r, g, b, a); // 中央下縦線
            }
            case 'w' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 左縦線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 右縦線
                drawRect(x + w/2 - lineWidth/2, y + h/2, lineWidth, h/2 - 2*scale, r, g, b, a); // 中央下縦線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case 'x' -> {
                // X の形状（斜線の代わりに十字）
                drawRect(x + w/2 - lineWidth/2, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 縦線
                drawRect(x + 2*scale, y + h/2, w - 4*scale, lineWidth, r, g, b, a); // 横線
            }
            case 'y' -> {
                drawRect(x + 1*scale, y + 2*scale, lineWidth, h/2, r, g, b, a); // 左上縦線
                drawRect(x + w - 2*scale, y + 2*scale, lineWidth, h/2, r, g, b, a); // 右上縦線
                drawRect(x + w/2 - lineWidth/2, y + h/2, lineWidth, h/2 - 2*scale, r, g, b, a); // 中央下縦線
            }
            case 'z' -> {
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
                drawRect(x + 3*scale, y + h/2, w - 6*scale, lineWidth, r, g, b, a); // 中央斜線
            }
            // 数字のパターン
            case '0' -> {
                drawRect(x + 1*scale, y + 3*scale, lineWidth, h - 6*scale, r, g, b, a); // 左縦線
                drawRect(x + w - 2*scale, y + 3*scale, lineWidth, h - 6*scale, r, g, b, a); // 右縦線
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case '1' -> {
                drawRect(x + w/2 - lineWidth/2, y + 2*scale, lineWidth, h - 4*scale, r, g, b, a); // 中央縦線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case '2' -> {
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + w - 2*scale, y + 3*scale, lineWidth, h/2 - 3*scale, r, g, b, a); // 右上縦線
                drawRect(x + 2*scale, y + h/2, w - 4*scale, lineWidth, r, g, b, a); // 中央線
                drawRect(x + 1*scale, y + h/2 + 1*scale, lineWidth, h/2 - 3*scale, r, g, b, a); // 左下縦線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
            }
            case '3' -> {
                drawRect(x + 2*scale, y + 2*scale, w - 4*scale, lineWidth, r, g, b, a); // 上線
                drawRect(x + 2*scale, y + h/2, w - 5*scale, lineWidth, r, g, b, a); // 中央線
                drawRect(x + 2*scale, y + h - 3*scale, w - 4*scale, lineWidth, r, g, b, a); // 下線
                drawRect(x + w - 2*scale, y + 3*scale, lineWidth, h - 6*scale, r, g, b, a); // 右縦線
            }
            default -> {
                // 不明な文字は矩形で表示
                drawRect(x + 2*scale, y + 3*scale, w - 4*scale, h - 6*scale, r, g, b, a);
            }
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
}
