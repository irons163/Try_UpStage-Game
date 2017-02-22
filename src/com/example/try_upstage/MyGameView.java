package com.example.try_upstage;

import android.content.Context;
import android.graphics.Canvas;

import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;

public class MyGameView extends GameView{

	public MyGameView(Context context, IGameController gameController,
			IGameModel gameModel) {
		super(context, gameController, gameModel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}
}
