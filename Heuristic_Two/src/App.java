
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int maxCodons = 20, populationSize = 100, minCodons = 5;

        int codonRange = maxCodons - minCodons + 1;
        int codonsPerIndividual = minCodons;

        for(int i = 0; i < populationSize; i++ ){
            codonsPerIndividual = (codonsPerIndividual - minCodons + 1) % codonRange + minCodons;
            System.out.println(codonsPerIndividual);
        }
    }
}
