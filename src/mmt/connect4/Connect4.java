package mmt.connect4;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class Connect4 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove the caption bar of the application
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Window win = getWindow();
		// Request that the screen never shuts down while our app has focus
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// Remove the status bar along the top of the screen - the one with
		// battery level, signal strength, etc.
		win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Create a new frame layout to manage the view components that are
		// visible
		// a frame layout is simply the whole screen that contains a single
		// drawable item
		FrameLayout fl = new FrameLayout(getApplicationContext());
		fl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		// Create our drawing area and add it to the frame layout as the only
		// item to take up the whole screen space
		Display disp = new Display(getApplicationContext());
		fl.addView(disp);

		// Set the drawing part of our application to the frame layout, which
		// contains our custom display canvas
		setContentView(fl);

	}

	// in response to the screen rotating etc.
	// need to make sure you have
	// android:configChanges="orientation|screenSize" as part
	// of the activity tag in the manifest file
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
