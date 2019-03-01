package com.isteer.service.model;

public class WorkLogDataOld {
	
	private int scl_key;
	private int service_key;
	private int project_key;
	private String mi_key;
	private String product_name = "";
	private String service_type;
	private String start_time;
	private String end_time;
	private int status;
	private int is_spare_parts_needed = 0;
	private int service_eng_count = 0;
	private String remarks;
	private String entered_by;
	private String entered_date_time;
	private String visited_date;
	private String entered_by_name = "";
	private String serviced_by_name = "";
	private String minutes_of_meeting = "";
	private String replace_suggestion = "";
	private String serviced_by;
	private double latitude = 0.0;
	private double longitude = 0.0;
	private double altitude = 0.0;

	public String getReplace_suggestion() {
		return replace_suggestion;
	}

	public void setReplace_suggestion(String replace_suggestion) {
		this.replace_suggestion = replace_suggestion;
	}

	public String getMinutes_of_meeting() {
		return minutes_of_meeting;
	}

	public void setMinutes_of_meeting(String minutes_of_meeting) {
		this.minutes_of_meeting = minutes_of_meeting;
	}

	public int getScl_key() {
		return scl_key;
	}
	public void setScl_key(int scl_key) {
		this.scl_key = scl_key;
	}
	public int getService_key() {
		return service_key;
	}
	public void setService_key(int service_key) {
		this.service_key = service_key;
	}
	public int getProject_key() {
		return project_key;
	}
	public void setProject_key(int project_key) {
		this.project_key = project_key;
	}
	public String getMi_key() {
		return mi_key;
	}
	public void setMi_key(String mi_key) {
		this.mi_key = mi_key;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getIs_spare_parts_needed() {
		return is_spare_parts_needed;
	}
	public void setIs_spare_parts_needed(int is_spare_parts_needed) {
		this.is_spare_parts_needed = is_spare_parts_needed;
	}
	public String getEntered_by() {
		return entered_by;
	}
	public void setEntered_by(String entered_by) {
		this.entered_by = entered_by;
	}
	public String getEntered_date_time() {
		return entered_date_time;
	}
	public void setEntered_date_time(String entered_date_time) {
		this.entered_date_time = entered_date_time;
	}
	public String getVisited_date() {
		return visited_date;
	}
	public void setVisited_date(String visited_date) {
		this.visited_date = visited_date;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public String getServiced_by() {
		return serviced_by;
	}
	public void setServiced_by(String serviced_by) {
		this.serviced_by = serviced_by;
	}
	public int getService_eng_count() {
		return service_eng_count;
	}
	public void setService_eng_count(int service_eng_count) {
		this.service_eng_count = service_eng_count;
	}
	public String getEntered_by_name() {
		return entered_by_name;
	}
	public void setEntered_by_name(String entered_by_name) {
		this.entered_by_name = entered_by_name;
	}
	public String getServiced_by_name() {
		return serviced_by_name;
	}
	public void setServiced_by_name(String serviced_by_name) {
		this.serviced_by_name = serviced_by_name;
	}


}
