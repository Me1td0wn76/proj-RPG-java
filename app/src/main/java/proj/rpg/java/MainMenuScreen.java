package proj.rpg.java;

import org.lwjgl.glfw.GLFW;

/**
 * メインメニュー画面クラス。
 * ニューゲーム、コンティニュー、設定、終了のメニューを表示。
 */
public class MainMenuScreen extends GameScreen {
    private String[] menuItems = { "New Game", "Continue", "Settings", "Exit" };
    private String[] menuItemsJP = { "あたらしいゲーム", "つづきから", "せってい", "しゅうりょう" };
    private int selectedIndex = 0;
    private String selectedOption = "";
    private float selectionTimer = 0.0f; // アニメーション用タイマー

    private InputController inputController = new InputController();

    @Override
    public void update(Input input) {
        selectionTimer += 0.016f; // 60FPSでの時間更新

        // 上キーでメニュー項目を上に移動（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_UP, () -> {
            selectedIndex = (selectedIndex - 1 + menuItems.length) % menuItems.length;
            selectionTimer = 0.0f; // 選択変更時にタイマーリセット
        });

        // 下キーでメニュー項目を下に移動（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_DOWN, () -> {
            selectedIndex = (selectedIndex + 1) % menuItems.length;
            selectionTimer = 0.0f; // 選択変更時にタイマーリセット
        });

        // スペースキーで選択実行（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_SPACE, () -> {
            switch (selectedIndex) {
                case 0 -> selectedOption = "START_NEW";
                case 1 -> selectedOption = "CONTINUE";
                case 2 -> selectedOption = "SETTINGS";
                case 3 -> selectedOption = "EXIT";
            }
            transitionRequested = true;
        });
    }

    @Override
    public void render(UIRenderer uiRenderer) {
        // 画面をクリア（暗い色）
        uiRenderer.clear(0.05f, 0.05f, 0.2f, 1.0f);

        // タイトル
        String title = "MAIN MENU";
        float titleX = 960 / 2 - (title.length() * 12 * 1.5f) / 2;
        uiRenderer.drawText(title, titleX, 150, 1.5f, 0.9f, 0.9f, 0.9f, 1.0f);

        // メニュー項目
        float menuStartY = 250;
        float menuSpacing = 40;

        for (int i = 0; i < menuItems.length; i++) {
            float y = menuStartY + i * menuSpacing;
            boolean selected = (i == selectedIndex);

            if (selected) {
                // アニメーション効果を追加
                float pulseScale = 1.0f + (float) (Math.sin(selectionTimer * 4.0) * 0.1);
                float glowAlpha = (float) (Math.sin(selectionTimer * 3.0) * 0.3 + 0.7);

                // より大きく明確な背景ハイライト（脈打つ効果）
                uiRenderer.drawRect(280, y - 8, 400, 36, 0.3f * glowAlpha, 0.3f * glowAlpha, 0.6f * glowAlpha, 0.9f);

                // 左右のカーソル矢印（脈打つ効果）
                String leftCursor = ">";
                String rightCursor = "<";
                uiRenderer.drawText(leftCursor, 250, y, 1.2f * pulseScale, 1.0f, 1.0f, 0.3f, glowAlpha);
                uiRenderer.drawText(rightCursor, 700, y, 1.2f * pulseScale, 1.0f, 1.0f, 0.3f, glowAlpha);

                // 選択中の項目（英語と日本語両方表示）
                float itemX = 320;
                uiRenderer.drawText(menuItems[i], itemX, y - 10, 1.0f, 0.8f, 0.8f, 0.8f, 1.0f);
                uiRenderer.drawText(menuItemsJP[i], itemX, y + 5, 1.2f * pulseScale, 1.0f, 1.0f, 0.9f, glowAlpha);

                // 追加の装飾線（グロー効果）
                uiRenderer.drawRect(290, y - 12, 380, 3, 0.8f, 0.8f, 0.2f, glowAlpha);
                uiRenderer.drawRect(290, y + 30, 380, 3, 0.8f, 0.8f, 0.2f, glowAlpha);
            } else {
                // 通常の項目
                float itemX = 350;
                uiRenderer.drawText(menuItems[i], itemX, y, 0.9f, 0.5f, 0.5f, 0.5f, 0.8f);
            }
        }

        // 操作方法の説明（日本語）
        String instruction = "じょうげキーでせんたく、SPACEでけってい";
        float instrX = 960 / 2 - (instruction.length() * 8) / 2;
        uiRenderer.drawText(instruction, instrX, 500, 0.8f, 0.5f, 0.5f, 0.5f, 1.0f);

        // 現在の選択項目を上部に表示
        String currentSelection = "げんざいのせんたく: " + menuItemsJP[selectedIndex];
        float selX = 960 / 2 - (currentSelection.length() * 10) / 2;
        uiRenderer.drawText(currentSelection, selX, 100, 1.0f, 0.7f, 0.9f, 0.7f, 1.0f);
    }

    @Override
    public void onEnter() {
        super.onEnter();
        selectedIndex = 0;
        selectedOption = "";
        selectionTimer = 0.0f;
        inputController.reset(); // 入力状態をリセット
    }

    public String getSelectedOption() {
        return selectedOption;
    }
}
