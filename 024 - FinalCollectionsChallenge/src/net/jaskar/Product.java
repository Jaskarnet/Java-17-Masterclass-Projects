package net.jaskar;

public record Product(String sku, String name, String manufacturer, Category category) implements Comparable<Product> {
    public enum Category {
        ELECTRONICS,
        CLOTHING,
        HOME_APPLIANCES,
        BOOKS,
        BEAUTY,
        TOYS,
        SPORTS,
        GROCERIES,
        FURNITURE,
        AUTOMOTIVE,
        JEWELRY,
        OFFICE_SUPPLIES,
        HEALTH,
        OUTDOOR,
        PET_SUPPLIES,
        MUSIC,
        MOVIES,
        VIDEO_GAMES,
        ART,
        GARDENING,
        PRODUCE,
        DAIRY,
        CEREAL,
        MEAT,
        BEVERAGE;

        public String getDisplayName() {
            return name().charAt(0) + name().substring(1).toLowerCase().replace('_', ' ');
        }
    }

    @Override
    public String toString() {
        return String.format("Product[SKU: %s, Name: %s, Manufacturer: %s, Category: %s]", sku, name, manufacturer, category.getDisplayName());
    }

    @Override
    public int compareTo(Product o) {
        return sku.compareTo(o.sku);
    }
}
