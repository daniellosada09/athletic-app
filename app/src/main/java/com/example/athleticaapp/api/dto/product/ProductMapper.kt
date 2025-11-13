package com.example.athleticaapp.api.dto.product

object ProductMapper {

    fun fromDto(dto: ProductDto): ProductDto {
        return dto
    }

    fun fromDtoList(dtoList: List<ProductDto>): List<ProductDto> {
        return dtoList
    }
}
