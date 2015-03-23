package paulajbastos.com.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import paulajbastos.com.interactivestory.R;
import paulajbastos.com.interactivestory.model.Page;
import paulajbastos.com.interactivestory.model.Story;


public class StoryActivity extends ActionBarActivity {
    public static final String TAG = StoryActivity.class.getSimpleName();
    //private Page[] mPages;

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mCurrentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        //String name = intent.getStringExtra("name");
        mName = intent.getStringExtra(getString(R.string.key_name));
        if (mName == null){
            mName = "Friend";
        }

        //Log.d(TAG, mName);

        TextView theNameLogged = (TextView) findViewById(R.id.nameLogged);
        theNameLogged = (TextView) findViewById(R.id.nameLogged);

         mImageView = (ImageView) findViewById(R.id.storyImageView);
         mTextView = (TextView) findViewById(R.id.storyTextView);
         mChoice1 = (Button) findViewById(R.id.choiceButton1);
         mChoice2 = (Button) findViewById(R.id.choiceButton2);

        //theNameLogged.setText("Olá " + mName);
        //mPages = new Page[7];
        loadPage(0);

    }
    private void loadPage(int choice){

        mCurrentPage =  mStory.getPage(choice);
        //final Page page = mStory.getPage(choice);

        Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId());
        mImageView.setImageDrawable(drawable);

        String pageText = mCurrentPage.getText();
        //add the name if placeholder included. Wont add if no placeholder.
        pageText = String.format(pageText, mName);
        mTextView.setText(pageText);

        if(mCurrentPage.isFinal()){
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("PLAY AGAIN");
            mChoice2.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    //loadPage(0);
                }
            });

        }
        else {
            mChoice1.setText(mCurrentPage.getChoice1().getText());
            mChoice2.setText(mCurrentPage.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //vraiable page cant be read from here because this event cant see it. So we could call a
                    // FINAL before the Page page variable. But we didn't... we've refactored the page
                    // variable named it to mCurrentPage and have it declared it on the create method.
                    //---- int nextPage = page.getChoice1().getNextPage() ---- ;

                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //vraiable page cant be read from here because this event cant see it. So we could call a
                    // FINAL before the Page page variable. But we didn't... we've refactored the page
                    // variable named it to mCurrentPage and have it declared it on the create method.
                    //---- int nextPage = page.getChoice1().getNextPage() ---- ;

                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }

    }
}
