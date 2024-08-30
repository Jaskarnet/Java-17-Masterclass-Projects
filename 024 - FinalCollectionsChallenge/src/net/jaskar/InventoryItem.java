package net.jaskar;

public class InventoryItem implements Comparable<InventoryItem> {
    private Product product;
    private int qtyTotal;
    private int qtyReserved;
    private int qtyReorder;
    private int qtyLow;
    private double salesPrice;

    public InventoryItem(Product product, int qtyTotal, int qtyLow, double salesPrice) {
        this.product = product;
        this.qtyTotal = qtyTotal;
        this.qtyLow = qtyLow;
        this.salesPrice = salesPrice;
    }

    public boolean reserveItem(int qty) {
        if (qtyTotal - qtyReserved >= qty) {
            qtyReserved += qty;
            return true;
        }
        return false;
    }

    public void releaseItem(int qty) {
        qtyReserved -= qty;
    }

    public boolean sellItem(int qty) {
        if (qtyTotal >= qty) {
            qtyTotal -= qty;
            qtyReserved -= qty;
            if (qtyTotal <= qtyLow) {
                placeInventoryOrder();
            }
            return true;
        }
        return false;
    }

    private void placeInventoryOrder() {
        System.out.printf("Ordering qty %d : %s%n", qtyReorder, product);
    }

    public Product getProduct() {
        return product;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    @Override
    public String toString() {
        return "%s, $%.2f : [%04d,% 2d]".formatted(product, salesPrice, qtyTotal,
                qtyReserved);
    }

    @Override
    public int compareTo(InventoryItem o) {
        return product.compareTo(o.product);
    }
}
