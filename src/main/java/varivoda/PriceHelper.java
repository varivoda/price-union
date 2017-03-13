package varivoda;

import java.util.*;

/**
 * Статический хелпер для обращения с ценами
 */
public class PriceHelper {


    private PriceHelper() { // У статического хелпера не может быть экземпляров
    }

    /**
     * Объединение цен по заявленным правилам.
     */
    public static Collection<Price> unionPrices(Collection<Price> curPrices, Collection<Price> newPrices) {
        return unionPrices(getIndexListMap(curPrices), getIndexListMap(newPrices));
    }

    /**
     * Возвращает карту со списками цен (копиями объектов) с одинаковыми <productCode, number, depart> = Index
     */
    private static Map<Price.Index, List<Price>> getIndexListMap(Collection<Price> prices) {

        Price.Index curIndex;
        Map<Price.Index, List<Price>> result = new HashMap<>();
        for (Price curPrice : prices) {
            curIndex = curPrice.getIndex();
            if (!result.containsKey(curIndex)) {
                result.put(curIndex, new LinkedList<>(Collections.singletonList(new Price(curPrice))));
            } else {
                result.get(curIndex).add(new Price(curPrice));
            }
        }
        return result;
    }

    /**
     * Объединение цен в картах
     */
    private static Collection<Price> unionPrices(Map<Price.Index, List<Price>> curPricesMap, Map<Price.Index, List<Price>> newPricesMap) {

        Collection<Price> result = new LinkedList<>();

        // Добавляем в результат текущие цены, для которых нет обновлений
        for (Map.Entry<Price.Index, List<Price>> entry : curPricesMap.entrySet()) {
            if (!newPricesMap.containsKey(entry.getKey())) {
                result.addAll(entry.getValue());
            }
        }

        // проверяем новые цены
        for (Map.Entry<Price.Index, List<Price>> entry : newPricesMap.entrySet()) {
            if (!curPricesMap.containsKey(entry.getKey())) {
                result.addAll(entry.getValue());
            } else {
                result.addAll(unionPricesWithSameIndex(curPricesMap.get(entry.getKey()), entry.getValue()));
            }
        }


        return result;
    }

    /**
     * Объединяет цены с одинаковыми индексами  (productCode, number, depart)
     */
    private static Collection<Price> unionPricesWithSameIndex(Collection<Price> curPrices, Collection<Price> newPrices) {

        Collection<Price> newPriceLevel = new LinkedList<>(); // хранит цены с новыми интервалами
        Collection<Price> currentPriceLevel = curPrices;

        for (Price newPrice : newPrices) {

            for (Price curPrice : currentPriceLevel) {

                // Если цены одинаковые и пересечение есть
                if (curPrice.getValue() == newPrice.getValue() && isCrossing(curPrice, newPrice)) {

                    /* Не совсем понятно написано в задании
                    "если значения цен одинаковы, период действия имеющейся цены увеличивается согласно периоду новой цены;"
                    Как я понял из задания при условии равенства стоимости берем больший интервал */

                    // Заменили интервал у новой цены на максимальный
                    newPrice.setBegin(newPrice.getBegin().before(curPrice.getBegin()) ? newPrice.getBegin() : curPrice.getBegin());
                    newPrice.setEnd(newPrice.getEnd().after(curPrice.getEnd()) ? newPrice.getEnd() : curPrice.getEnd());
                    continue;
                }

                // интервалы не пересекаются
                if (!isCrossing(curPrice, newPrice)) {
                    newPriceLevel.add(curPrice);
                    continue;
                }

                // случай (  [ )  ] , где () - временной интервал старой цены, [] - временной интервал новой цены
                if (curPrice.getBegin().before(newPrice.getBegin()) && curPrice.getEnd().before(newPrice.getEnd())) {
                    curPrice.setEnd(newPrice.getBegin());
                    newPriceLevel.add(curPrice);
                    continue;
                }

                // случай [  ( ]  ) , где () - временной интервал старой цены, [] - временной интервал новой цены
                if (curPrice.getBegin().after(newPrice.getBegin()) && curPrice.getEnd().after(newPrice.getBegin())) {
                    curPrice.setBegin(newPrice.getEnd());
                    newPriceLevel.add(curPrice);
                    continue;
                }

                // случай ( [ ] ) , где () - временной интервал старой цены, [] - временной интервал новой цены
                if (curPrice.getBegin().before(newPrice.getBegin()) && curPrice.getEnd().after(newPrice.getEnd())) {
                    Price thirdPrice = new Price();
                    thirdPrice.setBegin(newPrice.getEnd());
                    thirdPrice.setEnd(curPrice.getEnd());
                    thirdPrice.setDepart(curPrice.getDepart());
                    thirdPrice.setNumber(curPrice.getNumber());
                    thirdPrice.setProductCode(curPrice.getProductCode());
                    thirdPrice.setValue(curPrice.getValue());
                    thirdPrice.setId(new Random().nextLong()); // Новый индекс. В задании ничего не сказано про него. Поэтому так)

                    curPrice.setEnd(newPrice.getBegin());
                    newPriceLevel.add(curPrice);
                    newPriceLevel.add(thirdPrice);
                    continue;
                }

                // остался случай [ () ] , где () - временной интервал старой цены, [] - временной интервал новой цены
                newPriceLevel.add(curPrice);

            }
            currentPriceLevel.clear();
            currentPriceLevel.addAll(newPriceLevel);
            currentPriceLevel.add(newPrice);
            newPriceLevel.clear();
        }
        return currentPriceLevel;
    }

    /**
     * Проверка на пересечение цен по временным интервалам.
     */
    private static boolean isCrossing(Price curPrice, Price newPrice) {
        return !(curPrice.getBegin().after(newPrice.getEnd()) || curPrice.getEnd().before(newPrice.getBegin()));
    }

}
