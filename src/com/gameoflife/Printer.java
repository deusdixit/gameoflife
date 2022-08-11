package com.gameoflife;

import com.gameoflife.metric.Metric;

import java.util.List;

public class Printer implements Visual {

    private boolean firstPrint = true;
    private Metric metric;

    public Printer() {
    }


    @Override
    public void output(Board board) {
        if (firstPrint) {
            System.out.println(board);
            System.out.print("\033[2K");
            firstPrint = false;
        } else {
            System.out.print("\033[0G");
            System.out.print("\033[s");
            List<Cell> changedCells = board.getChangedCells();
            for (Cell cell : changedCells) {
                System.out.printf("\033[%d;%df", cell.getX()+1, cell.getY() * 2 +1 );
                System.out.print(cell);
            }
            board.removeAllChangedCells();
            System.out.print("\033[u");
        }
        System.out.printf("Generation: %d | ", board.getGeneration());
        System.out.print(metric);
    }

    @Override
    public void setMetric(Metric m) {
        metric = m;
    }

}
