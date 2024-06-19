package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import java.util.*;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();
    private TextureManager textureManager;
    private BorderTextureManager borderTextureManager;
    private HashMap<String, Colors> typeColorsMap = new HashMap<>();

    private String[] typeNames;
    private int maxNumberOnDeck;

    public Deck(String[] typeNames, int maxNumberOnDeck) {
        this.textureManager = new TextureManager(typeNames);
        this.borderTextureManager = new BorderTextureManager();

        Gdx.app.log("Deck", "Type names: " + Arrays.toString(typeNames) + " | Initial Map Size: " + typeColorsMap.size());

        assignRandomColorsToTypesIfNeeded(typeNames);

        Gdx.app.log("Deck", "Map Size after random assignment: " + typeColorsMap.size());
        logTypeColorsMap();
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
            Gdx.app.log("Deck", "Type " + typeName + " already has a color assigned: " + typeColorsMap.get(typeName));
            return;
        }
        if (typeColorsMap.containsValue(color) && !forceUpdate) {
            throw new IllegalArgumentException("Color already assigned to another type.");
        }
        Gdx.app.log("Deck", "Adding color " + color + " to type " + typeName);
        typeColorsMap.put(typeName, color);
        logTypeColorsMap();
    }

    public void AddSpecialAbilityTo(String typeName, int cardValue, CardAction action) {
        for (Card card : deck) {
            if (card.getType().toString().equals(typeName) && card.getValue() == cardValue) {
                card.setSpecialAction(action);
                System.out.println("Special ability added to " + typeName + " " + cardValue);
                break; // Assuming only one card should have this ability
            }
        }
    }

    public void assignColorToType(String typeName, Colors color) {
        assignColorToType(typeName, color, false);
    }

    private void assignRandomColorsToTypesIfNeeded(String[] typeNames) {
        Colors[] colors = Colors.values();
        Random random = new Random();
        for (String typeName : typeNames) {
            if (!typeColorsMap.containsKey(typeName)) {
                Colors color;
                do {
                    color = colors[random.nextInt(colors.length)];
                } while (typeColorsMap.containsValue(color)); // Ensure unique colors for each type
                typeColorsMap.put(typeName, color);
                Gdx.app.log("Deck", "Assigned random color " + color + " to type " + typeName);
            }
        }
        logTypeColorsMap();
    }

    private void logTypeColorsMap() {
        StringBuilder mapContents = new StringBuilder("Current typeColorsMap: ");
        for (Map.Entry<String, Colors> entry : typeColorsMap.entrySet()) {
            mapContents.append(entry.getKey()).append(" -> ").append(entry.getValue()).append(", ");
        }
        Gdx.app.log("Deck", mapContents.toString());
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

    public void addCard(Card cardToMove)
    {
        deck.add(0,cardToMove);
    }
}
