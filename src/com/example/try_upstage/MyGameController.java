package com.example.try_upstage;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.Window;

import com.example.try_gameengine.framework.GameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_upstage.utils.BitmapUtil;
import com.example.try_upstage.utils.Common;

public class MyGameController extends GameController{
	MyGameView gameView;
	
	public MyGameController(Activity activity, IGameModel gameModel) {
		super(activity, gameModel);
		// TODO Auto-generated constructor stub
		initStart();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initGameView(Activity activity, IGameModel gameModel) {
		// TODO Auto-generated method stub
		gameView = new MyGameView(activity, this, gameModel);
	}

	@Override
	protected void arrangeView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setActivityContentView(Activity activity) {
		// TODO Auto-generated method stub
//		activity.requestWindowFeature(Window.FEATURE_NO_TITLE); // µL¼ÐÃD
		activity.setContentView(gameView);
		
//		DisplayMetrics dm = new DisplayMetrics();
//		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//		
//		Common.screenHeight = dm.heightPixels;
//		Common.screenWidth = dm.widthPixels;
//		
//		BitmapUtil.initBitmap(activity);
	}

	@Override
	protected void beforeGameStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterGameStart() {
		// TODO Auto-generated method stub
		
	}

}
