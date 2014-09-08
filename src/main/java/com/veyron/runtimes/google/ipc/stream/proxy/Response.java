// This file was auto-generated by the veyron vdl tool.
// Source: protocol.vdl
package com.veyron.runtimes.google.ipc.stream.proxy;

/**
 * type Response struct{Error error struct{Id string;Msg string};Endpoint string} 
 * Response is sent by the proxy to the server after processing Request.
 **/
public final class Response implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("Error")
      private com.veyron2.ipc.VeyronException error;
    
      @com.google.gson.annotations.SerializedName("Endpoint")
      private java.lang.String endpoint;
    

    
    public Response(final com.veyron2.ipc.VeyronException error, final java.lang.String endpoint) {
        
            this.error = error;
        
            this.endpoint = endpoint;
        
    }

    
    
    public com.veyron2.ipc.VeyronException getError() {
        return this.error;
    }
    public void setError(com.veyron2.ipc.VeyronException error) {
        this.error = error;
    }
    
    public java.lang.String getEndpoint() {
        return this.endpoint;
    }
    public void setEndpoint(java.lang.String endpoint) {
        this.endpoint = endpoint;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final Response other = (Response)obj;

        
        
        
        if (this.error == null) {
            if (other.error != null) {
                return false;
            }
        } else if (!this.error.equals(other.error)) {
            return false;
        }
         
         
        
        
        
        if (this.endpoint == null) {
            if (other.endpoint != null) {
                return false;
            }
        } else if (!this.endpoint.equals(other.endpoint)) {
            return false;
        }
         
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (error == null ? 0 : error.hashCode());
        
        result = prime * result + (endpoint == null ? 0 : endpoint.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, error);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, endpoint);
    	
    }
	public static final android.os.Parcelable.Creator<Response> CREATOR
		= new android.os.Parcelable.Creator<Response>() {
		@Override
		public Response createFromParcel(android.os.Parcel in) {
			return new Response(in);
		}
		@Override
		public Response[] newArray(int size) {
			return new Response[size];
		}
	};
	private Response(android.os.Parcel in) {
		
			this.error = (com.veyron2.ipc.VeyronException) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.error);
		
			this.endpoint = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.endpoint);
		
	}
}