package com.example.try_upstage.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.try_gameengine.action.GravityController;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionItemMoveByGravity;
import com.example.try_gameengine.action.MovementAtionController;
import com.example.try_gameengine.action.info.MovementActionMoveByGravityInfo;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_upstage.MainActivity;
import com.example.try_upstage.MyGameModel;
import com.example.try_upstage.R;
import com.example.try_upstage.utils.BitmapUtil;

public class Player extends Sprite {
	MovementAction jumpMovement, jumpLMovement, downMovement;
	int walkCount = 0;
	boolean isInjure = false;
	Bitmap bitmap, walkBitmap01, walkBitmap02, walkBitmap03, downbitmap,
			injureBmp;
	Thread injureThread;
	public Dir dir = Dir.NONE;
	private float moveX, moveY;
	
	public enum Dir{
		NONE,
		UP,
		DOWN
	}

	enum Rabbit_action {

		LMove(
				"RABIT_LMOVE",
				new Bitmap[] {
						BitmapUtil.getBitmapFromRes(R.drawable.rabit_left_stop),
						BitmapUtil
								.getBitmapFromRes(R.drawable.rabit_on_ground_left_jump0),
						BitmapUtil
								.getBitmapFromRes(R.drawable.rabit_on_ground_left_jump1), }), RMove(
				"RABIT_RMOVE",
				new Bitmap[] {
						BitmapUtil
								.getBitmapFromRes(R.drawable.rabit_right_stop),
						BitmapUtil
								.getBitmapFromRes(R.drawable.rabit_on_ground_right_jump0),
						BitmapUtil
								.getBitmapFromRes(R.drawable.rabit_on_ground_right_jump1) }), LJump(
				"RABIT_LJUMP",
				new Bitmap[] { BitmapUtil
						.getBitmapFromRes(R.drawable.rabit_on_ground_left_jump0) }), RJump(
				"RABIT_RJUMP",
				new Bitmap[] { BitmapUtil
						.getBitmapFromRes(R.drawable.rabit_on_ground_right_jump0) }), LDown(
				"RABIT_LDOWN",
				new Bitmap[] {
						BitmapUtil.getBitmapFromRes(R.drawable.rabit_left_stop),
						BitmapUtil
								.getBitmapFromRes(R.drawable.rabit_on_air_left_down) }), RDown(
				"RABIT_RDOWN",
				new Bitmap[] {
						BitmapUtil
								.getBitmapFromRes(R.drawable.rabit_right_stop),
						BitmapUtil
								.getBitmapFromRes(R.drawable.rabit_on_air_right_down) });

		String name;
		Bitmap[] bitmaps;

		private Rabbit_action(String name, Bitmap[] bitmaps) {
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

	public Player(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		
		this.setCollisionRectF(new RectF(getLeft(), getTop(), getLeft() + getWidth(), getTop() + getHeight()));
		addActionFPS(Rabbit_action.LJump.getName(),
				Rabbit_action.LJump.getBitmaps(), new int[] { 20, 20, 20 },
				true);
		addActionFPS(Rabbit_action.RJump.getName(),
				Rabbit_action.RJump.getBitmaps(), new int[] { 20, 20, 20 },
				true);

		initMovement();
	}

	private void initMovement() {
		GravityController gravityController = new GravityController(new PointF(50, -450));
		gravityController.setAy(300);
		MovementActionMoveByGravityInfo gravityInfo = new MovementActionMoveByGravityInfo(8000, gravityController);
		jumpMovement = new MovementActionItemMoveByGravity(gravityInfo);
		jumpMovement.setMovementActionController(new MovementAtionController());
		jumpMovement.getInfo().setSprite(this);
		jumpMovement.getInfo().setSpriteActionName(Rabbit_action.RJump.getName());
		
		jumpMovement.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
			
			@Override
			public void onTick(float dx, float dy) {
				// TODO Auto-generated method stub
//				move(dx, dy);
//				moveX = dx;
				move(dx, 0);
				moveY = dy;
				
				if(dy > 0){
					dir = Dir.DOWN;
				}else if(dy < 0){
					dir = Dir.UP;
				}else{
					dir = Dir.NONE;
				}
			}
		});
		
		jumpMovement.initMovementAction();
		
		gravityController = new GravityController(new PointF(-50, -450));
		gravityController.setAy(300);
		gravityInfo = new MovementActionMoveByGravityInfo(8000, gravityController);
		jumpLMovement = new MovementActionItemMoveByGravity(gravityInfo);
		jumpLMovement.setMovementActionController(new MovementAtionController());
		jumpLMovement.getInfo().setSprite(this);
		jumpLMovement.getInfo().setSpriteActionName(Rabbit_action.RJump.getName());
		
		jumpLMovement.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
			
			@Override
			public void onTick(float dx, float dy) {
				// TODO Auto-generated method stub
//				move(dx, dy);
//				moveX = dx;
				move(dx, 0);
				moveY = dy;
				
				if(dy > 0){
					dir = Dir.DOWN;
				}else if(dy < 0){
					dir = Dir.UP;
				}else{
					dir = Dir.NONE;
				}
			}
		});
		
		jumpLMovement.initMovementAction();
		
		
		gravityController = new GravityController(new PointF(0, 0));
		gravityController.setAy(100);
		gravityInfo = new MovementActionMoveByGravityInfo(8000, gravityController);
		downMovement = new MovementActionItemMoveByGravity(gravityInfo);
		downMovement.setMovementActionController(new MovementAtionController());
		downMovement.getInfo().setSprite(this);
		downMovement.getInfo().setSpriteActionName(Rabbit_action.RJump.getName());
		
		downMovement.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
			
			@Override
			public void onTick(float dx, float dy) {
				// TODO Auto-generated method stub
//				move(dx, dy);
//				moveX = dx;
				move(dx, 0);
				moveY = dy;
				
				dir = Dir.NONE;
			}
		});
		
		downMovement.initMovementAction();
	}

	public void jumpToRight() {
		jumpMovement.controller.restart();
//		jumpMovement.controller.cancelAllMove();
		setMovementAction(jumpMovement);
	}
	
	public void jumpToLeft() {
		jumpLMovement.controller.restart();
		setMovementAction(jumpLMovement);
	}
	
	public void down(){
		downMovement.controller.restart();
		setMovementAction(downMovement);
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		super.drawSelf(canvas, paint);
	}

	public void draw(Canvas canvas, float dy, float dx) {
		float y = getY();
		dy -= moveY;
//		dx -= moveX;
		y -= dy;
		setY(y);
		float x = getX();
		x -= dx;
		setX(x);

		if (isInjure) {
			// canvas.drawBitmap(injureBmp, x+=dx, y, null);
			setBitmapAndAutoChangeWH(injureBmp);
			drawSelf(canvas, null);
			walkCount = 0;
			return;
		}

		if ((dx == 0 || dx == MyGameModel.MOVESPEED) && dy >= 0) {
			bitmap = walkBitmap02;
			// canvas.drawBitmap(bitmap, x, y, null);
			setBitmapAndAutoChangeWH(bitmap);
			drawSelf(canvas, null);
			walkCount = 0;
		} else if (dy < 0) {
			// canvas.drawBitmap(downbitmap, x, y, null);
			setBitmapAndAutoChangeWH(downbitmap);
			drawSelf(canvas, null);
			walkCount = 0;
//			bitmap = walkBitmap02;
//			setBitmapAndAutoChangeWH(bitmap);
//			drawSelf(canvas, null);
//			walkCount = 0;
		} else if (MyGameModel.SLIDERSPEED == Math.abs(dx)) {
			bitmap = walkBitmap02;
			// canvas.drawBitmap(bitmap, x, y, null);
			setBitmapAndAutoChangeWH(bitmap);
			drawSelf(canvas, null);
			walkCount = 0;
		} else {
			if (walkCount % 30 == 0) {
				bitmap = walkBitmap02;
			} else if (walkCount % 60 == 0) {
				bitmap = walkBitmap01;
				walkCount = 0;
			} else {
				bitmap = walkBitmap03;
			}
			// canvas.drawBitmap(bitmap, x, y, null);
			setBitmapAndAutoChangeWH(bitmap);
			drawSelf(canvas, null);
			walkCount++;
		}
	}

	public void draw(Canvas canvas, float dy, float dx, boolean isInjure) {
		if (isInjure) {
			this.isInjure = isInjure;
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Player.this.isInjure = false;
				}
			};

			if (injureThread != null) {
				injureThread.interrupt();
			}

			injureThread = new Thread(runnable);
			injureThread.start();

			draw(canvas, dy, dx);
		} else {
			draw(canvas, dy, dx);
		}
	}

	public void updateBitmap(int type) {
		if (type == MyGameModel.LEFT) {
			setPlayerBmpLeft();
		} else if (type == MyGameModel.RIGHT) {
			setPlayerBmpRifgt();
		}
	}

	private void setPlayerBmpLeft() {
		if (MainActivity.PLAYER_SEX == MainActivity.GIRL) {
			bitmap = BitmapUtil.player_girl_left02_bitmap;
			walkBitmap01 = BitmapUtil.player_girl_left01_bitmap;
			walkBitmap02 = BitmapUtil.player_girl_left02_bitmap;
			walkBitmap03 = BitmapUtil.player_girl_left03_bitmap;
			downbitmap = BitmapUtil.player_girl_down_left_bitmap;
			injureBmp = BitmapUtil.player_girl_injure_left_bitmap;
		} else {
			bitmap = BitmapUtil.player_boy_left02_bitmap;
			walkBitmap01 = BitmapUtil.player_boy_left01_bitmap;
			walkBitmap02 = BitmapUtil.player_boy_left02_bitmap;
			walkBitmap03 = BitmapUtil.player_boy_left03_bitmap;
			downbitmap = BitmapUtil.player_boy_down_left_bitmap;
			injureBmp = BitmapUtil.player_boy_injure_left_bitmap;
		}
	}

	private void setPlayerBmpRifgt() {
		if (MainActivity.PLAYER_SEX == MainActivity.GIRL) {
			bitmap = BitmapUtil.player_girl_right02_bitmap;
			walkBitmap01 = BitmapUtil.player_girl_right01_bitmap;
			walkBitmap02 = BitmapUtil.player_girl_right02_bitmap;
			walkBitmap03 = BitmapUtil.player_girl_right03_bitmap;
			downbitmap = BitmapUtil.player_girl_down_right_bitmap;
			injureBmp = BitmapUtil.player_girl_injure_right_bitmap;
		} else {
			bitmap = BitmapUtil.player_boy_right02_bitmap;
			walkBitmap01 = BitmapUtil.player_boy_right01_bitmap;
			walkBitmap02 = BitmapUtil.player_boy_right02_bitmap;
			walkBitmap03 = BitmapUtil.player_boy_right03_bitmap;
			downbitmap = BitmapUtil.player_boy_down_right_bitmap;
			injureBmp = BitmapUtil.player_boy_injure_right_bitmap;
		}
	}
}
