package program.lexer;

import program.Reader;

import static program.lexer.Lexer.State.*;

public class Lexer {
    enum State {
        INP,
        CNS,
        WRD,
        BCOM,
        COM,
        ECOM,
        ERR,
        EXIT,
        CHECK,
        DELIM;
    }
    private static State state = INP;
    private static Character c; //current character
    private static String buffer ="";

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
                case DELIM:
                    delim();
                    break;
                case ERR:
                    err();
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
        else if(Characters.isDelemiter(c)){
            state = DELIM;
        }
        else{ //er
            state = ERR;
        }
        return;

    }
    private static void cns() {
        System.out.println("State: "+state.name());
        buffer+=c;
        c = Reader.read();
        while ((c!=null)&&(Characters.isDigit(c))){
            buffer+=c;
            c = Reader.read();
        }
        // TODO: 25.02.18 CONST OUT
        System.out.println("I read: "+buffer);
        state = CHECK;
    }

    private static void wrd() {
        System.out.println("State: "+state.name());
        state = EXIT;

    }

    private static void bcom() {
        System.out.println("State: "+state.name());
        state = EXIT;

    }

    private static void com() {
        System.out.println("State: "+state.name());
        state = EXIT;

    }


    private static void ecom() {
        System.out.println("State: "+state.name());
        state = EXIT;

    }

    private static void delim() {
        System.out.println("Delimiter found: "+c);
        // TODO: 25.02.18 OUT DELIMITER
        c = Reader.read();
        state = CHECK;
    }

    private static void err() {
        System.out.println("State: "+state.name());
        state = EXIT;
    }
}
