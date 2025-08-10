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
        String titleTextJP = "ダンジョンエクスプロ-ラー";
        float titleX = 960 / 2 - (titleText.length() * 12 * 2.0f) / 2; // 中央揃え
        float titleXJP = 960 / 2 - (titleTextJP.length() * 12 * 1.5f) / 2;

        uiRenderer.drawText(titleText, titleX, 180, 2.0f, 1.0f, 1.0f, 0.8f, 1.0f);
        uiRenderer.drawText(titleTextJP, titleXJP, 220, 1.5f, 0.9f, 0.9f, 0.7f, 1.0f);

        // サブタイトル
        String subtitleText = "A Wizardry-Style 3D RPG";
        String subtitleTextJP = "ウィザードリィふうの3DRPGゲーム";
        float subtitleX = 960 / 2 - (subtitleText.length() * 12) / 2;
        float subtitleXJP = 960 / 2 - (subtitleTextJP.length() * 12 * 0.8f) / 2;

        uiRenderer.drawText(subtitleText, subtitleX, 280, 1.0f, 0.8f, 0.8f, 0.6f, 1.0f);
        uiRenderer.drawText(subtitleTextJP, subtitleXJP, 300, 0.8f, 0.7f, 0.7f, 0.5f, 1.0f);

        // 点滅する「Press SPACE」メッセージ
        float blinkAlpha = (float) (Math.sin(titleTimer * 3.0) * 0.5 + 0.5);
        String pressText = "Press SPACE to continue";
        String pressTextJP = "SPACEキーでつづける";
        float pressX = 960 / 2 - (pressText.length() * 12) / 2;
        float pressXJP = 960 / 2 - (pressTextJP.length() * 12 * 1.2f) / 2;

        uiRenderer.drawText(pressText, pressX, 400, 1.0f, 1.0f, 1.0f, 1.0f, blinkAlpha);
        uiRenderer.drawText(pressTextJP, pressXJP, 430, 1.2f, 1.0f, 1.0f, 0.8f, blinkAlpha);

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
