
// This file was auto-generated by the veyron vdl tool.
// Source: wire.vdl
package com.veyron2.security.wire;

/**
 * type KeyCurve byte 
 * KeyCurve defines a namespace for elliptic curves.
 **/
public final class KeyCurve implements android.os.Parcelable, java.io.Serializable {
    private byte value;

    public KeyCurve(byte value) {
        this.value = value;
    }
    public byte getValue() { return this.value; }

    public void setValue(byte value) { this.value = value; }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final com.veyron2.security.wire.KeyCurve other = (com.veyron2.security.wire.KeyCurve)obj;
        
        return this.value == other.value;
        
    }
    @Override
    public int hashCode() {
        return (int)value;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
   		com.veyron2.vdl.ParcelUtil.writeValue(out, value);
    }
	public static final android.os.Parcelable.Creator<KeyCurve> CREATOR
		= new android.os.Parcelable.Creator<KeyCurve>() {
		@Override
		public KeyCurve createFromParcel(android.os.Parcel in) {
			return new KeyCurve(in);
		}
		@Override
		public KeyCurve[] newArray(int size) {
			return new KeyCurve[size];
		}
	};
	private KeyCurve(android.os.Parcel in) {
		value = (byte) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), value);
	}
}
