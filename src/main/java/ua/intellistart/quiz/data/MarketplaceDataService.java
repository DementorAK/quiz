package ua.intellistart.quiz.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.intellistart.quiz.model.Product;
import ua.intellistart.quiz.model.Purchase;
import ua.intellistart.quiz.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class MarketplaceDataService {

    @Autowired
    private final ProductsRepository productsRepository;
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final PurchaseRepository purchaseRepository;

    public MarketplaceDataService(ProductsRepository productsRepository, UsersRepository usersRepository, PurchaseRepository purchaseRepository){
        this.productsRepository = productsRepository;
        this.usersRepository = usersRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void saveProduct(Product product){
        productsRepository.save(product);
    }

    public void saveUser(User user){
        usersRepository.save(user);
    }

    public List<Product> getAllProducts(){
        return (List<Product>) productsRepository.findAll();
    }

    public List<User> getAllUsers(){
        return (List<User>) usersRepository.findAll();
    }

    public User getUserById(long id) throws Exception {
        Optional<User> result = usersRepository.findById(id);
        if (result.isEmpty()) {
            throw new Exception("User with id="+id+" not found in database");
        }
        return result.get();
    }

    public Product getProductById(long id) throws Exception {
        Optional<Product> result = productsRepository.findById(id);
        if (result.isEmpty()) {
            throw new Exception("Product with id="+id+" not found in database");
        }
        return result.get();
    }

    @Transactional
    public void savePurchase(Purchase purchase, User user){
        usersRepository.save(user);
        purchaseRepository.save(purchase);
    }

    public List<Purchase> getPurchasesByUser(User user) {
        return purchaseRepository.findByUser(user);
    }

    public List<Purchase> getPurchasesByProduct(Product product) {
        return purchaseRepository.findByProduct(product);
    }

    @Transactional
    public void deleteUser(User user) {
        purchaseRepository.removeByUser(user);
        usersRepository.delete(user);
    }

    @Transactional
    public void deleteProductAndUpdateUsers(Product product, List<User> users){
        purchaseRepository.removeByProduct(product);
        productsRepository.delete(product);
        usersRepository.saveAll(users);
    }
}
