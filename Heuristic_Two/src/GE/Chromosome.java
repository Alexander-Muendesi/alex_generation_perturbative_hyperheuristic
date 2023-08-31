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
     * @param flag True means execute the loop, false means skip the loop and its for deep copy thingy
     */
    public Chromosome(int maxCodons, int minCodons, Random random, boolean flag){
        this.maxCodons = maxCodons;
        this.minCodons = minCodons;
        this.random = random;
        this.chromosome = new ArrayList<Codon>();

        //Create the codons
        for(int i = 0;i < maxCodons && flag; i++)
            chromosome.add(new Codon(random));
    }

    /**
     * This method performs single point crossover. If the resulting offspring has a number of codons > maxCodons the excess is trimmed off.
     * @param secondChromosome 2nd parent to perform crossover with.
     * @return The seond parents modified chromosome
     */
    public List<Codon> singlePointCrossover(List<Codon> secondChromosome){
        int shortestChromosome = (this.chromosome.size() < secondChromosome.size()) ? this.chromosome.size() : secondChromosome.size();
        int crossoverPoint = random.nextInt(shortestChromosome-1);

        List<Codon> parentOne = new ArrayList<Codon>(this.chromosome.subList(0, crossoverPoint));
        parentOne.addAll(new ArrayList<Codon>(secondChromosome.subList(crossoverPoint, secondChromosome.size())));

        List<Codon> parentTwo = new ArrayList<Codon>(secondChromosome.subList(0, crossoverPoint));
        parentTwo.addAll(new ArrayList<Codon>(this.chromosome.subList(crossoverPoint, this.chromosome.size())));

        //trim off any excess codons
        if(parentOne.size() > this.maxCodons){
            parentOne = new ArrayList<Codon>(parentOne.subList(0, this.maxCodons));
        }
        if(parentTwo.size() > this.maxCodons){
            parentTwo = new ArrayList<Codon>(parentTwo.subList(0, this.maxCodons));
        }

        this.chromosome = parentOne;

        return parentTwo;
    }


    /**
     * Performs mutation by randomly fipping a bit in a codon.
     */
    public void mutate(){
        int index = random.nextInt(chromosome.size());
        chromosome.get(index).mutate();
    }

    /**
     * Creates a deep copy of the current chromosome
     * @return new deep copy chromosom
     */
    public Chromosome copy(){
        Chromosome copy = new Chromosome(maxCodons, minCodons, random, false);

        for(Codon c: chromosome)
            copy.chromosome.add(c.getCopy());
        
        return copy;
    }

    /**
     * getter method
     * @return returns the current chromosome
     */
    public List<Codon> getChromosome(){
        return this.chromosome;
    }

    /**
     * Setter method
     * @param chromosome
     */
    public void setChromosome(List<Codon> chromosome){
        this.chromosome = chromosome;
    }

}
