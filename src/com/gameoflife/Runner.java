package com.gameoflife;

import com.gameoflife.metric.Metric;

import java.io.IOException;

public class Runner {

    private int fieldSize,generationPerSecond;
    private double randomRate;
    private String pattern;

    private boolean disco;

    private boolean gui;

    public Runner(int fieldSize, double randomRate, int generationPerSecond, String pattern,boolean disco,boolean gui) {
        this.fieldSize = fieldSize;
        this.generationPerSecond = generationPerSecond;
        this.randomRate = randomRate;
        this.pattern = pattern;
        this.disco = disco;
        this.gui = gui;
    }


    public void run(Visual visual) {
        try {
            Board board = new Board(fieldSize,disco);
            if (pattern != null) {
                boolean[][] pat = board.getPattern(pattern);
                board.setPattern(pat, fieldSize / 2 - (pat.length / 2), fieldSize / 2 - (pat[0].length / 2));
            } else {
                board.fillRandom(randomRate);
            }
            if (System.getProperty("os.name").toLowerCase() == "win") {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

            Metric metric = new Metric();
            visual.setMetric(metric);
            String tagReal = "Echtdauer";
            String tagPrint = "Printdauer";
            String tagSim = "Simulationdauer";
            while (true) {
                metric.startCapture(tagReal);
                metric.startCapture(tagPrint);
                visual.output(board);
                metric.stopCapture(tagPrint);
                metric.startCapture(tagSim);
                board.simulate();
                metric.stopCapture(tagSim);
                Thread.sleep(generationPerSecond);
                metric.stopCapture(tagReal);
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
