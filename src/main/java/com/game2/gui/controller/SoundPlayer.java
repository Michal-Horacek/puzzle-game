package com.game2.gui.controller;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.io.InputStream;
import java.net.*;
import java.util.HashMap;

/**
 * Created by horacekm on 16.10.2017.
 */
public class SoundPlayer {

    private HashMap<SoundType, AudioClip> soundList;

    private boolean soundOn;

    public SoundPlayer() {

        soundList = new HashMap<>();
    }

    public void loadSounds() throws URISyntaxException {
        soundList.put(SoundType.EXPLODE1, new AudioClip(getClass().getResource("/sounds/Explode1.mp3").toURI().toString()));
        soundList.put(SoundType.EXPLODE2, new AudioClip(getClass().getResource("/sounds/Explode2.mp3").toURI().toString()));
        soundList.put(SoundType.LASER, new AudioClip(getClass().getResource("/sounds/Laser.mp3").toURI().toString()));
        soundList.put(SoundType.LOCK, new AudioClip(getClass().getResource("/sounds/Lock.mp3").toURI().toString()));
        soundList.put(SoundType.PICKUP, new AudioClip(getClass().getResource("/sounds/Pickup.mp3").toURI().toString()));
        soundList.put(SoundType.STONE, new AudioClip(getClass().getResource("/sounds/Stone.mp3").toURI().toString()));
        soundList.put(SoundType.TELEPORT, new AudioClip(getClass().getResource("/sounds/Teleport.mp3").toURI().toString()));
        soundList.put(SoundType.ACTBUTTON, new AudioClip(getClass().getResource("/sounds/ActBtn.mp3").toURI().toString()));
    }

    public void loadGuiSounds() throws URISyntaxException {
        soundList.put(SoundType.ACT, new AudioClip(getClass().getResource("/sounds/Act.mp3").toURI().toString()));
        soundList.put(SoundType.BUTTON, new AudioClip(getClass().getResource("/sounds/Button.mp3").toURI().toString()));
    }

    public void playSound(SoundType soundType) {
        if(soundOn) {
            soundList.get(soundType).play();

        }
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }
}
