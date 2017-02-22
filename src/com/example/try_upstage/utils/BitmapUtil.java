package com.example.try_upstage.utils;

import java.io.IOException;
import java.io.InputStream;

import com.example.try_upstage.MyGameModel;
import com.example.try_upstage.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings.Global;

public class BitmapUtil {
	static Context context;
	public final static int FOOTBOARD_WIDTH_PERSENT = 4;
	static final int PLAYER_WIDTH_PERSENT = 3;
	static final int TOOL_WIDTH_PERSENT = 4;
	static final int FIREBALL_WIDTH_PERSENT = 3;
	
	public static Bitmap floor;
	
	public static Bitmap player_girl_left01_bitmap;
	public static Bitmap player_girl_left02_bitmap;
	public static Bitmap player_girl_left03_bitmap;
	public static Bitmap player_girl_right01_bitmap;
	public static Bitmap player_girl_right02_bitmap;
	public static Bitmap player_girl_right03_bitmap;
	public static Bitmap player_girl_injure_left_bitmap;
	public static Bitmap player_girl_injure_right_bitmap;
	public static Bitmap player_girl_down_left_bitmap;
	public static Bitmap player_girl_down_right_bitmap;

	public static Bitmap player_boy_left01_bitmap;
	public static Bitmap player_boy_left02_bitmap;
	public static Bitmap player_boy_left03_bitmap;
	public static Bitmap player_boy_right01_bitmap;
	public static Bitmap player_boy_right02_bitmap;
	public static Bitmap player_boy_right03_bitmap;
	public static Bitmap player_boy_injure_left_bitmap;
	public static Bitmap player_boy_injure_right_bitmap;
	public static Bitmap player_boy_down_left_bitmap;
	public static Bitmap player_boy_down_right_bitmap;

	public static Bitmap footboard_normal_bitmap;
	public static Bitmap footboard_moving_left1_bitmap;
	public static Bitmap footboard_moving_left2_bitmap;
	public static Bitmap footboard_moving_left3_bitmap;
	public static Bitmap footboard_moving_right1_bitmap;
	public static Bitmap footboard_moving_right2_bitmap;
	public static Bitmap footboard_moving_right3_bitmap;
	public static Bitmap footboard_unstable1_bitmap;
	public static Bitmap footboard_unstable2_bitmap;
	public static Bitmap footboard_unstable3_bitmap;
	public static Bitmap footboard_spring_bitmap;
	public static Bitmap footboard_spiked_bitmap;
	public static Bitmap footboard_wood_bitmap;
	public static Bitmap footboard_wood2_bitmap;
	public static Bitmap footboard_wood3_bitmap;

	public static Bitmap tool_bomb_bitmap;
	public static Bitmap toll_cure_bitmap;
	public static Bitmap tool_bomb_explosion_bitmap;
	public static Bitmap fire_ball;
	
	public static Bitmap life_bg;
	public static Bitmap life;
	public static Bitmap top_spiked_bar;
	
	public static Bitmap eat_human_tree01;
	public static Bitmap eat_human_tree02;
	public static Bitmap eat_human_tree03;

	public static void initBitmap(Context context) {
		BitmapUtil.context = context;
		// initBitmap();

//		floor = BitmapFactory.decodeResource(context.getResources(),
//				R.drawable.footboard_normal);
		floor = createSpecificSizeBitmap(context.getResources().getDrawable(
				R.drawable.footboard_normal), 200, 60);
		
		
		
		int footbarWidth = Common.screenWidth / FOOTBOARD_WIDTH_PERSENT;
		int playerWidth = footbarWidth / PLAYER_WIDTH_PERSENT;
		int toolWidth = footbarWidth / TOOL_WIDTH_PERSENT;
		int fireballWidth = footbarWidth / FIREBALL_WIDTH_PERSENT;
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		options.inJustDecodeBounds = false;

		final BitmapFactory.Options toolOptions = new BitmapFactory.Options();
		toolOptions.inSampleSize = 2;
		toolOptions.inJustDecodeBounds = false;

		final BitmapFactory.Options fireballOptions = new BitmapFactory.Options();
		fireballOptions.inSampleSize = 2;
		fireballOptions.inJustDecodeBounds = false;

		player_girl_left01_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_girl_left01, options);
		player_girl_left02_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_girl_left02, options);
		player_girl_left03_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_girl_left03, options);
		player_girl_right01_bitmap = BitmapFactory
				.decodeResource(context.getResources(),
						R.drawable.player_girl_right01, options);
		player_girl_right02_bitmap = BitmapFactory
				.decodeResource(context.getResources(),
						R.drawable.player_girl_right02, options);
		player_girl_right03_bitmap = BitmapFactory
				.decodeResource(context.getResources(),
						R.drawable.player_girl_right03, options);
		player_girl_injure_left_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_girl_injure_left,
				options);
		player_girl_injure_right_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_girl_injure_right,
				options);
		player_girl_down_left_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_girl_down_left,
				options);
		player_girl_down_right_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_girl_down_right,
				options);

		int x = player_girl_left01_bitmap.getHeight();
		
//		player_girl_left01_bitmap = Bitmap.createBitmap(
//				player_girl_left01_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_left01_bitmap.getHeight()
//						/ player_girl_left01_bitmap.getWidth() * playerWidth));
//		player_girl_left02_bitmap = Bitmap.createBitmap(
//				player_girl_left02_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_left02_bitmap.getHeight()
//						/ player_girl_left02_bitmap.getWidth() * playerWidth));
//		player_girl_left03_bitmap = Bitmap.createBitmap(
//				player_girl_left03_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_left03_bitmap.getHeight()
//						/ player_girl_left03_bitmap.getWidth() * playerWidth));
//		player_girl_right01_bitmap = Bitmap.createBitmap(
//				player_girl_right01_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_right01_bitmap.getHeight()
//						/ player_girl_right01_bitmap.getWidth() * playerWidth));
//		player_girl_right02_bitmap = Bitmap.createBitmap(
//				player_girl_right02_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_right02_bitmap.getHeight()
//						/ player_girl_right02_bitmap.getWidth() * playerWidth));
//		player_girl_right03_bitmap = Bitmap.createBitmap(
//				player_girl_right03_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_right03_bitmap.getHeight()
//						/ player_girl_right03_bitmap.getWidth() * playerWidth));
//		player_girl_injure_left_bitmap = Bitmap.createBitmap(
//				player_girl_injure_left_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_injure_left_bitmap.getHeight()
//						/ player_girl_injure_left_bitmap.getWidth() * playerWidth));
//		player_girl_injure_right_bitmap = Bitmap.createBitmap(
//				player_girl_injure_right_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_injure_right_bitmap.getHeight()
//						/ player_girl_injure_right_bitmap.getWidth() * playerWidth));
//		player_girl_down_left_bitmap = Bitmap.createBitmap(
//				player_girl_down_left_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_down_left_bitmap.getHeight()
//						/ player_girl_down_left_bitmap.getWidth() * playerWidth));
//		player_girl_down_right_bitmap = Bitmap.createBitmap(
//				player_girl_down_right_bitmap, 0, 0, playerWidth,
//				(int)((float)player_girl_down_right_bitmap.getHeight()
//						/ player_girl_down_right_bitmap.getWidth() * playerWidth));
		
		player_girl_left01_bitmap = Bitmap.createScaledBitmap(
				player_girl_left01_bitmap, playerWidth,
				(int)((float)player_girl_left01_bitmap.getHeight()
						/ player_girl_left01_bitmap.getWidth() * playerWidth), true);
		player_girl_left02_bitmap = Bitmap.createScaledBitmap(
				player_girl_left02_bitmap, playerWidth,
				(int)((float)player_girl_left02_bitmap.getHeight()
						/ player_girl_left02_bitmap.getWidth() * playerWidth), true);
		player_girl_left03_bitmap = Bitmap.createScaledBitmap(
				player_girl_left03_bitmap, playerWidth,
				(int)((float)player_girl_left03_bitmap.getHeight()
						/ player_girl_left03_bitmap.getWidth() * playerWidth), true);
		player_girl_right01_bitmap = Bitmap.createScaledBitmap(
				player_girl_right01_bitmap, playerWidth,
				(int)((float)player_girl_right01_bitmap.getHeight()
						/ player_girl_right01_bitmap.getWidth() * playerWidth), true);
		player_girl_right02_bitmap = Bitmap.createScaledBitmap(
				player_girl_right02_bitmap, playerWidth,
				(int)((float)player_girl_right02_bitmap.getHeight()
						/ player_girl_right02_bitmap.getWidth() * playerWidth), true);
		player_girl_right03_bitmap = Bitmap.createScaledBitmap(
				player_girl_right03_bitmap, playerWidth,
				(int)((float)player_girl_right03_bitmap.getHeight()
						/ player_girl_right03_bitmap.getWidth() * playerWidth), true);
		player_girl_injure_left_bitmap = Bitmap.createScaledBitmap(
				player_girl_injure_left_bitmap, playerWidth,
				(int)((float)player_girl_injure_left_bitmap.getHeight()
						/ player_girl_injure_left_bitmap.getWidth() * playerWidth), true);
		player_girl_injure_right_bitmap = Bitmap.createScaledBitmap(
				player_girl_injure_right_bitmap, playerWidth,
				(int)((float)player_girl_injure_right_bitmap.getHeight()
						/ player_girl_injure_right_bitmap.getWidth() * playerWidth), true);
		player_girl_down_left_bitmap = Bitmap.createScaledBitmap(
				player_girl_down_left_bitmap, playerWidth,
				(int)((float)player_girl_down_left_bitmap.getHeight()
						/ player_girl_down_left_bitmap.getWidth() * playerWidth), true);
		player_girl_down_right_bitmap = Bitmap.createScaledBitmap(
				player_girl_down_right_bitmap, playerWidth,
				(int)((float)player_girl_down_right_bitmap.getHeight()
						/ player_girl_down_right_bitmap.getWidth() * playerWidth), true);
		
		player_boy_left01_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_walk01, options);
		player_boy_left03_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_walk03, options);
		player_boy_left02_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_walk02, options);
		player_boy_right01_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_right01, options);
		player_boy_right02_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_right02, options);
		player_boy_right03_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_right03, options);
		player_boy_injure_left_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_injure_left,
				options);
		player_boy_injure_right_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_injure_right,
				options);
		player_boy_down_left_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_down_left,
				options);
		player_boy_down_right_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.player_boy_down_right,
				options);

//		player_boy_left01_bitmap = Bitmap.createBitmap(
//				player_boy_left01_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_left01_bitmap.getHeight()
//						/ player_boy_left01_bitmap.getWidth() * playerWidth));
//		player_boy_left03_bitmap = Bitmap.createBitmap(
//				player_boy_left03_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_left03_bitmap.getHeight()
//						/ player_boy_left03_bitmap.getWidth() * playerWidth));
//		player_boy_left02_bitmap = Bitmap.createBitmap(
//				player_boy_left02_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_left02_bitmap.getHeight()
//						/ player_boy_left02_bitmap.getWidth() * playerWidth));
//		player_boy_right01_bitmap = Bitmap.createBitmap(
//				player_boy_right01_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_right01_bitmap.getHeight()
//						/ player_boy_right01_bitmap.getWidth() * playerWidth));
//		player_boy_right02_bitmap = Bitmap.createBitmap(
//				player_boy_right02_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_right02_bitmap.getHeight()
//						/ player_boy_right02_bitmap.getWidth() * playerWidth));
//		player_boy_right03_bitmap = Bitmap.createBitmap(
//				player_boy_right03_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_right03_bitmap.getHeight()
//						/ player_boy_right03_bitmap.getWidth() * playerWidth));
//		player_boy_injure_left_bitmap = Bitmap.createBitmap(
//				player_boy_injure_left_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_injure_left_bitmap.getHeight()
//						/ player_boy_injure_left_bitmap.getWidth() * playerWidth));
//		player_boy_injure_right_bitmap = Bitmap.createBitmap(
//				player_boy_injure_right_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_injure_right_bitmap.getHeight()
//						/ player_boy_injure_right_bitmap.getWidth() * playerWidth));
//		player_boy_down_left_bitmap = Bitmap.createBitmap(
//				player_boy_down_left_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_down_left_bitmap.getHeight()
//						/ player_boy_down_left_bitmap.getWidth() * playerWidth));
//		player_boy_down_right_bitmap = Bitmap.createBitmap(
//				player_boy_down_right_bitmap, 0, 0, playerWidth,
//				(int)((float)player_boy_down_right_bitmap.getHeight()
//						/ player_boy_down_right_bitmap.getWidth() * playerWidth));
		
		player_boy_left01_bitmap = Bitmap.createScaledBitmap(
				player_boy_left01_bitmap, playerWidth,
				(int)((float)player_boy_left01_bitmap.getHeight()
						/ player_boy_left01_bitmap.getWidth() * playerWidth), true);
		player_boy_left03_bitmap = Bitmap.createScaledBitmap(
				player_boy_left03_bitmap, playerWidth,
				(int)((float)player_boy_left03_bitmap.getHeight()
						/ player_boy_left03_bitmap.getWidth() * playerWidth), true);
		player_boy_left02_bitmap = Bitmap.createScaledBitmap(
				player_boy_left02_bitmap, playerWidth,
				(int)((float)player_boy_left02_bitmap.getHeight()
						/ player_boy_left02_bitmap.getWidth() * playerWidth), true);
		player_boy_right01_bitmap = Bitmap.createScaledBitmap(
				player_boy_right01_bitmap, playerWidth,
				(int)((float)player_boy_right01_bitmap.getHeight()
						/ player_boy_right01_bitmap.getWidth() * playerWidth), true);
		player_boy_right02_bitmap = Bitmap.createScaledBitmap(
				player_boy_right02_bitmap, playerWidth,
				(int)((float)player_boy_right02_bitmap.getHeight()
						/ player_boy_right02_bitmap.getWidth() * playerWidth), true);
		player_boy_right03_bitmap = Bitmap.createScaledBitmap(
				player_boy_right03_bitmap, playerWidth,
				(int)((float)player_boy_right03_bitmap.getHeight()
						/ player_boy_right03_bitmap.getWidth() * playerWidth), true);
		player_boy_injure_left_bitmap = Bitmap.createScaledBitmap(
				player_boy_injure_left_bitmap, playerWidth,
				(int)((float)player_boy_injure_left_bitmap.getHeight()
						/ player_boy_injure_left_bitmap.getWidth() * playerWidth), true);
		player_boy_injure_right_bitmap = Bitmap.createScaledBitmap(
				player_boy_injure_right_bitmap, playerWidth,
				(int)((float)player_boy_injure_right_bitmap.getHeight()
						/ player_boy_injure_right_bitmap.getWidth() * playerWidth), true);
		player_boy_down_left_bitmap = Bitmap.createScaledBitmap(
				player_boy_down_left_bitmap, playerWidth,
				(int)((float)player_boy_down_left_bitmap.getHeight()
						/ player_boy_down_left_bitmap.getWidth() * playerWidth), true);
		player_boy_down_right_bitmap = Bitmap.createScaledBitmap(
				player_boy_down_right_bitmap, playerWidth,
				(int)((float)player_boy_down_right_bitmap.getHeight()
						/ player_boy_down_right_bitmap.getWidth() * playerWidth), true);
		
		// 普通地板
		footboard_normal_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_normal);
		// 往左地板
		footboard_moving_left1_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_moving_left1);
		footboard_moving_left2_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_moving_left2);
		footboard_moving_left3_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_moving_left3);
		// bitmap = bitmap1;
		// 往右地板
		footboard_moving_right1_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_moving_right1);
		footboard_moving_right2_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_moving_right2);
		footboard_moving_right3_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_moving_right3);
		// 不穩定地板
		footboard_unstable1_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_unstable1);
		footboard_unstable2_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_unstable2);
		footboard_unstable3_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_unstable3);
		// bitmap = bitmap1;
		// 滑動地板
		footboard_spring_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_spring);
		// 陷阱地板
		footboard_spiked_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_spiked);

		tool_bomb_bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bomb, toolOptions);

		tool_bomb_explosion_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.bomb_explosion, toolOptions);

		toll_cure_bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.cure, toolOptions);

//		tool_bomb_bitmap = Bitmap.createBitmap(
//				tool_bomb_bitmap, 0, 0, toolWidth,
//				(int)((float)tool_bomb_bitmap.getHeight()
//						/ tool_bomb_bitmap.getWidth() * toolWidth));
//		tool_bomb_explosion_bitmap = Bitmap.createBitmap(
//				tool_bomb_explosion_bitmap, 0, 0, toolWidth,
//				(int)((float)tool_bomb_explosion_bitmap.getHeight()
//						/ tool_bomb_explosion_bitmap.getWidth() * toolWidth));
//		toll_cure_bitmap = Bitmap.createBitmap(
//				toll_cure_bitmap, 0, 0, toolWidth,
//				(int)((float)toll_cure_bitmap.getHeight()
//						/ toll_cure_bitmap.getWidth() * toolWidth));
		
		tool_bomb_bitmap = Bitmap.createScaledBitmap(
				tool_bomb_bitmap, toolWidth,
				(int)((float)tool_bomb_bitmap.getHeight()
						/ tool_bomb_bitmap.getWidth() * toolWidth), true);
		tool_bomb_explosion_bitmap = Bitmap.createScaledBitmap(
				tool_bomb_explosion_bitmap, toolWidth,
				(int)((float)tool_bomb_explosion_bitmap.getHeight()
						/ tool_bomb_explosion_bitmap.getWidth() * toolWidth), true);
		toll_cure_bitmap = Bitmap.createScaledBitmap(
				toll_cure_bitmap, toolWidth,
				(int)((float)toll_cure_bitmap.getHeight()
						/ toll_cure_bitmap.getWidth() * toolWidth), true);
		
		footboard_wood_bitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.footboard_wood, toolOptions);
		footboard_wood2_bitmap = BitmapFactory
				.decodeResource(context.getResources(),
						R.drawable.footboard_wood2, toolOptions);
		footboard_wood3_bitmap = BitmapFactory
				.decodeResource(context.getResources(),
						R.drawable.footboard_wood3, toolOptions);

		fire_ball = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.fireball, fireballOptions);

//		fire_ball = Bitmap.createBitmap(
//				fire_ball, 0, 0, fireballWidth,
//				(int)((float)fire_ball.getHeight()
//						/ fire_ball.getWidth() * fireballWidth));
		
		fire_ball = Bitmap.createScaledBitmap(
				fire_ball, fireballWidth,
				(int)((float)fire_ball.getHeight()
						/ fire_ball.getWidth() * fireballWidth), true);
	
		life_bg = BitmapFactory.decodeResource(context.getResources(),
						R.drawable.life_bg);
		life = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.life);
		top_spiked_bar = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.top_spiked_bar);
		
		eat_human_tree01 = BitmapFactory.decodeResource(context.getResources(), R.drawable.eat_human_tree01);
		eat_human_tree02 = BitmapFactory.decodeResource(context.getResources(), R.drawable.eat_human_tree);
		eat_human_tree03 = BitmapFactory.decodeResource(context.getResources(), R.drawable.eat_human_tree02);
	
		eat_human_tree01 = Bitmap.createScaledBitmap(
				eat_human_tree01, toolWidth,
				(int)((float)eat_human_tree01.getHeight()
						/ eat_human_tree01.getWidth() * toolWidth), true);
		
		eat_human_tree02 = Bitmap.createScaledBitmap(
				eat_human_tree02, toolWidth,
				(int)((float)eat_human_tree02.getHeight()
						/ eat_human_tree02.getWidth() * toolWidth), true);
		
		eat_human_tree03 = Bitmap.createScaledBitmap(
				eat_human_tree03, toolWidth,
				(int)((float)eat_human_tree03.getHeight()
						/ eat_human_tree03.getWidth() * toolWidth), true);
	}

	public static Bitmap createSpecificSizeBitmap(Drawable drawable, int width,
			int height) {

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	public static Bitmap getBitmap(String path) {
		try {
			InputStream is = context.getAssets().open(path);

			return BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getBitmapFromRes(int resId) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resId);
		return bitmap;
	}

}
