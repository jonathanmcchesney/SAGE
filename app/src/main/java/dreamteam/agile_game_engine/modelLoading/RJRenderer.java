package dreamteam.agile_game_engine.modelLoading;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;


import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.EllipticalOrbitAnimation3D;
import org.rajawali3d.animation.RotateOnAxisAnimation;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.materials.textures.TextureManager;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.RajawaliRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dreamteam.agile_game_engine.R;
import dreamteam.agile_game_engine.util.Logger;


public class RJRenderer extends RajawaliRenderer implements GLSurfaceView.Renderer
{
    Logger logger = new Logger("RJRenderer");

    Context context;

    public RJRenderer(Context context)
    {
        super(context);
        this.context = context;
        setFrameRate(60);
    }

    @Override
    public void onDrawFrame(GL10 unused)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {}


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig arg1) {}

    /**
     * implemented method left empty
     */
    public void onTouchEvent(MotionEvent event){
    }

    /**
     * implemented method, left empty
     */
    public void onOffsetsChanged(float x, float y, float z, float w, int i, int j){
    }


    public void changePosition (int axis)
    {
        if (axis == 0)
        {
            obj.setPosition(obj.getPosition().x + 0.3f,obj.getPosition().y,obj.getPosition().z);

        } else if ( axis == 1)
        {
            obj.setPosition(obj.getPosition().x - 0.3f,obj.getPosition().y,obj.getPosition().z);

        } else if (axis == 2)
        {
            obj.setPosition(obj.getPosition().x,obj.getPosition().y + 0.3f,obj.getPosition().z);

        } else if (axis == 3)
        {
            obj.setPosition(obj.getPosition().x,obj.getPosition().y - 0.3f,obj.getPosition().z);

        } else if (axis == 4)
        {
            obj.setPosition(obj.getPosition().x,obj.getPosition().y,obj.getPosition().z + 0.3f);

        } else if (axis == 5)
        {
            obj.setPosition(obj.getPosition().x,obj.getPosition().y,obj.getPosition().z - 0.3f);

        }
    }

    /**
     * variables are declared that will be used in the Rajawali demo.
     */
    private PointLight basicLight;
    private Object3D obj;
    private Animation3D camAnimation, lightAnimation;

    /**
     * this method is used to initialise the scene for the Rajawali OBJ loader.
     * a new pointLight is created, position and luminosity is set. This Light object
     * is then added to the current scene, a call is made to the camera to set the Z view
     * to keep everything in frame.
     */
    @Override
    protected void initScene() {
        basicLight = new PointLight();
        basicLight.setPower(9);
        basicLight.setPosition(0, -1, 2);
        logger.info("light intensity and position set up for OBJ demonstration successfully");
        getCurrentScene().addLight(basicLight);
        getCurrentCamera().setZ(16);
        logger.info("camera set up for OBJ demonstration successfully");

        /**
         * the LoaderOBJ is instantiated here, if we wanted to we could allow for AWD parsing
         * and loading for other types of files besides OBJ (3DS etc.). The OBJ file is passed in
         * as a raw file with _obj at the end. The _ is very important and must be changed from
         * .obj to _obj
         * The OBJ is then parsed and added to an object group which is in turn added as a child to
         * the scene. Basic animation is avaliable here thanks to the Rajawali integration, but this
         * is not a main feature we have coded (rather we simply implemented its features).
         */
        LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(),
                mTextureManager, R.raw.multiobjects_obj);
        try {
            objParser.parse();
            logger.info("OBJ has been parsed successfully");
            obj = objParser.getParsedObject();

            Texture currentSphereTexture = new Texture("sphereTexture", R.drawable.rajawali_tex);
            TextureManager.getInstance().addTexture(currentSphereTexture);

            Material currentSphereMaterial = new Material();
            currentSphereMaterial.addTexture(currentSphereTexture);
            currentSphereMaterial.setColorInfluence(0);

            obj.setMaterial(currentSphereMaterial);

            getCurrentScene().addChild(obj);

            camAnimation = new RotateOnAxisAnimation(Vector3.Axis.Y, 360);
            camAnimation.setRepeatMode(Animation.RepeatMode.INFINITE);
            camAnimation.setDurationMilliseconds(50000);
            camAnimation.setTransformable3D(obj);
            logger.info("Basic camera rotation successful");
        } catch (ParsingException e) {
            e.printStackTrace();
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        /**
         * here basic light animation is avaliable to be used.
         */
        lightAnimation = new EllipticalOrbitAnimation3D(new Vector3(),
                new Vector3(0, 10, 0), Vector3.getAxisVector(Vector3.Axis.Z), 0,
                360, EllipticalOrbitAnimation3D.OrbitDirection.CLOCKWISE);
        logger.info("Basic light rotation succuessful");

        lightAnimation.setDurationMilliseconds(30000);
        lightAnimation.setRepeatMode(Animation.RepeatMode.INFINITE);
        lightAnimation.setTransformable3D(basicLight);

        /**
         * the animations are then registered and played.
         */
        getCurrentScene().registerAnimation(camAnimation);
        getCurrentScene().registerAnimation(lightAnimation);
        camAnimation.play();
        lightAnimation.play();
    }

}