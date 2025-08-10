package proj.rpg.java;

import org.lwjgl.glfw.GLFW;

/**
 * タイトル画面クラス。
 * ゲーム開始時の最初の画面。
 */
public class TitleScreen extends GameScreen {
    private float titleTimer = 0.0f;
    private boolean spacePressed = false;

    @Override
    public void update(Input input) {
        titleTimer += 0.016f; // 60FPSでの時間更新

        // スペースキーでメニューに進む
        if (input.isKeyPressed(GLFW.GLFW_KEY_SPACE) && !spacePressed) {
            spacePressed = true;
            transitionRequested = true;
        }

        if (!input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            spacePressed = false;
        }
    }

    @Override
    public void render(UIRenderer uiRenderer) {
        // 画面をクリア（暗い青色）
        uiRenderer.clear(0.1f, 0.1f, 0.3f, 1.0f);

        // タイトルテキスト
        String titleText = "DUNGEON EXPLORER";
        float titleX = 960 / 2 - (titleText.length() * 12 * 2.0f) / 2; // 中央揃え
        uiRenderer.drawText(titleText, titleX, 200, 2.0f, 1.0f, 1.0f, 0.8f, 1.0f);

        // サブタイトル
        String subtitleText = "A Wizardry-Style 3D RPG";
        float subtitleX = 960 / 2 - (subtitleText.length() * 12) / 2;
        uiRenderer.drawText(subtitleText, subtitleX, 260, 1.0f, 0.8f, 0.8f, 0.6f, 1.0f);

        // 点滅する「Press SPACE」メッセージ
        float blinkAlpha = (float) (Math.sin(titleTimer * 3.0) * 0.5 + 0.5);
        String pressText = "Press SPACE to continue";
        float pressX = 960 / 2 - (pressText.length() * 12) / 2;
        uiRenderer.drawText(pressText, pressX, 400, 1.0f, 1.0f, 1.0f, 1.0f, blinkAlpha);

        // バージョン情報
        // バージョン情報
        String versionText = "v1.0.0";
        uiRenderer.drawText(versionText, 10, 540 - 30, 0.8f, 0.5f, 0.5f, 0.5f, 1.0f);
    }

    @Override
    public void onEnter() {
        super.onEnter();
        titleTimer = 0.0f;
        spacePressed = false;
    }
}
