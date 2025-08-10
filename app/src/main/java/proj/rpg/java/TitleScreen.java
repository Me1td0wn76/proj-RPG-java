package proj.rpg.java;

import org.lwjgl.glfw.GLFW;

/**
 * タイトル画面クラス。
 * ゲーム開始時の最初の画面。
 */
public class TitleScreen extends GameScreen {
    private float titleTimer = 0.0f;
    private InputController inputController = new InputController();

    @Override
    public void update(Input input) {
        titleTimer += 0.016f; // 60FPSでの時間更新

        // スペースキーでメニューに進む（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_SPACE, () -> {
            transitionRequested = true;
        });
    }

    @Override
    public void render(UIRenderer uiRenderer) {
        uiRenderer.clear(0.1f, 0.1f, 0.3f, 1.0f);

        // タイトルテキスト
        String titleText = "DUNGEON EXPLORER";
        String titleTextJP = "ダンジョンエクスプロ-ラー";
        uiRenderer.drawHighQualityCenteredText(titleText, 960 / 2, 180, 2.0f, 1.0f, 1.0f, 0.8f, 1.0f);
        uiRenderer.drawHighQualityCenteredText(titleTextJP, 960 / 2, 220, 1.5f, 0.9f, 0.9f, 0.7f, 1.0f);

        // サブタイトル
        String subtitleText = "A Wizardry-Style 3D RPG";
        String subtitleTextJP = "ウィザードリィふうの3DRPGゲーム";
        uiRenderer.drawHighQualityCenteredText(subtitleText, 960 / 2, 280, 1.0f, 0.8f, 0.8f, 0.6f, 1.0f);
        uiRenderer.drawHighQualityCenteredText(subtitleTextJP, 960 / 2, 300, 0.8f, 0.7f, 0.7f, 0.5f, 1.0f);

        // 点滅する「Press SPACE」メッセージ
        float blinkAlpha = (float) (Math.sin(titleTimer * 3.0) * 0.5 + 0.5);
        String pressText = "Press SPACE to continue";
        String pressTextJP = "SPACEキーでつづける";
        uiRenderer.drawHighQualityCenteredText(pressText, 960 / 2, 400, 1.0f, 1.0f, 1.0f, 1.0f, blinkAlpha);
        uiRenderer.drawHighQualityCenteredText(pressTextJP, 960 / 2, 430, 1.2f, 1.0f, 1.0f, 0.8f, blinkAlpha);

        // バージョン情報
        String versionText = "v1.0.0";
        uiRenderer.drawHighQualityText(versionText, 10, 540 - 30, 0.8f, 0.5f, 0.5f, 0.5f, 1.0f);
    }

    @Override
    public void onEnter() {
        super.onEnter();
        titleTimer = 0.0f;
        inputController.reset(); // 入力状態をリセット
    }
}
