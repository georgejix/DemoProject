package com.test.aidl;
import com.test.aidl.Book;
import com.test.aidl.IOnNewBookArrivedListener;

//新建之后build，会自动在app/build/generated/source/aidl/debug目录下生成对应文件
interface IBookManager{
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListner(IOnNewBookArrivedListener listener);
    void unregisterListner(IOnNewBookArrivedListener listener);
}