package proj.rpg.java;

import org.lwjgl.glfw.GLFW;

/**
 * 設定画面クラス。
 * 音量、画面設定などを調整。
 */
public class SettingsScreen extends GameScreen {
    private GameSettings settings;
    private String[] settingItems = { "Master Volume", "SFX Volume", "Music Volume", "Back" };
    private int selectedIndex = 0;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;

    public SettingsScreen(GameSettings settings) {
        this.settings = settings;
    }

    @Override
    public void update(Input input) {
        // 上キーでメニュー項目を上に移動
        if (input.isKeyPressed(GLFW.GLFW_KEY_UP) && !upPressed) {
            upPressed = true;
            selectedIndex = (selectedIndex - 1 + settingItems.length) % settingItems.length;
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_UP)) {
            upPressed = false;
        }

        // 下キーでメニュー項目を下に移動
        if (input.isKeyPressed(GLFW.GLFW_KEY_DOWN) && !downPressed) {
            downPressed = true;
            selectedIndex = (selectedIndex + 1) % settingItems.length;
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
            downPressed = false;
        }

        // 左右キーで設定値を変更
        if (input.isKeyPressed(GLFW.GLFW_KEY_LEFT) && !leftPressed) {
            leftPressed = true;
            adjustSetting(-0.1f);
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
            leftPressed = false;
        }

        if (input.isKeyPressed(GLFW.GLFW_KEY_RIGHT) && !rightPressed) {
            rightPressed = true;
            adjustSetting(0.1f);
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
            rightPressed = false;
        }

        // スペースキーで戻る（Back選択時）
        if (input.isKeyPressed(GLFW.GLFW_KEY_SPACE) && !spacePressed) {
            spacePressed = true;
            if (selectedIndex == 3) { // Back
                transitionRequested = true;
            }
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            spacePressed = false;
        }
    }

    private void adjustSetting(float delta) {
        switch (selectedIndex) {
            case 0 -> settings.setMasterVolume(settings.getMasterVolume() + delta);
            case 1 -> settings.setSfxVolume(settings.getSfxVolume() + delta);
            case 2 -> settings.setMusicVolume(settings.getMusicVolume() + delta);
        }
    }

    @Override
    public void render(UIRenderer uiRenderer) {
        // 画面をクリア
        uiRenderer.clear(0.05f, 0.05f, 0.1f, 1.0f);

        // タイトル
        String title = "SETTINGS";
        float titleX = 960 / 2 - (title.length() * 12 * 1.5f) / 2;
        uiRenderer.drawText(title, titleX, 100, 1.5f, 0.9f, 0.9f, 0.9f, 1.0f);

        // 設定項目
        float menuStartY = 200;
        float menuSpacing = 60;

        for (int i = 0; i < settingItems.length; i++) {
            float y = menuStartY + i * menuSpacing;
            boolean selected = (i == selectedIndex);

            if (selected) {
                // 選択中の項目
                String cursor = "> ";
                uiRenderer.drawText(cursor, 200, y, 1.0f, 1.0f, 1.0f, 0.2f, 1.0f);

                uiRenderer.drawText(settingItems[i], 230, y, 1.0f, 1.0f, 1.0f, 0.8f, 1.0f);

                // 背景ハイライト
                uiRenderer.drawRect(190, y - 5, 580, 40, 0.2f, 0.2f, 0.4f, 0.6f);

                // 設定値を表示
                if (i < 3) {
                    float value = 0;
                    switch (i) {
                        case 0 -> value = settings.getMasterVolume();
                        case 1 -> value = settings.getSfxVolume();
                        case 2 -> value = settings.getMusicVolume();
                    }

                    // スライダー表示
                    float sliderX = 500;
                    float sliderWidth = 200;
                    float sliderHeight = 20;

                    // スライダーの背景
                    uiRenderer.drawRect(sliderX, y + 5, sliderWidth, sliderHeight, 0.3f, 0.3f, 0.3f, 0.8f);

                    // スライダーの値部分
                    float valueWidth = sliderWidth * value;
                    uiRenderer.drawRect(sliderX, y + 5, valueWidth, sliderHeight, 0.5f, 0.7f, 0.9f, 0.9f);

                    // 値をテキストで表示
                    String valueText = String.format("%.0f%%", value * 100);
                    uiRenderer.drawText(valueText, sliderX + sliderWidth + 20, y, 1.0f, 0.9f, 0.9f, 0.9f, 1.0f);
                }
            } else {
                // 通常の項目
                uiRenderer.drawText(settingItems[i], 230, y, 1.0f, 0.7f, 0.7f, 0.7f, 1.0f);
            }
        }

        // 操作方法の説明
        String instruction = "UP/DOWN: Navigate  LEFT/RIGHT: Adjust  SPACE: Back";
        float instrX = 960 / 2 - (instruction.length() * 10) / 2;
        uiRenderer.drawText(instruction, instrX, 480, 0.9f, 0.6f, 0.6f, 0.6f, 1.0f);
    }

    @Override
    public void onEnter() {
        super.onEnter();
        selectedIndex = 0;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        spacePressed = false;
    }
}
