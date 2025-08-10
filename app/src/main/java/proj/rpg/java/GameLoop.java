package proj.rpg.java;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * シンプルな固定フレームレートのゲームループ（約60FPS）。
 * StateManagerを使用して各画面の更新と描画を管理。
 */
public class GameLoop {
    private final long window;
    private final Input input;
    private final Renderer renderer;
    private final Player player;
    private final DungeonMap map;
    private final StateManager stateManager;
    private final UIRenderer uiRenderer;

    public GameLoop(long window, Input input, Renderer renderer, Player player, DungeonMap map,
            StateManager stateManager, UIRenderer uiRenderer) {
        this.window = window;
        this.input = input;
        this.renderer = renderer;
        this.player = player;
        this.map = map;
        this.stateManager = stateManager;
        this.uiRenderer = uiRenderer;
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

            // StateManagerで状態更新
            stateManager.update();

            // 画面クリア
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // StateManagerで描画
            stateManager.render(renderer);

            // スワップ
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
