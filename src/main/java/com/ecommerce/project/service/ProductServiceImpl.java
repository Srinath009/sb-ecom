package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import org.jspecify.annotations.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        if (!productRepository.findByProductNameLikeIgnoreCase(productDTO.getProductName()).isEmpty()) {
            throw new APIException("Product already exists");
        }
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setImage("default.png");
        Double specialPrice  = (product.getDiscount() * 0.01) * product.getPrice();
        product.setSpecialPrice(product.getPrice() - specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.valueOf(sortDir.toUpperCase()), sortBy);
        Page<Product> productPage = productRepository.findAll(pageable);

        if(productPage.isEmpty())
            throw new APIException("No products found");

        return getProductResponse(productPage);
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.valueOf(sortDir.toUpperCase()), sortBy);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category,pageable);

        if(productPage.isEmpty())
            throw new APIException("No products found for category id: " + categoryId);

        return getProductResponse(productPage);
    }

    @Override
    public ProductResponse searchByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.valueOf(sortDir.toUpperCase()), sortBy);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%', pageable);

        return getProductResponse(productPage);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "ProductId", productId));

        Product productFromReq = modelMapper.map(productDTO, Product.class);

        productFromDb.setProductName(productFromReq.getProductName());
        productFromDb.setDescription(productFromReq.getDescription());
        productFromDb.setQuantity(productFromReq.getQuantity());
        productFromDb.setPrice(productFromReq.getPrice());
        productFromDb.setDiscount(productFromReq.getDiscount());
        Double specialPrice  = (productFromDb.getDiscount() * 0.01) * productFromDb.getPrice();
        productFromDb.setSpecialPrice(productFromDb.getPrice() - specialPrice);

        Product savedProduct =  productRepository.save(productFromDb);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "ProductId", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "ProductId", productId));
        String fileName = fileService.uploadImage(path, image);
        productFromDb.setImage(fileName);
        Product savedProduct = productRepository.save(productFromDb);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @NonNull
    private ProductResponse getProductResponse(Page<Product> productPage) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productPage.getContent().stream().
                map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList()));

        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

}
