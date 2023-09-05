package GE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrammarRules {
    private Map<String,Map<Integer,List<String>>> grammar;

    public GrammarRules(){
        grammar = new HashMap<String,Map<Integer,List<String>>>();

        List<String> vals = new ArrayList<String>();
        Map<Integer,List<String>> temp = new HashMap<Integer,List<String>>();

        //<Start>
        vals.add("<accept>"); vals.add("<heuristic>");
        temp.put(0, vals);
        grammar.put("<start>",temp);

        //<accept>
        temp.clear(); vals.clear();
        vals.add("IO"); 
        temp.put(0,vals); vals.clear();

        vals.add("AM");
        temp.put(1,vals);
        grammar.put("<accept>",temp);

        //<heuristic>
        temp.clear();vals.clear();
        vals.add("swap"); vals.add("<n>"); vals.add("<compSel>");
        temp.put(0, vals); vals.clear();

        vals.add("move"); vals.add("<n>"); vals.add("<compSel>");
        temp.put(1, vals); vals.clear();

        vals.add("add"); vals.add("<n>"); vals.add("<compSel>");
        temp.put(2,vals); vals.clear();

        vals.add("delete"); vals.add("<n>"); vals.add("<compSel>");
        temp.put(3,vals); vals.clear();

        vals.add("shuffle"); vals.add("<n>"); vals.add("<compSel>");
        temp.put(4, vals); vals.clear();

        vals.add("<heuristic>"); vals.add("<cop>"); vals.add("<heuristic>");
        temp.put(5, vals); vals.clear();

        vals.add("<heuristic>");
        temp.put(6, vals); vals.clear();

        vals.add("if"); vals.add("<cond>"); vals.add("<heuristic>"); vals.add("<heuristic>");
        temp.put(7,vals); vals.clear();

        grammar.put("<heuristic>", temp); temp.clear();

        //<cond>
        vals.add("<rop>"); vals.add("<h_value>"); vals.add("<h_value>");
        temp.put(0, vals); vals.clear();

        vals.add("if"); vals.add("<cond>"); vals.add("<cond>"); vals.add("<cond>");
        temp.put(1, vals); vals.clear();

        grammar.put("<cond>", temp); temp.clear();

        //h_value
        vals.add("prevFitness");
        temp.put(0,vals); vals.clear();

        vals.add("currFitness");
        temp.put(1, vals); vals.clear();

        vals.add("diffFitness");
        temp.put(2, vals); vals.clear();

        vals.add("currIteration");
        temp.put(3, vals); vals.clear();

        vals.add("totalIterations");
        temp.put(4,vals); vals.clear();

        grammar.put("<h_value>", temp); temp.clear();

        //<compSel>
        vals.add("lowestCost");
        temp.put(0,vals); vals.clear();

        vals.add("highestCost");
        temp.put(1, vals); vals.clear();

        vals.add("smallestSize");
        temp.put(2, vals); vals.clear();

        vals.add("largestSize");
        temp.put(3, vals); vals.clear();

        vals.add("random");
        temp.put(4, vals); vals.clear();

        vals.add("if"); vals.add("<prob>"); vals.add("<compSel>"); vals.add("<compSel>");
        temp.put(5, vals); vals.clear();

        grammar.put("<compSel>", temp); temp.clear();

        //<comp>
        vals.add("lecture");
        temp.put(0, vals); vals.clear();

        vals.add("period");
        temp.put(1, vals); vals.clear();

        vals.add("room");
        temp.put(2, vals); vals.clear();

        grammar.put("<comp>", temp); temp.clear();

        //cop
        vals.add("union");
        temp.put(0,vals); vals.clear();

        vals.add("rGradient");
        temp.put(1, vals); vals.clear();

        grammar.put("<cop>", temp); temp.clear();

        //rop
        vals.add("<=");
        temp.put(0,vals); vals.clear();

        vals.add("<");
        temp.put(1, vals); vals.clear();

        vals.add(">");
        temp.put(2,vals); vals.clear();

        vals.add(">=");
        temp.put(3,vals);

        grammar.put("<rop>",temp); temp.clear();

        //prob
        vals.add("25");
        temp.put(0,vals); vals.clear();

        vals.add("50");
        temp.put(1, vals); vals.clear();

        vals.add("75");
        temp.put(2,vals);

        grammar.put("<prob>", temp); temp.clear();

        //<n>

        vals.add("1");
        temp.put(0,vals); vals.clear();

        vals.add("2");
        temp.put(1, vals); vals.clear();

        vals.add("3");
        temp.put(2, vals); vals.clear();

        vals.add("4");
        temp.put(3, vals); vals.clear();

        vals.add("5");
        temp.put(4, vals); vals.clear();

        vals.add("6");
        temp.put(5, vals); vals.clear();

        vals.add("7");
        temp.put(6,vals); vals.clear();

        vals.add("8");
        temp.put(7, vals); vals.clear();

        vals.add("9");
        temp.put(8, vals); vals.clear();

        vals.add("10");
        temp.put(9,vals); vals.clear();

        vals.add("<n>");
        temp.put(10, vals); vals.clear();

        vals.add("<n>");
        vals.add("<n>");
        temp.put(11, vals); vals.clear();

        grammar.put("<n>", temp); temp.clear();
    }

    /**
     * 
     * @param key
     * @return The list of rules associated with the key.
     */
    public Map<Integer,List<String>> getRhs(String key){
        return this.grammar.get(key);
    }
}
