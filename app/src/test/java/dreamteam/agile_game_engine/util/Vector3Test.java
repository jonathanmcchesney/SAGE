package dreamteam.agile_game_engine.util;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * This class was created by Simon
 */

public class Vector3Test {

    @Test
    public void testVector3Creation()
    {
        Vector3 testVector3 = new Vector3(2,3,4);

        assertNotNull(testVector3);
    }

    @Test
    public void testVectorGetX()
    {
        Vector3 testVector3 = new Vector3(2,3,4);

        assertEquals(testVector3.getX(), 2, 0);
    }

    @Test
    public void testVectorGetY()
    {
        Vector3 testVector3 = new Vector3(2,3,4);

        assertEquals(testVector3.getY(), 3, 0);
    }

    @Test
    public void testVectorGetW()
    {
        Vector3 testVector3 = new Vector3(2,3,4);

        assertEquals(testVector3.getZ(), 4, 0);
    }


    @Test
    public void testVector3Magnitude()
    {
        Vector3 magnitudeVector = new Vector3(2, -4, 4);

        assertEquals(magnitudeVector.magnitude(), 6, 0);
    }

    @Test
    public void testVector3SqrMagnitude()
    {
        Vector3 sqrMagnitudeVector = new Vector3(2, -4, 4);

        assertEquals(sqrMagnitudeVector.sqrMagnitude(), 36, 0);
    }


    @Test
    public void testVector3Normalized()
    {
        Vector3 testNormalized = new Vector3(2, -4, 4);

        assertEquals(testNormalized.getX(), 2, 0);
        assertEquals(testNormalized.getY(), -4, 0);
        assertEquals(testNormalized.getZ(), 4, 0);

        testNormalized.normalize();

        assertEquals(testNormalized.getX(), 2.0/6, 0.0001);
        assertEquals(testNormalized.getY(), -4.0/6, 0.0001);
        assertEquals(testNormalized.getZ(), 4.0/6, 0.0001);

    }

    @Test
    public void testVector3Normalize()
    {
        Vector3 testNormalize = new Vector3(2, -4, 4);

        assertEquals(testNormalize.normalized().getX(), 2.0/6, 0.0001);
        assertEquals(testNormalize.normalized().getY(), -4.0/6, 0.0001);
        assertEquals(testNormalize.normalized().getZ(), 4.0/6, 0.0001);

    }

    @Test
    public void testVector3SetX()
    {
        Vector3 setterVector = new Vector3(2, -4, 4);

        assertEquals(setterVector.getX(), 2, 0);

        setterVector.setX(1);

        assertEquals(setterVector.getX(), 1, 0);

    }

    @Test
    public void testVector3SetY()
    {
        Vector3 setterVector = new Vector3(2, -4, 4);

        assertEquals(setterVector.getY(), -4, 0);

        setterVector.setY(2);

        assertEquals(setterVector.getY(), 2, 0);

    }


    @Test
    public void testVector3GetZ()
    {
        Vector3 setterVector = new Vector3(2, -4, 4);

        assertEquals(setterVector.getZ(), 4, 0);

        setterVector.setZ(3);

        assertEquals(setterVector.getZ(), 3, 0);

    }

    @Test
    public void testZero()
    {
        Vector3 zeroVector = Vector3.zero();
        assertEquals(zeroVector.getX(), 0, 0);
        assertEquals(zeroVector.getY(), 0, 0);
        assertEquals(zeroVector.getZ(), 0, 0);
    }

    @Test
    public void testRight()
    {
        Vector3 rightVector = Vector3.right();
        assertEquals(rightVector.getX(), 1, 0);
        assertEquals(rightVector.getY(), 0, 0);
        assertEquals(rightVector.getZ(), 0, 0);
    }

    @Test
    public void testUp()
    {
        Vector3 upVector = Vector3.up();
        assertEquals(upVector.getX(), 0, 0);
        assertEquals(upVector.getY(), 1, 0);
        assertEquals(upVector.getZ(), 0, 0);
    }

    @Test
    public void testForward()
    {
        Vector3 forwardVector = Vector3.forward();
        assertEquals(forwardVector.getX(), 0, 0);
        assertEquals(forwardVector.getY(), 0, 0);
        assertEquals(forwardVector.getZ(), 1, 0);
    }

    @Test
    public void testVector3Add()
    {
        Vector3 testVector3 = new Vector3(4,3,2);

        assertEquals(testVector3.getX(), 4, 0);
        assertEquals(testVector3.getY(), 3, 0);
        assertEquals(testVector3.getZ(), 2, 0);

        Vector3 resultVector = testVector3.add(new Vector3(1,2,3));

        assertEquals(resultVector.getX(), 5, 0);
        assertEquals(resultVector.getY(), 5, 0);
        assertEquals(resultVector.getZ(), 5, 0);

    }

    @Test
    public void testVector3Subtract()
    {
        Vector3 testVector3 = new Vector3(4,3,2);

        assertEquals(testVector3.getX(), 4, 0);
        assertEquals(testVector3.getY(), 3, 0);
        assertEquals(testVector3.getZ(), 2, 0);

        Vector3 resultVector = testVector3.subtract(new Vector3(3,2,1));

        assertEquals(resultVector.getX(), 1, 0);
        assertEquals(resultVector.getY(), 1, 0);
        assertEquals(resultVector.getZ(), 1, 0);
    }

    @Test
    public void testVector3ToVector4()
    {
        Vector3 testVector3 = new Vector3(1,2,3);

        Vector4 testVector4 = testVector3.toVector4();

        assertEquals(testVector4.getX(), testVector3.getX(), 0);
        assertEquals(testVector4.getY(), testVector3.getY(), 0);
        assertEquals(testVector4.getZ(), testVector3.getZ(), 0);
        assertEquals(testVector4.getW(), 0, 0);
    }

}