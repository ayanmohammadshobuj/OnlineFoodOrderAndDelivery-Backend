package com.shobuj.service.impl;

import com.shobuj.entity.Cart;
import com.shobuj.entity.CartItem;
import com.shobuj.entity.Food;
import com.shobuj.entity.User;
import com.shobuj.repository.CartItemRepository;
import com.shobuj.repository.CartRepository;
import com.shobuj.request.AddCartItemRequest;
import com.shobuj.service.CartService;
import com.shobuj.service.FoodService;
import com.shobuj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(request.getFoodId());
        Cart cart = cartRepository.findByUserId(user.getId());

        // Check if the cart already contains items from a different restaurant
        if (!cart.getCartItems().isEmpty() && !cart.getCartItems().get(0).getFood().getRestaurant().getId().equals(food.getRestaurant().getId())) {
            // Clear the cart
            cart.getCartItems().clear();
            updateCartTotalPrice(cart);
        }

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setCart(cart);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setTotalPrice(request.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        cart.getCartItems().add(savedCartItem);
        updateCartTotalPrice(cart);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found");
        }

        CartItem item = cartItemOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        CartItem updatedCartItem = cartItemRepository.save(item);

        // Update the total price of the cart
        updateCartTotalPrice(item.getCart());


        return updatedCartItem;
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByUserId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found");
        }

        CartItem item = cartItemOptional.get();
        cart.getCartItems().remove(item);
        cartItemRepository.delete(item);

        // Update the total price of the cart
        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }

    @Override
    public double calculateCartTotals(Cart cart) throws Exception {
        double total = 0L;

        for (CartItem cartItem : cart.getCartItems()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }

        // Add service charge of 5%
        total += total * 0.05;

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isEmpty()) {
            throw new Exception("Cart not found with id " + id);
        }
        return cartOptional.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByUserId(userId);
        cart.setTotalPrice(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart = findCartByUserId(userId);
        cart.getCartItems().clear();
        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }


    @Override
    public Cart removeItemFromCartByFoodId(Long foodId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByUserId(user.getId());

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getFood().getId().equals(foodId)) {
                cart.getCartItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
                updateCartTotalPrice(cart);
                return cartRepository.save(cart);
            }
        }

        return cart;
    }

    @Override
    public CartItem updateCartItemQuantityByFoodId(Long foodId, int quantity, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByUserId(user.getId());

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getFood().getId().equals(foodId)) {
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
                CartItem updatedCartItem = cartItemRepository.save(cartItem);

                // Update the total price of the cart
                updateCartTotalPrice(cart);

                return updatedCartItem;
            }
        }

        throw new Exception("Cart item not found for the given food ID");
    }

    @Override
    public CartItem getCartItemByFoodId(Long foodId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByUserId(user.getId());

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getFood().getId().equals(foodId)) {
                return cartItem;
            }
        }
        throw new Exception("Cart item not found for the given food ID");
    }

    @Override
    public CartItem deleteCartItemByUser(User user) throws Exception {
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart.getCartItems().isEmpty()) {
            throw new Exception("Cart is empty");
        }
        CartItem cartItem = cart.getCartItems().get(0);
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        updateCartTotalPrice(cart);
        return cartItem;
    }


    private void updateCartTotalPrice(Cart cart) throws Exception {
        double total = calculateCartTotals(cart);
        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }
}