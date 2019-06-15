package dreamteam.agile_game_engine;


import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dreamteam.agile_game_engine.graphics.Skybox;
import dreamteam.agile_game_engine.graphics.SkyboxShaderProgram;
import dreamteam.agile_game_engine.graphics.TextureHelper;
import dreamteam.agile_game_engine.objects.Billboard;
import dreamteam.agile_game_engine.util.Camera;
import dreamteam.agile_game_engine.objects.Cube;
import dreamteam.agile_game_engine.objects.Prism;
import dreamteam.agile_game_engine.objects.Triangle;
import dreamteam.agile_game_engine.util.AssetStore;
import dreamteam.agile_game_engine.util.FileIO;
import dreamteam.agile_game_engine.util.Logger;
import dreamteam.agile_game_engine.data.Singleton;

import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;

/**
 * This class was created by Simon and Erin
 */

public class GameLoop extends GLSurfaceView
{
    protected final MyGLRenderer renderer;
    private AssetStore assetStore;
    private Logger logger;

    //objects to be drawn
    private ArrayList<Cube> cubeList;
    private Triangle triangle;
    private Prism prism;
    private SkyboxShaderProgram skyboxProgram;
    private Skybox skybox;
    private int skyboxTexture;
    private Billboard billboard;

    //angles for rotation + locations for touch translation
    private int rot;
    private float shapeX,shapeY;
    public float xAngle;
    public float yAngle;
    protected float lastTouchX;
    protected float lastTouchY;
    private float newX,newY;

    //matrices used by the primitives
    public float[] MVPMatrix = new float[16];
    public float[] ProjectionMatrix = new float[16];
    public float[] ViewMatrix = new float[16];
    public float[] PVMatrix = new float[16];
    public float[] MVMatrix = new float[16];


    Singleton sceneRef = Singleton.getInstance();

    protected GameLoopThread gameLoopThread;

    /**
     * Game Loop constructor method.
     * @param context
     */
    public GameLoop(Context context)
    {
        super(context);

        //creating an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8,8,8,8,16,0);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        this.setBackgroundResource(R.drawable.bg3);
        this.setZOrderOnTop(true);

        assetStore = new AssetStore(new FileIO(context));
        renderer = new MyGLRenderer(context);

        //sets the renderer for this GLSurfaceView to the customised MyGLRenderer
        setRenderer(renderer);

        //constantly rendering
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        logger = new Logger("update Thread");

        gameLoopThread = new GameLoopThread(60);
        gameLoopThread.running = true;

        //Initialization logic for the Gameloop Thread.
        start(context);

        gameLoopThread.run();
    }

    /**
     * Method that hold the initialisation logic for the renderer and
     * game loop thread.
     * @param context
     */
    public void start(Context context)
    {
        triangle = new Triangle((assetStore));

        cubeList = new ArrayList<>();
        cubeList.add(new Cube(assetStore));
        cubeList.add(new Cube(assetStore));
        cubeList.add(new Cube(assetStore));
        cubeList.add(new Cube(assetStore));
        cubeList.add(new Cube(assetStore));
        cubeList.add(new Cube(assetStore));
        cubeList.add(new Cube(assetStore));
        cubeList.add(new Cube(assetStore));
        cubeList.add(new Cube(assetStore));
        cubeList.add(new Cube(assetStore));

        prism = new Prism(assetStore);

        rot = 0;

        skyboxProgram = new SkyboxShaderProgram(context);
        skybox = new Skybox();

        skyboxTexture = TextureHelper.loadCubeMap(context,
                new int[] { R.drawable.left, R.drawable.right,
                        R.drawable.bottom, R.drawable.top,
                        R.drawable.front, R.drawable.back});

        billboard = new Billboard(assetStore);
        logger.debug("Finished initialising game objects.");
}

    /**
     * Method for updating the objects in the game loop
     */
    public void update()
    {
        //Automatically increase the angle of rotation.
        shapeX += 2;
        shapeY -= 2;

        //Updates a different demo scene depending on the value of sceneRef.
        switch(sceneRef.getData())
        {
            //The default case updates the first demo.
            default: updateDemo0();break;
            case 0 : updateDemo0();break;
            case 1 : updateDemo1();break;
            case 2 : updateDemo2();break;
            case 3 : updateDemo3();break;
            case 4 : updateDemo4();break;
            case 5 : updateDemo5();break;
            case 6 : updateDemo6();break;
            case 7 : updateDemo7();break;
            case 8 : updateDemo8();break;
            case 9 : updateDemo9();break;
            case 10: updateDemo10();break;
            case 11: updateDemo11();break;
            case 12: updateDemo12();break;
            case 13: updateDemo13();break;
        }
    }

    /**
     * Nested thread for updating the game engine.
     */
    private class GameLoopThread implements Runnable
    {
        private int targetFramesPerSecond;
        private volatile boolean running = false;
        private long targetStepPeriod;

        /**
         * Constructor method
         *
         * @param targetFPS - the target frame rate
         */
        public GameLoopThread(int targetFPS)
        {
            targetFramesPerSecond = targetFPS;
            // Setup the target step period
            targetStepPeriod = 1000000000 / targetFramesPerSecond;
            logger.debug(("Created Game Loop!"));
        }

        /**
         * Run method for the Game Loop thread.
         */
        @Override
        public void run()
        {
        long startRun;
        long startStep, endStep;
        long sleepTime, overSleepTime;

        startRun = System.nanoTime() - targetStepPeriod;
        startStep = startRun;
        overSleepTime = 0L;

        try {
            while (running) {

                long currentTime = System.nanoTime();
                startStep = currentTime;

                //Performs Game Logic update
                update();

                //Blocks the Gameloop from running again until the Renderer finishes rendering the next frame
                running = false;

                //Requests the render to render the next frame
                requestRender();

                endStep = System.nanoTime();
                sleepTime = (targetStepPeriod - (endStep - startStep))
                        - overSleepTime;

                // If needed put the thread to sleep
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime / 1000000L); // Covert ns into ms

                    overSleepTime = (System.nanoTime() - endStep)
                            - sleepTime;
                } else {
                    overSleepTime = 0L;
                }
            }

        } catch (InterruptedException e) {
            logger.error("Gameloop Error", "Gameloop Error : " + e);
        }
    }
    }

    /**
     * Method for handling touch so game objects rotate bu a user's touch
     * @param event - stores the inputted touch.
     * @return boolean to determine if the touch was handled correctly.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if( event.getAction() == MotionEvent.ACTION_DOWN )
        {
            lastTouchX = event.getX();
            lastTouchY = event.getY();

        } else if( event.getAction() == MotionEvent.ACTION_MOVE ) {

            float dx = (event.getX() - lastTouchX) / 50.0f;
            float dy = (event.getY() - lastTouchY) / 50.0f;

            yAngle -= dy;
            xAngle += dx;

            shapeX += dx;
            shapeY -= dy;

            if(dx > 0) {
                newX -= 0.05f;
            } else{
                newX += 0.05f;
            }
            if(dy > 0) {
                newY -= 0.05f;
            } else {
                newY += 0.05f;
            }

        }
        return true;
    }

    /**
     * Updates a single static triangle.
     */
    private void updateDemo0()
    {
        triangle.scale(triangle.getModelMatrix(),2.0f);
    }

    /**
     * Updates a single rotating triangle that also rotates on a user's touch.
     */
    private void updateDemo1()
    {
        triangle.rotate(triangle.getModelMatrix(),-shapeY,0.0f,1.0f,0.0f);
        triangle.scale(triangle.getModelMatrix(),2.0f);
    }

    /**
     * Updates a single rotating cube that translates on a user's touch.
     */
    private void updateDemo2()
    {
        cubeList.get(0).translate(cubeList.get(0).getModelMatrix(), -1.0f, 0.0f, 2.0f);
        cubeList.get(0).translateByTouch(cubeList.get(0).getModelMatrix(), newX, newY);
        cubeList.get(0).rotate(cubeList.get(0).getModelMatrix(), shapeX, 0.0f, 1.0f, 0.0f);
        cubeList.get(0).rotate(cubeList.get(0).getModelMatrix(), -shapeY, 1.0f, 0.0f, 0.0f);
        cubeList.get(0).scale(cubeList.get(0).getModelMatrix(), 0.5f);
    }

    /**
     * Updates two rotating cubes that also rotate on a user's touch.
     */
    private void updateDemo3()
    {
        for(Cube cube : cubeList) {
            if (cube.equals(cubeList.get(0))) {
                cube.translate(cube.getModelMatrix(), -1.0f, 0.0f, 2.0f);
                cube.translateAlongY(cube.getModelMatrix());

            } else {
                cube.translate(cube.getModelMatrix(), 1.0f, 0.0f, 2.0f);
                cube.translateAlongX(cube.getModelMatrix());
            }
            cube.rotate(cube.getModelMatrix(), shapeX, 0.0f, 1.0f, 0.0f);
            cube.rotate(cube.getModelMatrix(), -shapeY, 1.0f, 0.0f, 0.0f);
            cube.scale(cube.getModelMatrix(), 0.5f);
        }
    }

    /**
     * Updates a row of rotating cubes that also rotate on a user's touch.
     */
    private void updateDemo4()
    {
        float x = -5.0f;
        for(Cube cube : cubeList)
        {
            cube.translate(cube.getModelMatrix(), x, 0.0f, 2.0f);
            x++;
            cube.rotate(cube.getModelMatrix(), shapeX, 0.0f, 1.0f, 0.0f);
            cube.rotate(cube.getModelMatrix(), -shapeY, 1.0f, 0.0f, 0.0f);
            cube.scale(cube.getModelMatrix(), 0.35f);
        }
    }

    /**
     * Updates two rows of rotating cubes that also rotate on a user's touch.
     */
    private void updateDemo5()
    {
        cubeList.get(0).translate(cubeList.get(0).getModelMatrix(), -2.0f, 1.0f, 2.0f);
        cubeList.get(1).translate(cubeList.get(1).getModelMatrix(), -1.0f, 1.0f, 2.0f);
        cubeList.get(2).translate(cubeList.get(2).getModelMatrix(), 0.0f, 1.0f, 2.0f);
        cubeList.get(3).translate(cubeList.get(3).getModelMatrix(), 1.0f, 1.0f, 2.0f);
        cubeList.get(4).translate(cubeList.get(4).getModelMatrix(), 2.0f, 1.0f, 2.0f);
        cubeList.get(5).translate(cubeList.get(5).getModelMatrix(), -2.0f, -1.0f, 2.0f);
        cubeList.get(6).translate(cubeList.get(6).getModelMatrix(), -1.0f, -1.0f, 2.0f);
        cubeList.get(7).translate(cubeList.get(7).getModelMatrix(), 0.0f, -1.0f, 2.0f);
        cubeList.get(8).translate(cubeList.get(8).getModelMatrix(), 1.0f, -1.0f, 2.0f);
        cubeList.get(9).translate(cubeList.get(9).getModelMatrix(), 2.0f, -1.0f, 2.0f);
        for(Cube cube : cubeList)
        {
            cube.rotate(cube.getModelMatrix(), shapeX, 0.0f, 1.0f, 0.0f);
            cube.rotate(cube.getModelMatrix(), -shapeY, 1.0f, 0.0f, 0.0f);
            cube.scale(cube.getModelMatrix(), 0.35f);
        }
    }

    /**
     * Updates a triangle formation of rotating cubes that also rotate on a user's touch.
     */
    private void updateDemo6()
    {
        cubeList.get(0).translate(cubeList.get(0).getModelMatrix(), -2f, 0.5f, 2.0f);
        cubeList.get(1).translate(cubeList.get(1).getModelMatrix(), 0f, 0.5f, 2.0f);
        cubeList.get(2).translate(cubeList.get(2).getModelMatrix(), 2f, 0.5f, 2.0f);
        cubeList.get(3).translate(cubeList.get(3).getModelMatrix(), -1f, 0f, 1.0f);
        cubeList.get(4).translate(cubeList.get(4).getModelMatrix(), 1f, 0f, 1.0f);
        cubeList.get(5).translate(cubeList.get(5).getModelMatrix(), 0f, -0.5f, 0f);

        for(int i = 0; i<6; i++)
        {
            Cube cube = cubeList.get(i);

            cube.rotate(cube.getModelMatrix(), shapeX * i, 0.0f, 1.0f, 0.0f);
            cube.rotate(cube.getModelMatrix(), -shapeY * i, 1.0f, 0.0f, 0.0f);
            cube.scale(cube.getModelMatrix(), 0.5f);
        }
    }

    /**
     * Updates a single rotating prism that also rotates on a user's touch.
     */
    private void updateDemo7()
    {
        prism.rotate(prism.getModelMatrix(), shapeX, 0.0f, 1.0f, 0.0f);
        prism.rotate(prism.getModelMatrix(), -shapeY, 0.0f, 1.0f, 0.0f);
        prism.scale(prism.getModelMatrix(), 0.5f);
    }

    /**
     * Updates a rotating cube and a rotating prism that also rotate on a user's touch.
     */
    private void updateDemo8()
    {
        cubeList.get(0).translate(cubeList.get(0).getModelMatrix(), 0.0f, -2.5f, 2.5f);
        cubeList.get(0).rotate(cubeList.get(0).getModelMatrix(), shapeX, 0.0f, 1.0f, 0.0f);
        cubeList.get(0).rotate(cubeList.get(0).getModelMatrix(), -shapeY, 0.0f, 1.0f, 0.0f);
        cubeList.get(0).scale(cubeList.get(0).getModelMatrix(), 0.5f);
        prism.rotate(prism.getModelMatrix(), shapeX, 0.0f, 1.0f, 0.0f);
        prism.rotate(prism.getModelMatrix(), -shapeY, 0.0f, 1.0f, 0.0f);
        prism.scale(prism.getModelMatrix(), 0.5f);
    }

    /**
     * This method was written by Shane.
     */
    private void updateDemo9()
    {
        cubeList.get(0).translate(cubeList.get(0).getModelMatrix(), 0.0f, -2.5f, 2.5f);
        cubeList.get(0).scale(cubeList.get(0).getModelMatrix(), 0.5f);
    }

    /**
     * This method was written by Shane.
     */
    private void updateDemo10()
    {
        cubeList.get(0).translate(cubeList.get(0).getModelMatrix(), 0.0f, -2.5f, 2.5f);
        cubeList.get(0).scale(cubeList.get(0).getModelMatrix(), 0.5f);
    }

    /**
     * This method was written by Shane.
     */
    private void updateDemo11()
    {
        cubeList.get(0).translate(cubeList.get(0).getModelMatrix(), 0.0f, -2.5f, 2.5f);
        cubeList.get(0).scale(cubeList.get(0).getModelMatrix(), 0.5f);
    }

    /**
     * Updates the skybox, allowing it to be rotated by a user's touch.
     */
    private void updateDemo12()
    {
        setIdentityM(ViewMatrix, 0);
        rotateM(ViewMatrix, 0, -yAngle, 1f, 0f, 0f);
        rotateM(ViewMatrix, 0, -xAngle, 0f, 1f, 0f);
        multiplyMM(PVMatrix, 0, ProjectionMatrix, 0, ViewMatrix, 0);
    }

    /**
     * Updates the skybox and a single translating cube. The skybox is manipulated by the
     * user's touch, allowing the discovery of the cube.
     */
    private void updateDemo13()
    {
        updateDemo12();
        cubeList.get(0).translate(cubeList.get(0).getModelMatrix(), -1.0f, 0.0f, 5.0f);
        cubeList.get(0).translateAlongY(cubeList.get(0).getModelMatrix());
        cubeList.get(0).rotate(cubeList.get(0).getModelMatrix(), rot, 0.0f, 1.0f, 0.0f);
        cubeList.get(0).rotate(cubeList.get(0).getModelMatrix(), -rot, 1.0f, 0.0f, 0.0f);
        cubeList.get(0).scale(cubeList.get(0).getModelMatrix(), 0.5f);
    }


    /**
     * Main renderer
     */
    public class MyGLRenderer implements GLSurfaceView.Renderer
    {
        Camera c = new Camera();

        int textRef = 0;
        int bTextRef = 0;

        Logger logger = new Logger("MyGLRenderer");

        Context context;

        /**
         * Constructor method for the renderer.
         * @param context
         */
        public MyGLRenderer(Context context)
        {
            this.context = context;
            logger.debug("Created renderer!");
        }

        /**
         * Method in which the objects get rendered to the screen
         * @param unused - GL10 parameter that isn't used.
         */
        @Override
        public void onDrawFrame(GL10 unused)
        {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            //Draws a different demo scene depending on the value of sceneRef.
            switch(sceneRef.getData())
            {
                //The default case updates the first demo.
                default: drawDemo0();break;
                case 0 :drawDemo0();break;
                case 1 :drawDemo1();break;
                case 2 :drawDemo2();break;
                case 3 : drawDemo3();break;
                case 4 :drawDemo4and5();break;
                case 5 :drawDemo4and5();break;
                case 6 :drawDemo6();break;
                case 7 :drawDemo7();break;
                case 8 :drawDemo8();break;
                case 9 :drawDemo9();break;
                case 10:drawDemo10();break;
                case 11:drawDemo11();break;
                case 12: drawDemo12();break;
                case 13: drawDemo13();break;

            }

            //Allows the Gameloop Thread to run again, after all rendering for the frame is done.
            gameLoopThread.running = true;
            gameLoopThread.run();
        }


        /**
         * Method to deal with changes to the surface.
         * @param unused
         * @param width
         * @param height
         */
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

        /**
         * Method for setting things up when the renderer is created.
         * @param unused
         * @param arg1
         */
        @Override
        public void onSurfaceCreated(GL10 unused, EGLConfig arg1)
        {
            GLES20.glClearDepthf(1.0f);
            GLES20.glEnable(GL10.GL_DEPTH_TEST);

            Matrix.setLookAtM(ViewMatrix, 0, c.getEyeX(), c.getEyeY(), c.getEyeZ(),
                    c.getCamX(), c.getCamY(), c.getCamZ(),
                    c.getUpX(), c.getUpY(), c.getUpZ());
            start(context);
        }

        /**
         * Draws a single static triangle.
         */
        private void drawDemo0()
        {
            if(textRef == 0)
            {
                triangle.setupShaders(assetStore);
            }
            triangle.draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
        }

        /**
         * Draws a single rotating triangle that also rotates on a user's touch.
         */
        private void drawDemo1()
        {
            if (textRef == 0)
            {
                triangle.setupShaders(assetStore);
            }
            triangle.draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            textRef = 1;
        }

        /**
         * Draws a single rotating cube that translates on a user's touch.
         */
        private void drawDemo2()
        {
            if(textRef == 1)
            {
                for(Cube cube : cubeList)
                {
                    cube.setUpShaders(assetStore);
                }
            }
            cubeList.get(0).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            textRef = 0;
        }

        /**
         * Updates two rotating cubes that also rotate on a user's touch.
         */
        private void drawDemo3()
        {
            cubeList.get(0).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            cubeList.get(1).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
        }

        /**
         * Draws either one or two of rotating cubes that also rotate on a user's touch, depending
         * on the update method that is being called.
         */
        private void drawDemo4and5()
        {
            for(Cube cube : cubeList)
            {
                cube.draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            }
        }

        /**
         * Draws a triangle formation of rotating cubes that also rotate on a user's touch.
         */
        private void drawDemo6()
        {
            cubeList.get(0).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            cubeList.get(1).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            cubeList.get(2).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            cubeList.get(3).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            cubeList.get(4).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            cubeList.get(5).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
        }

        /**
         * Updates a single rotating prism that also rotates on a user's touch.
         */
        private void drawDemo7()
        {
            prism.draw(ProjectionMatrix, ViewMatrix,MVMatrix,MVPMatrix);
        }

        /**
         * Draws a single rotating prism and a rotating cube that also rotates on a user's touch.
         */
        private void drawDemo8()
        {
            if(bTextRef==1)
            {
                for(Cube cube : cubeList)
                {
                    cube.setUpShaders(assetStore);
                }
            }
            cubeList.get(0).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            prism.draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            bTextRef = 0;

        }

        /**
         * This method was written by Shane.
         */
        private void drawDemo9()
        {
            if(bTextRef==0)
            {
                billboard.setupShaders(assetStore);
            }

            //Manipulate view matrix here;
            Matrix.rotateM(ViewMatrix, 0, 1.4f, 1, 0, 0);
            cubeList.get(0).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            billboard.draw(ViewMatrix, ProjectionMatrix);
            bTextRef=1;
        }

        /**
         * This method was written by Shane.
         */
        private void drawDemo10()
        {
            Matrix.rotateM(ViewMatrix, 0, 1.4f, 0, 1, 0);
            cubeList.get(0).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            billboard.draw(ViewMatrix, ProjectionMatrix);

        }

        /**
         * This method was written Shane.
         */
        private void drawDemo11()
        {
            if(bTextRef==0)
            {
                billboard.setupShaders(assetStore);
            }
            Matrix.rotateM(ViewMatrix, 0, 1.4f, 0, 0, 1);

            cubeList.get(0).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            billboard.draw(ViewMatrix, ProjectionMatrix);
            bTextRef = 1;
        }

        /**
         * Draws the skybox.
         */
        private void drawDemo12()
        {
            logger.debug("skybox is drawn");
            skyboxProgram.useProgram();
            skyboxProgram.setUniforms(PVMatrix, skyboxTexture);
            skybox.bindData(skyboxProgram);
            skybox.draw();
        }

        /**
         * Draws the skybox and a single translating cube.
         */
        private void drawDemo13()
        {
            if(bTextRef==1)
            {
                for(Cube cube : cubeList)
                {
                    cube.setUpShaders(assetStore);
                }
            }
            drawDemo12();
            cubeList.get(0).draw(ProjectionMatrix,ViewMatrix,MVMatrix,MVPMatrix);
            bTextRef = 0;
        }


    }
}
