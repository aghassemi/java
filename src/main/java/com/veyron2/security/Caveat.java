// This file was auto-generated by the veyron vdl tool.
// Source: types.vdl
package com.veyron2.security;

/**
 * type Caveat struct{ValidatorVOM []byte} 
 * Caveat is a condition on the applicability of a blessing (or
 * a Discharge).
 * 
 * These conditions are provided to PrivateID.Bless and are verified
 * in PublicID.Authorize.
 **/
public final class Caveat implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("ValidatorVOM")
      private byte[] validatorVOM;
    

    
    public Caveat(final byte[] validatorVOM) {
        
            this.validatorVOM = validatorVOM;
        
    }

    
    
    public byte[] getValidatorVOM() {
        return this.validatorVOM;
    }
    public void setValidatorVOM(byte[] validatorVOM) {
        this.validatorVOM = validatorVOM;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final Caveat other = (Caveat)obj;

        
        
        if (!java.util.Arrays.equals(this.validatorVOM, other.validatorVOM)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (validatorVOM == null ? 0 : validatorVOM.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, validatorVOM);
    	
    }
	public static final android.os.Parcelable.Creator<Caveat> CREATOR
		= new android.os.Parcelable.Creator<Caveat>() {
		@Override
		public Caveat createFromParcel(android.os.Parcel in) {
			return new Caveat(in);
		}
		@Override
		public Caveat[] newArray(int size) {
			return new Caveat[size];
		}
	};
	private Caveat(android.os.Parcel in) {
		
			this.validatorVOM = (byte[]) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.validatorVOM);
		
	}
}