package test;

import icecaptools.IcecapCompileMe;

public class TestBug5 {

    private static class StringBuilderException extends RuntimeException {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private String message;

        /**
         * Constructs a new <code>StringBuilderException</code>.
         */
        public StringBuilderException() {
            setMessage(null);
        }

        public String toString() {
            return null;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String newMessage) {
            message = (newMessage == null) ? "null" : newMessage;
        }

    } 

    private static class StringBuilder {
        private StringBuilderException exception;

        @IcecapCompileMe
        public StringBuilder(int capacity) {
            exception = new StringBuilderException();
            if (capacity < 0) {
                throw exception;
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new StringBuilder(100);
        args = null;
    }
}
