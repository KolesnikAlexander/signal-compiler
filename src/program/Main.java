package program;

public class Main {

    public static void main(String[] args) {

        Reader.init("file.txt");
        Character c = Reader.read();
        while (c!=null) {
            System.out.print(c);
            c = Reader.read();
        }

    }
}
