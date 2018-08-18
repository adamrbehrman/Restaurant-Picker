package main;

public class Restaurant implements Comparable<Restaurant> {

	private String name;
	private Type type;
	private Time[] time;
	private boolean close;

	public Restaurant(String[] strings) {
		name = strings[0].trim();
		for (int i = 0; i < Type.values().length; i++) {
			if (Type.values()[i].name().toLowerCase().equals(strings[1].toLowerCase().trim())) {
				type = Type.values()[i];
			}
		}

		String[] times = strings[2].split("/");
		int index = 0;
		time = new Time[times.length];
		for (int i = 0; i < Time.values().length; i++) {
			if (time.length - 1 >= index) {
				if (Time.values()[i].name().toLowerCase().equals(times[index].toLowerCase().trim())) {
					time[index] = Time.values()[i];
					index++;
				}
			}
		}

		if (strings[3].trim().equalsIgnoreCase("close")) {
			close = true;
		} else if (strings[3].trim().equalsIgnoreCase("far")) {
			close = false;
		}
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public Time[] getTime() {
		return time;
	}

	public boolean isClose() {
		return close;
	}

	public String toCSVLine() {
		String data = "";
		for (int i = 0; i < time.length; i++) {
			if (i > 0) {
				data += "/" + time[i].name();
			} else {
				data += time[i].name();
			}
		}
		if(close) {
			return name + "," + type + "," + data + ",close";
		} else {
			return name + "," + type + "," + data + ",far";
		}
		
	}

	@Override
	public int compareTo(Restaurant r) {
		return this.getName().compareTo(r.getName());
	}

	@Override
	public String toString() {
		return toCSVLine().replace(",", ", ");
	}
}
