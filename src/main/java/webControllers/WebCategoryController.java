package webControllers;

import GSONSerializable.AllCategoryGSONSerializer;
import GSONSerializable.CategoryGSONSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import hibernate.CategoryHibernate;
import model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.util.List;

@Controller
public class WebCategoryController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("KursinioHibernate");
    CategoryHibernate categoryHibernateControl = new CategoryHibernate(entityManagerFactory);

    @RequestMapping(value = "/Category/catList")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllCategories() {

        List<Category> allCat = categoryHibernateControl.getCategoryList();

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Category.class, new CategoryGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(allCat.get(0));

        Type categoryList = new TypeToken<List<Category>>() {
        }.getType();
        gson.registerTypeAdapter(categoryList, new AllCategoryGSONSerializer());
        parser = gson.create();

        return parser.toJson(allCat);
    }

    @RequestMapping(value = "/Category/CategoryInfo", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategoryInfoById(@RequestParam("id") int id) {

        Category category = categoryHibernateControl.findCategory(id);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Category.class, new CategoryGSONSerializer());
        Gson parser = gson.create();
        return parser.toJson(category);
    }
}
