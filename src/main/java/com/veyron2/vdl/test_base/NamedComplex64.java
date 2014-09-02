
// This file was auto-generated by the veyron vdl tool.
// Source: base.vdl
package com.veyron2.vdl.test_base;

/**
 * type NamedComplex64 complex64 
 **/
public final class NamedComplex64 implements android.os.Parcelable, java.io.Serializable {
    private org.apache.commons.math3.complex.Complex value;

    public NamedComplex64(org.apache.commons.math3.complex.Complex value) {
        this.value = value;
    }
    public org.apache.commons.math3.complex.Complex getValue() { return this.value; }

    public void setValue(org.apache.commons.math3.complex.Complex value) { this.value = value; }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final com.veyron2.vdl.test_base.NamedComplex64 other = (com.veyron2.vdl.test_base.NamedComplex64)obj;
        
        if (this.value == null) {
            return other.value == null;
        }
        return this.value.equals(other.value);
        
    }
    @Override
    public int hashCode() {
        return (value == null ? 0 : value.hashCode());
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
   		com.veyron2.vdl.ParcelUtil.writeValue(out, value);
    }
	public static final android.os.Parcelable.Creator<NamedComplex64> CREATOR
		= new android.os.Parcelable.Creator<NamedComplex64>() {
		@Override
		public NamedComplex64 createFromParcel(android.os.Parcel in) {
			return new NamedComplex64(in);
		}
		@Override
		public NamedComplex64[] newArray(int size) {
			return new NamedComplex64[size];
		}
	};
	private NamedComplex64(android.os.Parcel in) {
		value = (org.apache.commons.math3.complex.Complex) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), value);
	}
}
