package GE;

import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import constructor_classes.Solutions;
public class Chromosome {
    private List<Codon> chromosome;
    private final int maxCodons;
    private final int minCodons;
    private final Random random;
    private int[] fitness; //will hold the hard constraint cost at index 0 and the soft constraint cost at index 1
    private int numCodons;
    private Node root;
    private GrammarRules grammar;
    private int codonCounter = 0;
    int treeCounter = 0;
    public int  prevFitness = 0;
    public int currFitness = 1;
    public int diffFitness = 2;
    public int currIteration = 3;
    public int totalIterations = 4;

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
        this.root = null;
        grammar = new GrammarRules();

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
     * @return true means the individual was successfully created, false means the individuals derivation tree resulted in infinite recursion
     */
    public boolean evaluateIndividual(){
        treeCounter = 0;
        try{
            this.root = generateDerivationTree("<start>");
            this.root.setLevel(0);
        }
        catch(Exception e){
            return false;
        }
        

        System.out.println(root.toString());
        System.out.println("\n\n" + evaluateDerivationTree(this.root));
        //TODO: have to update the fitness value when done here

        return true;
    }

    /**
     * This function applies the derivation tree to the to a timetable. Uses a depth first approach to evaluate the tree
     * @param node
     */
    public String evaluateDerivationTree(Node node){
        String val = node.getValue();

        if(val.equals("if")){//DONE
            return "";//this will never have any children so just return empty string for now
        }     
        else if(node instanceof TerminalNode){
            if(node.getValue().equals("prevFitness"))
                return String.valueOf(this.prevFitness);
            else if(node.getValue().equals("currFitness"))
                return String.valueOf(this.currFitness);
            else if(node.getValue().equals("currIteration"))
                return String.valueOf(this.currIteration);
            else if(node.getValue().equals("totalIterations"))
                return String.valueOf(this.totalIterations);
            else if(node.getValue().equals("diffFitness"))
                return String.valueOf(this.diffFitness);

            return node.getValue();
        }
        else if(val.equals("<accept>")){
            return evaluateDerivationTree(node.getChildren().get(0));
        }
        else if(val.equals("<heuristic>")){//DONE
            List<String> vals = new ArrayList<String>();

            for(Node n: node.getChildren())
                vals.add(evaluateDerivationTree(n));

            if(vals.get(0).equals("")){//means the if statement was chosen
                if(vals.get(1).equals("TRUE")){
                    return vals.get(2);
                }
                else if(vals.get(1).equals("FALSE")){
                    return vals.get(3);
                }
            }
            else{//anything but the if statement was chosen
                StringBuilder sb = new StringBuilder();
                int counter = 0;

                for(String v: vals){
                    if(counter < vals.size()-1)
                        sb.append(v).append(" ");
                    else
                        sb.append(v);

                    counter++;
                }
                return sb.toString();
            }
        }
        else if(val.equals("<cond>")){//DONE
            List<String> cond = new ArrayList<String>();
            int ifCounter = 0;

            for(Node n : node.getChildren()){
                cond.add(evaluateDerivationTree(n));
            }

            if(cond.get(ifCounter).equals(""))
                ifCounter++;

            if(cond.get(ifCounter).equals("<=")){
                int lhs = Integer.parseInt(cond.get(ifCounter + 1));
                int rhs = Integer.parseInt(cond.get(ifCounter + 2));

                return (lhs <= rhs) ? "TRUE" : "FALSE";
            }
            else if(cond.get(ifCounter).equals("<")){
                int lhs = Integer.parseInt(cond.get(ifCounter + 1));
                int rhs = Integer.parseInt(cond.get(ifCounter + 2));

                return (lhs < rhs) ? "TRUE" : "FALSE";
            }
            else if(cond.get(ifCounter).equals(">")){
                int lhs = Integer.parseInt(cond.get(ifCounter + 1));
                int rhs = Integer.parseInt(cond.get(ifCounter + 2));

                return (lhs > rhs) ? "TRUE" : "FALSE";
            }
            else if(cond.get(ifCounter).equals(">=")){
                int lhs = Integer.parseInt(cond.get(ifCounter + 1));
                int rhs = Integer.parseInt(cond.get(ifCounter + 2));

                return (lhs >= rhs) ? "TRUE" : "FALSE";
            }
            else if(cond.get(ifCounter).equals("TRUE") || cond.get(ifCounter).equals("FALSE"))
                return cond.get(ifCounter);
            else{
                System.out.println("Error in cond, evaluate derivation tree. Value: ");
                System.exit(-1);
                return "";
            }
        }
        else if(val.equals("<compSel>")){//DONE
            List<String> vals = new ArrayList<String>();
            for(Node n: node.getChildren()){
                vals.add(evaluateDerivationTree(n));
            }

            if(vals.size() == 2){
                return vals.get(0) + " " + vals.get(1);
            }
            else if(vals.get(0).equals("")){
                int probability = Integer.parseInt(vals.get(1));

                if(random.nextInt(101) >= probability)
                    return vals.get(2);
                else
                    return vals.get(3);
            }
            else{
                System.out.println("error in compsel, evaluate derivation tree");
                System.exit(-1);
                return "";
            }
        }
        else if(val.equals("<prob>")){//DONE
            return evaluateDerivationTree(node.getChildren().get(0));
        }
        else if(val.equals("<h_value>")){//DONE
            return evaluateDerivationTree(node.getChildren().get(0));
        }
        else if(val.equals("<comp>")){//DONE
            return evaluateDerivationTree(node.getChildren().get(0));
        }
        else if(val.equals("<cop>")){//DONE
            return evaluateDerivationTree(node.getChildren().get(0));
        }
        else if(val.equals("<rop>")){//DONE
            return evaluateDerivationTree(node.getChildren().get(0));
        }
        else if(val.equals("<n>")){//DONE
            if(node.getChildren().size() == 1){//only one child so a number between 1 and 10
                return evaluateDerivationTree(node.getChildren().get(0));
            }
            else{
                StringBuilder sb = new StringBuilder();

                for(Node n: node.getChildren())
                    sb.append(evaluateDerivationTree(n));

                return sb.toString();
            }
        }
        // else if(val.equals("<start>")){
        else{
            List<String> vals = new ArrayList<String>();
            for(Node n: node.getChildren())
                vals.add(evaluateDerivationTree(n));

            int counter = 0;
            StringBuilder result = new StringBuilder();
            for(String elem: vals){
                if(counter == vals.size() -1){
                    result.append(elem);
                }
                else 
                    result.append(elem).append(" ");

                counter++;
            }
            return result.toString();
        }   

        System.out.println("This line should never be reached");
        return "";
    }

    /**
     * Generates the derivation tree using the grammar
     * @param symbol The current symbol in the grammar
     * @return
     * @throws Exception
     */
    public Node generateDerivationTree(String symbol) throws Exception{
        if(treeCounter > 10000){//this will prevent a stackoverflow should the recursion just keep going
            throw new Exception("Recursion did not terminate when generating the derivation tree");
        }
        treeCounter++;
        if(!grammar.containsKey(symbol)){//create a terminal node
            return new TerminalNode(symbol);
        }

        Map<Integer,List<String>> rhs = grammar.getRhs(symbol);
        //select a rule based on the current codon

        List<String> rule = rhs.get(getCodonValue() % rhs.size());

        Node node = new FunctionNode(symbol);
        for(String r: rule){
            Node n = generateDerivationTree(r);
            node.children.add(n);
        }

        return node;

    }

    private int getCodonValue(){
        int val = chromosome.get(codonCounter++).getDenaryValue();
        codonCounter = (codonCounter == chromosome.size())? 0 : codonCounter;
        return val;
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
