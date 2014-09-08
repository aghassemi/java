// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package com.veyron.examples.rockpaperscissors;

/**
 * type GameID struct{ID string} 
 * A GameID is used to uniquely identify a game within one Judge.
 **/
public final class GameID implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("ID")
      private java.lang.String iD;
    

    
    public GameID(final java.lang.String iD) {
        
            this.iD = iD;
        
    }

    
    
    public java.lang.String getID() {
        return this.iD;
    }
    public void setID(java.lang.String iD) {
        this.iD = iD;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final GameID other = (GameID)obj;

        
        
        
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
        
        result = prime * result + (iD == null ? 0 : iD.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, iD);
    	
    }
	public static final android.os.Parcelable.Creator<GameID> CREATOR
		= new android.os.Parcelable.Creator<GameID>() {
		@Override
		public GameID createFromParcel(android.os.Parcel in) {
			return new GameID(in);
		}
		@Override
		public GameID[] newArray(int size) {
			return new GameID[size];
		}
	};
	private GameID(android.os.Parcel in) {
		
			this.iD = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.iD);
		
	}
}