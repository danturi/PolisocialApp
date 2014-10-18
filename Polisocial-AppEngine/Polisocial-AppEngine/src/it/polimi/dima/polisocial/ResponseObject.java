/**
 * 
 */
package it.polimi.dima.polisocial;

/**
 * @author danturi
 * 		response object from the server which contains the object and a string in case exception raised
 */
public class ResponseObject {

	private String exception;
	private Object object;
	
	public ResponseObject() {
		super();
		this.exception=null;
		this.object=null;
	}
	
	public ResponseObject(String exception, Object object) {
		super();
		this.exception = exception;
		this.object = object;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
	
}
