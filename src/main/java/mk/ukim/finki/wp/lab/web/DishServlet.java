package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "dishServlet", urlPatterns = "/dish")
public class DishServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final DishService dishService;
    private final ChefService chefService;

    public DishServlet(SpringTemplateEngine springTemplateEngine, DishService dishService, ChefService chefService) {
        this.springTemplateEngine = springTemplateEngine;
        this.dishService = dishService;
        this.chefService = chefService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chefIdParam = req.getParameter("chefId");
        Long chefId = Long.parseLong(chefIdParam);

        Chef selectedChef = chefService.findById(chefId);

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);
        context.setVariable("dishes", dishService.listDishes());
        context.setVariable("selectedChef", selectedChef);

        springTemplateEngine.process("dishesList.html", context, resp.getWriter());
    }
}