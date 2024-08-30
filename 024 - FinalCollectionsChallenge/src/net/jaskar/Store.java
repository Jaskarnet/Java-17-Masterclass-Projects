package net.jaskar;

import java.time.LocalDate;
import java.util.*;

public class Store {
    private Map<String, InventoryItem> inventory = new HashMap<>();
    private NavigableSet<Cart> carts = new TreeSet<>(Comparator.comparing(Cart::getId));
    private Map<Product.Category, Map<String, InventoryItem>> aisleInventory = new EnumMap<>(Product.Category.class);
    Random random = new Random();

    public static void main(String[] args) {
        Store myStore = new Store();
        myStore.stockStore();
        myStore.listInventory();

        myStore.stockAisles();
        myStore.listProductsByCategory();

        myStore.manageStoreCarts();
        myStore.listProductsByCategory(false, true);

        myStore.carts.forEach(System.out::println);
        myStore.abandonCarts();
        myStore.listProductsByCategory(false, true);
        myStore.carts.forEach(System.out::println);
    }

    private void manageStoreCarts() {
        Cart cart1 = new Cart(Cart.CartType.PHYSICAL, 1);
        carts.add(cart1);

        InventoryItem item = aisleInventory.get(Product.Category.PRODUCE).get("apple");
        cart1.addItem(item, 6);
        cart1.addItem(aisleInventory.get(Product.Category.PRODUCE).get("pear"), 5);
        cart1.addItem(aisleInventory.get(Product.Category.BEVERAGE).get("coffee"), 1);
        System.out.println(cart1);

        cart1.removeItem(aisleInventory.get(Product.Category.PRODUCE).get("pear"), 2);
        System.out.println(cart1);

        Cart cart2 = new Cart(Cart.CartType.VIRTUAL, 1);
        carts.add(cart2);
        cart2.addItem(inventory.get("L103"), 20);
        cart2.addItem(inventory.get("B100"), 10);
        System.out.println(cart2);

        Cart cart3 = new Cart(Cart.CartType.VIRTUAL, 0);
        carts.add(cart3);
        cart3.addItem(inventory.get("R777"), 998);
        System.out.println(cart3);
        if (!checkOutCart(cart3)) {
            System.out.println("Something went wrong, could not check out");
        }

        Cart cart4 =  new Cart(Cart.CartType.PHYSICAL, 0);
        carts.add(cart4);
        cart4.addItem(aisleInventory.get(Product.Category.BEVERAGE).get("tea"), 1);
        System.out.println(cart4);
    }

    private boolean checkOutCart(Cart cart) {
        for (var cartItem : cart.getProducts().entrySet()) {
            var item = inventory.get(cartItem.getKey());
            int qty = cartItem.getValue();
            if (!item.sellItem(qty)) return false;
        }
        cart.printSalesSlip(inventory);
        carts.remove(cart);
        return true;
    }

    private void abandonCarts() {
        int dayOfYear = LocalDate.now().getDayOfYear();
        Cart lastCart = null;
        for (Cart cart : carts) {
            if (cart.getCartDate().getDayOfYear() == dayOfYear) {
                break;
            }
            lastCart = cart;
        }

        var oldCarts = carts.headSet(lastCart, true);
        Cart abandonedCart = null;
        while ((abandonedCart = oldCarts.pollFirst()) != null) {
            for (String sku : abandonedCart.getProducts().keySet()) {
                InventoryItem item = inventory.get(sku);
                item.releaseItem(abandonedCart.getProducts().get(sku));
            }
        }
    }

    private void listProductsByCategory() {

        listProductsByCategory(true, false);
    }

    private void listProductsByCategory(boolean includeHeader, boolean includeDetail) {

        aisleInventory.keySet().forEach(k -> {
            if (includeHeader) System.out.println("--------\n" + k + "\n--------");
            if (!includeDetail) {
                aisleInventory.get(k).keySet().forEach(System.out::println);
            } else {
                aisleInventory.get(k).values().forEach(System.out::println);
            }
        });
    }

    private void stockStore() {
        inventory = new HashMap<>();
        List<Product> products = new ArrayList<>(List.of(
                new Product("A100","apple","local",Product.Category.PRODUCE),
                new Product("B100","banana","local",Product.Category.PRODUCE),
                new Product("P100","pear","local",Product.Category.PRODUCE),
                new Product("L103","lemon","local",Product.Category.PRODUCE),
                new Product("M201","milk","farm",Product.Category.DAIRY),
                new Product("Y001","yogurt","farm",Product.Category.DAIRY),
                new Product("C333","cheese","farm",Product.Category.DAIRY),
                new Product("R777","rice chex","Nabisco",Product.Category.CEREAL),
                new Product("G111","granola","Nat Valley",Product.Category.CEREAL),
                new Product("BB11","ground beef","butcher",Product.Category.MEAT),
                new Product("CC11","chicken","butcher",Product.Category.MEAT),
                new Product("BC11","bacon","butcher",Product.Category.MEAT),
                new Product("BC77","coke","coca cola",Product.Category.BEVERAGE),
                new Product("BC88","coffee","value",Product.Category.BEVERAGE),
                new Product("BC99","tea","herbal",Product.Category.BEVERAGE)
        ));
        products.forEach(p -> inventory.put(p.sku(), new InventoryItem(p, 1000, 5, random.nextDouble(0, 1.25))));
    }

    private void stockAisles() {
        for (InventoryItem item : inventory.values()) {
            Product.Category aisle = item.getProduct().category();
            Map<String, InventoryItem> productMap = aisleInventory.get(aisle);
            if (productMap == null)
                productMap = new TreeMap<>();
            productMap.put(item.getProduct().name(), item);
            aisleInventory.putIfAbsent(aisle, productMap);
        }
    }

    private void listInventory() {
        System.out.println("-".repeat(20));
        inventory.values().forEach(System.out::println);
    }
}
