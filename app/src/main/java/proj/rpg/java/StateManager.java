package proj.rpg.java;

/**
 * ゲーム状態の管理と画面遷移を行うクラス。
 * 各ゲーム画面のライフサイクルを管理。
 */
public class StateManager {
    private GameState currentState;
    private GameState previousState;

    // 各画面のクラス
    private TitleScreen titleScreen;
    private MainMenuScreen mainMenuScreen;
    private CharacterCreationScreen characterCreationScreen;
    private SaveSelectScreen saveSelectScreen;
    private SettingsScreen settingsScreen;
    private FieldScreen fieldScreen;

    // 共有データ
    private SaveDataManager saveDataManager;
    private GameSettings settings;
    private UIRenderer uiRenderer;
    private Input input;

    // 画面遷移用データ
    private SaveDataManager.SaveData currentSaveData;
    private int selectedSaveSlot = -1;

    public StateManager(UIRenderer uiRenderer, Input input) {
        this.uiRenderer = uiRenderer;
        this.input = input;
        this.saveDataManager = new SaveDataManager();
        this.settings = new GameSettings();

        // 各画面を初期化
        this.titleScreen = new TitleScreen();
        this.mainMenuScreen = new MainMenuScreen();
        this.characterCreationScreen = new CharacterCreationScreen();
        this.saveSelectScreen = new SaveSelectScreen(saveDataManager);
        this.settingsScreen = new SettingsScreen(settings);
        this.fieldScreen = new FieldScreen();

        // 初期状態はタイトル画面
        currentState = GameState.TITLE;
        previousState = GameState.TITLE;
    }

    public void update() {
        switch (currentState) {
            case TITLE -> {
                titleScreen.update(input);
                if (titleScreen.shouldTransition()) {
                    changeState(GameState.MAIN_MENU);
                }
            }
            case MAIN_MENU -> {
                mainMenuScreen.update(input);
                if (mainMenuScreen.shouldTransition()) {
                    switch (mainMenuScreen.getSelectedOption()) {
                        case "START_NEW" -> changeState(GameState.SAVE_SELECT);
                        case "CONTINUE" -> changeState(GameState.SAVE_SELECT);
                        case "SETTINGS" -> changeState(GameState.SETTINGS);
                        case "EXIT" -> System.exit(0);
                    }
                    mainMenuScreen.resetTransition();
                }
            }
            case SAVE_SELECT -> {
                saveSelectScreen.update(input);
                if (saveSelectScreen.shouldTransition()) {
                    selectedSaveSlot = saveSelectScreen.getSelectedSlot();

                    if (saveSelectScreen.isNewGame()) {
                        // 新しいゲーム → キャラクター作成
                        changeState(GameState.CHARACTER_CREATION);
                    } else {
                        // 既存データをロード → フィールドへ
                        currentSaveData = saveDataManager.loadGame(selectedSaveSlot);
                        fieldScreen.loadFromSaveData(currentSaveData);
                        changeState(GameState.FIELD);
                    }
                    saveSelectScreen.resetTransition();
                }
            }
            case CHARACTER_CREATION -> {
                characterCreationScreen.update(input);
                if (characterCreationScreen.shouldTransition()) {
                    // キャラクター作成完了 → セーブして フィールドへ
                    String playerName = characterCreationScreen.getPlayerName();
                    currentSaveData = new SaveDataManager.SaveData(playerName, 1, 2, 2, "NORTH");

                    saveDataManager.saveGame(selectedSaveSlot, currentSaveData);
                    fieldScreen.loadFromSaveData(currentSaveData);
                    changeState(GameState.FIELD);
                    characterCreationScreen.resetTransition();
                }
            }
            case SETTINGS -> {
                settingsScreen.update(input);
                if (settingsScreen.shouldTransition()) {
                    changeState(previousState); // 元の画面に戻る
                    settingsScreen.resetTransition();
                }
            }
            case FIELD -> {
                fieldScreen.update(input);
                // フィールドは通常は遷移しない（ゲームのメイン画面）
                // 将来的にはバトル画面への遷移なども実装
            }
            case BATTLE -> {
                // TODO: 戦闘画面の更新処理
            }
            case GAME_OVER -> {
                // TODO: ゲームオーバー画面の更新処理
            }
        }
    }

    public void render(Renderer renderer) {
        switch (currentState) {
            case TITLE -> {
                uiRenderer.beginUI();
                titleScreen.render(uiRenderer);
                uiRenderer.endUI();
            }
            case MAIN_MENU -> {
                uiRenderer.beginUI();
                mainMenuScreen.render(uiRenderer);
                uiRenderer.endUI();
            }
            case SAVE_SELECT -> {
                uiRenderer.beginUI();
                saveSelectScreen.render(uiRenderer);
                uiRenderer.endUI();
            }
            case CHARACTER_CREATION -> {
                uiRenderer.beginUI();
                characterCreationScreen.render(uiRenderer);
                uiRenderer.endUI();
            }
            case SETTINGS -> {
                uiRenderer.beginUI();
                settingsScreen.render(uiRenderer);
                uiRenderer.endUI();
            }
            case FIELD -> fieldScreen.render(renderer, uiRenderer);
            case BATTLE -> {
                // TODO: 戦闘画面の実装
                uiRenderer.beginUI();
                uiRenderer.drawText("BATTLE - Under Construction", 300, 300, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
                uiRenderer.endUI();
            }
            case GAME_OVER -> {
                // TODO: ゲームオーバー画面の実装
                uiRenderer.beginUI();
                uiRenderer.drawText("GAME OVER", 400, 300, 1.5f, 1.0f, 0.0f, 0.0f, 1.0f);
                uiRenderer.endUI();
            }
        }
    }

    private void changeState(GameState newState) {
        previousState = currentState;
        currentState = newState;

        // 状態変更時の初期化処理
        switch (newState) {
            case TITLE -> titleScreen.onEnter();
            case MAIN_MENU -> mainMenuScreen.onEnter();
            case SAVE_SELECT -> saveSelectScreen.onEnter();
            case CHARACTER_CREATION -> characterCreationScreen.onEnter();
            case SETTINGS -> settingsScreen.onEnter();
            case FIELD -> fieldScreen.onEnter();
            case BATTLE -> {
                // TODO: 戦闘画面の初期化
            }
            case GAME_OVER -> {
                // TODO: ゲームオーバー画面の初期化
            }
        }
    }

    // セッター・ゲッター
    public GameState getCurrentState() {
        return currentState;
    }

    public SaveDataManager getSaveDataManager() {
        return saveDataManager;
    }

    public GameSettings getSettings() {
        return settings;
    }
}
