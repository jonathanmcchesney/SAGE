package dreamteam.agile_game_engine.graphics;

import android.opengl.GLES20;

/**
 * Adapted from: OpenGL ES 2 for Android: A Quick-Start Guide (Pragmatic Programmers) by Kevin Brothaler.
 * This class was created by Erin.
 */

public class ShaderTools
{
    /**
     * Loads and compiles a vertex shader, returning the OpenGL object ID.
     * @param shaderCode - the string containing the vertex shader code to be compiled.
     * @return the complied vertex shader
     */
    public static int compileVertexShader(String shaderCode)
    {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    /**
     * Loads and compiles a fragment shader, returning the OpenGL object ID.
     * @param shaderCode - the string containing the fragment shader code to compiled.
     * @return the compiled fragment shader.
     */
    public static int compileFragmentShader(String shaderCode)
    {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    /**
     * Compiles a shader, returning the OpenGL object ID.
     * @param type - the type of shader to be compiled.
     * @param shaderCode - the string containing the shader code to be compiled.
     * @return the compiled shader.
     */
    private static int compileShader(int type, String shaderCode)
    {
        // Create a new shader object.
        final int shaderObjectId = GLES20.glCreateShader(type);

        if (shaderObjectId == 0) {

            return 0;
        }

        // Pass in the shader source.
        GLES20.glShaderSource(shaderObjectId, shaderCode);

        // Compile the shader.
        GLES20.glCompileShader(shaderObjectId);

        // Get the compilation status.
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS,
                compileStatus, 0);

        // Verify the compile status.
        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.
            GLES20.glDeleteShader(shaderObjectId);

            return 0;
        }

        // Return the shader object ID.
        return shaderObjectId;
    }

    /**
     * Links a vertex shader and a fragment shader together into an OpenGL
     * program. Returns the OpenGL program object ID, or 0 if linking failed.
     * @param vertexShaderId - the ID of the vertex shader.
     * @param fragmentShaderId - the ID of the fragment shader.
     * @return the created program with the vertex and fragment shaders attached.
     */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId)
    {

        // Create a new program object.
        final int programObjectId = GLES20.glCreateProgram();

        if (programObjectId == 0) {


            return 0;
        }

        // Attach the vertex shader to the program.
        GLES20.glAttachShader(programObjectId, vertexShaderId);

        // Attach the fragment shader to the program.
        GLES20.glAttachShader(programObjectId, fragmentShaderId);

        // Link the two shaders together into a program.
        GLES20.glLinkProgram(programObjectId);

        // Get the link status.
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS,
                linkStatus, 0);


        // Verify the link status.
        if (linkStatus[0] == 0) {
            // If it failed, delete the program object.
            GLES20.glDeleteProgram(programObjectId);

            return 0;
        }

        // Return the program object ID.
        return programObjectId;
    }

    /**
     * Method that compiles the shaders, links and validates the
     * program, returning the program ID.
     * @param vertexShaderSource - source code of the vertex shader.
     * @param fragmentShaderSource  - source code of the fragment shader.
     * @return the built program.
     */
    public static int buildProgram(String vertexShaderSource,
                                   String fragmentShaderSource) {
        int program;

        // Compile the shaders.
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        // Link them into a shader program.
        program = linkProgram(vertexShader, fragmentShader);


        return program;
    }

    /**
     * Alternative method for loading in shaders.
     * @param type - the type of shader to be created.
     * @param shaderCode - the string containing the shader code to be compiled.
     * @return the loaded shader.
     */
    public static int loadShader(int type, String shaderCode)
    {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        // return the shader
        return shader;
    }
}