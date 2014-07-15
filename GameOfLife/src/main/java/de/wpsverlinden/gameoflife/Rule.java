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

import java.util.Arrays;
import java.util.List;

public class Rule {

    
    public static Rule R_23_3 = new Rule(Arrays.asList(new Integer[]{2, 3}), Arrays.asList(new Integer[]{3}));
    public static Rule R_1357_1357 = new Rule(Arrays.asList(new Integer[]{1, 3, 5, 7}), Arrays.asList(new Integer[]{1, 3, 5, 7}));
    public static Rule R_3_3 = new Rule(Arrays.asList(new Integer[]{3}), Arrays.asList(new Integer[]{3}));
    public static Rule R_13_3 = new Rule(Arrays.asList(new Integer[]{1, 3}), Arrays.asList(new Integer[]{3}));
    public static Rule R_34_3 = new Rule(Arrays.asList(new Integer[]{3, 4}), Arrays.asList(new Integer[]{3}));
    public static Rule R_35_3 = new Rule(Arrays.asList(new Integer[]{3, 5}), Arrays.asList(new Integer[]{3}));
    public static Rule R_2_3 = new Rule(Arrays.asList(new Integer[]{2}), Arrays.asList(new Integer[]{3}));
    public static Rule R_24_3 = new Rule(Arrays.asList(new Integer[]{2, 4}), Arrays.asList(new Integer[]{3}));
    public static Rule R_245_3 = new Rule(Arrays.asList(new Integer[]{2, 4, 5}), Arrays.asList(new Integer[]{3}));
    public static Rule R_125_36 = new Rule(Arrays.asList(new Integer[]{1, 2, 5}), Arrays.asList(new Integer[]{3, 6}));  
    public static Rule R_1234_3 = new Rule(Arrays.asList(new Integer[]{1, 2, 3, 4}), Arrays.asList(new Integer[]{3}));
    
    public static Rule[] RuleSet = new Rule[]{R_23_3, R_1357_1357, R_3_3, R_13_3, R_34_3, R_35_3, R_2_3, R_24_3, R_245_3, R_125_36, R_1234_3};
    
    private List<Integer> neigboursToSurvive, neighboursToReborn;

    private Rule(List<Integer> neigboursToLive, List<Integer> neighboursToDie) {
        this.neigboursToSurvive = neigboursToLive;
        this.neighboursToReborn = neighboursToDie;
    }

    public boolean matchSurvive(int count) {
        return neigboursToSurvive.contains(count);
    }
    
    public boolean matchReborn(int count) {
        return neighboursToReborn.contains(count);
    }
    
    @Override
    public String toString() {
        String s = "";
        for (Integer i : neigboursToSurvive) {
            s += i.toString();
        }
        s += "/";
        for (Integer i : neighboursToReborn) {
            s += i.toString();
        }
        return s + " world";
    }
}
