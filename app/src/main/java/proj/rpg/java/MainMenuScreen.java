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
        uiRenderer.clear(0.05f, 0.05f, 0.2f, 1.0f);
        String title = "MAIN MENU";
        uiRenderer.drawHighQualityCenteredText(title, 960 / 2, 80, 1.5f, 1.0f, 1.0f, 0.8f, 1.0f);
        uiRenderer.drawHighQualityCenteredText("メインメニュー", 960 / 2, 120, 1.2f, 0.9f, 0.9f, 0.7f, 1.0f);
        for (int i = 0; i < menuItems.length; i++) {
            float y = 200 + i * 60;
            float r = (i == selectedIndex) ? 1.0f : 0.8f;
            float g = (i == selectedIndex) ? 1.0f : 0.8f;
            float b = (i == selectedIndex) ? 0.0f : 0.8f;
            float a = 1.0f;
            uiRenderer.drawHighQualityCenteredText(menuItemsJP[i] + " / " + menuItems[i], 960 / 2, y, 1.2f, r, g, b, a);
        }
        String instruction = "↑↓で選択、SPACEで決定";
        uiRenderer.drawHighQualityCenteredText(instruction, 960 / 2, 500, 0.9f, 0.7f, 0.7f, 0.7f, 1.0f);
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
