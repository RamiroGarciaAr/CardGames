package com.mygdx.game;

import java.util.ArrayList;
import java.util.Collections;


public class Deck
{
    private ArrayList<Card> deck = new ArrayList<Card>();

    public Deck(String[] typeNames,int maxNumberOnDeck)
    {
        for (String typeName : typeNames) {
            for (int cardNumber = 1; cardNumber <= maxNumberOnDeck; cardNumber++) {
                deck.add(new Card(typeName, cardNumber));
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
