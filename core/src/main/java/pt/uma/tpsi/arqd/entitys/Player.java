package pt.uma.tpsi.arqd.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.game.Animator;
import java.util.ArrayList;

public class Player extends Ship {
    private int posX, posY;
    private final Animator animator;
    private final int speed = 10;
    private ArrayList<Laser> laserArrayList;
    private SpriteBatch batch;
    int bulletSpeed = 5;
    int HP = 100;
    private int bulletDamage = 0;


    public Player(int posX, int posY, SpriteBatch batch, String pathString, String bulletString, int colum, int rows,int width,int height) {
        super(posX, posY, batch, pathString, bulletString, colum, rows,height,width);
        this.animator = new Animator(batch, pathString, colum, rows);
        this.laserArrayList = new ArrayList<Laser>();
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

        for(Laser l : laserArrayList){
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


    public String StringHealth(){

        return "Player Health:" + HP;
    }



    private void keepWithinBounds() {
        int screenWidth = Gdx.graphics.getWidth();
//        int screenHeight = Gdx.graphics.getHeight();
        int playerWidth = animator.getWidth();
        int playerHeight = animator.getHeight();


        posX = Math.max(0, Math.min(posX, screenWidth - playerWidth));
        posY = Math.max(0, Math.min(posY, 300 - playerHeight));
    }

    public ArrayList<Laser> getLaserArrayList(){
        return this.laserArrayList;
    }

    //  shooting logic
    @Override
    public void shoot() {
        Laser l = new Laser(this.posX,this.posY, batch,false,this.bulletSpeed);
        l.create();
        laserArrayList.add(l);
    }

    @Override
    public int getDamage() {
        return 0;
    }
}
