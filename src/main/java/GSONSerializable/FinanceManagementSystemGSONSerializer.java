package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.*;

import java.lang.reflect.Type;

public class FinanceManagementSystemGSONSerializer implements JsonSerializer<FinanceManagementSystem> {
  @Override
  public JsonElement serialize(
      FinanceManagementSystem financeManagementSystem, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject systemJson = new JsonObject();
    systemJson.addProperty("company",financeManagementSystem.getCompany());
    systemJson.addProperty("name",financeManagementSystem.getName());
    systemJson.addProperty("systemVersion",financeManagementSystem.getSystemVersion());

    return systemJson;
  }
}
