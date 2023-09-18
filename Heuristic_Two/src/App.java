import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import GE.Chromosome;
import GE.GrammaticalEvolution;
import constructor_classes.Solutions;
import constructor_classes.Timetable;
import data_classes.DataReader;

public class App {

    public static long seed = 1694894941884L;
    public static void main(String[] args) throws Exception {
        // //TODO: the major problem with add and delete is that the delete operator is monopolizing the chromosomes and deleting most elements
        // Random random = new Random(2154645);
        // DataReader dataReader = new DataReader(1);
        // int tournamentSize = 5;
        // int minCodons = 8;
        // int maxCodons = 24;
        // int populationSize = 141;
        // double crossoverRate = 0.070625;
        // double mutationRate = 1.0 - crossoverRate;

        // GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
        // ge.execute();

        // parameterTuning();
        int counter = 0;
        while(counter < 1000){
            long startTime = System.currentTimeMillis();
            executeRun();
            long endTime = System.currentTimeMillis();
            double executionTime = (endTime - startTime) / 60000.0;
            System.out.println("Execution Time: " + executionTime + "\n");
            counter++;
            seed = System.currentTimeMillis();
        }
    }

    public static void executeRun(){
        System.out.println("Seed: " + seed);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Future<?>> fs = new ArrayList<>();
        
        fs.add(executorService.submit(()->execute1()));
        fs.add(executorService.submit(()->execute3()));
        fs.add(executorService.submit(()->execute4()));
        fs.add(executorService.submit(()->execute11()));
        fs.add(executorService.submit(()->execute13()));
        fs.add(executorService.submit(()->execute14()));
        fs.add(executorService.submit(()->execute15()));
        fs.add(executorService.submit(()->execute18()));

        try{
            for(Future<?> f: fs)
                f.get();
            fs.clear();
            executorService.shutdown();
            // scanner.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //best values  = count8: 5.4375 8.5 24.25 141.25 0.070625
    public static void parameterTuning(){
        SobolReader s = new SobolReader();
        Double []params = null;
        s.getParams();//skip the line with 0's
        int count = 0;
        while((params = s.getParams()) != null){
            if(count <= 246){
                System.out.println("Count: " + count);
    
                for(double d: params)
                    System.out.print(d + " ");
                System.out.println();
    
                Random random = new Random(2154645);
                DataReader dataReader = new DataReader(1);
                int tournamentSize = params[0].intValue();
                int minCodons = params[1].intValue();
                int maxCodons = params[2].intValue();
                int populationSize = params[3].intValue();
                double crossoverRate = params[4];
                double mutationRate = 1.0 - crossoverRate;
    
                GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
                ge.execute();
    
                System.gc();
            }
            count++;
        }
    }

    public static void execute1(){
        Random random = new Random(seed);
        DataReader dataReader = new DataReader(1);
        int tournamentSize = 5;
        int minCodons = 8;
        int maxCodons = 24;
        int populationSize = 141;
        double crossoverRate = 0.070625;
        double mutationRate = 1.0 - crossoverRate;

        GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
        ge.execute();
    }

    public static void execute3(){
        Random random = new Random(seed);
        DataReader dataReader = new DataReader(3);
        int tournamentSize = 5;
        int minCodons = 8;
        int maxCodons = 24;
        int populationSize = 141;
        double crossoverRate = 0.070625;
        double mutationRate = 1.0 - crossoverRate;

        GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
        ge.execute();
    }

    public static void execute4(){
        Random random = new Random(seed);
        DataReader dataReader = new DataReader(4);
        int tournamentSize = 5;
        int minCodons = 8;
        int maxCodons = 24;
        int populationSize = 141;
        double crossoverRate = 0.070625;
        double mutationRate = 1.0 - crossoverRate;

        GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
        ge.execute();
    }

    public static void execute11(){
        Random random = new Random(seed);
        DataReader dataReader = new DataReader(11);
        int tournamentSize = 5;
        int minCodons = 8;
        int maxCodons = 24;
        int populationSize = 141;
        double crossoverRate = 0.070625;
        double mutationRate = 1.0 - crossoverRate;

        GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
        ge.execute();
    }

    public static void execute13(){
        Random random = new Random(seed);
        DataReader dataReader = new DataReader(13);
        int tournamentSize = 5;
        int minCodons = 8;
        int maxCodons = 24;
        int populationSize = 141;
        double crossoverRate = 0.070625;
        double mutationRate = 1.0 - crossoverRate;

        GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
        ge.execute();
    }

    public static void execute14(){
        Random random = new Random(seed);
        DataReader dataReader = new DataReader(14);
        int tournamentSize = 5;
        int minCodons = 8;
        int maxCodons = 24;
        int populationSize = 141;
        double crossoverRate = 0.070625;
        double mutationRate = 1.0 - crossoverRate;

        GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
        ge.execute();
    }

    public static void execute15(){
        Random random = new Random(seed);
        DataReader dataReader = new DataReader(15);
        int tournamentSize = 5;
        int minCodons = 8;
        int maxCodons = 24;
        int populationSize = 141;
        double crossoverRate = 0.070625;
        double mutationRate = 1.0 - crossoverRate;

        GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
        ge.execute();
    }

    public static void execute18(){
        Random random = new Random(seed);
        DataReader dataReader = new DataReader(18);
        int tournamentSize = 5;
        int minCodons = 8;
        int maxCodons = 24;
        int populationSize = 141;
        double crossoverRate = 0.070625;
        double mutationRate = 1.0 - crossoverRate;

        GrammaticalEvolution ge = new GrammaticalEvolution(random, maxCodons, minCodons, tournamentSize, populationSize, mutationRate, crossoverRate, 2000, dataReader);
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
