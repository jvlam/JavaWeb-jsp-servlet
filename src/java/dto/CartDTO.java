/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MY LAPTOP
 */
public class CartDTO {

    private Map<String, BuyItem> cart;

    public CartDTO() {
    }

    public CartDTO(Map<String, BuyItem> cart) {
        this.cart = cart;
    }

    public Map<String, BuyItem> getCart() {
        return cart;
    }

    public void setCart(Map<String, BuyItem> cart) {
        this.cart = cart;
    }

    public boolean add(BuyItem product) {
        boolean check = false;
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }
        if (this.cart.containsKey(product.getProduct().getId())) {
            int currentQuantity = this.cart.get(product.getProduct().getId()).getQuantity();
            product.setQuantity(currentQuantity + product.getQuantity());
        }
        this.cart.put(product.getProduct().getId(), product);
        check = true;
        return check;
    }

    public boolean update(String id, BuyItem p) {
        boolean check = false;
        if (this.cart != null) {
            if (this.cart.containsKey(id)) {
                this.cart.replace(p.getProduct().getId(), p);
                check = true;
            }
        }
        return check;
    }

    public boolean remove(String id) {
        boolean check = false;
        if (this.cart != null) {
            if (this.cart.containsKey(id)) {
                this.cart.remove(id);
                check = true;
            }
        }
        return check;
    }

    public double getTotalMoney() {
        double total = 0;
        for (BuyItem p : cart.values()) {
            total += p.getPrice() * p.getQuantity();
        }
        return total;
    }
}
