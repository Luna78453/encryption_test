import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Sha256 fuckYou = new Sha256();
        fuckYou.Encrypt("Java/testo.txt", "Java/testo-sha256.txt");

        /*System.out.println(fuckYouToo);

        for(Byte c : fuckYouToo){
            System.out.println("times two");
            System.out.println((byte)(c * 2) + " " + (c * 2) + " " + c);
            System.out.println("times three");
            System.out.println((byte)(c * 3) + " " + (c * 3) + " " + c);
            System.out.println("times four");
            System.out.println((byte)(c * 4) + " " + (c * 4) + " " + (char)c);
        }*/
    }
}