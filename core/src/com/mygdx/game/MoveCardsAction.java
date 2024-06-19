package com.mygdx.game;


public class MoveCardsAction implements CardAction {
    private final String from;
    private final String to;
    private final int amount;

    public MoveCardsAction(String from, String to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

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
}
