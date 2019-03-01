package com.isteer.service.model;

public class EventData {
	
	private String event_key;
	private String event_user_key;
	private String event_title = "";
	private String event_type;
	//private String event_category_key;
	private String from_date_time;
	private String to_date_time;
	//private String event_repeat_config;
	//private String repeat_condition;
	private String event_description = "";
	private String status;
	//private String parent_event_key;
	//private String child_event_key;
	//private String entered_by;
	private String entered_on;
	//private String last_modified_by;
	//private String last_modified_on;
	//private String completed_by;
	private String completed_on;
	//private String cancelled_by;
	private String cancelled_on;
	//private String dsr_status;
	//private String followup_key;
	private String cmkey;
	private String area = "";
	private String latitude;
	private String longitude;
	private String altitude;
	private String visit_update_time;
	//private String follow_key;
	//private String contact_key;
	//private String action_description;
	private String plan = "";
	private String objective = "";
	private String preparation = "";
	private String strategy = "";
	//private String action_date;
	//private String action_type;
	private String customer_name = "";
	private String action_response = "";
	//private String reschedule_date;
	//private String action_by;
	//private String action_byid;
	//private String purpose;
	//private String action_required_by;
	//private String act_enterd_by;
	//private String act_enterd_date;
	//private String act_request_date;
	//private String enq_key;
	//private String cancel_by;
	//private String cancel_on;
	private String event_visited_latitude;
	private String event_visited_longitude;
	private String event_visited_altitude;
	
	private String event_month;
	private String event_date;
	private String event_date_absolute;
	
	private String action = "";
	private String minutes_of_meet = "";

	private String is_synced_to_server;

	public String getEvent_key() {
		return event_key;
	}
	public void setEvent_key(String event_key) {
		this.event_key = event_key;
	}
	public String getEvent_user_key() {
		return event_user_key;
	}
	public void setEvent_user_key(String event_user_key) {
		this.event_user_key = event_user_key;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public String getFrom_date_time() {
		return from_date_time;
	}
	public void setFrom_date_time(String from_date_time) {
		this.from_date_time = from_date_time;
	}
	public String getTo_date_time() {
		return to_date_time;
	}
	public void setTo_date_time(String to_date_time) {
		this.to_date_time = to_date_time;
	}
	public String getEvent_description() {
		return event_description;
	}
	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCmkey() {
		return cmkey;
	}
	public void setCmkey(String cmkey) {
		this.cmkey = cmkey;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAltitude() {
		return altitude;
	}
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
	public String getVisit_update_time() {
		return visit_update_time;
	}
	public void setVisit_update_time(String visit_update_time) {
		this.visit_update_time = visit_update_time;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getEvent_visited_latitude() {
		return event_visited_latitude;
	}
	public void setEvent_visited_latitude(String event_visited_latitude) {
		this.event_visited_latitude = event_visited_latitude;
	}
	public String getEvent_visited_longitude() {
		return event_visited_longitude;
	}
	public void setEvent_visited_longitude(String event_visited_longitude) {
		this.event_visited_longitude = event_visited_longitude;
	}
	public String getEvent_visited_altitude() {
		return event_visited_altitude;
	}
	public void setEvent_visited_altitude(String event_visited_altitude) {
		this.event_visited_altitude = event_visited_altitude;
	}
	public String getEvent_month() {
		return event_month;
	}
	public void setEvent_month(String event_month) {
		this.event_month = event_month;
	}
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public String getEvent_date_absolute() {
		return event_date_absolute;
	}
	public void setEvent_date_absolute(String event_date_absolute) {
		this.event_date_absolute = event_date_absolute;
	}
	public String getCompleted_on() {
		return completed_on;
	}
	public void setCompleted_on(String completed_on) {
		this.completed_on = completed_on;
	}
	public String getCancelled_on() {
		return cancelled_on;
	}
	public void setCancelled_on(String cancelled_on) {
		this.cancelled_on = cancelled_on;
	}
	public String getEvent_title() {
		return event_title;
	}
	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}
	public String getIs_synced_to_server() {
		return is_synced_to_server;
	}
	public void setIs_synced_to_server(String is_synced_to_server) {
		this.is_synced_to_server = is_synced_to_server;
	}
	public String getEntered_on() {
		return entered_on;
	}
	public void setEntered_on(String entered_on) {
		this.entered_on = entered_on;
	}
	public String getAction_response() {
		return action_response;
	}
	public void setAction_response(String action_response) {
		this.action_response = action_response;
	}
	public String getPreparation() {
		return preparation;
	}
	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getMinutes_of_meet() {
		return minutes_of_meet;
	}
	public void setMinutes_of_meet(String minutes_of_meet) {
		this.minutes_of_meet = minutes_of_meet;
	}
	
}
