package varivoda;

import java.util.Date;

public class Price {

    private long id;
    private String productCode;
    private int number;
    private int depart;
    private Date begin;
    private Date end;
    private long value;


    public Price(Price price) {
        this.id = price.getId();
        this.productCode = price.getProductCode();
        this.number = price.getNumber();
        this.depart = price.getDepart();
        this.begin = price.getBegin();
        this.end = price.getEnd();
        this.value = price.getValue();
    }

    public Price() {
    }

    public Price(long id, String productCode, int number, int depart, Date begin, Date end, long value) {
        this.id = id;
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    @Override
    public String toString() {
        return "\nPrice" +
                "\nid=" + id +
                "\nproductCode='" + productCode + '\'' +
                "\nnumber=" + number +
                "\ndepart=" + depart +
                "\nbegin=" + begin +
                "\nend=" + end +
                "\nvalue=" + value +
                '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (id != price.id) return false;
        if (number != price.number) return false;
        if (depart != price.depart) return false;
        if (value != price.value) return false;
        if (productCode != null ? !productCode.equals(price.productCode) : price.productCode != null) return false;
        if (begin != null ? !begin.equals(price.begin) : price.begin != null) return false;
        return end != null ? end.equals(price.end) : price.end == null;
    }

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


    /**
     * Класс определяет уникаьный идетнификатор цены
     */
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

