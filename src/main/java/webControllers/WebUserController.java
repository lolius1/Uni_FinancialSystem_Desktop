package webControllers;

import GSONSerializable.AllUserGSONSerializer;
import GSONSerializable.UserGSONSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import hibernate.UserHibernate;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.util.List;

@Controller
public class WebUserController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("KursinioHibernate");
    UserHibernate userHibernateControl = new UserHibernate(entityManagerFactory);

    @RequestMapping(value = "/User/userList")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllUsers() {

        List<User> allUsers = userHibernateControl.getUserList();

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(allUsers.get(0));

        Type userList = new TypeToken<List<User>>() {
        }.getType();
        gson.registerTypeAdapter(userList, new AllUserGSONSerializer());
        parser = gson.create();

        return parser.toJson(allUsers);
    }

    @RequestMapping(value = "/User/UserInfo", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUserInfoById(@RequestParam("id") int id) {


        User user = userHibernateControl.findUser(id);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserGSONSerializer());
        Gson parser = gson.create();
        return parser.toJson(user);
    }
}
