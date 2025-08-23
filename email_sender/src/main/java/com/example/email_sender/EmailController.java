package com.example.email_sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class EmailController {

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/")
    public String showForm() {
        return "emailForm";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("toEmail") String toEmail,
                            @RequestParam("subject") String subject,
                            @RequestParam("body") String body,
                            Model model) {
        try {
            emailSenderService.sendEmail(toEmail, subject, body);
            model.addAttribute("message", "✅ Email sent successfully to " + toEmail);
        } catch (Exception e) {
            model.addAttribute("message", "❌ Error sending email: " + e.getMessage());
        }
        return "emailForm";
    }
}
