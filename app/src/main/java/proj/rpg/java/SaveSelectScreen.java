package proj.rpg.java;

import org.lwjgl.glfw.GLFW;

/**
 * セーブスロット選択画面クラス。
 * 10個のセーブスロットから選択または新規作成。
 */
public class SaveSelectScreen extends GameScreen {
    private SaveDataManager saveDataManager;
    private int selectedSlot = 0;
    private boolean isNewGame = false;
    private SaveDataManager.SaveData[] saveSlots;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean spacePressed = false;

    public SaveSelectScreen(SaveDataManager saveDataManager) {
        this.saveDataManager = saveDataManager;
        this.saveSlots = new SaveDataManager.SaveData[10];
    }

    @Override
    public void update(Input input) {
        // 上キーでスロット選択を上に移動
        if (input.isKeyPressed(GLFW.GLFW_KEY_UP) && !upPressed) {
            upPressed = true;
            selectedSlot = (selectedSlot - 1 + 10) % 10;
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_UP)) {
            upPressed = false;
        }

        // 下キーでスロット選択を下に移動
        if (input.isKeyPressed(GLFW.GLFW_KEY_DOWN) && !downPressed) {
            downPressed = true;
            selectedSlot = (selectedSlot + 1) % 10;
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
            downPressed = false;
        }

        // スペースキーで選択実行
        if (input.isKeyPressed(GLFW.GLFW_KEY_SPACE) && !spacePressed) {
            spacePressed = true;
            isNewGame = (saveSlots[selectedSlot] == null);
            transitionRequested = true;
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            spacePressed = false;
        }
    }

    @Override
    public void render(UIRenderer uiRenderer) {
        // 画面をクリア
        uiRenderer.clear(0.05f, 0.1f, 0.05f, 1.0f);

        // タイトル
        String title = "SELECT SAVE SLOT";
        float titleX = 960 / 2 - (title.length() * 12 * 1.5f) / 2;
        uiRenderer.drawText(title, titleX, 50, 1.5f, 0.9f, 0.9f, 0.9f, 1.0f);

        // セーブスロットリスト
        float slotStartY = 120;
        float slotSpacing = 40;

        for (int i = 0; i < 10; i++) {
            float y = slotStartY + i * slotSpacing;
            boolean selected = (i == selectedSlot);

            String slotText;
            SaveDataManager.SaveData data = saveSlots[i];

            if (data != null) {
                // 既存データ
                slotText = String.format("Slot %d: %s (Level %d) - %s",
                        i + 1, data.playerName, data.playerLevel, data.lastPlayedAt);
            } else {
                // 空きスロット
                slotText = String.format("Slot %d: [Empty]", i + 1);
            }

            if (selected) {
                // カーソル表示
                String cursor = "> ";
                uiRenderer.drawText(cursor, 50, y, 1.0f, 1.0f, 1.0f, 0.2f, 1.0f);

                // 選択中の項目を明るく表示
                uiRenderer.drawText(slotText, 80, y, 1.0f, 1.0f, 1.0f, 0.8f, 1.0f);

                // 背景ハイライト
                uiRenderer.drawRect(40, y - 5, 880, 30, 0.2f, 0.4f, 0.2f, 0.6f);
            } else {
                // 通常表示
                if (data != null) {
                    uiRenderer.drawText(slotText, 80, y, 1.0f, 0.8f, 0.8f, 0.6f, 1.0f);
                } else {
                    uiRenderer.drawText(slotText, 80, y, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f);
                }
            }
        }

        // 操作方法の説明
        String instruction = "Use UP/DOWN arrows to navigate, SPACE to select";
        float instrX = 960 / 2 - (instruction.length() * 8) / 2;
        uiRenderer.drawText(instruction, instrX, 500, 0.8f, 0.6f, 0.6f, 0.6f, 1.0f);
    }

    @Override
    public void onEnter() {
        super.onEnter();

        // セーブデータをロード
        for (int i = 0; i < 10; i++) {
            saveSlots[i] = saveDataManager.loadGame(i);
        }

        selectedSlot = 0;
        isNewGame = false;
        upPressed = false;
        downPressed = false;
        spacePressed = false;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public boolean isNewGame() {
        return isNewGame;
    }
}
