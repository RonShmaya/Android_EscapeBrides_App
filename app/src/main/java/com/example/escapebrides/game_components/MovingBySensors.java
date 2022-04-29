package com.example.escapebrides.game_components;



import android.util.Log;

import com.example.escapebrides.callBacks.CallBack_changeDirection;
import com.example.escapebrides.tools.GameServices;

public class MovingBySensors extends Moving{
   private Character character;
   private float x_direction;
   private float y_direction;
   private CallBack_changeDirection changeDirection = new CallBack_changeDirection() {
        @Override
        public void changeDirection(float x, float y ) {
            x_direction = x;
            y_direction = y;
            active_callback_moving();
        }
    };

    public MovingBySensors(Character character) {
        this.character = character;
        GameServices.getGameServices().setCallBack_stepDetector(changeDirection);
    }

    @Override
    protected void active_callback_moving() {
        float x = Math.abs(x_direction);
        float y = Math.abs(y_direction);
        if(x < y)
            move_horizontal();
        else
            move_vertiacal();


        Log.d("myLog" , "value is    "+ this.x_direction + "   ---- " + this.y_direction);
    }

    private void move_horizontal() {
        if (y_direction > 0)
            this.character.setDirection(Direction.right);
        else
            this.character.setDirection(Direction.left);
    }

    private void move_vertiacal() {
        if (x_direction > 0)
            this.character.setDirection(Direction.down);
        else
            this.character.setDirection(Direction.up);
    }
}
