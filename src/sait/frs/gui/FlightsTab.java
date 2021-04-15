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
 * The travel agent can find a flight by providing the origin airport, the
 * destination airport, and a day of the week the flight is departing. The
 * travel agent can make a flight reservation for a traveler. A reservation code
 * will be generated and assigned to the traveler’s name and citizenship.
 * 
 * Holds the components for the Flights tab.
 * 
 * @author Jonghyun Park
 * @version March 05, 2020
 */
public class FlightsTab extends TabBase {

	/**
	 * Instance of travel manager.
	 */
	private Manager manager;

	/**
	 * List of flights.
	 */
	private JList<Flight> flightsList;
	private DefaultListModel<Flight> flightsModel;

	/**
	 * Variable of TextField.
	 */
	private JTextField flightText;
	private JTextField airlineText;
	private JTextField dayText;
	private JTextField timeText;
	private JTextField costText;
	private JTextField nameText;
	private JTextField citizenText;

	/**
	 * Creates the components for flights tab.
	 * 
	 */
	public FlightsTab(Manager manager) {
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

		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
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

		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);

		// User can only select one item at a time.
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.flightsList);
		scrollPane.setPreferredSize(new Dimension(400, 200));

		flightsList.addListSelectionListener(new MyListSelectionListener());
		panel.setBorder(new EmptyBorder(10, 20, 10, 20));

		panel.add(scrollPane, BorderLayout.CENTER);

		return panel;
	}

	/**
	 * When a travel agent selects a flight from the list, the text fields will be
	 * populated with the selected flight code, airline, day, time and cost. The
	 * travel agent will enter the traveler’s full name and citizenship. The flight
	 * code, airline, day, time and cost cannot be edited. An error message will be
	 * displayed if a reservation is to be made and no flight is selected, the name
	 * field is empty, or the citizenship field is empty.
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

		JLabel day = new JLabel("Day:");
		day.setFont(new Font("Tahoma", Font.PLAIN, 15));
		day.setHorizontalAlignment(SwingConstants.RIGHT);
		dayText = new JTextField(20);
		dayText.setEditable(false);
		centerInner.add(day);
		centerInner.add(dayText);

		JLabel time = new JLabel("Time:");
		time.setFont(new Font("Tahoma", Font.BOLD, 15));
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		timeText = new JTextField(20);
		timeText.setEditable(false);
		centerInner.add(time);
		centerInner.add(timeText);

		JLabel cost = new JLabel("Cost:");
		cost.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cost.setHorizontalAlignment(SwingConstants.RIGHT);
		costText = new JTextField(20);
		costText.setEditable(false);
		centerInner.add(cost);
		centerInner.add(costText);

		JLabel name = new JLabel("Name:");
		name.setFont(new Font("Tahoma", Font.BOLD, 15));
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		nameText = new JTextField(20);
		centerInner.add(name);
		centerInner.add(nameText);

		JLabel citizenship = new JLabel("Citizenship:");
		citizenship.setFont(new Font("Tahoma", Font.BOLD, 15));
		citizenship.setHorizontalAlignment(SwingConstants.RIGHT);
		citizenText = new JTextField(20);
		centerInner.add(citizenship);
		centerInner.add(citizenText);

		panel.add(centerInner, BorderLayout.CENTER);

		// South Inner Panel
		JButton reserveButton = new JButton("Reserve");
		reserveButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(reserveButton, BorderLayout.SOUTH);

		/**
		 * The reserveButton actionListener works to receive as its input arguments: a
		 * Flight object, the travelers name and citizenship. An exception is thrown if
		 * the flight is completely booked, or the flight is null, or the name is
		 * empty/null, or the citizenship is empty/null. If there are no exceptions
		 * thrown a Reservation object is created, saved to the binary file and returned
		 * by the method.
		 */
		reserveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Flight flightMake = flightsList.getSelectedValue();
				String nameMake = nameText.getText();
				String citizenMake = citizenText.getText();

				try {
					manager.makeReservation(flightMake, nameMake, citizenMake);
					manager.persist();

					Reservation revCode = manager.getReservations().get(manager.getReservations().size() - 1);
					JOptionPane.showMessageDialog(null, "Reservation created. Your code is " + revCode.getCode());

				} catch (NullFlightException e1) {
					JOptionPane.showMessageDialog(null, "You do not selected flight, Please select flight first");
				} catch (NoMoreSeatsException e1) {
					JOptionPane.showMessageDialog(null, "The flight does not have available seat");
				} catch (InvalidNameException e1) {
					JOptionPane.showMessageDialog(null, "You do not Enter Your name");
				} catch (InvalidCitizenshipException e1) {
					JOptionPane.showMessageDialog(null, "You do not Enter vaild citizenship");
				}
			}
		});

		return panel;
	}

	/**
	 * This panel work for the travel agent can find a flight by providing the
	 * origin airport, the destination airport, and a day of the week the flight is
	 * departing.
	 * 
	 * @return JPanel that goes in south.
	 */
	private JPanel createSouthPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// North Inner Panel
		JLabel title = new JLabel("Flight Finder", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title, BorderLayout.NORTH);

		// Center Inner Panel
		JPanel centerInner = new JPanel();
		centerInner.setLayout(new BorderLayout());

		JPanel ci1 = new JPanel();
		ci1.setLayout(new GridLayout(3, 1));
		JPanel ci2 = new JPanel();
		ci2.setLayout(new GridLayout(3, 1));

		String[] airports = new String[manager.getAirports().size()];
		for (int i = 0; i < airports.length; i++) {
			airports[i] = manager.getAirports().get(i);
		}
		JLabel from = new JLabel("From:");
		JComboBox<String> fromComboBox = new JComboBox<String>(airports);
		ci1.add(from);
		ci2.add(fromComboBox);

		JLabel to = new JLabel("To:");
		JComboBox<String> toComboBox = new JComboBox<String>(airports);
		ci1.add(to);
		ci2.add(toComboBox);

		String[] days = { "Any", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		JLabel day = new JLabel("Day:");
		JComboBox<String> dayComboBox = new JComboBox<String>(days);
		day.setFont(new Font("Tahoma", Font.BOLD, 11));
		ci1.add(day);
		ci2.add(dayComboBox);

		centerInner.add(ci1, BorderLayout.WEST);
		centerInner.add(ci2, BorderLayout.CENTER);
		panel.add(centerInner, BorderLayout.CENTER);

		// South Inner Panel
		JButton resultButton = new JButton("Find Flights");
		resultButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(resultButton, BorderLayout.SOUTH);

		/**
		 * The resultButton works to receive as its input arguments: the originating
		 * airport, the destination airport, and the day of week. The method returns an
		 * ArrayList of any matching Flight objects. If no matches are found, the list
		 * control will be empty.
		 */
		resultButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String from = fromComboBox.getSelectedItem().toString();
				String to = toComboBox.getSelectedItem().toString();
				if (from.equals(to)) {
					JOptionPane.showMessageDialog(null, "Please Select Different Airport");
				} else {
					String weekday = dayComboBox.getSelectedItem().toString();
					ArrayList<Flight> findFlight = manager.findFlights(from, to, weekday);

					for (int i = 0; i < findFlight.size(); i++) {
						flightsModel.addElement(findFlight.get(i));
					}
				}
			}
		});
		return panel;
	}

	private class MyListSelectionListener implements ListSelectionListener {

		/**
		 * , * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			String selectedFlight = flightsList.getSelectedValue().getCode();
			String selectedAirline = flightsList.getSelectedValue().getAirline();
			String selectedDay = flightsList.getSelectedValue().getWeekday();
			String selectedTime = flightsList.getSelectedValue().getTime();
			double cost = flightsList.getSelectedValue().getCostPerSeat();
			String selectedPrice = String.format("%s%.2f", "$", cost);

			flightText.setText(selectedFlight);
			airlineText.setText(selectedAirline);
			dayText.setText(selectedDay);
			timeText.setText(selectedTime);
			costText.setText(selectedPrice);
		}
	}
}
