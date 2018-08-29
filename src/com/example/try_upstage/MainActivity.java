package com.example.try_upstage;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.try_gameengine.stage.BaseStage;
import com.example.try_upstage.utils.BitmapUtil;
import com.example.try_upstage.utils.CommonUtil;
//import android.support.v7.app.ActionBarActivity;

public class MainActivity extends BaseStage {
	MyGameModel gameModel;
	final public static int GIRL=0;
	final public static int BOY=1;
	public static int PLAYER_SEX = GIRL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
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

	@Override
	protected void initGame() {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �L���D
		
		setContentView(R.layout.activity_main);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		CommonUtil.screenHeight = dm.heightPixels;
		CommonUtil.screenWidth = dm.widthPixels;
		
		BitmapUtil.initBitmap(this);
  
	}

	@Override
	protected void initGameModel() {
		// TODO Auto-generated method stub
		gameModel = new MyGameModel(this, null);
	}

	@Override
	protected void initGameController() {
		// TODO Auto-generated method stub
		new MyGameController(this, gameModel);
	}
}
