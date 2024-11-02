package pt.uma.tpsi.arqd.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;

public class Explosion {
    protected int posX, posY;
    protected Animator animator;
    protected Rectangle boundingBox;
    private boolean animationFinished = false;

    private float elapsedTime = 0f;  //o elapse time vai dar track do tempo da animação
    private  float extra_Duration = 0.5f;  // este extra_Duration vai adicionar tempo animação

    public Explosion(int posX, int posY, SpriteBatch batch, String pathString, int colum, int rows) {
        this.posY = posY;
        this.posX = posX;
        this.animator = new Animator(batch, pathString, colum, rows);
        this.boundingBox = new Rectangle(posX, posY, 50, 50);

    }

    public void create() {
        animator.create();

    }

    public void render() {
        elapsedTime += Gdx.graphics.getDeltaTime(); //ua o deltaTime para receber o tempo
        animator.render(posX, posY);
        boundingBox.setPosition(posX, posY);


        if (elapsedTime >= extra_Duration) {
            animationFinished = true;
        }

    }


    public boolean isFinished() {
//        System.out.println(elapsedTime);// debugging
//        System.out.println(animationFinished);// debugging
        return animationFinished;
    }
}
