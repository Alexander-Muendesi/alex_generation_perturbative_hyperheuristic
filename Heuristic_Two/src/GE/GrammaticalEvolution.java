package GE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import constructor_classes.Solutions;
import constructor_classes.Timetable;
import data_classes.DataReader;

public class GrammaticalEvolution {
    private Map<Integer, Chromosome> population;
    private Random random;
    private int tournamentSize, populationSize, maxGenerations;
    private int maxCodons, minCodons;
    private double mutationRate, crossoverRate;
    private Timetable timetable;

    public GrammaticalEvolution(Random random, int maxCodons, int minCodons, int tournamentSize, int populationSize,
                                    double mutationRate, double crossoverRate, int maxGenerations, DataReader dataReader){
        this.population = new HashMap<Integer,Chromosome>();
        this.random = random;
        this.tournamentSize = tournamentSize;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;

        Solutions solution = new Solutions(dataReader, random);
        this.timetable = new Timetable(solution.generateSolution(), dataReader, random);
        this.timetable.calculateFitness();
    }

    public void execute(){
        generateInitialPopulation();
        int numIterations = 0;
        Chromosome bestIndividual = null;

        while(numIterations < maxGenerations){
            bestIndividual = evaluateFitness();//evaluate the population

            numIterations++;
            generateNewPopulation(numIterations);
            bestIndividual.printFitness();
        }
        bestIndividual.printFitness();
    }

    /**
     * Finds the chromosome that resulted in the best fitness
     * @return Chromosome representing the best individual
     */
    public Chromosome evaluateFitness(){
        Chromosome best = this.population.get(0);
        best.evaluateIndividual();

        for(int i=0; i<this.population.size(); i++){
            Chromosome temp = this.population.get(i);
            temp.evaluateIndividual();

            if(temp.getFitness() < best.getFitness())
                best = temp;
        }

        return best;
    }

    /**
     * This method creates a new population using the generational approach.
     * @param currIteration Represents the current generation. Used for currIteration variable in Chromosome
     */
    public void generateNewPopulation(int currIteration){
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

                    if(parentOne.partiallyEvaluateIndividual()){//make sure the derivation tree can be created without infinite recursion happening
                        newPopulation.put(counter++, parentOne);
                        numCrossoverIndividuals--;
                    }
                    
                    if(parentTwo.partiallyEvaluateIndividual()){//make sure the derivation tree can be created without infinite recursion happening
                        newPopulation.put(counter++, parentTwo);
                        numCrossoverIndividuals--;
                    }

                }
                else{//perform mutation
                    Chromosome parentOne = tournamentSelection();
                    parentOne.mutate();

                    if(parentOne.partiallyEvaluateIndividual()){//make sure the derivation tree can be created without infinite recursion happening
                        newPopulation.put(counter++,parentOne);
                        numMutationIndividuals--;
                    }
                }
            }
            else if(numCrossoverIndividuals > 0){
                Chromosome parentOne = tournamentSelection();
                Chromosome parentTwo = tournamentSelection();

                parentTwo = parentOne.singlePointCrossover(parentTwo);
                if(parentOne.partiallyEvaluateIndividual()){
                    newPopulation.put(counter++, parentOne);
                    numCrossoverIndividuals--;
                }

                if(parentTwo.partiallyEvaluateIndividual()){
                    newPopulation.put(counter++, parentTwo);
                    numCrossoverIndividuals--;
                }
            }
            else if(numMutationIndividuals > 0){
                Chromosome parentOne = tournamentSelection();
                parentOne.mutate();

                if(parentOne.partiallyEvaluateIndividual()){
                    newPopulation.put(counter++,parentOne);
                    numMutationIndividuals--;
                }
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
            Chromosome c = new Chromosome(this.maxCodons, this.minCodons, this.random, true, codonsPerIndividual, timetable.copy());
            c.totalIterations = this.maxGenerations;
            c.prevFitness = -1;
            c.currFitness = this.timetable.fitness;

            if(c.partiallyEvaluateIndividual()){
                this.population.put(i, c);
                codonsPerIndividual = (codonsPerIndividual - minCodons + 1) % codonRange + minCodons;
            }
            else
                --i;
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
