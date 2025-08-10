package proj.rpg.java;

/**
 * プレイヤーの位置と向きを保持。グリッド単位で移動。
 */
public class Player {
    private int x;
    private int y;
    private Facing facing;

    public Player() {
        this(0, 0, Facing.NORTH);
    }

    public Player(int x, int y, Facing facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Facing getFacing() {
        return facing;
    }

    public void turnLeft() {
        this.facing = facing.turnLeft();
    }

    public void turnRight() {
        this.facing = facing.turnRight();
    }

    public void moveForward(DungeonMap map) {
        int nx = x + facing.dx;
        int ny = y + facing.dy;
        if (!map.isWall(nx, ny)) {
            x = nx;
            y = ny;
        }
    }

    public void moveBackward(DungeonMap map) {
        int nx = x - facing.dx;
        int ny = y - facing.dy;
        if (!map.isWall(nx, ny)) {
            x = nx;
            y = ny;
        }
    }

    public void strafeLeft(DungeonMap map) {
        // 左へ平行移動（向きは変えない）
        int nx = x - facing.dy;
        int ny = y + facing.dx;
        if (!map.isWall(nx, ny)) {
            x = nx;
            y = ny;
        }
    }

    public void strafeRight(DungeonMap map) {
        int nx = x + facing.dy;
        int ny = y - facing.dx;
        if (!map.isWall(nx, ny)) {
            x = nx;
            y = ny;
        }
    }

    /**
     * プレイヤーの位置を設定（セーブデータロード用）
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * プレイヤーの向きを設定（セーブデータロード用）
     */
    public void setFacing(Facing facing) {
        this.facing = facing;
    }
}
