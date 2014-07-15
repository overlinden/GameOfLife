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
import de.wpsverlinden.gameoflife.Rule;
import de.wpsverlinden.gameoflife.Settings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SidebarListener implements ActionListener, ChangeListener{
    
    private Population population;
    private final Sidebar sidebar;
    private PopulationSimulator populationSimulator;

    SidebarListener(Sidebar sidebar) {
        this.sidebar = sidebar;   
    }

    void setPopulation(Population population) {
        this.population = population;
        populationSimulator = population.getPopulationSimulator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(sidebar.startButton)) {
            sidebar.stepButton.setEnabled(false);
            sidebar.startButton.setEnabled(false);
            sidebar.cellSizeSlider.setEnabled(false);
            sidebar.aliveRatioSlider.setEnabled(false);
            sidebar.stopButton.setEnabled(true);
            sidebar.clearButton.setEnabled(false);
            sidebar.stepButton.setEnabled(false);
            sidebar.randomButton.setEnabled(false);
            populationSimulator.startSimulation();
        } else if (e.getSource().equals(sidebar.stopButton)) {
            sidebar.stepButton.setEnabled(true);
            sidebar.startButton.setEnabled(true);
            sidebar.cellSizeSlider.setEnabled(true);
            sidebar.aliveRatioSlider.setEnabled(true);
            sidebar.randomButton.setEnabled(true);
            sidebar.clearButton.setEnabled(true);
            sidebar.stopButton.setEnabled(false);
            populationSimulator.stopSimulation();
        } else if (e.getSource().equals(sidebar.stepButton)) {
            populationSimulator.tick();
        } else if (e.getSource().equals(sidebar.randomButton)) {
            populationSimulator.seed((float) Settings.aliveRatio / 100f);
        } else if (e.getSource().equals(sidebar.clearButton)) {
            sidebar.cellSizeSlider.setEnabled(true);
            sidebar.aliveRatioSlider.setEnabled(true);
            sidebar.stopButton.setEnabled(false);
            populationSimulator.stopSimulation();
            populationSimulator.clear();
        } else if (e.getSource().equals(sidebar.borderComboBox)) {
            JComboBox cb = (JComboBox) e.getSource();
            populationSimulator.setBorder((Border) cb.getSelectedItem());
        } else if (e.getSource().equals(sidebar.ruleComboBox)) {
            JComboBox cb = (JComboBox) e.getSource();
            populationSimulator.setRule((Rule) cb.getSelectedItem());
        }
        population.repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            if (e.getSource().equals(sidebar.speedSlider)) {
                Settings.genPerMin = Math.max(source.getValue(), 30);
                source.setValue(Settings.genPerMin);
                sidebar.speedLabel.setText("Speed: " + Settings.genPerMin + " gen / minute");
            } else if (e.getSource().equals(sidebar.cellSizeSlider)) {
                Settings.cellSize = Math.max(source.getValue(), 5);
                source.setValue(Settings.cellSize);
                sidebar.cellSizeLabel.setText("Cell size: " + Settings.cellSize + "px");
                populationSimulator.clear();
            } else if (e.getSource().equals(sidebar.aliveRatioSlider)) {
                Settings.aliveRatio = source.getValue();
                sidebar.aliveRatioLabel.setText("Alive ratio: " + Settings.aliveRatio + "%");
            }
            population.repaint();
        }
    }   
}
