package com.tecsoluction.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tecsoluction.course.entities.Order;
import com.tecsoluction.course.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order", description = "Endpoints for Managing Orders")
public class OrderResource {
    @Autowired
    private OrderService service;

    @Operation(summary = "Find all orders", description = "Retrieves the full history of orders placed in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<Order>> findall() {
        List<Order> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Find order by ID", description = "Retrieves detailed information about a specific order, including client, items, and payment status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Place new order", description = "Creates a new order associated with a user and products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20", description = "Order placed successfully"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<Order> insert(@RequestBody Order obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @Operation(summary = "Delete order", description = "Removes an order from the system by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Database integrity violation")

    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update order", description = "Updates order details (e.g., Order Status or Items).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
