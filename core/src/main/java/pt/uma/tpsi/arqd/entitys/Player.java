package pt.uma.tpsi.arqd.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;
import java.util.ArrayList;

public class Player extends Ship {
    private int posX, posY;
    private final Animator animator;
    private final int speed = 10;
    private ArrayList<Laser> laser;
    private SpriteBatch batch;




    public Player(int posX, int posY, SpriteBatch batch, String pathString, String bulletString, int colum, int rows,int width,int height) {
        super(posX, posY, batch, pathString, bulletString, colum, rows,height,width);
        this.animator = new Animator(batch, pathString, colum, rows);
        this.laser= new ArrayList<Laser>();
        this.batch = batch;


    }

    public void create() {
        animator.create();
    }

    public void render() {
        handleInput();
        keepWithinBounds();
        boundingBox.setPosition(posX,posY);
        animator.render(posX, posY);

        for(Laser l : laser){
            l.render();
        }
    }

    // este metodo vai lidar com os inputs do user
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            posY += speed;  // para cima
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            posY -= speed;  // para baixo
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            posX -= speed;  // para esquerda
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            posX += speed;  // para direita
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shoot();  // para disparar spacebar
        }


    }

    private void keepWithinBounds() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int playerWidth = animator.getWidth();
        int playerHeight = animator.getHeight();


        posX = Math.max(0, Math.min(posX, screenWidth - playerWidth));
        posY = Math.max(0, Math.min(posY, screenHeight - playerHeight));
    }

    public ArrayList<Laser> getLaser(){
        return this.laser;
    }


    //  shooting logic
    @Override
    public void shoot() {
        Laser l = new Laser(this.posX,this.posY, batch,false);
        l.create();
        laser.add(l);

    }
}
