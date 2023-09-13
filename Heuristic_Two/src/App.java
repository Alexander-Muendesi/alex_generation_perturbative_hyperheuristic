import java.util.Random;

import GE.Chromosome;
import GE.GrammaticalEvolution;
import constructor_classes.Solutions;
import constructor_classes.Timetable;
import data_classes.DataReader;

public class App {
    public static void main(String[] args) throws Exception {
        //TODO: the major problem with add and delete is that the delete operator is monopolizing the chromosomes and deleting most elements
        Random random = new Random(0);
        DataReader dataReader = new DataReader(1);
        int maxCodons = 30;
        int minCodons = 5;
        int tournamentSize = 4;
        int populationSize = 50;
        double mutationRate = 0.8;
        double crossoverRate = 0.2;
        int maxGenerations = 2000;

        GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, maxGenerations, dataReader);
        ge.execute();            
    }

    public static void test(){
        int maxCodons = 20, minCodons = 2 , numCodons = 15;
        Random random = new Random(0);
        boolean flag = true;

        DataReader datareader = new DataReader(1);
        datareader.readFile();

        Solutions solution = new Solutions(datareader, random);

        Timetable timetable = new Timetable(solution.generateSolution(), datareader, random);
        Chromosome c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);
        c = new Chromosome(maxCodons, minCodons, random, flag, numCodons, timetable);

        if(c.partiallyEvaluateIndividual()){
            c.evaluateIndividual();
            c.printFitness();
        }
    }
}
