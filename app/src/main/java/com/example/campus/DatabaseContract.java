package com.example.campus;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static class StudentTable implements BaseColumns {

        public static final String TABLE_NAME = "Apply";
        public static final String _ID = "ID";
        public static final String COMP = "CompanyName";
        public static final String CGPA = "CGPA";
        public static final String Resume = "Resume";
        public static final String Name = "Name";

    }
}
