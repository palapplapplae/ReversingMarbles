package Project2_108;
/*
6413108 Palarp Wasuwat
6413211 Kobkit Ruangsuriyakit
*/

import java.util.*;

public class Main {
    public static void main (String[] args) {
        Scanner scan = new Scanner(System.in);
        String input;
        int n = -1, row = -1, col = -1;
        SolutionPath solutionPath;
        LightBoard lightBoard;

        do {    // main game loop
            try {
                System.out.println("Enter number 'n' for create board size n x n (or enter 0 to exit): ");
                n = scan.nextInt();
                if (n < 2) throw new ArithmeticException();
            } catch (InputMismatchException e) {
                System.out.println("Please enter NUMBER.");
                scan.nextLine();
                continue;
            } catch (ArithmeticException ae) {
                if (n == 0) break;
                System.out.println("Please enter number at least 2!");
                continue;
            }
            scan.nextLine(); // clear old data in System.in

            String[] buf = {};
            boolean isValidInput = false;
            do {    // input initial board loop
                System.out.printf("Enter initial states (%d bits), left to right, line by line:\n", n*n);
                try {
                    input = scan.nextLine();
                    buf = input.split("");
                    for (int i = 0; i < input.length(); i++) buf[i] = buf[i].trim();
                    if (buf.length != n*n) throw new ArithmeticException();
                    for (int i = 0; i < input.length(); i++) {
                        if (!buf[i].equals("0") && !buf[i].equals("1")) throw new ArithmeticException();
                    }
                    isValidInput = true;
                } catch (ArithmeticException e) { System.out.println("Please enter new initial states."); }
            } while (!isValidInput);
            lightBoard = new LightBoard(buf, n);
            lightBoard.printBoard();

            do {    // input position of broken light
                System.out.println("Set broken light (Y/N) ?");
                input = scan.nextLine().toUpperCase();
            } while (!input.equals("Y") && !input.equals("N"));
            while (input.equals("Y")) {
                try {
                    System.out.printf("Enter row of broken light (0-%d) = ", n - 1);
                    row = scan.nextInt();
                    System.out.printf("Enter column of broken light (0-%d) = ", n - 1);
                    col = scan.nextInt();
                    lightBoard.setBrokenPosition(row, col);
                    break;
                } catch (InputMismatchException | ArithmeticException e) { System.out.printf("Please enter new input between 0-%d", n - 1); }
            }

            solutionPath = new SolutionPath(lightBoard, n);
            LightBoard endPoint = solutionPath.solution();
            if (endPoint != null) solutionPath.printPath(endPoint);
            else System.out.println("\nNo solution !!\n");
            System.out.println("=".repeat(100) + "\n");
        } while (n != 0);
    }
}
