package com.example.try_upstage.sprites;


import com.example.try_upstage.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Controler {
	/**
	 * º“¿¿¡‰ΩL√∏ªs
	 */
	private Bitmap e1, e2; //•™ªP•k§Ë¶V¡‰
	
	int height, width; 
	public static int bmpHeight, bmpWidth;
	
	public Controler(Context ct, int height, int width) {
		this.height = height;
		this.width = width;
		Resources res = ct.getResources();
		e1 = BitmapFactory.decodeResource(res, R.drawable.left_keyboard_btn);
		e2 = BitmapFactory.decodeResource(res, R.drawable.right_keyboard_btn);
		bmpHeight = e1.getHeight();
		bmpWidth = e1.getWidth();
		res = null;
	}

	public void paint(Canvas c) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		c.drawBitmap(e1, 0, height-bmpHeight, paint);
		c.drawBitmap(e2, width-bmpWidth, height-bmpHeight, paint);
	}
}
