package com.mygdx.game.Cards;

import com.mygdx.game.Players.Player;

public class LookAtAction implements CardAction {


    @Override
    public void execute(Player user) {
        System.out.println(user.toString()+ "is looking at cards");
    }
}
