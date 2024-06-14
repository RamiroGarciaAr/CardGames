package com.mygdx.game;

import java.util.HashSet;

import java.util.Set;

public class Type
{
    private final String typeName;
    private final Set<String> winSet;

    public Type(String typeName)
    {
        this.typeName = typeName;
        this.winSet = new HashSet<>();
    }
    public void addBeats(String otherTypeName)
    {
        winSet.add(otherTypeName);
    }
    public String getTypeName()
    {
        return typeName;
    }
    public boolean canBeat(Type otherType)
    {
        return winSet.contains(otherType.typeName);
    }
    @Override
    public String toString()
    {
        return typeName;
    }

}



