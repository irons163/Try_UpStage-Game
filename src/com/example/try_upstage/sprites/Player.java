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
import com.example.try_upstage.MainActivity;
import com.example.try_upstage.MyGameModel;
import com.example.try_upstage.R;
import com.example.try_upstage.utils.BitmapUtil;

public class Player extends Sprite{
	MovementAction jumpMovement, jumpLMovement;
	int walkCount = 0;
	boolean isInjure=false;
	Bitmap bitmap, walkBitmap01, walkBitmap02, walkBitmap03, downbitmap, injureBmp;
	Thread injureThread;
	
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
				"RABIT_LDOWN", new Bitmap[] { BitmapUtil.getBitmapFromRes(R.drawable.rabit_left_stop), BitmapUtil
						.getBitmapFromRes(R.drawable.rabit_on_air_left_down) }), RDown(
				"RABIT_RDOWN", new Bitmap[] {BitmapUtil
						.getBitmapFromRes(R.drawable.rabit_right_stop), BitmapUtil
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
		// TODO Auto-generated constructor stub
		
		addActionFPS(Rabbit_action.LJump.getName(),
				Rabbit_action.LJump.getBitmaps(), new int[] { 20, 20, 20 },
				true);
		addActionFPS(Rabbit_action.RJump.getName(),
				Rabbit_action.RJump.getBitmaps(), new int[] { 20, 20, 20 },
				true);
		
		initMovement();
	}
	
	private void initMovement(){
		jumpMovement = new MovementActionSet();
		jumpMovement.setMovementActionController(new MovementAtionController());
		MovementActionInfo info = new MovementActionInfo(50, 5, 2, -160, "", null, true, this, Rabbit_action.RJump.getName());
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
		
		
		jumpLMovement = new MovementActionSet();
		info = new MovementActionInfo(5000, 200, -2, 10, "", null, true, this, Rabbit_action.LJump.getName());
		jumpLMovement.addMovementAction(new MovementActionItemBaseReugularFPS(info));
		jumpLMovement.setActionListener(new IActionListener() {
			
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
		
		jumpLMovement.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
			
			@Override
			public void onTick(float dx, float dy) {
				// TODO Auto-generated method stub
				move(dx, dy);
			}
		});
		
		jumpLMovement.initMovementAction();
		jumpLMovement.start();
		
		setMovementAction(jumpMovement);
		
		
		
		
	}

	public void onTouch(){
		jumpMovement.controller.restart();
		setMovementAction(jumpMovement);
	}
	
	
@Override
public void drawSelf(Canvas canvas, Paint paint) {
	// TODO Auto-generated method stub
	
	
	
	super.drawSelf(canvas, paint);
}
	
public void draw(Canvas canvas, float dy, float dx) {
		
		float y = getY(); 
		y -= dy; //�y��y�ܤp�A�N���Ϥ�������
		setY(y);
		float x = getX(); 
		x -= dx; //�y��x�ܤp�A�N���Ϥ����W��
		setX(x);
		
		if(isInjure){
//			canvas.drawBitmap(injureBmp, x+=dx, y, null);
			setBitmapAndAutoChangeWH(injureBmp);
			drawSelf(canvas, null);
			walkCount = 0;
			return;
		}
		
		if(dx==0 && dy>=0){
			bitmap = walkBitmap02;
//			canvas.drawBitmap(bitmap, x, y, null);
			setBitmapAndAutoChangeWH(bitmap);
			drawSelf(canvas, null);
			walkCount = 0;
		}else if(dy<0){
//			canvas.drawBitmap(downbitmap, x, y, null);
			setBitmapAndAutoChangeWH(downbitmap);
			drawSelf(canvas, null);
			walkCount = 0;
			
		//�p�G�첾����slide�t�סA�N�����a�èS�����ʡA�u�O�a�O�b�ϤH���ʡA�]���n���R���(walkBitmap02)
		//���O����k�����I�O�ASLIDERSPEED�����n�OMoveSpeed���⭿�A
		//�_�h�����a�b���ʮ� MoveSpeed - SLIDERSPEED = SLIDERSPEED �|�ɭP�~�P���R��C
		}else if(MyGameModel.SLIDERSPEED==Math.abs(dx)){
			bitmap = walkBitmap02;
//			canvas.drawBitmap(bitmap, x, y, null);
			setBitmapAndAutoChangeWH(bitmap);
			drawSelf(canvas, null);
			walkCount = 0;
		}else{
			if(walkCount%2==0){
				bitmap = walkBitmap02;
			}else if(walkCount%3==0){
				bitmap = walkBitmap01;
			}else{
				bitmap = walkBitmap03;
			}
//			canvas.drawBitmap(bitmap, x, y, null);
			setBitmapAndAutoChangeWH(bitmap);
			drawSelf(canvas, null);
			
			walkCount++;
		}
	}
	
	public void draw(Canvas canvas, float dy, float dx, boolean isInjure){
		if(isInjure){
			this.isInjure = isInjure;
			
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Player.this.isInjure=false;
				}
			};
			
			if(injureThread!=null){
				injureThread.interrupt();
			}
			
			injureThread = new Thread(runnable);
			injureThread.start();

			draw(canvas, dy, dx);
		}else{	
			draw(canvas, dy, dx);
		}
	}
	
	public void updateBitmap(int type){
		if(type==MyGameModel.LEFT){
			setPlayerBmpLeft();
		}else if(type==MyGameModel.RIGHT){
			setPlayerBmpRifgt();
		}
	}
	
	private void setPlayerBmpLeft(){
		if(MainActivity.PLAYER_SEX==MainActivity.GIRL){
			bitmap = BitmapUtil.player_girl_left02_bitmap;
			walkBitmap01 = BitmapUtil.player_girl_left01_bitmap;
			walkBitmap02 = BitmapUtil.player_girl_left02_bitmap;
			walkBitmap03 = BitmapUtil.player_girl_left03_bitmap;
			downbitmap = BitmapUtil.player_girl_down_left_bitmap;
			injureBmp = BitmapUtil.player_girl_injure_left_bitmap;
		}else{
			bitmap = BitmapUtil.player_boy_left02_bitmap;
			walkBitmap01 = BitmapUtil.player_boy_left01_bitmap;
			walkBitmap02 = BitmapUtil.player_boy_left02_bitmap;
			walkBitmap03 = BitmapUtil.player_boy_left03_bitmap;
			downbitmap = BitmapUtil.player_boy_down_left_bitmap;
			injureBmp = BitmapUtil.player_boy_injure_left_bitmap;
		}
	}
	
	private void setPlayerBmpRifgt(){
		if(MainActivity.PLAYER_SEX==MainActivity.GIRL){
			bitmap = BitmapUtil.player_girl_right02_bitmap;
			walkBitmap01 = BitmapUtil.player_girl_right01_bitmap;
			walkBitmap02 = BitmapUtil.player_girl_right02_bitmap;
			walkBitmap03 = BitmapUtil.player_girl_right03_bitmap;
			downbitmap = BitmapUtil.player_girl_down_right_bitmap;
			injureBmp = BitmapUtil.player_girl_injure_right_bitmap;
		}else{
			bitmap = BitmapUtil.player_boy_right02_bitmap;
			walkBitmap01 = BitmapUtil.player_boy_right01_bitmap;
			walkBitmap02 = BitmapUtil.player_boy_right02_bitmap;
			walkBitmap03 = BitmapUtil.player_boy_right03_bitmap;
			downbitmap = BitmapUtil.player_boy_down_right_bitmap;
			injureBmp = BitmapUtil.player_boy_injure_right_bitmap;
		}
	}
}