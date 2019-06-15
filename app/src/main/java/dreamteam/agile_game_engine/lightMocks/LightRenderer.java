package dreamteam.agile_game_engine.lightMocks;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dreamteam.agile_game_engine.util.Camera;
import dreamteam.agile_game_engine.util.MyShaders;
import dreamteam.agile_game_engine.util.Vector3;

import dreamteam.agile_game_engine.util.AssetStore;
import dreamteam.agile_game_engine.util.FileIO;
import dreamteam.agile_game_engine.util.Logger;

/**
 * These methods were created by Shane and integrated by Jonathan.
 */

public class LightRenderer implements GLSurfaceView.Renderer
{

    private final float[] MVPMatrix = new float[16];
    private final float[] ProjectionMatrix = new float[16];
    private final float[] ViewMatrix = new float[16];
    public final float[] ModelMatrix = new float[16];
    Camera c = new Camera();
    public float xAngle;
    public float yAngle;
    Logger logger = new Logger("MyGLRenderer");
    AssetStore assetManager;
    Context context;

    private float [] rgbaPointLightColour = {1.0f, 1.0f, 1.0f, 1.0f};//Colour of light point
    private Cubes cubes;
    private PointLight pointLight;
    private MyShaders shaders;


    public LightRenderer(Context context)
    {
        this.context = context;
    }
    public void onPause()
    {
		/* Do stuff to pause the renderer */
    }
    public void onResume()
    {
		/* Do stuff to resume the renderer */
    }

    @Override
    public void onDrawFrame(GL10 arg0) {

        //GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);//Change background colour RGBA
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);


        Matrix.multiplyMM(MVPMatrix, 0, ProjectionMatrix, 0, ViewMatrix, 0);
        Matrix.setIdentityM(ModelMatrix, 0);
        Matrix.translateM(ModelMatrix, 0, 0.0f, 0.0f, 2.0f);


        Matrix.rotateM(ModelMatrix, 0, xAngle, 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(ModelMatrix, 0, -yAngle, 1.0f, 0.0f, 0.0f);
        cubes.draw(ViewMatrix, MVPMatrix, ProjectionMatrix, pointLight.getmLightPosInEyeSpace());
        pointLight.draw(ViewMatrix, MVPMatrix, ProjectionMatrix);
    }



    public void setUpCameraForPointLightDemo(){
        // Position the eye in front of the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = -0.5f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        Matrix.setLookAtM(ViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0,0, width, height);
        final float ratio = (float)width/height,
                bottom = -1.0f,
                top = 1.0f,
                right = ratio,
                left = -ratio,
                near = 1.0f,
                far = 10.0f;
        Matrix.frustumM(ProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
        shaders = new MyShaders(rgbaPointLightColour, 20f, 20.0f);
        pointLight = new PointLight(shaders, new Vector3(0f, 0f, -10f));
        cubes = new Cubes(shaders);
        setUpCameraForPointLightDemo();
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        assetManager = new AssetStore(new FileIO(context));

    }
}