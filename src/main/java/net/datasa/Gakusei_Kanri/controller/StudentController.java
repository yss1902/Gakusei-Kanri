package net.datasa.Gakusei_Kanri.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.datasa.Gakusei_Kanri.domain.dto.StudentDTO;
import net.datasa.Gakusei_Kanri.domain.dto.StudentForm;
import net.datasa.Gakusei_Kanri.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public String list(Model model) {
        List<StudentDTO> students = studentService.findAll();
        model.addAttribute("students", students);
        return "students/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        return "students/new";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("studentForm") StudentForm studentForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "students/new";
        }
        StudentDTO created = studentService.create(studentForm);
        redirectAttributes.addFlashAttribute("message", "Student created with ID " + created.getId());
        return "redirect:/students";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        StudentDTO student = studentService.findOne(id);
        model.addAttribute("student", student);
        return "students/detail";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudentForm form = studentService.getForm(id);
        model.addAttribute("studentForm", form);
        model.addAttribute("studentId", id);
        return "students/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("studentForm") StudentForm studentForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("studentId", id);
            return "students/edit";
        }
        studentService.update(id, studentForm);
        redirectAttributes.addFlashAttribute("message", "Student updated.");
        return "redirect:/students/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Student deleted.");
        return "redirect:/students";
    }
}
