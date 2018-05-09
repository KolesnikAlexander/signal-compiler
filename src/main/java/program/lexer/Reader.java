package program.lexer;

import java.io.*;

public class Reader {
    private static String filePath;
    private static File file;
    private static java.io.Reader reader;
    public static int row = 0;
    public static int line = 0;
    public static boolean wasBackSlashN = true;

    public static void init(String filePath){
        Reader.filePath = filePath;
        try {
            Reader.file = new File(filePath);
            Reader.reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            Reader.reader = new InputStreamReader(new FileInputStream(file));

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding");
            System.exit(-1);
        }
    }
    public static Character read(){
        int val = 0;
        try {
            val = reader.read();
        } catch (IOException e) {
            System.err.println("IO exception");
            e.printStackTrace();
            System.exit(-1);
        }
        if(val == -1)
            return null;
        else{
            setPosition((char)val);
            return new Character((char)val);
        }


    }

    private static void setPosition(char val) {
        if(wasBackSlashN){
            row = 1;
            line++;
            wasBackSlashN = false;
        }
        else
            row++;

        if (val == '\n'){
            wasBackSlashN = true;
        }

    }
}
