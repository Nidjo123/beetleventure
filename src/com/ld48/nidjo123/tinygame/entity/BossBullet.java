package com.ld48.nidjo123.tinygame.entity;

import com.ld48.nidjo123.tinygame.Art;
import com.ld48.nidjo123.tinygame.Bitmap;
import com.ld48.nidjo123.tinygame.TinyGame;

public class BossBullet extends Entity {
	private int x, y;
	
	private Bitmap bullet = Art.spriteSheet[2][6];
	
	public BossBullet(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void doLogic(TinyGame game) {
		x -= 2;
		if (x < -16) {
			TinyGame.entities.remove(this);
		}
	}
	
	public void render(int[] pixels) {
		bullet.render(x - 10, y + 16, pixels);
	}
}
