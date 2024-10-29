package pt.uma.tpsi.arqd.entitys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;

public class Laser {

        //atributos
    protected int posX,posY;
    protected Animator animator;
    protected Rectangle boundingBox;

    //contrutor basico
    public Laser(int posX,int posY, SpriteBatch batch){
        this.posX = posX;
        this.posY = posY;
        this.animator= new Animator(batch,"laser-bolts.png",2,1);


    }


    public void create() {
        animator.create();
        boundingBox = new Rectangle(posX,posY,animator.getWidth(), animator.getHeight());
    }

    public void render() {
        this.posY +=7;
        animator.render(posX, posY);
        boundingBox.setPosition(posX,posY);

    }
}
