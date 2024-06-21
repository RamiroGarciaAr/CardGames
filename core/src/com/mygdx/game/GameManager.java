package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Cards.Card;
import com.mygdx.game.Cards.Deck;
import com.mygdx.game.Players.AI;
import com.mygdx.game.Players.Player;
import com.mygdx.game.Cards.Type;
import java.util.*;

public class GameManager
{
    private final List<Type> cardTypes;
    private Player playing;
    private int amountOfRounds;
    private int roundTimer;

    public GameManager(ArrayList<String> typeNames, int maxNumberOnDeck)
    {
        this.cardTypes = new ArrayList<>();
        initializeCardTypes(typeNames);
    }
    public GameManager(ArrayList<String> typeNames,int maxNumberOnDeck,int amountOfRounds)
    {
        this.cardTypes = new ArrayList<>();
        initializeCardTypes(typeNames);
        this.amountOfRounds = amountOfRounds;
    }
    public GameManager(ArrayList<String>  typeNames, int maxNumberOnDeck, List<Type> cardTypes, int amountOfRounds, int roundTimer)
    {
        this.cardTypes = cardTypes;
        initializeCardTypes(typeNames);
        this.amountOfRounds = amountOfRounds;
        this.roundTimer = roundTimer;
    }


    public void playRound(Card playerCard, Player player, AI machine)
    {
        if (playerCard != null)
        {
            if (!machine.getCardsInHand().isEmpty())
            {
                Card machineCard = machine.playRandomCard();

                int comparisonResult = CardComparator.compare(playerCard, machineCard);
                if (comparisonResult > 0) {
                    player.setScore(player.getScore() + 1);
                    System.out.println("Player wins with " + playerCard + " against " + machineCard);
                } else if (comparisonResult < 0) {
                    machine.setScore(machine.getScore() + 1);
                    System.out.println("Machine wins with " + machineCard + " against " + playerCard);
                } else {
                    System.out.println("It's a tie with " + playerCard + " and " + machineCard);
                }
            }
        }
        else
            Gdx.app.error("GameManager", "ERROR: One of the players has used a NULL card");
    }

    private void initializeCardTypes(ArrayList<String> typeNames)
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
    public void dealInitialCards(Player player,int numCards, Deck deck)
    {
        for(int i=0; i<numCards;i++ )
        {
            player.addCardToHand(deck.drawCard());
        }
    }

}
class CardComparator {
    public static int compare(Card o1, Card o2) {
        if (o1.getType().canBeat(o2.getType())) {
            return 1;
        } else if (o2.getType().canBeat(o1.getType())) {
            return -1;
        } else {
            return o1.compareTo(o2);
        }
    }
}
