package GSONSerializable;

import com.google.gson.*;
import model.Category;
import model.User;

import java.lang.reflect.Type;
import java.util.List;

public class AllUserGSONSerializer implements JsonSerializer<List<User>> {
    @Override
    public JsonElement serialize(List<User> users, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserGSONSerializer());
        Gson parser = gsonBuilder.create();

        for (User u: users) {
            jsonArray.add(parser.toJson(u));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
