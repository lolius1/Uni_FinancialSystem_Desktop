package GSONSerializable;

import com.google.gson.*;
import model.Category;
import model.Expenditure;
import model.Income;
import model.User;

import java.lang.reflect.Type;

public class CategoryGSONSerializer implements JsonSerializer<Category> {
  @Override
  public JsonElement serialize(
      Category category, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject catJson = new JsonObject();
    JsonObject incomeJson = new JsonObject();
    JsonObject expenditureJson = new JsonObject();
    JsonObject userJson = new JsonObject();
    catJson.addProperty("category_ID", category.getCategory_ID());
    catJson.addProperty("name", category.getName());
    catJson.addProperty("description", category.getDescription());
    catJson.addProperty("parent", category.getParent());

    for (Income i : category.getIncome()) {
      incomeJson.addProperty("income_ID", i.getIncome_ID());
      incomeJson.addProperty("Name", i.getName());
      incomeJson.addProperty("Amount", i.getAmount());
    }

    for (Expenditure e: category.getExpenditure()) {
      expenditureJson.addProperty("expenditure_ID", e.getExpenditure_ID());
      expenditureJson.addProperty("Name", e.getName());
      expenditureJson.addProperty("Amount", e.getAmount());
    }

    for (User u: category.getResponsibleUsers()) {
      userJson.addProperty("user_ID", u.getUser_ID());
      userJson.addProperty("Name", u.getName());
      userJson.addProperty("Login", u.getLogin());
    }

    catJson.add("income", incomeJson);
    catJson.add("expenditure", expenditureJson);
    catJson.add("responsibleUsers",userJson);

    return catJson;
  }
}
