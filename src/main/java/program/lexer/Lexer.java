package program.lexer;

import program.Reader;
import program.lexer.table.Error;
import program.lexer.table.Lexeme;
import program.lexer.table.TableCell;

import static program.lexer.Lexer.State.*;
import static program.lexer.table.Tables.*;

public class Lexer {
    enum State {
        INP,
        CNS,
        WRD,
        BCOM,
        COM,
        ECOM,
        AST,
        EXP_D,
        EXP_E,
        EXP_X,
        EXP_P,
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
                case EXP_D:
                    expD();
                    break;
                case EXP_E:
                    expE();
                    break;
                case EXP_X:
                    expX();
                    break;
                case EXP_P:
                    expP();
                    break;
                case DELIM:
                    delim();
                    break;
                case ER:
                    er();
                    break;
            }
        }
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
        else if(c.equals('$')){
            state = EXP_D;
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
        if(c!=null && Characters.isLetter(c)){
            cnsErr();
            while ((c!=null)&&(Characters.isLetter(c))){
                c = Reader.read();
            }
        }
        else
            cnsOut();

        state = CHECK;
//        cnsOut();
//        state = CHECK;
    }

    private static void cnsErr() {
        errors.add(new Error(line,row,
                "Identifier cannot start from digit"));
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
            return;
        }
        key = getKeyByValue(identifiers, new TableCell(buffer, "ID"));
        if (key != null){
            lexemes.add(new Lexeme(key, line, row));
            return;
        }
        else{
            identifiers.put(identifierCount, new TableCell(buffer,"ID"));
            lexemes.add(new Lexeme(identifierCount, line, row));
            identifierCount++;
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
            sdelOut();
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
        errors.add(new Error(line,row,
                "Comment bracket is not closed"));
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
        errors.add(new Error(line,row,
                "Asterisk may be a part of a comment operator only: (* comment *)"));
    }

    private static void expD() {
        buffer+=c;
        c = Reader.read();
        if(c.equals('E')){
            state = EXP_E;
        }
        else{
            state = CHECK;
            expErr();
        }
    }

    private static void expE() {
        buffer+=c;
        c = Reader.read();
        if(c.equals('X')){
            state = EXP_X;
        }
        else{
            state = CHECK;
            expErr();
        }
    }

    private static void expX() {
        buffer+=c;
        c = Reader.read();
        if(c.equals('P')){
            state = EXP_P;
        }
        else{
            expErr();
            state = CHECK;
        }
    }

    private static void expP() {
        buffer+=c;
        expDOut();
        c = Reader.read();
        state = CHECK;

    }

    private static void expDOut() {
        Integer key = getKeyByValue(multDelim, new TableCell(buffer, "M-DELIM"));
        lexemes.add(new Lexeme(key, line, row));
    }

    private static void expErr() {
        errors.add(new Error(line,row,
                "'"+buffer+"' is not allowed. Expected to be '$EXP'"));
    }

    private static void delim() {
        buffer+=c;
        sdelOut();
        c = Reader.read();
        state = CHECK;
    }

    private static void sdelOut(){
        Integer key = getKeyByValue(singleDelim, new TableCell(buffer, "S-DELIM"));
        lexemes.add(new Lexeme(key, line, row));
    }

    private static void er() {
        erErr();
        c = Reader.read();
        state = CHECK;
    }

    private static void erErr() {
        errors.add(new Error(line,row,
                "Unknown symbol: "+c));
    }
}
