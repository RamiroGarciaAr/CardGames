package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Card implements Comparable<Card> {
    private final Type type;
    private final int value;
    private Vector2 cardPosition;
    private Vector2 cardSize;
    private final Texture texture;
    private final Texture borderTexture;
    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 150;

    public Card(String typeName, int value, TextureManager textureManager,BorderTextureManager borderTextureManager, Colors borderColor) {
        this.type = new Type(typeName);
        this.value = value;
        this.texture = textureManager.getRandomTextureOfType(typeName);
        this.cardSize = new Vector2(CARD_WIDTH, CARD_HEIGHT);
        this.cardPosition = new Vector2();


        if (borderColor != null) {
            this.borderTexture = borderTextureManager.getBorderTexture(borderColor);
        } else {
            this.borderTexture = borderTextureManager.getRandomBorderTexture();
        }
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

    public Vector2 getCardPosition() {
        return cardPosition;
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
            if (borderTexture != null) {
                batch.draw(borderTexture, cardPosition.x, cardPosition.y, cardSize.x, cardSize.y);
            }
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
        float numberX = cardPosition.x + cardSize.x - 15.5f;
        float numberY = cardPosition.y + cardSize.y - 15 + font.getLineHeight() / 2;
        font.draw(batch, numberText, numberX, numberY);
    }
    public boolean isTouched(float touchX, float touchY)
    {
        return touchX >= cardPosition.x && touchX <= cardPosition.x + cardSize.x
                && touchY >= cardPosition.y && touchY <= cardPosition.y + cardSize.y;
    }
    public void highlightCard(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(cardPosition.x, cardPosition.y, cardSize.x, cardSize.y);
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
