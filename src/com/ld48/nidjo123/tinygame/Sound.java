package com.ld48.nidjo123.tinygame;

import javax.sound.sampled.*;

//Notch, thank you for this part of code!

public class Sound {
	public static Sound fire = loadSound("/snd/fire.wav");
	public static Sound phurt = loadSound("/snd/phurt.wav");
	public static Sound bossdie = loadSound("/snd/bossdie.wav");
	public static Sound hurt = loadSound("/snd/hurt.wav");
	public static Sound start = loadSound("/snd/start.wav");
	
	public static Sound loadSound(String string) {
		Sound sound = new Sound();
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(string));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			sound.clip = clip;
		} catch (Exception e) {
			System.out.println(e);
		}
		return sound;
	}
	
	private Clip clip;
	
	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
