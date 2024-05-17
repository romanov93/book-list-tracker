package ru.romanov.booktracker.web.mapper;

import java.util.List;

public interface Mappable<ENTITY, DTO> {

    ENTITY toEntity(DTO dto);

    DTO toDto(ENTITY entity);

    List<ENTITY> toEntity(List<DTO> dtos);

    List<DTO> toDto(List<ENTITY> entities);
}
