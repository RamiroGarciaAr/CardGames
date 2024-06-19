package com.mygdx.game;

public interface CardAction {
    void execute(Player fromPlayer, Player toPlayer, Deck deck, GameManager gameManager);
}
