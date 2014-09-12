package dto;

import java.util.Timer;

import android.view.View;

public class ViewDTO {

	private int x;
	private int y;
	private Timer timer;
	int speed=0;
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	private View view;
	
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public View getView() {
		return view;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setView(View view) {
		this.view = view;
	}
}
