package com.cts.userservice.dto;


import org.springframework.http.HttpStatus;
 
public class Response<T> {
	private Boolean success;
	private HttpStatus status;
	private T data;
	private String errorMessage;

	public Response(){

	}

	/**
     * Constructor for a successful response.
     *
     * @param success whether the operation was successful
     * @param status the HTTP status of the response
     * @param data the data to be included in the response
     */
    public Response(Boolean success, HttpStatus status, T data, String errorMessage) {
        this.success = success;
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
    }

	
	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}