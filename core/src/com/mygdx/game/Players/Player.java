
package com.mygdx.game.Players;

import com.mygdx.game.Cards.Card;
import com.mygdx.game.Cards.Deck;

import java.util.ArrayList;

public class Player
{
    private int score;
    private final int amountOfCardInHand;
    private ArrayList<Card> cardsInHand;
    public Player(int startingScore, int amountOfCardInHand)
    {
        cardsInHand = new ArrayList<>();
        this.score = startingScore;
        this.amountOfCardInHand = amountOfCardInHand;
    }
    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    public ArrayList<Card> getCardsInHand()
    {
        return cardsInHand;
    }
    public void addCardToHand(Card card)
    {
        cardsInHand.add(card);
    }
    public Card playCard(int idx)
    {
        if (idx >= 0 && idx < amountOfCardInHand)
        {
            return cardsInHand.remove(idx);
        }
        return  null;
    }

    public Card drawCard(Deck deck)
    {
        if (!cardsInHand.isEmpty())
        {
            Card cardRemoved = cardsInHand.remove(0);
            cardsInHand.add(0,deck.drawCard());
            return cardRemoved;
        }
        else return null;
    }
}
