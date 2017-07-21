package maze;

import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Maze. Can write maze in console and find a way through the maze.
 */
public class Maze {

    private Random random = new Random();

    private int[][] array;
    private int width;
    private int height;
    private int wallMark;
    private int emptyCellMark;
    private int pathMark;
    private int playerMark;

    private Cell startCell;
    private Cell finishCell;

    public Maze(int[][] array, int width, int height, int wallMark, int emptyCellMark, int pathMark, int playerMark, Cell start, Cell finish) {
        this.array = array;

        this.width = width;
        this.height = height;

        this.wallMark = wallMark;
        this.emptyCellMark = emptyCellMark;
        this.pathMark = pathMark;
        this.playerMark = playerMark;

        this.startCell = start;
        this.finishCell = finish;
    }

    public boolean generatedPath(Cell start, Cell finish) {
        Stack<Cell> stack = new Stack<>();
        stack.push(start);
        Cell nowCell = start;
        Cell lostCell = null;
        do {
            Cell cell = nowCell;
            this.array[cell.getX()][cell.getY()] = pathMark;
            List<Cell> neighbours = Util.getNeighbour(array, cell, 1, new int[]{emptyCellMark, playerMark});
            if (neighbours.isEmpty()) {
                nowCell = stack.pop();
                lostCell = nowCell;
            } else {
                Cell neighbour = Util.getRandomNeighbour(neighbours, random);
                if (null != lostCell) {
                    stack.push(lostCell);
                }
                stack.push(neighbour);
                nowCell = neighbour;
            }
        } while (!stack.isEmpty() && !nowCell.equals(finish));

        cleanPath();

        if (stack.isEmpty()) {
            return false;
        }

        for (Cell cell : stack) {
            this.array[cell.getX()][cell.getY()] = pathMark;
        }

        return true;
    }

    // print to console all maze
    public void printMaze(String symbol, ConsoleColor wall, ConsoleColor path, ConsoleColor empty, ConsoleColor player) {
        for (int[] line : this.array) {
            StringBuilder sb = new StringBuilder();
            for (int value : line) {
                if (value == wallMark) {
                    sb.append(wall.getValue()).append(symbol).append(ConsoleColor.RESET.getValue());
                } else if (value == emptyCellMark) {
                    sb.append(empty.getValue()).append(symbol).append(ConsoleColor.RESET.getValue());
                } else if (value == pathMark) {
                    sb.append(path.getValue()).append(symbol).append(ConsoleColor.RESET.getValue());
                } else if (value == playerMark) {
                    sb.append(player.getValue()).append(symbol).append(ConsoleColor.RESET.getValue());
                }
            }
            System.out.println(sb);
        }
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getFinishCell() {
        return finishCell;
    }

    private void cleanPath() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (this.array[i][j] == pathMark) {
                    this.array[i][j] = emptyCellMark;
                }
            }
        }
    }
}
