package cz.libsoft.groceryshop.model;

public enum OrderStatus {
    ORDERED("ORDERED"),
    PAID("PAID"),
    SENT("SENT"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private String externalMapping;

    private OrderStatus(String externalMapping) {
        this.externalMapping = externalMapping;
    }

    public String getExternalMapping() {
        return externalMapping;
    }
}
