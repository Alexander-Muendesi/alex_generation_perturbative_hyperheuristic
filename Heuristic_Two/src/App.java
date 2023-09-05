import java.util.Random;

import GE.Chromosome;

public class App {
    public static void main(String[] args) throws Exception {
        int maxCodons = 20, minCodons = 2 , numCodons = 15;
        Random random = new Random(0);
        boolean flag = true;

        Chromosome chromosome = new Chromosome(maxCodons, minCodons, random, flag, numCodons);
        chromosome.evaluateIndividual();
    }
}
