package com.mygdx.game;

import java.util.Random;

public class AI extends Player
{

    AI(int startingScore, int amountOfCardInHand) {
        super(startingScore, amountOfCardInHand);
    }

    public Card playRandomCard()
    {
        Random random = new Random();
        int index = random.nextInt(getCardsInHand().size());
        return playCard(index);
    }
}
