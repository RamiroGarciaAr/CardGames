package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Card implements Comparable<Card> {
    private Type type;
    private int value;

    private Vector2 numberPivot;
    private Vector2 cardPosition;
    private Vector2 cardSize;
    private Texture texture;
    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 150;

    public Card(String typeName, int value, TextureManager textureManager) {
        this.type = new Type(typeName);
        this.value = value;
        this.texture = textureManager.getRandomTextureOfType(typeName);
        this.cardSize = new Vector2(CARD_WIDTH, CARD_HEIGHT);
        this.cardPosition = new Vector2();
        this.numberPivot = new Vector2();
    }

    public Type getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public Vector2 getCardSize() {
        return cardSize;
    }

    public Vector2 getNumberPivot() {
        return numberPivot;
    }

    public Vector2 getCardPosition() {
        return cardPosition;
    }

    public void setNumberPivot(float x, float y) {
        numberPivot = new Vector2(x, y);
    }

    public void setCardPosition(float x, float y) {
        cardPosition = new Vector2(x, y);
    }

    public void setCardSize(float x, float y) {
        cardSize = new Vector2(x, y);
    }

    public void drawCardBatch(SpriteBatch batch, BitmapFont font) {
        if (texture != null) {
            batch.draw(texture, cardPosition.x, cardPosition.y, cardSize.x, cardSize.y);
            drawCardNumber(font, batch);
        }
    }

    public void drawCardShape(ShapeRenderer shapeRenderer) {
        drawNumberCircle(shapeRenderer);
    }

    private void drawNumberCircle(ShapeRenderer shapeRenderer) {
        float circleRadius = 15;
        float circleX = cardPosition.x + cardSize.x / 2 - circleRadius;
        float circleY = cardPosition.y + cardSize.y / 2 - circleRadius;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.circle(circleX, circleY, circleRadius);
        shapeRenderer.end();
    }

    private void drawCardNumber(BitmapFont font, SpriteBatch batch) {
        String numberText = String.valueOf(value);
        float numberX = cardPosition.x + cardSize.x - 26;
        float numberY = cardPosition.y + cardSize.y - 26 + font.getLineHeight() / 2;
        font.draw(batch, numberText, numberX, numberY);
    }

    @Override
    public int compareTo(Card other) {
        return this.value - other.value;
    }

    @Override
    public String toString() {
        return value + " of " + type.toString();
    }
}
