package com.sketchpunk.ovolume;

//http://stackoverflow.com/questions/4763464/howto-receive-media-volume-change-notifications
//http://ttf2otf.com/
//http://www.urbanfonts.com/fontsearch.php?s=Digital&searchtype=free

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;

public class MainActivity extends FragmentActivity{	
	private AudioUiGrp mGrpSystem;
	private AudioUiGrp mGrpRinger;
	public Typeface mTypeface;
	public AudioManager mAudio;
	
	/*========================================================
	Main*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        mAudio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mTypeface = Typeface.createFromAsset(getAssets(),"dsdigib.ttf");
        
        mGrpSystem = new AudioUiGrp(this,R.id.sbSystem,R.id.lblSystem,R.id.btnSystem,AudioManager.STREAM_SYSTEM);
        mGrpRinger = new AudioUiGrp(this,R.id.sbRinger,R.id.lblRinger,R.id.btnRinger,AudioManager.STREAM_RING);
    	*/
    }//func


	/*========================================================
	Menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }//func

    
	/*========================================================
	Menu*/
	protected static class AudioUiGrp implements SeekBar.OnSeekBarChangeListener,OnCheckedChangeListener{
		private SeekBar mSeekBar;
		private TextView mLabel;
		private ToggleButton mToggle;
		private int mType;
		private AudioManager mAudio;
		private int mVolume;
		private int mMaxVolume;
		
		public AudioUiGrp(MainActivity act,int seekbar,int label,int toggle,int atype){
			mType = atype;
			mAudio = act.mAudio;
			
			//Setup SeekBar
			mMaxVolume = mAudio.getStreamMaxVolume(mType);
			mVolume = mAudio.getStreamVolume(mType);
	        
	        mSeekBar = (SeekBar)act.findViewById(seekbar);
	        mSeekBar.setMax(mMaxVolume);
	        mSeekBar.setProgress(mVolume);
	        mSeekBar.setOnSeekBarChangeListener(this);
	        mSeekBar.setTag(this);
	        
	        //Setup Label
	        mLabel = (TextView)act.findViewById(label);
	        mLabel.setText(String.format("%d/%d",mVolume,mMaxVolume));
	        mLabel.setTypeface(act.mTypeface);
	        
	        mToggle = (ToggleButton)act.findViewById(toggle);
	        //mToggle.setTextOff("Mute");
	        //mToggle.setTextOn("Mute");
	        mToggle.setOnCheckedChangeListener(this);
		}//func

		private void setVolume(int vol){
			mAudio.setStreamVolume(mType,vol,AudioManager.FLAG_PLAY_SOUND);
			mLabel.setText(String.format("%d/%d",vol,mMaxVolume));
		}//func
		
		@Override
		public void onProgressChanged(SeekBar bar, int progress, boolean fromUser){
			if(fromUser) mVolume = progress;
			setVolume(progress);
		}//func

		@Override
		public void onStartTrackingTouch(SeekBar seekBar){}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar){}


		@Override
		public void onCheckedChanged(CompoundButton btn, boolean state){
			if(state){
				mSeekBar.setProgress(0);
			}else{
				System.out.println(mVolume);
				if(mVolume == 0){
					mVolume = (mMaxVolume / 2);
				}//if
				mSeekBar.setProgress(mVolume);
			}//if
			
		}//func
	}//cls
	
}//cls
