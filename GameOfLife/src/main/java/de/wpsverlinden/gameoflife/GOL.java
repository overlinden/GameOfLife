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

package de.wpsverlinden.gameoflife;

import de.wpsverlinden.gameoflife.gui.Population;
import de.wpsverlinden.gameoflife.gui.PopulationSimulator;
import de.wpsverlinden.gameoflife.gui.Sidebar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class GOL extends JComponent {

    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 600;
    private Population population;
    private Sidebar sidebar;
    private PopulationSimulator pSimulator;

    public static void main(String[] args) {
        GOL app = new GOL();
        app.wireComponents();
        app.initGUI();
    }

    private void wireComponents() {
        pSimulator = new PopulationSimulator();
        population = new Population();
        sidebar = new Sidebar();
        population.setSidebar(sidebar);
        population.setPSimulator(pSimulator);
        sidebar.setPopulation(population);
        pSimulator.setPopulation(population);
        pSimulator.start();
    }

    private void initGUI() {
        Dimension d = new Dimension(APP_WIDTH, APP_HEIGHT);
        JFrame f = new JFrame("Conway's Game of Life");
        f.setLayout(new BorderLayout());
        f.add(sidebar, BorderLayout.WEST);
        f.add(population, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                pSimulator.clear();
            }
        });
        f.setMinimumSize(d);
        f.pack();
        f.setVisible(true);

        pSimulator.clear();
    }
}
