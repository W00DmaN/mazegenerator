package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Util class for work with maze.
 */
public final class Util {

    private static Random random = new Random();

    //search all cell around with goodMarks values
    public static List<Cell> getNeighbour(int[][] array, Cell cell, int step, int[] goodMarks) {
        List<Cell> neighbourCell = new ArrayList<>();
        //up
        neighbourCell.add(new Cell(cell.getX(), cell.getY() - step));
        //down
        neighbourCell.add(new Cell(cell.getX(), cell.getY() + step));
        //left
        neighbourCell.add(new Cell(cell.getX() - step, cell.getY()));
        //right
        neighbourCell.add(new Cell(cell.getX() + step, cell.getY()));

        List<Cell> result = new ArrayList<>();

        for (Cell neighbour : neighbourCell) {
            if (inArray(neighbour.getX(), neighbour.getY(), array.length, array[0].length)) {
                if (isNeighbour(array[neighbour.getX()][neighbour.getY()], goodMarks)) {
                    result.add(neighbour);
                }
            }
        }
        return result;
    }

    //get random Cell from list cell
    public static Cell getRandomNeighbour(List<Cell> neighbours) {
        if (neighbours.size() == 1) {
            return neighbours.get(0);
        } else {
            return neighbours.get(random.nextInt(neighbours.size()));
        }
    }

    private static boolean isNeighbour(int value, int[] goodMarks) {
        for (int mark : goodMarks) {
            if (mark == value) {
                return true;
            }
        }
        return false;
    }

    private static boolean inArray(int x, int y, int width, int height) {
        return x < width && y < height && x > 0 && y > 0;
    }
}
