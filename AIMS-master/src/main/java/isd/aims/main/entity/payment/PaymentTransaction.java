package isd.aims.main.entity.payment;

import isd.aims.main.entity.db.DBConnection;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class PaymentTransaction {
	private String errorCode;
	private String transactionId;
	private String transactionContent;
	private int amount;
	private Integer orderID;
	private Date createdAt;

	public PaymentTransaction(String errorCode, String transactionId, String transactionContent, int amount, Date createdAt) {
		super();
		this.errorCode = errorCode;
		this.transactionId = transactionId;
		this.transactionContent = transactionContent;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	public void save(int orderId) throws SQLException {
		this.orderID = orderId;
		String query = "INSERT INTO \"Transaction\" (orderID, createAt, content) VALUES (?, ?, ?)";
		try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, orderId);
			preparedStatement.setDate(2, new java.sql.Date(createdAt.getTime()));
			preparedStatement.setString(3, transactionContent);
			preparedStatement.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public int checkPaymentByOrderId(int orderId) throws SQLException {
		int count = 0;
		String query = "SELECT COUNT(*) FROM Transaction WHERE orderID = ?";
		try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, orderId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					count = resultSet.getInt(1);
				}
			}
		}
		return count;
	}

	public boolean isSuccess() {
		// Assuming a null errorCode or an errorCode "00" means success
		return errorCode == null || "00".equals(errorCode);
	}

	// Get a message based on the success or failure of the transaction
	public String getMessage() {
		if (isSuccess()) {
			String successMessage = "Payment was successful.";
			sendEmail("boy17112003@gmail.com", successMessage);
			return successMessage;
		} else {
			return "Payment failed with error code: " + errorCode;
		}
	}

	private void sendEmail(String recipient, String messageBody) {
		final String username = "boy17112003@gmail.com"; // Replace with your email
		final String password = "tpna gobi qmcu koxj"; // Replace with your email password

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject("Payment Notification");
			message.setText(messageBody);

			Transport.send(message);
			System.out.println("Email sent successfully!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
}
