package ua.intellistart.quiz.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.intellistart.quiz.data.MarketplaceDataService;
import ua.intellistart.quiz.model.Product;
import ua.intellistart.quiz.model.Purchase;
import ua.intellistart.quiz.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Marketplace {
    @Autowired
    private MarketplaceDataService marketplaceDataService;

    public Marketplace() {
    }

    public void addNewUser(String firsName, String lastName, BigDecimal amount){
        User newUser = new User(0L, firsName, lastName, amount);
        marketplaceDataService.saveUser(newUser);
    }

    public void addNewProduct(String name, BigDecimal price){
        Product newProduct = new Product(0L, name, price);
        marketplaceDataService.saveProduct(newProduct);
    }

    public void addTestData(){
        addNewProduct("Beads", new BigDecimal("99.99"));
        addNewProduct("Earrings", new BigDecimal("500.50"));
        addNewProduct("Ring", new BigDecimal("799.99"));

        addNewUser("Tom", "Cruise", new BigDecimal("1000.00"));
        addNewUser("Bradley", "Pitt", new BigDecimal("700.00"));
        addNewUser("Nicolas", "Cage", new BigDecimal("100.00"));
    }

    public List<User> getUsers(){
        return marketplaceDataService.getAllUsers();
    }

    public List<Product> getProducts(){
        return marketplaceDataService.getAllProducts();
    }

    public String addPurchase(long userId, long productId) throws Exception {
        User user = marketplaceDataService.getUserById(userId);
        Product product = marketplaceDataService.getProductById(productId);
        if (product.getPrice().compareTo(user.getAmountOfMoney())>0){
            throw new Exception("User does not have enough money. $"+
                    product.getPrice().subtract(user.getAmountOfMoney())+" missing");
        }

        user.setAmountOfMoney(user.getAmountOfMoney().subtract(product.getPrice()));
        Purchase purchase = new Purchase(user, product);
        marketplaceDataService.savePurchase(purchase, user);

        return String.format(
                "'%s %s' successfully bought '%s' worth $%s",
                user.getFirstName(),
                user.getLastName(),
                product.getName(),
                product.getPrice().toString());
    }

    public Product[] getProductsFromPurchasesByUserId(long userId) throws Exception {
        User user = marketplaceDataService.getUserById(userId);
        List<Purchase> purchases = marketplaceDataService.getPurchasesByUser(user);
        return purchases.stream().map(Purchase::getProduct).distinct().toArray(Product[]::new);
    }

    public User[] getUsersFromPurchasesByProductId(long productId) throws Exception {
        Product product = marketplaceDataService.getProductById(productId);
        List<Purchase> purchases = marketplaceDataService.getPurchasesByProduct(product);
        return purchases.stream().map(Purchase::getUser).distinct().toArray(User[]::new);
    }

    public String deleteUserById(long id) throws Exception {
        User user = marketplaceDataService.getUserById(id);
        List<Purchase> purchases = marketplaceDataService.getPurchasesByUser(user);
        String result = String.format(
                "'%s' and his %d purchases have been deleted",
                user.getFullName(),
                purchases.size());
        marketplaceDataService.deleteUser(user);
        return result;
    }

    public String deleteProductById(long id) throws Exception {
        Product product = marketplaceDataService.getProductById(id);
        List<Purchase> purchases = marketplaceDataService.getPurchasesByProduct(product);
        List<User> users = purchases.stream()
                .map(p -> {
                    User user = p.getUser();
                    BigDecimal amount = user.getAmountOfMoney();
                    amount = amount.add(p.getProduct().getPrice());
                    user.setAmountOfMoney(amount);
                    return user;
                })
                .distinct()
                .collect(Collectors.toList());
        String result = String.format(
                "'%s' and its %d purchases have been deleted. %d users received a refund",
                product.getName(),
                purchases.size(),
                users.size());
        marketplaceDataService.deleteProductAndUpdateUsers(product, users);
        return result;
    }
}
