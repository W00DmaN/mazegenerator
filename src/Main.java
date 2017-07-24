import java.util.Scanner;

import maze.ConsoleColor;
import maze.Maze;
import maze.MazeGenerated;

/**
 * Class for run demo.
 * Print maze with set width and height and maze with path from start to finish.
 */
public class Main {

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Set width maze: ");
        int widthMaze = keyboard.nextInt();
        System.out.println("Set height maze: ");
        int heightMaze = keyboard.nextInt();

        MazeGenerated mazeGenerated = new MazeGenerated.Builder(widthMaze, heightMaze).build();
        Maze maze = mazeGenerated.generate();

        maze.printColorMaze("█", ConsoleColor.BLACK, ConsoleColor.GREEN, ConsoleColor.WHITE, ConsoleColor.RED);
        maze.printSymbolMaze("##", "::", "  ", "PP");

        System.out.println("***********************************************************************");

        boolean isPath = maze.generatedPath(maze.getStartCell(), maze.getFinishCell());
        if (isPath) {
            maze.printColorMaze("█", ConsoleColor.BLACK, ConsoleColor.GREEN, ConsoleColor.WHITE, ConsoleColor.RED);
            maze.printSymbolMaze("##", "::", "  ", "PP");
        } else {
            System.out.println("Not a path!");
        }
    }
}
