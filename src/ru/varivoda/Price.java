package ru.varivoda;

import java.util.Date;

/**
 * Created by ivan on 09.03.17.
 */
public class Price {

    private long id;
    private String productCode;
    private int number;
    private int depart;
    private Date begin;
    private Date end;
    private long value;

    public Index getIndex() {
        return new Index();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDepart() {
        return depart;
    }

    public void setDepart(int depart) {
        this.depart = depart;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }


    public class Index {

        public String getProductCode() {
            return productCode;
        }

        public int getNumber() {
            return number;
        }

        public int getDepart() {
            return depart;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Index index = (Index) o;

            if (number != index.getNumber()) return false;
            if (depart != index.getDepart()) return false;
            return productCode != null ? productCode.equals(index.getProductCode()) : index.getProductCode() == null;
        }

        @Override
        public int hashCode() {
            int result = productCode != null ? productCode.hashCode() : 0;
            result = 31 * result + number;
            result = 31 * result + depart;
            return result;
        }
    }


}

