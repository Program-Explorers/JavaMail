import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {

    private static String USER_NAME = "";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = ""; // GMail password
    private static String RECIPIENT = "";

    public static void main(String[] args) {
        String subject, body;
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter the your Gmail user name (just the part before @gmail.com): ");
        USER_NAME = keyboard.nextLine();

        System.out.print("\nEnter in your password: ");
        PASSWORD = keyboard.nextLine();

        System.out.print("\nEnter in the full email of your recipient: ");
        RECIPIENT = keyboard.nextLine();

        System.out.print("\nEnter in the subject of your email: ");
        subject = keyboard.nextLine();

        System.out.print("\nEnter in the message of the email: ");
        body = keyboard.nextLine();

        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT }; // list of recipient email addresses

        sendFromGMail(from, pass, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}