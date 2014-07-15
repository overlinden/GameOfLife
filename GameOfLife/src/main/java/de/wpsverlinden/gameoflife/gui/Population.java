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
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Population extends JComponent {

    private int cellsPerRow, cellsPerCol;
    private Sidebar sidebar;
    private PopulationSimulator pSimulator;
    private final PopulationListener populationListener;

    public Population() {
        populationListener = new PopulationListener(this);
        addMouseListener(populationListener);
    }
    
    public void setPSimulator(PopulationSimulator pSimulator) {
        this.pSimulator = pSimulator;
        populationListener.setPopulationSimulator(pSimulator);
    }

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }
    
    public PopulationSimulator getPopulationSimulator() {
        return pSimulator;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        cellsPerRow = getWidth() / Settings.cellSize + 1;
        cellsPerCol = getHeight() / Settings.cellSize + 1;
        
        for (int i = 0; i < cellsPerRow; i++) {
            for (int j = 0; j < cellsPerCol; j++) {
                Cell c = pSimulator.getCell(i, j);
                if (c.getCurState() == Cell.State.ALIVE && c.getPrevState() == Cell.State.DEAD) {
                    g.setColor(new Color(.2f, .7f, .2f, 1));
                } else if (c.getCurState() == Cell.State.ALIVE) {
                    g.setColor(new Color(.2f, .6f, .2f, 1));
                } else if (c.getCurState() == Cell.State.DEAD && c.getPrevState() == Cell.State.ALIVE) {
                    g.setColor(new Color(.05f, .15f, .05f, 1));
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(i * Settings.cellSize, j * Settings.cellSize, Settings.cellSize, Settings.cellSize);
            }
        }

        g.setColor(Color.GRAY);
        for (int i = 0; i <= cellsPerRow; i++) {
            g.drawLine(Settings.cellSize * i, 0, Settings.cellSize * i, getHeight());
        }

        for (int i = 0; i <= cellsPerCol; i++) {
            g.drawLine(0, Settings.cellSize * i, getWidth(), Settings.cellSize * i);
        }
    }
}
