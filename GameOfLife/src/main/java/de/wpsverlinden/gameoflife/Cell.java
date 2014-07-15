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

public class Cell {

    public enum State {
        DEAD, ALIVE
    }
    private State prev, cur, next;
    private int x, y;

    public Cell(int x, int y, State state) {
        this.cur = state;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public State getCurState() {
        return cur;
    }

    public void setCurState(State state) {
        this.cur = state;
    }

    public State getNextState() {
        return next;
    }

    public void setNextState(State willBeAlive) {
        this.next = willBeAlive;
    }

    public State getPrevState() {
        return prev;
    }

    public void setPrevState(State prevState) {
        this.prev = prevState;
    }

    public void shiftStatus() {
        prev = cur;
        cur = next;
    }
}
