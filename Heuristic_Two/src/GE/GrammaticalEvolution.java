package GE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GrammaticalEvolution {
    private Map<Integer, Chromosome> population;
    private Random random;
    private int tournamentSize, populationSize, maxGenerations;
    private int maxCodons, minCodons;
    private double mutationRate, crossoverRate;

    public GrammaticalEvolution(Random random, int maxCodons, int minCodons, int tournamentSize, int populationSize,
                                    double mutationRate, double crossoverRate, int maxGenerations){
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
     * This method creates a new population using the generational approach.
     */
    public void generateNewPopulation(){
        Map<Integer, Chromosome> newPopulation = new HashMap<Integer,Chromosome>();
        int numCrossoverIndividuals = (int) (this.crossoverRate * this.populationSize);
        int numMutationIndividuals = this.populationSize - numCrossoverIndividuals;
        int counter = 0;

        while(newPopulation.size() != this.populationSize){
            if(numCrossoverIndividuals > 0 && numMutationIndividuals > 0){
                //randomly create an individual from mutation or crossover
                if(this.random.nextInt(2) == 0){
                    Chromosome parentOne = tournamentSelection();
                    Chromosome parentTwo = tournamentSelection();

                    parentTwo = parentOne.singlePointCrossover(parentTwo);
                    newPopulation.put(counter++, parentOne);
                    newPopulation.put(counter++, parentTwo);

                    numCrossoverIndividuals -=2;
                }
                else{//perform mutation
                    Chromosome parentOne = tournamentSelection();
                    parentOne.mutate();
                    newPopulation.put(counter++,parentOne);
                    numMutationIndividuals--;
                }
            }
            else if(numCrossoverIndividuals > 0){
                Chromosome parentOne = tournamentSelection();
                Chromosome parentTwo = tournamentSelection();

                parentTwo = parentOne.singlePointCrossover(parentTwo);
                newPopulation.put(counter++, parentOne);
                newPopulation.put(counter++, parentTwo);

                numCrossoverIndividuals -= 2;
            }
            else if(numMutationIndividuals > 0){
                Chromosome parentOne = tournamentSelection();
                parentOne.mutate();

                newPopulation.put(counter++,parentOne);
                numMutationIndividuals--;
            }
        }

        this.population = newPopulation;
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
     * @return Copy of the fittest individual
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

        return bestIndividual.copy();
    }
}
