package proj.rpg.java;

/**
 * フィールド画面クラス。
 * 実際のダンジョン探索ゲームプレイを行う画面。
 */
public class FieldScreen extends GameScreen {
    private Player player;
    private DungeonMap map;

    public FieldScreen() {
        // 基本的なダンジョンマップを生成
        this.map = new DungeonMap();
        this.player = new Player();
    }

    @Override
    public void update(Input input) {
        // プレイヤーの移動処理
        input.update(player, map);
    }

    @Override
    public void render(UIRenderer uiRenderer) {
        // フィールド画面では3D描画は別途Rendererで行う
        // UIRendererは最上位のUI要素のみ描画
    }

    /**
     * 3Dレンダラーを使って3D部分を描画（専用メソッド）
     */
    public void render(Renderer renderer, UIRenderer uiRenderer) {
        // 3D部分の描画
        renderer.render(player, map);

        // UI部分の描画（ステータス、ミニマップなど）
        uiRenderer.beginUI();

        // プレイヤーの位置情報を表示
        String posText = String.format("Position: (%d, %d)", player.getX(), player.getY());
        uiRenderer.drawText(posText, 10, 10, 0.8f, 1.0f, 1.0f, 1.0f, 0.9f);

        // 向いている方向を表示
        String facingText = "Facing: " + player.getFacing().toString();
        uiRenderer.drawText(facingText, 10, 30, 0.8f, 1.0f, 1.0f, 1.0f, 0.9f);

        // 操作方法の簡易表示
        String controlText = "WASD: Move/Turn  QE: Strafe";
        uiRenderer.drawText(controlText, 10, 540 - 30, 0.7f, 0.8f, 0.8f, 0.8f, 0.8f);

        uiRenderer.endUI();
    }

    /**
     * セーブデータからフィールド状態をロード
     */
    public void loadFromSaveData(SaveDataManager.SaveData saveData) {
        player.setPosition(saveData.playerX, saveData.playerY);
        player.setFacing(Facing.valueOf(saveData.facing));

        // 他のゲームデータもロード（アイテム、経験値など）
        // 現在は基本情報のみ
    }

    @Override
    public void onEnter() {
        super.onEnter();
        // フィールドに入る際の初期化処理
        // 必要に応じて音楽再生などを開始
    }

    // ゲッター
    public Player getPlayer() {
        return player;
    }

    public DungeonMap getMap() {
        return map;
    }
}
