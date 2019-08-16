package com.yzune.ipcdemo;

import com.yzune.ipcdemo.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}