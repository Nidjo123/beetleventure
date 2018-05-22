package com.ld48.nidjo123.tinygame.entity;

import java.awt.Rectangle;

import com.ld48.nidjo123.tinygame.Art;
import com.ld48.nidjo123.tinygame.Bitmap;
import com.ld48.nidjo123.tinygame.TinyGame;

public class Bullet extends Entity {
	private int x, y;
	
	private Bitmap bullet = Art.spriteSheet[0][4];
	
	public Bullet(int x, int y) {
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
		x += 2;
		if (x > TinyGame.WIDTH) {
			TinyGame.entities.remove(this);
		}
	}
	
	public void render(int[] pixels) {
		bullet.render(x, y, pixels);
	}
}
