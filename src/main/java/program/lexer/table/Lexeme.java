package program.lexer.table;

import java.util.Objects;

public class Lexeme {
    int code;
    int line;
    int row;

    public Lexeme(int code, int line, int row) {
        this.code = code;
        this.line = line;
        this.row = row;
    }

    public Lexeme(Lexeme lexeme) {
        this.code = lexeme.code;
        this.line = lexeme.line;
        this.row = lexeme.row;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexeme lexeme = (Lexeme) o;
        return code == lexeme.code;
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
