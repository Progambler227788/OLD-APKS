package com.example.connectfour;

import static android.os.SystemClock.sleep;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

public class BoardFragment extends Fragment {
    public static final String GAME_STATE = "gameState";
    private ConnectFourGame mGame;

    private Context context;

    // Inside the onCreateView method
    GridLayout mGrid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

         mGrid = view.findViewById(R.id.grid_layout);

        mGame = new ConnectFourGame();

        context = getActivity();

        if (savedInstanceState == null) {
            startGame();
        } else {
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setDisc();
        }

        for (int i = 0; i < mGrid.getChildCount(); i++) {
            if (mGrid.getChildAt(i) instanceof Button) {
                Button button = (Button) mGrid.getChildAt(i);
                button.setOnClickListener(this::onButtonClick);
            }
        }

        return view;
    }

    // New method startGame
    private void startGame() {
        mGame.newGame();
        setDisc();
    }

    // Updated onButtonClick method
    public void onButtonClick(View view) {
       // mGrid = view.findViewById(R.id.grid_layout);
        int buttonIndex = mGrid.indexOfChild(view);
        int row = buttonIndex / ConnectFourGame.COL;
        int col = buttonIndex % ConnectFourGame.COL;

        mGame.selectDisc(row, col);
        setDisc();

        if (mGame.isGameOver()) {
            // Instantiate a Toast to congratulate the player if they won the game
            // Call method newGame in class ConnectFourGame
            // Call method setDisc
            sleep(4);
            if(mGame.player == 2) {
                Toast.makeText(context, "Congratulations! Player 1 (BLUE) won!", Toast.LENGTH_LONG).show();
            }
            else if(mGame.player == -1) {
                Toast.makeText(context, "Draw !", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context, "Congratulations! Player 2 (RED) won!", Toast.LENGTH_LONG).show();
            }
            startGame();
        }

    }

    // SetDisc method
    private void setDisc() {
        for (int buttonIndex = 0; buttonIndex < mGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);
            int row = buttonIndex / ConnectFourGame.COL;
            int col = buttonIndex % ConnectFourGame.COL;

            Drawable discDrawable;

            // Instantiate an instance of class Drawable for the three drawable discs
            switch (mGame.getDisc(row, col)) {
                case ConnectFourGame.BLUE:
                    discDrawable = ContextCompat.getDrawable(context, R.drawable.circle_blue);
                    break;
                case ConnectFourGame.RED:
                    discDrawable = ContextCompat.getDrawable(context, R.drawable.circle_red);
                    break;
                default:
                    discDrawable = ContextCompat.getDrawable(context, R.drawable.circle_white);
                    break;
            }
            if(discDrawable != null) {
                discDrawable = DrawableCompat.wrap(discDrawable);
                gridButton.setBackground(discDrawable);
            }
        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the game state
        outState.putString(GAME_STATE, mGame.getState());
    }
}
