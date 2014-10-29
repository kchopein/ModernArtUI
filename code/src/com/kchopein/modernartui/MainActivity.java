package com.kchopein.modernartui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private SeekBar mSeekBar;
    private ArrayList<FrameLayout> mFrames;

    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIElements();
    }

    private void initializeUIElements()
    {
        // SeekBar:
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(getSeekBarChangeListener());

        // Frames:
        mFrames = new ArrayList<FrameLayout>();
        mFrames.add((FrameLayout) findViewById(R.id.frame1));
        mFrames.add((FrameLayout) findViewById(R.id.frame2));
        mFrames.add((FrameLayout) findViewById(R.id.frame3));
        mFrames.add((FrameLayout) findViewById(R.id.frame4));
        mFrames.add((FrameLayout) findViewById(R.id.frame5));
        mFrames.add((FrameLayout) findViewById(R.id.frame6));
        mFrames.add((FrameLayout) findViewById(R.id.frame7));
    }

    private SeekBar.OnSeekBarChangeListener getSeekBarChangeListener()
    {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                changeColors(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    private void changeColors(int newColorSeed)
    {
        for (FrameLayout currentFrame : mFrames)
        {
            Drawable background = currentFrame.getBackground();
            if(background instanceof ColorDrawable)
            {
                int intColor = ((ColorDrawable) background).getColor();

                if(!isGrey(intColor))
                {
                    int newRed = Math.abs(Color.red(intColor)+newColorSeed);
                    int newBlue = Math.abs(Color.red(intColor)-newColorSeed);
                    currentFrame.setBackgroundColor(Color.argb(255, newRed, Color.green(intColor), newBlue));
                }
            }

        }
    }

    private boolean isGrey(int intColor) {
        return Color.red(intColor) == Color.blue(intColor) && Color.blue(intColor) == Color.green(intColor);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
