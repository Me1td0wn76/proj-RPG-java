package proj.rpg.java;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

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
        DungeonMap map = new DungeonMap(); // デフォルトマップを使用
        Player player = new Player(2, 2, Facing.NORTH);
        Input input = new Input(window);
        Renderer renderer = new Renderer(WIDTH, HEIGHT, map, player);
        UIRenderer uiRenderer = new UIRenderer(WIDTH, HEIGHT);

        // StateManagerを使用した新しいシステム
        StateManager stateManager = new StateManager(uiRenderer, input);
        loop = new GameLoop(window, input, renderer, player, map, stateManager, uiRenderer);

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
