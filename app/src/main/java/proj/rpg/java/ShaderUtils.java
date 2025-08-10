package proj.rpg.java;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;

/**
 * 最小限のカラーのみのシェーダ（行列掛け + 単色）。
 * ただし本実装では固定機能パイプライン(glBegin/glEnd)を使うため、
 * シェーダは行列Uniformの受け渡し用のダミーとして使います。
 * 現在のRendererは固定機能で描画しているため、このクラスは未使用です。
 * 近い将来、モダンOpenGLに切り替える際に利用します。
 */
public class ShaderUtils {
    public static int createSimpleColorShader() {
        String vs = "#version 150 core\n" +
                "uniform mat4 uMvp;\n" +
                "in vec3 aPos;\n" +
                "void main(){\n" +
                "    gl_Position = uMvp * vec4(aPos, 1.0);\n" +
                "}";
        String fs = "#version 150 core\n" +
                "out vec4 FragColor;\n" +
                "void main(){\n" +
                "    // glColorを使うのでここは未使用\n" +
                "    FragColor = vec4(1.0);\n" +
                "}";

        int program = glCreateProgram();
        int v = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(v, vs);
        glCompileShader(v);
        if (glGetShaderi(v, GL_COMPILE_STATUS) == 0) {
            throw new IllegalStateException("Vertex shader compile error: " + glGetShaderInfoLog(v));
        }

        int f = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(f, fs);
        glCompileShader(f);
        if (glGetShaderi(f, GL_COMPILE_STATUS) == 0) {
            throw new IllegalStateException("Fragment shader compile error: " + glGetShaderInfoLog(f));
        }

        glAttachShader(program, v);
        glAttachShader(program, f);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            throw new IllegalStateException("Program link error: " + glGetProgramInfoLog(program));
        }
        glDeleteShader(v);
        glDeleteShader(f);
        return program;
    }
}
