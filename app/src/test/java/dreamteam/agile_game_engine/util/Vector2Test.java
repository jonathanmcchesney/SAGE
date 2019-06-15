package dreamteam.agile_game_engine.util;

/**
 * This class was created by Simon
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class Vector2Test {

    @Test
    public void testVector2Creation()
    {
        Vector2 vector2 = new Vector2(1,2);

        assertNotNull(vector2);
    }

    @Test
    public void testVector2GetX()
    {
        Vector2 vector2 = new Vector2(1,2);

        assertEquals(vector2.getX(), 1, 0);
    }

    @Test
    public void testVector2GetY()
    {
        Vector2 vector2 = new Vector2(1,2);

        assertEquals(vector2.getY(), 2, 0);
    }


    @Test
    public void testVector2Magnitude()
    {
        Vector2 magnitudeVector = new Vector2(3, 4);

        assertEquals(magnitudeVector.magnitude(), 5, 0);
    }

    @Test
    public void testVector2SqrMagnitude()
    {
        Vector2 sqrMagnitudeVector = new Vector2(3, 4);

        assertEquals(sqrMagnitudeVector.sqrMagnitude(), 25, 0);
    }


    @Test
    public void testVector2Normalized()
    {
        Vector2 testNormalized = new Vector2(3, 4);

        assertEquals(testNormalized.getX(), 3, 0);
        assertEquals(testNormalized.getY(), 4, 0);

        testNormalized.normalize();

        assertEquals(testNormalized.getX(), 3.0f/5.0f, 0.0001);
        assertEquals(testNormalized.getY(), 4.0f/5.0f, 0.0001);

    }

    @Test
    public void testVector2Normalize()
    {
        Vector2 testNormalize = new Vector2(3, 4);

        assertEquals(testNormalize.normalized().getX(), 3.0/5.0, 0.0001);
        assertEquals(testNormalize.normalized().getY(), 4.0/5.0, 0.0001);

    }

    @Test
    public void testSetters()
    {
        Vector2 setterVector = new Vector2(2, -4);

        assertEquals(setterVector.getX(), 2, 0);
        assertEquals(setterVector.getY(), -4, 0);

        setterVector.setX(1);
        setterVector.setY(2);

        assertEquals(setterVector.getX(), 1, 0);
        assertEquals(setterVector.getY(), 2, 0);

    }

    @Test
    public void testZero()
    {
        Vector2 zeroVector = Vector2.zero();
        assertEquals(zeroVector.getX(), 0, 0);
        assertEquals(zeroVector.getY(), 0, 0);
    }

    @Test
    public void testRight()
    {
        Vector2 rightVector = Vector2.right();
        assertEquals(rightVector.getX(), 1, 0);
        assertEquals(rightVector.getY(), 0, 0);
    }

    @Test
    public void testUp()
    {
        Vector2 upVector = Vector2.up();
        assertEquals(upVector.getX(), 0, 0);
        assertEquals(upVector.getY(), 1, 0);
    }

    @Test
    public void testToVector3()
    {
        Vector2 testVector2 = new Vector2(3,4);
        Vector3 testVector3 = testVector2.toVector3();

        assertEquals(testVector3.getX(), 3, 0);
        assertEquals(testVector3.getY(), 4, 0);
        assertEquals(testVector3.getZ(), 0, 0);
    }

    @Test
    public void testVector2Add()
    {
        Vector2 testVector2 = new Vector2(4,3);

        assertEquals(testVector2.getX(), 4, 0);
        assertEquals(testVector2.getY(), 3, 0);

        Vector2 resultVector = testVector2.add(new Vector2(1,2));

        assertEquals(resultVector.getX(), 5, 0);
        assertEquals(resultVector.getY(), 5, 0);

    }

    @Test
    public void testVector2Subtract()
    {
        Vector2 testVector2 = new Vector2(4,3);

        assertEquals(testVector2.getX(), 4, 0);
        assertEquals(testVector2.getY(), 3, 0);

        Vector2 resultVector = testVector2.subtract(new Vector2(3,2));

        assertEquals(resultVector.getX(), 1, 0);
        assertEquals(resultVector.getY(), 1, 0);
    }


}