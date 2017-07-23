package maze;

import java.util.List;
import java.util.Stack;

/**
 * Maze. Can write maze in console and find a way through the maze.
 */
public class Maze {

    private int[][] array;
    private int width;
    private int height;
    private int wallMark;
    private int emptyCellMark;
    private int pathMark;
    private int playerMark;

    private Cell startCell;
    private Cell finishCell;

    private Maze(Builder builder) {
        this.array = builder.array;

        this.width = array.length;
        this.height = array[0].length;

        this.wallMark = builder.wallMark;
        this.emptyCellMark = builder.emptyCellMark;
        this.pathMark = builder.pathMark;
        this.playerMark = builder.playerMark;
        this.startCell = builder.startCell;
        this.finishCell = builder.finishCell;
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
                Cell neighbour = Util.getRandomNeighbour(neighbours);
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

    public static class Builder {
        private int[][] array;
        private int wallMark = -1;
        private int emptyCellMark = 0;
        private int pathMark = -100;
        private int playerMark = 2;
        private Cell startCell;
        private Cell finishCell;

        public Builder(int[][] array, Cell startCell, Cell finishCell) {
            this.array = array;
            this.startCell = startCell;
            this.finishCell = finishCell;
        }

        public Builder setArray(int[][] value) {
            this.array = value;
            return this;
        }

        public Builder setWallMark(int wallMark) {
            this.wallMark = wallMark;
            return this;
        }

        public Builder setEmptyCellMark(int emptyCellMark) {
            this.emptyCellMark = emptyCellMark;
            return this;
        }

        public Builder setPathMark(int pathMark) {
            this.pathMark = pathMark;
            return this;
        }

        public Builder setPlayerMark(int playerMark) {
            this.playerMark = playerMark;
            return this;
        }

        public Builder setStartCell(Cell startCell) {
            this.startCell = startCell;
            return this;
        }

        public Builder setFinishCell(Cell finishCell) {
            this.finishCell = finishCell;
            return this;
        }

        public Maze build() {
            return new Maze(this);
        }
    }

}
