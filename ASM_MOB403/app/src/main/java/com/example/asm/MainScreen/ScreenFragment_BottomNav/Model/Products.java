package com.example.asm.MainScreen.ScreenFragment_BottomNav.Model;
import java.io.Serializable;
public class Products implements Serializable {
    String id;
    String productname;
    String producttype;
    String productimage;
    Number price;
    String size;
    Number mount;

    public Products() {
    }

    public Products(String id, String productname, String producttype, String productimage, Number price, String size, Number mount) {
        this.id = id;
        this.productname = productname;
        this.producttype = producttype;
        this.productimage = productimage;
        this.price = price;
        this.size = size;
        this.mount = mount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Number getMount() {
        return mount;
    }

    public void setMount(Number mount) {
        this.mount = mount;
    }
}
