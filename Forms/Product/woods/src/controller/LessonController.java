package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class LessonController extends DBConnect {
	private static ResultSet spotResult;
	private static ResultSet lessonResult;
	private static ResultSet customerLessonResult;
	private static ResultSet todayLessonsResult;
	private static ResultSet customerCountResult;
	private static ResultSet outstandingLessonResult;
	private static ResultSet dateLessonDueResult;
	private static ResultSet lessonInfoResult;
	
	// New statements to avoid errors
	private static Statement spotStatement;
	private static Statement lessonStatement;
	private static Statement customerLessonStatement;
	private static Statement paymentStatement;
	private static Statement todayLessonsStatement;
	private static Statement customerCountStatement;
	private static Statement outstandingLessonStatement;
	private static Statement dateLessonDueStatement;
	private static Statement lessonInfoStatement;
	
	public static void addLesson(int productId, Date datePaymentDue, String location, String day, Date time, int duration, int staffId, String term, int year, int positions){		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String timeString = timeFormat.format(time);
		String datePaymentDueString = dateFormat.format(datePaymentDue);
		
		String qryAddLesson = "insert into grouplessons (productIdFK, staffIdFK, datePaymentDue, location, day, time, duration, term, year, positions) values " + "('" + productId + "'," + "'" + staffId + "'," + "'" + datePaymentDueString + "',"  + "'" + location + "'," + "'" + day + "'," + "'" + timeString + "'," + "'" + duration + "'," + "'" + term + "'," + "'" + year +"'," + "'" + positions + "')";

		try{
			statement.executeUpdate(qryAddLesson);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
	}
	

	public static ArrayList<String[]> getLessons(){
		Calendar today = Calendar.getInstance();
		int thisYear = today.get(Calendar.YEAR);
		// Create new statements to avoid ResultSet closed error
		try{
			spotStatement = connection.createStatement();
			lessonStatement = connection.createStatement();
		} catch(Exception ex){
			System.out.println("Statements not made");
		}
		
		ArrayList<String[]> lessons = new ArrayList<String[]>();
		
		String qryGetLessons = "select * from grouplessons where term >= " + Initialise.getCurrentTerm() + " and year >= " + thisYear;
		
		System.out.println(qryGetLessons);
		
		try{
			lessonResult = lessonStatement.executeQuery(qryGetLessons);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
	
		try{
			while(lessonResult.next()){
				int productId = Integer.parseInt(lessonResult.getString("productIdFK"));
				int maxLessonSize = Integer.parseInt(lessonResult.getString("positions"));
				
				// Only allow for lessons with available spots to be added to a customer
				if(getNumberOfSpots(productId, maxLessonSize) > 0){
					String[] lesson = getLessonDetails(productId);
					lessons.add(lesson);
				}
			}	
		} catch(Exception ex){
			System.out.println("E 2: " + ex);
		}		
		return lessons;
	}
	
	public static int getNumberOfSpots(int productId, int positions){
		int spots = -1;
		
		String qryGetSpots = "select customerIdFK from customerlessons where lessonIdFK = " + productId;
		
		
		try{
			spotResult = spotStatement.executeQuery(qryGetSpots);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		int positionsTaken = 0;
		try{
			
			while(spotResult.next()){
				// Count the number of positions already taken by a customer
				positionsTaken = positionsTaken + 1;
			}

		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		spots = positions - positionsTaken;
		
		return spots;
	}

	public static void addPlayer(int lessonId, int customerId) {
		String qryAddPlayerLesson = "insert into customerlessons (lessonIdFK, customerIdFK) values ('" + lessonId + "', '" + customerId + "')";
		
		try{
			statement.executeUpdate(qryAddPlayerLesson);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
	}
	
	public static ArrayList<String[]> getCustomerLessons(int customerId){
		ArrayList<String[]> lessons = new ArrayList<String[]>();
		
		String qryGetCustomerLessons = "select * from customerLessons where customerIdFK = " + customerId;
		System.out.println(qryGetCustomerLessons);
		
		try{
			customerLessonStatement = connection.createStatement();
			customerLessonResult = customerLessonStatement.executeQuery(qryGetCustomerLessons);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(customerLessonResult.next()){
				String[] lesson = getLessonDetails(Integer.parseInt(customerLessonResult.getString("lessonIdFK")));
				lessons.add(lesson);
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return lessons;
	}

	public static void payForLesson(int customerId, int productId) {
		// Changes the paymentMade column to 1 when the customer pays for the lesson
		String qryUpdatePayment = "UPDATE customerLessons SET paymentMade = " + 1 + " WHERE customerIdFK = " + customerId + " AND lessonIdFK = " + productId;
		
		try{
			paymentStatement = connection.createStatement();
			paymentStatement.executeUpdate(qryUpdatePayment);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
	}
	
	public static ArrayList<String[]> getLessonsToday(){
		ArrayList<String[]> lessons = new ArrayList<String[]>();
		
		// Get the day of the week today
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE"); 
		String today = dateFormat.format(now);
		
		String qryGetLessonsToday = "select * from grouplessons where day = '" + today + "' order by time asc";
		
		System.out.println(qryGetLessonsToday);
		
		try{
			todayLessonsStatement = connection.createStatement();
			todayLessonsResult = todayLessonsStatement.executeQuery(qryGetLessonsToday);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(todayLessonsResult.next()){
				String[] lesson = new String[6];
				
				// "Coach", "Name", "Location", "Time", "Duration", "Number of players"
				
				lesson[0] = StaffController.getStaffName(Integer.parseInt(todayLessonsResult.getString("staffIdFK")));
				lesson[1] = ProductController.getProductName(Integer.parseInt(todayLessonsResult.getString("productIdFK")));
				
				String location = todayLessonsResult.getString("location");
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
				lesson[2] = location;
				// Format the times to hh:mm am/pm
				SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
				SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm:ss");
				String time = timeFormat.format(originalFormat.parse(todayLessonsResult.getString("time")));
				
				lesson[3] = time;
				int duration = Integer.parseInt(todayLessonsResult.getString("duration"));
				int hours = duration / 60;
				int minutes = duration - hours * 60;
				if(hours >= 1){
					if(hours > 1){
						lesson[4] = hours + " hours";
					} else if(hours == 1){
						lesson[4] = hours + " hour";
					}
					
					if(minutes > 0){
						lesson[4] = hours + " hours " + minutes + " minutes";
					}
				} else{
					lesson[4] = minutes + " minutes";
				}
				lesson[5] = Integer.toString(getCustomerCount(Integer.parseInt(todayLessonsResult.getString("productIdFK"))));
				
				lessons.add(lesson);
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
        
		return lessons;
	}
	
	public static int getCustomerCount(int lessonId){
		int customerCount = 0;
		
		String qryGetCustomerCount = "select paymentMade from customerLessons where lessonIdFK = " + lessonId;
		
		System.out.println(qryGetCustomerCount);
		
		try{
			customerCountStatement = connection.createStatement();
			customerCountResult = customerCountStatement.executeQuery(qryGetCustomerCount);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(customerCountResult.next()){
				customerCount = customerCount + 1;
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		return customerCount;
	}
	
	public static ArrayList<String[]> getOutstandingLessons(int customerId){
		ArrayList<String[]> outstandingLessons = new ArrayList<String[]>();
		
		String qryGetOutstandingLessons = "select * from customerLessons where paymentMade = 0 AND customerIdFK = " + customerId;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		// Today's date to determine if the payment is overdue 
		Date now = new Date();
		
		try{
			outstandingLessonStatement = connection.createStatement();
			outstandingLessonResult = outstandingLessonStatement.executeQuery(qryGetOutstandingLessons);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(outstandingLessonResult.next()){
				// Lesson name, date due, overdue status, amount
				String[] outstandingLesson = new String[5];
				
				outstandingLesson[0] = ProductController.getProductName(Integer.parseInt(outstandingLessonResult.getString("lessonIdFK")));
				outstandingLesson[1] = "Lesson";
				Date dateDue = getDateLessonDue(Integer.parseInt(outstandingLessonResult.getString("lessonIdFK")));
				outstandingLesson[2] = dateFormat.format(dateDue);
				if(dateDue.before(now)){
					outstandingLesson[3] = "Overdue";
				} else{
					outstandingLesson[3] = "Upcoming";
				}
				outstandingLesson[4] = ProductController.getPrice(Integer.parseInt(outstandingLessonResult.getString("lessonIdFK")));
				
				outstandingLessons.add(outstandingLesson);
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return outstandingLessons;
	}
	
	public static Date getDateLessonDue(int lessonId){		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date dateDue = new Date();
		
		String qryGetDateLessonDue = "select datePaymentDue from groupLessons where productIdFK = " + lessonId;
		
		try{
			dateLessonDueStatement = connection.createStatement();
			dateLessonDueResult = dateLessonDueStatement.executeQuery(qryGetDateLessonDue);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(dateLessonDueResult.next()){
				String dbDate = dateLessonDueResult.getString("datePaymentDue");
				
				dateDue = dateFormat.parse(dbDate);
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		return dateDue;
	}
	
	public static String[] getLessonDetails(int lessonId){
		String[] lesson = new String[7];
		
		String qryGetLessonDetails = "select * from grouplessons where productIdFK = " + lessonId;
		
		try{
			lessonInfoStatement = connection.createStatement();
			lessonInfoResult = lessonInfoStatement.executeQuery(qryGetLessonDetails);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm:ss");
		
		try{
			while(lessonInfoResult.next()){
				lesson[0] = Integer.toString(lessonId);
				lesson[1] = ProductController.getProductName(lessonId);
				lesson[2] = lessonInfoResult.getString("day").substring(0,1).toUpperCase() + lessonInfoResult.getString("day").substring(1);
				lesson[3] = lessonInfoResult.getString("term");
				lesson[4] = lessonInfoResult.getString("year");
				lesson[5] = timeFormat.format(originalFormat.parse(lessonInfoResult.getString("time")));
				String location = lessonInfoResult.getString("location");
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
				lesson[6] = location;
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return lesson;
		
	}

	public static void removeCustomerLesson(int lessonId, int customerId) {
		String qryDeleteCustomerLesson = "delete from customerLessons where lessonIdFK = " + lessonId + " and customerIdFK = " + customerId;
		
		try{
			statement.executeUpdate(qryDeleteCustomerLesson);
		} catch(Exception ex){
			System.out.println("Customer lesson not deleted");
		}
	}
}
