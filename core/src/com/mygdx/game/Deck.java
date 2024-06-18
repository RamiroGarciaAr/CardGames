package com.mygdx.game;


import com.badlogic.gdx.Gdx;

import java.util.*;


public class Deck
{
    private ArrayList<Card> deck = new ArrayList<Card>();

    private Map<String,Colors> typeColorsMap = new HashMap<String,Colors>();

    public Deck(String[] typeNames,int maxNumberOnDeck)
    {
        TextureManager textureManager = new TextureManager(typeNames);
        BorderTextureManager borderTextureManager = new BorderTextureManager();
        System.out.println("MapSize: " + typeColorsMap.size() + " typesNames: " + typeNames.length);
        if (typeColorsMap.size() < typeNames.length)
        {
            Gdx.app.log("Deck","Insufficient colors defined for types. Using random colors.");
            assignRandomColorsToTypes(typeNames);
        }

        for (String typeName : typeNames) {
            for (int cardNumber = 1; cardNumber <= maxNumberOnDeck; cardNumber++)
            {
                deck.add(new Card(typeName, cardNumber, textureManager, borderTextureManager,getColorForType(typeName)));
            }
        }
        Shuffle();
    }
    public void assignColorToType(String typeName, Colors color) {
        if (typeColorsMap.containsValue(color)) {
            throw new IllegalArgumentException("Color already assigned to another type.");
        }
        System.out.println("Adding color to: " + typeName);
        typeColorsMap.put(typeName, color);
    }
    public void assignRandomColorsToTypes(String[] typeNames) {
        Colors[] colors = Colors.values();
        Random random = new Random();
        for (String typeName : typeNames) {
            Colors color;
            do {
                color = colors[random.nextInt(colors.length)];
            } while (typeColorsMap.containsValue(color)); // Ensure unique colors for each type
            typeColorsMap.put(String.valueOf(typeName), color);
        }
    }
    public Colors getColorForType(String typeName) {
        return typeColorsMap.get(typeName);
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
