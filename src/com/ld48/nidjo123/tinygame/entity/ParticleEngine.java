package com.ld48.nidjo123.tinygame.entity;

import java.util.Random;

import com.ld48.nidjo123.tinygame.Art;
import com.ld48.nidjo123.tinygame.TinyGame;
import com.ld48.nidjo123.tinygame.Bitmap;

public class ParticleEngine {
	public static final ParticleEngine pe = new ParticleEngine();
	
	Random random = TinyGame.gameInstance.random;
	
	Bitmap bm[] = { Art.spriteSheet[2][4], Art.spriteSheet[2][5], Art.spriteSheet[2][6], Art.spriteSheet[2][7] };
	
	public void castParticles(int x, int y, int ammount, int[] pixels) {
		for (int i = 0; i < ammount; i++) {
			int z = random.nextInt(20);
			pixels[y * TinyGame.WIDTH + x - z] = -1;
		}
	}
}
