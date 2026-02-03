package com.sales.system.controller.user;

import com.sales.system.dto.cart.AddItemRequest;
import com.sales.system.dto.cart.CartResponseDTO;
import com.sales.system.dto.user.UpdateItemRequest;
import com.sales.system.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users/{userId}/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartResponse(userId));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItem(
            @PathVariable Long userId,
            @Valid @RequestBody AddItemRequest request
    ) {
        return ResponseEntity.ok(
                cartService.addItem(userId, request.getProductId(), request.getQuantity())
        );
    }

    @PutMapping("/items")
    public ResponseEntity<CartResponseDTO> updateItem(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateItemRequest request
    ) {
        return ResponseEntity.ok(
                cartService.updateQuantity(userId, request.getProductId(), request.getQuantity())
        );
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeItem(
            @PathVariable Long userId,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(cartService.removeItem(userId, productId));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getTotal(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
