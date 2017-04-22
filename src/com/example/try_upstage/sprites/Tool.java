package com.example.try_upstage.sprites;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementActionItemBaseReugularFPS;
import com.example.try_gameengine.action.MovementActionSetWithThread;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_upstage.sprites.Player.Rabbit_action;

public class Tool extends Sprite{
	MovementAction floorDownMovementAction;
	
	public Tool(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
	}
	
	private void initMovement(){
		floorDownMovementAction = new MovementActionSetWithThread();
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
	
}
