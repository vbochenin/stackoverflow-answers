package algorithm;

public interface AttributeConverter<T, T1> {
    Integer convertToDatabaseColumn(T t);

    T convertToEntityAttribute(Integer integer);
}
