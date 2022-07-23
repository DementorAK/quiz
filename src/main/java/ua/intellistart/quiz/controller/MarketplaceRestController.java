package ua.intellistart.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.intellistart.quiz.model.Marketplace;

import java.math.BigDecimal;

@RestController
public class MarketplaceRestController {

    @Autowired
    private Marketplace marketplace;

    @RequestMapping(value = {"/help", "/"})
    public String help(){
        return "<B><A href=/help>/help</A></B> - this help page" +
                "\n<BR /><B><A href=/test>/test</A></B> - load test data (3 users and 3 products)" +
                "\n<BR /><B><A href=/users>/users</A></B> - print list of users" +
                "\n<BR /><B><A href=/products>/products</A></B> - print list of products" +
                "\n<BR />" +
                "\n<HR /><B>/add_user?first=[First name]&last=[Last name]&amount=[Amount of money]</B> - add new user to marketplace, e.g. <A href=/add_user?first=Vasya&last=Pupkin&amount=123.50>/add_user?first=Vasya&last=Pupkin&amount=123.50</A>" +
                "\n<BR /><B>/add_product?name=[Name of product]&price=[Price]</B> - add new product to marketplace, e.g. <A href=/add_product?name=Bubble%20gum&price=75.50>/add_product?name=Bubble gum&price=75.50</A>" +
                "\n<BR />" +
                "\n<HR /><B>/add_purchase?user=[User ID]&product=[Product ID]</B> - add new purchase on marketplace, e.g. <A href=/add_purchase?user=1&product=1>/add_purchase?user=1&product=1</A>" +
                "\n<BR />" +
                "\n<HR /><B>/purchases_by_user?user=[User ID]</B> - print list of products from the user's purchases, e.g. <A href=/purchases_by_user?user=1>/purchases_by_user?user=1</A>" +
                "\n<BR /><B>/purchases_by_product?product=[Product ID]</B> - print list of users from product purchases, e.g. <A href=/purchases_by_product?product=1>/purchases_by_product?product=1</A>" +
                "\n<BR />" +
                "\n<HR /><B>/delete_user?id=[User ID]</B> - delete user and his purchases, e.g. <A href=/delete_user?id=1>/delete_user?id=1</A>" +
                "\n<BR /><B>/delete_product?id=[Product ID]</B> - delete product and its purchases, e.g. <A href=/delete_product?id=1>/delete_product?id=1</A>";
    }

    @RequestMapping(value = {"/load_test", "/test"})
    public ResponseEntity<?> loadTest(){
        try {
            marketplace.addTestData();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(value = {"/get_users", "/users"})
    public ResponseEntity<?> getUsers(){
        try {
            return new ResponseEntity<>(marketplace.getUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"/get_products", "/products"})
    public ResponseEntity<?> getProducts(){
        try {
            return new ResponseEntity<>(marketplace.getProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/add_user", params = {"first", "last", "amount"})
    public ResponseEntity<?> addUser(
            @RequestParam("first") String first,
            @RequestParam("last") String last,
            @RequestParam("amount") String amount ){
        try {
            marketplace.addNewUser(first, last, new BigDecimal(amount));
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/add_product", params = {"name", "price"})
    public ResponseEntity<?> addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") String price ){
        try {
            marketplace.addNewProduct(name, new BigDecimal(price));
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/add_purchase", params = {"user", "product"})
    public ResponseEntity<?> addPurchase(
            @RequestParam("user") long userId,
            @RequestParam("product") long productId ){
        try {
            return new ResponseEntity<>(
                    marketplace.addPurchase(userId, productId),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/purchases_by_user", params = "user")
    public ResponseEntity<?> getPurchaseByUser(
            @RequestParam("user") long userId ){
        try {
            return new ResponseEntity<>(
                    marketplace.getProductsFromPurchasesByUserId(userId),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/purchases_by_product", params = "product")
    public ResponseEntity<?> getPurchaseByProduct(
            @RequestParam("product") long productId ){
        try {
            return new ResponseEntity<>(
                    marketplace.getUsersFromPurchasesByProductId(productId),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete_user", params = "id")
    public ResponseEntity<?> deleteUser(
            @RequestParam("id") long id ){
        try {
            return new ResponseEntity<>(
                    marketplace.deleteUserById(id),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete_product", params = "id")
    public ResponseEntity<?> deleteProduct(
            @RequestParam("id") long id ){
        try {
            return new ResponseEntity<>(
                    marketplace.deleteProductById(id),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
