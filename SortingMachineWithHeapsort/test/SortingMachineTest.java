import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Layan Abdallah & Oak Hodous
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    /**
     * testing the constructor.
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /**
     * testing adding to an empty.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    //ADD TEST CASES
    /**
     * Testing adding to an empty heap.
     */
    @Test
    public final void testAddToEmptyHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red");
        m.add("red");
        assertEquals(mExpected, m);
    }

    /**
     * Testing adding to a non empty heap only one entry.
     */
    @Test
    public final void testAddToOneEntryHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "yellow");
        m.add("yellow");
        assertEquals(mExpected, m);
    }

    /**
     * Testing adding to a non empty heap multiple entries.
     */
    @Test
    public final void testAddToMultipleEntriesHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "yellow", "orange");
        m.add("orange");
        assertEquals(mExpected, m);
    }

    //CHANGETOEXTRACTION MODE TEST CASES
    /**
     * Testing changing to extraction mode when the heap is empty.
     */
    @Test
    public final void testChangeToExtractionModeEmptyHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER,
                false);

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /**
     * Testing changing to extraction mode when the heap has one entry.
     */
    @Test
    public final void testChangeToExtractionModeOneEntryHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "red");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /**
     * Testing changing to extraction mode with multiple entries.
     */
    @Test
    public final void testChangeToExtractionModeMultipleEntriesHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "yellow", "purple");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "red", "yellow", "purple");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    //REMOVE FIRST TEST CASES
    /**
     * Testing removing first causing an empty heap.
     */
    @Test
    public final void testRemoveFirstToEmptyHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER,
                false);

        String removed = m.removeFirst();
        String expectedR = "green";

        assertEquals(expectedR, removed);
        assertEquals(mExpected, m);
    }

    /**
     * Testing removing first causing a heap with only 1 entry.
     */
    @Test
    public final void testRemoveFirstOneEntryHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "red",
                "green");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "red");

        String removed = m.removeFirst();
        String expectedR = "green";

        assertEquals(expectedR, removed);
        assertEquals(mExpected, m);
    }

    /**
     * Testing removing first causing a heap with multiply entries.
     */
    @Test
    public final void testRemoveFirstMultipleEntriesHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "yellow", "red", "purple");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "yellow", "red");

        String removed = m.removeFirst();
        String expectedR = "purple";

        assertEquals(expectedR, removed);
        assertEquals(mExpected, m);
    }

    //MORE REMOVE TEST CASES
    /**
     * Testing remove causing an empty heap.
     */
    @Test
    public final void testRemoveToEmptyHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "hello");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        String temp = m.removeFirst();
        String expectedTemp = "hello";

        assertEquals(mExpected, m);
        assertEquals(expectedTemp, temp);

    }

    /**
     * Testing remove causing a heap with one entry.
     */
    @Test
    public final void testRemoveHeapWithOneEntry() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String temp = m.removeFirst();
        String expectedT = "green";
        assertEquals(mExpected, m);
        assertEquals(expectedT, temp);
    }

    /**
     * Testing remove causing a heap with multiple entries.
     */
    @Test
    public final void testRemoveHeapWithMultipleEntries() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "yellow", "red");
        String temp = m.removeFirst();
        String expectedT = "green";
        assertEquals(mExpected, m);
        assertEquals(expectedT, temp);
    }

    //IS IN INSTERTION MODE TEST CASES
    /**
     * Testing is in insertion mode on a non empty heap when it's true.
     */
    @Test
    public final void testIsInInsertionModeTrueNonEmptyHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        assertTrue(m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Testing is in insertion mode on a non empty heap when it's false.
     */
    @Test
    public final void testIsInInsertionModeFalseNonEmptyHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        assertFalse(m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Testing is in insertion mode on an empty heap when it's true.
     */
    @Test
    public final void testIsInInsertionModeTrueEmptyHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertTrue(m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Testing is in insertion mode on an empty heap when it's false.
     */
    @Test
    public final void testIsInInsertionModeFalseEmptyHeap() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertFalse(m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    //SIZE TEST CASES
    /**
     * Testing size when empty in insertion mode.
     *
     * @Test public final void testSizeEmptyInsertionMode() {
     *       SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
     *       SortingMachine<String> mExpected = this.createFromArgsRef(ORDER,
     *       true); assertEquals(0, m.size()); assertEquals(mExpected, m); }
     *
     *       /** Testing size when empty in extraction mode.
     */
    @Test
    public final void testSizeEmptyExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(0, m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Testing size when not empty in insertion mode.
     */
    @Test
    public final void testSizeNonEmptyInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red");
        assertEquals(2, m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Testing size when not empty in extraction mode.
     */
    @Test
    public final void testSizeNonEmptyExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red");
        assertEquals(2, m.size());
        assertEquals(mExpected, m);
    }

    //ORDER TEST CASES
    /**
     * Testing order when empty in insertion mode.
     */
    @Test
    public final void testOrderEmptyInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    /**
     * Testing order when empty in extraction mode.
     */
    @Test
    public final void testOrderEmptyExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    /**
     * Testing order when non empty in insertion mode.
     */
    @Test
    public final void testOrderNonEmptyInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red");
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    /**
     * Testing order when non empty in extraction mode.
     */
    @Test
    public final void testOrderNonEmptyExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red");
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

}
