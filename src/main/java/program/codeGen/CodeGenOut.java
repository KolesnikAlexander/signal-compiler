package program.codeGen;

import program.lexer.table.Error;

public class CodeGenOut {
    public static void printListing(){
        for (String s : CodeGenerator.listing) {
            System.out.println(s);
        }
    }
    public static void printCodeGenErrors(){
        for (Error error : CodeGenerator.errors) {
            System.out.println(error);
        }
    }
}
