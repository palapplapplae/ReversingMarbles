/*  Members
6413108 Palarp Wasuwat
6413211 Kobkit Ruangsuriyakit
*/

package Project1_108;

import java.util.ArrayList;

public class Table {
    private ArrayList<String> table, end;


    // for Custom table in test case
    public Table(ArrayList<String> test) {
        table = test;
        end = new ArrayList<>();
        for (int i = 0; i < (4) + 1; i++) {
            if (i < 2) end.add("b"+i);
            else if (i == 2) end.add("__");
            else end.add("w" + (i - (3)));
        }
        printTable(0);
    }

    // Empty table for backtracking method
    public Table() {
        table = new ArrayList<>(0);
        end = new ArrayList<>();
    }

    // Create table size 2n + 1
    public Table(int n) {
        table = new ArrayList<>();
        end = new ArrayList<>();

        for (int i = 0; i < (2 * n) + 1; i++) {
            if (i < n){
                table.add("w" + i);
                end.add("b"+i);
            }
            else if (i == n) {
                table.add("__");
                end.add("__");
            }
            else {
                table.add("b" + (i - (n + 1)));
                end.add("w" + (i - (n + 1)));
            }
        }
        printTable(0);
    }

    public void printTable(int step) {
        if (step == 0) System.out.println("Initial  >>");
        else System.out.printf("\nStep %2d  >>\n", step);

        for (int i = 0; i < 4; i++) {
            System.out.printf("\t");
            for (int j = 0, data = 0; j < (table.size() * 5) + 1; j++) {
                if (i == 0 && j % 5 == 0)
                    System.out.printf(" ");
                else if (i == 3 && j % 5 == 0)
                    System.out.printf("|");
                else if (i == 0 || i == 3)
                    System.out.printf("_");
                else if (j % 5 == 0)
                    System.out.printf("|");
                else if (j % 5 == 1) {
                    if (i == 2) {
                        System.out.printf("%3s ", table.get(data));
                        data++;
                    }
                    else System.out.printf("%3s ", " ");
                }
            }
            System.out.println();
        }
    }

    // Check if table is correct or not
    public boolean isCorrect(){
        for(int i=0;i<table.size();i++)
            if(!table.get(i).equals(end.get(i))) return false;
        return true;
    }

    // Check if table is unmovable or not
     public boolean isEnd() {
        int count = 0;
        for(int i=findRange()[0];i<=findRange()[1];i++) count += isMove(table.get(i));
        return !(count == 0);
     }

     // Check is marble can move or jump (can't move = 0 , move = 1 , jump = 2)
     public int isMove(String str){
        if (str.equals("__")) return 0;

        int[] marble = stringToMarble(str);
        switch (marble[0]) {
            case 0 -> {
                if (marble[1] - 1 < 0) return 0;
                if (table.get(marble[1] - 1).equals("__")) return 1;
                else if (marble[1] - 2 >= 0 && (stringToMarble(table.get(marble[1] - 1))[0] == 1)) {
                    if (table.get(marble[1] - 2).equals("__")) return 2;
                }
            }
            case 1 -> {
                if (marble[1] > table.size() - 2) return 0;
                if (table.get(marble[1] + 1).equals("__")) return 1;
                else if (marble[1] + 2 < table.size() && (stringToMarble(table.get(marble[1] + 1))[0] == 0)) {
                    if (table.get(marble[1] + 2).equals("__")) return 2;
                }
            }
        };
        return 0;
     }

     //move a marble (first argument is marble and second is how marble move)
     public void moveMable(int[] marble, int move) {
        String temp = table.get(marble[1]);
        switch (marble[0]) {
            case 0 -> {     // black move or jump left
                table.set(marble[1], table.get(marble[1] - move));
                table.set(marble[1] - move, temp);
            }
            case 1 -> {     // white move or jump right
                table.set(marble[1], table.get(marble[1] + move));
                table.set(marble[1] + move, temp);
            }
        };
     }

     // Convert String to marble [type,number,index]
     // type 0 = black , type 1 = white
     public int[] stringToMarble(String str){
         String[] temp = str.split("");
         int[] marble = new int[2];
         marble[0] = switch (temp[0]){
             case "b" -> 0;
             case "w" -> 1;
             default -> -1;
         };
         marble[1] = table.indexOf(str);
         return marble;
     }

     //find range that marble can move (there is at most 5 index that around the __ can move)
     public int[] findRange(){
         int start = table.indexOf("__")-2;
         int end = table.indexOf("__")+2;

         if(table.indexOf("__")<2) start = 0;
         if(table.size()-table.indexOf("__")< 3) end = table.size()-1;
         return new int[] {start,end};
     }

    //getter and setter
    public ArrayList<String> getTable() { return table; }
    public ArrayList<String> getEnd() { return end; }
}
