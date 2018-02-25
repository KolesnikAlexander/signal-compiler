package program;

import java.io.*;

public class Reader {
    private static String filePath;
    private static File file;
    private static java.io.Reader reader;
    public static int posX = 0;
    public static int posY = 0;
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
            System.out.println("X: "+posX+", Y: "+posY+", symbol: "+val);
            return new Character((char)val);
        }


    }

    private static void setPosition(char val) {
        if(wasBackSlashN){
            posX = 1;
            posY++;
            wasBackSlashN = false;
        }
        else
            posX++;

        if (val == '\n'){
            wasBackSlashN = true;
        }

    }
}
