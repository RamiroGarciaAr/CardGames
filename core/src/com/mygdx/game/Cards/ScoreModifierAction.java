package com.mygdx.game.Cards;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Players.Player;

public class ScoreModifierAction implements CardAction
{
    private final int amount;
    public ScoreModifierAction(int amount)
    {
        this.amount = amount;
    }
    @Override
    public void execute(Player user)
    {
        Gdx.app.log("Score Modifier Action","Change Score");
        user.setScore(user.getScore() + amount);
    }
}
