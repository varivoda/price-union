package varivoda;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static varivoda.PriceGenerator.generate;

/**
 * Просто пасекает время алгоритма. Было интересно как на разных данных отработает
 */
public class PerformanceTest {

    private Collection<Price> curPrices = new LinkedList<>();
    private Collection<Price> newPrices = new LinkedList<>();

    @Test
    public void testTime() {
        curPrices = generate(10000);
        newPrices = generate(10000);

        long start = System.currentTimeMillis();

        Collection<Price> prices = PriceHelper.unionPrices(curPrices, newPrices);

        System.out.println("Full time = " + (System.currentTimeMillis() - start));
        System.out.println("Size = " + prices.size());
        System.out.println();

    }
}
