package com.mygdx.game.Cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.mygdx.game.Managers.BorderTextureManager;
import com.mygdx.game.Managers.TextureManager;

import java.util.Iterator;

public class Card extends Actor implements Comparable<Card> {
    private final Type type;
    private final int value;
    private Vector2 cardPosition;
    private Vector2 cardSize;
    private final Texture texture;
    private final Texture borderTexture;
    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 150;
    private boolean isSpecialCard = false;
    private CardAction specialAction;



    public Card(String typeName, int value, TextureManager textureManager, BorderTextureManager borderTextureManager, Colors borderColor) {
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

    //Getters
    public Type getType() {
        return type;
    }
    public Vector2 getCardSize() {
        return cardSize;
    }
    public Vector2 getCardPosition() {
        return cardPosition;
    }
    public int getValue() {
        return value;
    }
    public CardAction getSpecialAction()
    {
        System.out.println("TEST");
        return specialAction;
    }
    public boolean isSpecialCard() {
        return isSpecialCard;
    }

    //Setters
    public void setCardPosition(float x, float y) {
        cardPosition = new Vector2(x, y);
    }
    public void setSpecialAction(CardAction action) {
        this.specialAction = action;
        isSpecialCard = true;
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

    private void drawCardNumber(BitmapFont font, SpriteBatch batch) {
        String numberText = String.valueOf(value);
        float numberX = cardPosition.x + cardSize.x - 15.5f;
        float numberY = cardPosition.y + cardSize.y - 15 + font.getLineHeight() / 2;
        font.draw(batch, numberText, numberX, numberY);
    }

    public boolean isTouched(float touchX, float touchY) {
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

    //Actor
    public void animateToPosition(float x, float y, float duration)
    {
        MoveToAction placementAction = new MoveToAction();
        placementAction.setPosition(x,y);
        placementAction.setDuration(duration);
    }
    @Override
    public void act(float delta){
        for(Iterator<Action> iter = this.getActions().iterator(); iter.hasNext();){
            iter.next().act(delta);
        }
    }
}
