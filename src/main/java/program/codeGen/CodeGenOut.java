package program.codeGen;

public class CodeGenOut {
    public static void printListing(){
        System.out.println("CODE GENETRATOR:");
        System.out.println("------------------");
        for (String s : CodeGenerator.listing) {
            System.out.println(s);
        }
    }
}
