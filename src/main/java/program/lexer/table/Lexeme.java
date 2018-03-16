package program.lexer.table;

public class Lexeme {
    int code;
    int line;
    int row;

    public Lexeme(int code, int line, int row) {
        this.code = code;
        this.line = line;
        this.row = row;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getLine() {
        return line;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "code=" + code +
                ", line=" + line +
                ", row=" + row +
                '}'+"\n";
    }
}
