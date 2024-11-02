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
    public int bulletSpeed = 5;
    public int HP = 100;
    public int Score;
    private boolean isDead = false;
    private Explosion explosion;






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
        verifyDeath(); // Verifica se o jogador morreu
        handleExplosion();
        if (!isDead ) {
            handleInput();
            keepWithinBounds();
            boundingBox.setPosition(posX, posY);
            animator.render(posX, posY);
        }

        laserArrayList.removeIf(Laser::isOutOfBounds);
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

    public String scoreString(){
        return "Score:" + Score;
    }


    private void keepWithinBounds() {

       int screenWidth = Gdx.graphics.getWidth();
        int playerWidth = animator.getWidth();
        int playerHeight = animator.getHeight();


        posX = Math.max(0, Math.min(posX, screenWidth - playerWidth));
        posY = Math.max(0, Math.min(posY, 300 - playerHeight));
    }

    public ArrayList<Laser> getLaserArrayList(){
        return this.laserArrayList;
    }



    public void handleExplosion() {
        //caso o palyer esta marcado
        if (isDead) {
            if (explosion == null) {
                explosion = new Explosion(posX, posY, batch, "explosion.png", 4, 1);
                explosion.create();
            }
            // Renderiza a explosão enquanto nao esta "Finished"
            if (!explosion.isFinished()) {
                explosion.render();
            }
        }
    }


    public void verifyDeath() {
        if (HP <= 0) {
            isDead = true; // Marca o jogador como morto

        }
    }

    //  shooting logic
    @Override
    public void shoot() {
        if (!isDead) {
            Laser l = new Laser(this.posX, this.posY, batch, false, this.bulletSpeed,90);
            l.create();
            laserArrayList.add(l);
        }
    }


    @Override
    public int getDamage() {
        return 0;
    }
}
