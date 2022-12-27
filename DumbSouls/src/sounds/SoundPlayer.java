package sounds;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

public class SoundPlayer{
	
	public static void PlaySound(String sound) {
		try {
			File file = new File("res/sounds/" + sound);
			if (file.exists()) {
				AudioInputStream audioInp = AudioSystem.getAudioInputStream(file.toURI().toURL());
				Clip clip = AudioSystem.getClip();
				clip.open(audioInp);
				clip.start();
			}
			else {
				System.out.println("!!!This sound file does not exists: " + sound);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void PlaySoundTrack(String music) {
		try {
			File file = new File("res/sounds/" + music);
			if (file.exists()) {
				AudioInputStream audioInp = AudioSystem.getAudioInputStream(file.toURI().toURL());
				Clip clip = AudioSystem.getClip();
				clip.open(audioInp);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				System.out.println("!!!This sound file does not exists: " + music);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
