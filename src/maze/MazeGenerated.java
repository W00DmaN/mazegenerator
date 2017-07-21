package maze;

import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Class for generated "Perfect" maze
 */
public class MazeGenerated {

    private int[][] array;
    private int width;
    private int height;
    private int wallMark;
    private int emptyCellMark;
    private int pathMark;
    private int playerMark;


    private Random random = new Random();

    public MazeGenerated(int width, int height, int wallMark, int emptyCellMark, int pathMark, int playerMark) {
        if (width % 2 == 0) {
            width++;
        }
        if (height % 2 == 0) {
            height++;
        }
        this.array = new int[width][height];

        this.width = width;
        this.height = height;

        this.wallMark = wallMark;
        this.emptyCellMark = emptyCellMark;
        this.pathMark = pathMark;
        this.playerMark = playerMark;
    }

    public MazeGenerated(int[][] array, int wallMark, int emptyCellMark, int pathMark) {
        this.array = array;
        this.wallMark = wallMark;
        this.emptyCellMark = emptyCellMark;
        this.width = array.length;
        this.height = array[0].length;
        this.pathMark = pathMark;
    }


    public Maze generateLiberint() {
        startArrayValue(wallMark);

        int visitCall = getAllCellCount();

        Cell startCell = new Cell(1, 1);
        Stack<Cell> stack = new Stack<>();
        stack.push(startCell);
        Cell nowCell = startCell;
        do {
            Cell cell = nowCell;
            List<Cell> neighbours = Util.getNeighbour(array, cell, 2, new int[]{wallMark});
            this.array[cell.getX()][cell.getY()] = emptyCellMark;
            if (neighbours.isEmpty()) {
                nowCell = stack.pop();
            } else {
                Cell neighbour = Util.getRandomNeighbour(neighbours, random);
                dropWall(cell, neighbour);
                stack.push(neighbour);
                nowCell = neighbour;
                visitCall--;
            }
        } while (visitCall > 1);
        Cell start = createEntry();
        Cell finish = createExit();
        return new Maze(this.array, width, height, wallMark, emptyCellMark, pathMark, playerMark, start, finish);
    }

    private Cell createExit() {
        int position;
        if (random.nextInt(2) > 0) {
            //generate exit in right
            position = random.nextInt(width - 1) + 1;
            if (this.array[position][height - 2] == wallMark) {
                position = movePosition(position);
            }
            this.array[position][height - 1] = emptyCellMark;
            return new Cell(position, height - 1);
        } else {
            //generate exit in down
            position = random.nextInt(height - 1) + 1;
            if (this.array[width - 2][position] == wallMark) {
                position = movePosition(position);
            }
            this.array[width - 1][position] = emptyCellMark;
            return new Cell(width - 1, position);
        }
    }

    private int movePosition(int position) {
        if (position > 1) {
            position--;
        } else {
            position++;
        }
        return position;
    }

    //create Entry to maze in top-left
    private Cell createEntry() {
        this.array[1][0] = playerMark;
        return new Cell(1, 0);
    }

    private void startArrayValue(int value) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                array[i][j] = value;
            }
        }
    }

    //drop wall between 2 cells. Distance = 2 cell.
    private void dropWall(Cell start, Cell end) {
        if (start.getX() == end.getX()) {
            if (start.getY() > end.getY()) {
                this.array[end.getX()][start.getY() - 1] = emptyCellMark;
            } else {
                this.array[end.getX()][start.getY() + 1] = emptyCellMark;
            }
        }
        if (start.getY() == end.getY()) {
            if (start.getX() > end.getX()) {
                this.array[start.getX() - 1][start.getY()] = emptyCellMark;
            } else {
                this.array[start.getX() + 1][start.getY()] = emptyCellMark;
            }
        }
    }

    //Get all mandatory cell for visit during generation maze.
    private int getAllCellCount() {
        return ((width + 1) / 2 - 1) * ((height + 1) / 2 - 1);
    }

}