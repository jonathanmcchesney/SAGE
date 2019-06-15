package dreamteam.agile_game_engine.modelLoading;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

import dreamteam.agile_game_engine.R;
import dreamteam.agile_game_engine.StartupActivity;

/**
 * This class was created by Jonathan.
 */

public class RajawaliActivity extends Activity {

    /**
     * Here the buttons are declared, as well as the renderer and surface view.
     */
    private Button leftButton, rightButton, upButton, downButton, forwardButton, backwardButton, restartButton;
    private RJRenderer renderer;

    /**
     * This Activity sets up the Rajawali demo. IT takes in the default xml layout as the content view.
     * A Rajawali surfaceView is then instantiated, FPS is set to 60 and the render mode is set to render
     * when dirty for better performance. The MyGLRenderer is then instantiated and then set. The background
     * is also set here.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);;

//        surface.setEGLContextClientVersion(2);
//        surface.setEGLConfigChooser(8,8,8,8,16,0);
//        surface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//        surface.setBackgroundResource(R.drawable.bg1);
//        surface.setZOrderOnTop(true);

        surface.setFrameRate(60.0);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);

        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));
        initGUI();

        renderer = new RJRenderer(this);
        surface.setSurfaceRenderer(renderer);
    }

    /**
     * This initialises the GUI, the aesthetics of the buttons and their layout. They
     * can possibly be textured in the future. Each button has a relevant on listener
     * associated
     */
    protected void initGUI()
    {
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout linear2Layout = new LinearLayout(this);

        leftButton = new Button(this);
        rightButton = new Button(this);
        upButton = new Button(this);
        downButton = new Button(this);
        forwardButton = new Button(this);
        backwardButton = new Button(this);
        restartButton = new Button(this);

        customizeButtons();

        linear2Layout.addView(forwardButton);
        linear2Layout.addView(restartButton);
        linear2Layout.addView(backwardButton);
        linear2Layout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        this.addContentView(linear2Layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        linearLayout.addView(leftButton);
        linearLayout.addView(upButton);
        linearLayout.addView(downButton);
        linearLayout.addView(rightButton);
        linearLayout.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        this.addContentView(linearLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        leftButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                renderer.changePosition(1);
            }
        });

        upButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                renderer.changePosition(2);
            }
        });

        downButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                renderer.changePosition(3);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                renderer.changePosition(0);
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                renderer.changePosition(5);
            }
        });

        backwardButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                renderer.changePosition(4);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(RajawaliActivity.this,StartupActivity.class));
                finish();
            }
        });
    }

    /**
     * The method customises the font/type/text visible on the buttons.
     */
    public void customizeButtons()
    {
        leftButton.setText("Left ");
        leftButton.setTextColor(Color.WHITE);
        leftButton.setBackgroundColor(Color.BLACK);
        leftButton.setTypeface(Typeface.SERIF);
        leftButton.setTextSize(18);

        upButton.setText(" Up ");
        upButton.setTextColor(Color.WHITE);
        upButton.setBackgroundColor(Color.BLACK);
        upButton.setTypeface(Typeface.SERIF);
        upButton.setTextSize(18);

        downButton.setText(" Down ");
        downButton.setTextColor(Color.WHITE);
        downButton.setBackgroundColor(Color.BLACK);
        downButton.setTypeface(Typeface.SERIF);
        downButton.setTextSize(18);

        rightButton.setText(" Right");
        rightButton.setTextColor(Color.WHITE);
        rightButton.setBackgroundColor(Color.BLACK);
        rightButton.setTypeface(Typeface.SERIF);
        rightButton.setTextSize(18);

        backwardButton.setText(" Zoom In ");
        backwardButton.setTextColor(Color.WHITE);
        backwardButton.setBackgroundColor(Color.BLACK);
        backwardButton.setTypeface(Typeface.SERIF);
        backwardButton.setTextSize(18);

        forwardButton.setText(" Zoom Out ");
        forwardButton.setTextColor(Color.WHITE);
        forwardButton.setBackgroundColor(Color.BLACK);
        forwardButton.setTypeface(Typeface.SERIF);
        forwardButton.setTextSize(18);

        restartButton.setText("  Restart  ");
        restartButton.setTextColor(Color.WHITE);
        restartButton.setBackgroundColor(Color.BLACK);
        restartButton.setTypeface(Typeface.SERIF);
        restartButton.setTextSize(18);
    }
}
