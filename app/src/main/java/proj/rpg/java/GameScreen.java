package proj.rpg.java;

/**
 * ゲーム画面の基底クラス。
 * 各画面が実装すべき基本的なインターフェースを提供。
 */
public abstract class GameScreen {
    protected boolean transitionRequested = false;

    /**
     * 画面の更新処理
     */
    public abstract void update(Input input);

    /**
     * 画面の描画処理
     */
    public abstract void render(UIRenderer uiRenderer);

    /**
     * 画面に入るときの初期化処理
     */
    public void onEnter() {
        transitionRequested = false;
    }

    /**
     * 画面から出るときの終了処理
     */
    public void onExit() {
        // サブクラスでオーバーライド
    }

    /**
     * 画面遷移が要求されているか
     */
    public boolean shouldTransition() {
        return transitionRequested;
    }

    /**
     * 遷移フラグをリセット
     */
    public void resetTransition() {
        transitionRequested = false;
    }
}
