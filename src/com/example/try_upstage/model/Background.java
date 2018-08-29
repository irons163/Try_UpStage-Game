package com.example.try_upstage.model;

import com.example.try_upstage.utils.CommonUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background {
	Bitmap bitmap;
	Rect bitmapRect;
	Rect bitmapRect2;
	
	
	
	public Background(Bitmap bitmap){
		this.bitmap = bitmap;
		
	}
	
	public void setPosition(int x, int y){
		init(x, y);
	}
	
	public void init(int x, int y){
//		bitmapRect = new Rect(x , y - bitmap.getHeight(), x + bitmap.getWidth(), y);
//		bitmapRect2 = new Rect(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
		
		bitmapRect = new Rect(x , y - CommonUtil.screenHeight, x + CommonUtil.screenWidth, y);
		bitmapRect2 = new Rect(x, y, x + CommonUtil.screenWidth, y + CommonUtil.screenHeight);
	}
	
	public void update(){
		bitmapRect.set(bitmapRect.left, bitmapRect.top + 5, bitmapRect.right, bitmapRect.bottom + 5);
		bitmapRect2.set(bitmapRect2.left, bitmapRect2.top + 5, bitmapRect2.right, bitmapRect2.bottom + 5);
		
		if(bitmapRect.top >= bitmap.getHeight()){
			bitmapRect.set(bitmapRect.left, -bitmap.getHeight(), bitmapRect.right, 0);
		}else if(bitmapRect2.top >= bitmap.getHeight()){
			bitmapRect2.set(bitmapRect2.left, -bitmap.getHeight(), bitmapRect2.right, 0);
		}
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(bitmap, null, bitmapRect, null);
		canvas.drawBitmap(bitmap, null, bitmapRect2, null);
	}
	
}
