import java.util.Random;

import GE.Chromosome;
import GE.GrammaticalEvolution;
import constructor_classes.Solutions;
import constructor_classes.Timetable;
import data_classes.DataReader;

public class App {
    public static void main(String[] args) throws Exception {
        Random random = new Random(0);
        DataReader dataReader = new DataReader(1);
        int maxCodons = 20;
        int minCodons = 2;
        int tournamentSize = 4;
        int populationSize = 50;
        double mutationRate = 0.5;
        double crossoverRate = 0.5;
        int maxGenerations = 100;

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
