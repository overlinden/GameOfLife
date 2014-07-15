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

import de.wpsverlinden.gameoflife.Cell;
import de.wpsverlinden.gameoflife.Settings;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopulationListener extends MouseAdapter {

    private final Population population;
    private PopulationSimulator pSimulator;

    PopulationListener(Population population) {
        this.population = population;
    }
    
    public void setPopulationSimulator(PopulationSimulator pSimulator) {
        this.pSimulator = pSimulator;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        Cell cell = pSimulator.getCell(point.x / Settings.cellSize, point.y / Settings.cellSize);
        cell.setPrevState(Cell.State.DEAD);
        cell.setNextState(Cell.State.DEAD);
        if (cell.getCurState() == Cell.State.ALIVE) {
            cell.setCurState(Cell.State.DEAD);
        } else {
            cell.setCurState(Cell.State.ALIVE);
        }
        population.repaint();
    }
}
