package GE;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Chromosome {
    private List<Codon> chromosome;
    private final int maxCodons;
    private final int minCodons;
    private final Random random;

    /**
     * Cosntructor
     * @param maxCodons
     * @param minCodons
     * @param random
     */
    public Chromosome(int maxCodons, int minCodons, Random random){
        this.maxCodons = maxCodons;
        this.minCodons = minCodons;
        this.random = random;
        chromosome = new ArrayList<Codon>();

        //Create the codons
        for(int i = 0;i < maxCodons; i++)
            chromosome.add(new Codon(random));
    }


    public void mutate(){
        
    }

    /**
     * getter method
     * @return returns the current chromosome
     */
    public List<Codon> getChromosome(){
        return this.chromosome;
    }

}
