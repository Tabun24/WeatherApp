package com.example.weatherapp.serialization;

import java.util.ArrayList;
import java.util.List;

import com.example.weatherapp.Utils.TimeConverter;
import com.google.gson.annotations.SerializedName;

public class GsonParse {

	@SerializedName("coord")
	private Coord coord;
	
	@SerializedName("sys")
	private Sys sys ;
	
	@SerializedName("weather")
	private  List<Weather> weather = new ArrayList<Weather>();
	
	@SerializedName("base")
	private String base;
	
	@SerializedName("main")
	private Main  main;
	
	@SerializedName("wind")
	private Wind wind ;
	
	@SerializedName("clouds")
	private Clouds clouds ;
	
	@SerializedName("dt")
	private String dt ;
	
	@SerializedName("id")
	private String id ;
	
	@SerializedName("name")
	private String name ;
	
	@SerializedName("cod")
	private String cod ;
	
	
	
	
	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public List<Weather> getWeather() {
		return weather;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public String getDt() {
		return dt;
	}
	
	public String getDtTime(){
		return TimeConverter.getTime2(dt);
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getDtDate() {
		
		return TimeConverter.getDate(dt);
	}

	
	
}
