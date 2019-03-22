package commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class Utils {

    public static <T> Table<Integer, Integer, T> getSubtable(Table<Integer, Integer, T> table, int fromX, int toX, int fromY, int toY) {
        final HashBasedTable<Integer, Integer, T> newTable = HashBasedTable.create();

        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                newTable.put(x - fromX, y - fromY, table.get(y, x));
            }
        }
        return newTable;
    }

    public static <T> void appendTable(Table<Integer, Integer, T> table, Table<Integer, Integer, T> to, int xTarget, int yTarget) {

        final int originalTableRowCount = rowCount(table);
        final int originalTableColCount = colCount(table);

        for (int x = 0; x <= originalTableColCount; x++) {
            for (int y = 0; y <= originalTableRowCount; y++) {
                to.put(y + yTarget, x + xTarget, table.get(y, x));
            }
        }
    }

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
                final String s = Objects.toString(val, "null");
                maxColWidth = Math.max(maxColWidth, s.length());
            }
            colWidths.add(maxColWidth);
        }

        StringBuilder separator = new StringBuilder("-----+");

        for (int x = 0; x <= colCount; x++) {
            final String s = x + "";
            final int whiteSpaces = colWidths.get(x) - s.length();

            StringBuilder line = new StringBuilder();
            line.append(StringUtils.repeat(" ", whiteSpaces))
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

                final String s = Objects.toString(val, "null");
                final int whiteSpaces = colWidths.get(x) - s.length();

                out.append(StringUtils.repeat(" ", whiteSpaces))
                    .append(s)
                    .append(" | ");
            }

            out.append("\n");
        }

        return out.toString();
    }

    private static <T> int rowCount(final Table<Integer, Integer, T> table) {
        return Collections.max(table.rowKeySet());
    }

    private static <T> int colCount(final Table<Integer, Integer, T> table) {
        return Collections.max(table.columnKeySet());
    }
}
