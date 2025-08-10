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

        // 英語タイトル（パターンベース描画）
        uiRenderer.drawCenteredText(titleText, 960 / 2, 180, 2.0f, 1.0f, 1.0f, 0.8f, 1.0f);

        // 日本語タイトル（高品質テクスチャベース描画）
        uiRenderer.drawHighQualityCenteredText(titleTextJP, 960 / 2, 220, 1.5f, 0.9f, 0.9f, 0.7f, 1.0f);

        // サブタイトル
        String subtitleText = "A Wizardry-Style 3D RPG";
        String subtitleTextJP = "ウィザードリィふうの3DRPGゲーム";

        // 英語サブタイトル（パターンベース描画）
        uiRenderer.drawCenteredText(subtitleText, 960 / 2, 280, 1.0f, 0.8f, 0.8f, 0.6f, 1.0f);

        // 日本語サブタイトル（高品質テクスチャベース描画）
        uiRenderer.drawHighQualityCenteredText(subtitleTextJP, 960 / 2, 300, 0.8f, 0.7f, 0.7f, 0.5f, 1.0f);

        // 点滅する「Press SPACE」メッセージ
        float blinkAlpha = (float) (Math.sin(titleTimer * 3.0) * 0.5 + 0.5);
        String pressText = "Press SPACE to continue";
        String pressTextJP = "SPACEキーでつづける";

        // 英語（パターンベース描画）
        uiRenderer.drawCenteredText(pressText, 960 / 2, 400, 1.0f, 1.0f, 1.0f, 1.0f, blinkAlpha);

        // 日本語（高品質テクスチャベース描画）
        uiRenderer.drawHighQualityCenteredText(pressTextJP, 960 / 2, 430, 1.2f, 1.0f, 1.0f, 0.8f, blinkAlpha);

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
