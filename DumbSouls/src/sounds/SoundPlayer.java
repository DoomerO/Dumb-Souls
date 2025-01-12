package sounds;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

public class SoundPlayer{
	
	public Clip clip;
	
	public SoundPlayer(String sound) {
		try {
			File file = new File(sound);
			if (file.exists()) {
				AudioInputStream audioInp = AudioSystem.getAudioInputStream(file.toURI().toURL());
				clip = AudioSystem.getClip();
				clip.open(audioInp);
				
			}
			else {
				System.out.println("!!!This sound file does not exist: " + sound);
			}
		} catch(Exception exc) {
			exc.printStackTrace();
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
