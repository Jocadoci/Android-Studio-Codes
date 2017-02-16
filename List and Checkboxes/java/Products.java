package jocadoci.;

//This class is a complement to send request from the "ListCheckboxesActivity" class.
public class Products {

    String name = null;
    String code = null;
    String price = null;
    boolean selected = false;

    public Products(String name, String code, String price, boolean selected) {
        super();
        this.code = code;
        this.price =  price;
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
