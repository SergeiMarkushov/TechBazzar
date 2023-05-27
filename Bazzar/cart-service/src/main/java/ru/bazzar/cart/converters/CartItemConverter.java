package ru.bazzar.cart.converters;

import org.springframework.stereotype.Component;
import ru.bazzar.cart.api.CartItemDto;
import ru.bazzar.cart.model.CartItem;

@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(cartItem.getProductId());
        cartItemDto.setPrice(cartItem.getPrice());
        cartItemDto.setProductTitle(cartItem.getProductTitle());
        cartItemDto.setPricePerProduct(cartItem.getPricePerProduct());
        cartItemDto.setQuantity(cartItem.getQuantity());
        return cartItemDto;
    }
}
