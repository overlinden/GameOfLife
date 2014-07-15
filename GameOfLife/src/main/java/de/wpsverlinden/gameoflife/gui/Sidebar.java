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
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Sidebar extends JComponent {

    private final SidebarListener sbListener;
    JButton startButton, stopButton, stepButton, clearButton, randomButton;
    JSlider speedSlider, cellSizeSlider, aliveRatioSlider;
    JLabel borderLabel, ruleLabel, speedLabel, cellSizeLabel, aliveRatioLabel;
    JComboBox borderComboBox, ruleComboBox;

    public void setPopulation(Population population) {
        sbListener.setPopulation(population);
    }

    public Sidebar() {
        sbListener = new SidebarListener(this);
        borderComboBox = new JComboBox(Border.values());
        ruleComboBox = new JComboBox(Rule.RuleSet);
        clearButton = new JButton("Clear field");
        randomButton = new JButton("Random seed");
        startButton = new JButton("Start simulation");
        stopButton = new JButton("Stop simulation");
        stepButton = new JButton("Single step");
        speedSlider = new JSlider(0, 900, Settings.genPerMin);
        cellSizeSlider = new JSlider(0, 100, Settings.cellSize);
        aliveRatioSlider = new JSlider(0, 100, Settings.aliveRatio);
        borderLabel = new JLabel("Border behavior");
        ruleLabel = new JLabel("Rule set");
        speedLabel = new JLabel("Speed: " + Settings.genPerMin + " gen / minute");
        cellSizeLabel = new JLabel("Cell size: " + Settings.cellSize + "px");
        aliveRatioLabel = new JLabel("Alive ratio: " + Settings.aliveRatio + "%");

        borderComboBox.addActionListener(sbListener);
        ruleComboBox.addActionListener(sbListener);

        startButton.addActionListener(sbListener);
        stopButton.addActionListener(sbListener);
        stepButton.addActionListener(sbListener);
        clearButton.addActionListener(sbListener);
        randomButton.addActionListener(sbListener);

        cellSizeSlider.addChangeListener(sbListener);
        speedSlider.addChangeListener(sbListener);
        aliveRatioSlider.addChangeListener(sbListener);

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        borderLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(borderLabel);
        borderComboBox.setMaximumSize(new Dimension(Short.MAX_VALUE, borderComboBox.getPreferredSize().height));
        add(borderComboBox);

        add(Box.createRigidArea(new Dimension(0, 10)));
        
        ruleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(ruleLabel);
        ruleComboBox.setMaximumSize(new Dimension(Short.MAX_VALUE, ruleComboBox.getPreferredSize().height));
        add(ruleComboBox);

        add(Box.createRigidArea(new Dimension(0, 20)));

        cellSizeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(cellSizeLabel);
        cellSizeSlider.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        cellSizeSlider.setMinorTickSpacing(5);
        cellSizeSlider.setMajorTickSpacing(20);
        cellSizeSlider.setPaintTicks(true);
        cellSizeSlider.setPaintLabels(true);
        cellSizeSlider.setSnapToTicks(true);
        cellSizeSlider.setMinimumSize(new Dimension(0, 100));
        add(cellSizeSlider);
        
        add(Box.createRigidArea(new Dimension(0, 10)));

        aliveRatioLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(aliveRatioLabel);
        aliveRatioSlider.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        aliveRatioSlider.setMinorTickSpacing(5);
        aliveRatioSlider.setMajorTickSpacing(20);
        aliveRatioSlider.setPaintTicks(true);
        aliveRatioSlider.setPaintLabels(true);
        aliveRatioSlider.setSnapToTicks(true);
        add(aliveRatioSlider);
        
        add(Box.createRigidArea(new Dimension(0, 10)));

        speedLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(speedLabel);
        speedSlider.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        speedSlider.setMinorTickSpacing(30);
        speedSlider.setMajorTickSpacing(180);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setSnapToTicks(true);
        add(speedSlider);

        add(Box.createVerticalGlue());

        GridLayout buttonGrid = new GridLayout(0, 1);
        buttonGrid.setVgap(5);
        JPanel buttonPanel = new JPanel(buttonGrid);
        buttonPanel.add(clearButton);
        buttonPanel.add(randomButton);
        buttonPanel.add(startButton);
        stopButton.setEnabled(false);
        buttonPanel.add(stopButton);
        buttonPanel.add(stepButton);
        buttonPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, buttonPanel.getPreferredSize().height));
        add(buttonPanel);
    }
}
