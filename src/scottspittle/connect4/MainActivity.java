package scottspittle.connect4;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

//Main extends the behaviour of Activity class
public class MainActivity extends Activity {

    @Override //Override behaviour of Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        Window win = getWindow();
        
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);        
        win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout fl = new FrameLayout(getApplicationContext());

        fl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
      
        // Create our drawing area and add it to the frame layout as the only item to take up the whole screen space
        Display display = new Display(getApplicationContext());
        fl.addView(display);
        
        // Set the drawing part of our application to the frame layout, which contains our custom display canvas
        setContentView(fl);
        
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //Override original method to bypass screen orientaation
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
    	super.onConfigurationChanged(newConfig);
    	return;
    }
    
}
