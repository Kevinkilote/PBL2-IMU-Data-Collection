package com.example.accelerationdemo;

public class AccDataChunk {
    private int max_chunk_size = 100000;
    public int numberOfRecords=0;
    public int maximumNumberOfRecords = 100000;
    public int startYear;
    public int startMonth;
    public int startDay;
    public int startHours;
    public int startMinutes;
    public int startSeconds;
    public int startMilliseconds;
    public int caller =-1;
    public float [] acc_x;
    public float [] acc_y;
    public float [] acc_z;
    public long  [] time_stamp;

    // Constructor
    public AccDataChunk(int maxsize)
    {
        maximumNumberOfRecords = maxsize;
        if (maximumNumberOfRecords<1 || maximumNumberOfRecords> max_chunk_size)
        {
            maximumNumberOfRecords = max_chunk_size;
        }

        acc_x = new float[maximumNumberOfRecords];
        acc_y = new float[maximumNumberOfRecords];
        acc_z = new float[maximumNumberOfRecords];
        time_stamp = new long[maximumNumberOfRecords];

        numberOfRecords=0;

    }
}