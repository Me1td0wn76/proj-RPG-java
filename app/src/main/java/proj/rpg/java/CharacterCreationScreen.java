package proj.rpg.java;

import org.lwjgl.glfw.GLFW;

/**
 * キャラクター作成画面クラス。
 * プレイヤー名を入力してキャラクターを作成。
 */
public class CharacterCreationScreen extends GameScreen {
    private StringBuilder playerName;
    private int maxNameLength = 12;
    private boolean enterPressed = false;

    public CharacterCreationScreen() {
        playerName = new StringBuilder();
    }

    @Override
    public void update(Input input) {
        // 文字入力（簡易版：A-Zのみ）
        for (int key = GLFW.GLFW_KEY_A; key <= GLFW.GLFW_KEY_Z; key++) {
            if (input.isKeyPressed(key) && playerName.length() < maxNameLength) {
                char c = (char) ('A' + (key - GLFW.GLFW_KEY_A));
                playerName.append(c);
                break; // 1フレームに1文字のみ
            }
        }

        // バックスペースで文字削除
        if (input.isKeyPressed(GLFW.GLFW_KEY_BACKSPACE) && playerName.length() > 0) {
            playerName.deleteCharAt(playerName.length() - 1);
        }

        // エンターキーで完了（名前が入力されている場合）
        if (input.isKeyPressed(GLFW.GLFW_KEY_ENTER) && !enterPressed && playerName.length() > 0) {
            enterPressed = true;
            transitionRequested = true;
        }
        if (!input.isKeyPressed(GLFW.GLFW_KEY_ENTER)) {
            enterPressed = false;
        }
    }

    @Override
    public void render(UIRenderer uiRenderer) {
        // 画面をクリア
        uiRenderer.clear(0.1f, 0.05f, 0.1f, 1.0f);

        // タイトル
        String title = "CHARACTER CREATION";
        float titleX = 960 / 2 - (title.length() * 12 * 1.5f) / 2;
        uiRenderer.drawText(title, titleX, 100, 1.5f, 0.9f, 0.9f, 0.9f, 1.0f);

        // 説明
        String instruction1 = "Enter your character's name:";
        float instr1X = 960 / 2 - (instruction1.length() * 12) / 2;
        uiRenderer.drawText(instruction1, instr1X, 200, 1.0f, 0.8f, 0.8f, 0.8f, 1.0f);

        // 名前入力ボックス
        float boxX = 300;
        float boxY = 250;
        float boxWidth = 360;
        float boxHeight = 40;

        // 入力ボックスの背景
        uiRenderer.drawRect(boxX, boxY, boxWidth, boxHeight, 0.2f, 0.2f, 0.2f, 0.8f);

        // 入力ボックスの枠線
        uiRenderer.drawRectWithBorder(boxX, boxY, boxWidth, boxHeight,
                0.0f, 0.0f, 0.0f, 0.0f,
                0.6f, 0.6f, 0.6f, 1.0f, 2.0f);

        // 入力された名前を表示
        String nameText = playerName.toString();
        if (nameText.isEmpty()) {
            nameText = "(enter name)";
            uiRenderer.drawText(nameText, boxX + 10, boxY + 12, 1.0f, 0.5f, 0.5f, 0.5f, 0.7f);
        } else {
            uiRenderer.drawText(nameText, boxX + 10, boxY + 12, 1.0f, 1.0f, 1.0f, 0.9f, 1.0f);
        }

        // カーソル点滅
        if (System.currentTimeMillis() % 1000 < 500) {
            float cursorX = boxX + 10 + nameText.length() * 12;
            uiRenderer.drawRect(cursorX, boxY + 10, 2, 20, 1.0f, 1.0f, 1.0f, 1.0f);
        }

        // 操作方法
        String instruction2 = "Use A-Z to type, BACKSPACE to delete, ENTER to confirm";
        float instr2X = 960 / 2 - (instruction2.length() * 10) / 2;
        uiRenderer.drawText(instruction2, instr2X, 350, 0.9f, 0.6f, 0.6f, 0.6f, 1.0f);

        // 確認ボタン（名前が入力されている場合）
        if (playerName.length() > 0) {
            String confirmText = "Press ENTER to create character";
            float confirmX = 960 / 2 - (confirmText.length() * 10) / 2;
            uiRenderer.drawText(confirmText, confirmX, 420, 1.0f, 0.8f, 1.0f, 0.8f, 1.0f);
        }

        // プレビュー情報
        if (playerName.length() > 0) {
            String preview = String.format("Character: %s", playerName.toString());
            float previewX = 960 / 2 - (preview.length() * 10) / 2;
            uiRenderer.drawText(preview, previewX, 480, 1.0f, 0.9f, 0.7f, 0.3f, 1.0f);
        }
    }

    @Override
    public void onEnter() {
        super.onEnter();
        playerName.setLength(0); // 名前をクリア
        enterPressed = false;
    }

    public String getPlayerName() {
        return playerName.toString();
    }
}
