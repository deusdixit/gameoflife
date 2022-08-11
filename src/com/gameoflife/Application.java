package com.gameoflife;

import com.gameoflife.gui.MainFrame;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Application {

    public static void main(String[] args) throws InterruptedException, IOException {
        int fieldSize = 30;
        double randomRate = 0.5;
        int generationPerSecond = 10;
        String pattern = null;
        boolean disco = false;
        boolean gui = false;
        Options op = new Options();

        op.addOption("s", "size", true, "Feldgröße");
        op.addOption("r", "rate", true, "Wahrscheinlichkeit, dass ein Feld zum Start lebendig ist. (0 - 1.0)");
        op.addOption("g", "generationRate", true, "Wieviele generationen pro Sekunde erzeugt werden");
        op.addOption("p", "pattern", true, "Vorgegebene Patterndatei");
        op.addOption("d", "disco", false, "Disco einschalten");
        op.addOption("v", "gui", false, "With GUI");
        CommandLineParser parser = new DefaultParser();


        try {
            CommandLine cmd = parser.parse(op, args);
            if (cmd.hasOption("size")) {
                fieldSize = Integer.parseInt(cmd.getOptionValue("size"));
            } else {
                hilfe(op);
            }
            if (cmd.hasOption("rate")) {
                randomRate = Double.parseDouble(cmd.getOptionValue("rate"));
            }
            if (cmd.hasOption("generationRate")) {
                generationPerSecond = (int) ((1.0 / Double.parseDouble(cmd.getOptionValue("generationRate"))) * 1000);
            }
            if (cmd.hasOption("pattern")) {
                String filePath = cmd.getOptionValue("pattern");
                pattern = "";
                for (String line : Files.readAllLines(Paths.get(filePath))) {
                    pattern += line + "\n";
                }
            }
            if (cmd.hasOption("disco")) {
                disco = true;
            }
            if (cmd.hasOption("gui")) {
                gui = true;
            }
        } catch (ParseException | NumberFormatException e) {
            hilfe(op);
        }

        Runner runner = new Runner(fieldSize, randomRate, generationPerSecond, pattern, disco, gui);
        Visual visual;
        if (gui) {
            visual = new MainFrame();
        } else {
            visual = new Printer();
        }
        runner.run(visual);

    }

    private static void hilfe(Options op) {

        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp("Usage : ", op);

        System.exit(0);

    }

}
