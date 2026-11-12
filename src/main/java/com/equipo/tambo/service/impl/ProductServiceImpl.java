package com.equipo.tambo.service.impl;

import com.equipo.tambo.dto.ProductRequest;
import com.equipo.tambo.dto.ProductResponse;
import com.equipo.tambo.entity.Category;
import com.equipo.tambo.entity.Product;
import com.equipo.tambo.repository.CategoryRepository;
import com.equipo.tambo.repository.ProductRepository;
import com.equipo.tambo.repository.SaleDetailRepository;
import com.equipo.tambo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SaleDetailRepository saleDetailRepository;

    @Override
    public List<ProductResponse> listProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getById(Long id) {
        return toResponse(getProduct(id));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el producto con ID " + id
                ));
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        applyProductRequest(product, request);

        return toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = getProduct(id);
        applyProductRequest(product, request);

        return toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProduct(id);

        if(saleDetailRepository.existsByProductId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el producto porque tiene ventas asociadas"
            );
        }

        productRepository.delete(product);
    }

    private void applyProductRequest(Product product, ProductRequest request) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setCost(request.cost());
        product.setStock(request.stock());

        if (request.active() != null) {
            product.setActive(request.active());
        }

        product.setCategory(findCategoryOrThrow(request.category()));
    }

    private Category findCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró la categoría con ID " + id
                ));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCost(),
                product.getStock(),
                product.getActive(),
                product.getCategory()
        );
    }
}