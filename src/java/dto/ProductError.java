
package dto;

/**
 *
 * @author MY LAPTOP
 */
public class ProductError {
    private String pid;
    private String pName;
    private String image;
    private String price;
    private String quantity;
    private String cid;
    
    public ProductError() {
        this.pid = "";
        this.pName = "";
        this.image = "";
        this.price = "";
        this.quantity = "";
        this.cid = "";
    }

    public ProductError(String pid, String pName, String image, String price, String quantity, String cid) {
        this.pid = pid;
        this.pName = pName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "ProductError{" + "pid=" + pid + ", pName=" + pName + ", image=" + image + ", price=" + price + ", quantity=" + quantity + ", cid=" + cid + '}';
    }
    
}
