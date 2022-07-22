package ua.intellistart.quiz.model;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.intellistart.quiz.data.MarketplaceDataService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MarketplaceTest {

    @Autowired
    private Marketplace marketplace;

    @Test
    void testingData() throws Exception {

        marketplace.addTestData();
        marketplace.addNewUser("Vasya", "Pupkin", new BigDecimal("123.50"));
        marketplace.addNewProduct("Bubble gum", new BigDecimal("75.50"));

        List<User> users = marketplace.getUsers();
        assertEquals(4, users.size());

        List<Product> products = marketplace.getProducts();
        assertEquals(4, products.size());

        marketplace.addPurchase(users.get(0).getId(), products.get(0).getId());
        marketplace.addPurchase(users.get(0).getId(), products.get(1).getId());

        marketplace.addPurchase(users.get(1).getId(), products.get(0).getId());
        marketplace.addPurchase(users.get(2).getId(), products.get(0).getId());

        Product[] findProducts = marketplace.getProductsFromPurchasesByUserId(users.get(0).getId());
        assertEquals(2, findProducts.length);

        User[] findUsers = marketplace.getUsersFromPurchasesByProductId(products.get(0).getId());
        assertEquals(3, findUsers.length);

        String result = marketplace.deleteProductById(products.get(0).getId());
        assertFalse(result.isBlank());

        result = marketplace.deleteUserById(users.get(0).getId());
        assertFalse(result.isBlank());

    }

}