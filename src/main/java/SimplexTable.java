import java.util.LinkedList;
import java.util.Map;

/**
 * Created by tori on 009 09.12.16.
 */
public class SimplexTable {
    public String[] basis;
    public Double[][] table; // размерность таблицы определяется количеством ограничений(строки) и переменных(столбцы) + строка выхода
    public LinkedList<String> valuesName;
    public Map<String, Double> aimFunk;

    public void changeBasis(String name, Integer number){
        basis[number] = name;
        Integer mainColumn = valuesName.indexOf(name);

        //меняем ведущую строку
        Double elToDivide = table[number][mainColumn];
        for (int column = 1; column < table[0].length; column++)
            table[number][column] =table[number][column]/elToDivide;

        //перестраиваем симплекс таблицу относительно ведущей строки
        for (int string = 0; string < table.length - 1; string++) {
            if(string != number) {
                Double takeAwayMulti = table[string][mainColumn];
                for (int column = 1; column < table[0].length; column++)
                    table[string][column] = table[string][column] - table[number][column]*takeAwayMulti;
            }
        }

        // изменяем значения коеф для нововведеной базисной переменной
        table[number][0] = aimFunk.get(name);

        for (int column = 1; column < table[0].length; column++)
                table[table.length - 1][column] = 0.0;

        // подсчет оценок
        for (int column = 1; column < table[0].length; column++) {
            for (int string = 0; string < table.length - 1; string++) {
                table[table.length - 1][column] += table[string][column] * table[string][0];
            }
            table[table.length - 1][column] -=  aimFunk.get(valuesName.get(column));
        }
    }

    public String mainColumnMax(){
        Double minDelta = 0.0;
        String mainColumn = "";
        for(int column = 2; column < table[0].length; column++)
            if(minDelta > table[table.length - 1][column]){
                minDelta = table[table.length - 1][column];
                mainColumn = valuesName.get(column);
            }
        return mainColumn;
    }

    public Integer elementInBasisToChange(String mainColumn){
        Integer mainCol = valuesName.indexOf(mainColumn);
        Double min = Double.MAX_VALUE;
        Integer result = -1;
        for (int string = 0; string < table.length - 1; string++){
            if((table[string][mainCol] > 0) && (min > Math.abs((double)(table[string][1]/table[string][mainCol]))) ){
                result = string;
                min = Math.abs((double)(table[string][1]/table[string][mainCol]));
            }
        }
        return result;
    }
    public String mainColumnMin() {
        Double maxDelta = 0.0;
        String mainColumn = "";
        for (int column = 2; column < table[0].length; column++)
            if (maxDelta < table[table.length - 1][column]) {
                maxDelta = table[table.length - 1][column];
                mainColumn = valuesName.get(column);
            }
        return mainColumn;
    }
}