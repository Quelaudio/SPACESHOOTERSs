package pt.uma.tpsi.arqd.entitys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.game.Animator;

public class Explosion {
    protected int posX, posY;
    protected Animator animator;
    protected String pathString;

    public Explosion(int posX, int posY, SpriteBatch batch, String pathString, int colum, int rows) {
        this.posY = posY;
        this.posX = posX;
        this.pathString = pathString;
        this.animator = new Animator(batch, pathString, colum, rows);

    }
}
