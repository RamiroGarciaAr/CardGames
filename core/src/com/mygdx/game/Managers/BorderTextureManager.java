package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Cards.Colors;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BorderTextureManager {
    private final Map<Colors, Texture> borderTexturesMap = new HashMap<>();

    private final Colors[] borderColors = Colors.values();

    public BorderTextureManager() {
        for (Colors color : borderColors) {
            String fileName = "NT_" + color + ".png";
            FileHandle file = Gdx.files.internal("BorderColors/NoTextBorder/" + fileName);
            if (file.exists()) {
                borderTexturesMap.put(color, new Texture(file));
            } else {
                Gdx.app.error("BorderTextureManager", "Texture not found: " + fileName);
            }
        }
    }

    public Texture getBorderTexture(Colors color) {
        return borderTexturesMap.get(color);
    }

    public Texture getRandomBorderTexture() {
        Colors randomColor = borderColors[new Random().nextInt(borderColors.length)];
        return borderTexturesMap.get(randomColor);
    }
}
