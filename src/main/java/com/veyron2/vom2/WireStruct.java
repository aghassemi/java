// This file was auto-generated by the veyron vdl tool.
// Source: wiretype.vdl
package com.veyron2.vom2;

/**
 * type WireStruct struct{Name string;Fields []veyron2/vom2.WireField struct{Name string;Type veyron2/vom2.TypeID uint64}} 
 * WireStruct represents a struct type definition.
 **/
public final class WireStruct implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      private java.lang.String name;
    
      private java.util.List<com.veyron2.vom2.WireField> fields;
    

    
    public WireStruct(final java.lang.String name, final java.util.List<com.veyron2.vom2.WireField> fields) {
        
            this.name = name;
        
            this.fields = fields;
        
    }

    
    
    public java.lang.String getName() {
        return this.name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public java.util.List<com.veyron2.vom2.WireField> getFields() {
        return this.fields;
    }
    public void setFields(java.util.List<com.veyron2.vom2.WireField> fields) {
        this.fields = fields;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final WireStruct other = (WireStruct)obj;

        
        
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
         
        
        
        if (this.fields == null) {
            if (other.fields != null) {
                return false;
            }
        } else if (!this.fields.equals(other.fields)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (name == null ? 0 : name.hashCode());
        
        result = prime * result + (fields == null ? 0 : fields.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, name);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, fields);
    	
    }
	public static final android.os.Parcelable.Creator<WireStruct> CREATOR
		= new android.os.Parcelable.Creator<WireStruct>() {
		@Override
		public WireStruct createFromParcel(android.os.Parcel in) {
			return new WireStruct(in);
		}
		@Override
		public WireStruct[] newArray(int size) {
			return new WireStruct[size];
		}
	};
	private WireStruct(android.os.Parcel in) {
		
			this.name = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.name);
		
			this.fields = (java.util.List<com.veyron2.vom2.WireField>) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.fields);
		
	}
}