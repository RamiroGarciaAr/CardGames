package com.mygdx.game.Cards;

import com.mygdx.game.Players.Player;

import java.util.ArrayList;

public class LookAtAction implements CardAction {

    private int amount;
    private ArrayList<Card> storedCards = new ArrayList<>();

    public LookAtAction(int amount)
    {
        this.amount = amount;
    }
    public LookAtAction(ArrayList<Card> deck, int amount)
    {
        this.amount = amount;
        this.storedCards = deck;

    }
    @Override
    public void execute(Player opponent) {

        System.out.println("You are looking at the opponents Hand\nHe has a:");
        if (storedCards.isEmpty())
        {
            for (int i =0;i<amount;i++)
            {
                ArrayList<Card> opponentCardsInHand = opponent.getCardsInHand();
                System.out.println(opponentCardsInHand.get(i).toString());
            }
        }
        else
        {
            for (int i=0;i<amount;i++)
            {
                System.out.println(storedCards.get(i).toString());
            }
        }

    }
}
