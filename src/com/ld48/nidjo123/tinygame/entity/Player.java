package com.ld48.nidjo123.tinygame.entity;

import java.awt.Rectangle;

import com.ld48.nidjo123.tinygame.Art;
import com.ld48.nidjo123.tinygame.Bitmap;
import com.ld48.nidjo123.tinygame.Sound;
import com.ld48.nidjo123.tinygame.TinyGame;

public class Player extends Entity {
	private int x = 0;
	private int y = 0;
	public int health = 100;
	public int step = 0, wStep = 0;
	int dir = 0;
	int lastDir = 0;
	private int num = 2;
	public boolean up, down, right, left, shoot, onGround;
	
	public static final Player player = new Player(20, 80);
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void hit() {
		health -= 10;
		Sound.phurt.play();
	}
	
	Bitmap[] bm = { Art.spriteSheet[0][0], Art.spriteSheet[0][1], Art.spriteSheet[0][2], Art.spriteSheet[0][3], Art.spriteSheet[1][0],
			Art.spriteSheet[1][1] };
	
	long shootTime = System.currentTimeMillis();
	
	public void doLogic(TinyGame game) {
		long passed = System.currentTimeMillis() - shootTime;
		
		if (health <= 0) {
			TinyGame.gameInstance.setLost();
		}
		
		if (y == 174) {
			onGround = true;
		} else
			onGround = false;
		
		step++;
		if (shoot && passed > 400) {
			game.entities.add(new Bullet(x + 10, y));
			Sound.fire.play();
			shootTime = System.currentTimeMillis();
		}
		move();
	}
	
	public void render(int[] pixels) {
		if (!onGround) {
			if (step % 5 == 0) {
				bm[0].render(x, y, pixels);
			} else if (step % 4 == 0) {
				bm[1].render(x, y, pixels);
			} else if (step % 7 == 0) {
				bm[2].render(x, y, pixels);
			} else {
				bm[3].render(x, y, pixels);
			}
		} else {
			if (wStep % 5 == 0) {
				bm[4].render(x, y, pixels);
			} else
				bm[5].render(x, y, pixels);
		}
	}
	
	public void move() {
		if (up && y > 0 && !TinyGame.bossFight) {
			y -= num;
		} else if (up && y > 15 && TinyGame.bossFight) {
			y -= num;
		}
		if (down && y < 174) {
			y += num;
		}
		if (right && x < 50) {
			x += num;
		}
		if (left && x > 0) {
			x -= num;
		}
		if (right && x < 50 && onGround) {
			wStep++;
		}
		if (left && x > 0 && onGround) {
			wStep++;
		}
		if (step % 20 == 0 && y > 0 && !onGround) {
			y -= 2;
		}
		if (step % 25 == 0 && y < TinyGame.HEIGHT - 16 && !onGround) {
			y += 2;
		}
	}
	
	public void resetKeys() {
		down = false;
		up = false;
		right = false;
		left = false;
		shoot = false;
	}
	
	public int getHealth() {
		return health;
	}
}
