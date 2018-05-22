package com.ld48.nidjo123.tinygame;

import java.util.Random;

public class Bitmap {
	public int w, h;
	public int pixels[];
	
	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		
		pixels = new int[w * h];
	}
	
	public void render(int x, int y, int[] pixels2) {
		int k = 0;
		for (int i = y; i < y + h; i++) {
			for (int j = x; j < x + w; j++) {
				if (j >= 0 && pixels[k] != -3 && j < TinyGame.WIDTH && i <= TinyGame.HEIGHT) {
					pixels2[i * TinyGame.WIDTH + j] = pixels[k];
				}
				k++;
			}
		}
	}
	
}
