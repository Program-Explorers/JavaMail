import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {

    private static String USER_NAME = "";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = ""; // GMail password
    private static String RECIPIENT = "";
    private static String HOST = "";
    public static void main(String[] args) {
        String subject, body, option;
        Scanner keyboard = new Scanner(System.in);

        System.out.print("\033[H\033[2J");   
        System.out.flush(); 
        System.out.println("Login In To Begin");
        System.out.print("\nEnter your email address: ");
        USER_NAME = keyboard.nextLine();
        String[] user_name_split = USER_NAME.split("@");
        USER_NAME = user_name_split[0];
        HOST = user_name_split[1];
        String[] host_split = HOST.split(".com");
        HOST = host_split[0];

        System.out.print("\nEnter in your password: ");
        PASSWORD = keyboard.nextLine();
        // System.out.println("\nEmail " + USER_NAME + "@gmail.com");
        // System.out.println("Password " + PASSWORD);
        // System.out.print("\nIs this information correct(type YES OR NO) ");
        // while(option.equals("YES")){
            
        // }
        System.out.print("\033[H\033[2J");   
        System.out.flush();  

        System.out.print("\nEnter in the email of your recipient: ");
        RECIPIENT = keyboard.nextLine();

        System.out.print("\nEnter in the subject of your email: ");
        subject = keyboard.nextLine();

        System.out.print("\nEnter in the message of the email: ");
        body = keyboard.nextLine();

        System.out.print("\033[H\033[2J");   
        System.out.flush(); 

        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT }; // list of recipient email addresses

        sendFromGMail(from, pass, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp."+HOST+".com";
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

            for (InternetAddress address : toAddress) {
                message.addRecipient(Message.RecipientType.TO, address);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("\nFrom: " + USER_NAME + "@gmail.com");
            System.out.println("To: " + RECIPIENT);
            System.out.println("Subject: " + subject);
            System.out.println(body);
            System.out.println("\nSent message!");
        } catch (MessagingException ae) {
            System.out.println("Email not found! Please run the program again.");
        }
    }
}