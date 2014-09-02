// This file was auto-generated by the veyron vdl tool.
// Source: vsync.vdl
package com.veyron.runtimes.google.vsync;

/**
 * type LogValue struct{Mutation veyron/services/store/raw.Mutation struct{ID veyron2/storage.ID [16]byte;PriorVersion veyron/services/store/raw.Version uint64;Version veyron/services/store/raw.Version;IsRoot bool;Value any;Dir []veyron/services/store/raw.DEntry struct{Name string;ID veyron2/storage.ID}};SyncTime int64;Delete bool;Continued bool} 
 * LogValue represents an object mutation within a transaction.
 **/
public final class LogValue implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      private com.veyron.services.store.raw.Mutation mutation;
    
      private long syncTime;
    
      private boolean delete;
    
      private boolean continued;
    

    
    public LogValue(final com.veyron.services.store.raw.Mutation mutation, final long syncTime, final boolean delete, final boolean continued) {
        
            this.mutation = mutation;
        
            this.syncTime = syncTime;
        
            this.delete = delete;
        
            this.continued = continued;
        
    }

    
    
    public com.veyron.services.store.raw.Mutation getMutation() {
        return this.mutation;
    }
    public void setMutation(com.veyron.services.store.raw.Mutation mutation) {
        this.mutation = mutation;
    }
    
    public long getSyncTime() {
        return this.syncTime;
    }
    public void setSyncTime(long syncTime) {
        this.syncTime = syncTime;
    }
    
    public boolean getDelete() {
        return this.delete;
    }
    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    
    public boolean getContinued() {
        return this.continued;
    }
    public void setContinued(boolean continued) {
        this.continued = continued;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final LogValue other = (LogValue)obj;

        
        
        if (this.mutation == null) {
            if (other.mutation != null) {
                return false;
            }
        } else if (!this.mutation.equals(other.mutation)) {
            return false;
        }
         
        
        
        if (this.syncTime != other.syncTime) {
            return false;
        }
         
        
        
        if (this.delete != other.delete) {
            return false;
        }
         
        
        
        if (this.continued != other.continued) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (mutation == null ? 0 : mutation.hashCode());
        
        result = prime * result + java.lang.Long.valueOf(syncTime).hashCode();
        
        result = prime * result + java.lang.Boolean.valueOf(delete).hashCode();
        
        result = prime * result + java.lang.Boolean.valueOf(continued).hashCode();
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, mutation);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, syncTime);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, delete);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, continued);
    	
    }
	public static final android.os.Parcelable.Creator<LogValue> CREATOR
		= new android.os.Parcelable.Creator<LogValue>() {
		@Override
		public LogValue createFromParcel(android.os.Parcel in) {
			return new LogValue(in);
		}
		@Override
		public LogValue[] newArray(int size) {
			return new LogValue[size];
		}
	};
	private LogValue(android.os.Parcel in) {
		
			this.mutation = (com.veyron.services.store.raw.Mutation) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.mutation);
		
			this.syncTime = (long) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.syncTime);
		
			this.delete = (boolean) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.delete);
		
			this.continued = (boolean) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.continued);
		
	}
}