package program;

import program.lexer.Lexer;
import program.lexer.Tables;

public class Main {

    public static void main(String[] args) {

        Tables.initTables();
        Reader.init("file.txt");
        Lexer.run();
//        Character c = Reader.read();
//        while (c!=null) {
//            System.out.print(c);        System.out.println("State: "+state.name());
//            c = Reader.read();
//        }

    }
}
