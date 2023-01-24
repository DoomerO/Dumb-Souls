package sounds;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

public class SoundPlayer{
	
	private Clip clip;
	public SoundPlayer(String sound) {
		try {
			File file = new File("res/sounds/" + sound);
			if (file.exists()) {
				AudioInputStream audioInp = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(audioInp);
			}
			else {
				throw new RuntimeException("This file does not exists!!!" + sound);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}
