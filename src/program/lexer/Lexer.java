package program.lexer;

import program.Reader;

import static program.lexer.Lexer.State.*;

public class Lexer {
    enum State {
        INP,
        OUT,
        CNS,
        WRD,
        BCOM,
        COM,
        ECOM,
        ERR,
        EXIT,
        CHECK
    }
    private static State state = INP;
    private static Character c; //current character

    public static void run(){

        while (state != EXIT) {
            switch (state) {
                case INP:
                    inp();
                    break;
                case OUT:
                    out();
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
                case ERR:
                    err();
                    break;
            }
        }
    }

    private static void err() {

    }

    private static void ecom() {

    }

    private static void com() {

    }

    private static void bcom() {

    }

    private static void wrd() {

    }

    private static void cns() {

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
        if(Characters.isWhitespace(c)){
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
            state = OUT;
            // TODO: 25.02.18 FILL OUT INFO
        }
        else if (c == null){ //eof
            state = EXIT;
        }
        else{ //er
            state = OUT;
            // TODO: 25.02.18 FILL OUT INFO
        }
        return;

    }
}
