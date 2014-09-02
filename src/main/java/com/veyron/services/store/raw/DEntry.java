// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package com.veyron.services.store.raw;

/**
 * type DEntry struct{Name string;ID veyron2/storage.ID [16]byte} 
 * DEntry is a directory entry.
 **/
public final class DEntry implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      private java.lang.String name;
    
      private com.veyron2.storage.ID iD;
    

    
    public DEntry(final java.lang.String name, final com.veyron2.storage.ID iD) {
        
            this.name = name;
        
            this.iD = iD;
        
    }

    
    
    public java.lang.String getName() {
        return this.name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public com.veyron2.storage.ID getID() {
        return this.iD;
    }
    public void setID(com.veyron2.storage.ID iD) {
        this.iD = iD;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final DEntry other = (DEntry)obj;

        
        
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
         
        
        
        if (this.iD == null) {
            if (other.iD != null) {
                return false;
            }
        } else if (!this.iD.equals(other.iD)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (name == null ? 0 : name.hashCode());
        
        result = prime * result + (iD == null ? 0 : iD.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, name);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, iD);
    	
    }
	public static final android.os.Parcelable.Creator<DEntry> CREATOR
		= new android.os.Parcelable.Creator<DEntry>() {
		@Override
		public DEntry createFromParcel(android.os.Parcel in) {
			return new DEntry(in);
		}
		@Override
		public DEntry[] newArray(int size) {
			return new DEntry[size];
		}
	};
	private DEntry(android.os.Parcel in) {
		
			this.name = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.name);
		
			this.iD = (com.veyron2.storage.ID) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.iD);
		
	}
}