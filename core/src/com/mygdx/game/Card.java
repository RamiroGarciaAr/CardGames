package com.mygdx.game;

public class Card implements Comparable<Card>
{
    private Type type;
    private int value;

    public Card(String typeName, int value) {
        this.type = new Type(typeName);
        this.value = value;
    }
    public Type getType()
    {
        return type;
    }

    public int getValue()
    {
        return value;
    }

    public int compareTo(Card other) {
        return this.value - other.value;
    }
    @Override
    public String toString()
    {
        return value + "of" + type.toString();
    }

}
