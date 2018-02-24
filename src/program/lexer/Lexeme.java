package program.lexer;

public class Lexeme {
    int code;
    int line;
    int row;

    public Lexeme(int code, int line, int row) {
        this.code = code;
        this.line = line;
        this.row = row;
    }
}
