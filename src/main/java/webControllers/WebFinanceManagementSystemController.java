package webControllers;

import GSONSerializable.FinanceManagementSystemGSONSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hibernate.FinanceManagementSystemHibernate;
import model.FinanceManagementSystem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Controller
public class WebFinanceManagementSystemController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("KursinioHibernate");
    FinanceManagementSystemHibernate financeManagementSystemHibernateControl = new FinanceManagementSystemHibernate(entityManagerFactory);

    @RequestMapping(value = "/System/SystemInfo", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getFinanceManagementSystemInfoByCompany(@RequestParam("company") String company) {
        if (company.equals("")) return "No company name provided";

        FinanceManagementSystem financeManagementSystem = financeManagementSystemHibernateControl.findFinanceManagementSystem(company);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(FinanceManagementSystem.class, new FinanceManagementSystemGSONSerializer());
        Gson parser = gson.create();
        return parser.toJson(financeManagementSystem);
    }
}
