package kurs;

import java.util.LinkedList;

import static util.PrettifyOutUtils.printSimplexTable;
import static util.PrettifyOutUtils.printTableVariableValues;

/**
 * Created by tori on 010 10.12.16.
 */
public class SimplexMaxMin {


    public static Double[] result = new Double[9];

    public static Double findMax(Double[] aimFunction, Double[][] restrictionsCoefs, LinkedList<String> inequality) {
        SimplexTable simple = new SimplexTable(aimFunction, restrictionsCoefs, inequality, -1);
        printSimplexTable(simple);
        String element = simple.mainColumnMax();
        while (!element.equals("")) {
            Integer base = simple.elementInBasisToChange(element);
            simple.changeBasis(element, base);
            element = simple.mainColumnMax();
            printSimplexTable(simple);
        }
        printTableVariableValues(simple);

        System.out.println("max = " + simple.delta[0][1]);
        return simple.delta[0][1];
    }


    public static Double findMin(Double[] aimFunction, Double[][] restrictionsCoefs, LinkedList<String> inequality) {
        SimplexTable simple = new SimplexTable(aimFunction, restrictionsCoefs, inequality, 1);
        printSimplexTable(simple);
        String element = simple.mainColumnMin();
        while (!element.equals("")) {
            Integer base = simple.elementInBasisToChange(element);
            simple.changeBasis(element, base);
            element = simple.mainColumnMin();
            printSimplexTable(simple);
        }
        printTableVariableValues(simple);
        for (int index = 1 ; index < 10; index++) {
            result[index - 1] = 0.0;
            for (int basisindex = 0; basisindex < simple.basis.length; basisindex++)
                if (simple.basis[basisindex].equals("x" + index)) {
                    result[index - 1] = simple.table[basisindex][1];
                    break;
                }
        }
        System.out.println("min = " + simple.delta[0][1]);
        return simple.delta[0][1];
    }



}
