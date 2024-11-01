package pt.uma.tpsi.arqd.entitys;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;

public class SmallShip extends Ship{
    int bulletSpeed = 7;
    Rectangle boundingBox;
    private SpriteBatch batch;


    public SmallShip(int posX, int posY, SpriteBatch batch, String pathString,String bulletString, int colum, int rows,int width, int height) {
        super(posX,posY,batch,pathString,bulletString, colum, rows, width,  height);
        this.animator = new Animator(batch, pathString, colum, rows);
        this.boundingBox = new Rectangle(posX,posY, width,height);
        this.batch = batch;
    }
    @Override
    public void shoot() {
        laser = new Laser(this.posX,this.posY,batch,true,this.bulletSpeed);
        laser.create();
    }

    @Override
    public int getDamage() {
        return 5;
    }

}
