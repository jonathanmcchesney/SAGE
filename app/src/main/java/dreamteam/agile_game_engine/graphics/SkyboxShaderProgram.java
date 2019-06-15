package dreamteam.agile_game_engine.graphics;

import android.content.Context;
import android.opengl.GLES20;

import dreamteam.agile_game_engine.R;
import dreamteam.agile_game_engine.util.TextResourceReader;

/**
 * Adapted from: OpenGL ES 2 for Android: A Quick-Start Guide (Pragmatic Programmers) by Kevin Brothaler.
 * This class was created by Erin.
 */

public class SkyboxShaderProgram
{
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // Attribute constants
    protected static final String A_POSITION = "a_Position";

    // Shader program
    protected final int program;

    private final int uMatrixLocation;
    private final int uTextureUnitLocation;
    private final int aPositionLocation;



    int vShaderSky = R.raw.skybox_vertex_shader;
    int fShaderSky = R.raw.skybox_fragment_shader;

    /**
     * Contructor for the SkyboxShaderProgram object
     * @param context - the context.
     */
    public SkyboxShaderProgram(Context context)
    {
        program = ShaderTools.buildProgram(
                TextResourceReader
                        .readTextFileFromResource(context, vShaderSky),
                TextResourceReader
                        .readTextFileFromResource(context, fShaderSky));


        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
    }


    /**
     * Method to set the current OpenGL shader program to
     * this shader program.
     */
    public void useProgram()
    {
        // Set the current OpenGL shader program to this program.
        GLES20.glUseProgram(program);
    }

    /**
     * Method to set the uniforms.
     * @param matrix - the matrix used to set the uniforms.
     * @param textureId - the id of the texture to be used.
     */
    public void setUniforms(float[] matrix, int textureId)
    {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, textureId);
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    /**
     * getter for aPositionLocation
     */
    protected int getPositionAttributeLocation()
    {
        return aPositionLocation;
    }
}
