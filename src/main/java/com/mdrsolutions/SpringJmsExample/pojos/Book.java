package com.mdrsolutions.SpringJmsExample.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
    private final String bookId;
    private final String title;

    public Book(@JsonProperty("bookId")String bookId,
                @JsonProperty("title") String title) {
        this.bookId = bookId;
        this.title = title;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }
}
