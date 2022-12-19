package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Category;
import model.Expenditure;
import model.Income;
import model.User;

import java.lang.reflect.Type;

public class UserGSONSerializer implements JsonSerializer<User> {
  @Override
  public JsonElement serialize(
      User user, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject userJson = new JsonObject();
    JsonObject catJson = new JsonObject();
    userJson.addProperty("user_ID", user.getUser_ID());
    userJson.addProperty("name", user.getName());
    userJson.addProperty("surname", user.getSurname());
    userJson.addProperty("login", user.getLogin());
    userJson.addProperty("password", user.getPsw());
    userJson.addProperty("type", user.getType());

    for (Category c: user.getResponsibleCategories()) {
      catJson.addProperty("category_ID", c.getCategory_ID());
      catJson.addProperty("name", c.getName());
      catJson.addProperty("description", c.getDescription());
    }
    userJson.add("responsibleCategories",catJson);

    return userJson;
  }
}
