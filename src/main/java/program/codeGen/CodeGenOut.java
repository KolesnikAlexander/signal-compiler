package program.codeGen;

import program.lexer.table.Error;

public class CodeGenOut {
    public static void printListing(){
        System.out.println("CODE GENETRATOR:");
        System.out.println("------------------");
        for (String s : CodeGenerator.listing) {
            System.out.println(s);
        }

        System.out.println("ERRORS:");
        for (Error error : CodeGenerator.errors) {
            System.out.println(error);
        }
    }
}
