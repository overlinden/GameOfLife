/*
 * GameOfLife - Conway's Game of Life
 * Copyright (C) 2014 Oliver Verlinden (http://wps-verlinden.de)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.wpsverlinden.gameoflife.gui;

import de.wpsverlinden.gameoflife.Border;
import de.wpsverlinden.gameoflife.Cell;
import de.wpsverlinden.gameoflife.Rule;
import de.wpsverlinden.gameoflife.Settings;
import java.util.Date;

public class PopulationSimulator extends Thread {

    private Population population;
    private Rule rule;
    private Cell[][] cells;
    private int cellsPerRow, cellsPerCol;
    private Border border;
    private boolean running = false;

    public PopulationSimulator() {
        this.border = Border.Solid;
        this.rule = Rule.R_23_3;
        setDaemon(true);
    }

    public void setPopulation(Population population) {
        this.population = population;
    }
    
    public void setBorder(Border border) {
        this.border = border;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
    
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void startSimulation() {
        running = true;
    }

    public void stopSimulation() {
        running = false;
    }

    @Override
    public void run() {
        long startTime;
        long delta = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (running) {
                startTime = new Date().getTime();
                tick();
                delta = new Date().getTime() - startTime;
            }
            population.repaint();
            try {
                Thread.sleep((int) (1000 / (float) Settings.genPerMin * 60) - delta);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    public void tick() {
        calculateNextGeneration();
        activateNextGeneration();
    }

    private void calculateNextGeneration() {
        for (Cell[] c : cells) {
            for (Cell c2 : c) {
                if (c2.getCurState() == Cell.State.ALIVE) {
                    processLivingRulesFor(c2);
                } else {
                    processDeadRules(c2);
                }
            }
        }
    }

    private void activateNextGeneration() {
        for (Cell[] c : cells) {
            for (Cell c2 : c) {
                c2.shiftStatus();
            }
        }
    }

    private void processLivingRulesFor(Cell activeCell) {
        if (rule.matchSurvive(numberOfLivingNeighboursOf(activeCell))) {
            activeCell.setNextState(Cell.State.ALIVE);
        } else {
            activeCell.setNextState(Cell.State.DEAD);
        }
    }

    private void processDeadRules(Cell activeCell) {
        if (rule.matchReborn(numberOfLivingNeighboursOf(activeCell))) {
            activeCell.setNextState(Cell.State.ALIVE);
        }
    }

    private int numberOfLivingNeighboursOf(Cell activeCell) {
        int ret = 0;
        int cellX = activeCell.getX();
        int cellY = activeCell.getY();

        if (border == Border.Solid) {
            try {
                ret += (cells[cellX][cellY + 1].getCurState() == Cell.State.ALIVE ? 1 : 0);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                ret += (cells[cellX][cellY - 1].getCurState() == Cell.State.ALIVE ? 1 : 0);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                ret += (cells[cellX + 1][cellY].getCurState() == Cell.State.ALIVE ? 1 : 0);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                ret += (cells[cellX - 1][cellY].getCurState() == Cell.State.ALIVE ? 1 : 0);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                ret += (cells[cellX + 1][cellY + 1].getCurState() == Cell.State.ALIVE ? 1 : 0);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                ret += (cells[cellX + 1][cellY - 1].getCurState() == Cell.State.ALIVE ? 1 : 0);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                ret += (cells[cellX - 1][cellY + 1].getCurState() == Cell.State.ALIVE ? 1 : 0);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                ret += (cells[cellX - 1][cellY - 1].getCurState() == Cell.State.ALIVE ? 1 : 0);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        } else if (border == Border.Torus) {
            ret += (cells[cellX % cellsPerRow][(cellY + 1) % cellsPerCol].getCurState() == Cell.State.ALIVE ? 1 : 0);
            ret += (cells[cellX % cellsPerRow][(cellY + cellsPerCol - 1) % cellsPerCol].getCurState() == Cell.State.ALIVE ? 1 : 0);
            ret += (cells[(cellX + 1) % cellsPerRow][cellY % cellsPerCol].getCurState() == Cell.State.ALIVE ? 1 : 0);
            ret += (cells[(cellX + cellsPerRow - 1) % cellsPerRow][cellY % cellsPerCol].getCurState() == Cell.State.ALIVE ? 1 : 0);
            ret += (cells[(cellX + 1) % cellsPerRow][(cellY + 1) % cellsPerCol].getCurState() == Cell.State.ALIVE ? 1 : 0);
            ret += (cells[(cellX + 1) % cellsPerRow][(cellY + cellsPerCol - 1) % cellsPerCol].getCurState() == Cell.State.ALIVE ? 1 : 0);
            ret += (cells[(cellX + cellsPerRow - 1) % cellsPerRow][(cellY + 1) % cellsPerCol].getCurState() == Cell.State.ALIVE ? 1 : 0);
            ret += (cells[(cellX + cellsPerRow - 1) % cellsPerRow][(cellY + cellsPerCol - 1) % cellsPerCol].getCurState() == Cell.State.ALIVE ? 1 : 0);
        }
        return ret;
    }

    public void clear() {
        cellsPerRow = population.getWidth() / Settings.cellSize + 1;
        cellsPerCol = population.getHeight() / Settings.cellSize + 1;
        cells = new Cell[cellsPerRow][cellsPerCol];
        for (int i = 0; i < cellsPerRow; i++) {
            for (int j = 0; j < cellsPerCol; j++) {
                cells[i][j] = new Cell(i, j, Cell.State.DEAD);
            }
        }
    }

    public void seed(float aliveRatio) {
        for (int i = 0; i < cellsPerRow; i++) {
            for (int j = 0; j < cellsPerCol; j++) {
                if (Math.random() < aliveRatio) {
                    cells[i][j].setCurState(Cell.State.ALIVE);
                } else {
                    cells[i][j].setCurState(Cell.State.DEAD);
                }
            }
        }
    }
}
