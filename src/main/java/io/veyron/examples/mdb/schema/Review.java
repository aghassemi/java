// This file was auto-generated by the veyron vdl tool.
// Source: schema.vdl
package io.veyron.examples.mdb.schema;

/**
 * type Review struct{Rating byte;Text string} 
 * Review is a movie review.
 **/
public final class Review implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("Rating")
      private byte rating;
    
      @com.google.gson.annotations.SerializedName("Text")
      private java.lang.String text;
    

    
    public Review(final byte rating, final java.lang.String text) {
        
            this.rating = rating;
        
            this.text = text;
        
    }

    
    
    public byte getRating() {
        return this.rating;
    }
    public void setRating(byte rating) {
        this.rating = rating;
    }
    
    public java.lang.String getText() {
        return this.text;
    }
    public void setText(java.lang.String text) {
        this.text = text;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final Review other = (Review)obj;

        
        
        
        if (this.rating != other.rating) {
            return false;
        }
         
         
        
        
        
        if (this.text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!this.text.equals(other.text)) {
            return false;
        }
         
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (int)rating;
        
        result = prime * result + (text == null ? 0 : text.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, rating);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, text);
    	
    }
	public static final android.os.Parcelable.Creator<Review> CREATOR
		= new android.os.Parcelable.Creator<Review>() {
		@Override
		public Review createFromParcel(android.os.Parcel in) {
			return new Review(in);
		}
		@Override
		public Review[] newArray(int size) {
			return new Review[size];
		}
	};
	private Review(android.os.Parcel in) {
		
			this.rating = (byte) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.rating);
		
			this.text = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.text);
		
	}
}