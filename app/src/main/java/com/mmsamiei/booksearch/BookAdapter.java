package com.mmsamiei.booksearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Win2 on 7/27/2016.
 */
public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, ArrayList<Book> aBooks) {
        super(context, 0, aBooks);
    }
    //TODO VIEW HOLDER
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Book book = getItem(position);
    }
}
