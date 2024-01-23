/*  Members
6413108 Palarp Wasuwat
6413211 Kobkit Ruangsuriyakit
*/

package Project1_108;

import java.util.*;

public class Play {
    private int step = 1;
    private Table table;
    private ArrayDeque<Table> solution = new ArrayDeque<>();

    public Play(Table table) {
        this.table = table;
    }

    // Manual mode
    public void play() {
        boolean isAutoMode = false;
        Scanner sc = new Scanner(System.in);
        while(!table.isCorrect() && table.isEnd()){
            System.out.printf("\nEnter marble ID or 'a' to switch to auto mode: ");
            String input = sc.next().toLowerCase();
            if ((!table.getTable().contains(input) || input.equals("__")) && !input.equals("a")) {
                System.out.println("Invalid Input!!!");
                continue;
            }

            if(input.equals("a")) {
                isAutoMode = true;
                solution(table);
                String output = (solution.isEmpty()) ? "No solution !!!" : "Done !!!";
                System.out.println("\n==================== AUTO MODE ====================");
                while(!solution.isEmpty()) solution.pollLast().printTable(step++);
                System.out.println("\n\t" + output);
                break;
            }
            else {
                String info = "\t>>> " + input;
                switch (table.isMove(input)) {
                    case 0 -> info += " Can't move ";
                    case 1 -> info += " Move ";
                    case 2 -> info += " Jump ";
                }
                switch (table.stringToMarble(input)[0]) {
                    case 0 -> info += "Left ";
                    case 1 -> info += "Right ";
                }
                table.moveMable(table.stringToMarble(input), table.isMove(input));
                table.printTable(step++);
                System.out.println(info);
            }
        }
        if (!isAutoMode)
            System.out.printf("=".repeat(20) + ((table.isCorrect()) ? " YOU WIN >.< " : " YOU LOSE ;^; ") + "=".repeat(20) + "\n");
    }

    // Auto mode (Backtracking)
    public boolean solution(Table table) {
        solution.push(table);
        if(this.table.getEnd().equals(table.getTable())) return true;
        Table temp = new Table();
        table.getTable().forEach(x -> temp.getTable().add(x));
        for(int i=temp.findRange()[0];i<=temp.findRange()[1];i++) {
            if((temp.isMove(temp.getTable().get(i)) != 0) && !temp.getTable().get(i).equals("__")) {
                temp.moveMable(temp.stringToMarble(temp.getTable().get(i)),temp.isMove(temp.getTable().get(i)));
                if(solution(temp)) return true;
                else Collections.copy(temp.getTable(), solution.peekFirst().getTable());
            }
        }
        solution.pollFirst();
        return false;
    }

    // method forTestCase is exactly same as method solution, but this method will print each move that create by backtracking algorithm
    public boolean forTestCase(Table table) {
        solution.push(table);
        if(this.table.getEnd().equals(table.getTable())) return true;

        Table temp = new Table();
        table.getTable().forEach(x -> temp.getTable().add(x));
        for(int i=temp.findRange()[0];i<=temp.findRange()[1];i++) {
            if((temp.isMove(temp.getTable().get(i)) != 0) && !temp.getTable().get(i).equals("__")) {
                temp.moveMable(temp.stringToMarble(temp.getTable().get(i)),temp.isMove(temp.getTable().get(i)));
                temp.printTable(step++);
                if(forTestCase(temp)) return true;
                else {
                    Collections.copy(temp.getTable(), solution.peekFirst().getTable());
                    temp.printTable(step++);
                }
            }
        }
        System.out.println("\nNo possible move for this state , backtrack to previous state");
        solution.pollFirst();
        return false;
    }


}
