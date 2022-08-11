package com.gameoflife.gui;

import com.gameoflife.Board;
import com.gameoflife.Cell;
import com.gameoflife.metric.Metric;
import com.gameoflife.Visual;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements Visual {

    private Board board;
    private int rectHeight, rectWidth;

    private final int OFFSET_X = 0;

    private final int OFFSET_Y = 0;

    private boolean firstRun = true;



    public MainFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setTitle("Game of Life");
        setVisible(true);
        setLocationRelativeTo(null);

    }

    private void paintAll(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY.brighter());
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Color c;
                Cell cell = board.getCell(i, j);
                if (cell.isDisco()) {
                    c = new Color(Color.HSBtoRGB(1.0f/((cell.getDiscoNum()%360)/100.0f),1.0f,1.0f));
                } else {
                    c = Color.RED;
                }
                if (cell.getCurrent() == Cell.State.ALIVE) {
                    g.setColor(c);
                } else {
                    g.setColor(Color.LIGHT_GRAY.brighter());
                }
                g.fillRect(j * rectWidth +OFFSET_X, i * rectHeight + OFFSET_Y, rectWidth, rectHeight);
                g.setStroke(new BasicStroke(1));
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(j * rectWidth + OFFSET_X, i * rectHeight + OFFSET_Y, rectWidth, rectHeight);
            }
        }
    }

    @Override
    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        if ( board != null ) {
            if ( firstRun ) {
                paintAll(g);
                firstRun = false;
            } else {
                java.util.List<Cell> changedCells = board.getChangedCells();
                while(changedCells.size() > 0) {
                    Cell cell = board.removeChangedCell();
                    Color c;
                    if (cell.isDisco()) {
                        c = new Color(Color.HSBtoRGB(1.0f/((cell.getDiscoNum()%360)/100.0f),1.0f,1.0f));
                    } else {
                        c = Color.RED;
                    }
                    if (cell.getCurrent() == Cell.State.ALIVE) {
                        g.setColor(c);
                    } else {
                        g.setColor(Color.LIGHT_GRAY.brighter());
                    }
                    g.fillRect(cell.getY() * rectWidth + OFFSET_X, cell.getX() * rectHeight + OFFSET_Y, rectWidth, rectHeight);
                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(cell.getY() * rectWidth + OFFSET_X, cell.getX() * rectHeight + OFFSET_Y, rectWidth, rectHeight);
                }
            }
        }

    }


    @Override
    public void output(Board board) {
        this.board = board;
        int newRectHeight = (int) (getSize().getHeight()) / board.getSize();
        int newRectWidth = (int) (getSize().getWidth()) / board.getSize();
        if ( newRectWidth != rectWidth || newRectHeight != rectHeight) {
            firstRun = true;
        }
        rectWidth = newRectWidth;
        rectHeight = newRectHeight;
        repaint();
    }

    @Override
    public void setMetric(Metric m) {

    }

}
