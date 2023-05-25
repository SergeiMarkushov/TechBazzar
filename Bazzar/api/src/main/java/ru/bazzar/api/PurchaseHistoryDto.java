package ru.bazzar.api;

public class PurchaseHistoryDto {
    private Long id;
    private String email;
    private String productTitle;
    private String organizationTitle;
    private int quantity;
    private String datePurchase;

    public PurchaseHistoryDto() {
    }

    public PurchaseHistoryDto(Long id, String email, String productTitle, String organizationTitle, int quantity, String datePurchase) {
        this.id = id;
        this.email = email;
        this.productTitle = productTitle;
        this.organizationTitle = organizationTitle;
        this.quantity = quantity;
        this.datePurchase = datePurchase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getOrganizationTitle() {
        return organizationTitle;
    }

    public void setOrganizationTitle(String organizationTitle) {
        this.organizationTitle = organizationTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(String datePurchase) {
        this.datePurchase = datePurchase;
    }
}
