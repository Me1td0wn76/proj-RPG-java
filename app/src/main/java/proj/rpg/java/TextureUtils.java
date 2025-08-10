package proj.rpg.java;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * 単純なチェッカー（市松模様）テクスチャをCPUで生成し、OpenGLテクスチャとして返す。
 */
public class TextureUtils {
    public static int createCheckerTexture(int width, int height, int block, int color1, int color2) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean use1 = ((x / block) + (y / block)) % 2 == 0;
                int c = use1 ? color1 : color2;
                byte a = (byte) ((c >> 24) & 0xFF);
                byte r = (byte) ((c >> 16) & 0xFF);
                byte g = (byte) ((c >> 8) & 0xFF);
                byte b = (byte) (c & 0xFF);
                buffer.put(r).put(g).put(b).put(a);
            }
        }
        buffer.flip();

        int texId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        return texId;
    }
}
