// This file was auto-generated by the veyron vdl tool.
// Source: wire.vdl
package com.veyron2.security.wire;

/**
 * type ChainPrivateID struct{PublicID veyron2/security/wire.ChainPublicID struct{Certificates []veyron2/security/wire.Certificate struct{Name string;PublicKey veyron2/security/wire.PublicKey struct{Curve veyron2/security/wire.KeyCurve byte;XY []byte};Caveats []veyron2/security/wire.Caveat struct{Service veyron2/security.BlessingPattern string;Bytes []byte};Signature veyron2/security.Signature struct{Hash veyron2/security.Hash string;R []byte;S []byte}}};Secret []byte} 
 * ChainPrivateID represents the chain implementation of PrivateIDs from veyron/runtimes/google/security.
 **/
public final class ChainPrivateID implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      private com.veyron2.security.wire.ChainPublicID publicID;
    
      private java.util.List<java.lang.Byte> secret;
    

    
    public ChainPrivateID(final com.veyron2.security.wire.ChainPublicID publicID, final java.util.List<java.lang.Byte> secret) {
        
            this.publicID = publicID;
        
            this.secret = secret;
        
    }

    
    
    public com.veyron2.security.wire.ChainPublicID getPublicID() {
        return this.publicID;
    }
    public void setPublicID(com.veyron2.security.wire.ChainPublicID publicID) {
        this.publicID = publicID;
    }
    
    public java.util.List<java.lang.Byte> getSecret() {
        return this.secret;
    }
    public void setSecret(java.util.List<java.lang.Byte> secret) {
        this.secret = secret;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final ChainPrivateID other = (ChainPrivateID)obj;

        
        
        if (this.publicID == null) {
            if (other.publicID != null) {
                return false;
            }
        } else if (!this.publicID.equals(other.publicID)) {
            return false;
        }
         
        
        
        if (this.secret == null) {
            if (other.secret != null) {
                return false;
            }
        } else if (!this.secret.equals(other.secret)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (publicID == null ? 0 : publicID.hashCode());
        
        result = prime * result + (secret == null ? 0 : secret.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, publicID);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, secret);
    	
    }
	public static final android.os.Parcelable.Creator<ChainPrivateID> CREATOR
		= new android.os.Parcelable.Creator<ChainPrivateID>() {
		@Override
		public ChainPrivateID createFromParcel(android.os.Parcel in) {
			return new ChainPrivateID(in);
		}
		@Override
		public ChainPrivateID[] newArray(int size) {
			return new ChainPrivateID[size];
		}
	};
	private ChainPrivateID(android.os.Parcel in) {
		
			this.publicID = (com.veyron2.security.wire.ChainPublicID) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.publicID);
		
			this.secret = (java.util.List<java.lang.Byte>) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.secret);
		
	}
}