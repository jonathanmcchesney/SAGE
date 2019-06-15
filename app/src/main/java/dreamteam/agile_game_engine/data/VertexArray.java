package dreamteam.agile_game_engine.data;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


/**
 * Adapted from: OpenGL ES 2 for Android: A Quick-Start Guide (Pragmatic Programmers) by Kevin Brothaler.
 * This class was created by Erin.
 */

public class VertexArray
{
    private final FloatBuffer floatBuffer;

    /**
     * Constructor method.
     * @param vertexData - the vertices to put into the VertexArray object
     */
    public VertexArray(float[] vertexData)
    {
        floatBuffer = ByteBuffer
                .allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation,
                                       int componentCount, int stride)
    {
        floatBuffer.position(dataOffset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount,
                GLES20.GL_FLOAT, false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);

        floatBuffer.position(0);
    }
}
