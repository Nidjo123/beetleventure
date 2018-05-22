package com.ld48.nidjo123.tinygame.entity;

import java.awt.Rectangle;

import com.ld48.nidjo123.tinygame.Art;
import com.ld48.nidjo123.tinygame.Bitmap;
import com.ld48.nidjo123.tinygame.TinyGame;

public class GreenEnemy extends Entity {
	private int x, y;
	public static boolean hit = false;
	
	Bitmap[] bm = { Art.spriteSheet[1][4], Art.spriteSheet[1][5], Art.spriteSheet[1][6], Art.spriteSheet[1][7] };
	
	public void setHit() {
		hit = true;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public GreenEnemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void doLogic(TinyGame game) {
		if (x < -30 || y > 170) {
			game.entities.remove(this);
		}
		if (hit)
			y *= 1.03;
		move();
	}
	
	public void render(int[] pixels) {
		if (x >= 0 && x <= TinyGame.WIDTH && y >= 0 && y <= TinyGame.HEIGHT) {
			if (TinyGame.step % 5 == 0) {
				bm[0].render(x, y, pixels);
			} else if (TinyGame.step % 4 == 0) {
				bm[1].render(x, y, pixels);
			} else if (TinyGame.step % 7 == 0) {
				bm[2].render(x, y, pixels);
			} else {
				bm[3].render(x, y, pixels);
			}
		}
	}
	
	public void move() {
		x--;
		if (TinyGame.step % 20 == 0) {
			y -= 2;
		}
		if (TinyGame.step % 25 == 0 && y < TinyGame.HEIGHT - 16) {
			y += 2;
		}
	}
}
