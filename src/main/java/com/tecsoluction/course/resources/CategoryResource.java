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

import com.tecsoluction.course.entities.Category;
import com.tecsoluction.course.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "Endpoints for Managing Categories")
public class CategoryResource {
    @Autowired
    private CategoryService service;

    @Operation(summary = "Find all categories", description = "Retrieves the full list of product categories available.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
    })
    @GetMapping
    public ResponseEntity<List<Category>> findall() {
        List<Category> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Find category by ID", description = "Retrieves specific details of a category based on the ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Register new category", description = "Creates a new product category (e.g., Electronics, Books).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody Category obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @Operation(summary = "Delete category", description = "idRemoves a category from the system by its .")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Database integrity violation"),

    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update category", description = "Updates the name or details of an existing category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
