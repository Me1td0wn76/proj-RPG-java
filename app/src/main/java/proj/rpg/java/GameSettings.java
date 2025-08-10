package proj.rpg.java;

/**
 * ゲームの設定を管理するクラス。
 * 音量、解像度、キー配置などの設定データを保持。
 */
public class GameSettings {
    private float masterVolume = 1.0f;
    private float sfxVolume = 1.0f;
    private float musicVolume = 1.0f;

    private int windowWidth = 960;
    private int windowHeight = 540;
    private boolean fullscreen = false;

    // キー設定
    private int keyUp = 87; // W
    private int keyDown = 83; // S
    private int keyLeft = 65; // A
    private int keyRight = 68; // D
    private int keyRotateLeft = 81; // Q
    private int keyRotateRight = 69; // E
    private int keyAction = 32; // Space
    private int keyCancel = 256; // Escape

    // アクセサメソッド
    public float getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(float masterVolume) {
        this.masterVolume = Math.max(0.0f, Math.min(1.0f, masterVolume));
    }

    public float getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(float sfxVolume) {
        this.sfxVolume = Math.max(0.0f, Math.min(1.0f, sfxVolume));
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = Math.max(0.0f, Math.min(1.0f, musicVolume));
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = Math.max(640, windowWidth);
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = Math.max(480, windowHeight);
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    // キー設定
    public int getKeyUp() {
        return keyUp;
    }

    public void setKeyUp(int keyUp) {
        this.keyUp = keyUp;
    }

    public int getKeyDown() {
        return keyDown;
    }

    public void setKeyDown(int keyDown) {
        this.keyDown = keyDown;
    }

    public int getKeyLeft() {
        return keyLeft;
    }

    public void setKeyLeft(int keyLeft) {
        this.keyLeft = keyLeft;
    }

    public int getKeyRight() {
        return keyRight;
    }

    public void setKeyRight(int keyRight) {
        this.keyRight = keyRight;
    }

    public int getKeyRotateLeft() {
        return keyRotateLeft;
    }

    public void setKeyRotateLeft(int keyRotateLeft) {
        this.keyRotateLeft = keyRotateLeft;
    }

    public int getKeyRotateRight() {
        return keyRotateRight;
    }

    public void setKeyRotateRight(int keyRotateRight) {
        this.keyRotateRight = keyRotateRight;
    }

    public int getKeyAction() {
        return keyAction;
    }

    public void setKeyAction(int keyAction) {
        this.keyAction = keyAction;
    }

    public int getKeyCancel() {
        return keyCancel;
    }

    public void setKeyCancel(int keyCancel) {
        this.keyCancel = keyCancel;
    }

    /**
     * デフォルト設定にリセット
     */
    public void resetToDefaults() {
        masterVolume = 1.0f;
        sfxVolume = 1.0f;
        musicVolume = 1.0f;
        windowWidth = 960;
        windowHeight = 540;
        fullscreen = false;
        keyUp = 87; // W
        keyDown = 83; // S
        keyLeft = 65; // A
        keyRight = 68; // D
        keyRotateLeft = 81; // Q
        keyRotateRight = 69; // E
        keyAction = 32; // Space
        keyCancel = 256; // Escape
    }
}
