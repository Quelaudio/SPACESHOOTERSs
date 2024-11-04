package pt.uma.tpsi.arqd.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;

public class LargeShip extends Ship{
    int bulletSpeed = 10;
    Rectangle boundingBox;
    private SpriteBatch batch;


    public LargeShip(int posX, int posY, SpriteBatch batch, String pathString, String bulletString,int colum, int rows,int width, int height) {
        super(posX,posY,batch,pathString,bulletString, colum, rows, width,  height);
        this.animator = new Animator(batch, pathString, colum, rows);
        this.boundingBox = new Rectangle(posX,posY, width,height);
        this.batch = batch;
    }




    @Override
    public void shoot() {
        // isto ira calcular o centro basicamente pegas na altura e with e divides por 2
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        //calcular o angulo da nave para o centro
        float angleToCenter = (float) Math.toDegrees(Math.atan2(centerY - this.posY, centerX - this.posX));

        laser = new Laser(this.posX, this.posY, batch, true, this.bulletSpeed, angleToCenter);
        laser.create();
    }

    @Override
    public int getDamage() {
        return 15;
    }

}
