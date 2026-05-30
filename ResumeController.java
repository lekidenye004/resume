package com.resume.resume.Controller;

import com.resume.resume.model.ContactMessage;
import com.resume.resume.Service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class ResumeController {

    private final ContactService contactService;

    public ResumeController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Populate resume data (same as before)
        model.addAttribute("name", "Alex Johnson");
        model.addAttribute("title", "Senior Full Stack Developer");
        model.addAttribute("summary", "Passionate developer with 8+ years of experience building scalable web applications. Expertise in Java, Spring Boot, React, and cloud technologies.");
        model.addAttribute("email", "alex.johnson@example.com");
        model.addAttribute("phone", "+1 (555) 123-4567");
        model.addAttribute("location", "San Francisco, CA");
        model.addAttribute("github", "https://github.com/alexjohnson");
        model.addAttribute("linkedin", "https://linkedin.com/in/alexjohnson");

        model.addAttribute("skills", Map.of(
                "Backend", new String[]{"Java", "Spring Boot", "Node.js", "Python"},
                "Frontend", new String[]{"React", "Angular", "Thymeleaf", "Bootstrap"},
                "Database", new String[]{"MySQL", "PostgreSQL", "MongoDB", "H2"},
                "DevOps", new String[]{"Docker", "Kubernetes", "AWS", "Jenkins"}
        ));

        model.addAttribute("experiences", new Object[]{
                Map.of("title", "Senior Software Engineer", "company", "TechCorp Inc.", "period", "2021 – Present", "description", "Lead backend development for microservices architecture, improved API response time by 40%."),
                Map.of("title", "Full Stack Developer", "company", "Innovate Solutions", "period", "2018 – 2021", "description", "Developed and maintained client portals using React and Spring Boot, integrated payment gateways."),
                Map.of("title", "Junior Developer", "company", "StartUp Hub", "period", "2016 – 2018", "description", "Assisted in building e-commerce platforms, bug fixing, and unit testing.")
        });

        model.addAttribute("educations", new Object[]{
                Map.of("degree", "M.Sc. in Computer Science", "institution", "Stanford University", "year", "2016"),
                Map.of("degree", "B.E. in Software Engineering", "institution", "University of Washington", "year", "2014")
        });

        model.addAttribute("projects", new Object[]{
                Map.of("name", "E-Commerce Microservices", "tech", "Spring Boot, Kafka, Docker", "description", "Built scalable order processing system handling 10k+ requests/sec."),
                Map.of("name", "Portfolio Generator", "tech", "React, Thymeleaf, AWS", "description", "Web app allowing developers to create resumes dynamically."),
                Map.of("name", "Chat Application", "tech", "WebSocket, MongoDB, Vue.js", "description", "Real-time messaging platform with end-to-end encryption.")
        });

        // Create an empty ContactMessage object for the form (not required for manual params)
        model.addAttribute("contactMessage", new ContactMessage());
        return "index";
    }

    @PostMapping("/contact/send")
    public String sendMessage(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String subject,
                              @RequestParam String message,
                              RedirectAttributes redirectAttributes) {

        // Manual validation
        if (name == null || name.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Name is required.");
            return "redirect:/#contact";
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            redirectAttributes.addFlashAttribute("error", "Valid email is required.");
            return "redirect:/#contact";
        }
        if (subject == null || subject.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Subject is required.");
            return "redirect:/#contact";
        }
        if (message == null || message.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Message cannot be empty.");
            return "redirect:/#contact";
        }

        // Save the message
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(name.trim());
        contactMessage.setEmail(email.trim());
        contactMessage.setSubject(subject.trim());
        contactMessage.setMessage(message.trim());
        contactService.saveMessage(contactMessage);

        redirectAttributes.addFlashAttribute("success", "Your message has been sent. I'll get back to you soon!");
        return "redirect:/#contact";
    }
}