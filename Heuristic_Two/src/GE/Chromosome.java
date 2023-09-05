package GE;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Chromosome {
    private List<Codon> chromosome;
    private final int maxCodons;
    private final int minCodons;
    private final Random random;
    private int[] fitness; //will hold the hard constraint cost at index 0 and the soft constraint cost at index 1
    private int numCodons;

    /**
     * Cosntructor
     * @param maxCodons
     * @param minCodons
     * @param random
     * @param flag True means execute the loop, false means skip the loop and its for deep copy thingy
     */
    public Chromosome(int maxCodons, int minCodons, Random random, boolean flag, int numCodons){
        this.maxCodons = maxCodons;
        this.minCodons = minCodons;
        this.random = random;
        this.chromosome = new ArrayList<Codon>();
        this.fitness = new int[2];
        this.fitness[0] = Integer.MAX_VALUE; this.fitness[1] = Integer.MAX_VALUE;

        this.numCodons = numCodons;

        //Create the codons
        for(int i = 0;i < numCodons && flag; i++)
            chromosome.add(new Codon(random));
    }

    /**
     * This method performs single point crossover. If the resulting offspring has a number of codons > maxCodons the excess is trimmed off.
     * @param secondChromosome 2nd parent to perform crossover with.
     * @return The seond parent which has been modified
     */
    public Chromosome singlePointCrossover(Chromosome secondChromosome){
        int shortestChromosome = (this.chromosome.size() < secondChromosome.chromosome.size()) ? this.chromosome.size() : secondChromosome.chromosome.size();
        int crossoverPoint = random.nextInt(shortestChromosome-1);

        List<Codon> parentOne = new ArrayList<Codon>(this.chromosome.subList(0, crossoverPoint));
        parentOne.addAll(new ArrayList<Codon>(secondChromosome.chromosome.subList(crossoverPoint, secondChromosome.chromosome.size())));

        List<Codon> parentTwo = new ArrayList<Codon>(secondChromosome.chromosome.subList(0, crossoverPoint));
        parentTwo.addAll(new ArrayList<Codon>(this.chromosome.subList(crossoverPoint, this.chromosome.size())));

        //trim off any excess codons
        if(parentOne.size() > this.maxCodons){
            parentOne = new ArrayList<Codon>(parentOne.subList(0, this.maxCodons));
            this.numCodons = this.maxCodons;
        }
        if(parentTwo.size() > this.maxCodons){
            parentTwo = new ArrayList<Codon>(parentTwo.subList(0, this.maxCodons));
            secondChromosome.chromosome = parentTwo;
            secondChromosome.numCodons = this.maxCodons;
        }

        this.chromosome = parentOne;
        this.numCodons = this.chromosome.size();
        secondChromosome.chromosome = parentTwo;
        secondChromosome.numCodons = parentTwo.size();

        return secondChromosome;
    }

    /**
     * Maps the chromosome to a derivation tree and then evaluates it
     */
    public void evaluateIndividual(){

        //TODO: have to update the fitness value when done here
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
        Chromosome copy = new Chromosome(this.maxCodons, this.minCodons, this.random, false, this.numCodons);

        for(Codon c: chromosome)
            copy.chromosome.add(c.getCopy());
        
            copy.numCodons = this.numCodons;
        
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

    /**
     * Getter method
     * @return hard + soft constraint cost
     */
    public int getFitness(){
        return this.fitness[0] + this.fitness[1];
    }

    /**
     * Print the fitness values of a chromosome
     */
    public void printFitness(){
        System.out.println("HC: " + " SC: " + this.fitness[0] + this.fitness[1]);
    }

}
