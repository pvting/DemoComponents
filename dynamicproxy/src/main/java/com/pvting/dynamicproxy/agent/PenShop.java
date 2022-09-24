package com.pvting.dynamicproxy.agent;

public class PenShop implements IShop{
    @Override
    public String sell() {
        return "Pen";
    }
}
