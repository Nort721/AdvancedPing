package com.nort721.advping.data;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

@Setter
@Getter
public class TickHolder {
    public static final Queue<TickHolder> tickHolders = new LinkedList<>();
    public TickHolder() {
        tickHolders.add(this);
    }
    private int action;

    // when action happened, action was set to 0
    public boolean hasPassed(int ticks) {
        return action >= ticks;
    }
}
