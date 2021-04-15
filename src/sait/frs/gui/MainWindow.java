package sait.frs.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import sait.frs.manager.Manager;

/**
 * The main window (JFrame).
 * 
 */
public class MainWindow extends JFrame {
	private static final String TAB_FLIGHTS = "flights";
	private static final String TAB_RESERVATIONS = "reservations";

	/**
	 * Holds the flight and reservation manager.
	 */
	private Manager manager;

	/**
	 * Card layout to display tab content.
	 */
	private CardLayout cardLayout;

	/**
	 * North panel.
	 */
	private JPanel northPanel;

	/**
	 * Center panel.
	 */
	private JPanel centerPanel;

	/**
	 * Flights tab button.
	 */
	private JButton flightsButton;

	/**
	 * Reservations tab button.
	 */
	private JButton reservationsButton;

	/**
	 * Flights tab panel.
	 */
	private TabBase flightsTab;

	/**
	 * Reservations tab panel.
	 */
	private TabBase reservationsTab;

	/**
	 * Creates the Main Window and any components inside it.
	 */
	
	public MainWindow() {
		getContentPane().setPreferredSize((new Dimension(1000, 600)));
		setResizable(true);
		this.manager = new Manager();
		
		setTitle("Flight Reservation Management System");

		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		northPanel = createNorthPanel();
		getContentPane().add(northPanel, BorderLayout.NORTH);

		centerPanel = createCenterPanel();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		JPanel tabPanel = createTabPanel();
		panel.add(tabPanel, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Creates the center panel.
	 * 
	 * @return JPanel that goes in center.
	 */
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();

		cardLayout = new CardLayout();

		flightsTab = new FlightsTab(manager);
		reservationsTab = new ReservationsTab(manager);

		panel.setLayout(cardLayout);

		panel.add(flightsTab.getPanel(), TAB_FLIGHTS);
		panel.add(reservationsTab.getPanel(), TAB_RESERVATIONS);

		cardLayout.first(panel);
	
	
		return panel;
	}
		
	/**
	 * Creates the tab buttons.
	 * 
	 * @return JPanel containing tab buttons.
	 */
	private JPanel createTabPanel() {
		JPanel tabPanel = new JPanel();

		tabPanel.setLayout(new GridLayout(1, 2));

		flightsButton = new JButton("Flights");
		reservationsButton = new JButton("Reservations");

		flightsButton.addActionListener(new TabButtonActionListener());
		reservationsButton.addActionListener(new TabButtonActionListener());

		tabPanel.add(flightsButton);
		tabPanel.add(reservationsButton);

		return tabPanel;
	}
		
	/**
	 * Displays the JFrame window.
	 */
	public void display() {
		pack();
		setVisible(true);
	}

	/**
	 * Inner action listener class that listens for a tab button to be clicked.
	 */
	private class TabButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == flightsButton) {
				cardLayout.show(centerPanel, TAB_FLIGHTS);
				
			} else if (e.getSource() == reservationsButton) {
				cardLayout.show(centerPanel, TAB_RESERVATIONS);
			}
		}
	}
}
