package ua.nure.petrikin.OnlineBanking.web.util;

import java.util.Map;
import java.util.TreeMap;

public class Param {
	private String name;
	private Map<String, String> tests;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getTests() {
		return tests;
	}
	public void setTests(Map<String, String> tests) {
		this.tests = tests;
	}
	
	public void put(String msg, String p){
		if(tests == null){
			tests = new TreeMap<String, String>();
			tests.put(msg, p);
		} else {
			tests.put(msg, p);
		}
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	
}
