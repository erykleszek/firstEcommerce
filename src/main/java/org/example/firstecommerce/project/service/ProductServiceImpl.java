package org.example.firstecommerce.project.service;

import org.example.firstecommerce.project.Exceptions.ResourceNotFoundException;
import org.example.firstecommerce.project.model.Category;
import org.example.firstecommerce.project.model.Product;
import org.example.firstecommerce.project.payload.ProductDTO;
import org.example.firstecommerce.project.payload.ProductResponse;
import org.example.firstecommerce.project.repositories.CategoryRepository;
import org.example.firstecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent((productDTOS));
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent((productDTOS));
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {

        List<Product> products = productRepository
                .findByProductNameLikeIgnoreCase("%" + keyword + "%");

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, Product product) {
        //Get the exsisting product form DB
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId", productId));
        //Update the product info with the one in request body
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());

        Product savedProduct = productRepository.save(productFromDb);
        //Save to DB
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Prodcut", "productId", productId));

        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //Get the product from DB
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId", productId));
        //Upload img to server
        //Get the file name of uploaded img
        String path = "images/";
        String fileName = uploadImage(path, image);
        //upd the new file name to the product
        productFromDb.setImage(fileName);
        //safe updated product
        Product updatedProduct = productRepository.save(productFromDb);
        // return DTO after mapping product to DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);

    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        //get file names of curent / original file
        String originalFilename = file.getOriginalFilename();

        // generate a unique file name
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String filePath = path + File.separator + fileName;

        // check if path exist and create
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();

        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // return the file name
        return fileName;
    }
}
