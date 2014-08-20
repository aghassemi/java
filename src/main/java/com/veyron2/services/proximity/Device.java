// This file was auto-generated by the veyron vdl tool.
// Source: proximity.vdl
package com.veyron2.services.proximity;

/**
 * type Device struct{MAC string;Names []string;Distance string} 
 * Device represents one neighborhood device.  It contains that device's
 * MAC address, observed names, and the average distance to the device.
 * TODO(bprosnitz) This is bluetooth specific. We need a more generate service as well.
 **/
public final class Device {
    
    
      private java.lang.String mAC;
    
      private java.util.ArrayList<java.lang.String> names;
    
      private java.lang.String distance;
    

    
    public Device(final java.lang.String mAC, final java.util.ArrayList<java.lang.String> names, final java.lang.String distance) {
        
            this.mAC = mAC;
        
            this.names = names;
        
            this.distance = distance;
        
    }

    
    
    public java.lang.String getMAC() {
        return this.mAC;
    }
    public void setMAC(java.lang.String mAC) {
        this.mAC = mAC;
    }
    
    public java.util.ArrayList<java.lang.String> getNames() {
        return this.names;
    }
    public void setNames(java.util.ArrayList<java.lang.String> names) {
        this.names = names;
    }
    
    public java.lang.String getDistance() {
        return this.distance;
    }
    public void setDistance(java.lang.String distance) {
        this.distance = distance;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final Device other = (Device)obj;

        
        
        if (this.mAC == null) {
            if (other.mAC != null) {
                return false;
            }
        } else if (!this.mAC.equals(other.mAC)) {
            return false;
        }
         
        
        
        if (this.names == null) {
            if (other.names != null) {
                return false;
            }
        } else if (!this.names.equals(other.names)) {
            return false;
        }
         
        
        
        if (this.distance == null) {
            if (other.distance != null) {
                return false;
            }
        } else if (!this.distance.equals(other.distance)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (mAC == null ? 0 : mAC.hashCode());
        
        result = prime * result + (names == null ? 0 : names.hashCode());
        
        result = prime * result + (distance == null ? 0 : distance.hashCode());
        
        return result;
    }
}