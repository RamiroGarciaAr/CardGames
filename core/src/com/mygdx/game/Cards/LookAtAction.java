package com.mygdx.game.Cards;

import com.mygdx.game.Players.Player;

public class LookAtAction implements CardAction {

    private int amount;
    public LookAtAction(int amount)
    {
        this.amount = amount;
    }
    @Override
    public void execute(Player user) {
        System.out.println(user.toString()+ "is looking at cards");
    }
}
