package FA.JustBlog.Core.controller;

import FA.JustBlog.Core.models.Email;
import FA.JustBlog.Core.models.response.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailNotificationController {
    JavaMailSender javaMailSender;
    private static final Logger logger = LoggerFactory.getLogger(MailNotificationController.class);

    @PostMapping("/send-email-message")
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public ResponseEntity<ApiResponse<String>> sendEmailMessage(@RequestBody Email emailDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(emailDto.getTo());
        simpleMailMessage.setSubject(emailDto.getSubject());
        simpleMailMessage.setText(emailDto.getText());

        javaMailSender.send(simpleMailMessage);

        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Email sent successfully !!")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/send-email")
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public ResponseEntity<ApiResponse<String>> sendEmail(@ModelAttribute Email emailDto) throws MessagingException, IOException {
        logger.info("Starting sendEmail method");

        if (javaMailSender == null) {
            logger.error("JavaMailSender is not initialized");
            throw new IllegalStateException("JavaMailSender is not initialized");
        }

        if (emailDto == null) {
            logger.error("Email DTO is null");
            throw new IllegalArgumentException("Email DTO is null");
        }

        logger.info("Creating MimeMessage");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        if (emailDto.getTo() == null) {
            logger.error("Recipient email address is null");
            throw new IllegalArgumentException("Recipient email address is null");
        }

        if (emailDto.getSubject() == null) {
            logger.error("Email subject is null");
            throw new IllegalArgumentException("Email subject is null");
        }

        if (emailDto.getText() == null) {
            logger.error("Email text is null");
            throw new IllegalArgumentException("Email text is null");
        }

        logger.info("Setting email fields");
        mimeMessageHelper.setTo(emailDto.getTo());
        mimeMessageHelper.setSubject(emailDto.getSubject());
        mimeMessageHelper.setText(emailDto.getText(), true);

        if (emailDto.getAttachment() != null && !emailDto.getAttachment().isEmpty()) {
            String originalFilename = emailDto.getAttachment().getOriginalFilename();
            if (originalFilename != null && !originalFilename.isEmpty()) {
                logger.info("Adding attachment: " + originalFilename);
                File attachment = convertMultipartToFile(emailDto.getAttachment(), originalFilename);
                mimeMessageHelper.addAttachment(originalFilename, attachment);
            } else {
                logger.warn("Attachment filename is null or empty");
            }
        }

        logger.info("Sending email");
        javaMailSender.send(mimeMessage);

        logger.info("Email sent successfully");

        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Email sent successfully !!")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    private static File convertMultipartToFile(MultipartFile multipartFile, String fileName) throws IOException {
        logger.info("Converting multipart file to file: " + fileName);
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);

        if (!convFile.getParentFile().exists()) {
            logger.info("Creating temporary directory: " + convFile.getParentFile().getAbsolutePath());
            convFile.getParentFile().mkdirs();
        }

        multipartFile.transferTo(convFile);
        logger.info("File created: " + convFile.getAbsolutePath());
        return convFile;
    }
}
