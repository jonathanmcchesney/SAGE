package dreamteam.agile_game_engine.lightMocks;

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
        import android.widget.RelativeLayout;

        import dreamteam.agile_game_engine.R;
        import dreamteam.agile_game_engine.StartupActivity;

/**
 * These methods were created by Shane and integrated by Jonathan.
 */

public class MockActivity extends Activity
{
    private GLSurfaceView surfaceView;
    private Button intensityButton, positionButton, restartButton;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        RelativeLayout baseLayout = (RelativeLayout)findViewById(R.id.base);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        surfaceView = new LightSurfaceView(this);
        setContentView(surfaceView);
        setupGUI();

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        surfaceView.onPause();
    }

    protected void setupGUI()
    {
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout linear2Layout = new LinearLayout(this);
        intensityButton = new Button(this);
        positionButton = new Button(this);
        restartButton = new Button(this);
        customizeButtons();

        linear2Layout.addView(restartButton);
        linear2Layout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        this.addContentView(linear2Layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        linearLayout.addView(intensityButton);
        linearLayout.addView(positionButton);
        linearLayout.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        this.addContentView(linearLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        intensityButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Change intensity Logic here
            }
        });


        restartButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(MockActivity.this,StartupActivity.class));
                finish();
            }
        });

        positionButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Change position logic here
            }
        });
    }

    public void customizeButtons()
    {
        intensityButton.setText("");
        intensityButton.setTextColor(Color.WHITE);
        intensityButton.setBackgroundColor(Color.BLACK);
        intensityButton.setTypeface(Typeface.SERIF);
        intensityButton.setTextSize(18);
        restartButton.setText("  Restart  ");
        restartButton.setTextColor(Color.WHITE);
        restartButton.setBackgroundColor(Color.BLACK);
        restartButton.setTypeface(Typeface.SERIF);
        restartButton.setTextSize(18);
        positionButton.setText("");
        positionButton.setTextColor(Color.WHITE);
        positionButton.setBackgroundColor(Color.BLACK);
        positionButton.setTypeface(Typeface.SERIF);
        positionButton.setTextSize(18);
    }
}