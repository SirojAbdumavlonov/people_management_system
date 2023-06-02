package com.example.springmvc.newcontrollers;


import com.example.springmvc.dao.PersonDAO;
import com.example.springmvc.models.Person;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    public PeopleController(PersonDAO personDAO){
        this.personDAO = personDAO;
    }


    @GetMapping()//people page
    public String index(Model model){
        model.addAttribute("people",personDAO.index());
        return "people/index.html";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personDAO.show(id));
        return "people/person.html";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person",new Person());

        return "people/new.html";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person
                        ,BindingResult bindingResult){


        if(bindingResult.hasErrors()){
            return "people/new.html";
        }

        personDAO.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id) {
        model.addAttribute("person",personDAO.show(id));
        return "people/edit.html";
    }
    //Binding result is necessary to find errors while filling fields
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult,@PathVariable("id") int id) throws SQLException {

        if(bindingResult.hasErrors()){
            return "people/edit.html";
        }

        personDAO.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
