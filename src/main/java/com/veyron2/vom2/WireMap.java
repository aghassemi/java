// This file was auto-generated by the veyron vdl tool.
// Source: wiretype.vdl
package com.veyron2.vom2;

/**
 * type WireMap struct{Name string;Key veyron2/vom2.TypeID uint64;Elem veyron2/vom2.TypeID} 
 * WireMap represents a map type definition.
 **/
public final class WireMap implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("Name")
      private java.lang.String name;
    
      @com.google.gson.annotations.SerializedName("Key")
      private com.veyron2.vom2.TypeID key;
    
      @com.google.gson.annotations.SerializedName("Elem")
      private com.veyron2.vom2.TypeID elem;
    

    
    public WireMap(final java.lang.String name, final com.veyron2.vom2.TypeID key, final com.veyron2.vom2.TypeID elem) {
        
            this.name = name;
        
            this.key = key;
        
            this.elem = elem;
        
    }

    
    
    public java.lang.String getName() {
        return this.name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public com.veyron2.vom2.TypeID getKey() {
        return this.key;
    }
    public void setKey(com.veyron2.vom2.TypeID key) {
        this.key = key;
    }
    
    public com.veyron2.vom2.TypeID getElem() {
        return this.elem;
    }
    public void setElem(com.veyron2.vom2.TypeID elem) {
        this.elem = elem;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final WireMap other = (WireMap)obj;

        
        
        
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
         
         
        
        
        
        if (this.key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!this.key.equals(other.key)) {
            return false;
        }
         
         
        
        
        
        if (this.elem == null) {
            if (other.elem != null) {
                return false;
            }
        } else if (!this.elem.equals(other.elem)) {
            return false;
        }
         
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (name == null ? 0 : name.hashCode());
        
        result = prime * result + (key == null ? 0 : key.hashCode());
        
        result = prime * result + (elem == null ? 0 : elem.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, name);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, key);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, elem);
    	
    }
	public static final android.os.Parcelable.Creator<WireMap> CREATOR
		= new android.os.Parcelable.Creator<WireMap>() {
		@Override
		public WireMap createFromParcel(android.os.Parcel in) {
			return new WireMap(in);
		}
		@Override
		public WireMap[] newArray(int size) {
			return new WireMap[size];
		}
	};
	private WireMap(android.os.Parcel in) {
		
			this.name = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.name);
		
			this.key = (com.veyron2.vom2.TypeID) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.key);
		
			this.elem = (com.veyron2.vom2.TypeID) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.elem);
		
	}
}