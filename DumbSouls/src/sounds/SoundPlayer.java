package sounds;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

public class SoundPlayer{
	
	public Clip clip;
	
	public SoundPlayer(String sound) {
		try {
			File file = new File("res/sounds/" + sound);
			if (file.exists()) {
				AudioInputStream audioInp = AudioSystem.getAudioInputStream(file.toURI().toURL());
				this.clip = AudioSystem.getClip();
				clip.open(audioInp);
				
			}
			else {
				System.out.println("!!!This sound file does not exists: " + sound);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void PlaySound() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void LoopSound() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void StopSound() {
		clip.stop();
	}
}
