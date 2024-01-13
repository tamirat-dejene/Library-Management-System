package com.lms.backend;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class BookTransaction {
    private String issue_id;
    private String book_id;
    private String user_id;
    private String issue_date;
    private String return_date;

    public BookTransaction(String issue_id, String book_id, String user_id, String issue_date, String return_date) {
        this.issue_id = issue_id;
        this.book_id = book_id;
        this.user_id = user_id;
        this.issue_date = issue_date;
        this.return_date = return_date;
    }
    
    public BookTransaction(){
        ////
    }

    public String getIssue_id() {
        return issue_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public String getReturn_date() {
        return return_date;
    }    
    
   // public abstract void returnBook(String book_id, String user_id);
   // public abstract void borrowBook(String borrId, String user_id, String title, Date rd, Date dd);
}
