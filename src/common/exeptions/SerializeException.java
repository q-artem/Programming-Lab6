package common.exeptions;

public class SerializeException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Произошла ошибка при формировании объекта - невозможно сериализовать запрос.";
    }
}
