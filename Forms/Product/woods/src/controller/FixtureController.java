package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FixtureController extends DBConnect {
	private static ResultSet spotResult;
	private static ResultSet fixtureResult;
	private static ResultSet todayFixturesResult;
	private static ResultSet customerCountResult;
	private static ResultSet outstandingFixtureResult;
	private static ResultSet dateFixtureDueResult;
	private static ResultSet customerFixtureResult;
	private static ResultSet fixtureInfoResult;
	
	// New statements to avoid errors
	private static Statement spotStatement;
	private static Statement fixtureStatement;
	private static Statement paymentStatement;
	private static Statement todayFixturesStatement;
	private static Statement customerCountStatement;
	private static Statement outstandingFixtureStatement;
	private static Statement dateFixtureDueStatement;
	private static Statement customerFixtureStatement;
	private static Statement fixtureInfoStatement;
	
	public static void addFixture(int productId, Date datePaymentDue, String location, String day, Date time, int duration, String term, int year, int positions){		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String timeString = timeFormat.format(time);
		String datePaymentDueString = dateFormat.format(datePaymentDue);
		
		String qryAddFixture = "insert into fixtures (productIdFK, datePaymentDue, location, day, time, duration, term, year, positions) values " + "('" + productId + "'," + "'" + datePaymentDueString + "',"  + "'" + location + "'," + "'" + day + "'," + "'" + timeString + "'," + "'" + duration + "'," + "'" + term + "'," + "'" + year + "'," + "'" + positions + "')";
		System.out.println(qryAddFixture);

		try{
			statement.executeUpdate(qryAddFixture);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
	}
	
	public static ArrayList<String[]> getFixtures(){
		Calendar today = Calendar.getInstance();
		int thisYear = today.get(Calendar.YEAR);
		// Create new statements to avoid ResultSet closed error
		try{
			spotStatement = connection.createStatement();
			fixtureStatement = connection.createStatement();
		} catch(Exception ex){
			System.out.println("Statements not made");
		}
		
		ArrayList<String[]> fixtures = new ArrayList<String[]>();
		
		String qryGetFixtures = "select * from fixtures where term >= +" + Initialise.getCurrentTerm() + " AND year >= " + thisYear;
		
		System.out.println(qryGetFixtures);
		try{
			fixtureResult = fixtureStatement.executeQuery(qryGetFixtures);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(fixtureResult.next()){
				int productId = Integer.parseInt(fixtureResult.getString("productIdFK"));
				int maxLessonSize = Integer.parseInt(fixtureResult.getString("positions"));
				
				// Only allow for lessons with available spots to be added to a customer
				if(getNumberOfSpots(productId, maxLessonSize) > 0){
					String fixtureName = ProductController.getProductName(productId);
					String[] fixture = {Integer.toString(productId), fixtureName};
					fixtures.add(fixture);
				}
				
			}	
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return fixtures;
	}
		
	public static int getNumberOfSpots(int productId, int positions){
		int spots = -1;
		
		String qryGetSpots = "select customerIdFK from customerfixtures where fixtureIdFK = " + productId;
		
		try{
			spotResult = spotStatement.executeQuery(qryGetSpots);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			int positionsTaken = 0;
			while(spotResult.next()){
				// Count the number of positions already taken by a customer
				positionsTaken = positionsTaken + 1;
			}
			
			spots = positions - positionsTaken;
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		
		return spots;
	}

	public static void addPlayer(int fixtureId, int customerId) {
		String qryAddPlayerFixture = "insert into customerfixtures (fixtureIdFK, customerIdFK) values ('" + fixtureId + "', '" + customerId + "')";
		
		try{
			statement.executeUpdate(qryAddPlayerFixture);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
	}

	public static void payForFixture(int customerId, int productId) {
		String qryUpdatePayment = "UPDATE customerFixtures SET paymentMade = " + 1 + " WHERE customerIdFK = " + customerId + " AND fixtureIdFK = " + productId;
		
		try{
			paymentStatement = connection.createStatement();
			paymentStatement.executeUpdate(qryUpdatePayment);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
	}
	
	public static ArrayList<String[]> getFixturesToday(){
		ArrayList<String[]> fixtures = new ArrayList<String[]>();
		
		// Get the day of the week today
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE"); 
		String today = dateFormat.format(now);
		
		String qryGetFixturesToday = "select * from fixtures where day = '" + today + "' order by time asc";	
		
		try{
			todayFixturesStatement = connection.createStatement();
			todayFixturesResult = todayFixturesStatement.executeQuery(qryGetFixturesToday);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(todayFixturesResult.next()){
				String[] fixture = new String[5];
				
				// "Name", "Location", "Time", "Duration", "Number of players"
				
				fixture[0] = ProductController.getProductName(Integer.parseInt(todayFixturesResult.getString("productIdFK")));
				String location = todayFixturesResult.getString("location");
				switch(location){
				case "AH": location = "Albany Hills";
							break;
				case "AC": location = "Albany Creek";
							break;
				case "AS": location = "All Saints";
							break;
				case "SD": location = "St Dympna's";
							break;
				case "AE": location = "Aspley East State School";
							break;
				}
				fixture[1] = location;
				
				// Format the times to hh:mm am/pm
				SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
				SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm:ss");
				String time = timeFormat.format(originalFormat.parse(todayFixturesResult.getString("time")));
				
				fixture[2] = time;
				int duration = Integer.parseInt(todayFixturesResult.getString("duration"));
				int hours = duration / 60;
				int minutes = duration - hours * 60;
				if(hours >= 1){
					if(hours > 1){
						fixture[3] = hours + " hours";
					} else if(hours == 1){
						fixture[3] = hours + " hour";
					}
					if(minutes > 0){
						fixture[3] = hours + " hours " + minutes + " minutes";
					}
				} else{
					fixture[3] = minutes + " minutes";
				}
				fixture[4] = Integer.toString(getCustomerCount(Integer.parseInt(todayFixturesResult.getString("productIdFK"))));
				
				fixtures.add(fixture);
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return fixtures;
	}

	public static int getCustomerCount(int productId) {
		int customerCount = 0;
		
		String qryFixtureCustomerCount = "select paymentMade from customerfixtures where fixtureIdFK = " + productId;
		
		try{
			customerCountStatement = connection.createStatement();
			customerCountResult = customerCountStatement.executeQuery(qryFixtureCustomerCount);
		} catch (Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(customerCountResult.next()){
				customerCount = customerCount + 1;
			}
		} catch (Exception ex){
			System.out.println("Error 2: " + ex);
		}
		return customerCount;
	}
	
	public static ArrayList<String[]> getOutstandingFixtures(int customerId){
		ArrayList<String[]> outstandingFixtures = new ArrayList<String[]>();
		
		String qryGetOutstandingFixtures = "select * from customerFixtures where paymentMade = 0 AND customerIdFK = " + customerId;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		// Today's date to determine if the payment is overdue 
		Date now = new Date();
		
		try{
			outstandingFixtureStatement = connection.createStatement();
			outstandingFixtureResult = outstandingFixtureStatement.executeQuery(qryGetOutstandingFixtures);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(outstandingFixtureResult.next()){
				// Lesson name, date due, overdue status, amount
				String[] outstandingFixture = new String[5];
				
				outstandingFixture[0] = ProductController.getProductName(Integer.parseInt(outstandingFixtureResult.getString("fixtureIdFK")));
				outstandingFixture[1] = "Fixture";
				Date dateDue = getDateFixtureDue(Integer.parseInt(outstandingFixtureResult.getString("fixtureIdFK")));
				outstandingFixture[2] = dateFormat.format(dateDue);
				if(dateDue.before(now)){
					outstandingFixture[3] = "Overdue";
				} else{
					outstandingFixture[3] = "Upcoming";
				}
				outstandingFixture[4] = ProductController.getPrice(Integer.parseInt(outstandingFixtureResult.getString("fixtureIdFK")));
				
				outstandingFixtures.add(outstandingFixture);
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return outstandingFixtures;
	}

	public static Date getDateFixtureDue(int fixtureId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date dateDue = new Date();
		
		String qryGetDateFixtureDue = "select datePaymentDue from fixtures where productIdFK = " + fixtureId;
		
		try{
			dateFixtureDueStatement = connection.createStatement();
			dateFixtureDueResult = dateFixtureDueStatement.executeQuery(qryGetDateFixtureDue);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(dateFixtureDueResult.next()){
				String dbDate = dateFixtureDueResult.getString("datePaymentDue");
				
				dateDue = dateFormat.parse(dbDate);
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		return dateDue;
	}
	
	public static ArrayList<String[]> getCustomerFixtures(int customerId){
		ArrayList<String[]> fixtures = new ArrayList<String[]>();
		
		String qryGetCustomerFixtures = "select fixtureIdFK from customerfixtures where customerIdFK = " + customerId;
		
		try{
			customerFixtureStatement = connection.createStatement();
			customerFixtureResult = customerFixtureStatement.executeQuery(qryGetCustomerFixtures);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(customerFixtureResult.next()){
				int fixtureId = Integer.parseInt(customerFixtureResult.getString("fixtureIdFK"));
				String[] fixture = getFixtureInfo(fixtureId);
				fixtures.add(fixture);
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return fixtures; 
	}
	
	public static String[] getFixtureInfo(int fixtureId){
		String[] fixture = new String[7];
		
		String qryGetFixture = "select * from fixtures where productIdFK = " + fixtureId;
		
		try{
			fixtureInfoStatement = connection.createStatement();
			fixtureInfoResult = fixtureInfoStatement.executeQuery(qryGetFixture);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm:ss");
		
		try{
			while(fixtureInfoResult.next()){
				fixture[0] = Integer.toString(fixtureId);
				fixture[1] = ProductController.getProductName(fixtureId);
				fixture[2] = fixtureInfoResult.getString("day").substring(0,1).toUpperCase() + fixtureInfoResult.getString("day").substring(1);
				fixture[3] = fixtureInfoResult.getString("term");
				fixture[4] = fixtureInfoResult.getString("year");
				fixture[5] = timeFormat.format(originalFormat.parse(fixtureInfoResult.getString("time")));
				String location = fixtureInfoResult.getString("location");
				switch(location){
				case "AH": location = "Albany Hills";
							break;
				case "AC": location = "Albany Creek";
							break;
				case "AS": location = "All Saints";
							break;
				case "SD": location = "St Dympna's";
							break;
				case "AE": location = "Aspley East State School";
							break;
				}
				fixture[6] = location;
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return fixture;
	}
	
	public static void removeCustomerFixture(int fixtureId, int customerId){
		String qryDeleteCustomerFixture = "delete from customerFixtures where fixtureIdFK = " + fixtureId + " and customerIdFK = " + customerId;
		
		try{
			statement.executeUpdate(qryDeleteCustomerFixture);
		} catch(Exception ex){
			System.out.println("Customer fixture not deleted");
		}
	}
}
