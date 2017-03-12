package com.example.try_upstage.sprites;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementActionItemBaseReugularFPS;
import com.example.try_gameengine.action.MovementActionSet;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_upstage.sprites.Player.Rabbit_action;
import com.example.try_upstage.utils.BitmapUtil;

public class Floor extends Sprite{
	MovementAction floorDownMovementAction;
	
	public static final int NOTOOL =0;
	public static final int BOMB =1;
	public static final int CURE =2;
	public static final int BOMB_EXPLODE =3;
	public static final int EAT_HUMAN_TREE =4;
	public int toolNum = NOTOOL; 
	public int which; //���ئa�O
	int animStep; //�O�ơA���F���͹Ϥ��ܤ�
	
	public Bitmap drawBitmap; //ø�s�Ϥ��P�Ȧs�Ϥ���i

	Bitmap bitmap1;

	Bitmap bitmap2;

	Bitmap bitmap3;
	
	public EatHumanTree eatHumanTree;
	
	public Floor(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		// TODO Au to-generated constructor stub
		
//		this.context = context;
//		this.x = x;
//		this.y = y;
//		this.height = height;
//		this.width = width;
		Random r = new Random();
		which = r.nextInt(6); //�H�� 0~5�A�ثe�C�ئa�O�X�{���v�̼ˡA�i����ʡC
		 if (which == 0) {  //���q�a�O
				drawBitmap = BitmapUtil.footboard_normal_bitmap;
		 } else if (which == 1) { //�����a�O
				bitmap1 = BitmapUtil.footboard_moving_left1_bitmap;
				bitmap2 = BitmapUtil.footboard_moving_left2_bitmap;
				bitmap3 = BitmapUtil.footboard_moving_left3_bitmap;
				drawBitmap = bitmap1;
		 } else if (which == 2){ //���k�a�O
				drawBitmap = BitmapUtil.footboard_moving_right1_bitmap;
		 }else if(which == 3){ //��í�w�a�O
				bitmap1 = BitmapUtil.footboard_unstable1_bitmap;
				bitmap2=BitmapUtil.footboard_unstable2_bitmap;
				bitmap3=BitmapUtil.footboard_unstable3_bitmap;
				drawBitmap = bitmap1;
		 }else if(which==4){ //�ưʦa�O
//			 bitmap = BitmapUtil.footboard_spring_bitmap;
				bitmap1 = BitmapUtil.footboard_wood_bitmap;
				bitmap2=BitmapUtil.footboard_wood2_bitmap;
				bitmap3=BitmapUtil.footboard_wood3_bitmap;
			 drawBitmap = BitmapUtil.footboard_wood_bitmap;
		 }else if(which==5){ //�����a�O
			 drawBitmap = BitmapUtil.footboard_spiked_bitmap;
		 }
		 
		 int random = r.nextInt(6);
		 
			 if(random==1){
				 toolNum=random;
			 }else if(random==2){
				 toolNum=random;
			 }else if(random==4){
				 toolNum=random;
				 eatHumanTree = new EatHumanTree(getX(), getY(), false);
			 }else{
				 toolNum=0;
			 }
			 
//		setBitmapAndAutoChangeWH(drawBitmap);
			 setBitmap(drawBitmap);
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		
		if(eatHumanTree!=null){
			eatHumanTree.setPosition(x, y);
		}
	}
	
	private void initMovement(){
		floorDownMovementAction = new MovementActionSet();
		MovementActionInfo info = new MovementActionInfo(5000, 200, 2, 10, "", this, Rabbit_action.RJump.getName());
		floorDownMovementAction.addMovementAction(new MovementActionItemBaseReugularFPS(info));
		floorDownMovementAction.setActionListener(new IActionListener() {
			
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
		
		floorDownMovementAction.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
			
			@Override
			public void onTick(float dx, float dy) {
				// TODO Auto-generated method stub
				move(dx, dy);
			}
		});
		
		floorDownMovementAction.initMovementAction();
		floorDownMovementAction.start();
		
		setMovementAction(floorDownMovementAction);
		
		
		
	}
	
	/**
	 * �p��a�O�ƻ�ɭԤ����A�u�b��í�w�M����a�O�����p�U�ϥΡC
	 */
	public void setCount(){
		if(which==3 || which==4){
		animStep++;}
	}
	
//	public void draw(Canvas canvas, float speed){
//		move(0, speed);
//		drawSelf(canvas, null);
//	}
	
	/**
	 * ø�ϰʧ@
	 * @param canvas �nø�Ϫ��e��
	 * @param dy �Ϥ�Y�b���ʶZ��
	 */
	public void draw(Canvas canvas, float dy) {
		
//		this.y -= dy;
		
		move(0, -dy);
		
//		Rect rect1 = new Rect(x,y,x+width,y+height);	
		RectF rect1 = new RectF(getX(),getY(),getX()+getWidth(),getY()+getHeight());
		
		if(which == 1){ //�����a�O
			//�Ϥ�����i�A�i������A���o�̪���k�ܲ¡A���Ӧ���n����k
//			if(animStep<10){
//				bitmap = bitmap1;
//				animStep++;
//			}else if(animStep>=10 && animStep<19){
//				bitmap = bitmap2;
//				animStep++;
//			}else{
//				animStep=0;
//			}
			
			if(animStep%3==0){
				drawBitmap = BitmapUtil.footboard_moving_left1_bitmap;
				animStep++;
			}else if(animStep%3==1){
				drawBitmap = BitmapUtil.footboard_moving_left2_bitmap;
				animStep++;
			}else{
				drawBitmap = BitmapUtil.footboard_moving_left3_bitmap;
				animStep=0;
			}
			
		}else if(which == 2){
			if(animStep%3==0){
				drawBitmap = BitmapUtil.footboard_moving_right1_bitmap;
				animStep++;
			}else if(animStep%3==1){
				drawBitmap = BitmapUtil.footboard_moving_right2_bitmap;
				animStep++;
			}else{
				drawBitmap = BitmapUtil.footboard_moving_right3_bitmap;
				animStep=0;
			}
		}else if(which == 3){ //��í�w�a�O
			if(animStep<10){
				drawBitmap = bitmap1; //��l�Ϥ�
			}else if(animStep<20){
				drawBitmap = bitmap2; //���͵���
			}else if(animStep<28){
				drawBitmap = bitmap3; //���͵���
			}else if(animStep>=30){
				drawBitmap=null; //�a�O����
			}
		}else if(which == 4){ //����a�O
			if(animStep%10==0){
				drawBitmap = bitmap1;
			}else if(animStep%10==1){
				drawBitmap = bitmap2;
			}else if(animStep%10==3){
				drawBitmap = bitmap3;
			}else if(animStep%10==5){
				animStep=0;
				drawBitmap=null;
			}
		}
		
		if(drawBitmap!=null) {
//		canvas.drawBitmap(bitmap, null, rect1, null);
//			setBitmapAndAutoChangeWH(drawBitmap);
			setBitmap(drawBitmap);
//			drawSelf(canvas, null);
			canvas.drawBitmap(getBitmap(), null, rect1, null);
		}
	}
}
