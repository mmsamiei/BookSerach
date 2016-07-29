package com.mmsamiei.booksearch;

import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BookListActivity extends AppCompatActivity  {
    private ListView lvBooks;
    private BookAdapter bookAdapter;
    private BookClient client;
    private CircularProgressView progressView;

    //


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_list,menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBooks(query);
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                BookListActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        lvBooks = (ListView) findViewById(R.id.lvBooks);
        ArrayList<Book> aBooks = new ArrayList<Book>();
        bookAdapter= new BookAdapter(this,aBooks);
        lvBooks.setAdapter(bookAdapter);
        progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.setVisibility(CircularProgressView.GONE);
    }
    private void fetchBooks(String s){
        progressView.setVisibility(CircularProgressView.VISIBLE);
        progressView.startAnimation();
        client = new BookClient();
        client.getBooks(s,new JsonHttpResponseHandler(){
           @Override
           public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               try {
                   progressView.setVisibility(CircularProgressView.GONE);
                   progressView.stopAnimation();
                   JSONArray docs=null;
                   if(response!=null){
                       docs=response.getJSONArray("docs");
                       final ArrayList<Book> books = Book.fromJson(docs);
                       bookAdapter.clear();
                       for (Book book : books) {
                           bookAdapter.add(book);
                       }
                       bookAdapter.notifyDataSetChanged();
                   }
               }
               catch (JSONException e){
                   e.printStackTrace();
               }
           }

           @Override
           public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
               progressView.setVisibility(CircularProgressView.GONE);
           }
       });

    }


}
