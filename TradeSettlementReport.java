package com.trade.settlements;
/**
* The TradeSettlementReport program implements an application that 
* create a Report for Trade Settlements.
*
* @author  Bala S Rayala
* @version 1.0
*/

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.DecimalFormat;

public class TradeSettlementReport {

	
	private static Map<String,Double> in_trade = new HashMap<String,Double>();
	private static Map<String,Double> out_trade = new HashMap<String,Double>();
	private static Map<String,Double> rank_trade = new HashMap<String,Double>();
	
	private static DecimalFormat decFormat = new DecimalFormat("####0.00");
	/**
	 * 
	 * Calculate the Trade Amount.
	 * Split the values based on underscore.
	 * Retrieve the date and make it in format.
	 * Fetch the Currency and identify the currency code.
	 * Based on Currency, identify the day.
	 * Based on trade type(buy/sell), calculate the total price. 
	 * */
	public void calculatePrice(String trade){
		String[] tokens = trade.split("_");		
		double pricePerUnit = Double.parseDouble(tokens[7]);
		double units =  Double.parseDouble(tokens[6]);
		double agreedFX = Double.parseDouble(tokens[2]);
		double totalPrice = pricePerUnit * units * agreedFX;
		
		String settlementDate = tokens[5];
		String entityName = tokens[0];
		String tradeType = tokens[1];
		String currency = tokens[3];
		
		
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date settlementDateFormat = new Date();
		
		try{ 
			settlementDateFormat = dateFormat.parse(settlementDate);
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(settlementDateFormat);
		
		if(currency.equalsIgnoreCase("AED") || currency.equalsIgnoreCase("SAR")){
			 if(settlementDateFormat.toString().startsWith("Fri")){ 
                 cal.add(Calendar.DATE, 2);  
           }else if(settlementDateFormat.toString().startsWith("Sat")){
                 cal.add(Calendar.DATE, 1);  
           }
		}else{
			 if(settlementDateFormat.toString().startsWith("Sat")){
                 cal.add(Calendar.DATE, 2);  
			 }else if(settlementDateFormat.toString().startsWith("Sun")){
                 cal.add(Calendar.DATE, 1);  
			 }
		}
		
		
		  Date currentDate = cal.getTime();
		  
		  double totalUSDTrade=0.00;
		  
		  if(tradeType.equalsIgnoreCase("B")){
			  
			  if(out_trade.containsKey(dateFormat.format(currentDate).toString())){
				  totalUSDTrade=out_trade.get(dateFormat.format(currentDate).toString());
				  out_trade.put(dateFormat.format(currentDate).toString(), totalPrice+totalUSDTrade);
               }else{
            	   out_trade.put(dateFormat.format(currentDate).toString(), totalPrice);
          }
			  rank_trade.put(entityName,  totalPrice);
			  totalUSDTrade=0.00;
			  
		  }
		  else if(tradeType.equals("S")){
	            //otherwise add to incoming settlement
	            if(in_trade.containsKey(dateFormat.format(currentDate).toString())){
	            	totalUSDTrade=in_trade.get(dateFormat.format(currentDate).toString());
	            	in_trade.put(dateFormat.format(currentDate).toString(), totalPrice+totalUSDTrade);
	             }else{
	            	 in_trade.put(dateFormat.format(currentDate).toString(), totalPrice);
	            }
	            rank_trade.put(entityName,  totalPrice);
	            totalUSDTrade=0.00;
	        }
		
	}
	
	
	/**
	 * Sorting Trade Settlements values for ranking
	 * using Comparator, sort the list of values.
	 * 
	 * */
	 private static void sortValues(Map<String, Double> valueMap) {        
         Set<Entry<String,Double>> entries = valueMap.entrySet();

         // used linked list to sort, because insertion of elements in linked list is faster than an array list. 
         List<Entry<String,Double>> valueList = new LinkedList<Entry<String,Double>>(entries);

         // sorting the List
         Collections.sort(valueList, new Comparator<Entry<String,Double>>() {
             @Override
             public int compare(Entry<String, Double> value1,
                     Entry<String, Double> value2) {
                 return value2.getValue().compareTo(value1.getValue());
             }
         });

         // Storing the list into Linked HashMap to preserve the order of insertion. 
         Map<String,Double> aMap2 = new LinkedHashMap<String, Double>();
         for(Entry<String,Double> entry: valueList) {
             aMap2.put(entry.getKey(), entry.getValue());
         }

         System.out.println("         ");
         System.out.println("### Settlements Ranking ###            ");
         for(Entry<String,Double> entry : aMap2.entrySet()) {
             System.out.println(entry.getKey()+" and amount is $"+decFormat.format(entry.getValue()));
         }   
         System.out.println("### ******* ###            ");
 }
	
	public static void main(String[] args) {
		
				
		TradeSettlementReport settlement = new TradeSettlementReport();
		
		/**
		 * create sample data by using TestData class.
		 * Call createData method.
		 * */
		
		List<String> list = TestData.createData();
		
		/**
		 * 
		 * Calculate the Trade Price for each and every data
		 * Call calcualtePrice method.
		 * 
		 * */
		
		list.forEach((temp) -> {
			settlement.calculatePrice(temp);
		});
		
		/**
		 * Settlements Out Trade
		 * Fetch the values from out_trade.
		 * */
		
		System.out.println("### Settlements Trade Out ###");
		
		 for (Map.Entry<String, Double> entry : out_trade.entrySet()) {
	            String date = entry.getKey();
	            Double value = entry.getValue();
	            System.out.println("Trade Date is "+date+" & amount is $"+decFormat.format(value));
	        }
		 
		 System.out.println("### ***** ###");
         System.out.println(" ");
        
         /**
 		 * Settlements In Trade
 		 * Fetch the values from in_trade.
 		 * */
         
         System.out.println("### Settlements Trade In ###");
         for (Map.Entry<String, Double> entry : in_trade.entrySet()) {
         String date = entry.getKey();
         Double value = entry.getValue();
         System.out.println("Trade Date is "+date+" & amount is $"+decFormat.format(value));
         }
         System.out.println("### ***** ###");
         
         // sort the trade rank values.
         sortValues(rank_trade);
	}

}
