// This file was auto-generated by the veyron vdl tool.
// Source: base.vdl

package com.veyron2.vdl.test_base;

/**
 * NamedSet set[string] 
 **/
public final class NamedSet implements java.util.Set<java.lang.String>, android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    private java.util.Set<java.lang.String> impl;

    public NamedSet(java.util.Set<java.lang.String> impl) {
        this.impl = impl;
    }

    public java.util.Set<java.lang.String> getValue() {
        return this.impl;
    }

    public void setValue(java.util.Set<java.lang.String> newImpl) {
        this.impl = newImpl;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final NamedSet other = (NamedSet) obj;
        if (!(this.impl.equals(other.impl)))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        return (impl == null) ? 0 : impl.hashCode();
    }

    @Override
    public void clear() {
        impl.clear();
    }
    @Override
    public boolean add(java.lang.String object) {
        return impl.add(object);
    }
    @Override
    public boolean addAll(java.util.Collection<? extends java.lang.String> collection) {
        return impl.addAll(collection);
    }
    @Override
    public boolean contains(java.lang.Object object) {
        return impl.contains(object);
    }
    @Override
    public boolean containsAll(java.util.Collection<?> collection) {
        return impl.containsAll(collection);
    }
    @Override
    public boolean isEmpty() {
        return impl.isEmpty();
    }
    @Override
    public java.util.Iterator<java.lang.String> iterator() {
        return impl.iterator();
    }
    @Override
    public boolean remove(java.lang.Object object) {
        return impl.remove(object);
    }
    @Override
    public boolean removeAll(java.util.Collection<?> collection) {
        return impl.removeAll(collection);
    }
    @Override
    public boolean retainAll(java.util.Collection<?> collection) {
        return impl.retainAll(collection);
    }
    @Override
    public int size() {
        return impl.size();
    }
    @Override
    public java.lang.Object[] toArray() {
        return impl.toArray();
    }
    @Override
    public <T> T[] toArray(T[] array) {
        return impl.toArray(array);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
        com.veyron2.vdl.ParcelUtil.writeValue(out, impl);
    }
    public static final android.os.Parcelable.Creator<NamedSet> CREATOR = new android.os.Parcelable.Creator<NamedSet>() {
        @Override
        public NamedSet createFromParcel(android.os.Parcel in) {
            return new NamedSet(in);
        }
        @Override
        public NamedSet[] newArray(int size) {
            return new NamedSet[size];
        }
    };
    private NamedSet(android.os.Parcel in) {
        impl = (java.util.Set<java.lang.String>) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), impl);
    }
}
