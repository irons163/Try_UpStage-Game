package com.example.try_upstage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.Data;
import com.example.try_gameengine.framework.GameModel;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.TouchDispatcher;
import com.example.try_gameengine.scene.DialogScene;
import com.example.try_gameengine.utils.GameTimeUtil;
import com.example.try_upstage.model.Background;
import com.example.try_upstage.sprites.Controler;
import com.example.try_upstage.sprites.EatHumanTree;
import com.example.try_upstage.sprites.FireBall;
import com.example.try_upstage.sprites.Floor;
import com.example.try_upstage.sprites.Player;
import com.example.try_upstage.sprites.Player.Dir;
import com.example.try_upstage.sprites.ToolUtil;
import com.example.try_upstage.utils.BitmapUtil;
import com.example.try_upstage.utils.CommonUtil;

public class MyGameModel extends GameModel {
	Background background;
	List<Floor> floors = new ArrayList<Floor>();
	ArrayList<ArrayList<Floor>> footboards = new ArrayList<ArrayList<Floor>>();
	ArrayList<Floor> footboardsTheSameLine = new ArrayList<Floor>();
	int footboardHeight, footboardWidth; // 地板高寬
	ArrayList<Integer> currentXs = new ArrayList<Integer>();
	int currentX = 200;
	int DISTANCE_MULTIPLE = 50;
	Floor floor;
	private final Object CREATE_FOOTBAR_LOCK = new Object();
	int height, width; // 螢幕高寬
	Floor footboard;
	private final float BASE_SPEED = (float) CommonUtil.screenWidth / 300f;
	float SPEED = BASE_SPEED;
	int drawCount;
	Player player;
	final static int SMOOTH_DEVIATION = 4;
	public static ToolUtil toolExplodingUtil;
	float playerMoveSpeed = 0;
	public static final float MOVESPEED = (float) CommonUtil.screenWidth / 66;
	public static final float SLIDERSPEED = MOVESPEED / 3;
	boolean playerDownOnFootBoard = false;
	boolean playerStandOnFootboard = false;
	private int move = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	int whichFoorbar = 0;
	int life = 90;
	public final float DOWNSPEED = BASE_SPEED * 3.3f;
	Paint paint = new Paint();
	ArrayList<FireBall> fireballs = new ArrayList<FireBall>();
	boolean isPressLeftMoveBtn, isPressRightMoveBtn;
	Controler controler;
	boolean isInjure = false;
	boolean isDrawPlayer = true;
	final static int GAME_TIME = 5;
	int count;
	GameTimeUtil gameTimeUtil;
	
	public MyGameModel(Context context, Data data) {
		super(context, data);
		background = new Background(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.background2));
		background.setPosition(0, 0);

		Floor floor = new Floor(0, 0, false);
		floor.setPosition(100, 500);
		floor.setBitmapAndAutoChangeWH(BitmapUtil.floor);

		floors.add(floor);
		footboardWidth = (int) CommonUtil.screenWidth
				/ BitmapUtil.FOOTBOARD_WIDTH_PERSENT;
		footboardHeight = (int) ((float) BitmapUtil.footboard_normal_bitmap
				.getHeight() / BitmapUtil.footboard_normal_bitmap.getWidth() * footboardWidth);

		width = CommonUtil.screenWidth;
		height = CommonUtil.screenHeight;

		currentXs.add(currentX);

		createFloor();
		initPlayer();

		this.controler = new Controler(context, this.getHeight(),
				this.getWidth());
		
		gameTimeUtil = new GameTimeUtil(1000);
	}

	public void initFool() {

	}

	public void initPlayer() {
		player = new Player(200, 50, false);
		player.setPosition(200, 800);
		player.setBitmapAndAutoChangeWH(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.frog_down));

		player.updateBitmap(LEFT);
	}

	@Override
	protected void doDraw(Canvas canvas) {
		
		super.doDraw(canvas);


		background.draw(canvas);
		controler.paint(canvas);


                               
		for (int j = 0; j < footboards.size(); j++) {
			ArrayList<Floor> f = footboards.get(j);
			for (int k = 0; k < f.size(); k++) {
				Floor ene = f.get(k);
				ToolUtil toolUtil = ene.toolUtil;
				EatHumanTree eatHumanTree = ene.eatHumanTree;
				
				if (ene.toolNum == Floor.BOMB_EXPLODE
						&& toolExplodingUtil != null) {
					if (toolExplodingUtil.isExploding) {
						toolUtil = null;
						if (isDrawPlayer) {
							isDrawPlayer = false;
							player.draw(canvas, SPEED, 0, isInjure);
							toolExplodingUtil.draw(canvas, 0);
						}
					} else {
						toolExplodingUtil = null;
						ene.toolNum = Floor.NOTOOL;
					}
				}

				if (toolUtil != null) {
					toolUtil.draw(canvas, 0);
				}

				if (eatHumanTree != null) {
					eatHumanTree.drawSelf(canvas, paint);
				}

				if (ene.getY() > 0 - SPEED && ene.drawBitmap != null) {
//					ene.draw(canvas, SPEED);
					ene.drawSelf(canvas, paint); 
				}
			}
		}


		Bitmap bitmap = BitmapUtil.top_spiked_bar;
		float topSpikedBarHeight = (float) bitmap.getHeight()
				/ bitmap.getWidth() * width;
		RectF rectTopSpikedBar = new RectF(0, 0, width, topSpikedBarHeight);
		canvas.drawBitmap(bitmap, null, rectTopSpikedBar, null);

		if (topSpikedBarHeight >= player.getY()) {
			injure(90);
		}

		float playerDy = 0;
		float playerDx = 0;
		if (isDrawPlayer) {
			if (playerStandOnFootboard) {
				if (move == LEFT) {
					if (player.getX() <= 0) {
						playerDy = -SPEED;
						playerDx = 0;
						// player.draw(canvas, SPEED, 0, isInjure);
						move = 0;
					} else {
						playerDy = -SPEED;
						playerDx = MOVESPEED + playerMoveSpeed;
						// player.draw(canvas, SPEED, MOVESPEED +
						// playerMoveSpeed, isInjure);
					}
				} else if (move == RIGHT) {
					if (player.getX() + player.getWidth() >= width) {
						playerDy = -SPEED;
						playerDx = 0;
						// player.draw(canvas, SPEED, 0, isInjure);
						move = 0;
					} else {
						playerDy = -SPEED;
						playerDx = -MOVESPEED + playerMoveSpeed;
						// player.draw(canvas, SPEED, -MOVESPEED +
						// playerMoveSpeed, isInjure);
					}
				} else {
					if (whichFoorbar == 1) {
						playerDy = -SPEED;
						playerDx = playerMoveSpeed;
						// player.draw(canvas, SPEED, playerMoveSpeed,
						// isInjure);
					} else if (whichFoorbar == 2) {
						playerDy = -SPEED;
						playerDx = playerMoveSpeed;
						// player.draw(canvas, SPEED, playerMoveSpeed,
						// isInjure);
					} else {
						playerDy = -SPEED;
						playerDx = 0;
						// player.draw(canvas, SPEED, 0, isInjure);
					}
				}
			} else {
				if (move == LEFT) {
					if (player.getX() <= 0) {
						playerDy = -DOWNSPEED;
						playerDx = 0;
						// player.draw(canvas, -DOWNSPEED, 0, isInjure);
						move = 0;
					} else {
						playerDy = -DOWNSPEED;
						playerDx = 8;
						// player.draw(canvas, -DOWNSPEED, 8, isInjure);
					}
				} else if (move == RIGHT) {
					if (player.getX() + player.getWidth() >= width) {
						playerDy = -DOWNSPEED;
						playerDx = 0;
						// player.draw(canvas, -DOWNSPEED, 0, isInjure);
						move = 0;
					} else {
						playerDy = -DOWNSPEED;
						playerDx = -8;
						// player.draw(canvas, -DOWNSPEED, -8, isInjure);
					}
				} else {
					playerDy = -DOWNSPEED;
					playerDx = 0;
					// player.draw(canvas, -DOWNSPEED, 0, isInjure);
				}
				playerDownOnFootBoard = true;
				Log.e("playerDownOnFootBoard", "true");
				
//				if(player.dir == Dir.DOWN){
//					playerDy = 0;
//				}
			}
		}

		for (int i = 0; i < fireballs.size(); i++) {
			FireBall ball = fireballs.get(i);
			if (player.getY() < ball.y + ball.fireballHeight
					&& player.getY() > ball.y
					&& player.getX() + player.getWidth() > ball.x
							+ SMOOTH_DEVIATION
					&& player.getX() < ball.x + ball.fireballWidth
							- SMOOTH_DEVIATION) {
				injure(90);
				if (isDrawPlayer) {
					isDrawPlayer = false;
					player.draw(canvas, playerDy, playerDx, isInjure);
				}
			}
//			ball.draw(canvas, -(SPEED + SPEED), 0);
			ball.draw(canvas, 0, 0);
		}

		if (isDrawPlayer) {
			isDrawPlayer = false;
			player.draw(canvas, playerDy, playerDx, isInjure);
			Log.e("player", "down");
		}else{
			Log.e("player", "no down");
		}
		int lifeBarX = 0;
		Bitmap lifeBgBmp = BitmapUtil.life_bg;
		Bitmap lifeBmp = BitmapUtil.life;
		RectF rect5 = new RectF(width - lifeBarX - lifeBgBmp.getWidth(),
				topSpikedBarHeight, width - lifeBarX, topSpikedBarHeight
						+ lifeBgBmp.getHeight());
		canvas.drawBitmap(lifeBgBmp, null, rect5, null);
		RectF rect4 = new RectF(
				width
						- lifeBarX
						- ((lifeBmp.getWidth() * (life / 30)) / 3)
						- (((float) (lifeBgBmp.getWidth() - lifeBmp.getWidth()) / 2)),
				topSpikedBarHeight
						+ (((float) (lifeBgBmp.getHeight() - lifeBmp
								.getHeight()) / 2)),
				width
						- lifeBarX
						- (((float) (lifeBgBmp.getWidth() - lifeBmp.getWidth()) / 2)),
				topSpikedBarHeight
						+ lifeBmp.getHeight()
						+ (((float) (lifeBgBmp.getHeight() - lifeBmp
								.getHeight()) / 2)));
		Rect rectLife;
		if (life == 0 || player.getY() > height) {
			rectLife = new Rect(0, 0, 0, 0);
		} else if (life == 30) {
			rectLife = new Rect(lifeBmp.getWidth() * 2 / 3, 0,
					lifeBmp.getWidth(), lifeBmp.getHeight());
		} else if (life == 60) {
			rectLife = new Rect(lifeBmp.getWidth() / 3, 0, lifeBmp.getWidth(),
					lifeBmp.getHeight());
		} else {
			rectLife = new Rect(0, 0, lifeBmp.getWidth(), lifeBmp.getHeight());
		}

		canvas.drawBitmap(lifeBmp, rectLife, rect4, null);
		
		drawInjure(canvas);
		
		LayerManager.getInstance().drawLayers(canvas, paint);
	}

	private void injure(int willLoseLife) {
		isInjure = true;
		life -= willLoseLife;
		if (life < 0) {
			life = 0;
		}
	}
	
	private void drawInjure(Canvas canvas){
		if(!isInjure)
			return;
		paint.setColor(Color.RED);
		paint.setAlpha(100);
		Rect rect6 = new Rect(0, 0, width, height);
		canvas.drawRect(rect6, paint);
	}

	@Override
	protected void process() {
		super.process();
		
		isDrawPlayer = true;
		TouchDispatcher.getInstance().dispatch();
		
		if(!gameFlag)
			return;
		
		if (gameTimeUtil.isArriveExecuteTime()) {
			if (gameFlag)
				count++;
		}
		
		if(gameFlag){
			isGameWinLose();
		}

		isInjure = false;
		
		float footboardByPlayerX, footboardByPlayerY = 0;
		int re = 0;
		playerStandOnFootboard = false;
		for (int j = 0; j < footboards.size(); j++) {
			ArrayList<Floor> f = footboards.get(j);
			for (int k = 0; k < f.size(); k++) {
				boolean remove = false;
				Floor ene = f.get(k);

				ToolUtil toolUtil = null;
				EatHumanTree eatHumanTree = null;
				if (ene.toolNum == Floor.NOTOOL) {
					toolUtil = ene.toolUtil = null;
				} else if (ene.toolNum == Floor.BOMB) {
					toolUtil = ene.toolUtil;
				} else if (ene.toolNum == Floor.BOMB_EXPLODE) {
					toolUtil = ene.toolUtil = null;
				} else if (ene.toolNum == Floor.EAT_HUMAN_TREE) {
					eatHumanTree = ene.eatHumanTree;
				} else {
					toolUtil = ene.toolUtil;
				}
				
				if(RectF.intersects(player.getFrameInScene(), ene.getFrameInScene()) && player.dir != Dir.UP){
					if (toolUtil != null
							&& (toolUtil.tool_x < player.getX()
									+ player.getWidth() - SMOOTH_DEVIATION * 4)
							&& (toolUtil.tool_x + toolUtil.tool_width > player
									.getX() + SMOOTH_DEVIATION * 4)
							&& ene.toolNum != Floor.BOMB_EXPLODE) {
						if (ene.toolNum == Floor.BOMB) {
							injure(60);
							ene.toolNum = Floor.BOMB_EXPLODE;
							toolExplodingUtil = new ToolUtil(ene.getX(),
									ene.getY(), Floor.BOMB_EXPLODE);
						
						} else if (ene.toolNum == Floor.CURE) {
							life = 90;
							ene.toolNum = Floor.NOTOOL;
						}
					}

					if (eatHumanTree != null
							&& (eatHumanTree.getX() < player.getX()
									+ player.getWidth() - SMOOTH_DEVIATION * 4)
							&& (eatHumanTree.getX() + eatHumanTree.getWidth() > player
									.getX() + SMOOTH_DEVIATION * 4)) {
						if (eatHumanTree.eatStartAndDetectHurtPlayer()) {
							injure(30);
						}
					}

					if (playerDownOnFootBoard) {
						whichFoorbar = 0;
						playerMoveSpeed = 0;
						if (ene.which == 5) {
							injure(30);
						}
						if (ene.which == 1) {
							whichFoorbar = 1;
							playerMoveSpeed = SLIDERSPEED;
						} else if (ene.which == 2) {
							whichFoorbar = 2;
							playerMoveSpeed = -SLIDERSPEED;
						}
						
						player.removeAllMovementActions();
						player.dir = Dir.NONE;
						
						Log.e("RE", "removeAllMovementActions" + player.dir);
					}

					playerStandOnFootboard = true;
					footboardByPlayerY = ene.getY();
					player.setY(footboardByPlayerY - player.getHeight() +1);
					ene.setCount();
					playerDownOnFootBoard = false;
					

				}

				if (ene.getY() > 0 - SPEED && ene.drawBitmap != null) {

				} else {
					remove = true;
					re = k;
				}

				if (remove) {
					f.remove(re);
					k--;
				}
			}
		}
		
		if(!playerStandOnFootboard)
			Log.e("playerStandOnFootboard", "false" 
					 + player.dir);
			
		background.update();
		for (Floor floor : floors) {
			floor.frameTrig();
		}

		for (ArrayList<Floor> floors : footboards) {
			for (Floor floor : floors) {
//				floor.move(0, SPEED);
				floor.moveByY(-SPEED);
				floor.frameTrig();
				if (floor.eatHumanTree != null) {
					floor.eatHumanTree.move(0, SPEED);
					floor.eatHumanTree.frameTrig();
				}
				if(floor.toolUtil!=null)
					floor.toolUtil.tool_y += SPEED;
			}
		}

		drawCount++;
		if (drawCount % ((int) ((float) 60 / SPEED * BASE_SPEED)) == 0) {
			synchronized (CREATE_FOOTBAR_LOCK) {
				CREATE_FOOTBAR_LOCK.notifyAll();
			}
			drawCount = 0;
		}

		player.frameTrig();
		
		for(FireBall fireBall : fireballs){
			fireBall.y += (SPEED + SPEED);
		}
	}

	private void isGameWinLose() {
		if(life==0){
			showGameOver();
			count = 0;
			gameFlag = false;
		}else if(GAME_TIME - count <= 0) {
//			showGameWin();
			showGameOver();
			count = 0;
			gameFlag = false;;
		}
		
	}
	
	private void showGameOver(){
		final Layer bgLayer = new Layer(BitmapUtil.gameover, BitmapUtil.gameover.getWidth(), BitmapUtil.gameover.getHeight(), false);
		bgLayer.setPosition(0, bgLayer.getHeight());
//		final Sprite restartButton = new Sprite(BitmapUtil.restartBtn01, 350, 200, false);
		final ButtonLayer restartButton = new ButtonLayer(0, 0, false);
		final LabelLayer labelLayer = new LabelLayer("hello", 0, 0, false);
		labelLayer.setTextSize(100);
		labelLayer.setPosition(500, 500);
		final ALayer dialog = new ALayer() {
			
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				// TODO Auto-generated method stub
				return super.onTouchEvent(event);
			}
			
			@Override
			public void onTouched(MotionEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void drawSelf(Canvas canvas, Paint paint) {
//				canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
				paint = new Paint();
				paint.setAlpha(150);
				canvas.drawRect(new RectF(0, 0, CommonUtil.screenWidth, CommonUtil.screenHeight ), paint);
				
				bgLayer.drawSelf(canvas, paint);
				
				restartButton.drawSelf(canvas, paint);
				
				labelLayer.drawSelf(canvas, paint);
			}
		};
		
		
		restartButton.setBitmapAndAutoChangeWH(BitmapUtil.restartBtn01);
		restartButton.setButtonBitmap(BitmapUtil.restartBtn01, BitmapUtil.restartBtn02, BitmapUtil.restartBtn01);
		restartButton.setPosition(CommonUtil.screenWidth/2.0f - restartButton.getWidth()/2.0f, CommonUtil.screenHeight/4.0f*3);
		restartButton.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				restartGame();
				dialog.removeFromParent();
			}
		});
		
		dialog.addChild(restartButton);
		dialog.setAutoAdd(true);
	}
	
	private void showGameWin(){
		final Layer bgLayer = new Layer(BitmapUtil.gameover, BitmapUtil.gameover.getWidth(), BitmapUtil.gameover.getHeight(), false);
		bgLayer.setPosition(0, bgLayer.getHeight());
//		final ButtonLayer restartButton = new ButtonLayer(0, 0, false);
		final LabelLayer labelLayer = new LabelLayer("hello", 0, 0, false);
		labelLayer.setTextSize(100);
		labelLayer.setPosition(500, 500);
		
		final ALayer dialog = new ALayer() {
			
			@Override
			public void onTouched(MotionEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void drawSelf(Canvas canvas, Paint paint) {
				// TODO Auto-generated method stub
				
			}
		};
		
//		ButtonLayer button = new ButtonLayer();
//		ButtonLayer button2 = new ButtonLayer();
//
//		button.setOnClickListener(new ButtonLayer.OnClickListener() {
//			
//			@Override
//			public void onClick(ButtonLayer buttonLayer) {
//				gameFlag = false;
//				dialog.removeFromParent();
//			}
//		});
//
//
//		button2.setOnClickListener(new ButtonLayer.OnClickListener() {
//
//			@Override
//			public void onClick(ButtonLayer ButtonLayer) {
//				restartGame();
//				dialog.removeFromParent();
//			}
//		});
		
		final ButtonLayer restartButton = new ButtonLayer(0, 0, false);
		restartButton.setBitmapAndAutoChangeWH(BitmapUtil.restartBtn01);
		restartButton.setButtonBitmap(BitmapUtil.restartBtn01, BitmapUtil.restartBtn02, BitmapUtil.restartBtn01);
		restartButton.setPosition(CommonUtil.screenWidth/2.0f - restartButton.getWidth()/2.0f, CommonUtil.screenHeight/4.0f*3);
		restartButton.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				restartGame();
				dialog.removeFromParent();
			}
		});

		dialog.addChild(bgLayer);
		dialog.addChild(restartButton);
		dialog.setAutoAdd(true);
	}
	
	private void restartGame(){
		playerReset();
		floorReset();
		lifeReset();
		fireballReset();
		paint.setAlpha(255);
		gameFlag = true;
	}
	
	private void playerReset(){
		player.setPosition(200, 800);
		player.updateBitmap(LEFT);
	}

	private void floorReset(){
		floors.clear();
	}
	
	private void lifeReset(){
		life = 90;
	}
	
	private void fireballReset(){
		fireballs.clear();
	}

	@Override
	public synchronized void onTouchEvent(MotionEvent event) {		
		float _x = event.getX();
		float _y = event.getY();
		if (((event.getAction() & event.ACTION_MASK) == event.ACTION_POINTER_DOWN)
				|| ((event.getAction() & event.ACTION_MASK) == event.ACTION_POINTER_UP)) {
			_x = event.getX(event.getActionIndex());
			_y = event.getY(event.getActionIndex());
		}
		if (event.getAction() == event.ACTION_DOWN
				|| ((event.getAction() & event.ACTION_MASK) == event.ACTION_POINTER_DOWN)) {
			if (_x > 0 && _x < Controler.bmpWidth
					&& _y > this.getHeight() - Controler.bmpHeight
					&& _y < this.getHeight()) {
				isPressLeftMoveBtn = true;
				move = LEFT;
				player.updateBitmap(LEFT);
			} else if (_x > this.getWidth() - Controler.bmpWidth
					&& _x < this.getWidth()
					&& _y > this.getHeight() - Controler.bmpHeight
					&& _y < this.getHeight()) {
				isPressRightMoveBtn = true;
				move = RIGHT;
				player.updateBitmap(RIGHT);
			} else {
				if (_x > this.getWidth() / 2) {
					player.jumpToRight();
				} else {
					player.jumpToLeft();
				}

			}
		}
		
		if ((event.getAction() == event.ACTION_UP)
				&& (isPressLeftMoveBtn || isPressRightMoveBtn)) {
			if (isPressLeftMoveBtn) {
				isPressLeftMoveBtn = !isPressLeftMoveBtn;
			} else if (isPressRightMoveBtn) {
				isPressRightMoveBtn = !isPressRightMoveBtn;
			}
			move = 0;
			player.updateBitmap(0);
		} else if (((event.getAction() & event.ACTION_MASK) == event.ACTION_POINTER_UP)
				&& isPressLeftMoveBtn && isPressRightMoveBtn) {
			if (_x > 0 && _x < Controler.bmpWidth
					&& _y > this.getHeight() - Controler.bmpHeight
					&& _y < this.getHeight()) {
				isPressLeftMoveBtn = false;
				move = RIGHT;
				player.updateBitmap(RIGHT);
			}
			if (_x > this.getWidth() - Controler.bmpWidth
					&& _x < this.getWidth()
					&& _y > this.getHeight() - Controler.bmpHeight
					&& _y < this.getHeight()) {
				isPressRightMoveBtn = false;
				move = LEFT;
				player.updateBitmap(LEFT);
			}
		}
		
		LayerManager.getInstance().onTouchLayers(event);
	}

	public static boolean gameFlag = true;
	boolean readyFlag = false;

	private void createFloor() {
		new Thread(new Runnable() {
			public void run() {
				Random r = new Random();
				int i = 0;

				while (gameFlag) {
					while (!readyFlag) {

						synchronized (CREATE_FOOTBAR_LOCK) {
							try {
								CREATE_FOOTBAR_LOCK.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						if (!gameFlag) {
							break;
						}
						int readyForCreateFootboardParentNumber = currentXs
								.size();
						int randomCreateNumber = r
								.nextInt(readyForCreateFootboardParentNumber + 1);
						randomCreateNumber++;
						if (randomCreateNumber > 2)
							randomCreateNumber = 2;

						footboardsTheSameLine = new ArrayList<Floor>();
						ArrayList<Integer> tempCurrentXs = new ArrayList<Integer>();
						int des = r.nextInt(21);
						if (des < 7) {
							des = 7;
						} else if (10 <= des && des < 18) {
							des = 18;
						}

						des *= DISTANCE_MULTIPLE;
						int temp = 0;
						int x = r
								.nextInt(readyForCreateFootboardParentNumber + 1);
						x++;
						if (x > 2)
							x = 2;

						if (x == 2 && currentXs.size() == 1) {
							x = 2;
						} else if (x == 1 && currentXs.size() == 1) {
							x = 1;
						} else if (x == 2 && currentXs.size() == 2) {
							x = 2;
						} else if (x == 1 && currentXs.size() == 2) {
							x = 1;
						}

						int addX = 0;
						for (int number = 0; number < readyForCreateFootboardParentNumber; number++) {
							currentX = currentXs.get(number);

							if (number == 1 && tempCurrentXs.size() > 0) {
								if (addX + footboardWidth > width
										- footboardWidth - 80) {
									x--;
								}
							}

							if (currentX < 80 && x == 2) {
								x--;
							} else if (currentX > width - footboardWidth - 80
									&& x == 2) {
								x--;
							}

							for (int k = 0; k < x; k++) {
								if (addX + footboardWidth * 2 > width) {
									continue;
								}

								if (currentX == width - footboardWidth) {
									// x=1;
									if (des < DISTANCE_MULTIPLE * 10) {
										temp = -des;
									} else {
										temp = des - DISTANCE_MULTIPLE * 20;
									}
								} else if (currentX == 0) {
									if (des >= DISTANCE_MULTIPLE * 10) {
										temp = DISTANCE_MULTIPLE * 20 - des;
									} else {
										temp = des;
									}
								} else {
									if (des < DISTANCE_MULTIPLE * 10) {
										temp = -des;
									} else {
										temp = des - DISTANCE_MULTIPLE * 10;
									}
								}

								if (number == 1 && tempCurrentXs.size() > 0) {
									if (addX + footboardWidth + 80 > currentX
											+ temp) {
										temp = Math.abs(temp);
									}
								}

								if (x == 2 && k == 0) {
									temp = -Math.abs(temp);
								} else if (x == 2 && k == 1) {
									temp = Math.abs(temp);
								}

								if (k > 0
										&& addX + footboardWidth > currentX
												+ temp) {
									continue;
								}

								addX = 0;
								if (i == 0) { // 第一種座標
									// 111 currentX += temp;
									addX = currentX + temp;
									if (addX < 0) {
										addX = 0;
									} else if (addX > width - footboardWidth) {
										addX = width - footboardWidth;
									}
									// 產生地板的Y座標為螢幕下面邊緣，此方法會產生和人物交錯的現象。
									// footboard = new Footboard(getContext(),
									// addX,
									// height-footboardHeight, footboardHeight,
									// footboardWidth);
									// 產生地板的Y座標為螢幕下面再加一個地板高，此方法不會有人物交錯的現象，
									// 但是會有人物剛好掉在一半掉在螢幕外僥倖沒死的情況。此外，因為多往下一個地板高，
									// 所以第二個地板與第一個地板的間距會比較大，因為第一個地板預設是螢幕下面邊緣。
									// footboard = new Floor(context, addX,
									// height, footboardHeight, footboardWidth);

									footboard = new Floor(addX, 10, false);
									footboard.setPosition(addX, 500);
									// footboard.setBitmapAndAutoChangeWH(BitmapUtil.floor);
									footboard.setBitmap(BitmapUtil.floor);
									footboard.setWidth(footboardWidth);
									footboard.setHeight(footboardHeight);

									footboardsTheSameLine.add(footboard);

								}

								tempCurrentXs.add(addX);
							}
							x = randomCreateNumber - x;
						}

						currentXs = tempCurrentXs;
						footboards.add(footboardsTheSameLine);

						FireBall fireBall = new FireBall(width);
						fireballs.add(fireBall);
					}
				}
			}
		}).start();
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
