package dreamteam.agile_game_engine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dreamteam.agile_game_engine.lightMocks.MockActivity;
import dreamteam.agile_game_engine.modelLoading.RajawaliActivity;

/**
 * This class was created by Jonathan.
 */

public class StartupActivity extends Activity {

    /**
     * This activity will be used to trigger either the Custom cube demo or the Rajawali Demo
     * It uses the xml layout defined in startup.xml
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        Button demo1Button = (Button) findViewById(R.id.button1);
        Button demo2Button = (Button) findViewById(R.id.button2);
        Button demo3Button = (Button) findViewById(R.id.button3);

        demo1Button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(StartupActivity.this, MainActivity.class));
            }
        });

        demo2Button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(StartupActivity.this, RajawaliActivity.class));
            }
        });

        demo3Button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(StartupActivity.this, MockActivity.class));
            }
        });

    }
}
