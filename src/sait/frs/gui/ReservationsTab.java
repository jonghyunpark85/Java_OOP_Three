package sait.frs.gui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import sait.frs.exception.*;
import sait.frs.manager.*;
import sait.frs.problemdomain.*;

/**
 * The travel agent can find existing flight reservations using the reservation
 * code, airline, and traveler name. The criteria can match any combination of
 * the three fields. An existing flight reservation can be modified. The
 * traveler name and citizenship can be updated. An existing flight reservation
 * can be soft-deleted, marking it as inactive and freeing up a seat on the
 * flight.
 * 
 * Holds the components for the Flights tab.
 * 
 * @author Jonghyun Park
 * @version March 05, 2020
 */
public class ReservationsTab extends TabBase {
	/**
	 * Instance of travel manager.
	 */
	private Manager manager;

	private JList<Reservation> reservationList;
	private DefaultListModel<Reservation> resevationModel;

	private JTextField codeText;
	private JTextField flightText;
	private JTextField airlineText;
	private JTextField costText;
	private JTextField nameText;
	private JTextField citizenText;
	private JComboBox<String> statusBox;

	/**
	 * Creates the components for reservations tab.
	 * 
	 */
	public ReservationsTab(Manager manager) {
		this.manager = manager;
		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);
	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();
		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	/**
	 * Creates the center panel.
	 * 
	 * @return JPanel that goes in center.
	 */

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		resevationModel = new DefaultListModel<>();
		reservationList = new JList<>(resevationModel);

		// User can only select one item at a time.
		reservationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.reservationList);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		scrollPane.setBackground(Color.WHITE);

		reservationList.addListSelectionListener(new MyListSelectionListener());
		panel.setBorder(new EmptyBorder(10, 20, 10, 20));

		panel.add(scrollPane, BorderLayout.CENTER);

		return panel;
	}

	/**
	 * The only fields that can be edited are the name, citizenship, and status.
	 * None of the other fields can be modified in any way by the user. After the
	 * travel agent has made any changes to the reservation, the update button will
	 * be clicked.
	 * 
	 * @return JPanel that goes in east.
	 */
	private JPanel createEastPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// North Inner Panel
		JLabel title = new JLabel("Reserve", SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 29));
		panel.add(title, BorderLayout.NORTH);

		// Center Inner Panel
		JPanel centerInner = new JPanel();
		centerInner.setLayout(new GridLayout(7, 2));

		JLabel code = new JLabel("Code:");
		code.setFont(new Font("Tahoma", Font.BOLD, 15));
		code.setHorizontalAlignment(SwingConstants.RIGHT);
		codeText = new JTextField(30);
		codeText.setEditable(false);
		centerInner.add(code);
		centerInner.add(codeText);

		JLabel flight = new JLabel("Flight:");
		flight.setFont(new Font("Tahoma", Font.BOLD, 15));
		flight.setHorizontalAlignment(SwingConstants.RIGHT);
		flightText = new JTextField(20);
		flightText.setEditable(false);
		centerInner.add(flight);
		centerInner.add(flightText);

		JLabel airline = new JLabel("Airline:");
		airline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		airline.setHorizontalAlignment(SwingConstants.RIGHT);
		airlineText = new JTextField(20);
		airlineText.setEditable(false);
		centerInner.add(airline);
		centerInner.add(airlineText);

		JLabel cost = new JLabel("Cost:");
		cost.setFont(new Font("Tahoma", Font.BOLD, 15));
		cost.setHorizontalAlignment(SwingConstants.RIGHT);
		costText = new JTextField(20);
		costText.setEditable(false);
		centerInner.add(cost);
		centerInner.add(costText);

		JLabel name = new JLabel("Name:");
		name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		nameText = new JTextField(20);
		centerInner.add(name);
		centerInner.add(nameText);

		JLabel citizenship = new JLabel("Citizenship:");
		citizenship.setFont(new Font("Tahoma", Font.PLAIN, 15));
		citizenship.setHorizontalAlignment(SwingConstants.RIGHT);
		citizenText = new JTextField(20);
		centerInner.add(citizenship);
		centerInner.add(citizenText);

		String[] active = { "", "Active", "Inactive" };
		JLabel status = new JLabel("Status");
		status.setFont(new Font("Tahoma", Font.BOLD, 15));
		status.setHorizontalAlignment(SwingConstants.RIGHT);
		statusBox = new JComboBox<String>(active);
		centerInner.add(status);
		centerInner.add(statusBox);

		panel.add(centerInner, BorderLayout.CENTER);

		// South Inner Panel
		JButton reserve = new JButton("Update");
		reserve.setFont(new Font("Tahoma", Font.PLAIN, 15));

		/**
		 * This reserve button works to the Reservation object will be called and an
		 * error maybe displayed if an exception occurs. The persist method in the
		 * Manager class saves all Reservation and Flight objects to a binary file on
		 * the hard drive.
		 */
		reserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameMake = nameText.getText();

				try {
					reservationList.getSelectedValue().setName(nameMake);
				} catch (InvalidNameException e1) {
					JOptionPane.showMessageDialog(null, "You do not Enter vaild Your name");
				}

				String citizenMake = citizenText.getText();
				try {
					reservationList.getSelectedValue().setCitizenship(citizenMake);
				} catch (InvalidCitizenshipException e1) {
					JOptionPane.showMessageDialog(null, "You do not Enter vaild citizenship");
				}

				if (statusBox.getSelectedItem().equals("Active")) {
					reservationList.getSelectedValue().setActive(true);
				} else if (statusBox.getSelectedItem().equals("Inactive")) {
					reservationList.getSelectedValue().setActive(false);
				}
				manager.persist();
			}
		});

		panel.add(reserve, BorderLayout.SOUTH);

		return panel;

	}

	/**
	 * A travel agent can search for an existing reservation that contains the
	 * specified reservation code, or airline or traveller’s full name. The list
	 * will be populated with any reservations that are found. Each row in the list
	 * displays the code of the corresponding reservation record.
	 * 
	 * @return JPanel that goes in south.
	 */
	private JPanel createSouthPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// North Inner Panel
		JLabel title = new JLabel("Search", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title, BorderLayout.NORTH);

		// Center Inner Panel
		JPanel centerInner = new JPanel();
		centerInner.setLayout(new BorderLayout());

		JPanel ci1 = new JPanel();
		ci1.setLayout(new GridLayout(3, 1));
		JPanel ci2 = new JPanel();
		ci2.setLayout(new GridLayout(3, 1));

		JLabel code = new JLabel("Code:");
		code.setHorizontalAlignment(SwingConstants.RIGHT);
		JTextField codeText = new JTextField(50);
		ci1.add(code);
		ci2.add(codeText);

		JLabel airline = new JLabel("Airline:");
		airline.setHorizontalAlignment(SwingConstants.RIGHT);
		JTextField airlineText = new JTextField(50);
		ci1.add(airline);
		ci2.add(airlineText);

		JLabel name = new JLabel("Name:");
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		JTextField nameText = new JTextField(50);
		ci1.add(name);
		ci2.add(nameText);

		centerInner.add(ci1, BorderLayout.WEST);
		centerInner.add(ci2, BorderLayout.CENTER);
		panel.add(centerInner, BorderLayout.CENTER);

		JButton result = new JButton("Find Reservations");

		/**
		 * This result button works to the travel agent can find existing flight
		 * reservations using the reservation code, airline, and traveler name. The
		 * criteria can match any combination of the three fields.
		 */
		result.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String code = codeText.getText();
				String airline = airlineText.getText();
				String name = nameText.getText();

				ArrayList<Reservation> findRev = manager.findReservations(code, airline, name);

				for (int i = 0; i < findRev.size(); i++) {
					resevationModel.addElement(findRev.get(i));
				}
			}
		});

		panel.add(result, BorderLayout.SOUTH);

		return panel;
	}

	private class MyListSelectionListener implements ListSelectionListener {

		/**
		 * , * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			String selectedCode = reservationList.getSelectedValue().getCode();
			String selectedFlight = reservationList.getSelectedValue().getFlight().getCode();
			String selectedAirline = reservationList.getSelectedValue().getFlight().getAirline();
			double cost = reservationList.getSelectedValue().getFlight().getCostPerSeat();
			String selectedPrice = String.format("%s%.2f", "$", cost);
			String selectedName = reservationList.getSelectedValue().getName();
			String selectedCitizenship = reservationList.getSelectedValue().getCitizenship();

			codeText.setText(selectedCode);
			flightText.setText(selectedFlight);
			airlineText.setText(selectedAirline);
			costText.setText(selectedPrice);
			nameText.setText(selectedName);
			citizenText.setText(selectedCitizenship);

			if (reservationList.getSelectedValue().isActive()) {
				statusBox.setSelectedIndex(1);
			} else {
				statusBox.setSelectedIndex(2);
			}
		}
	}

}
