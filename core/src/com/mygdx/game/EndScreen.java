package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndScreen implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture backgroundTexture;
    private boolean playerWon;

    public EndScreen(boolean playerWon) {
        this.playerWon = playerWon;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        backgroundTexture = new Texture("Backgrounds/End/endscreen.jpg");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        String message = playerWon ? "Congratulations! You Won!" : "Game Over! You Lost!";
        font.draw(batch, message, (float) Gdx.graphics.getWidth() / 2 - 50, (float) Gdx.graphics.getHeight() / 2);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
    }
}