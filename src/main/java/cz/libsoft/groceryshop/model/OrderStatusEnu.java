package cz.libsoft.groceryshop.model;

public enum OrderStatusEnu {
    ordered("ordered"),
    paid("paid"),
    sent("sent"),
    delivered("delivered"),
    cancelled("cancelled");

    private String externalMapping;

    private OrderStatusEnu(String externalMapping) {
        this.externalMapping = externalMapping;
    }

    public String getExternalMapping() {
        return externalMapping;
    }
}
