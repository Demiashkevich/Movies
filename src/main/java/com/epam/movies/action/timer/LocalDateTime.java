package com.epam.movies.action.timer;

import org.joda.time.LocalDate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LocalDateTime implements Iterable<LocalDate> {

    private final LocalDate START;
    private final LocalDate END;

    public LocalDateTime(LocalDate START, LocalDate END) {
        this.START = START;
        this.END = END;
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return new LocalDateTimeIterator(START, END);
    }

    private class LocalDateTimeIterator implements Iterator<LocalDate> {

        private LocalDate current;
        private LocalDate end;

        LocalDateTimeIterator(LocalDate current, LocalDate end) {
            this.current = current;
            this.end = end;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        public LocalDate next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            LocalDate ret = current;
            current = current.plusMonths(1);
            if (current.compareTo(end) > 0) {
                current = null;
            }
            return ret;
        }
    }

}
