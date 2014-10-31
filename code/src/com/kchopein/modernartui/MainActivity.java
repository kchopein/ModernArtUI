package com.kchopein.modernartui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private final int blueMultiplicationFactor = 200;
    private SeekBar mSeekBar;
    private ArrayList<FrameLayout> mFrames;
    private final int redMultiplicationFactor = 100;
    private final int greenMultiplicationFactor = 1;

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

        this.changeColors(0);
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
        for (int i = 0, mFramesSize = mFrames.size(); i < mFramesSize; i++) {
            FrameLayout currentFrame = mFrames.get(i);
            Drawable background = currentFrame.getBackground();
            if (background instanceof ColorDrawable) {
                int intColor = ((ColorDrawable) background).getColor();

                if (!isGrey(intColor)) {
                    int newGreen = getColorComponent(newColorSeed, i, greenMultiplicationFactor);
                    int newRed = getColorComponent(newColorSeed, i, redMultiplicationFactor);
                    int newBlue = getColorComponent(newColorSeed, i, blueMultiplicationFactor);
                    currentFrame.setBackgroundColor(Color.argb(255, newRed, newGreen, newBlue));
                }
            }

        }
    }

    private int getColorComponent(int newColorSeed, int frameId, int multiplicationFactor) {
        int bruteValue = Math.abs(((multiplicationFactor * frameId) + newColorSeed));

        while (bruteValue > 255)
        {
            bruteValue = Math.abs(255 + (255 - bruteValue));
        }

        return bruteValue;
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
		if (id == R.id.more_information) {
			this.showInfo();
		}
		return super.onOptionsItemSelected(item);
	}

    private void showInfo()
    {
        Builder dialogBuilder = new AlertDialog.Builder(this);
        
		OnClickListener createDialogClickListener = this.createDialogClickListener();
		dialogBuilder
        	.setMessage(R.string.info_message_text)
        	.setPositiveButton(R.string.visit_MOMA, createDialogClickListener)
        	.setNeutralButton(R.string.not_now, createDialogClickListener)
        	.create()
        	.show();
        	
    }

	private OnClickListener createDialogClickListener() {
		return new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == DialogInterface.BUTTON_POSITIVE)
				{
					String url = "http://www.moma.org/";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
				}
			}
		};
	}
}
