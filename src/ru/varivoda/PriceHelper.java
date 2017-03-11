package ru.varivoda;

import java.util.*;

/**
 * Created by ivan on 09.03.17.
 */
public class PriceHelper {

    public static Collection<Price> unionPrices(Collection<Price> curPrices, Collection<Price> newPrices) {


        // Мапируем по соответсвию : productCode, number, depart - Price
        Map<Price.Index, List<Price>> curPricesMap = new HashMap<>();
        Price.Index curIndex;
        for (Price curPrice : curPrices) {
            curIndex = curPrice.getIndex();
            if (!curPricesMap.containsKey(curIndex)) {
                curPricesMap.put(curIndex, new LinkedList<>(Collections.singletonList(curPrice)));
            } else {
                curPricesMap.get(curIndex).add(curPrice);
            }
        }

        Map<Price.Index, List<Price>> newPricesMap = new HashMap<>();
        for (Price newPrice : newPrices) {
            curIndex = newPrice.getIndex();
            if (!newPricesMap.containsKey(curIndex)) {
                newPricesMap.put(curIndex, new LinkedList<>(Collections.singletonList(newPrice)));
            } else {
                newPricesMap.get(curIndex).add(newPrice);
            }
        }

        return unionMapPrices(curPricesMap, newPricesMap);
    }

    private static Collection<Price> unionMapPrices(Map<Price.Index, List<Price>> curPricesMap, Map<Price.Index, List<Price>> newPricesMap) {
        Collection<Price> result = new LinkedList<>();

        for (Map.Entry<Price.Index, List<Price>> entry : newPricesMap.entrySet()) {
            if (!curPricesMap.containsKey(entry.getKey())) {
                result.addAll(entry.getValue());
            } else {
                result.addAll(unionPricesWithSameIndex(curPricesMap.get(entry.getKey()), entry.getValue()));
            }
        }

        return result;
    }

    private static Collection<Price> unionPricesWithSameIndex(Collection<Price> curPrices, Collection<Price> newPrices) {

        Collection<Price> pricesAfterUnion = new LinkedList<>();

        for (Price newPrice : newPrices) {
            for (Price curPrice : curPrices) {
                pricesAfterUnion.clear();
                curPrices.remove(curPrice); // Удаляем обработанную цену
                pricesAfterUnion.addAll(crossPrices(curPrice, newPrice));

            }
            curPrices.add(newPrice);
            curPrices.addAll(pricesAfterUnion);
        }
        return curPrices;
    }

    /**
     * Пересечение цен по временным интервалам.
     * newPrice может изменяться
     * @param curPrice
     * @param newPrice
     * @return
     */
    private static Collection<Price> crossPrices(Price curPrice, Price newPrice) {

        Collection<Price> result = new LinkedList<>();

        // Если цены одинаковые и пересечение есть
        if (curPrice.getValue() == newPrice.getValue() && isCrossing(curPrice, newPrice)) {

                    /* Не совсем понятно написано в задании
                    "если значения цен одинаковы, период действия имеющейся цены увеличивается согласно периоду новой цены;"
                    Как я понял из задания при условии равенства стоимости берем больший интервал */

            // Заменили интервал у новой цены
            newPrice.setBegin(newPrice.getBegin().before(curPrice.getBegin()) ? newPrice.getBegin() : curPrice.getBegin());
            newPrice.setEnd(newPrice.getEnd().after(curPrice.getEnd()) ? newPrice.getEnd() : curPrice.getEnd());
            return result; // пустой список
        }


        // интервалы не пересекаются
        if (!isCrossing(curPrice, newPrice)) {
            result.add(curPrice);
            return result;
        }

        // случай (  [ )  ] , где () - временной интервал старой цены, [] - временной интервал новой цены
        if (curPrice.getBegin().before(newPrice.getBegin()) && curPrice.getEnd().before(newPrice.getEnd())) {
            curPrice.setEnd(newPrice.getBegin());
            result.add(curPrice);
            return result;
        }

        // случай [  ( ]  ) , где () - временной интервал старой цены, [] - временной интервал новой цены
        if (curPrice.getBegin().after(newPrice.getBegin()) && curPrice.getEnd().after(newPrice.getBegin())) {
            curPrice.setBegin(newPrice.getEnd());
            result.add(curPrice);
            return result;
        }

        // случай ( [ ] ) , где () - временной интервал старой цены, [] - временной интервал новой цены
        if (curPrice.getBegin().before(newPrice.getBegin()) && curPrice.getEnd().after(newPrice.getEnd())) {
            Price thirdPrice = new Price(); // Цена для третьего интервала
            thirdPrice.setBegin(newPrice.getEnd());
            thirdPrice.setEnd(curPrice.getEnd());
            thirdPrice.setDepart(curPrice.getDepart());
            thirdPrice.setNumber(curPrice.getNumber());
            thirdPrice.setProductCode(curPrice.getProductCode());
            thirdPrice.setValue(curPrice.getValue());
            thirdPrice.setId(new Random().nextLong()); // Новый индекс. В задании ничего не сказано про него. Поэтому так)

            curPrice.setEnd(newPrice.getBegin());
            result.add(curPrice);
            result.add(thirdPrice);
            return result;
        }

        // остался случай [ () ] , где () - временной интервал старой цены, [] - временной интервал новой цены
        return result; // пустой список
    }

    /**
     * Проверка на пересечение цен по временным интервалам.
     */
    private static boolean isCrossing(Price curPrice, Price newPrice) {
        return !(curPrice.getBegin().after(newPrice.getEnd()) && curPrice.getEnd().before(newPrice.getBegin()));
    }
}
