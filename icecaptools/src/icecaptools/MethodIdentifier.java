package icecaptools;

public interface MethodIdentifier {
    String getClassName();

    String getName();

    String getSignature();

    int getGenericValue();

    void setGenericValue(int m);
}
