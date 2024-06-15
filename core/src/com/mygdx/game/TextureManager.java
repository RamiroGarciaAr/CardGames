package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.*;

public class TextureManager {
    private Map<String, ArrayList<Texture>> textureMap = new HashMap<>();

    public TextureManager(String[] typeNames) {
        // Print current working directory
        for (String folderTypeName : typeNames) {

            // Ensure the folder path is correct
            FileHandle folder = Gdx.files.internal("assets/"+folderTypeName + "/");
            if (folder.exists() && folder.isDirectory()) {
                textureMap.put(folderTypeName, new ArrayList<>()); // Initialize the set for textures
                FileHandle[] textureFiles = folder.list();
                Gdx.app.log("TextureManager", "Number of files found: " + textureFiles.length);

                for (FileHandle file : textureFiles) {
                    if (file.extension().equals("png") || file.extension().equals("jpg")) {
                        Gdx.app.log("TextureManager", "Loading texture: " + file.name());
                        try {
                            Texture texture = new Texture(file);
                            textureMap.get(folderTypeName).add(texture);
                        } catch (Exception e) {
                            Gdx.app.error("TextureManager", "Error loading texture: " + file.name() + " - " + e.getMessage(), e);
                        }
                    } else {
                        Gdx.app.log("TextureManager", "Skipped non-image file: " + file.name());
                    }
                }
            } else {
                Gdx.app.error("TextureManager", "ERROR: " + folderTypeName + " is not a directory or does not exist.");
            }
        }
        printTextureMap();
    }
    public Texture getRandomTextureOfType(String typeName) {
        ArrayList<Texture> textures = textureMap.get(typeName);
        if (textures != null && !textures.isEmpty()) {
            int index = new Random().nextInt(textures.size());
            return (Texture) textures.toArray()[index];
        }
        return null;
    }
    private void printTextureMap() {
        Gdx.app.log("TextureManager", "Texture Map Contents:");
        for (Map.Entry<String, ArrayList<Texture>> entry : textureMap.entrySet()) {
            Gdx.app.log("TextureManager", "Type: " + entry.getKey() + " has " + entry.getValue().size() + " textures");
        }
    }

    // Other methods to manage textures can be added here
}
