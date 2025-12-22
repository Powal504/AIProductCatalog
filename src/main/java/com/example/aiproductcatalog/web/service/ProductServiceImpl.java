package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.web.api.dto.ProductDTO;
import com.example.aiproductcatalog.web.api.mapper.ProductMapper;
import com.example.aiproductcatalog.web.model.Category;
import com.example.aiproductcatalog.web.model.Product;
import com.example.aiproductcatalog.web.model.ProductInCategory;
import com.example.aiproductcatalog.web.repository.CategoryRepository;
import com.example.aiproductcatalog.web.repository.ProductInCategoryRepository;
import com.example.aiproductcatalog.web.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductInCategoryRepository productInCategoryRepository;
    private final ProductMapper productMapper;

    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    Optional<ProductInCategory> relation = productInCategoryRepository.findAll()
                            .stream()
                            .filter(link -> link.getProductId().equals(product.getId()))
                            .findFirst();

                    Category category = relation
                            .flatMap(link -> categoryRepository.findById(link.getCategoryId()))
                            .orElse(null);

                    return productMapper.toDto(product, category);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO addProduct(String name, String image, Integer categoryId) {
        Product product = Product.builder()
                .name(name)
                .image(image)
                .build();

        Product saved = productRepository.save(product);

        if (categoryRepository.existsById(categoryId)) {
            ProductInCategory link = ProductInCategory.builder()
                    .productId(saved.getId())
                    .categoryId(categoryId)
                    .build();
            productInCategoryRepository.save(link);
        }

        Category category = categoryRepository.findById(categoryId).orElse(null);

        return productMapper.toDto(saved, category);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
        productInCategoryRepository.findAll()
                .stream()
                .filter(link -> link.getProductId().equals(id))
                .forEach(link -> productInCategoryRepository.deleteById(link.getId()));
    }
}
