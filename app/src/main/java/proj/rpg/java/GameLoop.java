package proj.rpg.java;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * シンプルな固定フレームレートのゲームループ（約60FPS）。
 * update() -> render() を呼び出し、ウィンドウイベントも処理。
 */
public class GameLoop {
    private final long window;
    private final Input input;
    private final Renderer renderer;
    private final Player player;
    private final DungeonMap map;

    public GameLoop(long window, Input input, Renderer renderer, Player player, DungeonMap map) {
        this.window = window;
        this.input = input;
        this.renderer = renderer;
        this.player = player;
        this.map = map;
    }

    public void start() {
        final double targetFps = 60.0;
        final double frameTime = 1_000_000_000.0 / targetFps; // ns
        long last = System.nanoTime();

        while (!glfwWindowShouldClose(window)) {
            long now = System.nanoTime();
            if (now - last < frameTime) {
                // スリープ代わりにイベント処理
                glfwPollEvents();
                continue;
            }
            last = now;

            // 入力更新
            input.update(player, map);

            // 画面クリア
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // 描画
            renderer.render();

            // スワップ
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
