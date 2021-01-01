package mm.pndaza.tipitakaabidan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable
{
    private int id;
    private String word;
    private String bookid;
    private String bookName;
    private int pageNumber;

    public Word(int id, String word) {
        this.id = id;
        this.word = word;
    }

    protected Word(Parcel in) {
        id = in.readInt();
        word = in.readString();
        bookid = in.readString();
        bookName = in.readString();
        pageNumber = in.readInt();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(word);
        parcel.writeString(bookid);
        parcel.writeString(bookName);
        parcel.writeInt(pageNumber);
    }
}
