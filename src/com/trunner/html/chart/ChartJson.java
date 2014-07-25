package com.trunner.html.chart;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

public class ChartJson {
	String key;
	ArrayList<Integer> i;
	public ChartJson(String key,ArrayList<Integer> i){
		this.key = key;
		this.i = i;
	}
	
	public String toJsonString(){
		HashMap<String, Object> map = new HashMap<String, Object>();  
		
		ArrayList<Series> series = new ArrayList<Series>();
		
		series.add(new Series(key,i));
		map.put("series", series);
		map.put("chart", new Chart("line"));
		map.put("title",new Title(key));
		map.put("yAxis", new YAxis("values"));
		Gson gson = new Gson();  
		String str = gson.toJson(map);
		
		
		
		return str;
	}
	public static void main(String[] args) {
	}

}
