package Project2_108;
/*
6413108 Palarp Wasuwat
6413211 Kobkit Ruangsuriyakit
*/

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;

import java.util.ArrayDeque;

public class SolutionPath {
    private int n;
    private LightBoard initBoard, validBoard;
    private Graph<LightBoard, DefaultEdge> boardGraph = new SimpleGraph<>(DefaultEdge.class);
    private ArrayDeque<LightBoard> processingQueue = new ArrayDeque<>();
    // Text coloring
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BG_RED = "\u001B[41m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLACK = "\u001B[30m";

    public SolutionPath(LightBoard initBoard, int n) {
        String[] buffer = new String[n*n];
        String[] buffer2 = new String[n*n];
        this.initBoard = initBoard;
        this.n = n;

        // initialize endBoard
        for (int i = 0; i < n*n; i++) {
            buffer[i] = "-1";
            buffer2[i] = "0";
        }
        validBoard = new LightBoard(buffer2, n);

        boardGraph.addVertex(this.initBoard);
    }

    public LightBoard solution() {
        processingQueue.add(initBoard);

        while (!processingQueue.isEmpty() && !initBoard.equals(validBoard)) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // deep copy initBoard
                    LightBoard tempLightBoard = new LightBoard(processingQueue.peekFirst().getBoard(), n, -1, -1);
                    tempLightBoard.setBrokenPosition(initBoard.getBrokenPosition()[0], initBoard.getBrokenPosition()[1]);
                    tempLightBoard.toggleLight(i, j);           // toggle light in each row and column
                    if (!boardGraph.containsVertex(tempLightBoard)) {
                        processingQueue.add(tempLightBoard);    // add initBoard to queue
                        boardGraph.addVertex(tempLightBoard);
                        boardGraph.addEdge(processingQueue.peekFirst(), tempLightBoard);
                    }
                    if (tempLightBoard.equals(validBoard)) return tempLightBoard;
                }
            }
            processingQueue.pollFirst();
        }
        return null;
    }

    public LightBoard solutionT() {
        int count = 0;
        processingQueue.add(initBoard);

        while (!processingQueue.isEmpty() && !initBoard.equals(validBoard)) {
            System.out.println("=== Step " + ++count + " ===");
            System.out.print("Processing ");
            processingQueue.peekFirst().printLightBit();
            System.out.println();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // deep copy initBoard
                    LightBoard tempLightBoard = new LightBoard(processingQueue.peekFirst().getBoard(), n, -1, -1);
                    tempLightBoard.setBrokenPosition(initBoard.getBrokenPosition()[0], initBoard.getBrokenPosition()[1]);
                    tempLightBoard.toggleLight(i, j);           // toggle light in each row and column
                    tempLightBoard.printLightBit();
                    if (!boardGraph.containsVertex(tempLightBoard)) {
                        processingQueue.add(tempLightBoard);    // add initBoard to queue
                        boardGraph.addVertex(tempLightBoard);
                        boardGraph.addEdge(processingQueue.peekFirst(), tempLightBoard);
                        System.out.print("\t->\tNew");
                    }
                    if (tempLightBoard.equals(validBoard)){
                        System.out.println("\n=== Current Queue ===");
                        while (!processingQueue.isEmpty()) {
                            processingQueue.pollFirst().printLightBit();
                            System.out.println();
                        }
                        System.out.println();
                        System.out.println("=".repeat(100));
                        return tempLightBoard;
                    }
                    System.out.println();
                }
            }
            System.out.print("Remove ");
            processingQueue.peekFirst().printLightBit();
            System.out.println();
            processingQueue.pollFirst();
        }
        return null;
    }

    public void printPath(LightBoard endBoard) {
        ShortestPathAlgorithm<LightBoard, DefaultEdge> shpath = new DijkstraShortestPath<>(boardGraph);
        GraphPath<LightBoard, DefaultEdge> gpath = shpath.getPath(initBoard, endBoard);

        System.out.printf("\n%d moves to turn off all light.\n\n", gpath.getVertexList().size()-1);
        for (int i = 0; i < gpath.getVertexList().size(); i++) {
            LightBoard lb = gpath.getVertexList().get(i);
            if (lb.equals(initBoard)) System.out.println(ANSI_BG_RED + ANSI_BLACK + " >>>>>       Initial       <<<<< " + ANSI_RESET);
            else System.out.println(ANSI_YELLOW + ">>> Move " + i + " : Toggle row " + lb.movingRow + " column " + lb.movingCol + ANSI_RESET);
            lb.printBoard();
        }
    }
}
