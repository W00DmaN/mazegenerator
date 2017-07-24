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

    public MazeGenerated(Builder builder) {
        this.array = new int[builder.width][builder.height];

        this.width = builder.width;
        this.height = builder.height;

        this.wallMark = builder.wallMark;
        this.emptyCellMark = builder.emptyCellMark;
        this.pathMark = builder.pathMark;
        this.playerMark = builder.playerMark;
    }

    public MazeGenerated(int[][] array, int wallMark, int emptyCellMark, int pathMark) {
        this.array = array;
        this.wallMark = wallMark;
        this.emptyCellMark = emptyCellMark;
        this.width = array.length;
        this.height = array[0].length;
        this.pathMark = pathMark;
    }

    public Maze generate() {
        startArrayValue(wallMark);

        int visitCall = getAllCellCount();

        Cell startCell = new Cell(1, 1);
        Stack<Cell> stack = new Stack<>();
        stack.push(startCell);
        Cell nowCell = startCell;
        do {
            List<Cell> neighbours = Util.getNeighbour(array, nowCell, 2, new int[]{wallMark});
            this.array[nowCell.getX()][nowCell.getY()] = emptyCellMark;
            if (neighbours.isEmpty()) {
                nowCell = stack.pop();
            } else {
                Cell neighbour = Util.getRandomNeighbour(neighbours);
                dropWall(nowCell, neighbour);
                stack.push(neighbour);
                nowCell = neighbour;
                visitCall--;
            }
        } while (visitCall > 0 && !stack.isEmpty());
        Cell start = createEntry();
        Cell finish = createExit();
        return new Maze.Builder(array, start, finish).setWallMark(wallMark)
                .setEmptyCellMark(emptyCellMark)
                .setPathMark(pathMark)
                .setPlayerMark(playerMark).build();
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

    public static class Builder {

        private int wallMark = -1;
        private int emptyCellMark = 0;
        private int pathMark = -100;
        private int playerMark = 2;

        private int width;
        private int height;

        public Builder(int width, int height) {
            if (width % 2 == 0) {
                width++;
            }
            if (height % 2 == 0) {
                height++;
            }
            this.width = width;
            this.height = height;
        }

        public Builder setWallMark(int value) {
            this.wallMark = value;
            return this;
        }

        public Builder setEmptyCellMark(int value) {
            this.emptyCellMark = value;
            return this;
        }

        public Builder setPathMark(int value) {
            this.pathMark = value;
            return this;
        }

        public Builder setPlayerMark(int value) {
            this.playerMark = value;
            return this;
        }

        public MazeGenerated build() {
            return new MazeGenerated(this);
        }

    }

}
