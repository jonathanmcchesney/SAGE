package dreamteam.agile_game_engine.lightMocks;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import dreamteam.agile_game_engine.R;
import dreamteam.agile_game_engine.util.Logger;

/**
 * These methods were created by Shane and integrated by Jonathan.
 */

    public class LightSurfaceView extends GLSurfaceView
    {
        private final LightRenderer renderer;
        Logger log = new Logger("LightSurfaceView");

        public LightSurfaceView(Context context)
        {
            super(context);
            setEGLContextClientVersion(2);
            setEGLConfigChooser(8,8,8,8,16,0);
            this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            this.setBackgroundResource(R.drawable.bg1);
            this.setZOrderOnTop(true);
            renderer = new LightRenderer(context);
            setRenderer(renderer);
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }
}
