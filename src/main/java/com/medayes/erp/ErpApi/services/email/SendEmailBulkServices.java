package com.medayes.erp.ErpApi.services.email;

import com.medayes.erp.ErpApi.dto.request.task.RecipientDTO;
import com.medayes.erp.ErpApi.dto.request.task.SendEmailDTO;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;






@Service
@EnableAsync
public class SendEmailBulkServices {
    private final JavaMailSender mailSender;
    @Value("${mail.from}")
    private String fromEmail;
    @Autowired
    SendEmailBulkServices(JavaMailSender javaMailSender)
    {
        this.mailSender=javaMailSender;
    }

@Async
    public void sendEmail(SendEmailDTO dto) {
        for(RecipientDTO r:dto.recipients())
        {
        try {
            String emailMessage =
                    "Hi " + r.personName() + ",<br><br>" +
                            "You have been assigned a task by <b>" + dto.assignedBy() + " (" + r.personName()+ ")</b>.<br><br>" +
                            "Task Details<br>" +
                            "Task Name: " + dto.taskName() + "<br>" +
                            "Description (Remarks): " + dto.remake() + "<br>" +
                            "Priority: " + dto.priority() + "<br>" +
                            "Deadline: " + dto.deadline() + "<br><br>" +
                            "Please complete the task as soon as possible.";




            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(r.email());
            helper.setSubject("New task assigned");
            helper.setText(emailMessage, true); // true = HTML

            mailSender.send(mimeMessage);

            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    public void sendEmailOtp(SendEmailDTO dto) {
        for(RecipientDTO r:dto.recipients())
        {
            try {
                String emailMessage =
                        "Hi "+"/n"+
                                "You OTP for login  is" + dto.otp()  +
                                "Do not  share it with anyone";




                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper =
                        new MimeMessageHelper(mimeMessage, true, "UTF-8");

                helper.setFrom(fromEmail);
                helper.setTo(r.email());
                helper.setSubject("OTP for login");
                helper.setText(emailMessage, true); // true = HTML

                mailSender.send(mimeMessage);

                System.out.println("Email sent successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}



