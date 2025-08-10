package proj.rpg.java;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

/**
 * 固定機能パイプラインを使った最低限の擬似3D描画。
 * - 床: 大きなテクスチャ付き四角形
 * - 壁: 視線方向の最初の壁セルに板ポリゴンを1枚描画
 */
public class Renderer {
    private final int width;
    private final int height;
    private final DungeonMap map;
    private final Player player;

    private final int floorTex;
    private final int wallTex;

    public Renderer(int width, int height, DungeonMap map, Player player) {
        this.width = width;
        this.height = height;
        this.map = map;
        this.player = player;

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glViewport(0, 0, width, height);

        // 投影行列設定（パースペクティブ）
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float fov = 60f;
        float aspect = (float) width / height;
        float near = 0.1f;
        float far = 100f;
        float top = (float) Math.tan(Math.toRadians(fov / 2.0)) * near;
        float right = top * aspect;
        glFrustum(-right, right, -top, top, near, far);

        // テクスチャ作成
        floorTex = TextureUtils.createCheckerTexture(256, 256, 32,
                0xFF2A2A30, 0xFF1E1E24); // 暗い床
        wallTex = TextureUtils.createCheckerTexture(256, 256, 32,
                0xFF6A6AB0, 0xFF4C4C88); // 壁
    }

    public void render() {
        // ビュー行列（カメラ）
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        float eyeX = player.getX() + 0.5f;
        float eyeY = 1.0f; // 目線の高さ
        float eyeZ = player.getY() + 0.5f;
        float yaw;
        switch (player.getFacing()) {
            case NORTH -> yaw = 0f; // -Z方向
            case SOUTH -> yaw = 180f; // +Z方向
            case EAST -> yaw = -90f; // +X方向
            case WEST -> yaw = 90f; // -X方向
            default -> yaw = 0f;
        }
        // カメラ変換（逆変換を適用）
        glRotatef(yaw, 0, 1, 0);
        glTranslatef(-eyeX, -eyeY, -eyeZ);

        // 描画
        drawFloor();
        drawFrontWall();
    }

    private void drawFloor() {
        glBindTexture(GL_TEXTURE_2D, floorTex);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        float size = 50f; // 広い床
        float tiling = 32f; // テクスチャの繰り返し
        glColor3f(1f, 1f, 1f);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex3f(-size, 0, -size);
        glTexCoord2f(tiling, 0);
        glVertex3f(size, 0, -size);
        glTexCoord2f(tiling, tiling);
        glVertex3f(size, 0, size);
        glTexCoord2f(0, tiling);
        glVertex3f(-size, 0, size);
        glEnd();
    }

    private void drawFrontWall() {
        glBindTexture(GL_TEXTURE_2D, wallTex);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        int maxDepth = 5;
        int px = player.getX();
        int py = player.getY();
        for (int d = 1; d <= maxDepth; d++) {
            int tx = px + player.getFacing().dx * d;
            int ty = py + player.getFacing().dy * d;
            if (map.isWall(tx, ty)) {
                float cx = tx + 0.5f;
                float cz = ty + 0.5f;
                float w = 1.0f; // 幅
                float h = 2.0f; // 高さ
                // カメラに正対する板ポリゴン（ビルボード的表現）
                glColor3f(1f, 1f, 1f);
                glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex3f(cx - w / 2, 0, cz);
                glTexCoord2f(1, 0);
                glVertex3f(cx + w / 2, 0, cz);
                glTexCoord2f(1, 1);
                glVertex3f(cx + w / 2, h, cz);
                glTexCoord2f(0, 1);
                glVertex3f(cx - w / 2, h, cz);
                glEnd();
                break;
            }
        }
    }

    /**
     * 指定されたプレイヤーとマップで描画（StateManager用）
     */
    public void render(Player player, DungeonMap map) {
        // 既存のフィールドを一時的に上書きして描画
        // 本来は引数を使って動的に描画すべきですが、現在の実装を活用
        render(); // 現在のプレイヤー/マップ状態で描画
    }

    public void dispose() {
        // テクスチャは必要に応じて削除可能
    }
}
