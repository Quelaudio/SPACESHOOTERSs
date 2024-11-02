package pt.uma.tpsi.arqd.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;

public class Laser {

    // Attributes
    protected int posX, posY;
    protected Animator animator;
    protected Rectangle boundingBox;
    private boolean flip;
    private int bulletSpeed;
    private float angle;  // New angle attribute for diagonal movement
    int screenHeight = Gdx.graphics.getHeight();
    int screenWidth = Gdx.graphics.getWidth(); // Also checking screen width for out-of-bounds

    // Constructor with angle parameter
    public Laser(int posX, int posY, SpriteBatch batch, boolean flip, int bulletSpeed, float angle) {
        this.posX = posX;
        this.posY = posY;
        this.flip = flip;
        this.bulletSpeed = bulletSpeed;
        this.angle = angle;
        this.animator = new Animator(batch, "laser-bolts.png", 2, 1);
        this.animator.setFlip(this.flip);
    }

    // Check if the laser is out of bounds
    public boolean isOutOfBounds() {
        return (this.posY > screenHeight || this.posY < 0 || this.posX < 0 || this.posX > screenWidth);
    }

    public void create() {
        animator.create();
        boundingBox = new Rectangle(posX, posY, animator.getWidth(), animator.getHeight());
    }

    public void render() {

        float deltaX = (float) (bulletSpeed * Math.cos(Math.toRadians(angle)));
        float deltaY = (float) (bulletSpeed * Math.sin(Math.toRadians(angle)));

        this.posX += deltaX;
        this.posY += deltaY;

        animator.render(posX, posY);
        boundingBox.setPosition(posX, posY);
    }
}
