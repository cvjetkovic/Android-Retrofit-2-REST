package cvjetkovic.retrofit2rest.model;

import java.io.Serializable;

/**
 * Created by Vladimir Cvjetkovic
 */
public class Item implements Serializable {

    Long id, price;
    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return name;
    }

    public void setNama(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
