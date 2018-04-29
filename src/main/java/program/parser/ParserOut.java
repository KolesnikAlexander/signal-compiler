package program.parser;

import program.lexer.table.Error;
import static program.parser.Parser.tree;

public class ParserOut {
    public static void outErrors(){
        System.out.println("Parser errors: "+Parser.errors.size());
        for (Error error : Parser.errors) {
            System.out.println(error.getLine()+":"+error.getRow()+" "+error.getMessage());
        }
    }

    public static void treeOut(){
        printNode(tree, 0);
    }

    public static String tabs(int n){
        String string = "";
        for (int i = 0; i < n; i++){
            string+="  ";
        }
        return string;
    }
    public static void printNode(Node node, int level){
        System.out.println(tabs(level) + node);//String.format("%10s", node)
        int nextLevel = ++level;
        for(Node n:node.getBranches()){
            printNode(n, nextLevel);
        }
    }
}