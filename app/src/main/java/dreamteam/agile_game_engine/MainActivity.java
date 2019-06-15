package dreamteam.agile_game_engine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


import dreamteam.agile_game_engine.audio.SoundEffect;
import dreamteam.agile_game_engine.data.Singleton;

/**
 * This class was created by Erin, with methods created by Jonathan for GUI and interaction.
 */

public class MainActivity extends Activity
{
    private GLSurfaceView glView;
    private Button previousButton, nextButton, muteButton, restartButton;
    private boolean isMusicPlaying = true;
    Singleton sceneRef = Singleton.getInstance();
    SoundEffect spin = new SoundEffect(this,R.raw.you_spin_me_round);
    SoundEffect swag = new SoundEffect(this,R.raw.swag);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        glView = new GameLoop((this));
        setContentView(glView);
        setupGUI();
        spin.play();

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * this method sets up the buttons on the screen and allows touch/clicking to
     * be handled accordingly. The layout is set to be linear and text and images
     * are also set respectively.
     */
    protected void setupGUI()
    {
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout linear2Layout = new LinearLayout(this);
        previousButton = new Button(this);
        nextButton = new Button(this);
        muteButton = new Button(this);
        restartButton = new Button(this);
        customizeButtons();

        linear2Layout.addView(muteButton);
        linear2Layout.addView(restartButton);
        linear2Layout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        this.addContentView(linear2Layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        linearLayout.addView(previousButton);
        linearLayout.addView(nextButton);
        linearLayout.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        this.addContentView(linearLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        previousButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                swag.play();
                if(sceneRef.getData()!=0)
                {
                    sceneRef.setData(sceneRef.getData()-1);
                }
                else
                    sceneRef.setData(13);
            }
        });

        muteButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (isMusicPlaying != true)
                {
                    muteButton.setText("Pause");
                    spin.play();
                    isMusicPlaying = true;
                } else {
                    muteButton.setText("Play");
                    spin.pause();
                    isMusicPlaying = false;
                }
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,StartupActivity.class));
                spin.stop();
                finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                swag.play();
                if(sceneRef.getData()!=13) {
                    sceneRef.setData(sceneRef.getData() + 1);
                }
                else
                    sceneRef.setData(0);
            }
        });
    }

    public void customizeButtons()
    {
        previousButton.setText("  Previous  ");
        previousButton.setTextColor(Color.WHITE);
        previousButton.setBackgroundColor(Color.BLACK);
        previousButton.setTypeface(Typeface.SERIF);
        previousButton.setTextSize(18);

        muteButton.setText("  Pause  ");
        muteButton.setTextColor(Color.WHITE);
        muteButton.setBackgroundColor(Color.BLACK);
        muteButton.setTypeface(Typeface.SERIF);
        muteButton.setTextSize(18);

        restartButton.setText("  Restart  ");
        restartButton.setTextColor(Color.WHITE);
        restartButton.setBackgroundColor(Color.BLACK);
        restartButton.setTypeface(Typeface.SERIF);
        restartButton.setTextSize(18);

        nextButton.setText("  Next  ");
        nextButton.setTextColor(Color.WHITE);
        nextButton.setBackgroundColor(Color.BLACK);
        nextButton.setTypeface(Typeface.SERIF);
        nextButton.setTextSize(18);
    }

}

