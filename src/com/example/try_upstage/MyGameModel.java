package com.example.try_upstage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.Data;
import com.example.try_gameengine.framework.GameModel;
import com.example.try_upstage.model.Background;
import com.example.try_upstage.sprites.Controler;
import com.example.try_upstage.sprites.EatHumanTree;
import com.example.try_upstage.sprites.FireBall;
import com.example.try_upstage.sprites.Floor;
import com.example.try_upstage.sprites.Player;
import com.example.try_upstage.sprites.ToolUtil;
import com.example.try_upstage.utils.BitmapUtil;
import com.example.try_upstage.utils.Common;

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
	private final float BASE_SPEED = (float) Common.screenWidth / 1200;
	float SPEED = BASE_SPEED;
	int drawCount;
	Player player;
	final static int SMOOTH_DEVIATION = 4;
	public static ToolUtil toolExplodingUtil;
	float playerMoveSpeed = 0;
	public static final float MOVESPEED = (float) Common.screenWidth / 66;
	public static final float SLIDERSPEED = MOVESPEED / 3;
	boolean playerDownOnFootBoard = false;
	boolean playerStandOnFootboard = false;
	private int move = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	int whichFoorbar = 0;
	int life = 90;
	public final float DOWNSPEED = BASE_SPEED;
	Paint paint = new Paint();
	ArrayList<FireBall> fireballs = new ArrayList<FireBall>();
	boolean isPressLeftMoveBtn, isPressRightMoveBtn;
	Controler controler;

	public MyGameModel(Context context, Data data) {
		super(context, data);
		background = new Background(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.background2));
		background.setPosition(0, 0);

		Floor floor = new Floor(0, 0, false);
		floor.setPosition(100, 500);
		floor.setBitmapAndAutoChangeWH(BitmapUtil.floor);

		floors.add(floor);
		footboardWidth = (int) Common.screenWidth
				/ BitmapUtil.FOOTBOARD_WIDTH_PERSENT;
		footboardHeight = (int) ((float) BitmapUtil.footboard_normal_bitmap
				.getHeight() / BitmapUtil.footboard_normal_bitmap.getWidth() * footboardWidth);

		width = Common.screenWidth;
		height = Common.screenHeight;

		currentXs.add(currentX);

		createFloor();
		initPlayer();

		this.controler = new Controler(context, this.getHeight(),
				this.getWidth());
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

		boolean isInjure = false;
		boolean isDrawPlayer = true;
		background.draw(canvas);
		controler.paint(canvas);

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
					toolUtil = null;
				} else if (ene.toolNum == Floor.BOMB) {
					toolUtil = new ToolUtil(ene.getX(), ene.getY(), Floor.BOMB);
					// toolUtil.draw(canvas, SPEED);
				} else if (ene.toolNum == Floor.BOMB_EXPLODE) {

				} else if (ene.toolNum == Floor.EAT_HUMAN_TREE) {
					eatHumanTree = ene.eatHumanTree;
				} else {
					toolUtil = new ToolUtil(ene.getX(), ene.getY(), Floor.CURE);
					// toolUtil.draw(canvas, SPEED);
				}

				if ((ene.getX() < player.getX() + player.getWidth()
						- SMOOTH_DEVIATION * 4)
						&& (ene.getX() + footboardWidth > player.getX()
								+ SMOOTH_DEVIATION * 4)
						&& (ene.getY() >= player.getY() + player.getHeight()
								- 1 && ene.getY() < player.getY()
								+ player.getHeight() + DOWNSPEED + SPEED)) {

					if (toolUtil != null
							&& (toolUtil.tool_x < player.getX()
									+ player.getWidth() - SMOOTH_DEVIATION * 4)
							&& (toolUtil.tool_x + toolUtil.tool_width > player
									.getX() + SMOOTH_DEVIATION * 4)
							&& ene.toolNum != Floor.BOMB_EXPLODE) {
						if (ene.toolNum == Floor.BOMB) {
							isInjure = true;
							ene.toolNum = Floor.BOMB_EXPLODE;
							toolExplodingUtil = new ToolUtil(ene.getX(),
									ene.getY(), Floor.BOMB_EXPLODE);
							life -= 60;
							if (life < 0) {
								life = 0;
							}
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
							isInjure = true;
							life -= 30;
							if (life < 0) {
								life = 0;
							}
							paint.setColor(Color.RED);
							paint.setAlpha(100);
							Rect rect6 = new Rect(0, 0, width, height);
							canvas.drawRect(rect6, paint);
						}
					}

					if (playerDownOnFootBoard) {
						whichFoorbar = 0;
						playerMoveSpeed = 0;
						if (ene.which == 5) {
							isInjure = true;
							life -= 30;
							if (life < 0) {
								life = 0;
							}
							paint.setColor(Color.RED);
							paint.setAlpha(100);
							Rect rect6 = new Rect(0, 0, width, height);
							canvas.drawRect(rect6, paint);
						}
						if (ene.which == 1) {
							whichFoorbar = 1;
							playerMoveSpeed = SLIDERSPEED;
						} else if (ene.which == 2) {
							whichFoorbar = 2;
							playerMoveSpeed = -SLIDERSPEED;
						}
					}

					playerStandOnFootboard = true;
					footboardByPlayerY = ene.getY();
					player.setY(footboardByPlayerY - player.getHeight());
					ene.setCount();
					playerDownOnFootBoard = false;
				}

				if (ene.toolNum == Floor.BOMB_EXPLODE
						&& toolExplodingUtil != null) {
					if (toolExplodingUtil.isExploding) {
						toolUtil = null;
						if (isDrawPlayer) {
							isDrawPlayer = false;
							player.draw(canvas, SPEED, 0, isInjure);
							toolExplodingUtil.draw(canvas, SPEED);
						}
					} else {
						toolExplodingUtil = null;
						ene.toolNum = Floor.NOTOOL;
					}
				}

				if (toolUtil != null) {
					toolUtil.draw(canvas, SPEED);
				}

				if (eatHumanTree != null) {
					eatHumanTree.move(0, SPEED);
					eatHumanTree.drawSelf(canvas, paint);
				}

				if (ene.getY() > 0 - SPEED && ene.drawBitmap != null) {
					ene.draw(canvas, SPEED);
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

		Bitmap bitmap = BitmapUtil.top_spiked_bar;
		float topSpikedBarHeight = (float) bitmap.getHeight()
				/ bitmap.getWidth() * width;
		RectF rectTopSpikedBar = new RectF(0, 0, width, topSpikedBarHeight);
		canvas.drawBitmap(bitmap, null, rectTopSpikedBar, null);

		if (topSpikedBarHeight >= player.getY()) {
			isInjure = true;
			life = 0;
		}

		float playerDy = 0;
		float playerDx = 0;
		if (isDrawPlayer) {
			if (playerStandOnFootboard) {
				if (move == LEFT) {
					if (player.getX() <= 0) {
						playerDy = SPEED;
						playerDx = 0;
						// player.draw(canvas, SPEED, 0, isInjure);
						move = 0;
					} else {
						playerDy = SPEED;
						playerDx = MOVESPEED + playerMoveSpeed;
						// player.draw(canvas, SPEED, MOVESPEED +
						// playerMoveSpeed, isInjure);
					}
				} else if (move == RIGHT) {
					if (player.getX() + player.getWidth() >= width) {
						playerDy = SPEED;
						playerDx = 0;
						// player.draw(canvas, SPEED, 0, isInjure);
						move = 0;
					} else {
						playerDy = SPEED;
						playerDx = -MOVESPEED + playerMoveSpeed;
						// player.draw(canvas, SPEED, -MOVESPEED +
						// playerMoveSpeed, isInjure);
					}
				} else {
					if (whichFoorbar == 1) {
						playerDy = SPEED;
						playerDx = playerMoveSpeed;
						// player.draw(canvas, SPEED, playerMoveSpeed,
						// isInjure);
					} else if (whichFoorbar == 2) {
						playerDy = SPEED;
						playerDx = playerMoveSpeed;
						// player.draw(canvas, SPEED, playerMoveSpeed,
						// isInjure);
					} else {
						playerDy = SPEED;
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
				isInjure = true;
				life = 0;
				if (isDrawPlayer) {
					isDrawPlayer = false;
					player.draw(canvas, playerDy, playerDx, isInjure);
				}
			}
			ball.draw(canvas, -(SPEED + SPEED), 0);
		}

		if (isDrawPlayer) {
			player.draw(canvas, playerDy, playerDx, isInjure);
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
	}

	@Override
	protected void process() {
		super.process();

		background.update();
		for (Floor floor : floors) {
			floor.frameTrig();
		}

		for (ArrayList<Floor> floors : footboards) {
			for (Floor floor : floors) {
				floor.move(0, 3);
				floor.frameTrig();
				if (floor.eatHumanTree != null) {
					floor.eatHumanTree.move(0, 3);
					floor.frameTrig();
				}
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
		// return true;
	}

	public static boolean gameFlag = true;
	boolean readyFlag = false;

	private void createFloor() {
		new Thread(new Runnable() {
			public void run() {
				Random r = new Random();
				// int i = r.nextInt(3); //隨機出現三種座標之一
				int i = 0;
				// long delayTime =0;

				while (gameFlag) {
					while (!readyFlag) {

						synchronized (CREATE_FOOTBAR_LOCK) {
							try {
								CREATE_FOOTBAR_LOCK.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// try {
						// // Thread.sleep((int)(2000 / ((level + 2.8)*0.35)));
						// Thread.sleep((long)(BASE_SPEED*2000/SPEED)-delayTime);
						// } catch (InterruptedException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }

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

								// else if (i == 1) { // 第二種座標
								// footboard = new Footboard(getContext(),
								// currentX,
								// height, footboardHeight, footboardWidth);
								// footboards.add(footboard);
								// } else { // 第三種座標
								// footboard = new Footboard(getContext(),
								// currentX,
								// height, footboardHeight, footboardWidth);
								// footboards.add(footboard);
								// }

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
