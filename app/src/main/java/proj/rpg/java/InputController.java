package proj.rpg.java;

/**
 * 入力制御を管理する共通クラス。
 * キー入力のクールダウン機能を提供し、連続入力を防ぐ。
 */
public class InputController {
    private boolean[] keyPressed = new boolean[512];
    private long[] keyLastPressed = new long[512];

    // デフォルトのクールダウン時間を長く設定（500ミリ秒）
    private static final long DEFAULT_COOLDOWN_MS = 500;

    /**
     * キー入力を処理し、クールダウン時間を考慮してアクションを実行する
     * 
     * @param input  入力オブジェクト
     * @param key    キーコード
     * @param action 実行するアクション
     */
    public void handleKeyInput(Input input, int key, Runnable action) {
        handleKeyInputWithCooldown(input, key, action, DEFAULT_COOLDOWN_MS);
    }

    /**
     * キー入力を処理し、カスタムクールダウン時間を考慮してアクションを実行する
     * 
     * @param input      入力オブジェクト
     * @param key        キーコード
     * @param action     実行するアクション
     * @param cooldownMs クールダウン時間（ミリ秒）
     */
    public void handleKeyInputWithCooldown(Input input, int key, Runnable action, long cooldownMs) {
        boolean isPressed = input.isKeyPressed(key);
        long currentTime = System.currentTimeMillis();

        if (isPressed && !keyPressed[key]) {
            // 前回の入力からクールダウン時間が経過していることを確認
            if (currentTime - keyLastPressed[key] >= cooldownMs) {
                action.run();
                keyLastPressed[key] = currentTime;
            }
        }

        keyPressed[key] = isPressed;
    }

    /**
     * 短いクールダウン時間でキー入力を処理する（文字入力などに使用）
     * 
     * @param input  入力オブジェクト
     * @param key    キーコード
     * @param action 実行するアクション
     */
    public void handleKeyInputFast(Input input, int key, Runnable action) {
        handleKeyInputWithCooldown(input, key, action, 200); // 200ms
    }

    /**
     * 連続入力可能なキー入力を処理する（移動キーなど、長押し対応）
     * 
     * @param input  入力オブジェクト
     * @param key    キーコード
     * @param action 実行するアクション
     */
    public void handleKeyInputRepeatable(Input input, int key, Runnable action) {
        handleKeyInputWithCooldown(input, key, action, 100); // 100ms
    }

    /**
     * 入力状態をリセットする（画面遷移時などに使用）
     */
    public void reset() {
        keyPressed = new boolean[512];
        keyLastPressed = new long[512];
    }

    /**
     * 特定のキーの入力状態をリセットする
     * 
     * @param key リセットするキーコード
     */
    public void resetKey(int key) {
        if (key >= 0 && key < 512) {
            keyPressed[key] = false;
            keyLastPressed[key] = 0;
        }
    }

    /**
     * キーが現在押されているかを確認する（クールダウンは考慮しない）
     * 
     * @param input 入力オブジェクト
     * @param key   キーコード
     * @return キーが押されている場合true
     */
    public boolean isKeyPressed(Input input, int key) {
        return input.isKeyPressed(key);
    }

    /**
     * キーが最後に押された時刻を取得する
     * 
     * @param key キーコード
     * @return 最後に押された時刻（ミリ秒）
     */
    public long getLastPressedTime(int key) {
        if (key >= 0 && key < 512) {
            return keyLastPressed[key];
        }
        return 0;
    }
}
