package com.example.aiproductcatalog.web.api.mapper;

import com.example.aiproductcatalog.web.api.dto.ReviewDTO;
import com.example.aiproductcatalog.web.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface ReviewMapper {

        @Mapping(target = "username", source = "user.email")
        @Mapping(target = "avatarUrl", source = "user.avatarUrl")
        ReviewDTO toDto(Review review);
}
