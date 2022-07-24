package disjointsets;

import disjointsets.concrete.QuickFindDS;
import disjointsets.concrete.QuickUnionDS;
import disjointsets.concrete.WeightedQuickUnionDS;
import disjointsets.concrete.WeightedQuickUnionDSWithPathCompression;
import disjointsets.view.DisjointSets;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TestDisjointSets {

    @Test
    public void testBaseFunctions() {
        DisjointSets[] manyDisjointSets = new DisjointSets[]{
            new QuickFindDS(10),
            new QuickUnionDS(10),
            new WeightedQuickUnionDS(10),
            new WeightedQuickUnionDSWithPathCompression(10)
        };

        for (DisjointSets disjointSets : manyDisjointSets) {
            disjointSets.connect(4, 3);
            disjointSets.connect(3, 8);
            disjointSets.connect(6, 5);
            disjointSets.connect(9, 4);
            disjointSets.connect(2, 1);

            assertTrue(disjointSets.isConnected(8, 9));

            disjointSets.connect(5, 0);
            disjointSets.connect(7, 2);
            disjointSets.connect(6, 1);

            assertTrue(disjointSets.isConnected(1, 0));
            assertTrue(disjointSets.isConnected(6, 7));
            assertTrue(disjointSets.isConnected(4, 9));
            assertFalse(disjointSets.isConnected(1, 8));
            assertFalse(disjointSets.isConnected(2, 9));
            assertFalse(disjointSets.isConnected(6, 4));
        }
    }

    @Test
    public void testQuickFind() {
        QuickFindDS quickFindDS = new QuickFindDS(10);

        quickFindDS.connect(4, 3);
        quickFindDS.connect(3, 8);

        assertEquals(0, quickFindDS.heightOf(8));
        assertEquals(1, quickFindDS.heightOf(3));
        assertEquals(3, quickFindDS.sizeOf(8));
        assertEquals(3, quickFindDS.sizeOf(3));

        quickFindDS.connect(6, 5);
        quickFindDS.connect(9, 4);
        quickFindDS.connect(2, 1);
        quickFindDS.connect(5, 0);
        quickFindDS.connect(7, 2);

        assertEquals(0, quickFindDS.heightOf(1));
        assertEquals(1, quickFindDS.heightOf(2));
        assertEquals(0, quickFindDS.heightOf(0));
        assertEquals(1, quickFindDS.heightOf(6));
        assertEquals(3, quickFindDS.sizeOf(2));
        assertEquals(3, quickFindDS.sizeOf(5));

        quickFindDS.connect(6, 1);

        assertEquals(0, quickFindDS.heightOf(1));
        assertEquals(1, quickFindDS.heightOf(2));
        assertEquals(1, quickFindDS.heightOf(5));
        assertEquals(1, quickFindDS.heightOf(6));
        assertEquals(6, quickFindDS.sizeOf(0));
        assertEquals(6, quickFindDS.sizeOf(6));
        assertEquals(6, quickFindDS.sizeOf(7));

    }

    @Test
    public void testQuickUnion() {
        QuickUnionDS quickUnionDS = new QuickUnionDS(10);

        quickUnionDS.connect(4, 3);
        quickUnionDS.connect(3, 8);

        assertEquals(0, quickUnionDS.heightOf(8));
        assertEquals(1, quickUnionDS.heightOf(3));
        assertEquals(2, quickUnionDS.heightOf(4));
        assertEquals(3, quickUnionDS.sizeOf(8));
        assertEquals(3, quickUnionDS.sizeOf(4));

        quickUnionDS.connect(6, 5);
        quickUnionDS.connect(9, 4);
        quickUnionDS.connect(2, 1);
        quickUnionDS.connect(5, 0);
        quickUnionDS.connect(7, 2);

        assertEquals(0, quickUnionDS.heightOf(0));
        assertEquals(2, quickUnionDS.heightOf(6));
        assertEquals(0, quickUnionDS.heightOf(1));
        assertEquals(1, quickUnionDS.heightOf(7));
        assertEquals(3, quickUnionDS.sizeOf(7));
        assertEquals(3, quickUnionDS.sizeOf(0));

        quickUnionDS.connect(6, 1);

        assertEquals(0, quickUnionDS.heightOf(1));
        assertEquals(1, quickUnionDS.heightOf(2));
        assertEquals(2, quickUnionDS.heightOf(5));
        assertEquals(3, quickUnionDS.heightOf(6));
        assertEquals(6, quickUnionDS.sizeOf(1));
        assertEquals(6, quickUnionDS.sizeOf(7));
        assertEquals(6, quickUnionDS.sizeOf(5));
        assertEquals(6, quickUnionDS.sizeOf(6));

    }

    @Test
    public void testWeightedQuickUnion() {
        WeightedQuickUnionDS weightedQuickUnionDS = new WeightedQuickUnionDS(10);

        weightedQuickUnionDS.connect(4, 3);
        weightedQuickUnionDS.connect(3, 8);

        assertEquals(0, weightedQuickUnionDS.heightOf(3));
        assertEquals(1, weightedQuickUnionDS.heightOf(8));
        assertEquals(3, weightedQuickUnionDS.sizeOf(3));
        assertEquals(3, weightedQuickUnionDS.sizeOf(8));

        weightedQuickUnionDS.connect(6, 5);
        weightedQuickUnionDS.connect(9, 4);
        weightedQuickUnionDS.connect(2, 1);
        weightedQuickUnionDS.connect(5, 0);
        weightedQuickUnionDS.connect(7, 2);

        assertEquals(0, weightedQuickUnionDS.heightOf(1));
        assertEquals(1, weightedQuickUnionDS.heightOf(2));
        assertEquals(0, weightedQuickUnionDS.heightOf(5));
        assertEquals(1, weightedQuickUnionDS.heightOf(0));
        assertEquals(3, weightedQuickUnionDS.sizeOf(1));
        assertEquals(3, weightedQuickUnionDS.sizeOf(6));

        weightedQuickUnionDS.connect(6, 1);

        assertEquals(0, weightedQuickUnionDS.heightOf(1));
        assertEquals(1, weightedQuickUnionDS.heightOf(2));
        assertEquals(1, weightedQuickUnionDS.heightOf(5));
        assertEquals(2, weightedQuickUnionDS.heightOf(6));
        assertEquals(6, weightedQuickUnionDS.sizeOf(1));
        assertEquals(6, weightedQuickUnionDS.sizeOf(2));
        assertEquals(6, weightedQuickUnionDS.sizeOf(5));

    }

    @Test
    public void testWeightedQuickUnionWithPathCompression() {
        WeightedQuickUnionDSWithPathCompression weightedQuickUnionDSWithPathCompression =
            new WeightedQuickUnionDSWithPathCompression(10);

        weightedQuickUnionDSWithPathCompression.connect(4, 3);
        weightedQuickUnionDSWithPathCompression.connect(3, 8);

        assertEquals(0, weightedQuickUnionDSWithPathCompression.heightOf(3));
        assertEquals(1, weightedQuickUnionDSWithPathCompression.heightOf(8));
        assertEquals(3, weightedQuickUnionDSWithPathCompression.sizeOf(3));
        assertEquals(3, weightedQuickUnionDSWithPathCompression.sizeOf(8));

        weightedQuickUnionDSWithPathCompression.connect(6, 5);
        weightedQuickUnionDSWithPathCompression.connect(9, 4);
        weightedQuickUnionDSWithPathCompression.connect(2, 1);
        weightedQuickUnionDSWithPathCompression.connect(5, 0);
        weightedQuickUnionDSWithPathCompression.connect(7, 2);

        assertEquals(0, weightedQuickUnionDSWithPathCompression.heightOf(1));
        assertEquals(1, weightedQuickUnionDSWithPathCompression.heightOf(2));
        assertEquals(0, weightedQuickUnionDSWithPathCompression.heightOf(5));
        assertEquals(1, weightedQuickUnionDSWithPathCompression.heightOf(0));
        assertEquals(3, weightedQuickUnionDSWithPathCompression.sizeOf(1));
        assertEquals(3, weightedQuickUnionDSWithPathCompression.sizeOf(6));

        weightedQuickUnionDSWithPathCompression.connect(6, 1);

        assertEquals(0, weightedQuickUnionDSWithPathCompression.heightOf(1));
        assertEquals(1, weightedQuickUnionDSWithPathCompression.heightOf(2));
        assertEquals(1, weightedQuickUnionDSWithPathCompression.heightOf(5));
        assertEquals(2, weightedQuickUnionDSWithPathCompression.heightOf(6));
        assertEquals(6, weightedQuickUnionDSWithPathCompression.sizeOf(1));
        assertEquals(6, weightedQuickUnionDSWithPathCompression.sizeOf(2));
        assertEquals(6, weightedQuickUnionDSWithPathCompression.sizeOf(5));

        weightedQuickUnionDSWithPathCompression.find(6);
        assertEquals(1, weightedQuickUnionDSWithPathCompression.heightOf(6));

    }

}
