// This file was auto-generated by the veyron vdl tool.
// Source: schema.vdl
package com.veyron.examples.mdb.schema;

/**
 * type Dir struct{} 
 * Dir is used to represent directories.
 **/
public final class Dir implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    

    
    public Dir() {
        
    }

    
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final Dir other = (Dir)obj;

         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    }
	public static final android.os.Parcelable.Creator<Dir> CREATOR
		= new android.os.Parcelable.Creator<Dir>() {
		@Override
		public Dir createFromParcel(android.os.Parcel in) {
			return new Dir(in);
		}
		@Override
		public Dir[] newArray(int size) {
			return new Dir[size];
		}
	};
	private Dir(android.os.Parcel in) {
		
	}
}