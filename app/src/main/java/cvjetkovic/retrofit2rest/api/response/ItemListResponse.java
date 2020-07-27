package cvjetkovic.retrofit2rest.api.response;

import cvjetkovic.retrofit2rest.model.Item;

import java.util.List;

/**
 * Created by Vladimir Cvjetkovic
 */
public class ItemListResponse {

    private String message;
    List<Item> products;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Item> getData() {
        return products;
    }

    public void setData(List<Item> data) {
        this.products = data;
    }
}
