package com.tema.sda.Tema_SDA_3.web.dto;

import com.tema.sda.Tema_SDA_3.custom.annotations.CriticalFieldsValidation;

import java.io.Serializable;
import java.util.Objects;

public class BookDTO implements Serializable {

    @CriticalFieldsValidation
    private String title;

    private int totalNumberOfPages;

    private int volum;

    private boolean isBorrow;

    private String borrowedTo;

    @CriticalFieldsValidation
    private String author;

    private String section;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return title.equals(bookDTO.title) &&
                totalNumberOfPages == bookDTO.totalNumberOfPages &&
                volum == bookDTO.volum &&
                isBorrow == bookDTO.isBorrow &&
                Objects.equals(borrowedTo, bookDTO.borrowedTo) &&
                Objects.equals(author, bookDTO.author) &&
                Objects.equals(section, bookDTO.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, totalNumberOfPages, volum, isBorrow, borrowedTo, author, section);
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "title=" + title +
                ", totalNumberOfPages=" + totalNumberOfPages +
                ", volum=" + volum +
                ", isBorrow=" + isBorrow +
                ", borrowedTo='" + borrowedTo + '\'' +
                ", author='" + author + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}
