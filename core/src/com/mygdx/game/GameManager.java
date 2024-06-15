package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

public class GameManager
{
    private final List<Type> cardTypes;
    public final Deck deck;
    private int amountOfRounds;
    private int roundTimer;

    public GameManager(String[] typeNames, int maxNumberOnDeck)
    {
        this.cardTypes = new ArrayList<>();
        initializeCardTypes(typeNames);
        this.deck = new Deck(typeNames,maxNumberOnDeck);
    }
    public GameManager(String[] typeNames,int maxNumberOnDeck,int amountOfRounds)
    {
        this.cardTypes = new ArrayList<>();
        initializeCardTypes(typeNames);
        this.deck = new Deck(typeNames,maxNumberOnDeck);
        this.amountOfRounds = amountOfRounds;
    }
    public GameManager(String[] typeNames,int maxNumberOnDeck,int amountOfRounds,int roundTimer)
    {
        this.cardTypes = new ArrayList<>();
        initializeCardTypes(typeNames);
        this.deck = new Deck(typeNames,maxNumberOnDeck);
        this.amountOfRounds = amountOfRounds;
        this.roundTimer = roundTimer;
    }
    private void initializeCardTypes(String[] typeNames)
    {
        for (String typeName : typeNames)
        {
            cardTypes.add(new Type(typeName));
        }
    }
    private Type findTypeByName(String typeName)
    {
        for(Type type :  cardTypes)
        {
            if (type.getTypeName().equalsIgnoreCase(typeName)) return type;
        }
        return null;
    }
    public void addTypeRelation(String typeName,String beatsTypeName)
    {
        Type type = findTypeByName(typeName);
        if (type != null && findTypeByName(beatsTypeName)!= null)
        {
            type.addBeats(beatsTypeName);
        }
    }

    public void dealInitialCards(Player player,int numCards)
    {
        for(int i=0; i<numCards;i++ )
        {
            player.addCardToHand(deck.drawCard());
        }
    }
    public Deck getDeck()
    {
        return deck;
    }
}
