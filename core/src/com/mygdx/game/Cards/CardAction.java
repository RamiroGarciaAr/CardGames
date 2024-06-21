package com.mygdx.game.Cards;

import java.util.ArrayList;

public interface CardAction {

    ArrayList<Card> userDeck = new ArrayList<>();
    ArrayList<Card> computerDeck = new ArrayList<>();

    void execute();

}
