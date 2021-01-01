package mm.pndaza.tipitakaabidan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String id;
    private String name;
    private String nameInfo;

    public Book(String id, String name, String nameInfo) {
        this.id = id;
        this.name = name;
        this.nameInfo = nameInfo;
    }

    protected Book(Parcel in) {
        id = in.readString();
        name = in.readString();
        nameInfo = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameInfo() {
        return nameInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(nameInfo);
    }
}
