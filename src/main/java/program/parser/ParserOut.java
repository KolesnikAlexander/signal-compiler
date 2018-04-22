package program.parser;

import program.lexer.table.Error;

public class ParserOut {
    public static void outErrors(){
        System.out.println("Parser errors: "+Parser.errors.size());
        for (Error error : Parser.errors) {
            System.out.println(error.getLine()+":"+error.getRow()+" "+error.getMessage());
        }
    }
}