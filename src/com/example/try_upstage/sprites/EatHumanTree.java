package com.example.try_upstage.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementActionItemBaseReugularFPS;
import com.example.try_gameengine.action.MovementActionSet;
import com.example.try_gameengine.action.MovementAtionController;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_upstage.R;
import com.example.try_upstage.sprites.Player.Rabbit_action;
import com.example.try_upstage.utils.BitmapUtil;

public class EatHumanTree extends Sprite{
	MovementAction jumpMovement, jumpLMovement;
	
	boolean isEatingCanHurtPlayer = false; 
	boolean isActionFinished = true;
	
	enum EatHumanTree_Action {

		Eat(
				"EAT",
				new Bitmap[] {
						BitmapUtil.eat_human_tree01,
						BitmapUtil.eat_human_tree02,
						BitmapUtil.eat_human_tree03
				});

		String name;
		Bitmap[] bitmaps;

		private EatHumanTree_Action(String name, Bitmap[] bitmaps) {
			this.name = name;
			this.bitmaps = bitmaps;
		}

		public String getName() {
			return name;
		}

		public Bitmap[] getBitmaps() {
			return bitmaps;
		}
	}
	
	public EatHumanTree(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		// TODO Auto-generated constructor stub
		setBitmapAndAutoChangeWH(BitmapUtil.eat_human_tree01);
		
		setPosition(x, y);
		
		addActionFPS(EatHumanTree_Action.Eat.getName(),
				Rabbit_action.LJump.getBitmaps(), new int[] { 20, 20, 20 },
				true, new com.example.try_gameengine.framework.IActionListener() {
					
					@Override
					public void beforeChangeFrame(int nextFrameId) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterChangeFrame(int periousFrameId) {
						// TODO Auto-generated method stub
						if(periousFrameId==1){
							isEatingCanHurtPlayer = true;
						}else{
							isEatingCanHurtPlayer = false;
						}
					}
					
					@Override
					public void actionFinish() {
						// TODO Auto-generated method stub
						isActionFinished = true;
					}
				});
		
//		addActionFPS(Rabbit_action.RJump.getName(),
//				Rabbit_action.RJump.getBitmaps(), new int[] { 20, 20, 20 },
//				true);
		
//		initMovement();
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
//		super.setPosition(x, y);
		setX(x+30);
		setY(y-getHeight());
	}
	
	private void initMovement(){
		jumpMovement = new MovementActionSet();
		jumpMovement.setMovementActionController(new MovementAtionController());
		MovementActionInfo info = new MovementActionInfo(50, 5, 2, -160, "", this, EatHumanTree_Action.Eat.getName());
		jumpMovement.addMovementAction(new MovementActionItemBaseReugularFPS(info));
		jumpMovement.setActionListener(new IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void actionStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void actionFinish() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void actionCycleFinish() {
				// TODO Auto-generated method stub
				
			}
		});
		
		jumpMovement.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
			
			@Override
			public void onTick(float dx, float dy) {
				// TODO Auto-generated method stub
				move(dx, dy);
			}
		});
		
		jumpMovement.initMovementAction();
		jumpMovement.start();
		
		
//		jumpLMovement = new MovementActionSet();
//		info = new MovementActionInfo(5000, 200, -2, 10, "", null, true, this, Rabbit_action.LJump.getName());
//		jumpLMovement.addMovementAction(new MovementActionItemBaseReugularFPS(info));
//		jumpLMovement.setActionListener(new IActionListener() {
//			
//			@Override
//			public void beforeChangeFrame(int nextFrameId) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void afterChangeFrame(int periousFrameId) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void actionStart() {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void actionFinish() {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void actionCycleFinish() {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//		jumpLMovement.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
//			
//			@Override
//			public void onTick(float dx, float dy) {
//				// TODO Auto-generated method stub
//				move(dx, dy);
//			}
//		});
//		
//		jumpLMovement.initMovementAction();
//		jumpLMovement.start();
		
			
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
	}
	
	public boolean eatStartAndDetectHurtPlayer(){
		boolean isEat = false;
		if(isActionFinished){
//			setMovementAction(jumpMovement);
			setAction(EatHumanTree_Action.Eat.getName());
			isActionFinished = false;
		}
		
		if(isEatingCanHurtPlayer){
			isEat = isEatingCanHurtPlayer;
		}
		
		return isEat;
	}
}
