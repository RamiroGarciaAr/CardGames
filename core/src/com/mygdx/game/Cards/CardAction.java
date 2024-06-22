package com.mygdx.game.Cards;

import com.mygdx.game.Players.Player;

import java.util.ArrayList;

public interface CardAction {

    ArrayList<Card> userDeck = new ArrayList<>();
    ArrayList<Card> computerDeck = new ArrayList<>();

    void execute(Player user);

}
