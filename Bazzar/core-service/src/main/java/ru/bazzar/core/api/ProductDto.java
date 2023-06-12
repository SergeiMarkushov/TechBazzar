package ru.bazzar.core.api;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private String organizationTitle;
    private BigDecimal price;
    private int quantity;
    private boolean isConfirmed;
    private List<CharacteristicDto> characteristicsDto;
    private Long picture_id;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, String description, String organizationTitle, BigDecimal price, int quantity, boolean isConfirmed, List<CharacteristicDto> characteristics, Long pictureId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.organizationTitle = organizationTitle;
        this.price = price;
        this.quantity = quantity;
        this.isConfirmed = isConfirmed;
        this.characteristicsDto = characteristics;
        picture_id = pictureId;
    }

    public Long getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(Long picture_id) {
        this.picture_id = picture_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizationTitle() {
        return organizationTitle;
    }

    public void setOrganizationTitle(String organizationTitle) {
        this.organizationTitle = organizationTitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public List<CharacteristicDto> getCharacteristicsDto() {
        return characteristicsDto;
    }

    public void setCharacteristicsDto(List<CharacteristicDto> characteristicsDto) {
        this.characteristicsDto = characteristicsDto;
    }
}
