package proj.rpg.java;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * ゲーム全体の起動と終了処理、GLFWウィンドウのライフサイクルを管理。
 */
public class Game {
    private long window;
    private GameLoop loop;

    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;
    private static final String TITLE = "Wizardry-like Dungeon (LWJGL Minimal)";

    public void run() {
        initWindow();

        // ゲームコンポーネント初期化
        DungeonMap map = DungeonMap.createDemoMap();
        Player player = new Player(2, 2, Facing.NORTH);
        Input input = new Input(window);
        Renderer renderer = new Renderer(WIDTH, HEIGHT, map, player);
        loop = new GameLoop(window, input, renderer, player, map);

        // ループ開始
        loop.start();

        // 終了処理
        renderer.dispose();
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void initWindow() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        // 固定機能パイプライン（glBegin等）を使うため 2.1 を要求
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

        window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(1); // VSync
        glfwShowWindow(window);

        // クリアカラー
        glClearColor(0.05f, 0.05f, 0.08f, 1.0f);
    }
}
