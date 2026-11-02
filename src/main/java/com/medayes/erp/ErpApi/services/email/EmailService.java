package com.medayes.erp.ErpApi.services.email;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${mail.from}")
    private String fromEmail;
    @Autowired
    EmailService(JavaMailSender javaMailSender)
    {
        this.mailSender=javaMailSender;
    }

   // @Scheduled(fixedRate = 1200Ï0)
   public void sendEmail(String emailId, String taskName, String assignedBy, String deadline, String priority, String personName, String remake) {
       try {
           String emailMessage =
                   "Hi " + personName + ",<br><br>" +
                           "You have been assigned a task by <b>" + assignedBy + " (" + personName + ")</b>.<br><br>" +
                           "Task Details<br>" +
                           "Task Name: " + taskName + "<br>" +
                           "Description (Remarks): " + remake + "<br>" +
                           "Priority: " + priority + "<br>" +
                           "Deadline: " + deadline + "<br><br>" +
                           "Please complete the task as soon as possible.";




           MimeMessage mimeMessage = mailSender.createMimeMessage();
           MimeMessageHelper helper =
                   new MimeMessageHelper(mimeMessage, true, "UTF-8");
//           SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//           simpleMailMessage.setFrom(fromEmail);
//           simpleMailMessage.setTo(emailId);
//           simpleMailMessage.setSubject("New task assigned");
//           simpleMailMessage.setText(
//                   emailMessage
//           );
//
//           mailSender.send(simpleMailMessage);
           helper.setFrom(fromEmail);
           helper.setTo(emailId);
           helper.setSubject("New task assigned");
           helper.setText(emailMessage, true); // true = HTML

           mailSender.send(mimeMessage);

           System.out.println("Email sent successfully!");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }


}



