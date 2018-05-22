package com.ld48.nidjo123.tinygame.entity;

import com.ld48.nidjo123.tinygame.TinyGame;

public class Particle extends Entity {
	int life;
	int x, y;
	
	public Particle(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.life = life;
	}
	
	public void doLogic() {
		if (life-- < 0)
			TinyGame.gameInstance.entities.remove(this);
		x *= 0.999;
		y *= 0.999;
		y += 1.8;
	}
}
