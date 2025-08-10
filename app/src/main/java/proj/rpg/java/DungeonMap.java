package proj.rpg.java;

/**
 * 2D配列でマップを管理。0=床, 1=壁。
 */
public class DungeonMap {
    private final int width;
    private final int height;
    private final int[][] tiles;

    public DungeonMap(int width, int height, int[][] tiles) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }

    /**
     * デフォルトマップでのコンストラクタ
     */
    public DungeonMap() {
        this(8, 8, new int[][] {
                { 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 0, 0, 0, 0, 0, 0, 1 },
                { 1, 0, 1, 0, 1, 1, 0, 1 },
                { 1, 0, 1, 0, 0, 0, 0, 1 },
                { 1, 0, 1, 1, 1, 0, 1, 1 },
                { 1, 0, 0, 0, 0, 0, 0, 1 },
                { 1, 0, 1, 0, 1, 0, 0, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1 }
        });
    }

    public boolean isWall(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return true; // 外は壁扱い
        return tiles[y][x] == 1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public static DungeonMap createDemoMap() {
        // シンプルな壁で囲った5x5
        int[][] t = new int[][] {
                { 1, 1, 1, 1, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
        };
        return new DungeonMap(5, 5, t);
    }
}
