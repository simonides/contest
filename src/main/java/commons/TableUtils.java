package commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class TableUtils {

    private static final String NULL_TEXT = " ";

    /**
     * Creates a new table with the given rows and columns, filled with the given value
     */
    public static <T> Table<Integer, Integer, T> create(int rowCount, int colCount, T value) {
        final Table<Integer, Integer, T> table = HashBasedTable.create();

        fill(table, 0, 0, rowCount - 1, colCount - 1, value);
        return table;
    }

    /**
     * Fills the given range of the given table with the given value
     */
    public static <T> void fill(Table<Integer, Integer, T> table, int fromX, int fromY, int toX, int toY, T value) {
        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                table.put(y, x, value);
            }
        }
    }

    /**
     * Retrieves a subtable from the given table
     */
    public static <T> Table<Integer, Integer, T> getSubtable(Table<Integer, Integer, T> table, int fromX, int fromY, int toX, int toY) {
        final HashBasedTable<Integer, Integer, T> newTable = HashBasedTable.create();

        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                final T val = table.get(y, x);
                if (val != null) {
                    newTable.put(y - fromY, x - fromX, val);
                }
            }
        }
        return newTable;
    }

    /**
     * Filters the values of a table based on the given function. Returns a new table.
     */
    public static <T> Table<Integer, Integer, T> filterTable(Table<Integer, Integer, T> table, Function<T, Boolean> filterFunction) {
        final HashBasedTable<Integer, Integer, T> newTable = HashBasedTable.create();

        for (int x = 0; x <= colCount(table); x++) {
            for (int y = 0; y <= rowCount(table); y++) {
                final T value = table.get(y, x);
                if (filterFunction.apply(value)) {
                    newTable.put(y, x, value);
                }
            }
        }
        return newTable;
    }

    /**
     * Trims the empty values from the start of a given table. Returns a new table.
     */
    public static <T> Table<Integer, Integer, T> trimTable(Table<Integer, Integer, T> table) {
        int xMin = getMinXNonEmpty(table);
        int yMin = getMinYNonEmpty(table);

        int xMax = colCount(table);
        int yMax = rowCount(table);

        return getSubtable(table, xMin, yMin, xMax, yMax);
    }

    /**
     * Returns the first x-coordinate (column) of the given table that is not empty.
     */
    public static <T> int getMinXNonEmpty(final Table<Integer, Integer, T> table) {
        int minX = Integer.MAX_VALUE;

        for (int x = 0; x < colCount(table); x++) {
            for (int y = 0; y < rowCount(table); y++) {
                final T val = table.get(y, x);
                if (val != null) {
                    if (x < minX) {
                        minX = x;
                    }
                }
            }
        }

        return minX;
    }

    /**
     * Returns the first y-coordinate (row) of the given table that is not empty.
     */
    public static <T> int getMinYNonEmpty(final Table<Integer, Integer, T> table) {
        int minY = Integer.MAX_VALUE;

        for (int x = 0; x < colCount(table); x++) {
            for (int y = 0; y < rowCount(table); y++) {
                final T val = table.get(y, x);
                if (val != null) {
                    if (y < minY) {
                        minY = y;
                    }
                }
            }
        }

        return minY;
    }

    /**
     * Appends a table to another table at the given position. Overrides existing values.
     */
    public static <T> void appendTable(Table<Integer, Integer, T> table, Table<Integer, Integer, T> to, int xTarget, int yTarget) {

        final int originalTableRowCount = rowCount(table);
        final int originalTableColCount = colCount(table);

        for (int x = 0; x <= originalTableColCount; x++) {
            for (int y = 0; y <= originalTableRowCount; y++) {
                to.put(y + yTarget, x + xTarget, table.get(y, x));
            }
        }
    }

    /**
     * Replaces all null values in a table with the given value.
     */
    public static <T> void replaceNulls(Table<Integer, Integer, T> table, T value) {

        final int rowCount = rowCount(table);
        final int colCount = colCount(table);

        for (int y = 0; y <= rowCount; y++) {
            for (int x = 0; x <= colCount; x++) {
                if (table.get(y, x) == null) {
                    table.put(y, x, value);
                }
            }
        }
    }

    /**
     * Returns a string representation of a table.
     */
    public static <T> String formatTable(Table<Integer, Integer, T> table) {

        final int rowCount = rowCount(table);
        final int colCount = colCount(table);

        StringBuilder out = new StringBuilder();
        out.append("     | ");

        List<Integer> colWidths = new ArrayList<>();

        for (int x = 0; x <= colCount; x++) {
            int maxColWidth = (int)Math.log10(x) + 1;
            for (int y = 0; y <= rowCount; y++) {

                final T val = table.get(y, x);
                final String s = Objects.toString(val, NULL_TEXT);
                maxColWidth = Math.max(maxColWidth, s.length());
            }
            colWidths.add(maxColWidth);
        }

        StringBuilder separator = new StringBuilder("-----+");

        for (int x = 0; x <= colCount; x++) {
            final String s = x + "";
            final int whiteSpaces = colWidths.get(x) - s.length();

            StringBuilder line = new StringBuilder();
            line.append(StringUtils.repeat(NULL_TEXT, whiteSpaces))
                .append(s)
                .append(" | ");
            out.append(line);
            separator.append(StringUtils.repeat("-", line.length() - 1));
            separator.append("+");
        }

        out.append("\n");

        for (int y = 0; y <= rowCount; y++) {

            if (y % 5 == 0) {
                out.append(separator);
                out.append("\n");
            }

            out.append(String.format("%4s", y));
            out.append(" | ");

            for (int x = 0; x <= colCount; x++) {
                final T val = table.get(y, x);

                final String s = Objects.toString(val, " ");
                final int whiteSpaces = colWidths.get(x) - s.length();

                out.append(StringUtils.repeat(" ", whiteSpaces))
                    .append(s)
                    .append(" | ");
            }

            out.append("\n");
        }

        return out.toString();
    }

    /**
     * lo
     */
    public static <T> int rowCount(final Table<Integer, Integer, T> table) {
        return Collections.max(table.rowKeySet());
    }

    public static <T> int colCount(final Table<Integer, Integer, T> table) {
        return Collections.max(table.columnKeySet());
    }
}
