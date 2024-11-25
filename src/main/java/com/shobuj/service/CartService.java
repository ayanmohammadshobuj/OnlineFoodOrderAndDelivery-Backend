package com.shobuj.service;

import com.shobuj.entity.Cart;
import com.shobuj.entity.CartItem;
import com.shobuj.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest request, String jwt)throws Exception;

    public CartItem updateCartItemQuantity(Long cartItem, int quantity)throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt)throws Exception;

    public Long calculateCartTotals(Cart cart)throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart findCartByUserId(String jwt)throws Exception;

    public Cart clearCart(String jwt)throws Exception;


}
