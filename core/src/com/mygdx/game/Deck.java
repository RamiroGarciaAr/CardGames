package com.mygdx.game;


import java.util.ArrayList;
import java.util.Collections;


public class Deck
{
    private ArrayList<Card> deck = new ArrayList<Card>();
    private TextureManager textureManager;

    private BorderTextureManager borderTextureManager;

    public Deck(String[] typeNames,int maxNumberOnDeck)
    {
        this.textureManager = new TextureManager(typeNames);
        this.borderTextureManager = new BorderTextureManager();
        for (String typeName : typeNames) {
            for (int cardNumber = 1; cardNumber <= maxNumberOnDeck; cardNumber++)
            {
                deck.add(new Card(typeName, cardNumber,textureManager,borderTextureManager,null));
            }
        }
        Shuffle();
    }
    public void Shuffle()
    {
        Collections.shuffle(deck);
    }
    public Card drawCard()
    {
        if (!deck.isEmpty())
        {
            return deck.remove(0);
        }
        else return null;
    }
}
