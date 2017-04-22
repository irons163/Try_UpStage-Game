package com.example.try_upstage.sprites;

import com.example.try_upstage.utils.BitmapUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ToolUtil {
	public float tool_x;
	public float tool_y;
	public int tool_width;
	Bitmap bitmap;
	public int type;
	Thread bombExplodeThread;
	public boolean isExploding;
	public ToolUtil(float footboard_x, float footboard_y, int type){
		if(type==Floor.BOMB){
		bitmap = BitmapUtil.tool_bomb_bitmap;
		}else if(type==Floor.BOMB_EXPLODE){
			bitmap = BitmapUtil.tool_bomb_explosion_bitmap;
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isExploding=false;
				}
			};
			
			if(bombExplodeThread!=null){
				bombExplodeThread.interrupt();
			}
			
			isExploding=true;
			bombExplodeThread = new Thread(runnable);
			bombExplodeThread.start();
		}else{
		bitmap = BitmapUtil.toll_cure_bitmap;
		}
		tool_width=bitmap.getHeight();
		this.tool_x = footboard_x+30;
		this.tool_y = footboard_y-tool_width;
		this.type = type;
	}
	
	public void draw(Canvas canvas, float dy) {
		this.tool_y -= dy; 
		canvas.drawBitmap(bitmap, tool_x, tool_y, null);
	}
}
