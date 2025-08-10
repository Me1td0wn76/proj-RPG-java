package proj.rpg.java;

import org.lwjgl.glfw.GLFW;

/**
 * キャラクター作成画面クラス。
 * プレイヤー名入力、役職選択、スキルポイント割り振りを行う。
 */
public class CharacterCreationScreen extends GameScreen {
    // 作成ステップ
    private enum CreationStep {
        NAME_INPUT, // 名前入力
        JOB_SELECTION, // 役職選択
        SKILL_ALLOCATION // スキルポイント割り振り
    }

    private CreationStep currentStep = CreationStep.NAME_INPUT;

    // 名前入力関連
    private StringBuilder playerName;
    private int maxNameLength = 12;

    // 役職選択関連
    private String[] jobNames = { "Warrior", "Mage", "Priest", "Thief", "Ranger" };
    private String[] jobNamesJP = { "戦士", "魔法使い", "僧侶", "盗賊", "レンジャー" };
    private String[] jobDescriptions = {
            "High HP and physical attack power",
            "Strong magic power, low HP",
            "Healing magic and support spells",
            "High agility and critical hit rate",
            "Balanced stats and archery skills"
    };
    private String[] jobDescriptionsJP = {
            "高いHPと物理攻撃力を持つ",
            "強力な魔法力を持つが、HPは低い",
            "回復魔法と補助魔法が得意",
            "高い素早さとクリティカル率を持つ",
            "バランス型で弓術に長ける"
    };
    private int selectedJobIndex = 0;

    // スキルポイント割り振り関連
    private String[] skillNames = { "STR", "INT", "VIT", "AGI", "LUK" };
    private String[] skillNamesJP = { "力", "知性", "体力", "敏捷", "運" };
    private int[] baseStats = new int[5]; // 役職による基本ステータス
    private int[] allocatedPoints = new int[5]; // 割り振りポイント
    private int availablePoints = 20; // 使用可能ポイント
    private int selectedSkillIndex = 0;

    // 入力制御
    private InputController inputController = new InputController();

    public CharacterCreationScreen() {
        playerName = new StringBuilder();
    }

    @Override
    public void update(Input input) {
        switch (currentStep) {
            case NAME_INPUT:
                updateNameInput(input);
                break;
            case JOB_SELECTION:
                updateJobSelection(input);
                break;
            case SKILL_ALLOCATION:
                updateSkillAllocation(input);
                break;
        }
    }

    private void updateNameInput(Input input) {
        // 文字入力（A-Zのみ）InputControllerを使用
        for (int key = GLFW.GLFW_KEY_A; key <= GLFW.GLFW_KEY_Z; key++) {
            final int currentKey = key; // lambdaで使用するためfinalにする
            inputController.handleKeyInputFast(input, key, () -> {
                if (playerName.length() < maxNameLength) {
                    char c = (char) ('A' + (currentKey - GLFW.GLFW_KEY_A));
                    playerName.append(c);
                }
            });
        }

        // バックスペースで文字削除（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_BACKSPACE, () -> {
            if (playerName.length() > 0) {
                playerName.deleteCharAt(playerName.length() - 1);
            }
        });

        // エンターキーで次のステップへ（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_ENTER, () -> {
            if (playerName.length() > 0) {
                currentStep = CreationStep.JOB_SELECTION;
                initializeJobSelection();
            }
        });
    }

    private void updateJobSelection(Input input) {
        // 上下キーで役職選択（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_UP, () -> {
            selectedJobIndex = (selectedJobIndex - 1 + jobNames.length) % jobNames.length;
        });

        inputController.handleKeyInput(input, GLFW.GLFW_KEY_DOWN, () -> {
            selectedJobIndex = (selectedJobIndex + 1) % jobNames.length;
        });

        // エンターキーで次のステップへ（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_ENTER, () -> {
            currentStep = CreationStep.SKILL_ALLOCATION;
            initializeSkillAllocation();
        });

        // ESCキーで前のステップに戻る（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_ESCAPE, () -> {
            currentStep = CreationStep.NAME_INPUT;
        });
    }

    private void updateSkillAllocation(Input input) {
        // 上下キーでスキル選択（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_UP, () -> {
            selectedSkillIndex = (selectedSkillIndex - 1 + skillNames.length) % skillNames.length;
        });

        inputController.handleKeyInput(input, GLFW.GLFW_KEY_DOWN, () -> {
            selectedSkillIndex = (selectedSkillIndex + 1) % skillNames.length;
        });

        // 左右キーでポイント増減（連続入力対応）
        inputController.handleKeyInputRepeatable(input, GLFW.GLFW_KEY_LEFT, () -> {
            if (allocatedPoints[selectedSkillIndex] > 0) {
                allocatedPoints[selectedSkillIndex]--;
                availablePoints++;
            }
        });

        inputController.handleKeyInputRepeatable(input, GLFW.GLFW_KEY_RIGHT, () -> {
            if (availablePoints > 0) {
                allocatedPoints[selectedSkillIndex]++;
                availablePoints--;
            }
        });

        // エンターキーでキャラクター作成完了（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_ENTER, () -> {
            transitionRequested = true;
        });

        // ESCキーで前のステップに戻る（InputControllerを使用）
        inputController.handleKeyInput(input, GLFW.GLFW_KEY_ESCAPE, () -> {
            currentStep = CreationStep.JOB_SELECTION;
        });
    }

    private void initializeJobSelection() {
        selectedJobIndex = 0;
    }

    private void initializeSkillAllocation() {
        // 役職による基本ステータス設定
        switch (selectedJobIndex) {
            case 0: // Warrior
                baseStats = new int[] { 15, 8, 14, 10, 8 };
                break;
            case 1: // Mage
                baseStats = new int[] { 8, 16, 9, 11, 11 };
                break;
            case 2: // Priest
                baseStats = new int[] { 10, 13, 12, 9, 11 };
                break;
            case 3: // Thief
                baseStats = new int[] { 11, 10, 10, 16, 8 };
                break;
            case 4: // Ranger
                baseStats = new int[] { 12, 11, 11, 13, 8 };
                break;
        }

        // 割り振りポイントをリセット
        allocatedPoints = new int[5];
        availablePoints = 20;
        selectedSkillIndex = 0;
    }

    @Override
    public void render(UIRenderer uiRenderer) {
        // 画面をクリア
        uiRenderer.clear(0.1f, 0.05f, 0.1f, 1.0f);

        switch (currentStep) {
            case NAME_INPUT:
                renderNameInput(uiRenderer);
                break;
            case JOB_SELECTION:
                renderJobSelection(uiRenderer);
                break;
            case SKILL_ALLOCATION:
                renderSkillAllocation(uiRenderer);
                break;
        }
    }

    private void renderNameInput(UIRenderer uiRenderer) {
        // タイトル
        uiRenderer.drawHighQualityCenteredText("CHARACTER CREATION", 480, 80, 1.8f, 0.9f, 0.9f, 0.9f, 1.0f);
        uiRenderer.drawHighQualityCenteredText("キャラクター作成", 480, 110, 1.2f, 0.8f, 0.8f, 0.8f, 1.0f);

        // ステップ表示
        uiRenderer.drawHighQualityCenteredText("Step 1/3: Name Input", 480, 150, 1.0f, 0.7f, 0.7f, 0.7f, 1.0f);

        // 説明
        uiRenderer.drawHighQualityCenteredText("Enter your character's name:", 480, 200, 1.0f, 0.8f, 0.8f, 0.8f, 1.0f);
        uiRenderer.drawHighQualityCenteredText("キャラクターの名前を入力してください:", 480, 220, 0.9f, 0.7f, 0.7f, 0.7f, 1.0f);

        // 名前入力ボックス
        float boxX = 300;
        float boxY = 260;
        float boxWidth = 360;
        float boxHeight = 40;

        uiRenderer.drawRect(boxX, boxY, boxWidth, boxHeight, 0.2f, 0.2f, 0.2f, 0.8f);
        uiRenderer.drawRectWithBorder(boxX, boxY, boxWidth, boxHeight,
                0.0f, 0.0f, 0.0f, 0.0f, 0.6f, 0.6f, 0.6f, 1.0f, 2.0f);

        // 入力された名前を表示
        String nameText = playerName.toString();
        if (nameText.isEmpty()) {
            uiRenderer.drawHighQualityText("(enter name)", boxX + 10, boxY + 12, 1.0f, 0.5f, 0.5f, 0.5f, 0.7f);
        } else {
            uiRenderer.drawHighQualityText(nameText, boxX + 10, boxY + 12, 1.0f, 1.0f, 1.0f, 0.9f, 1.0f);
        }

        // カーソル点滅
        if (System.currentTimeMillis() % 1000 < 500) {
            float cursorX = boxX + 10 + nameText.length() * 12;
            uiRenderer.drawRect(cursorX, boxY + 10, 2, 20, 1.0f, 1.0f, 1.0f, 1.0f);
        }

        // 操作説明
        uiRenderer.drawHighQualityCenteredText("A-Z to type, BACKSPACE to delete, ENTER to continue", 480, 340, 0.9f,
                0.6f, 0.6f, 0.6f, 1.0f);
    }

    private void renderJobSelection(UIRenderer uiRenderer) {
        // タイトル
        uiRenderer.drawHighQualityCenteredText("CHARACTER CREATION", 480, 80, 1.8f, 0.9f, 0.9f, 0.9f, 1.0f);
        uiRenderer.drawHighQualityCenteredText("キャラクター作成", 480, 110, 1.2f, 0.8f, 0.8f, 0.8f, 1.0f);

        // ステップ表示
        uiRenderer.drawHighQualityCenteredText("Step 2/3: Job Selection", 480, 150, 1.0f, 0.7f, 0.7f, 0.7f, 1.0f);

        // 名前表示
        uiRenderer.drawHighQualityCenteredText("Name: " + playerName.toString(), 480, 180, 1.0f, 0.8f, 0.8f, 0.8f,
                1.0f);

        // 職業リスト
        for (int i = 0; i < jobNames.length; i++) {
            float y = 220 + i * 40;
            float r = 0.8f, g = 0.8f, b = 0.8f, a = 1.0f;

            if (i == selectedJobIndex) {
                r = 1.0f;
                g = 1.0f;
                b = 0.0f; // 選択中は黄色
                // 選択背景
                uiRenderer.drawRect(50, y - 5, 860, 30, 0.2f, 0.2f, 0.0f, 0.5f);
            }

            // 職業名（英語・日本語）
            uiRenderer.drawHighQualityText(jobNames[i] + " (" + jobNamesJP[i] + ")", 60, y, 1.0f, r, g, b, a);

            // 説明
            uiRenderer.drawHighQualityText(jobDescriptions[i], 60, y + 15, 0.8f, r * 0.8f, g * 0.8f, b * 0.8f,
                    a * 0.8f);
            uiRenderer.drawHighQualityText(jobDescriptionsJP[i], 60, y + 28, 0.8f, r * 0.7f, g * 0.7f, b * 0.7f,
                    a * 0.7f);
        }

        // 操作説明
        uiRenderer.drawHighQualityCenteredText("↑↓ to select, ENTER to continue, ESC to go back", 480, 460, 0.9f, 0.6f,
                0.6f, 0.6f, 1.0f);
    }

    private void renderSkillAllocation(UIRenderer uiRenderer) {
        // タイトル
        uiRenderer.drawHighQualityCenteredText("CHARACTER CREATION", 480, 50, 1.8f, 0.9f, 0.9f, 0.9f, 1.0f);
        uiRenderer.drawHighQualityCenteredText("キャラクター作成", 480, 75, 1.2f, 0.8f, 0.8f, 0.8f, 1.0f);

        // ステップ表示
        uiRenderer.drawHighQualityCenteredText("Step 3/3: Skill Point Allocation", 480, 105, 1.0f, 0.7f, 0.7f, 0.7f,
                1.0f);

        // キャラクター情報
        String characterInfo = String.format("Name: %s | Job: %s (%s)",
                playerName.toString(), jobNames[selectedJobIndex], jobNamesJP[selectedJobIndex]);
        uiRenderer.drawHighQualityCenteredText(characterInfo, 480, 130, 1.0f, 0.8f, 0.8f, 0.8f, 1.0f);

        // 利用可能ポイント表示
        uiRenderer.drawHighQualityCenteredText("Available Points: " + availablePoints, 480, 160, 1.2f, 1.0f, 1.0f, 0.0f,
                1.0f);

        // スキルリスト
        uiRenderer.drawHighQualityText("Skill", 100, 190, 1.0f, 0.9f, 0.9f, 0.9f, 1.0f);
        uiRenderer.drawHighQualityText("Base", 200, 190, 1.0f, 0.9f, 0.9f, 0.9f, 1.0f);
        uiRenderer.drawHighQualityText("Bonus", 280, 190, 1.0f, 0.9f, 0.9f, 0.9f, 1.0f);
        uiRenderer.drawHighQualityText("Total", 360, 190, 1.0f, 0.9f, 0.9f, 0.9f, 1.0f);

        for (int i = 0; i < skillNames.length; i++) {
            float y = 220 + i * 30;
            float r = 0.8f, g = 0.8f, b = 0.8f, a = 1.0f;

            if (i == selectedSkillIndex) {
                r = 1.0f;
                g = 1.0f;
                b = 0.0f; // 選択中は黄色
                // 選択背景
                uiRenderer.drawRect(50, y - 3, 400, 25, 0.2f, 0.2f, 0.0f, 0.5f);
            }

            // スキル名
            String skillDisplay = String.format("%s (%s)", skillNames[i], skillNamesJP[i]);
            uiRenderer.drawHighQualityText(skillDisplay, 60, y, 1.0f, r, g, b, a);

            // 基本値
            uiRenderer.drawHighQualityText(String.valueOf(baseStats[i]), 200, y, 1.0f, r, g, b, a);

            // ボーナス値
            String bonusText = "+" + allocatedPoints[i];
            uiRenderer.drawHighQualityText(bonusText, 280, y, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f);

            // 合計値
            int totalValue = baseStats[i] + allocatedPoints[i];
            uiRenderer.drawHighQualityText(String.valueOf(totalValue), 360, y, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f);
        }

        // 操作説明
        uiRenderer.drawHighQualityCenteredText("↑↓ to select skill, ←→ to allocate points", 480, 420, 0.9f, 0.6f, 0.6f,
                0.6f, 1.0f);
        uiRenderer.drawHighQualityCenteredText("ENTER to create character, ESC to go back", 480, 445, 0.9f, 0.6f, 0.6f,
                0.6f, 1.0f);
    }

    @Override
    public void onEnter() {
        super.onEnter();
        currentStep = CreationStep.NAME_INPUT;
        playerName.setLength(0); // 名前をクリア
        selectedJobIndex = 0;
        selectedSkillIndex = 0;
        availablePoints = 20;
        allocatedPoints = new int[5];
        inputController.reset(); // 入力状態をリセット
    }

    public String getPlayerName() {
        return playerName.toString();
    }

    public String getJobName() {
        return jobNames[selectedJobIndex];
    }

    public int[] getFinalStats() {
        int[] finalStats = new int[5];
        for (int i = 0; i < 5; i++) {
            finalStats[i] = baseStats[i] + allocatedPoints[i];
        }
        return finalStats;
    }
}
