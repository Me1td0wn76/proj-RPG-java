package proj.rpg.java;

import org.lwjgl.glfw.GLFW;

/**
 * メインメニュー画面クラス。
 * ニューゲーム、コンティニュー、設定、終了のメニューを表示。
 */
public class MainMenuScreen extends GameScreen {
    private String[] menuItems = { "New Game", "Continue", "Settings", "Exit" };
    private int selectedIndex = 0;
    private String selectedOption = "";

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean spacePressed = false;

    @Override
    public void update(Input input) {
        // 上キーでメニュー項目を上に移動
        if (input.isKeyPressed(GLFW.GLFW_KEY_UP) && !upPressed) {
            upPressed = true;
            selectedIndex = (selectedIndex - 1 + menuItems.length) % menuItems.length;
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_UP)) {
            upPressed = false;
        }

        // 下キーでメニュー項目を下に移動
        if (input.isKeyPressed(GLFW.GLFW_KEY_DOWN) && !downPressed) {
            downPressed = true;
            selectedIndex = (selectedIndex + 1) % menuItems.length;
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
            downPressed = false;
        }

        // スペースキーで選択実行
        if (input.isKeyPressed(GLFW.GLFW_KEY_SPACE) && !spacePressed) {
            spacePressed = true;

            switch (selectedIndex) {
                case 0 -> selectedOption = "START_NEW";
                case 1 -> selectedOption = "CONTINUE";
                case 2 -> selectedOption = "SETTINGS";
                case 3 -> selectedOption = "EXIT";
            }

            transitionRequested = true;
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            spacePressed = false;
        }
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
                // 選択中の項目は背景とカーソルを表示
                String cursor = "> ";
                float cursorX = 300;
                uiRenderer.drawText(cursor, cursorX, y, 1.0f, 1.0f, 1.0f, 0.2f, 1.0f);

                float itemX = cursorX + 30;
                uiRenderer.drawText(menuItems[i], itemX, y, 1.0f, 1.0f, 1.0f, 0.8f, 1.0f);

                // 背景ハイライト
                uiRenderer.drawRect(cursorX - 10, y - 5, 300, 30, 0.2f, 0.2f, 0.4f, 0.6f);
            } else {
                // 通常の項目
                float itemX = 330;
                uiRenderer.drawText(menuItems[i], itemX, y, 1.0f, 0.7f, 0.7f, 0.7f, 1.0f);
            }
        }

        // 操作方法の説明
        String instruction = "Use UP/DOWN arrows to navigate, SPACE to select";
        float instrX = 960 / 2 - (instruction.length() * 8) / 2;
        uiRenderer.drawText(instruction, instrX, 480, 0.8f, 0.5f, 0.5f, 0.5f, 1.0f);
    }

    @Override
    public void onEnter() {
        super.onEnter();
        selectedIndex = 0;
        selectedOption = "";
        upPressed = false;
        downPressed = false;
        spacePressed = false;
    }

    public String getSelectedOption() {
        return selectedOption;
    }
}
