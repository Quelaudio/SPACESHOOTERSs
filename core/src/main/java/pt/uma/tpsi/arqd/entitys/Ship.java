package pt.uma.tpsi.arqd.entitys;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;

public abstract class Ship {
    //atributos
    protected int posX, posY;
    protected Animator animator;
    protected String pathString;
    protected Rectangle boundingBox;
    protected boolean isDestroyed = false;



    //Constutor geral
    public Ship(int posX, int posY, SpriteBatch batch, String pathString, String bulletString, int colum, int rows, int width, int height) {
        this.posY = posY;
        this.posX = posX;
        this.pathString = pathString;
        this.animator = new Animator(batch, pathString, colum, rows);
        this.boundingBox = new Rectangle(posX, posY, width, height);
    }

    //aqui vai returnar a boundingBox aka. HitBox
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void create() {
        animator.create();
        boundingBox.setPosition(posX, posY);
    }

    public void render() {

    // o if serve para ver se ta destruido se nao tiver da render normal se nao passa po else
        if (!isDestroyed) {
            animator.render(posX, posY);
            boundingBox.setPosition(posX, posY);
        }
        else {
            // algo vai vir para aqui agora o que nao sei so sei que deus vai ajudar
        }


    }


    //apenas poem valor true na variavel isDestroyed
    public void explode() {
        isDestroyed = true;
    }


    //aqui apenas retorna o isDestroyed
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public abstract void shoot();
}
