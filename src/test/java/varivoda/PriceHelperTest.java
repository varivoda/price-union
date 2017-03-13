package varivoda;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Некоторые тесты для проверки алгоритма объединения
 */
public class PriceHelperTest {

    private List<Price> curPrices = new LinkedList<>();
    private List<Price> newPrices = new LinkedList<>();
    private DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Test
    public void whenCollectionsContainsPricesWithSameValueAndIndexesAndCrossingDateIntervalThenReturnedOnePriceWithUnionInterval() throws ParseException {

        curPrices.add(new Price(1, "122856", 1, 1, formatter.parse("01.01.2013 00:00:00"), formatter.parse("31.01.2013 23:59:59"), 11000));
        newPrices.add(new Price(2, "122856", 1, 1, formatter.parse("20.01.2013 00:00:00"), formatter.parse("20.02.2013 23:59:59"), 11000));

        Collection<Price> resultPrices = PriceHelper.unionPrices(curPrices, newPrices);

        Assert.assertThat(resultPrices.size(), Is.is(1));

        Price expectedPrice = new Price(100, "122856", 1, 1, formatter.parse("01.01.2013 00:00:00"), formatter.parse("20.02.2013 23:59:59"), 11000);
        assertThatCollectionContainsPrice(resultPrices, expectedPrice);
    }

    @Test
    public void whenCollectionsContainsPricesWithSameValueAndIndexesAndNotCrossingDateIntervalThenReturnTwoPriceWithoutDifferences() throws ParseException {

        Price curPrice = new Price(1, "122856", 1, 1, formatter.parse("01.01.2013 00:00:00"), formatter.parse("31.01.2013 23:59:59"), 11000);
        curPrices.add(curPrice);

        Price newPrice = new Price(2, "122856", 1, 1, formatter.parse("01.02.2013 00:00:00"), formatter.parse("20.02.2013 23:59:59"), 11000);
        newPrices.add(newPrice);

        Collection<Price> resultPrices = PriceHelper.unionPrices(curPrices, newPrices);

        Assert.assertThat(resultPrices.size(), Is.is(2));
        assertThatCollectionContainsPrice(resultPrices, curPrice);
        assertThatCollectionContainsPrice(resultPrices, newPrice);
    }

    @Test
    public void whenNewPriceDateIntervalIsIntoCurPriceDateIntervalAndvaluesIsNotEqualsAndIndexesIsEqualsThenNewPriceSeparateCurrentOnThreeIntervalsWithTheirValues() throws ParseException {
        Price curPrice = new Price(1, "122856", 1, 1, formatter.parse("01.01.2013 00:00:00"), formatter.parse("31.01.2013 23:59:59"), 11000);
        curPrices.add(curPrice);

        Price newPrice = new Price(2, "122856", 1, 1, formatter.parse("10.01.2013 00:00:00"), formatter.parse("13.01.2013 23:59:59"), 11001);
        newPrices.add(newPrice);

        Collection<Price> resultPrices = PriceHelper.unionPrices(curPrices, newPrices);

        Assert.assertThat(resultPrices.size(), Is.is(3));

        curPrice.setEnd(formatter.parse("10.01.2013 00:00:00"));
        Price thirdPrice = new Price(2, "122856", 1, 1, formatter.parse("13.01.2013 23:59:59"), formatter.parse("31.01.2013 23:59:59"), 11000);

        assertThatCollectionContainsPrice(resultPrices, curPrice);
        assertThatCollectionContainsPrice(resultPrices, newPrice);
        assertThatCollectionContainsPrice(resultPrices, thirdPrice);
    }

    @Test
    public void whenNewPriceDateIntervalIsIntoCurPriceDateIntervalAndvaluesIsEqualsAndIndexesIsEqualsThenReturnedOnePriceWithUnionDateInterval() throws ParseException {
        Price curPrice = new Price(1, "122856", 1, 1, formatter.parse("01.01.2013 00:00:00"), formatter.parse("31.01.2013 23:59:59"), 11000);
        curPrices.add(curPrice);

        Price newPrice = new Price(2, "122856", 1, 1, formatter.parse("10.01.2013 00:00:00"), formatter.parse("13.01.2013 23:59:59"), 11000);
        newPrices.add(newPrice);

        Collection<Price> resultPrices = PriceHelper.unionPrices(curPrices, newPrices);

        Assert.assertThat(resultPrices.size(), Is.is(1));

        assertThatCollectionContainsPrice(resultPrices, curPrice);
    }

    @Test
    public void whenCurPriceDateIntervalIsIntoNewPriceDateIntervalAndvaluesIsEqualsAndIndexesIsEqualsThenReturnedOnePriceWithUnionDateInterval() throws ParseException {
        Price curPrice = new Price(2, "122856", 1, 1, formatter.parse("10.01.2013 00:00:00"), formatter.parse("13.01.2013 23:59:59"), 11000);
        curPrices.add(curPrice);

        Price newPrice = new Price(1, "122856", 1, 1, formatter.parse("01.01.2013 00:00:00"), formatter.parse("31.01.2013 23:59:59"), 11000);
        newPrices.add(newPrice);

        Collection<Price> resultPrices = PriceHelper.unionPrices(curPrices, newPrices);

        Assert.assertThat(resultPrices.size(), Is.is(1));
        assertThatCollectionContainsPrice(resultPrices, newPrice);
    }

    @Test
    public void whenNewPriceDateIntervalIsCrossedCurPriceDateIntervalAndvaluesIsNotEqualsAndIndexesIsEqualsThenNewPriceSeparateCurrentOnTwooIntervalsWithTheirValues() throws ParseException {
        Price curPrice = new Price(1, "122856", 1, 1, formatter.parse("01.01.2013 00:00:00"), formatter.parse("31.01.2013 23:59:59"), 11000);
        curPrices.add(curPrice);

        Price newPrice = new Price(2, "122856", 1, 1, formatter.parse("10.01.2013 00:00:00"), formatter.parse("20.02.2013 23:59:59"), 11001);
        newPrices.add(newPrice);

        Collection<Price> resultPrices = PriceHelper.unionPrices(curPrices, newPrices);

        Assert.assertThat(resultPrices.size(), Is.is(2));

        curPrice.setEnd(formatter.parse("10.01.2013 00:00:00"));

        assertThatCollectionContainsPrice(resultPrices, curPrice);
        assertThatCollectionContainsPrice(resultPrices, newPrice);

    }

    /**
     * Проверяет содержится ли цена в коллекции без учета id.
     */
    private void assertThatCollectionContainsPrice(Collection<Price> prices, Price price) {

        for (Price curPrice : prices) {
            if (curPrice.getNumber() == price.getNumber() &&
                    curPrice.getDepart() == price.getDepart() &&
                    curPrice.getProductCode().equals(price.getProductCode()) &&
                    curPrice.getValue() == price.getValue() &&
                    curPrice.getBegin().equals(price.getBegin()) &&
                    curPrice.getEnd().equals(price.getEnd())
                    ) {
                return;
            }
        }
        throw new AssertionError(String.format("Цена %s не найдена в коллекции %s", price, prices));
    }

}