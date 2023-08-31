package GE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GrammaticalEvolution {
    private Map<Integer, Chromosome> population;
    private Random random;
    private int tournamentSize, populationSize, mutationRate, crossoverRate, maxGenerations;
    private int maxCodons, minCodons;

    public GrammaticalEvolution(Random random, int maxCodons, int minCodons, int tournamentSize, int populationSize,
                                    int mutationRate, int crossoverRate, int maxGenerations){
        this.population = new HashMap<Integer,Chromosome>();
        this.random = random;
        this.tournamentSize = tournamentSize;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
    }

    public void execute(){
        generateInitialPopulation();

    }

    /**
     * Creates the initial population of chromosomes. Creates them in a manner similar to ramped half and half.
     * It ensures that there is almost an equal number of individuals per allowed chromosome length
     */
    private void generateInitialPopulation(){
        int codonRange = this.maxCodons - minCodons + 1;
        int codonsPerIndividual = minCodons;

        for(int i = 0; i < populationSize; i++ ){
            this.population.put(i, new Chromosome(this.maxCodons, this.minCodons, this.random, true, codonsPerIndividual));
            codonsPerIndividual = (codonsPerIndividual - minCodons + 1) % codonRange + minCodons;
        }
    }

    /**
     * Creates a tournament of size tournament_size and returns the individual with the best fitness
     * @return Fittest individual
     */
    public Chromosome tournamentSelection(){
        List<Chromosome> tournament = new ArrayList<Chromosome>();
        Chromosome bestIndividual = null;

        while(tournament.size() < this.tournamentSize){
            Chromosome temp = population.get(random.nextInt(population.size()));
            
            if(tournament.isEmpty()){
                tournament.add(temp);
                bestIndividual = temp;
            }
            else if(tournament.contains(temp) == false){
                tournament.add(temp);
                bestIndividual = (temp.getFitness() < bestIndividual.getFitness()) ? temp : bestIndividual;
            }
        }

        return bestIndividual;
    }
}
