package com.ld48.nidjo123.tinygame.entity;

import com.ld48.nidjo123.tinygame.Art;
import com.ld48.nidjo123.tinygame.Sound;
import com.ld48.nidjo123.tinygame.TinyGame;
import com.ld48.nidjo123.tinygame.Bitmap;

public class Boss extends Entity {
	private int x, y;
	public static int health = 500;
	
	Bitmap[] bm = { Art.spriteSheet[2][0], Art.spriteSheet[2][1], Art.spriteSheet[3][0], Art.spriteSheet[3][1], Art.spriteSheet[2][2],
			Art.spriteSheet[2][3], Art.spriteSheet[3][2], Art.spriteSheet[3][3], Art.spriteSheet[2][4], Art.spriteSheet[2][5], Art.spriteSheet[3][4],
			Art.spriteSheet[3][5] };
	
	public Boss(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void hit() {
		health -= 10;
		Sound.hurt.play();
		if (health <= 0) {
			Sound.bossdie.play();
		}
	}
	
	public int getY() {
		return y;
	}
	
	public void doLogic(TinyGame game) {
		if (health <= 0) {
			game.setWin();
		}
		
		if (TinyGame.gameInstance.random.nextInt(40) == 10) {
			TinyGame.entities.add(new BossBullet(x, y));
		}
		move();
	}
	
	public void render(int[] pixels) {
		if (x >= 0 && x <= TinyGame.WIDTH && y >= 0 && y <= TinyGame.HEIGHT) {
			if (TinyGame.step % 5 == 0) {
				bm[0].render(x, y, pixels);
				bm[1].render(x + 16, y, pixels);
				bm[2].render(x, y + 16, pixels);
				bm[3].render(x + 16, y + 16, pixels);
			} else if (TinyGame.step % 4 == 0) {
				bm[4].render(x, y, pixels);
				bm[5].render(x + 16, y, pixels);
				bm[6].render(x, y + 16, pixels);
				bm[7].render(x + 16, y + 16, pixels);
			} else if (TinyGame.step % 7 == 0) {
				bm[8].render(x, y, pixels);
				bm[9].render(x + 16, y, pixels);
				bm[10].render(x, y + 16, pixels);
				bm[11].render(x + 16, y + 16, pixels);
			} else {
				bm[4].render(x, y, pixels);
				bm[5].render(x + 16, y, pixels);
				bm[6].render(x, y + 16, pixels);
				bm[7].render(x + 16, y + 16, pixels);
			}
		}
	}
	
	public void move() {
		int xx = Player.player.getX();
		int yy = Player.player.getY();
		
		if (yy < y + 15 && y > 0 && y < TinyGame.HEIGHT - 50) {
			y--;
		} else if (yy > y - 15 && y >= 0 && y < TinyGame.HEIGHT - 50) {
			y++;
		}
	}
}
