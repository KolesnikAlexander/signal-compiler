package program.lexer;

import program.Reader;

import static program.lexer.Lexer.State.*;
import static program.lexer.Tables.*;

public class Lexer {
    enum State {
        INP,
        CNS,
        WRD,
        BCOM,
        COM,
        ECOM,
        AST,
        ER,
        EXIT,
        CHECK,
        DELIM;
    }
    private static State state = INP;
    private static Character c; //current character
    private static String buffer ="";
    private static int line;
    private static int row;

    public static void run(){
        while (state != EXIT) {
            switch (state) {
                case INP:
                    inp();
                    break;
                case EXIT:
                    break;
                case CHECK:
                    check();
                    break;
                case CNS:
                    cns();
                    break;
                case WRD:
                    wrd();
                    break;
                case BCOM:
                    bcom();
                    break;
                case COM:
                    com();
                    break;
                case ECOM:
                    ecom();
                    break;
                case AST:
                    ast();
                    break;
                case DELIM:
                    delim();
                    break;
                case ER:
                    er();
                    break;
            }
        }
        System.out.println("State: "+state.name()+". Last character: "+c);
    }



    private static void inp() {
        c = Reader.read();
        state = CHECK;
    }
    private static void out() {
        // TODO: 25.02.18  OUT TO TABLES
        state = CHECK;
    }

    private static void check() {
        line = Reader.line;
        row = Reader.row;

        buffer = "";

        if (c == null){ //eof
            state = EXIT;
        }
        else if(Characters.isWhitespace(c)){
            state = INP;
        }
        else if(Characters.isDigit(c)){
            state = CNS;
        }
        else if(Characters.isLetter(c)){
            state = WRD;
        }
        else if(c.equals('(')){
            state = BCOM;
        }
        else if(c.equals('*')){
            state = AST;
        }
        else if(Characters.isDelemiter(c)){
            state = DELIM;
        }
        else{ //er
            state = ER;
        }
        return;

    }
    private static void cns() {
        buffer+=c;
        c = Reader.read();
        while ((c!=null)&&(Characters.isDigit(c))){
            buffer+=c;
            c = Reader.read();
        }
        cnsOut();
        state = CHECK;
    }

    private static void cnsOut() {
        Integer key = getKeyByValue(constants, new TableCell(buffer, "CNS"));
        if (key!=null){
            lexemes.add(new Lexeme(key, line, row));
        }
        else{
            constants.put(constCount, new TableCell(buffer,"CNS"));
            lexemes.add(new Lexeme(constCount, line, row));
            constCount++;
        }
        buffer = "";
    }

    private static void wrd() {
        buffer+=c;
        c = Reader.read();
        while ((c!=null)&&((Characters.isLetter(c))||Characters.isDigit(c))){
            buffer+=c;
            c = Reader.read();
        }
        wrdOut();
        state = CHECK;

    }

    private static void wrdOut() {
        Integer key = getKeyByValue(keyWords, new TableCell(buffer, "KEY"));
        if (key != null){
            lexemes.add(new Lexeme(key, line, row));
            buffer = "";
            return;
        }
        key = getKeyByValue(identifiers, new TableCell(buffer, "ID"));
        if (key != null){
            lexemes.add(new Lexeme(key, line, row));
            buffer = "";
            return;
        }
        else{
            identifiers.put(identifierCount, new TableCell(buffer,"ID"));
            lexemes.add(new Lexeme(identifierCount, line, row));
            identifierCount++;
            buffer = "";
            return;
        }

    }

    private static void bcom() {
        buffer+=c;
        c = Reader.read();
        if((c!= null) && c.equals('*')){
            state = COM;
            return;
        }
        else {
            singleDelimOut();
            state = CHECK;
        }
    }

    private static void com() {
        buffer+=c;
        c = Reader.read();
        if(c == null){
            exitErr();
            state = INP;
        }
        else if(c.equals('*')){
            state = ECOM;
            return;
        }
        else{
            return;
        }
    }

    private static void exitErr() {
        System.out.println("Comment bracket is not closed");
    }


    private static void ecom() {
        buffer+=c;
        c = Reader.read();
        if(c == null){
            exitErr();
            state = EXIT;
            return;
        }
        else if(c.equals('*')){
            return;
        }
        else if(c.equals(')')){
            buffer = "";
            state = INP;
            return;
        }
        else{
            state = COM;
            return;
        }

    }

    private static void ast() {
        astErr();
        c = Reader.read();
        state = CHECK;
    }

    private static void astErr() {
        System.out.println("Asterisk may be a part of a comment operator only: (* comment *)");
    }

    private static void delim() {
        buffer+=c;
        singleDelimOut();
        c = Reader.read();
        state = CHECK;
    }

    private static void singleDelimOut(){
        Integer key = getKeyByValue(singleDelim, new TableCell(buffer, "S-DELIM"));
        lexemes.add(new Lexeme(key, line, row));
        buffer = "";
    }

    private static void er() {

        state = EXIT;
    }
}
