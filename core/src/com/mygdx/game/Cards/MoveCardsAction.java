package com.mygdx.game.Cards;


import com.badlogic.gdx.Gdx;
import com.mygdx.game.Players.Player;

import java.util.ArrayList;
import java.util.List;

public class MoveCardsAction implements CardAction {

    private ArrayList<Card> to;
    private ArrayList<Card> from;
    private final int amount;

    //
    public MoveCardsAction(ArrayList<Card> to, int amount) {
        this.to = to;
        this.amount = amount;
    }
    //D
    public MoveCardsAction(ArrayList<Card> from, ArrayList<Card> to, int amount)
    {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
    //Swap
    private void moveCards(ArrayList<Card> from, ArrayList<Card> to,int amount) {

        int size = from.size();
        if (amount > size)
        {
            Gdx.app.error("Move Cards", String.format("Cannot remove %d from a deck with %d cards", amount, size));
        }
        for (int i=0; i<amount; i++) {
            to.add(to.size(), from.remove(0));
        }
    }

    @Override
    public void execute(Player user) {
        Gdx.app.log("Move Cards","Using");
      //  moveCards(from, to, amount);
    }
/*
    @Override
    public void execute(Player fromPlayer, Player toPlayer, Deck deck, GameManager gameManager) {
        for (int i = 0; i < amount; i++) {
            Card cardToMove = null;
            switch (from) {
                case "player":
                    cardToMove = fromPlayer.drawCard(deck);
                    break;
                case "machine":
                    cardToMove = toPlayer.drawCard(deck);
                    break;
                case "deck":
                    cardToMove = deck.drawCard();
                    break;
            }
            if (cardToMove != null) {
                switch (to) {
                    case "player":
                        fromPlayer.addCardToHand(cardToMove);
                        break;
                    case "machine":
                        toPlayer.addCardToHand(cardToMove);
                        break;
                    case "deck":
                        deck.addCard(cardToMove);
                        break;
                }
            }
        }
    }

 */
}
