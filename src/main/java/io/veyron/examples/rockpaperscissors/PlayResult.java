// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package io.veyron.examples.rockpaperscissors;

/**
 * type PlayResult struct{YouWon bool} 
 * PlayResult is the value returned by the Play method. It indicates the outcome of the game.
 **/
public final class PlayResult implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("YouWon")
      private boolean youWon;
    

    
    public PlayResult(final boolean youWon) {
        
            this.youWon = youWon;
        
    }

    
    
    public boolean getYouWon() {
        return this.youWon;
    }
    public void setYouWon(boolean youWon) {
        this.youWon = youWon;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final PlayResult other = (PlayResult)obj;

        
        
        
        if (this.youWon != other.youWon) {
            return false;
        }
         
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + java.lang.Boolean.valueOf(youWon).hashCode();
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, youWon);
    	
    }
	public static final android.os.Parcelable.Creator<PlayResult> CREATOR
		= new android.os.Parcelable.Creator<PlayResult>() {
		@Override
		public PlayResult createFromParcel(android.os.Parcel in) {
			return new PlayResult(in);
		}
		@Override
		public PlayResult[] newArray(int size) {
			return new PlayResult[size];
		}
	};
	private PlayResult(android.os.Parcel in) {
		
			this.youWon = (boolean) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.youWon);
		
	}
}