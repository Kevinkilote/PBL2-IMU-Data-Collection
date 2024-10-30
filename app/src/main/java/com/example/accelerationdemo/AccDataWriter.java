package com.example.accelerationdemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class AccDataWriter {

    static final String CRLF = "\r\n";   // End of line
    private AccDataChunk mychunk;
    private Context mcontext;
    public int ID=0;


    public AccDataWriter(Context context)
    {
        mcontext = context;
    }

    public void setAccelerationData(AccDataChunk chunk)
    {
        mychunk = chunk;
    }

    private String FormFileName()
    {
        String s="";

        s = s + System.currentTimeMillis();

        s = "PBL" + s;
        s= s + ".txt";
        return s;
    }


    public void Write()
    {

        // Path to be set to public SD directory (Download)
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);

        String fname = FormFileName();
        File file = new File(path, fname);
        double begtime, curtime, delta;
        int numrec;
        begtime = (double)(mychunk.startHours * 3600) +
                (double)(mychunk.startMinutes * 60) +
                (double)(mychunk.startSeconds) +
                ((double)(mychunk.startMilliseconds))/1000.0f;
        curtime = begtime;

        try {

            OutputStream os = new FileOutputStream(file);

            numrec = mychunk.numberOfRecords;
            for (int i =0; i<numrec;i++)
            {
				/*
				if (i>0)
				{
					long inter = mychunk.time_stamp[i] - mychunk.time_stamp[0]; // Nano
					delta = (((double)inter)/1000000000.0f);
					curtime = begtime + delta;
				}

				 */

                //delta = mychunk.time_stamp[0];
                String oneline = "" ;
                //oneline = oneline + timeToStr(curtime);
                oneline = i + ","+ mychunk.time_stamp[i] + "," + mychunk.acc_x[i] + ","+ mychunk.acc_y[i] + ","+ mychunk.acc_z[i];
                os.write(oneline.getBytes());
                os.write(CRLF.getBytes());

            }

            os.flush();
            os.close();
        } catch(Exception e) {
            System.out.println("Write error");
            Toast.makeText(mcontext,
                    "Writing Test file ERROR " + path.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}