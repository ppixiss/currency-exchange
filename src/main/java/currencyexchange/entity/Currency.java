package currencyexchange.entity;

import java.util.Objects;

public class Currency {

    private int id;
    private String code;
    private String name;
    private String sign;

    public Currency(int id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        this.name = fullName;
        this.sign = sign;
    }

    public Currency() {
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFullName(String fullName) {
        this.name = fullName;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "CurrenciesEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fullName='" + name + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Currency that = (Currency) o;
        return id == that.id && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(sign, that.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, sign);
    }
}
