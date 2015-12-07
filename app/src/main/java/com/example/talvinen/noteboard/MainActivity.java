package com.example.talvinen.noteboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends Activity implements OnTouchListener, View.OnClickListener {

    private ImageView mImageView;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private ImageView mImageView4;
    private EditText mEditText;
    private TextView mTextView;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private ViewGroup mRootLayoutI;
    private ViewGroup mRootLayoutI2;
    private ViewGroup mRootLayoutI3;
    private ViewGroup mRootLayoutI4;
    private ViewGroup mRootLayoutT;
    private ViewGroup mRootLayoutT2;
    private ViewGroup mRootLayoutT3;
    private ViewGroup mRootLayoutT4;
    private int _xDelta;
    private int _yDelta;

    Button btnSave;
    Button btnDelete;

    private int _Note_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootLayoutI = (ViewGroup) findViewById(R.id.root2);
        mImageView = (ImageView) mRootLayoutI.findViewById(R.id.camera1);

        mRootLayoutI2 = (ViewGroup) findViewById(R.id.root2);
        mImageView2 = (ImageView) mRootLayoutI2.findViewById(R.id.camera2);

        mRootLayoutI3 = (ViewGroup) findViewById(R.id.root2);
        mImageView3 = (ImageView) mRootLayoutI3.findViewById(R.id.camera3);

        mRootLayoutI4 = (ViewGroup) findViewById(R.id.root2);
        mImageView4 = (ImageView) mRootLayoutI4.findViewById(R.id.camera4);

        mEditText = (EditText) findViewById(R.id.editText);

        mRootLayoutT = (ViewGroup) findViewById(R.id.root2);
        mTextView = (TextView) mRootLayoutT.findViewById(R.id.textView);

        mRootLayoutT2 = (ViewGroup) findViewById(R.id.root2);
        mTextView2 = (TextView) mRootLayoutT2.findViewById(R.id.textView2);

        mRootLayoutT3 = (ViewGroup) findViewById(R.id.root2);
        mTextView3 = (TextView) mRootLayoutT3.findViewById(R.id.textView3);

        mRootLayoutT4 = (ViewGroup) findViewById(R.id.root2);
        mTextView4 = (TextView) mRootLayoutT4.findViewById(R.id.textView4);

        RelativeLayout.LayoutParams layoutParamsI = new RelativeLayout.LayoutParams(180, 180);
        mImageView.setLayoutParams(layoutParamsI);
        mImageView.setOnTouchListener(this);

        RelativeLayout.LayoutParams layoutParamsI2 = new RelativeLayout.LayoutParams(180, 180);
        mImageView2.setLayoutParams(layoutParamsI2);
        mImageView2.setOnTouchListener(this);

        RelativeLayout.LayoutParams layoutParamsI3 = new RelativeLayout.LayoutParams(180, 180);
        mImageView3.setLayoutParams(layoutParamsI3);
        mImageView3.setOnTouchListener(this);

        RelativeLayout.LayoutParams layoutParamsI4 = new RelativeLayout.LayoutParams(180, 180);
        mImageView4.setLayoutParams(layoutParamsI4);
        mImageView4.setOnTouchListener(this);

        RelativeLayout.LayoutParams layoutParamsT = new RelativeLayout.LayoutParams(200, 200);
        mTextView.setLayoutParams(layoutParamsT);
        mTextView.setOnTouchListener(this);

        RelativeLayout.LayoutParams layoutParamsT2 = new RelativeLayout.LayoutParams(200, 200);
        mTextView2.setLayoutParams(layoutParamsT2);
        mTextView2.setOnTouchListener(this);

        RelativeLayout.LayoutParams layoutParamsT3 = new RelativeLayout.LayoutParams(200, 200);
        mTextView3.setLayoutParams(layoutParamsT3);
        mTextView3.setOnTouchListener(this);

        RelativeLayout.LayoutParams layoutParamsT4 = new RelativeLayout.LayoutParams(200, 200);
        mTextView4.setLayoutParams(layoutParamsT4);
        mTextView4.setOnTouchListener(this);

        Button btnImg = (Button) findViewById(R.id.imageButton);
        Button btnText = (Button) findViewById(R.id.textButton);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        /*_Note_Id = 0;
        Intent intent = getIntent();
        _Note_Id =intent.getIntExtra("note_Id", 0);
        NoteRepo repo = new NoteRepo(this);
        Note note;
        new Note();
        note = repo.getNoteById(_Note_Id);

        mTextView.setText(note.text);
        mTextView2.setText(note.text2);
        mTextView3.setText(note.text3);
        mTextView4.setText(note.text4);*/

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSlot();
                Toast.makeText(MainActivity.this, "You clicked the button!", Toast.LENGTH_LONG).show();
            }
        });

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectText();
                Toast.makeText(MainActivity.this, "You clicked the button!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            NoteRepo repo = new NoteRepo(this);
            Note note = new Note();
            note.text=mTextView.getText().toString();
            note.text2=mTextView2.getText().toString();
            note.text3=mTextView3.getText().toString();
            note.text4=mTextView4.getText().toString();
            note.note_ID=_Note_Id;

            if (_Note_Id==0){
                _Note_Id = repo.insert(note);
                Toast.makeText(this,"New Note Insert",Toast.LENGTH_SHORT).show();
            }else{
                repo.update(note);
                Toast.makeText(this,"Note Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            NoteRepo repo = new NoteRepo(this);
            repo.delete(_Note_Id);
            Toast.makeText(this, "Note Record Deleted", Toast.LENGTH_SHORT);
            finish();
        }
    }

    private void selectText() {
        final CharSequence[] options = { "Slot 1", "Slot 2", "Slot 3", "Slot 4", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Slot");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Slot 1")) {
                    mTextView.setText(mEditText.getText());
                } else if (options[item].equals("Slot 2")) {
                    mTextView2.setText(mEditText.getText());
                } else if (options[item].equals("Slot 3")) {
                    mTextView3.setText(mEditText.getText());
                } else if (options[item].equals("Slot 4")) {
                    mTextView4.setText(mEditText.getText());
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectSlot() {
        final CharSequence[] options = { "Slot 1", "Slot 2", "Slot 3", "Slot 4", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Slot");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Slot 1")) {
                    selectImage();
                } else if (options[item].equals("Slot 2")) {
                    selectImage2();
                } else if (options[item].equals("Slot 3")) {
                    selectImage3();
                } else if (options[item].equals("Slot 4")) {
                    selectImage4();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intentImage, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intentImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intentImage, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectImage2() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intentImage, 3);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intentImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intentImage, 4);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectImage3() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intentImage, 5);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intentImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intentImage, 6);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectImage4() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intentImage, 7);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intentImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intentImage, 8);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentImage) {
        super.onActivityResult(requestCode, resultCode, intentImage);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

                    mImageView.setImageBitmap(bitmap);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = intentImage.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image ...***...", picturePath + "");
                mImageView.setImageBitmap(thumbnail);
            } else if (requestCode == 3) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

                    mImageView2.setImageBitmap(bitmap);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 4) {

                Uri selectedImage = intentImage.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image ...***...", picturePath + "");
                mImageView2.setImageBitmap(thumbnail);
            } else if (requestCode == 5) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

                    mImageView3.setImageBitmap(bitmap);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 6) {

                Uri selectedImage = intentImage.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image ...***...", picturePath + "");
                mImageView3.setImageBitmap(thumbnail);
            } else if (requestCode == 7) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

                    mImageView4.setImageBitmap(bitmap);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 8) {

                Uri selectedImage = intentImage.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image ...***...", picturePath + "");
                mImageView4.setImageBitmap(thumbnail);
            }
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                view.setLayoutParams(layoutParams);
                break;
        }
        mRootLayoutI.invalidate();
        mRootLayoutI2.invalidate();
        mRootLayoutI3.invalidate();
        mRootLayoutI4.invalidate();
        mRootLayoutT.invalidate();
        mRootLayoutT2.invalidate();
        mRootLayoutT3.invalidate();
        mRootLayoutT4.invalidate();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}