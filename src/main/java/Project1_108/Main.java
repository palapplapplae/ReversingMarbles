/*  Members
6413108 Palarp Wasuwat
6413211 Kobkit Ruangsuriyakit
*/

package Project1_108;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int num = 0;
        do // loop for start new game
        {
                try {
                    System.out.printf("Enter number of white marbles (or enter 0 to exit): ");
                    num = input.nextInt();
                    if (num < 2) throw new ArithmeticException();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter new number.");
                    input.next();
                    continue;
                } catch (ArithmeticException ae) {
                    if (num != 0) System.out.println("Please enter number at least 2.");
                    continue;
                }

            Play simulation = new Play(new Table(num));
            simulation.play();

            System.out.println("\n" + "#".repeat(100));
        } while (num != 0);
//        testCase(1);
    }

    /*
    function for test 2 condition ( w0 w1 __ b0 b1 and w0 b0 __ w1 b1 )
    selector is an input selection , if 1 will be w0 w1 __ b0 b1
    if 2 will be w0 b0 __ w1 b1
    */
    static void testCase(int selector) {
        switch (selector) {
            case 1 -> {
                Table table = new Table(2);
                Play test1 = new Play(table);
                if(test1.forTestCase(table)) System.out.println("\nDone !!!");
                else System.out.println("\nNo solution !!!");
            }
            case 2 -> {
                ArrayList<String > temp = new ArrayList<>();
                temp.add("w0"); temp.add("b0"); temp.add("__"); temp.add("w1"); temp.add("b1"); // add elements
                Table table = new Table(temp);
                Play test2 = new Play(table);
                if(test2.forTestCase(table)) System.out.println("\nDone !!!");
                else System.out.println("\nNo solution !!!");
            }
        }
    }

}