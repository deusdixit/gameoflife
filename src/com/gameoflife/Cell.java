package com.gameoflife;

import java.util.LinkedList;

public class Cell {

    private final String GREEN = "\u001b[32m";
    private final String WHITE = "\u001b[37m";
    private final String RESET = "\u001b[0m";
    private final String RED = "\u001b[31m";
    private final String BLOCK = "\u2584";

    private final String BLINKING = "\033[5m";

    private final String RESET_BLINKING = "\033[25m";

    private int discoNum = 1;

    public enum State { DEAD, ALIVE }

    private State current;

    private State nextState;

    private LinkedList<Cell> neighbors;

    private boolean disco;


    private int X, Y;

    public Cell(int x,int y,boolean disco) {
        X = x;
        Y = y;
        current = State.DEAD;
        nextState = State.DEAD;
        neighbors = new LinkedList<>();
        this.disco = disco;
    }

    public void addNeighbor(Cell c) {
        neighbors.add(c);
    }

    public State getCurrent() {
        return current;
    }

    public void setCurrent(State s) {
        current = s;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getDiscoNum() {
        return discoNum;
    }

    public boolean isDisco() {
        return disco;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State s) {
        nextState = s;
    }

    public void evalNextState() {
        int activeNeighbors = 0;
        for(Cell c : neighbors) {
            if (c.getCurrent() == State.ALIVE) {
                activeNeighbors++;
            }
        }
        setNextState(getCurrent());
        if (getCurrent() == State.ALIVE) {
            if ( activeNeighbors <= 1 || activeNeighbors > 3) {
                setNextState(State.DEAD);
            }
        } else {
            if (activeNeighbors == 3 ) {
                setNextState(State.ALIVE);
            }
        }
    }

    public boolean update() {
        boolean result = false;
        if ( getCurrent() != getNextState() ) {
            result = true;
            discoNum++;
        }
        setCurrent(getNextState());
        return result;
    }

    @Override
    public String toString() {
        if ( disco ) {
            return getCurrent() == State.ALIVE ? "\033[38;5;" + (discoNum % 255) + "m" + BLOCK + RESET : WHITE + BLOCK + RESET;
        } else {
            return getCurrent() == State.ALIVE ? RED + BLOCK + RESET : WHITE + BLOCK + RESET;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof Cell) {
            Cell c = (Cell)obj;
            return c.getX()==getX() && c.getY() == getY();
        }
        return false;
    }

}
