package dreamteam.agile_game_engine.util;


public class MyShaders
{
    private String pointLightColour,        /** Colour of the Point light source, RGBA, 0-1 */
    pointLightBrightness,                   /** Brightness of the Point light source */
    pointLightSize,                         /** Size of the Point light source */
    vertexShader,
    fragmentShader,
    pointVertexShader,
    pointFragmentShader;

    public MyShaders(){
        vertexShader = "uniform mat4 u_MVPMatrix;      \n"
                + "uniform mat4 u_MVMatrix;       \n"
                + "uniform vec3 u_LightPos;       \n"

                + "attribute vec4 a_Position;     \n"
                + "attribute vec4 a_Color;        \n"
                + "attribute vec3 a_Normal;       \n"

                + "varying vec4 v_Color;          \n"

                + "void main()                    \n"
                + "{                              \n"
                + "   vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);              \n"
                + "   vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));     \n"
                + "   float distance = length(u_LightPos - modelViewVertex);             \n"
                + "   vec3 lightVector = normalize(u_LightPos - modelViewVertex);        \n"
                + "   float diffuse = max(dot(modelViewNormal, lightVector), 0.1);   \n"
                + "   diffuse = diffuse * (3 / (1.0 + (0.25 * distance * distance)));  \n"
                + "   v_Color = a_Color * diffuse;                                       \n"
                + "   gl_Position = u_MVPMatrix * a_Position;                            \n"
                + "}                                                                     \n";

        fragmentShader = "precision mediump float;       \n"
                + "varying vec4 v_Color;          \n"
                + "void main()                    \n"
                + "{                              \n"
                + "   gl_FragColor = v_Color;     \n"
                + "}                              \n";
    }

    public MyShaders(float[] RGBA, float brightness, float lightSize)
    {

        //converting parameters to Strings for use in Shader code - Shane
        StringBuilder x = new StringBuilder();
        for(int i = 0; i<RGBA.length; i++){
            x.append(RGBA[i]);
            if(i<RGBA.length-1){x.append(", ");}
        }
        pointLightColour = x.toString();
        pointLightBrightness = Float.toString(brightness);
        pointLightSize = Float.toString(lightSize);

        vertexShader = "uniform mat4 u_MVPMatrix;      \n"
                + "uniform mat4 u_MVMatrix;       \n"
                + "uniform vec3 u_LightPos;       \n"

                + "attribute vec4 a_Position;     \n"
                + "attribute vec4 a_Color;        \n"
                + "attribute vec3 a_Normal;       \n"

                + "varying vec4 v_Color;          \n"

                + "void main()                    \n"
                + "{                              \n"
                + "   vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);              \n"
                + "   vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));     \n"
                + "   float distance = length(u_LightPos - modelViewVertex);             \n"
                + "   vec3 lightVector = normalize(u_LightPos - modelViewVertex);        \n"
                + "   float diffuse = max(dot(modelViewNormal, lightVector), 0.1);   \n"
                + "   diffuse = diffuse * ("+pointLightBrightness+" / (1.0 + (0.25 * distance * distance)));  \n"
                + "   v_Color = a_Color * diffuse;                                       \n"
                + "   gl_Position = u_MVPMatrix * a_Position;                            \n"
                + "}                                                                     \n";

        fragmentShader = "precision mediump float;       \n"
                + "varying vec4 v_Color;          \n"
                + "void main()                    \n"
                + "{                              \n"
                + "   gl_FragColor = v_Color;     \n"
                + "}                              \n";

        pointVertexShader =
                "uniform mat4 u_MVPMatrix;      \n"
                        + "attribute vec4 a_Position;                 \n"
                        + "void main()                                  \n"
                        + "{                                            \n"
                        + "   gl_Position = u_MVPMatrix                 \n"//POSITION OF LIGHT POINT
                        + "               * a_Position;                 \n"
                        + "   gl_PointSize = " + pointLightSize + ";        \n" // Alter point light size
                        + "}                                            \n";

        pointFragmentShader =
                "precision mediump float;       \n"
                        + "void main()                    \n"
                        + "{                              \n"
                        + "   gl_FragColor = vec4(" + pointLightColour +");             \n"//
                        + "}                              \n";
    }

    public String getVertexShader() { return vertexShader; }

    public String getFragmentShader() { return fragmentShader; }

    public String getPointVertexShader() { return pointVertexShader; }

    public String getPointFragmentShader() { return pointFragmentShader; }

}
