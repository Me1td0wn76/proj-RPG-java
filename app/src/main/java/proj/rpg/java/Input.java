package proj.rpg.java;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

/**
 * キー入力をポーリングしてプレイヤー制御へ反映。
 * WASD: 前後移動 + 平行移動、 QE: 旋回
 */
public class Input {
    private final long window;

    // 入力のリピートを簡易的に抑えるためのクールダウン（フレーム数）
    private int cooldown = 0;
    private static final int COOLDOWN_FRAMES = 8;

    public Input(long window) {
        this.window = window;
    }

    public void update(Player player, DungeonMap map) {
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            player.moveForward(map);
            cooldown = COOLDOWN_FRAMES;
        } else if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            player.moveBackward(map);
            cooldown = COOLDOWN_FRAMES;
        } else if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            // Aで左旋回
            player.turnLeft();
            cooldown = COOLDOWN_FRAMES;
        } else if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            // Dで右旋回
            player.turnRight();
            cooldown = COOLDOWN_FRAMES;
        } else if (glfwGetKey(window, GLFW_KEY_Q) == GLFW_PRESS) {
            // Qで左に平行移動
            player.strafeLeft(map);
            cooldown = COOLDOWN_FRAMES;
        } else if (glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS) {
            // Eで右に平行移動
            player.strafeRight(map);
            cooldown = COOLDOWN_FRAMES;
        }
    }

    /**
     * 指定キーが押されているかを確認（UI用）
     */
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(window, keyCode) == GLFW_PRESS;
    }
}
