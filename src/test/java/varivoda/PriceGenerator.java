package varivoda;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 * Генерит цены с захардкодеными параметрами.
 */
public class PriceGenerator {

    private static DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static Random numRandom = new Random(1000L);

    public static Collection<Price> generate(int count) {

        Collection<Price> result = new LinkedList<>();
        Price price;

        for (int i = 0; i < count; i++) {
            price = new Price();

            price.setId(numRandom.nextInt(100000));
            price.setNumber(numRandom.nextInt(10) + 1);
            price.setDepart(numRandom.nextInt(10) + 1);
            price.setValue(numRandom.nextInt(10000) + 10000);
            price.setProductCode(String.valueOf(numRandom.nextInt(1) + 10000));
            price.setBegin(generateDate());
            price.setEnd(generateDate());

            result.add(price);
        }

        return result;

    }

    public static Date generateDate() {
        Date parse = null;
        try {
            parse = formatter.parse(String.format("%02d.%02d.%d %02d:%02d:%02d",
                    numRandom.nextInt(30),
                    numRandom.nextInt(12),
                    numRandom.nextInt(1) + 2013,
                    numRandom.nextInt(24),
                    numRandom.nextInt(60),
                    numRandom.nextInt(60)
            ));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }


}
