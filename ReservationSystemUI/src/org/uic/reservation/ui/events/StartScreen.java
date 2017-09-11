package org.uic.reservation.ui.events;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.uic.reservation.ui.dataobjects.Login;
import org.uic.reservation.ui.dataobjects.ServiceResponse;
import org.uic.reservation.ui.dataobjects.Users;
import org.uic.reservation.ui.services.LoginClientService;
import org.uic.reservation.ui.services.UsersClientService;

import com.google.gson.Gson;

public class StartScreen extends JFrame implements ActionListener, MouseListener {

	JFrame frame1 = new JFrame();
	JFrame frame2 = new JFrame();
	JFrame frame3 = new JFrame();
	JFrame frame4 = new JFrame();

	JButton signUpButton = null;
	JButton loginButton = null;

	JButton chooseSeats = null;
	JButton backToFrame2 = null;
	JButton buySeats = null;
	JButton confirmPlan = null;
	JButton reSelectSeats = null;
	JButton cancel = null;

	JTextField usernameField = null;
	JPasswordField passwordField = null;

	JTextField firstNameField = null;
	JPasswordField newPasswordField = null;
	JTextField lastNameField = null;
	JTextField emailIDField = null;

	JComboBox<String> seatsDropDown = null;
	JLabel chosenSeatLabel = null;
	boolean userAuthenticated = false;
	int numberOfSeatsChosen = 0;
	int seatCounter = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		StartScreen ob1 = new StartScreen();
		ob1.launchFrame1();

	}

	private boolean validateLoginEntries() {
		boolean isValid = true;
		try {
			if (usernameField.getText().isEmpty())
				isValid = false;

			if (passwordField.getPassword().length < 6)
				isValid = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isValid;
	}

	private boolean validateSignUpEntries() {
		boolean isValid = true;
		try {
			if (firstNameField.getText().isEmpty())
				isValid = false;
			if (lastNameField.getText().isEmpty())
				isValid = false;
			if (emailIDField.getText().isEmpty())
				isValid = false;
			if (newPasswordField.getPassword().length < 6)
				isValid = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isValid;
	}

	private ServiceResponse signUpUser() {
		Users user = new Users();
		user.setFirstName(firstNameField.getText().toString().trim());
		user.setLastName(lastNameField.getText().toString().trim());
		user.setEmailId(emailIDField.getText().toString().trim());
		user.setPassword(newPasswordField.getText().toString().trim());
		UsersClientService usersClientService = new UsersClientService();
		ServiceResponse response = usersClientService.signupUser(user);
		return response;

	}

	private void launchFrame1() {

		JPanel logoPanel = new JPanel();
		JPanel title1Panel = new JPanel();
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		JPanel signUpPanel = new JPanel();
		signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));
		JPanel emptyFooter = new JPanel();
		JLabel title = null;
		try {
			title = new JLabel(new ImageIcon("SPI_Cinemas_logo.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		title.setFont(new Font("Serif", Font.BOLD, 35));
		logoPanel.setSize(10, 30);
		logoPanel.add(title);
		logoPanel.setBackground(Color.white);

		JLabel set1 = new JLabel("Welcome!");
		set1.setFont(new Font("Serif", Font.BOLD, 35));
		title1Panel.setSize(40, 40);
		title1Panel.add(set1);
		title1Panel.setBackground(Color.LIGHT_GRAY);

		// login
		JLabel noteUName = new JLabel("Username");
		noteUName.setFont(new Font("Serif", Font.BOLD, 30));

		JLabel notePassword = new JLabel("Password:");
		notePassword.setFont(new Font("Serif", Font.BOLD, 30));

		usernameField = new JTextField();
		passwordField = new JPasswordField();

		loginButton = new JButton("Login");
		loginButton.setFont(new Font("Serif", Font.BOLD, 25));
		loginButton.addActionListener(this);

		// loginPanel.setMaximumSize(new Dimension(200, 300));
		loginPanel.setBorder(BorderFactory.createEmptyBorder(250, 200, 400, 700));
		loginPanel.add(noteUName);
		loginPanel.add(usernameField);
		loginPanel.add(notePassword);
		loginPanel.add(passwordField);
		loginPanel.add(loginButton);
		loginPanel.setBackground(Color.white);

		// Sign up:

		JLabel title2 = new JLabel("New User? Sign Up:");
		title2.setFont(new Font("Serif", Font.BOLD, 35));

		JLabel fName = new JLabel("First Name:");
		fName.setFont(new Font("Serif", Font.BOLD, 25));

		JLabel lName = new JLabel("Last Name:");
		lName.setFont(new Font("Serif", Font.BOLD, 25));

		JLabel emailID = new JLabel("Email ID:");
		emailID.setFont(new Font("Serif", Font.BOLD, 25));

		JLabel newPassword = new JLabel("New Password:");
		newPassword.setFont(new Font("Serif", Font.BOLD, 25));

		firstNameField = new JTextField();
		newPasswordField = new JPasswordField();
		lastNameField = new JTextField();
		emailIDField = new JTextField();

		signUpButton = new JButton("Sign up");
		signUpButton.setFont(new Font("Serif", Font.BOLD, 25));

		signUpButton.addActionListener(this);

		signUpPanel.setBorder(BorderFactory.createEmptyBorder(100, 70, 400, 50));
		signUpPanel.add(title2);
		signUpPanel.add(fName);
		signUpPanel.add(firstNameField);
		signUpPanel.add(lName);
		signUpPanel.add(lastNameField);
		signUpPanel.add(emailID);
		signUpPanel.add(emailIDField);
		signUpPanel.add(newPassword);
		signUpPanel.add(newPasswordField);
		signUpPanel.add(signUpButton);
		signUpPanel.setBackground(Color.pink);

		frame1.setVisible(true);
		frame1.setSize(2000, 1000);
		frame1.getContentPane().add(title1Panel, BorderLayout.NORTH);
		frame1.getContentPane().add(logoPanel, BorderLayout.WEST);
		frame1.getContentPane().add(loginPanel, BorderLayout.CENTER);
		frame1.getContentPane().add(signUpPanel, BorderLayout.EAST);
		frame1.getContentPane().add(emptyFooter, BorderLayout.SOUTH);

	}

	private void launchFrame2() {

		frame2 = new JFrame();
		frame2.setVisible(true);
		frame2.setSize(2000, 1000);
		frame1.setVisible(false);
		frame3.setVisible(false);
		frame4.setVisible(false);
		seatCounter = 0;
		numberOfSeatsChosen = 0;
		JPanel headingPanel = new JPanel();
		JPanel showNamesPanel = new JPanel();
		JPanel numberOfSeatsPanel = new JPanel();
		JPanel showTimePanel = new JPanel();

		showNamesPanel.setLayout(new BoxLayout(showNamesPanel, BoxLayout.X_AXIS));

		// Heading
		JLabel movies = new JLabel("Pick a movie to watch:");
		movies.setFont(new Font("Serif", Font.BOLD, 45));
		headingPanel.add(movies);
		headingPanel.setBackground(Color.LIGHT_GRAY);

		JLabel[] moviePosters = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			String fileName = "Movie" + (i + 1) + ".png";
			moviePosters[i] = new JLabel(new ImageIcon(fileName));
			moviePosters[i].addMouseListener(this);
			showNamesPanel.add(moviePosters[i]);
		}
		showNamesPanel.setBackground(Color.red);

		// Number of seats:
		JLabel noOfSeatsLabel = new JLabel("Pick number of seats:");
		noOfSeatsLabel.setFont(new Font("Serif", Font.BOLD, 20));
		String[] noOfSeats = { "0", "1", "2", "3", "4" };
		seatsDropDown = new JComboBox<String>(noOfSeats);
		seatsDropDown.addActionListener(this);
		chooseSeats = new JButton("Proceed to select Seats");
		chooseSeats.addActionListener(this);
		seatsDropDown.setVisible(true);
		numberOfSeatsPanel.add(noOfSeatsLabel);
		numberOfSeatsPanel.add(seatsDropDown);
		numberOfSeatsPanel.add(chooseSeats);

		numberOfSeatsPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

		// Show timing:
		JLabel timeHeading = new JLabel("Show timings: 4:00 A.M.");
		timeHeading.setFont(new Font("Serif", Font.BOLD, 20));
		showTimePanel.add(timeHeading);
		showTimePanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

		frame2.getContentPane().add(headingPanel, BorderLayout.NORTH);
		frame2.getContentPane().add(showNamesPanel, BorderLayout.CENTER);
		frame2.getContentPane().add(numberOfSeatsPanel, BorderLayout.SOUTH);
		frame2.getContentPane().add(showTimePanel, BorderLayout.EAST);

	}

	private void launchFrame3() {
		frame3 = new JFrame();
		frame3.setSize(2000, 1000);
		frame3.setVisible(true);
		frame2.setVisible(false);
		frame4.setVisible(false);

		JPanel logoPanel = new JPanel();
		logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.LINE_AXIS));
		JLabel logo = new JLabel(new ImageIcon("SPI_Cinemas_logo.png"));
		logo.setFont(new Font("Serif", Font.BOLD, 35));
		JLabel screen = new JLabel("(Screen this way)");
		screen.setBorder(new EmptyBorder(0, 420, 0, 0));
		screen.setFont(new Font("Serif", Font.BOLD, 35));

		logoPanel.setBorder(BorderFactory.createEmptyBorder(80, 100, 80, 100));
		logoPanel.setBackground(Color.pink);
		logoPanel.setSize(10, 30);
		logoPanel.add(logo);
		logoPanel.add(screen);

		JPanel seatHeadingPanel = new JPanel();
		seatHeadingPanel.setLayout(new BoxLayout(seatHeadingPanel, BoxLayout.Y_AXIS));
		JLabel SeatHeading = new JLabel("Choose your seats");
		SeatHeading.setBorder(BorderFactory.createEmptyBorder(45, 175, 100, 175));
		SeatHeading.setFont(new Font("Serif", Font.BOLD, 30));
		seatHeadingPanel.setBackground(Color.pink);
		seatHeadingPanel.add(SeatHeading);

		JPanel rows = new JPanel();
		rows.setLayout(new FlowLayout());
		SeatsPane seats = new SeatsPane();
		rows.add(seats);
		rows.setBorder(BorderFactory.createEmptyBorder(20, 100, 400, 100));
		rows.setBackground(Color.pink);

		JPanel selectedSeatsPanel = new JPanel();
		selectedSeatsPanel.setBorder(BorderFactory.createEmptyBorder(45, 175, 150, 175));
		selectedSeatsPanel.setLayout(new BoxLayout(selectedSeatsPanel, BoxLayout.Y_AXIS));

		JLabel selectedSeats = new JLabel("Selected seats");
		selectedSeats.setFont(new Font("Serif", Font.BOLD, 30));
		chosenSeatLabel = new JLabel();
		chosenSeatLabel.setFont(new Font("Serif", Font.BOLD, 15));

		selectedSeatsPanel.setBackground(Color.pink);
		selectedSeatsPanel.add(selectedSeats);
		selectedSeatsPanel.add(chosenSeatLabel);

		JPanel move = new JPanel();
		move.setBorder(BorderFactory.createEmptyBorder(150, 20, 150, 20));
		move.setBackground(Color.pink);
		backToFrame2 = new JButton("<- Back to choose a movie");
		// backToFrame2.setSize(new Dimension(100, 100));
		backToFrame2.setPreferredSize(new Dimension(200, 50));
		backToFrame2.addActionListener(this);
		buySeats = new JButton("Next to confirm plan ->");
		buySeats.setPreferredSize(new Dimension(200, 50));
		buySeats.addActionListener(this);
		move.add(backToFrame2);
		move.add(buySeats);

		frame3.getContentPane().add(logoPanel, BorderLayout.NORTH);
		frame3.getContentPane().add(seatHeadingPanel, BorderLayout.WEST);
		frame3.getContentPane().add(rows, BorderLayout.CENTER);
		frame3.getContentPane().add(move, BorderLayout.SOUTH);
		frame3.getContentPane().add(selectedSeatsPanel, BorderLayout.EAST);
	}

	private void launchFrame4() {
		frame4 = new JFrame();
		frame4.setSize(2000, 1000);
		frame4.setVisible(true);
		frame3.setVisible(false);
		JPanel orderSummaryPanel = new JPanel();
		orderSummaryPanel.setBorder(BorderFactory.createEmptyBorder(300, 750, 100, 100));
		orderSummaryPanel.setLayout(new BoxLayout(orderSummaryPanel, BoxLayout.Y_AXIS));

		JLabel orderLabel = new JLabel("Order summary:");
		orderLabel.setFont(new Font("Serif", Font.BOLD, 55));
		String movieName = "Movie Name: ";// Fetch from server
		JLabel movieLabel = new JLabel(movieName);
		movieLabel.setFont(new Font("Serif", Font.BOLD, 35));

		String time = "Show time: ";// Fetch from server
		JLabel timeLabel = new JLabel(time);
		timeLabel.setFont(new Font("Serif", Font.BOLD, 35));

		String seats = "No. of seats: " + numberOfSeatsChosen;// Fetch from
																// server
		JLabel seatsLabel = new JLabel(seats);
		seatsLabel.setFont(new Font("Serif", Font.BOLD, 35));

		orderSummaryPanel.add(orderLabel);
		orderSummaryPanel.add(movieLabel);
		orderSummaryPanel.add(timeLabel);
		orderSummaryPanel.add(seatsLabel);
		orderSummaryPanel.setBackground(Color.PINK);

		JPanel move = new JPanel();
		confirmPlan = new JButton("Confirm");
		confirmPlan.setPreferredSize(new Dimension(200, 50));
		confirmPlan.addActionListener(this);

		cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(200, 50));
		cancel.addActionListener(this);

		reSelectSeats = new JButton("Reselect seats");
		reSelectSeats.setPreferredSize(new Dimension(200, 50));
		reSelectSeats.addActionListener(this);
		move.add(confirmPlan);
		move.add(cancel);
		move.add(reSelectSeats);

		frame4.getContentPane().add(orderSummaryPanel, BorderLayout.CENTER);
		frame4.getContentPane().add(move, BorderLayout.SOUTH);
	}

	private String authenticateUser() {
		Login login = new Login();
		LoginClientService loginClientService = new LoginClientService();
		login.setEmailId(usernameField.getText().toString().trim());
		login.setPassword(passwordField.getText().toString().trim());
		String response = loginClientService.loginUser(login);
		return response;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			boolean isValid = false;
			isValid = validateLoginEntries();
			if (isValid) {
				String response = authenticateUser();
				Gson gson = new Gson();
				ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
				if (!serviceResponse.isHasErrors()) {
					// Logic to hit the /shows api and do the mapping for each
					// performance with response
					launchFrame2();
				}else{
					JOptionPane.showMessageDialog(null, "Please enter a valid username/password");
				}
					
			} else {
				JOptionPane.showMessageDialog(null, "Invalid entries!");
			}
		} else if (e.getSource() == signUpButton) {
			boolean isValid = false;
			isValid = validateSignUpEntries();
			if (isValid) {
				ServiceResponse response = signUpUser();
				if (response.isHasErrors()) {
					JOptionPane.showMessageDialog(null, "Please enter valid details or the email id may already be registered");
				} else {
					JOptionPane.showMessageDialog(null, "Sign Up was successful, proceed to login!");
					launchFrame1();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Invalid entries!");
			}
		} else if (e.getSource() == chooseSeats) {
			if (numberOfSeatsChosen == 0) {
				JOptionPane.showMessageDialog(null, "Choose Number of seats!");
			} else
				//initiateOrder(numberOfSeatsChosen,753);
				launchFrame3();
		} else if (e.getSource() == seatsDropDown)
			numberOfSeatsChosen = Integer.parseInt(seatsDropDown.getSelectedItem().toString());
		else if (e.getSource() == cancel)
			launchFrame2();
		else if (e.getSource() == buySeats)
			
			launchFrame4();
		else if (e.getSource() == confirmPlan) {
			JOptionPane.showMessageDialog(null, "Success!");
			launchFrame2();
		} else if (e.getSource() == reSelectSeats)
			launchFrame3();
		else if (e.getSource() == backToFrame2)
			launchFrame2();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JOptionPane.showMessageDialog(null, "Available show timing: 4:00:00");

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public class SeatsPane extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SeatsPane() {
			setLayout(new GridBagLayout());
			Map<String, String> map = new HashMap<String, String>();
			map.put("1", "A");
			map.put("2", "B");
			map.put("3", "C");
			map.put("4", "D");
			map.put("5", "E");

			GridBagConstraints gbc = new GridBagConstraints();
			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 20; col++) {
					gbc.gridx = col;
					gbc.gridy = row;

					CellPane cellPane = new CellPane();
					int rownum = 1 + row;
					int colnum = 1 + col;
					cellPane.setName(map.get(Integer.toString(rownum)) + colnum);
					cellPane.setToolTipText(map.get(Integer.toString(rownum)) + colnum);
					Border border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
					cellPane.setBorder(border);
					add(cellPane, gbc);
				}
			}
		}
	}

	public class CellPane extends JPanel {
		/**
		 * 
		 */

		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		private Color defaultBackground;

		public CellPane() {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					++seatCounter;
					if (seatCounter <= numberOfSeatsChosen) {
						defaultBackground = getBackground();
						setBackground(Color.green);

						String source = e.getSource().toString();
						String seatName = source.substring(source.indexOf("[") + 1, source.indexOf(","));
						String current = chosenSeatLabel.getText();
						if (seatCounter == 1)
							chosenSeatLabel.setText(seatName);
						else
							chosenSeatLabel.setText(current + ", " + seatName);
					}
				}

			});
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(40, 40);
		}
	}
}