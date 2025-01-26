package com.catalogar.mapper;

import com.catalogar.dto.CatalogRequestDto;
import com.catalogar.model.Catalog;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class CatalogMapper {

    public Catalog toCatalog(CatalogRequestDto requestDto) {
        ZonedDateTime publishedAt = requestDto.isActive()
                ? ZonedDateTime.now()
                : null;

        return new Catalog(
                requestDto.slug(),
                publishedAt
        );
    }
}
