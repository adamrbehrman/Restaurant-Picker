package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Main {

	private static Scanner scan = new Scanner(System.in);
	private static ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
	private static String FILENAME = "allRestaurants.csv";

	public static void main(String[] args) throws FileNotFoundException {
		readData();
		Collections.sort(restaurants);
		createUpdatedCSV(restaurants, FILENAME);
		do {
			randomRestaurantGivenCriteria();
			System.out.println("\nWould you like to stop?");
		} while(!scan.nextLine().toLowerCase().contains("y"));
	}

	private static void randomRestaurantGivenCriteria() {
		ArrayList<Restaurant> temp = new ArrayList<Restaurant>(restaurants);
		boolean stop = false;
		String line = "";

		do {
			System.out.println("Want to sort restaurants by time?");
			if (scan.hasNextLine()) {
				line = scan.nextLine().toLowerCase();
				if (line.startsWith("y") || line.startsWith("n")) {
					stop = true;
					if (line.startsWith("y")) {
						temp = returnTimeSorted(timeCheck(), temp);
					}
				}
			}
		} while (!stop);
		stop = false;
		line = "";

		do {
			System.out.println("Want to sort restaurants by type?");
			if (scan.hasNextLine()) {
				line = scan.nextLine().toLowerCase();
				if (line.startsWith("y") || line.startsWith("n")) {
					stop = true;
					if (line.startsWith("y")) {
						temp = returnTypeSorted(typeCheck(), temp);
					}
				}
			}
		} while (!stop);
		stop = false;
		line = "";

		do {
			System.out.println("Want to sort restaurants by proxy?");
			if (scan.hasNextLine()) {
				line = scan.nextLine().toLowerCase();
				if (line.startsWith("y") || line.startsWith("n")) {
					stop = true;
					if (line.startsWith("y")) {
						temp = returnProxySorted(proxyCheck(), temp);
					}
				}
			}
		} while (!stop);

		Random rand = new Random();
		int i = rand.nextInt(temp.size() - 1);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nChef's choice: "+temp.get(i).getName()+".\n");
		temp.remove(i);
		System.out.println("Here are your other options: \n");
		for(Restaurant r:temp) {
			System.out.println(r.getName()+".");
		}
	}

	private static int timeCheck() {
		int time = Integer.MIN_VALUE;
		do {
			System.out.println("Select by time (Breakfast = 1, etc.): ");
			for (int i = 0; i < Time.values().length; i++) {
				if (i == 0) {
					System.out.print(Time.values()[i].name() + " " + (i + 1));
				} else {
					System.out.print(", " + Time.values()[i].name() + " " + (i + 1));
				}
			}
			System.out.println();
			try {
				time = Integer.parseInt(scan.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter an integer.");
			}
		} while (time < 1 || time > 4);
		time--;
		return time;
	}

	private static int typeCheck() {
		int type = Integer.MIN_VALUE;
		do {
			System.out.println("Select by type (Americana = 1, etc.):");
			for (int i = 0; i < Type.values().length; i++) {
				if (i == 0) {
					System.out.print(Type.values()[i].name() + " " + (i + 1));
				} else {
					System.out.print(", " + Type.values()[i].name() + " " + (i + 1));
				}
			}
			System.out.println();
			try {
				type = Integer.parseInt(scan.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter an integer.");
			}
		} while (type < 1 || type > 9);
		type--;
		return type;
	}

	private static int proxyCheck() {
		int proxy = Integer.MIN_VALUE;
		do {
			System.out.println("Select by proxy (Close [1] or far [2]):");
			try {
				proxy = Integer.parseInt(scan.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter an integer.");
			}
		} while (!(proxy == 1 || proxy == 2));
		proxy--;
		return proxy;
	}

	private static ArrayList<Restaurant> returnProxySorted(int proxy, ArrayList<Restaurant> data) {
		ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).isClose()) {
				temp.add(data.get(i));
			}
		}
		return temp;
	}

	private static ArrayList<Restaurant> returnTimeSorted(int time, ArrayList<Restaurant> data) {
		ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.get(i).getTime().length; j++) {
				if (data.get(i).getTime()[j].name().equals(Time.values()[time].name())) {
					temp.add(data.get(i));
				}
			}
		}
		return temp;
	}

	private static ArrayList<Restaurant> returnTypeSorted(int type, ArrayList<Restaurant> data) {
		ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getType().ordinal() == type) {
				temp.add(data.get(i));
			}
		}
		return temp;
	}

	private static void readData() throws FileNotFoundException {
		Scanner scan = new Scanner(new File("restaurants.txt"));
		while (scan.hasNextLine()) {
			restaurants.add(new Restaurant(scan.nextLine().trim().split(",")));
		}
	}

	private static void createUpdatedCSV(ArrayList<Restaurant> restaurants, String fileName) {
		try {
			PrintWriter pf = new PrintWriter(new File(fileName));
			for (Restaurant r : restaurants) {
				pf.write(r.toCSVLine() + "\n");
			}
			pf.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

}
