package controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import view.LogIn;

public class Initialise {
	public static LogIn logIn = new LogIn();
	
	public static void main(String[] args){
		DBConnect dbcon = new DBConnect();
		
		// Only show the log-in window if the program has been connected to the database
		if(dbcon.connected){
			// Show the log-in window
			logIn.setVisible(true);
		}
	}
	
	public static double getCurrentTerm(){
		double term = -1;
		
		Calendar today = Calendar.getInstance();
		int y = today.get(Calendar.YEAR);
		Calendar ausDay = new GregorianCalendar(today.get(Calendar.YEAR), 0, 26);
		Calendar term1Start = (Calendar)ausDay.clone();

		if(ausDay.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
			term1Start.add(Calendar.DAY_OF_WEEK, 1);
		} else{
			int ausDayOfWeek = ausDay.get(Calendar.DAY_OF_WEEK);
			// Set the term to start on the Monday of Australia Day week
			term1Start.add(Calendar.DAY_OF_WEEK, 2 - ausDayOfWeek);
		}
		
		if(today.before(term1Start)){
			term = 4.5;
			return term;
		}
		
		// Set the end date of term 1 to be the Friday 10 weeks after term 1's start date
		Calendar term1End = (Calendar)term1Start.clone();
    	term1End.add(Calendar.WEEK_OF_MONTH, 9);
    	term1End.add(Calendar.DAY_OF_WEEK, 4);
		
		// Calculate the date of Easter Sunday based on Computus
		// y is the current year
		int a = y % 19;
        int b = y / 100;
        int c = y % 100;
        int d = b / 4;
        int e = b % 4;
        int g = (8 * b + 13) / 25;
        int h = (19 * a + b - d - g + 15) % 30;
        int j = c / 4;
        int k = c % 4;
        int m = (a + 11 * h) / 319;
        int r = (2 * e + 2 * j - k - h + m + 32) % 7;
        // n is the month that Easter Sunday falls in
        int n = (h - m + r + 90) / 25;
        // p is the day of the month for Easter Sunday
        int p = (h - m + r + n + 19) % 32;

        Calendar easter = new GregorianCalendar(y, n-1, p);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                
        Calendar term2Start;
        Calendar term2End;
        
        //System.out.println("Term 2 without easter");
        
    	//System.out.println(dateFormat.format(term1Start.getTime()));
    	//System.out.println(dateFormat.format(term1End.getTime()));
    	   	
    	
        if(term1End.after(easter)){
        	term1End = (Calendar)easter.clone();
        	// Set Term 1 to end the day before Good Friday
        	term1End.add(Calendar.DAY_OF_MONTH, -3);
        	if(today.after(term1Start) && today.before(term1End)){
        		term = 1;
        		return term;
        }
        	
        	// Set term 2 to begin the Monday 2 weeks after Easter Sunday
        	term2Start = (Calendar)easter.clone();
        	term2Start.add(Calendar.WEEK_OF_MONTH, 2);
        	term2Start.add(Calendar.DAY_OF_WEEK, 1);
        	
        	// Set the end date of term 2
        	term2End = (Calendar)term2Start.clone();
        	term2End.add(Calendar.WEEK_OF_MONTH, 10);
        	term2End.add(Calendar.DAY_OF_WEEK, 4);
        	
        	if(today.after(term1End) && today.before(term2Start)){
        		term = 1.5;
        		return term;
        	}
        	
        	if(today.after(term2Start) && today.before(term2End)){
        		term = 2;
        		return term;
        	}
        } else{
        	term1End = (Calendar)term1Start.clone();
        	term1End.add(Calendar.WEEK_OF_MONTH, 9);
        	term1End.add(Calendar.DAY_OF_WEEK, 4);
        	
        	if(today.after(term1Start) && today.before(term1End)){
        		term = 1;
        		//System.out.println(term);
        		return term;
        	}
        	
        	// Add 2 weeks of holidays to the end of term 1 to get the beginning of term 2
        	term2Start = (Calendar)term1End.clone();
        	term2Start.add(Calendar.WEEK_OF_MONTH, 2);
        	term2Start.add(Calendar.DAY_OF_WEEK, 3);
        	
        	// Add 10 weeks to term 2 start to get term 2 end
        	term2End = (Calendar)term2Start.clone();
        	term2End.add(Calendar.WEEK_OF_MONTH, 9);
        	term2End.add(Calendar.DAY_OF_WEEK, 4);
        	
        	if(today.after(term1End) && today.before(term2Start)){
        		// 1.5 is the holidays between term 1 and 2
        		term = 1.5;
        		return term;
        	}
        	
        	if(today.after(term2Start) && today.before(term2End)){
        		term = 2;
        		//System.out.println(term);
        		return term;
        	}
        }
        
        Calendar term3Start = (Calendar)term2End.clone();
        term3Start.add(Calendar.WEEK_OF_MONTH, 2);
        term3Start.add(Calendar.DAY_OF_MONTH, 3);
        
        Calendar term3End = (Calendar)term3Start.clone();
        term3End.add(Calendar.WEEK_OF_MONTH, 9);
        term3End.add(Calendar.DAY_OF_MONTH, 4);
        
        if(today.after(term2End) && today.before(term3Start)){
        	term = 2.5;
        	return term;
        }
        
        if(today.after(term3Start) && today.before(term3End)){
    		term = 3;
    		//System.out.println(term);
    		return term;
    	}
		
        Calendar term4Start = (Calendar)term3End.clone();
        term4Start.add(Calendar.WEEK_OF_MONTH, 2);
        term4Start.add(Calendar.DAY_OF_MONTH, 3);
        
        Calendar term4End = (Calendar)term4Start.clone();
        term4End.add(Calendar.WEEK_OF_MONTH, 9);
        term4End.add(Calendar.DAY_OF_MONTH, 4);
        
        if(today.after(term3End) && today.before(term4Start)){
        	term = 3.5;
        	return term;
        }
        
        if(today.after(term4Start) && today.before(term4End)){
    		term = 4;
    		//System.out.println(term);
    		return term;
    	}
        
        if(today.after(term4End)){
        	term = 4.5;
        	return term;
        }
        
		return term;
	}
}
