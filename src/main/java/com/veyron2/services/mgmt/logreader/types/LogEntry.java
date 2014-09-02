// This file was auto-generated by the veyron vdl tool.
// Source: types.vdl
package com.veyron2.services.mgmt.logreader.types;

/**
 * type LogEntry struct{Position int64;Line string} 
 * LogLine is a log entry from a log file.
 **/
public final class LogEntry implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      private long position;
    
      private java.lang.String line;
    

    
    public LogEntry(final long position, final java.lang.String line) {
        
            this.position = position;
        
            this.line = line;
        
    }

    
    
    public long getPosition() {
        return this.position;
    }
    public void setPosition(long position) {
        this.position = position;
    }
    
    public java.lang.String getLine() {
        return this.line;
    }
    public void setLine(java.lang.String line) {
        this.line = line;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final LogEntry other = (LogEntry)obj;

        
        
        if (this.position != other.position) {
            return false;
        }
         
        
        
        if (this.line == null) {
            if (other.line != null) {
                return false;
            }
        } else if (!this.line.equals(other.line)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + java.lang.Long.valueOf(position).hashCode();
        
        result = prime * result + (line == null ? 0 : line.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, position);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, line);
    	
    }
	public static final android.os.Parcelable.Creator<LogEntry> CREATOR
		= new android.os.Parcelable.Creator<LogEntry>() {
		@Override
		public LogEntry createFromParcel(android.os.Parcel in) {
			return new LogEntry(in);
		}
		@Override
		public LogEntry[] newArray(int size) {
			return new LogEntry[size];
		}
	};
	private LogEntry(android.os.Parcel in) {
		
			this.position = (long) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.position);
		
			this.line = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.line);
		
	}
}