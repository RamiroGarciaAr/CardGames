package com.mygdx.game.Cards;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Managers.BorderTextureManager;
import com.mygdx.game.Managers.TextureManager;

import java.util.*;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();
    private TextureManager textureManager;
    private BorderTextureManager borderTextureManager;
    private HashMap<String, Colors> typeColorsMap = new HashMap<>();

    private ArrayList<String> typeNames;
    private final int maxNumberOnDeck;

    public Deck(ArrayList<String> typeNames, int maxNumberOnDeck) {
        this.textureManager = new TextureManager(typeNames);
        this.borderTextureManager = new BorderTextureManager();

        assignRandomColorsToTypesIfNeeded(typeNames);

        this.typeNames = typeNames;
        this.maxNumberOnDeck = maxNumberOnDeck;

    }
    public void generateDeck()
    {
        for (String typeName : typeNames) {
            for (int cardNumber = 1; cardNumber <= maxNumberOnDeck; cardNumber++) {
                deck.add(new Card(typeName, cardNumber, textureManager, borderTextureManager, getColorForType(typeName)));
            }
        }
        shuffle();
    }

    public void assignColorToType(String typeName, Colors color, boolean forceUpdate) {
        if (typeColorsMap.containsKey(typeName) && !forceUpdate) {
            return;
        }
        if (typeColorsMap.containsValue(color) && !forceUpdate) {
            throw new IllegalArgumentException("Color already assigned to another type.");
        }
        typeColorsMap.put(typeName, color);
    }
    private Card findCardInDeck(String typeName, int cardValue) {
        for (Card card : deck)
        {

            if (Objects.equals(typeName, card.getType().toString()) && cardValue == card.getValue()) {
                return card;
            }
        }
        return null;
    }
    public void AddSpecialAbilityTo(String typeName, int cardValue, CardAction action) {
       Card aux = findCardInDeck(typeName,cardValue);
       if (aux == null)
          Gdx.app.error("Deck","Null Card it is not in deck");
       else
        aux.setSpecialAction(action);
    }
    public void AddSpecialAbilityTo(int cardValue, CardAction action)
    {
        for (String type : typeNames)
        {
            AddSpecialAbilityTo(type,cardValue,action);
        }
    }
    public void AddSpecialAbilityTo(String type,CardAction action)
    {
        for (int number=0 ;number<maxNumberOnDeck;number++)
        {
            AddSpecialAbilityTo(type,number,action);
        }
    }

    public void assignColorToType(String typeName, Colors color) {
        assignColorToType(typeName, color, false);
    }

    private void assignRandomColorsToTypesIfNeeded(ArrayList<String> typeNames) {
        Colors[] colors = Colors.values();
        Random random = new Random();
        for (String typeName : typeNames) {
            if (!typeColorsMap.containsKey(typeName)) {
                Colors color;
                do {
                    color = colors[random.nextInt(colors.length)];
                } while (typeColorsMap.containsValue(color)); // Ensure unique colors for each type
                typeColorsMap.put(typeName, color);
            }
        }
    }



    public Colors getColorForType(String typeName) {
        return typeColorsMap.get(typeName);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card drawCard() {
        if (!deck.isEmpty()) {
            return deck.remove(0);
        } else {
            return null;
        }
    }
    public ArrayList<Card> getDeck()
    {
        return deck;
    }

    public void addCard(Card cardToMove)
    {
        deck.add(0,cardToMove);
    }
}
