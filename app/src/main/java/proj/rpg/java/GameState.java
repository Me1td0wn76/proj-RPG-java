package proj.rpg.java;

/**
 * ゲームの状態を表す列挙型。
 * 各画面やシーンの遷移を管理するために使用。
 */
public enum GameState {
    TITLE, // タイトル画面
    MAIN_MENU, // メインメニュー（はじめから/つづきから/せってい）
    CHARACTER_CREATION, // キャラクター作成
    SAVE_SELECT, // セーブデータ選択
    SETTINGS, // 設定画面
    FIELD, // フィールド（ダンジョン探索）
    BATTLE, // 戦闘（将来実装）
    GAME_OVER // ゲームオーバー
}
