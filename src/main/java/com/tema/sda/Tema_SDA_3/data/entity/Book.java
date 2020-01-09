package com.tema.sda.Tema_SDA_3.data.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "BOOK", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title"}, name = "Titlu unic"),
        @UniqueConstraint(columnNames = {"author"}, name = "Autor unic")
})
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "total_number_of_pages", nullable = false)
    private int totalNumberOfPages;

    @Column(name = "volum", nullable = false)
    private int volum;

    @Column(name = "is_borrow", columnDefinition = "boolean default false", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isBorrow;

    @Column(name = "borrowed_to")
    private String borrowedTo;

    @Column(name = "author", unique = true, nullable = false)
    private String author;

    @Column(name = "section")
    private String section;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public void setTotalNumberOfPages(int totalNumberOfPages) {
        this.totalNumberOfPages = totalNumberOfPages;
    }

    public int getVolum() {
        return volum;
    }

    public void setVolum(int volum) {
        this.volum = volum;
    }

    public boolean isBorrow() {
        return isBorrow;
    }

    public void setBorrow(boolean borrow) {
        isBorrow = borrow;
    }

    public String getBorrowedTo() {
        return borrowedTo;
    }

    public void setBorrowedTo(String borrowedTo) {
        this.borrowedTo = borrowedTo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return totalNumberOfPages == book.totalNumberOfPages &&
                volum == book.volum &&
                isBorrow == book.isBorrow &&
                version == book.version &&
                Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(borrowedTo, book.borrowedTo) &&
                Objects.equals(author, book.author) &&
                Objects.equals(section, book.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, totalNumberOfPages, volum, isBorrow, borrowedTo, author, section, version);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", totalNumberOfPages=" + totalNumberOfPages +
                ", volum=" + volum +
                ", isBorrow=" + isBorrow +
                ", borrowedTo='" + borrowedTo + '\'' +
                ", author='" + author + '\'' +
                ", section='" + section + '\'' +
                ", version=" + version +
                '}';
    }
}
