package ru.varivoda;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException {

        List<Price> curPrices = new LinkedList<>();
        List<Price> newPrices = new LinkedList<>();
        Price price = new Price();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");


        price.setId(1);
        price.setBegin(formatter.parse("01.01.2013 00:00:00"));
        price.setEnd(formatter.parse("31.01.2013 23:59:59"));
        price.setProductCode(String.valueOf(122856));
        price.setValue(11000);
        price.setDepart(1);
        price.setNumber(1);
        curPrices.add(price);


        price = new Price();
        price.setId(2);
        price.setBegin(formatter.parse("10.01.2013 00:00:00"));
        price.setEnd(formatter.parse("20.01.2013 23:59:59"));
        price.setProductCode(String.valueOf(122856));
        price.setValue(11000);
        price.setDepart(1);
        price.setNumber(2);
        curPrices.add(price);

        price = new Price();
        price.setId(3);
        price.setBegin(formatter.parse("01.01.2013 00:00:00"));
        price.setEnd(formatter.parse("31.01.2013 00:00:00"));
        price.setProductCode(String.valueOf(6654));
        price.setValue(5000);
        price.setDepart(2);
        price.setNumber(1);
        curPrices.add(price);


        // NEW

        price = new Price();
        price.setId(4);
        price.setBegin(formatter.parse("20.01.2013 00:00:00"));
        price.setEnd(formatter.parse("20.02.2013 23:59:59"));
        price.setProductCode(String.valueOf(122856));
        price.setValue(11000);
        price.setDepart(1);
        price.setNumber(1);
        newPrices.add(price);

        price = new Price();
        price.setId(5);
        price.setBegin(formatter.parse("15.01.2013 00:00:00"));
        price.setEnd(formatter.parse("25.01.2013 23:59:59"));
        price.setProductCode(String.valueOf(122856));
        price.setValue(92000);
        price.setDepart(1);
        price.setNumber(2);
        newPrices.add(price);

        price = new Price();
        price.setId(6);
        price.setBegin(formatter.parse("12.01.2013 00:00:00"));
        price.setEnd(formatter.parse("13.01.2013 00:00:00"));
        price.setProductCode(String.valueOf(6654));
        price.setValue(4000);
        price.setDepart(2);
        price.setNumber(1);
        newPrices.add(price);


        System.out.println();
        Collection<Price> prices = PriceHelper.unionPrices(curPrices, newPrices);
        System.out.println();


    }
}
