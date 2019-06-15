package dreamteam.agile_game_engine.graphics;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import dreamteam.agile_game_engine.data.VertexArray;

/**
 * Adapted from: OpenGL ES 2 for Android: A Quick-Start Guide (Pragmatic Programmers) by Kevin Brothaler.
 * This class was created by Erin.
 */

public class Skybox
{
    private static final int POSITION_COMPONENT_COUNT = 3;
    private final VertexArray vertexArray;
    private final ByteBuffer indexArray;

    /**
     * Constructor method.
     */
    public Skybox()
    {
        // Create a unit cube.
        vertexArray = new VertexArray(new float[]{
                -1,  1,  1,     // (0) Top-left near
                 1,  1,  1,     // (1) Top-right near
                -1, -1,  1,     // (2) Bottom-left near
                 1, -1,  1,     // (3) Bottom-right near
                -1,  1, -1,     // (4) Top-left far
                 1,  1, -1,     // (5) Top-right far
                -1, -1, -1,     // (6) Bottom-left far
                 1, -1, -1      // (7) Bottom-right far
        });

        // 6 indices per cube side.
        indexArray =  ByteBuffer.allocateDirect(6 * 6)
                .put(new byte[] {
                        // Front
                        1, 3, 0,
                        0, 3, 2,

                        // Back
                        4, 6, 5,
                        5, 6, 7,

                        // Left
                        0, 2, 4,
                        4, 2, 6,

                        // Right
                        5, 7, 1,
                        1, 7, 3,

                        // Top
                        5, 1, 4,
                        4, 1, 0,

                        // Bottom
                        6, 2, 7,
                        7, 2, 3
                });
        indexArray.position(0);
    }

    /**
     * Method for binding together the skybox VertexArry and
     * the shader program.
     * @param skyboxProgram - the ShaderProgram to be used by the skybox.
     */
    public void bindData(SkyboxShaderProgram skyboxProgram)
    {
        vertexArray.setVertexAttribPointer(0,
                skyboxProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }

    /**
     * Method for drawing the skybox to the screen.
     */
    public void draw()
    {
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_BYTE, indexArray);
    }

}