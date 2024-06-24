package com.mygdx.game.Players;

import com.mygdx.game.Cards.Card;

import java.util.Random;

public class AI extends Player
{

    public AI(int startingScore, int amountOfCardInHand) {
        super(startingScore, amountOfCardInHand);
    }

    public Card playRandomCard()
    {
        Random random = new Random();
        int index = random.nextInt(getCardsInHand().size());
        return playCard(index);
    }
    @Override
    public String toString()
    {
        return "Machine";
    }
}
