import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * Driver class for BSTRectangle
 * 
 * @author Brady Siegel <bmsiegel@vt.edu>
 * @version 2019-02-19
 */
public class Rectangle1 {

    /** Constants for each command */
    private static final String INSERT = "insert";
    private static final String RSEARCH = "regionsearch";
    private static final String INTERSECT = "intersections";
    private static final String DUMP = "dump";
    private static final String SEARCH = "search";
    private static final String REMOVE = "remove";


    /**
     * Main function: Takes a file as a command line argument, and carries out
     * the commands by calling functions on a BSTRectangle object
     * 
     * @param args
     *            Array of length 1 that holds fileName
     */
    public static void main(String[] args) {
        BSTRectangle rectangles = new BSTRectangle();
        if (args.length == 1) {
            String file = args[0];
            try {
                Scanner sc = new Scanner(new File(file));
                while (sc.hasNext()) {
                    String command = sc.next();
                    if (command.equals(INSERT)) {
                        String name = sc.next();
                        int x = sc.nextInt();
                        int y = sc.nextInt();
                        int w = sc.nextInt();
                        int h = sc.nextInt();
                        int[] dimensions = { x, y, w, h };
                        Boolean add = rectangles.insertRectangle(name,
                            dimensions);
                        if (add) {
                            System.out.println("Rectangle accepted: (" + name
                                + ", " + dimensions[0] + ", " + dimensions[1]
                                + ", " + dimensions[2] + ", " + dimensions[3]
                                + ")");
                        }
                        else {
                            System.out.println("Rectangle rejected: (" + name
                                + ", " + dimensions[0] + ", " + dimensions[1]
                                + ", " + dimensions[2] + ", " + dimensions[3]
                                + ")");
                        }
                    }
                    else if (command.equals(RSEARCH)) {
                        int x = sc.nextInt();
                        int y = sc.nextInt();
                        int w = sc.nextInt();
                        int h = sc.nextInt();
                        int[] dimensions = { x, y, w, h };
                        rectangles.regionSearch(dimensions);
                    }
                    else if (command.equals(SEARCH)) {
                        String name = sc.next();
                        boolean search = rectangles.search(name);
                        if (!search) {
                            System.out.println("Rectangle not found: " + name);
                        }
                    }
                    else if (command.equals(DUMP)) {
                        rectangles.dump();
                    }
                    else if (command.equals(REMOVE)) {
                        String check = sc.next();
                        try {
                            int x = Integer.parseInt(check);
                            int y = sc.nextInt();
                            int w = sc.nextInt();
                            int h = sc.nextInt();
                            int[] dimensions = { x, y, w, h };
                            if (!rectangles.removeRectangleDim(dimensions)) {
                                System.out.println("Rectangle rejected (" + x
                                    + ", " + y + ", " + w + ", " + h + ")");
                            }
                        }
                        catch (NumberFormatException e) {
                            String name = check;
                            Boolean remove = rectangles.removeRectangleName(
                                name);
                            if (!remove) {
                                System.out.println("Rectangle rejected "
                                    + name);
                            }
                        }
                    }
                    else if (command.equals(INTERSECT)) {
                        rectangles.intersections();
                    }
                }
                sc.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("File Not Found!");
            }
        }
        else {
            System.out.println("Usage: <Program Name> <filename>");
        }
    }

}
