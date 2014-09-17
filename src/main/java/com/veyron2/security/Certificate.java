// This file was auto-generated by the veyron vdl tool.
// Source: types.vdl
package com.veyron2.security;

/**
 * type Certificate struct{Extension string;PublicKey []byte;Caveats []veyron2/security.Caveat struct{ValidatorVOM []byte};Signature veyron2/security.Signature struct{Purpose []byte;Hash veyron2/security.Hash string;R []byte;S []byte}} 
 * Certificate represents the cryptographic proof of the binding of
 * extensions of a blessing held by one principal to another (represented by
 * a public key) under specific caveats.
 * 
 * For example, if a principal P1 has a blessing "alice", then it can
 * extend it with a Certificate to generate the blessing "alice/friend" for
 * another principal P2.
 **/
public final class Certificate implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("Extension")
      private java.lang.String extension;
    
      @com.google.gson.annotations.SerializedName("PublicKey")
      private byte[] publicKey;
    
      @com.google.gson.annotations.SerializedName("Caveats")
      private java.util.List<com.veyron2.security.Caveat> caveats;
    
      @com.google.gson.annotations.SerializedName("Signature")
      private com.veyron2.security.Signature signature;
    

    
    public Certificate(final java.lang.String extension, final byte[] publicKey, final java.util.List<com.veyron2.security.Caveat> caveats, final com.veyron2.security.Signature signature) {
        
            this.extension = extension;
        
            this.publicKey = publicKey;
        
            this.caveats = caveats;
        
            this.signature = signature;
        
    }

    
    
    public java.lang.String getExtension() {
        return this.extension;
    }
    public void setExtension(java.lang.String extension) {
        this.extension = extension;
    }
    
    public byte[] getPublicKey() {
        return this.publicKey;
    }
    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }
    
    public java.util.List<com.veyron2.security.Caveat> getCaveats() {
        return this.caveats;
    }
    public void setCaveats(java.util.List<com.veyron2.security.Caveat> caveats) {
        this.caveats = caveats;
    }
    
    public com.veyron2.security.Signature getSignature() {
        return this.signature;
    }
    public void setSignature(com.veyron2.security.Signature signature) {
        this.signature = signature;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final Certificate other = (Certificate)obj;

        
        
        
        if (this.extension == null) {
            if (other.extension != null) {
                return false;
            }
        } else if (!this.extension.equals(other.extension)) {
            return false;
        }
         
         
        
        
        if (!java.util.Arrays.equals(this.publicKey, other.publicKey)) {
            return false;
        }
         
        
        
        
        if (this.caveats == null) {
            if (other.caveats != null) {
                return false;
            }
        } else if (!this.caveats.equals(other.caveats)) {
            return false;
        }
         
         
        
        
        
        if (this.signature == null) {
            if (other.signature != null) {
                return false;
            }
        } else if (!this.signature.equals(other.signature)) {
            return false;
        }
         
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (extension == null ? 0 : extension.hashCode());
        
        result = prime * result + (publicKey == null ? 0 : publicKey.hashCode());
        
        result = prime * result + (caveats == null ? 0 : caveats.hashCode());
        
        result = prime * result + (signature == null ? 0 : signature.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, extension);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, publicKey);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, caveats);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, signature);
    	
    }
	public static final android.os.Parcelable.Creator<Certificate> CREATOR
		= new android.os.Parcelable.Creator<Certificate>() {
		@Override
		public Certificate createFromParcel(android.os.Parcel in) {
			return new Certificate(in);
		}
		@Override
		public Certificate[] newArray(int size) {
			return new Certificate[size];
		}
	};
	private Certificate(android.os.Parcel in) {
		
			this.extension = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.extension);
		
			this.publicKey = (byte[]) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.publicKey);
		
			this.caveats = (java.util.List<com.veyron2.security.Caveat>) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.caveats);
		
			this.signature = (com.veyron2.security.Signature) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.signature);
		
	}
}