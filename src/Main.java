import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Network network = new Network();
        network.setImages("images/images.txt");
        network.setImage("images/image1.txt");
        network.showImages();
        System.out.println("---------");
        network.createWeightMatrix();
        int makeStep;
        Scanner sc = new Scanner(System.in, "UTF-8");
        do {
            System.out.println("Enter Steps:");
            makeStep = sc.nextInt();
            for(int step = 0; step < makeStep && !network.finish; step++) {
                network.nextStep();
            }
            network.showImage();
            System.out.println("Step: " + network.getStep());
            if (network.finish) {
                return;
            }
        } while (makeStep > 0);
    }
}
