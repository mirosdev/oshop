package com.example.springframework.oshop.bootstrap;

import com.example.springframework.oshop.domain.Category;
import com.example.springframework.oshop.domain.Product;
import com.example.springframework.oshop.domain.Role;
import com.example.springframework.oshop.domain.User;
import com.example.springframework.oshop.services.interfaces.CategoryService;
import com.example.springframework.oshop.services.interfaces.ProductService;
import com.example.springframework.oshop.services.interfaces.RoleService;
import com.example.springframework.oshop.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;
    private final RoleService roleService;
    private final ProductService productService;

    @Autowired
    public BootstrapData(UserService userService,
                         CategoryService categoryService,
                         RoleService roleService,
                         ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.roleService = roleService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        //Role
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        roleService.saveRole(role);

        // Admin user
        User user = new User();
        user.setPassword("password");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setEmail("test@test.com");
        userService.saveUser(user);

        // Guest user
        User guest = new User();
        guest.setPassword("password");
        guest.setRoles(null);
        guest.setEmail("guest@test.com");
        userService.saveUser(guest);

        // Categories
        Category bread = new Category("Bread", "bread");
        Category dairy = new Category("Dairy", "dairy");
        Category fruits = new Category("Fruits", "fruits");
        Category seasoningsAndSpices = new Category("Seasonings and Spices", "seasoningsAndSpices");
        Category vegetables = new Category("Vegetables", "vegetables");

        List<Category> categories = new ArrayList<>();
        categories.add(bread);
        categories.add(dairy);
        categories.add(fruits);
        categories.add(seasoningsAndSpices);
        categories.add(vegetables);

        categoryService.saveAll(categories);

        // Products
        Product broccoli = new Product();
        broccoli.setTitle("Broccoli");
        broccoli.setPrice(2.2F);
        broccoli.setCategory("vegetables");
        broccoli.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Broccoli.jpg/1200px-Broccoli.jpg");

        Product beans = new Product();
        beans.setTitle("Beans");
        beans.setPrice(2.5F);
        beans.setCategory("vegetables");
        beans.setImageUrl("https://farm6.staticflickr.com/5011/5535638066_e22a345211_b.jpg");

        Product bagels = new Product();
        bagels.setTitle("Bagels");
        bagels.setPrice(1.9F);
        bagels.setCategory("bread");
        bagels.setImageUrl("https://www.publicdomainpictures.net/pictures/210000/velka/pieczywo-w-koszyku.jpg");

        Product breadProd = new Product();
        breadProd.setTitle("Bread");
        breadProd.setPrice(1.5F);
        breadProd.setCategory("bread");
        breadProd.setImageUrl("https://images.pexels.com/photos/209403/pexels-photo-209403.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");

        Product peaches = new Product();
        peaches.setTitle("Peaches");
        peaches.setPrice(2.5F);
        peaches.setCategory("fruits");
        peaches.setImageUrl("https://media.defense.gov/2015/Jun/04/2001053262/-1/-1/0/150602-F-BH656-005.JPG");

        Product bananas = new Product();
        bananas.setTitle("Bananas");
        bananas.setPrice(2.9F);
        bananas.setCategory("fruits");
        bananas.setImageUrl("https://cityportal.hr/wp-content/uploads/2018/02/banane.jpg");

        List<Product> products = new ArrayList<>();
        products.add(broccoli);
        products.add(beans);
        products.add(bagels);
        products.add(breadProd);
        products.add(peaches);
        products.add(bananas);
        productService.saveAll(products);
    }
}
