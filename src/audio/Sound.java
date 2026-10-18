
package audio;

import java.net.URL;
import java.util.EnumMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    private final EnumMap<SoundEffect, Clip> soundEffects = new EnumMap<>(SoundEffect.class);
    private final Clip musicClip;

    public Sound(){
        createSoundEffects();
        musicClip = createClip("/assets/Sound/themeSong.wav");
    }

    private void createSoundEffects() {
        soundEffects.put(SoundEffect.USE, createClip("/assets/Sound/eatSound.wav"));
        soundEffects.put(SoundEffect.STEP, createClip("/assets/Sound/stepSound.wav"));
        soundEffects.put(SoundEffect.PICKUP, createClip("/assets/Sound/itemPickup.wav"));
        soundEffects.put(SoundEffect.LOWHP, createClip("/assets/Sound/lowHpSong.wav"));
    }

    private Clip createClip(String filePath) {
        URL soundUrl = getClass().getResource(filePath);
        if (soundUrl == null) {
            throw new IllegalStateException("cant find file");
        }

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl)) {
            Clip newClip = AudioSystem.getClip();
            newClip.open(ais);
            return newClip;
        } catch (Exception e) {
            throw new IllegalStateException("cant load file");
        }
    }

    public Clip getSoundEffect(SoundEffect soundEffect) {
        return soundEffects.get(soundEffect);
    }

    public void playSound(SoundEffect soundEffect) {
        Clip soundClip = getSoundEffect(soundEffect);
        if (soundClip == null) {
            throw new IllegalArgumentException("cant find sound");
        }
        soundClip.setFramePosition(0);
        soundClip.start();
    }

    public void playMusic(){
        musicClip.setFramePosition(0);
        musicClip.start();
    }

    public void loopMusic(){
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopMusic(){
        musicClip.stop();
    }
}
