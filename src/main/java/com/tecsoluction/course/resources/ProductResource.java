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

import com.tecsoluction.course.entities.Product;
import com.tecsoluction.course.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Endpoints for managing products")
public class ProductResource {
    @Autowired
    private ProductService service;

    @Operation(summary = "Find all products", description = "Retrieves the full list of products available in the catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieves the full list of products available in the catalog.")
    })
    @GetMapping
    public ResponseEntity<List<Product>> findall() {
        List<Product> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Find product by ID", description = "Retrieves specific details of a product based on the ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")

    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Register new product", description = "Creates a new product and associates it with existing categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "422", description = "Validation error")

    })
    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody Product obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @Operation(summary = "Delete product", description = "Removes a product from the catalog by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Database integrity violation")

    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update product", description = "Updates data of an existing product (price, description, name, etc.).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
