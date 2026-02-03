package com.sales.system.service;

import com.sales.system.dto.cart.CartItemResponseDTO;
import com.sales.system.dto.cart.CartResponseDTO;
import com.sales.system.entity.Cart;
import com.sales.system.entity.CartItem;
import com.sales.system.entity.Product;
import com.sales.system.entity.User;
import com.sales.system.repository.CartItemRepository;
import com.sales.system.repository.CartRepository;
import com.sales.system.repository.ProductRepository;
import com.sales.system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public CartResponseDTO getCartResponse(Long userId) {
        Cart cart = getCart(userId);
        return mapToDTO(cart);
    }

    @Transactional
    public CartResponseDTO addItem(Long userId, Long productId, int quantity) {
        addItemRaw(userId, productId, quantity);
        return getCartResponse(userId);
    }

    @Transactional
    public CartResponseDTO updateQuantity(Long userId, Long productId, int quantity) {
        updateQuantityRaw(userId, productId, quantity);
        return getCartResponse(userId);
    }

    @Transactional
    public CartResponseDTO removeItem(Long userId, Long productId) {
        updateQuantityRaw(userId, productId, 0);
        return getCartResponse(userId);
    }

    private void addItemRaw(Long userId, Long productId, int quantity) {
        Cart cart = getCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst().orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }

        cartRepository.save(cart);
    }

    private void updateQuantityRaw(Long userId, Long productId, int quantity) {
        Cart cart = getCart(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
        }

        cartRepository.save(cart);
    }

    private Cart getCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getCart() == null) {
            throw new IllegalStateException("Cart not initialized for user");
        }
        return user.getCart();
    }

    private CartResponseDTO mapToDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getId());

        List<CartItemResponseDTO> items = cart.getItems().stream().map(item -> {
            CartItemResponseDTO itemDto = new CartItemResponseDTO();
            itemDto.setItemId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setUnitPrice(item.getProduct().getPrice());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setSubtotal(item.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
            return itemDto;
        }).toList();

        dto.setItems(items);
        dto.setTotal(items.stream()
                .map(CartItemResponseDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return dto;
    }

    public BigDecimal getTotal(Long userId) {
        CartResponseDTO cartDTO = getCartResponse(userId);
        return cartDTO.getTotal();
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getItems().forEach(cartItemRepository::delete);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
