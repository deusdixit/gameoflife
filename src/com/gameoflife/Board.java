package com.gameoflife;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private int generation = 0;
    private Cell[][] board;

    private List<Cell> changedCells;

    private boolean disco;

    public Board(int size,boolean disco) {
        board = new Cell[size][size];
        this.disco = disco;
        initBoard();
        initNeighbors();
        changedCells = Collections.synchronizedList(new LinkedList<Cell>());
    }

    public boolean[][] getPattern(String pattern) {
        String[] lines = pattern.split("\n");
        int longestLine = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > longestLine) {
                longestLine = lines[i].length();
            }
        }
        boolean[][] pat = new boolean[lines.length][longestLine];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                if (lines[i].charAt(j) == 'O') {
                    pat[i][j] = true;
                } else {
                    pat[i][j] = false;
                }
            }
        }
        return pat;
    }

    public void setPattern(boolean[][] pattern, int x, int y) {
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[i].length; j++) {
                setCell(x + i, y + j, pattern[i][j] ? Cell.State.ALIVE : Cell.State.DEAD);
            }
        }
    }

    public void fillRandom(double rate) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (Math.random() <= rate) {
                    board[i][j].setCurrent(Cell.State.ALIVE);
                }
            }
        }
    }

    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(i, j,disco);
            }
        }
    }

    public void setCell(int i, int j, Cell.State s) {
        int x = (i % board.length + board.length) % board.length;
        int y = (j % board.length + board.length) % board.length;
        board[x][y].setCurrent(s);
    }

    private void initNeighbors() {
        int[][] ops = new int[][]{{1, 0}, {1, 1}, {1, -1}, {0, 1}, {0, -1}, {-1, 1}, {-1, 0}, {-1, -1}};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                for (int[] op : ops) {
                    int x = i + op[0];
                    int y = j + op[1];
                    x = (x % board.length + board.length) % board.length;
                    y = (y % board.length + board.length) % board.length;
                    board[i][j].addNeighbor(board[x][y]);
                }
            }
        }
    }

    public void simulate() {
        //changedCells.clear();
        generation++;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].evalNextState();
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if ( board[i][j].update() && !changedCells.contains(board[i][j])) {
                    changedCells.add(board[i][j]);
                }
            }
        }
    }

    public int getGeneration() {
        return generation;
    }

    public Cell getCell(int i,int j) {
        return board[i][j];
    }

    public int getSize() {
        return board.length;
    }

    public List<Cell> getChangedCells() {
        return changedCells;
    }

    public Cell removeChangedCell() {
        return changedCells.remove(0);
    }

    public void removeAllChangedCells() {
        changedCells.clear();
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                result += board[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }
}
