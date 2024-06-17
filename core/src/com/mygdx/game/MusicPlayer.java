package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private List<Music> playlist;
    private int currentSongIndex;

    public MusicPlayer() {
        playlist = new ArrayList<>();
        currentSongIndex = 0;
    }

    public void loadSongs(String[] songPaths) {
        for (String path : songPaths) {
            Music music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Music/" + path));
            playlist.add(music);
        }
    }

    public void play() {
        if (!playlist.isEmpty()) {
            Music currentSong = playlist.get(currentSongIndex);
            currentSong.setVolume(0.1f);
            currentSong.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    playNextSong();
                }
            });
            currentSong.play();

        }
    }

    private void playNextSong() {
        currentSongIndex++;
        if (currentSongIndex >= playlist.size()) {
            currentSongIndex = 0; // Repetir la lista de reproducción
        }
        Music nextSong = playlist.get(currentSongIndex);
        nextSong.setVolume(0.1f);
        nextSong.play();
    }

    public void dispose() {
        for (Music music : playlist) {
            music.dispose();
        }
    }
}
