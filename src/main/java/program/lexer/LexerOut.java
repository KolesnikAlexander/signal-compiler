package program.lexer;

import program.lexer.table.Error;
import program.lexer.table.Lexeme;
import program.lexer.table.TableCell;
import program.lexer.table.Tables;

import java.util.Iterator;
import java.util.Map;

public class LexerOut {
    public static void printLexerResult() {
        System.out.println("LEXEMES: "+"\n---------------");
        printLexemesTable();

        System.out.println("\nSINGLE DELIMITERS"+"\n---------------");
        printHashMap(Tables.singleDelim);

        System.out.println("\nMULTI DELIMITERS"+"\n---------------");
        printHashMap(Tables.multDelim);

        System.out.println("\nKEYWORDS"+"\n---------------");
        printHashMap(Tables.keyWords);

        System.out.println("\nCONSTANTS: "+"\n---------------");
        printHashMap(Tables.constants);

        System.out.println("\nIDENTIFIERS: "+"\n---------------");
        printHashMap(Tables.identifiers);
    }

    private static void printHeader() {
        String col1 = String.format("%1$-"+4+ "s", "Code");
        String col2 = String.format("%1$-"+7+ "s", "Type");
        String col3 = "Value";
        System.out.println(col1+ " "+col2+"  "+col3);
        System.out.println("---------------");
    }

    private static void printHashMap(Map<Integer, TableCell> table){
        Iterator it = table.entrySet().iterator();
        printHeader();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            TableCell cell = (TableCell)pair.getValue();

            String col1 = String.format("%1$-"+4+ "s", pair.getKey().toString());
            String col2 = String.format("%1$-"+7+ "s", cell.getType());
            String col3 = cell.getValue();

            System.out.println(col1+ " "+col2+"  "+col3);
        }
    }

    private static void printLexemesTable(){
        for (Lexeme lexeme : Tables.lexemes) {
            TableCell tableCell = tableCellByCode(lexeme.getCode());

            String col1= lexeme.getLine() + ":"+ lexeme.getRow();
            String col2= lexeme.getCode().toString();
            String col3 = tableCell.getType();
            String col4 = tableCell.getValue();

            col1 = String.format("%1$-"+6+ "s", col1);
            col2 = String.format("%1$-"+4+ "s", col2);
            col3 = String.format("%1$-"+7+ "s", col3);

            System.out.println(col1+" "+col2+"  "+col3+" "+col4);
        }
    }

    public static TableCell tableCellByCode(int code) {
        if((0<=code) && (code <= 255)){
            return Tables.singleDelim.get(code);
        }
        else if((301<=code) && (code <= 400)){
            return Tables.multDelim.get(code);
        }
        else if((401<=code) && (code <= 500)){
            return Tables.keyWords.get(code);
        }
        else if((501<=code) && (code <= 1000)){
            return Tables.constants.get(code);
        }
        else if(1001 <= code){
            return Tables.identifiers.get(code);
        }
        else
            return null;
    }

    public static void printErrors() {
        System.out.println("Lexer errors: "+Tables.errors.size());
        for (Error error : Tables.errors) {
            System.out.println(error);
        }
    }
}
