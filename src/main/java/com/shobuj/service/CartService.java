package com.shobuj.service;

import com.shobuj.entity.Cart;
import com.shobuj.entity.CartItem;
import com.shobuj.entity.User;
import com.shobuj.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest request, String jwt)throws Exception;

    public CartItem updateCartItemQuantity(Long cartItem, int quantity)throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt)throws Exception;

    public double calculateCartTotals(Cart cart)throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart findCartByUserId(Long userId)throws Exception;

    public Cart clearCart(Long userId)throws Exception;


    Cart removeItemFromCartByFoodId(Long foodId, String jwt) throws Exception;

    public CartItem updateCartItemQuantityByFoodId(Long foodId, int quantity, String jwt) throws Exception;

    CartItem getCartItemByFoodId(Long foodId, String jwt) throws Exception;

    public CartItem deleteCartItemByUser(User user) throws Exception;

}
