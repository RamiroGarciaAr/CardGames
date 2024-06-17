package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BorderTextureManager {
    private final Map<Colors, Texture> borderTextures = new HashMap<>();
    private final Colors[] borderColors = Colors.values();

    public BorderTextureManager() {
        for (Colors color : borderColors) {
            String fileName = "NT_" + color + ".png";
            FileHandle file = Gdx.files.internal("BorderColors/NoTextBorder/" + fileName);
            if (file.exists()) {
                borderTextures.put(color, new Texture(file));
            } else {
                Gdx.app.error("BorderTextureManager", "Texture not found: " + fileName);
            }
        }
    }

    public Texture getBorderTexture(Colors color) {
        return borderTextures.get(color);
    }


    public Texture getRandomBorderTexture() {
        Colors randomColor = borderColors[new Random().nextInt(borderColors.length)];
        return borderTextures.get(randomColor);
    }
}
