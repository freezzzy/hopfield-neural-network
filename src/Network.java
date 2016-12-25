import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Network {

    int width;
    int height;
    int pixels;
    int size;
    Matrix image;
    Matrix weight;
    List<Matrix> images = new ArrayList<>();
    List<Integer> hopfieldRandom = new ArrayList<>();
    boolean change = true;
    int step;
    boolean finish = false;

    public List<Double> readLine(String filePath) {
        List<Double> line = new ArrayList<>();
        try {
            line = Files.readAllLines(Paths.get(filePath))
                    .stream()
                    .map(Double::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public void setImages(String filePath) {
        List<Double> array = readLine(filePath);
        height = array.get(0).intValue();
        width = array.get(1).intValue();
        size = array.get(2).intValue();
        pixels = width * height;
        array.remove(0);
        array.remove(0);
        array.remove(0);

        for (int indexImage = 0; indexImage < size; indexImage++) {
            List<Double> vector = new ArrayList<>();
            for (int indexPixel = 0; indexPixel < pixels; indexPixel++) {
                vector.add(array.get(0));
                array.remove(0);
            }

            Double[] vectorArr = new Double[vector.size()];
            vectorArr = vector.toArray(vectorArr);
            images.add(new Matrix(vectorArr));
        }
    }

    public void setImage(String filePath) {
        List<Double> array = readLine(filePath);
        int n = array.get(0).intValue();
        int m = array.get(1).intValue();
        pixels = m * n;
        array.remove(0);
        array.remove(0);

        List<Double> vector = new ArrayList<>();

        for (int indexPixel = 0; indexPixel < pixels; indexPixel++) {
            vector.add(array.get(0));
            array.remove(0);
        }

        Double[] vectorArr = new Double[vector.size()];
        vectorArr = vector.toArray(vectorArr);
        image = new Matrix(vectorArr);
    }

    public int getStep() {
        return step;
    }

    void showImages() {
        System.out.println("Images: ");
        for (int indexImage = 0; indexImage < size; indexImage++) {
            System.out.println("\nImage #" + (indexImage + 1));
            for (int indexPixel = 0; indexPixel < pixels; indexPixel++) {
                System.out.print(images.get(indexImage).get(0, indexPixel) == 1 ? " " : "@");
                if ((indexPixel + 1) % width == 0) {
                    System.out.println("");
                }
            }
        }
    }

    void showImage() {
        for (int indexPixel = 0; indexPixel < pixels; indexPixel++) {
            System.out.print(image.get(0, indexPixel) == 1 ? " " : "@");
            if ((indexPixel + 1) % width == 0) {
                System.out.println("");
            }
        }
    }

    void nextStep() {
        int random = getHopfielRandomOrExit();
        Matrix S = image.times(weight.getColumn(random));

        double a = activate(S.a[0][0]) > 0 ? 1 : -1;
        change = a != image.a[0][random];
        image.a[0][random] = a;
        step++;
        if (!change && !hopfieldRandom.isEmpty()) {
            hopfieldRandom.remove(hopfieldRandom.size()-1);
        }
    }

    double activate(double x) {
        double pow = Math.pow(Math.E, 2 * x);
        return (pow - 1) / (pow + 1);
    }

    int getHopfielRandomOrExit() {
        if (hopfieldRandom.isEmpty()) {
            if (change) {
                for (int i = 0; i < pixels; i++) {
                    hopfieldRandom.add(i);
                }
                Collections.shuffle(hopfieldRandom);
            } else {
                System.out.println("Recognition finished! Step = " + step);
                finish = true;
                return 0;
            }
        }
        return hopfieldRandom.get(hopfieldRandom.size() - 1);
    }

    void createWeightMatrix() {
        double Factor = 0.8;
        double E = 0.0000001;
        int N = pixels + 1;
        double change;
        weight = new Matrix(pixels, pixels);
        do {
            change = 0;
            for (int indexImage = 0; indexImage < size; indexImage++) {
                Matrix Xi = images.get(indexImage).transpose();
                Matrix deltaW = Xi.minus(weight.times(Xi)).times(Xi.transpose()).times(Factor / N);
                change += deltaW.abs().sum();
                weight = weight.plus(deltaW);
            }
        } while (change > E);
    }

}
