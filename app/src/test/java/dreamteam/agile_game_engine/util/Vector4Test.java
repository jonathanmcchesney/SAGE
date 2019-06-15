package dreamteam.agile_game_engine.util;


import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * This class was created by Simon
 */

public class Vector4Test {

    @Test
    public void testVector4Creation()
    {
        Vector4 testVector4 = new Vector4(4,3,2,1);

        assertNotNull(testVector4);
    }

    @Test
    public void testVector4GetX()
    {
        Vector4 testVector4 = new Vector4(4,3,2,1);

        assertEquals(testVector4.getX(), 4, 0);
    }

    @Test
    public void testVector4GetY()
    {
        Vector4 testVector4 = new Vector4(4,3,2,1);

        assertEquals(testVector4.getY(), 3, 0);
    }

    @Test
    public void testVector4GetZ()
    {
        Vector4 testVector4 = new Vector4(4,3,2,1);

        assertEquals(testVector4.getZ(), 2, 0);
    }

    @Test
    public void testVector4GetW()
    {
        Vector4 testVector4 = new Vector4(4,3,2,1);

        assertEquals(testVector4.getW(), 1, 0);
    }

    @Test
    public void testVector4Magnitude()
    {
        Vector4 magnitudeVector = new Vector4(4,3,2,1);

        assertEquals(magnitudeVector.magnitude(), 5.477, 0.1);
    }

    @Test
    public void testVector4SqrMagnitude()
    {
        Vector4 sqrMagnitudeVector = new Vector4(4,3,2,1);

        assertEquals(sqrMagnitudeVector.sqrMagnitude(), 30, 0);
    }


    @Test
    public void testVector4Normalized()
    {
        Vector4 testNormalized = new Vector4(4,3,2,1);
        double magnitude = testNormalized.magnitude();
        assertEquals(testNormalized.getX(), 4, 0);
        assertEquals(testNormalized.getY(), 3, 0);
        assertEquals(testNormalized.getZ(), 2, 0);
        assertEquals(testNormalized.getW(), 1, 0);

        testNormalized.normalize();

        assertEquals(testNormalized.getX(), 4.0/magnitude, 0.0001);
        assertEquals(testNormalized.getY(), 3.0/magnitude, 0.0001);
        assertEquals(testNormalized.getZ(), 2.0/magnitude, 0.0001);
        assertEquals(testNormalized.getW(), 1.0/magnitude, 0.0001);

    }

    @Test
    public void testVector4Normalize()
    {
        Vector4 testNormalize = new Vector4(4,3,2,1);

        assertEquals(testNormalize.normalized().getX(), 4.0/testNormalize.magnitude(), 0.0001);
        assertEquals(testNormalize.normalized().getY(), 3.0/testNormalize.magnitude(), 0.0001);
        assertEquals(testNormalize.normalized().getZ(), 2.0/testNormalize.magnitude(), 0.0001);
        assertEquals(testNormalize.normalized().getW(), 1.0/testNormalize.magnitude(), 0.0001);

    }

    @Test
    public void testVector4SetX()
    {
        Vector4 setterVector = new Vector4(4,3,2,1);

        assertEquals(setterVector.getX(), 4, 0);

        setterVector.setX(1);

        assertEquals(setterVector.getX(), 1, 0);

    }

    @Test
    public void testVector4SetY()
    {
        Vector4 setterVector = new Vector4(4,3,2,1);

        assertEquals(setterVector.getY(), 3, 0);

        setterVector.setY(2);

        assertEquals(setterVector.getY(), 2, 0);

    }

    @Test
    public void testVector4SetZ()
    {
        Vector4 setterVector = new Vector4(4,3,2,1);

        assertEquals(setterVector.getZ(), 2, 0);

        setterVector.setZ(3);

        assertEquals(setterVector.getZ(), 3, 0);

    }

    @Test
    public void testVector4SetW()
    {
        Vector4 setterVector = new Vector4(4,3,2,1);

        assertEquals(setterVector.getW(), 1, 0);

        setterVector.setW(4);

        assertEquals(setterVector.getW(), 4, 0);

    }

    @Test
    public void testZero()
    {
        Vector4 zeroVector = Vector4.zero();
        assertEquals(zeroVector.getX(), 0, 0);
        assertEquals(zeroVector.getY(), 0, 0);
        assertEquals(zeroVector.getZ(), 0, 0);
        assertEquals(zeroVector.getW(), 0, 0);
    }

    @Test
    public void testRight()
    {
        Vector4 rightVector = Vector4.right();
        assertEquals(rightVector.getX(), 1, 0);
        assertEquals(rightVector.getY(), 0, 0);
        assertEquals(rightVector.getZ(), 0, 0);
        assertEquals(rightVector.getW(), 0, 0);
    }

    @Test
    public void testUp()
    {
        Vector4 upVector = Vector4.up();
        assertEquals(upVector.getX(), 0, 0);
        assertEquals(upVector.getY(), 1, 0);
        assertEquals(upVector.getZ(), 0, 0);
        assertEquals(upVector.getW(), 0, 0);
    }

    @Test
    public void testForward()
    {
        Vector4 forwardVector = Vector4.forward();
        assertEquals(forwardVector.getX(), 0, 0);
        assertEquals(forwardVector.getY(), 0, 0);
        assertEquals(forwardVector.getZ(), 1, 0);
        assertEquals(forwardVector.getW(), 0, 0);
    }

    @Test
    public void testAlpha()
    {
        Vector4 alphaVector = Vector4.alpha();
        assertEquals(alphaVector.getX(), 0, 0);
        assertEquals(alphaVector.getY(), 0, 0);
        assertEquals(alphaVector.getZ(), 0, 0);
        assertEquals(alphaVector.getW(), 1, 0);
    }

    @Test
    public void testVector4Add()
    {
        Vector4 testVector4 = new Vector4(4,3,2,1);

        assertEquals(testVector4.getX(), 4, 0);
        assertEquals(testVector4.getY(), 3, 0);
        assertEquals(testVector4.getZ(), 2, 0);
        assertEquals(testVector4.getW(), 1, 0);

        Vector4 resultVector = testVector4.add(new Vector4(1,2,3,4));

        assertEquals(resultVector.getX(), 5, 0);
        assertEquals(resultVector.getY(), 5, 0);
        assertEquals(resultVector.getZ(), 5, 0);
        assertEquals(resultVector.getW(), 5, 0);
    }

    @Test
    public void testVector4Subtract()
    {
        Vector4 testVector4 = new Vector4(4,3,2,1);

        assertEquals(testVector4.getX(), 4, 0);
        assertEquals(testVector4.getY(), 3, 0);
        assertEquals(testVector4.getZ(), 2, 0);
        assertEquals(testVector4.getW(), 1, 0);

        Vector4 resultVector = testVector4.subtract(new Vector4(3,2,1,1));

        assertEquals(resultVector.getX(), 1, 0);
        assertEquals(resultVector.getY(), 1, 0);
        assertEquals(resultVector.getZ(), 1, 0);
        assertEquals(resultVector.getW(), 0, 0);
    }

}