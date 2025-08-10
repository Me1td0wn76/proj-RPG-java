package proj.rpg.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * セーブデータの管理を行うクラス。
 * JSONファイルでセーブデータを保存・読み込み。
 */
public class SaveDataManager {
    private static final String SAVE_DIR = "saves";
    private static final String SAVE_FILE_PREFIX = "save_";
    private static final String SAVE_FILE_EXTENSION = ".json";
    private static final int MAX_SAVE_SLOTS = 10;

    public static class SaveData {
        public String playerName;
        public int playerLevel;
        public int playerX, playerY;
        public String facing;
        public String createdAt;
        public String lastPlayedAt;
        public int playtimeMinutes;

        public SaveData() {
        }

        public SaveData(String name, int level, int x, int y, String face) {
            this.playerName = name;
            this.playerLevel = level;
            this.playerX = x;
            this.playerY = y;
            this.facing = face;
            this.createdAt = getCurrentTimeString();
            this.lastPlayedAt = getCurrentTimeString();
            this.playtimeMinutes = 0;
        }

        private String getCurrentTimeString() {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        }
    }

    public SaveDataManager() {
        createSaveDirectoryIfNotExists();
    }

    private void createSaveDirectoryIfNotExists() {
        try {
            Path saveDir = Paths.get(SAVE_DIR);
            if (!Files.exists(saveDir)) {
                Files.createDirectories(saveDir);
            }
        } catch (IOException e) {
            System.err.println("Failed to create save directory: " + e.getMessage());
        }
    }

    /**
     * セーブデータを保存する
     */
    public boolean saveGame(int slotIndex, SaveData data) {
        if (slotIndex < 0 || slotIndex >= MAX_SAVE_SLOTS) {
            return false;
        }

        try {
            data.lastPlayedAt = data.getCurrentTimeString();
            String filename = SAVE_FILE_PREFIX + slotIndex + SAVE_FILE_EXTENSION;
            Path savePath = Paths.get(SAVE_DIR, filename);

            // 簡易JSON形式で保存（Gsonの代替）
            String json = saveDataToJson(data);
            Files.write(savePath, json.getBytes());

            return true;
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
            return false;
        }
    }

    /**
     * セーブデータを読み込む
     */
    public SaveData loadGame(int slotIndex) {
        if (slotIndex < 0 || slotIndex >= MAX_SAVE_SLOTS) {
            return null;
        }

        try {
            String filename = SAVE_FILE_PREFIX + slotIndex + SAVE_FILE_EXTENSION;
            Path savePath = Paths.get(SAVE_DIR, filename);

            if (!Files.exists(savePath)) {
                return null;
            }

            String json = new String(Files.readAllBytes(savePath));
            return jsonToSaveData(json);

        } catch (IOException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            return null;
        }
    }

    /**
     * 全セーブスロットの概要情報を取得
     */
    public SaveData[] getAllSaveSlots() {
        SaveData[] slots = new SaveData[MAX_SAVE_SLOTS];
        for (int i = 0; i < MAX_SAVE_SLOTS; i++) {
            slots[i] = loadGame(i);
        }
        return slots;
    }

    /**
     * セーブスロットが空かどうか確認
     */
    public boolean isSlotEmpty(int slotIndex) {
        return loadGame(slotIndex) == null;
    }

    /**
     * セーブデータをJSON文字列に変換（簡易実装）
     */
    private String saveDataToJson(SaveData data) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"playerName\": \"").append(escapeJson(data.playerName)).append("\",\n");
        json.append("  \"playerLevel\": ").append(data.playerLevel).append(",\n");
        json.append("  \"playerX\": ").append(data.playerX).append(",\n");
        json.append("  \"playerY\": ").append(data.playerY).append(",\n");
        json.append("  \"facing\": \"").append(data.facing).append("\",\n");
        json.append("  \"createdAt\": \"").append(data.createdAt).append("\",\n");
        json.append("  \"lastPlayedAt\": \"").append(data.lastPlayedAt).append("\",\n");
        json.append("  \"playtimeMinutes\": ").append(data.playtimeMinutes).append("\n");
        json.append("}");
        return json.toString();
    }

    /**
     * JSON文字列をセーブデータに変換（簡易実装）
     */
    private SaveData jsonToSaveData(String json) {
        SaveData data = new SaveData();

        // 簡単なJSONパース（正規表現使用）
        data.playerName = extractJsonString(json, "playerName");
        data.playerLevel = extractJsonInt(json, "playerLevel");
        data.playerX = extractJsonInt(json, "playerX");
        data.playerY = extractJsonInt(json, "playerY");
        data.facing = extractJsonString(json, "facing");
        data.createdAt = extractJsonString(json, "createdAt");
        data.lastPlayedAt = extractJsonString(json, "lastPlayedAt");
        data.playtimeMinutes = extractJsonInt(json, "playtimeMinutes");

        return data;
    }

    private String extractJsonString(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]+)\"";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        return m.find() ? m.group(1) : "";
    }

    private int extractJsonInt(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*(\\d+)";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }

    private String escapeJson(String str) {
        if (str == null)
            return "";
        return str.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
