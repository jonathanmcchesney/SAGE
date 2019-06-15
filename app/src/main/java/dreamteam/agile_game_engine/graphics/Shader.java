package dreamteam.agile_game_engine.graphics;

/**
 * This class was created by Simon.
 */

public class Shader
{
    private String shaderProgram;

    /**
     * Constructor method.
     * @param shaderProgram - the string containing the shader code for this shader object.
     */
    public Shader(String shaderProgram)
    {
        this.shaderProgram = shaderProgram;
    }

    //getter and setter
    public String getShader()
    {
        return shaderProgram;
    }
    public void setShader(String shaderProgram)

    {
        this.shaderProgram = shaderProgram;
    }

}
