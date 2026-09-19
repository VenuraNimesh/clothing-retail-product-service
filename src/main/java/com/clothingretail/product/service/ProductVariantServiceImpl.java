package com.clothingretail.product.service;

import com.clothingretail.product.entity.Color;
import com.clothingretail.product.entity.Product;
import com.clothingretail.product.entity.ProductVariant;
import com.clothingretail.product.entity.Size;
import com.clothingretail.product.exception.*;
import com.clothingretail.product.mapper.ProductVariantMapper;
import com.clothingretail.product.model.request.CreateProductVariantRequest;
import com.clothingretail.product.model.request.UpdateProductVariantRequest;
import com.clothingretail.product.model.request.UpdateProductVariantStatusRequest;
import com.clothingretail.product.model.response.ProductVariantResponse;
import com.clothingretail.product.repository.ColorRepository;
import com.clothingretail.product.repository.ProductRepository;
import com.clothingretail.product.repository.ProductVariantRepository;
import com.clothingretail.product.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final ProductVariantMapper productVariantMapper;

    @Override
    @Transactional
    public ProductVariantResponse createVariant(UUID productId, CreateProductVariantRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(productId)
                );

        Size size = sizeRepository.findById(request.sizeId())
                .orElseThrow(() ->
                        new SizeNotFoundException(request.sizeId())
                );

        Color color = colorRepository.findById(request.colorId())
                .orElseThrow(() ->
                        new ColorNotFoundException(request.colorId())
                );

        if (productVariantRepository
                .existsBySkuIgnoreCase(request.sku())) {

            throw new DuplicateSkuException(request.sku());
        }

        if (productVariantRepository
                .existsByProductIdAndSizeIdAndColorId(
                        productId,
                        request.sizeId(),
                        request.colorId()
                )) {

            throw new DuplicateProductVariantException(
                    productId,
                    request.sizeId(),
                    request.colorId()
            );
        }

        ProductVariant variant =
                productVariantMapper.toEntity(
                        request,
                        product,
                        size,
                        color
                );

        ProductVariant savedVariant =
                productVariantRepository.save(variant);

        return productVariantMapper.toResponse(savedVariant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductVariantResponse> getVariants(UUID productId, Pageable pageable) {
        getProduct(productId);

        return productVariantRepository
                .findByProductId(productId, pageable)
                .map(productVariantMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductVariantResponse getVariantById(UUID productId, UUID variantId) {
        getProduct(productId);

        ProductVariant variant =
                productVariantRepository.findById(variantId)
                        .orElseThrow(() ->
                                new ProductVariantNotFoundException(
                                        variantId
                                )
                        );

        validateVariantBelongsToProduct(
                variant,
                productId
        );

        return productVariantMapper.toResponse(variant);
    }

    @Override
    @Transactional
    public ProductVariantResponse updateVariant(UUID productId, UUID variantId, UpdateProductVariantRequest request) {
        getProduct(productId);

        ProductVariant variant =
                productVariantRepository.findById(variantId)
                        .orElseThrow(() ->
                                new ProductVariantNotFoundException(
                                        variantId
                                )
                        );

        validateVariantBelongsToProduct(
                variant,
                productId
        );

        Size size = getSize(request.sizeId());

        Color color = getColor(request.colorId());

        if (productVariantRepository
                .existsBySkuIgnoreCaseAndIdNot(
                        request.sku(),
                        variantId
                )) {

            throw new DuplicateSkuException(request.sku());
        }

        if (productVariantRepository
                .existsByProductIdAndSizeIdAndColorIdAndIdNot(
                        productId,
                        request.sizeId(),
                        request.colorId(),
                        variantId
                )) {

            throw new DuplicateProductVariantException(
                    productId,
                    request.sizeId(),
                    request.colorId()
            );
        }

        productVariantMapper.updateEntity(
                variant,
                request,
                size,
                color
        );

        return productVariantMapper.toResponse(variant);
    }

    @Override
    @Transactional
    public void updateVariantStatus(UUID productId, UUID variantId, UpdateProductVariantStatusRequest request) {
        getProduct(productId);

        ProductVariant variant =
                productVariantRepository.findById(variantId)
                        .orElseThrow(() ->
                                new ProductVariantNotFoundException(
                                        variantId
                                )
                        );

        validateVariantBelongsToProduct(
                variant,
                productId
        );

        variant.setStatus(request.status());
    }

    private Product getProduct(UUID productId) {

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(productId)
                );
    }

    private Size getSize(UUID sizeId) {

        return sizeRepository.findById(sizeId)
                .orElseThrow(() ->
                        new SizeNotFoundException(sizeId)
                );
    }

    private Color getColor(UUID colorId) {

        return colorRepository.findById(colorId)
                .orElseThrow(() ->
                        new ColorNotFoundException(colorId)
                );
    }

    private void validateUniqueSku(String sku) {

        if (productVariantRepository
                .existsBySkuIgnoreCase(sku)) {

            throw new DuplicateSkuException(sku);
        }
    }

    private void validateUniqueCombination(
            UUID productId,
            UUID sizeId,
            UUID colorId
    ) {

        if (productVariantRepository
                .existsByProductIdAndSizeIdAndColorId(
                        productId,
                        sizeId,
                        colorId
                )) {

            throw new DuplicateProductVariantException(
                    productId,
                    sizeId,
                    colorId
            );
        }
    }

    private void validateVariantBelongsToProduct(
            ProductVariant variant,
            UUID productId
    ) {

        if (!variant.getProduct().getId().equals(productId)) {
            throw new ProductVariantNotFoundException(
                    variant.getId()
            );
        }
    }
}
